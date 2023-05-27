package me.noctambulist.aasweb.model.mongo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.noctambulist.aasweb.model.dto.ScheduleDTO;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/29 22:28
 */
@Document("Schedule")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Schedule implements Serializable {

    private static final long serialVersionUID = -5455687511820574677L;

    @Id
    @JsonProperty("id")
    @JsonSerialize(using = ToStringSerializer.class)  // Serialize using org.bson.types.ObjectId#toString
    private ObjectId id;

    @Field("course_id")
    @JsonProperty("course_id")
    private String courseId;

    @Field("course_name")
    @JsonProperty("course_name")
    private String courseName;

    @Field("course_number")
    @JsonProperty("course_number")
    private String courseNumber;

    @Field("credit")
    @JsonProperty("credit")
    private String credit;

    @Field("course_attributes")
    @JsonProperty("course_attributes")
    private String courseAttributes;

    @Field("exam_type")
    @JsonProperty("exam_type")
    private String examType;

    @Field("tutor")
    @JsonProperty("tutor")
    private String tutor;

    @Field("department")
    @JsonProperty("department")
    private String department;

    @Field("weekly")
    @JsonProperty("weekly")
    private String weekly;

    @Field("week")
    @JsonProperty("week")
    private String week;

    @Field("section_id")
    @JsonProperty("section_id")
    private String sectionId;

    @Field("section_num")
    @JsonProperty("section_num")
    private String sectionNum;

    @Field("campus")
    @JsonProperty("campus")
    private String campus;

    @Field("building")
    @JsonProperty("building")
    private String building;

    @Field("class_room")
    @JsonProperty("class_room")
    private String classRoom;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(id, schedule.id) && Objects.equals(courseId, schedule.courseId) &&
                Objects.equals(courseName, schedule.courseName) && Objects.equals(courseNumber, schedule.courseNumber) &&
                Objects.equals(credit, schedule.credit) && Objects.equals(courseAttributes, schedule.courseAttributes) &&
                Objects.equals(examType, schedule.examType) && Objects.equals(tutor, schedule.tutor) &&
                Objects.equals(department, schedule.department) && Objects.equals(weekly, schedule.weekly) &&
                Objects.equals(week, schedule.week) && Objects.equals(sectionId, schedule.sectionId) &&
                Objects.equals(sectionNum, schedule.sectionNum) && Objects.equals(campus, schedule.campus) &&
                Objects.equals(building, schedule.building) && Objects.equals(classRoom, schedule.classRoom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseId, courseName, courseNumber, credit, courseAttributes, examType, tutor,
                department, weekly, week, sectionId, sectionNum, campus, building, classRoom);
    }

    public static ScheduleDTO entityToDTO(Schedule schedule) {
        ScheduleDTO dto = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, dto);
        return dto;
    }

}
