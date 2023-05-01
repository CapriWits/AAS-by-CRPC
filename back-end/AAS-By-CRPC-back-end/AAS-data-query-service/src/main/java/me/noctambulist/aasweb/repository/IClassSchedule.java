package me.noctambulist.aasweb.repository;

import me.noctambulist.aasweb.model.ClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author: Hypocrite30
 * @Date: 2023/5/1 23:08
 */
public interface IClassSchedule extends JpaRepository<ClassSchedule, Integer>, JpaSpecificationExecutor<ClassSchedule> {

}
