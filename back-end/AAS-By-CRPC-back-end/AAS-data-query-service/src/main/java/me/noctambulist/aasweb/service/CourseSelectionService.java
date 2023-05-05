package me.noctambulist.aasweb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import me.noctambulist.aasweb.common.exception.CustomException;
import me.noctambulist.aasweb.common.result.R;
import me.noctambulist.aasweb.common.result.ResultEnum;
import me.noctambulist.aasweb.common.util.JsonUtils;
import me.noctambulist.aasweb.controller.CourseSelectionController.Classes;
import me.noctambulist.aasweb.model.ClassSchedule;
import me.noctambulist.aasweb.model.CourseOrder;
import me.noctambulist.aasweb.model.Score;
import me.noctambulist.aasweb.model.StudentInfo;
import me.noctambulist.aasweb.model.TutorInfo;
import me.noctambulist.aasweb.model.mongo.Schedule;
import me.noctambulist.aasweb.repository.IClassSchedule;
import me.noctambulist.aasweb.repository.ICourseOrder;
import me.noctambulist.aasweb.repository.IScore;
import me.noctambulist.aasweb.repository.IStudentInfo;
import me.noctambulist.aasweb.repository.ITutorInfo;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RTransaction;
import org.redisson.api.RedissonClient;
import org.redisson.api.TransactionOptions;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static me.noctambulist.aasweb.common.constant.RedisConstants.SKU_LOCK;
import static me.noctambulist.aasweb.common.constant.RedisConstants.SKU_PREFIX;

/**
 * @Author: Hypocrite30
 * @Date: 2023/5/3 13:48
 */
@Service
@Slf4j
public class CourseSelectionService {

    private final MongoTemplate mongoTemplate;
    private final RedissonClient redissonClient;
    private final StringRedisTemplate stringRedisTemplate;
    private final IStudentInfo iStudentInfo;
    private final ICourseOrder iCourseOrder;
    private final IClassSchedule iClassSchedule;
    private final ITutorInfo iTutorInfo;
    private final IScore iScore;

    @Autowired
    public CourseSelectionService(MongoTemplate mongoTemplate, RedissonClient redissonClient,
                                  StringRedisTemplate stringRedisTemplate, IStudentInfo iStudentInfo,
                                  ICourseOrder iCourseOrder, IClassSchedule iClassSchedule, ITutorInfo iTutorInfo,
                                  IScore iScore) {
        this.mongoTemplate = mongoTemplate;
        this.redissonClient = redissonClient;
        this.stringRedisTemplate = stringRedisTemplate;
        this.iStudentInfo = iStudentInfo;
        this.iCourseOrder = iCourseOrder;
        this.iClassSchedule = iClassSchedule;
        this.iTutorInfo = iTutorInfo;
        this.iScore = iScore;
    }

    @Transactional
    public R chooseCourse(Long studentId, Integer semesterId, List<Classes> classes)
            throws JsonProcessingException {
        StudentInfo studentInfo = iStudentInfo.findByUniqueId(studentId)
                .orElseThrow(() -> new CustomException(ResultEnum.STUDENT_NOT_EXISTS));
        // 1. Judge coupon is enough
        double totalCredit = classes.stream().mapToDouble(Classes::getCredit).sum();
        if (studentInfo.getCoupon() < totalCredit) {
            throw new CustomException(ResultEnum.INSUFFICIENT_COUPON);
        }
        double remainingCoupon = studentInfo.getCoupon() - totalCredit;
        studentInfo.setCoupon(remainingCoupon);
        iStudentInfo.saveAndFlush(studentInfo);
        // 2. Generate course selection order
        Map<String, Long> tutorNameId = new HashMap<>();
        List<Schedule> schedules = new ArrayList<>();
        ArrayNode arrayNode = JsonUtils.newArrayNode();
        for (Classes c : classes) {
            Schedule schedule = mongoTemplate.findOne(
                    Query.query(Criteria.where("course_id").is(c.getCourseId())
                            .and("course_number").is(c.getCourseNum())),
                    Schedule.class);
            if (ObjectUtils.isEmpty(schedule)) {
                throw new CustomException(ResultEnum.COURSE_NOT_EXISTS, JsonUtils.newObjectNode()
                        .put("course_id", c.getCourseId())
                        .put("course_number", c.getCourseNum()));
            }
            schedules.add(schedule);
            TutorInfo tutorInfo = iTutorInfo.findByName(schedule.getTutor())
                    .orElseThrow(() -> new CustomException(ResultEnum.TUTOR_NOT_EXISTS));
            tutorNameId.put(schedule.getTutor(), tutorInfo.getUniqueId());
            ObjectNode node = JsonUtils.newObjectNode();
            node.put("course_id", c.getCourseId());
            node.put("course_number", c.getCourseNum());
            node.put("credit", c.getCredit());
            node.put("course_name", schedule.getCourseName());
            arrayNode.add(node);
        }
        String classesJson = JsonUtils.toJson(arrayNode);
        CourseOrder courseOrder = CourseOrder.builder().studentId(studentId).classesJson(classesJson)
                .totalCredit(totalCredit).build();
        iCourseOrder.saveAndFlush(courseOrder);
        // 3. Generate class schedule
        for (Schedule schedule : schedules) {
            Score score = iScore.saveAndFlush(Score.builder().score(0d).build());
            ClassSchedule classSchedule = ClassSchedule.builder().courseId(schedule.getCourseId())
                    .courseNum(schedule.getCourseNumber()).studentId(studentId)
                    .tutorId(tutorNameId.get(schedule.getTutor())).semesterId(semesterId)
                    .classInfo(JsonUtils.toJson(schedule)).score(score).build();
            iClassSchedule.saveAndFlush(classSchedule);
        }
        // 4. Deduct [sku_courseId_courseNum] in redis
        RTransaction transaction = redissonClient.createTransaction(TransactionOptions.defaults());
        for (Classes c : classes) {
            String key = String.format(SKU_PREFIX, c.getCourseId(), c.getCourseNum());
            RLock lock = redissonClient.getLock(String.format(SKU_LOCK, c.getCourseId(), c.getCourseNum()));
            boolean isLocked = false;
            try {
                isLocked = lock.tryLock(5, 5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                log.error("Redisson trylock failed", e);
            }
            if (!isLocked) {
                throw new CustomException(ResultEnum.INTERNAL_SERVER_ERROR);
            }
            try {
                RBucket<String> bucket = transaction.getBucket(key, StringCodec.INSTANCE);
                String v = bucket.get();
                if (ObjectUtils.isEmpty(v)) {
                    throw new CustomException(ResultEnum.INTERNAL_SERVER_ERROR);
                }
                int remainStock = Integer.parseInt(v);
                if (remainStock < 1) {
                    throw new CustomException(-1, "课余量不足", JsonUtils.newObjectNode()
                            .put("course_id", c.getCourseId())
                            .put("course_number", c.getCourseNum()));
                }
                bucket.setAsync(String.valueOf(remainStock - 1));
            } finally {
                lock.unlock();
            }
        }
        transaction.commit();

        return R.success(JsonUtils.newObjectNode().put("remaining_coupon", remainingCoupon));
    }

    @Transactional
    public R dropCourse(Long studentId, Integer semesterId, String courseId, String courseNum, Double credit) {
        // 1. Update ClassSchedule [status] field to "已退课"
        if (iClassSchedule.dropCourse(studentId, semesterId, courseId, courseNum) != 1) {
            throw new CustomException(-1, "退课失败", JsonUtils.newObjectNode()
                    .put("student_id", studentId).put("course_id", courseId)
                    .put("course_number", courseNum).put("semester_id", semesterId));
        }
        // 2. Increase course sku in Redis
        String key = String.format(SKU_PREFIX, courseId, courseNum);
        RTransaction transaction = redissonClient.createTransaction(TransactionOptions.defaults());
        RLock lock = redissonClient.getLock(String.format(SKU_LOCK, courseId, courseNum));
        try {
            boolean isLocked = false;
            try {
                isLocked = lock.tryLock(20, 20, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                log.error("Redisson trylock failed", e);
            }
            if (!isLocked) {
                throw new CustomException(ResultEnum.INTERNAL_SERVER_ERROR);
            }
            RAtomicLong stock = redissonClient.getAtomicLong(key);
            long remainingStock = stock.incrementAndGet();
            if (remainingStock <= 0) {
                throw new CustomException(-1, "退课失败：库存错误");
            }
        } finally {
            lock.unlock();
        }
        transaction.commit();
        // 3. Refund of credit coupon
        if (iStudentInfo.addCoupon(credit, studentId) != 1) {
            throw new CustomException(-1, "返还学分券错误");
        }

        return R.success();
    }

}
