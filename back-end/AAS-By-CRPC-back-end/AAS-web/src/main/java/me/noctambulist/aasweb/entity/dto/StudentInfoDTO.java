package me.noctambulist.aasweb.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.noctambulist.aasweb.entity.StudentInfo;

import java.io.Serializable;
import java.util.Objects;

/**
 * Same as Student, except for an {@link StudentInfo#getId()}
 *
 * @Author: Hypocrite30
 * @Date: 2023/4/23 22:46
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentInfoDTO implements Serializable {

    private static final long serialVersionUID = -1825408324160410992L;

    @JsonProperty("unique_id")
    private Long uniqueId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("id_card_num")
    private String idCardNum;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("nationality")
    private String nationality = "汉族";

    @JsonProperty("birthday")
    private String birthday;

    @JsonProperty("exam_from")
    private String examFrom;

    @JsonProperty("gaokao_score")
    private Double gaokaoScore;

    @JsonProperty("graduated_school")
    private String graduatedSchool;

    @JsonProperty("department")
    private String department;

    @JsonProperty("major")
    private String major;

    @JsonProperty("campus")
    private String campus;

    @JsonProperty("class")
    private String clazz;

    @JsonProperty("trainning_level")
    private String trainningLevel = "本科";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentInfoDTO that = (StudentInfoDTO) o;
        return Objects.equals(uniqueId, that.uniqueId) && Objects.equals(name, that.name) && Objects.equals(idCardNum, that.idCardNum) && Objects.equals(phone, that.phone) && Objects.equals(gender, that.gender) && Objects.equals(nationality, that.nationality) && Objects.equals(birthday, that.birthday) && Objects.equals(examFrom, that.examFrom) && Objects.equals(gaokaoScore, that.gaokaoScore) && Objects.equals(graduatedSchool, that.graduatedSchool) && Objects.equals(department, that.department) && Objects.equals(major, that.major) && Objects.equals(campus, that.campus) && Objects.equals(clazz, that.clazz) && Objects.equals(trainningLevel, that.trainningLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueId, name, idCardNum, phone, gender, nationality, birthday, examFrom, gaokaoScore, graduatedSchool, department, major, campus, clazz, trainningLevel);
    }

}
