package me.noctambulist.aasweb.repository;

import me.noctambulist.aasweb.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/26 22:35
 */
@Repository
public interface ISemester extends JpaRepository<Semester, Integer>, JpaSpecificationExecutor<Semester> {
}
