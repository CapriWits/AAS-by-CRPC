package me.noctambulist.aasweb.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.noctambulist.aasweb.model.mongo.Schedule;

import java.util.List;
import java.util.Objects;

/**
 * Same as Schedule, except for an {@link Schedule#getId()}
 *
 * @Author: Hypocrite30
 * @Date: 2023/4/30 11:25
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleDTO {

    @JsonProperty("course_id")
    private String courseId;

    @JsonProperty("course_name")
    private String courseName;

    @JsonProperty("course_number")
    private String courseNumber;

    @JsonProperty("credit")
    private String credit;

    @JsonProperty("course_attributes")
    private String courseAttributes;

    @JsonProperty("exam_type")
    private String examType;

    @JsonProperty("tutor")
    private String tutor;

    @JsonProperty("department")
    private List<String> department;

    @JsonProperty("weekly")
    private String weekly;

    @JsonProperty("week")
    private String week;

    @JsonProperty("section_id")
    private String sectionId;

    @JsonProperty("section_num")
    private String sectionNum;

    @JsonProperty("campus")
    private String campus;

    @JsonProperty("building")
    private String building;

    @JsonProperty("class_room")
    private String classRoom;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleDTO that = (ScheduleDTO) o;
        return Objects.equals(courseId, that.courseId) && Objects.equals(courseName, that.courseName) &&
                Objects.equals(courseNumber, that.courseNumber) && Objects.equals(credit, that.credit) &&
                Objects.equals(courseAttributes, that.courseAttributes) && Objects.equals(examType, that.examType) &&
                Objects.equals(tutor, that.tutor) && Objects.equals(department, that.department) &&
                Objects.equals(weekly, that.weekly) && Objects.equals(week, that.week) &&
                Objects.equals(sectionId, that.sectionId) && Objects.equals(sectionNum, that.sectionNum) &&
                Objects.equals(campus, that.campus) && Objects.equals(building, that.building) &&
                Objects.equals(classRoom, that.classRoom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, courseName, courseNumber, credit, courseAttributes, examType, tutor, department,
                weekly, week, sectionId, sectionNum, campus, building, classRoom);
    }

}
