package me.noctambulist.aasweb.repository;

import me.noctambulist.aasweb.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/28 11:00
 */
@Repository
public interface IScore extends JpaRepository<Score, Integer>, JpaSpecificationExecutor<Score> {
}