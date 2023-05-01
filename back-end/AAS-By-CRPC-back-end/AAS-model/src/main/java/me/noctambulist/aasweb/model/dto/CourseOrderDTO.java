package me.noctambulist.aasweb.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.noctambulist.aasweb.model.CourseOrder;

import java.io.Serializable;
import java.util.Objects;

/**
 * Same as {@link CourseOrder}, except for an {@link CourseOrder#getId()}
 *
 * @Author: Hypocrite30
 * @Date: 2023/5/1 15:48
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseOrderDTO implements Serializable {

    private static final long serialVersionUID = 8974957899857086844L;

    @JsonProperty("student_id")
    private Long studentId;

    @JsonProperty("total_credit")
    private Double totalCredit;

    @JsonProperty("classes")
    private String classesJson;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseOrderDTO dto = (CourseOrderDTO) o;
        return Objects.equals(studentId, dto.studentId) && Objects.equals(totalCredit, dto.totalCredit)
                && Objects.equals(classesJson, dto.classesJson);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, totalCredit, classesJson);
    }

}
