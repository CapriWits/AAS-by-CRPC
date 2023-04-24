package me.noctambulist.aasweb.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.noctambulist.aasweb.entity.dto.StudentInfoDTO;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Objects;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/23 11:49
 */
@Entity
@Table(name = "student_info",
        indexes = {@Index(name = "uniqueId_idCardNum", columnList = "unique_id,id_card_num", unique = true)}
)
@DynamicInsert
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "unique_id", nullable = false)
    private Long uniqueId;

    @Column(name = "name")
    private String name;

    @Column(name = "id_card_num")
    private String idCardNum;

    @Column(name = "phone")
    private String phone;

    @Column(name = "gender")
    private String gender;

    @Column(name = "nationality")
    private String nationality = "汉族";

    @Column(name = "birthday")
    private String birthday;

    @Column(name = "exam_from")
    private String examFrom;

    @Column(name = "gaokao_score")
    private Double gaokaoScore;

    @Column(name = "graduated_school")
    private String graduatedSchool;

    @Column(name = "department")
    private String department;

    @Column(name = "major")
    private String major;

    @Column(name = "campus")
    private String campus;

    @Column(name = "class")
    private String clazz;

    @Column(name = "trainning_level")
    private String trainningLevel = "本科";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentInfo that = (StudentInfo) o;
        return Objects.equals(id, that.id) && Objects.equals(uniqueId, that.uniqueId) &&
                Objects.equals(name, that.name) && Objects.equals(idCardNum, that.idCardNum) &&
                Objects.equals(phone, that.phone) && Objects.equals(gender, that.gender) &&
                Objects.equals(nationality, that.nationality) && Objects.equals(birthday, that.birthday) &&
                Objects.equals(examFrom, that.examFrom) && Objects.equals(gaokaoScore, that.gaokaoScore) &&
                Objects.equals(graduatedSchool, that.graduatedSchool) && Objects.equals(department, that.department) &&
                Objects.equals(major, that.major) && Objects.equals(campus, that.campus) &&
                Objects.equals(clazz, that.clazz) && Objects.equals(trainningLevel, that.trainningLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uniqueId, name, idCardNum, phone, gender, nationality, birthday, examFrom, gaokaoScore,
                graduatedSchool, department, major, campus, clazz, trainningLevel);
    }

    public static StudentInfoDTO entityToDTO(StudentInfo studentInfo) {
        StudentInfoDTO dto = new StudentInfoDTO();
        BeanUtils.copyProperties(studentInfo, dto);
        return dto;
    }
}
