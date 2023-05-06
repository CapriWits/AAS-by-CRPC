package me.noctambulist.aasweb.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.noctambulist.aasweb.common.result.R;
import me.noctambulist.aasweb.common.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

import static me.noctambulist.aasweb.common.constant.RedisConstants.CLASS_SELECTION_CONTROLLER;
import static me.noctambulist.aasweb.common.constant.RedisConstants.SKU_PREFIX;

/**
 * @Author: Hypocrite30
 * @Date: 2023/5/1 11:55
 */
@RestController
@RequestMapping("/course_select")
@Slf4j
@CrossOrigin
public class CourseSelectionController {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public CourseSelectionController(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // =========================================================================================

    @PostMapping("/set_course_selection_list")
    @ResponseBody
    public R SettCourseSelectionList(@RequestBody @Validated final SetCourseSelectionListParam param) {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        if (ObjectUtils.isEmpty(param.departments)) {
            param.departments.add("");
        }
        // delete + push = overwrite add
        listOperations.getOperations().delete(CLASS_SELECTION_CONTROLLER);
        listOperations.rightPushAll(CLASS_SELECTION_CONTROLLER, param.departments);
        return R.success();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class SetCourseSelectionListParam {
        @NotNull(message = "可选课院系列表不能为空")
        List<String> departments;
    }

    // =========================================================================================

    @PostMapping("/get_course_selection_list")
    @ResponseBody
    public R GetCourseSelectionList() throws IOException {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        Long len = listOperations.size(CLASS_SELECTION_CONTROLLER);
        List<String> departments = listOperations.range(CLASS_SELECTION_CONTROLLER, 0, len - 1);
        return R.success(JsonUtils.newObjectNode().
                set("departments", JsonUtils.fromJson(JsonUtils.toJson(departments), ArrayNode.class)));
    }

    @PostMapping("/set_course_sku")
    @ResponseBody
    public R setCourseSku(@RequestBody @Validated final SetCourseSkuParam param) {
        redisTemplate.opsForValue().set(String.format(SKU_PREFIX, param.courseId, param.courseNum), param.sku);
        return R.success();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class SetCourseSkuParam {
        @NotNull(message = "课程号不能为空")
        @JsonProperty("course_id")
        String courseId;
        @NotNull(message = "课序号不能为空")
        @JsonProperty("course_num")
        String courseNum;
        @NotNull(message = "课余量不能为空")
        String sku;
    }
}
