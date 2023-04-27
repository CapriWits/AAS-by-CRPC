package me.noctambulist.aasweb.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.noctambulist.aasweb.entity.dto.CouponDTO;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/27 15:30
 */
@Entity
@Table(name = "coupon",
        indexes = {@Index(columnList = "student_id", name = "idx_student_id", unique = true)})
@DynamicInsert
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "coupon")
    private Double coupon = 0d;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id",
            referencedColumnName = "unique_id", insertable = false, updatable = false)
    private StudentInfo studentInfo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coupon coupon1 = (Coupon) o;
        return Objects.equals(id, coupon1.id) && Objects.equals(studentId, coupon1.studentId) && Objects.equals(coupon, coupon1.coupon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, studentId, coupon);
    }

    public static CouponDTO entity2DTO(Coupon coupon) {
        CouponDTO dto = new CouponDTO();
        BeanUtils.copyProperties(coupon, dto);
        return dto;
    }

}
