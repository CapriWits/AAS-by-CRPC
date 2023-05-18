package me.noctambulist.aasweb.repository;

import me.noctambulist.aasweb.model.ClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @Author: Hypocrite30
 * @Date: 2023/5/1 23:08
 */
public interface IClassSchedule extends JpaRepository<ClassSchedule, Integer>, JpaSpecificationExecutor<ClassSchedule> {

    @Modifying
    @Query("UPDATE ClassSchedule c SET c.status='已退课' " +
            "WHERE c.studentId=?1 AND c.semesterId=?2 AND c.courseId=?3 AND c.courseNum=?4")
    int dropCourse(Long studentId, Integer semesterId, String courseId, String courseNum);

    @Query("SELECT cs.courseId, cs.courseNum FROM ClassSchedule cs WHERE cs.tutorId = :tutorId GROUP BY cs.courseId, cs.courseNum")
    List<Object[]> findDistinctCourseIdAndCourseNumByTutorId(@Param("tutorId") Long tutorId);

    Optional<ClassSchedule> findFirstByCourseIdAndCourseNum(String courseId, String courseNum);

}
