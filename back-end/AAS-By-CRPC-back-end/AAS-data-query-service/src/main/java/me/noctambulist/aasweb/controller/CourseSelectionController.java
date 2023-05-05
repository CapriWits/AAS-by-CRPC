package me.noctambulist.aasweb.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.noctambulist.aasweb.common.result.R;
import me.noctambulist.aasweb.service.CourseSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: Hypocrite30
 * @Date: 2023/5/3 12:51
 */
@RestController
@RequestMapping("/course")
@Slf4j
@CrossOrigin
public class CourseSelectionController {

    private final CourseSelectionService courseSelectionService;

    @Autowired
    public CourseSelectionController(CourseSelectionService courseSelectionService) {
        this.courseSelectionService = courseSelectionService;
    }

    // =========================================================================================

    @PostMapping("/choose_course")
    @ResponseBody
    public R chooseCourse(@RequestBody @Validated final ChooseCourseParam param) throws JsonProcessingException {
        return courseSelectionService.chooseCourse(param.studentId, param.semesterId, param.classes);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class ChooseCourseParam {
        @NotNull(message = "学生 id 不能为空")
        @JsonProperty("student_id")
        Long studentId;

        @NotNull(message = "学期 id 不能为空")
        @JsonProperty("semester_id")
        Integer semesterId;

        @NotNull(message = "待选课程不能为空")
        @Valid
        List<Classes> classes;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Classes {
        @NotNull(message = "课程号不能为空")
        @JsonProperty("course_id")
        String courseId;
        @NotNull(message = "课序号不能为空")
        @JsonProperty("course_number")
        String courseNum;
        @NotNull(message = "学分不能为空")
        Double credit;
    }

    // =========================================================================================

    @PostMapping("/drop_course")
    @ResponseBody
    public R chooseCourse(@RequestBody @Validated final DropCourseParam param) {
        return courseSelectionService.dropCourse(param.studentId, param.semesterId, param.courseId, param.courseNum,
                param.credit);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class DropCourseParam {
        @NotNull(message = "学生 id 不能为空")
        @JsonProperty("student_id")
        Long studentId;
        @NotNull(message = "学期 id 不能为空")
        @JsonProperty("semester_id")
        Integer semesterId;
        @NotNull(message = "课程号不能为空")
        @JsonProperty("course_id")
        String courseId;
        @NotNull(message = "课序号不能为空")
        @JsonProperty("course_number")
        String courseNum;
        @NotNull(message = "学分不能为空")
        Double credit;
    }
}
