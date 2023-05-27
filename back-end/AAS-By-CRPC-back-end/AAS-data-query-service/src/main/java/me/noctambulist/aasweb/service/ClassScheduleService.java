package me.noctambulist.aasweb.service;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.noctambulist.aasweb.common.util.JsonUtils;
import me.noctambulist.aasweb.model.ClassSchedule;
import me.noctambulist.aasweb.model.StudentInfo;
import me.noctambulist.aasweb.repository.IClassSchedule;
import me.noctambulist.aasweb.repository.IStudentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author: Hypocrite30
 * @Date: 2023/5/1 23:08
 */
@Service
public class ClassScheduleService {

    private final IClassSchedule iClassSchedule;
    private final IStudentInfo iStudentInfo;

    @Autowired
    public ClassScheduleService(IClassSchedule iClassSchedule, IStudentInfo iStudentInfo) {
        this.iClassSchedule = iClassSchedule;
        this.iStudentInfo = iStudentInfo;
    }

    @Transactional
    public ClassSchedule create(ClassSchedule classSchedule) {
        return iClassSchedule.saveAndFlush(classSchedule);
    }

    public ClassSchedule findById(Integer id) {
        return iClassSchedule.findById(id).orElse(null);
    }

    public List<ClassSchedule> findAll() {
        return iClassSchedule.findAll();
    }

    public List<ClassSchedule> findByStudentIdAndSemesterId(Long studentId, Integer semesterId) {
        return iClassSchedule.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate studentIdPredicate = criteriaBuilder.equal(root.get("studentId"), studentId);
            predicates.add(studentIdPredicate);

            Predicate semesterIdPredicate = criteriaBuilder.equal(root.get("semesterId"), semesterId);
            predicates.add(semesterIdPredicate);

            Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), "选中");
            predicates.add(statusPredicate);

            query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
            return null;
        });
    }

    public ArrayNode findByTutorId(Long tutorId) throws IOException {
        ArrayNode arrayNode = JsonUtils.newArrayNode();
        List<Object[]> courseIdsAndNums = iClassSchedule.findDistinctCourseIdAndCourseNumByTutorId(tutorId);
        for (Object[] courseIdAndNum : courseIdsAndNums) {
            ObjectNode node = JsonUtils.newObjectNode();
            String courseId = (String) courseIdAndNum[0];
            String courseNum = (String) courseIdAndNum[1];
            Optional<ClassSchedule> optional = iClassSchedule.findFirstByCourseIdAndCourseNum(courseId, courseNum);
            if (optional.isPresent()) {
                String courseName = (String) JsonUtils.fromJsonToMap(optional.get().getClassInfo()).get("course_name");
                node.put("courseId", courseId);
                node.put("courseNum", courseNum);
                node.put("courseName", courseName);
                arrayNode.add(node);
            }
        }
        return arrayNode;
    }

    public ObjectNode findClassmate(String courseId, String courseNum, Long tutorId) {
        List<ClassSchedule> classSchedules = iClassSchedule.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate courseIdPredicate = criteriaBuilder.equal(root.get("courseId"), courseId);
            predicates.add(courseIdPredicate);

            Predicate courseNumPredicate = criteriaBuilder.equal(root.get("courseNum"), courseNum);
            predicates.add(courseNumPredicate);

            Predicate tutorIdPredicate = criteriaBuilder.equal(root.get("tutorId"), tutorId);
            predicates.add(tutorIdPredicate);

            Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), "选中");
            predicates.add(statusPredicate);

            query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
            return null;
        });
        ArrayNode arrayNode = JsonUtils.newArrayNode();
        for (ClassSchedule classSchedule : classSchedules) {
            ObjectNode tmp = JsonUtils.newObjectNode();
            Optional<StudentInfo> optionalStudentInfo = iStudentInfo.findByUniqueId(classSchedule.getStudentId());
            optionalStudentInfo.ifPresent(studentInfo -> {
                tmp.put("studentId", classSchedule.getStudentId());
                tmp.put("studentName", studentInfo.getName());
            });
            arrayNode.add(tmp);
        }
        ObjectNode node = JsonUtils.newObjectNode();
        node.set("classmates", JsonUtils.objectToJsonNode(classSchedules));
        node.set("studentIdNameMap", arrayNode);
        return node;
    }
}
