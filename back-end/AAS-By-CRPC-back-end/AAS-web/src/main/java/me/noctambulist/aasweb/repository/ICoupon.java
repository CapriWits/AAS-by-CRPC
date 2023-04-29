package me.noctambulist.aasweb.repository;

import me.noctambulist.aasweb.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/27 15:36
 */
@Repository
public interface ICoupon extends JpaRepository<Coupon, Long>, JpaSpecificationExecutor<Coupon> {

    @Modifying
    @Transactional
    void deleteByStudentId(Long studentId);

    Optional<Coupon> findByStudentId(Long uniqueId);

}
