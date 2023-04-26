package me.noctambulist.aasweb.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.noctambulist.aasweb.entity.TutorInfo;

import java.io.Serializable;
import java.util.Objects;

/**
 * Same as TutorInfo, except for an {@link TutorInfo#getId()}
 *
 * @Author: Hypocrite30
 * @Date: 2023/4/25 22:46
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TutorInfoDTO implements Serializable {

    private static final long serialVersionUID = -9152062547773090626L;

    @JsonProperty("unique_id")
    private Long uniqueId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("nationality")
    private String nationality = "汉族";

    @JsonProperty("birthday")
    private String birthday;

    @JsonProperty("department")
    private String department;

    @JsonProperty("campus")
    private String campus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TutorInfoDTO dto = (TutorInfoDTO) o;
        return Objects.equals(uniqueId, dto.uniqueId) && Objects.equals(name, dto.name) && Objects.equals(phone, dto.phone) && Objects.equals(gender, dto.gender) && Objects.equals(nationality, dto.nationality) && Objects.equals(birthday, dto.birthday) && Objects.equals(department, dto.department) && Objects.equals(campus, dto.campus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueId, name, phone, gender, nationality, birthday, department, campus);
    }

}
