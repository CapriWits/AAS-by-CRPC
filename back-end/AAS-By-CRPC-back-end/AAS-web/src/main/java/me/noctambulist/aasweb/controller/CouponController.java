package me.noctambulist.aasweb.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.noctambulist.aasweb.common.exception.CustomException;
import me.noctambulist.aasweb.common.exception.GlobalExceptionHandler;
import me.noctambulist.aasweb.common.result.R;
import me.noctambulist.aasweb.common.result.ResultEnum;
import me.noctambulist.aasweb.common.util.JsonUtils;
import me.noctambulist.aasweb.entity.Coupon;
import me.noctambulist.aasweb.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/27 15:57
 */
@RestController
@RequestMapping("/coupon")
@Slf4j
public class CouponController {

    private final CouponService couponService;

    @Autowired
    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    // =========================================================================================

    @PostMapping("/find_all_coupon")
    @ResponseBody
    public R findAllCoupon() {
        List<Coupon> coupons = couponService.findAll();
        ArrayNode arrayNode;
        try {
            arrayNode = JsonUtils.fromJson(JsonUtils.toJson(coupons), ArrayNode.class);
        } catch (IOException e) {
            log.error("Json process error.", e);
            throw new CustomException(ResultEnum.INTERNAL_SERVER_ERROR);
        }
        return R.success(JsonUtils.newObjectNode().set("coupon_info", arrayNode));
    }

    // =========================================================================================

    /**
     * Insert Coupon information.
     * </p>
     * If an insertion fails due to a unique index in MySQL, return {@link ResultEnum#CONFLICT}
     *
     * @param param {@link InsertCouponParam}
     * @return {@link R#success()}
     * @see GlobalExceptionHandler#handleHandleCustomException(DataIntegrityViolationException)
     */
    @PostMapping("/insert_coupon")
    @ResponseBody
    public R insertCoupon(@RequestBody @Validated final InsertCouponParam param) {
        Coupon coupon = Coupon.builder().studentId(param.studentId).coupon(param.coupon).build();
        couponService.create(coupon);
        return R.success();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class InsertCouponParam {
        @NotNull(message = "学生 id 不能为空")
        @JsonProperty("student_id")
        Long studentId;
        @JsonProperty("coupon")
        Double coupon;
    }

    // =========================================================================================

    @PostMapping("/delete_coupon")
    @ResponseBody
    public R deleteCoupon(@RequestBody @Validated final DeleteCouponParam param) {
        couponService.deleteByStudentId(param.studentId);
        return R.success();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class DeleteCouponParam {
        @NotNull(message = "学生 id 不能为空")
        @JsonProperty("student_id")
        Long studentId;
    }

    // =========================================================================================

    @PostMapping("/update_coupon")
    @ResponseBody
    public R updateCoupon(@RequestBody @Validated final UpdateCouponParam param) {
        Coupon coupon = Coupon.builder().studentId(param.studentId).coupon(param.coupon).build();
        couponService.update(param.studentId, coupon);
        return R.success();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class UpdateCouponParam {
        @NotNull(message = "学生 id 不能为空")
        @JsonProperty("student_id")
        Long studentId;
        @JsonProperty("coupon")
        Double coupon;
    }

}
