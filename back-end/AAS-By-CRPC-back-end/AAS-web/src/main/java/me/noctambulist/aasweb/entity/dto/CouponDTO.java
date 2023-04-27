package me.noctambulist.aasweb.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.noctambulist.aasweb.entity.Coupon;

import java.io.Serializable;
import java.util.Objects;

/**
 * Same as Coupon, except for an {@link Coupon#getId()}
 *
 * @Author: Hypocrite30
 * @Date: 2023/4/27 15:41
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponDTO implements Serializable {

    private static final long serialVersionUID = -3158035633407124559L;

    @JsonProperty("student_id")
    private Long studentId;

    @JsonProperty("coupon")
    private Double coupon = 0d;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CouponDTO couponDTO = (CouponDTO) o;
        return Objects.equals(studentId, couponDTO.studentId) && Objects.equals(coupon, couponDTO.coupon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, coupon);
    }

}
