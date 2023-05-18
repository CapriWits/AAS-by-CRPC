package me.noctambulist.aasweb.service;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.noctambulist.aasweb.common.util.JsonUtils;
import me.noctambulist.aasweb.model.ClassSchedule;
import me.noctambulist.aasweb.repository.IClassSchedule;
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

    @Autowired
    public ClassScheduleService(IClassSchedule iClassSchedule) {
        this.iClassSchedule = iClassSchedule;
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

}
