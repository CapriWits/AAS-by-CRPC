package me.noctambulist.aasweb.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.noctambulist.aasweb.common.result.R;
import me.noctambulist.aasweb.common.util.JsonUtils;
import me.noctambulist.aasweb.model.CourseOrder;
import me.noctambulist.aasweb.model.dto.CourseOrderDTO;
import me.noctambulist.aasweb.service.CourseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Hypocrite30
 * @Date: 2023/5/1 16:06
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class CourseOrderController {

    private final CourseOrderService courseOrderService;

    @Autowired
    public CourseOrderController(CourseOrderService courseOrderService) {
        this.courseOrderService = courseOrderService;
    }

    // =========================================================================================

    @PostMapping("/find_all")
    @ResponseBody
    public R findAll() {
        List<CourseOrderDTO> orders = courseOrderService.findAll().stream().map(CourseOrder::entityToDTO)
                .collect(Collectors.toList());
        return R.success(JsonUtils.newObjectNode().set("orders", JsonUtils.objectToJsonNode(orders)));
    }

    // =========================================================================================

    @PostMapping("/insert_order")
    @ResponseBody
    public R insertOrder(@RequestBody @Validated final InsertOrderParam param) {
        CourseOrder courseOrder = CourseOrder.builder().studentId(param.studentId).totalCredit(param.totalCredit)
                .classesJson(param.classesJson).build();
        courseOrderService.create(courseOrder);
        return R.success();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class InsertOrderParam {
        @JsonProperty("student_id")
        @NotNull(message = "学生 id 不能为空")
        Long studentId;
        @JsonProperty("total_credit")
        Double totalCredit;
        @JsonProperty("classes")
        String classesJson;
    }

    // =========================================================================================

    @PostMapping("/find_order_by_studentId")
    @ResponseBody
    public R findOrderByStudentId(@RequestBody @Validated final FindOrderByStudentIdParam param) {
        List<CourseOrderDTO> orders = courseOrderService.findByStudentId(param.studentId).stream()
                .map(CourseOrder::entityToDTO).collect(Collectors.toList());
        return R.success(JsonUtils.newObjectNode().set("orders", JsonUtils.objectToJsonNode(orders)));
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class FindOrderByStudentIdParam {
        @JsonProperty("student_id")
        @NotNull(message = "学生 id 不能为空")
        Long studentId;
    }

}
