package me.noctambulist.aasweb.service;

import me.noctambulist.aasweb.model.CourseOrder;
import me.noctambulist.aasweb.repository.ICourseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Hypocrite30
 * @Date: 2023/5/1 15:55
 */
@Service
public class CourseOrderService {

    private final ICourseOrder iCourseOrder;

    @Autowired
    public CourseOrderService(ICourseOrder iCourseOrder) {
        this.iCourseOrder = iCourseOrder;
    }

    @Transactional
    public CourseOrder create(CourseOrder courseOrder) {
        return iCourseOrder.saveAndFlush(courseOrder);
    }

    @Transactional(readOnly = true)
    public List<CourseOrder> findAll() {
        return iCourseOrder.findAll();
    }

    @Transactional(readOnly = true)
    public List<CourseOrder> findByStudentId(Long studentId) {
        return iCourseOrder.findByStudentId(studentId);
    }

}
