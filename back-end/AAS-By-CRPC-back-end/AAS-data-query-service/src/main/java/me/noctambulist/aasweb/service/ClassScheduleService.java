package me.noctambulist.aasweb.service;

import me.noctambulist.aasweb.model.ClassSchedule;
import me.noctambulist.aasweb.repository.IClassSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

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

    public List<ClassSchedule> findByTutorId(Long tutorId) {
        return iClassSchedule.findByTutorIdOrderByCourseIdAscCourseNumAsc(tutorId);
    }

}
