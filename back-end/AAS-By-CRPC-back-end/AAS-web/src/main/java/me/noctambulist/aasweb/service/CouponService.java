package me.noctambulist.aasweb.service;

import me.noctambulist.aasweb.common.exception.CustomException;
import me.noctambulist.aasweb.common.result.ResultEnum;
import me.noctambulist.aasweb.repository.ICoupon;
import me.noctambulist.aasweb.model.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/27 15:44
 */
@Service
public class CouponService {

    private final ICoupon iCoupon;

    @Autowired
    public CouponService(ICoupon iCoupon) {
        this.iCoupon = iCoupon;
    }

    @Transactional
    public Coupon create(Coupon coupon) {
        return iCoupon.saveAndFlush(coupon);
    }

    @Transactional
    public Coupon update(Long studentId, Coupon coupon) {
        Optional<Coupon> optionalCoupon = iCoupon.findByStudentId(studentId);
        optionalCoupon.orElseThrow(() -> new CustomException(ResultEnum.NOT_FOUND));
        coupon.setId(optionalCoupon.get().getId());
        iCoupon.saveAndFlush(coupon);
        return coupon;
    }

    public void deleteByStudentId(Long studentId) {
        iCoupon.deleteByStudentId(studentId);
    }

    public List<Coupon> findAll() {
        return iCoupon.findAll();
    }

}
