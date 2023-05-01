package me.noctambulist.aasweb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.noctambulist.aasweb.model.dto.CourseOrderDTO;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * @Author: Hypocrite30
 * @Date: 2023/5/1 15:21
 */
@Entity
@Table(name = "course_order")
@DynamicInsert
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseOrder extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 420906321601379446L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "total_credit")
    private Double totalCredit;

    @Column(name = "classes", columnDefinition = "json")
    private String classesJson;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CourseOrder that = (CourseOrder) o;
        return Objects.equals(id, that.id) && Objects.equals(studentId, that.studentId)
                && Objects.equals(totalCredit, that.totalCredit) && Objects.equals(classesJson, that.classesJson);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, studentId, totalCredit, classesJson);
    }

    public static CourseOrderDTO entityToDTO(CourseOrder courseOrder) {
        CourseOrderDTO dto = new CourseOrderDTO();
        BeanUtils.copyProperties(courseOrder, dto);
        return dto;
    }

}
