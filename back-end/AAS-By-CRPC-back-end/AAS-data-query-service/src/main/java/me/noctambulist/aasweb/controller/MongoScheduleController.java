package me.noctambulist.aasweb.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.noctambulist.aasweb.common.result.R;
import me.noctambulist.aasweb.common.util.JsonUtils;
import me.noctambulist.aasweb.model.mongo.Schedule;
import me.noctambulist.aasweb.service.MongoScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/29 23:01
 */
@RestController
@RequestMapping("/mongo/schedule")
@Slf4j
@CrossOrigin
public class MongoScheduleController {

    private final MongoScheduleService mongoScheduleService;

    @Autowired
    public MongoScheduleController(MongoScheduleService mongoScheduleService) {
        this.mongoScheduleService = mongoScheduleService;
    }

    // =========================================================================================

    @PostMapping("/find_all")
    @ResponseBody
    public R findAll() {
        List<Schedule> schedules = mongoScheduleService.findAll();
        return R.success(JsonUtils.newObjectNode().set("schedule_info", JsonUtils.objectToJsonNode(schedules)));
    }

    // =========================================================================================

    @PostMapping("/insert_schedule")
    @ResponseBody
    public R insertSchedule(@RequestBody @Validated final InsertScheduleParam param) {
        Schedule schedule = Schedule.builder().courseId(param.courseId).courseName(param.courseName)
                .courseNumber(param.courseNumber).credit(param.credit).courseAttributes(param.courseAttributes)
                .examType(param.examType).tutor(param.tutor).department(param.department).weekly(param.weekly)
                .week(param.week).sectionId(param.sectionId).sectionNum(param.sectionNum).campus(param.campus)
                .building(param.building).classRoom(param.classRoom).build();
        Schedule savedSchedule = mongoScheduleService.create(schedule);
        return R.success(JsonUtils.newObjectNode().set("schedule_info", JsonUtils.objectToJsonNode(savedSchedule)));
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class InsertScheduleParam {
        @JsonProperty("course_id")
        @NotNull(message = "课程 id 不能为空")
        String courseId;

        @NotNull(message = "课程名称不能为空")
        @JsonProperty("course_name")
        String courseName;

        @JsonProperty("course_number")
        @NotNull(message = "课序号不能为空")
        String courseNumber;

        @JsonProperty("credit")
        @NotNull(message = "学分不能为空")
        String credit;

        @JsonProperty("course_attributes")
        @NotNull(message = "课程属性不能为空")
        String courseAttributes;

        @JsonProperty("exam_type")
        @NotNull(message = "考试类型不能为空")
        String examType;

        @JsonProperty("tutor")
        @NotNull(message = "导师不能为空")
        String tutor;

        @JsonProperty("department")
        @NotNull(message = "可选该课院系不能为空")
        List<String> department;

        @JsonProperty("weekly")
        @NotNull(message = "周次不能为空")
        String weekly;

        @JsonProperty("week")
        @NotNull(message = "星期不能为空")
        String week;

        @JsonProperty("section_id")
        @NotNull(message = "节次不能为空")
        String sectionId;

        @JsonProperty("section_num")
        @NotNull(message = "节数不能为空")
        String sectionNum;

        @JsonProperty("campus")
        @NotNull(message = "校区不能为空")
        String campus;

        @JsonProperty("building")
        @NotNull(message = "教学楼不能为空")
        String building;

        @JsonProperty("class_room")
        @NotNull(message = "教室不能为空")
        String classRoom;
    }

    // =========================================================================================

    @PostMapping("/delete_schedule")
    @ResponseBody
    public R deleteSchedule(@RequestBody @Validated final DeleteScheduleParam param) {
        mongoScheduleService.deleteById(param.id);
        return R.success();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class DeleteScheduleParam {
        @NotNull(message = "课程信息表 id 不能为空")
        String id;
    }

    // =========================================================================================

    @PostMapping("/find_schedule_by_department")
    @ResponseBody
    public R findScheduleFilterDepartment(@RequestBody @Validated final FindScheduleWithFilterParam param) {
        List<Schedule> schedules = mongoScheduleService.findScheduleByDepartment(param.department);
        return R.success(JsonUtils.newObjectNode().set("schedule_info", JsonUtils.objectToJsonNode(schedules)));
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class FindScheduleWithFilterParam {
        @NotNull(message = "学生院系不能为空")
        String department;
    }

    // =========================================================================================

    @PostMapping("/find_schedule_by_courseId_courseNum")
    @ResponseBody
    public R findScheduleFilterCourseIdNum(@RequestBody @Validated final FindScheduleFilterCourseIdNumParam param) {
        List<Schedule> schedules = mongoScheduleService.findByCourseIdAndCourseNumber(param.courseId, param.courseNum);
        return R.success(JsonUtils.newObjectNode().set("schedule_info", JsonUtils.objectToJsonNode(schedules)));
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class FindScheduleFilterCourseIdNumParam {
        @JsonProperty("course_id")
        @NotNull(message = "课程号不能为空")
        String courseId;
        @JsonProperty("course_number")
        @NotNull(message = "课序号不能为空")
        String courseNum;
    }

}
