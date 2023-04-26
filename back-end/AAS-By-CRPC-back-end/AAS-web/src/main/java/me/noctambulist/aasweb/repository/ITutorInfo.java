package me.noctambulist.aasweb.repository;

import me.noctambulist.aasweb.entity.TutorInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/25 22:55
 */
@Repository
public interface ITutorInfo extends JpaRepository<TutorInfo, Integer>, JpaSpecificationExecutor<TutorInfo> {

    Optional<TutorInfo> findByUniqueId(Long uniqueId);

    @Modifying
    @Transactional
    void deleteByUniqueId(Long id);
}

