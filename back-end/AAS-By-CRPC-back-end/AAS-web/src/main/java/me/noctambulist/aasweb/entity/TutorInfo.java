package me.noctambulist.aasweb.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.noctambulist.aasweb.entity.dto.TutorInfoDTO;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/25 22:42
 */
@Entity
@Table(name = "tutor_info", indexes = {
        @Index(name = "idx_unique_id", columnList = "unique_id", unique = true),
        @Index(name = "idx_name", columnList = "name")
})
@DynamicInsert
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TutorInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -1555683910238315571L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "unique_id", nullable = false, unique = true)
    private Long uniqueId;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "gender")
    private String gender;

    @Column(name = "nationality")
    private String nationality = "汉族";

    @Column(name = "birthday")
    private String birthday;

    @Column(name = "department")
    private String department;

    @Column(name = "campus")
    private String campus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TutorInfo tutorInfo = (TutorInfo) o;
        return Objects.equals(id, tutorInfo.id) && Objects.equals(uniqueId, tutorInfo.uniqueId) && Objects.equals(name, tutorInfo.name) && Objects.equals(phone, tutorInfo.phone) && Objects.equals(gender, tutorInfo.gender) && Objects.equals(nationality, tutorInfo.nationality) && Objects.equals(birthday, tutorInfo.birthday) && Objects.equals(department, tutorInfo.department) && Objects.equals(campus, tutorInfo.campus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, uniqueId, name, phone, gender, nationality, birthday, department, campus);
    }

    public static TutorInfoDTO entity2DTO(TutorInfo tutorInfo) {
        TutorInfoDTO dto = new TutorInfoDTO();
        BeanUtils.copyProperties(tutorInfo, dto);
        return dto;
    }

}
