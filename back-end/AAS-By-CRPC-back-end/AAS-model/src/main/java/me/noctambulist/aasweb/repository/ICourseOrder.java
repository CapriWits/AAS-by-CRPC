package me.noctambulist.aasweb.repository;

import me.noctambulist.aasweb.model.CourseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Hypocrite30
 * @Date: 2023/5/1 15:54
 */
@Repository
public interface ICourseOrder extends JpaRepository<CourseOrder, Integer>, JpaSpecificationExecutor<CourseOrder> {

    List<CourseOrder> findByStudentId(Long studentId);

}
