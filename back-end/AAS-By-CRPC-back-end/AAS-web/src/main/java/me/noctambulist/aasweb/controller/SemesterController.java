package me.noctambulist.aasweb.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.noctambulist.aasweb.common.exception.GlobalExceptionHandler;
import me.noctambulist.aasweb.common.result.R;
import me.noctambulist.aasweb.common.result.ResultEnum;
import me.noctambulist.aasweb.common.util.JsonUtils;
import me.noctambulist.aasweb.entity.Semester;
import me.noctambulist.aasweb.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/26 22:42
 */
@RestController
@RequestMapping("/semester")
@Slf4j
public class SemesterController {

    private final SemesterService semesterService;

    @Autowired
    public SemesterController(SemesterService semesterService) {
        this.semesterService = semesterService;
    }


    // =========================================================================================

    /**
     * Insert Semester information.
     * </p>
     * If an insertion fails due to a unique index in MySQL, return {@link ResultEnum#CONFLICT}
     *
     * @param param {@link InsertSemesterParam}
     * @return {@link R#success()}
     * @see GlobalExceptionHandler#handleHandleCustomException(DataIntegrityViolationException)
     */
    @PostMapping("/insert_semester")
    @ResponseBody
    public R insertSemester(@RequestBody @Validated final InsertSemesterParam param) {
        Semester semester = Semester.builder().semester(param.semester).build();
        semesterService.create(semester);
        return R.success();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class InsertSemesterParam {
        @NotNull(message = "学期信息不能为空")
        String semester;
    }

    // =========================================================================================

    @PostMapping("/delete_semester")
    @ResponseBody
    public R deleteSemester(@RequestBody @Validated final DeleteSemesterParam param) {
        semesterService.deleteById(param.id);
        return R.success();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class DeleteSemesterParam {
        @NotNull(message = "学期 id 不能为空")
        Integer id;
    }

    // =========================================================================================

    @PostMapping("/find_all_semester")
    @ResponseBody
    public R findAllSemester() throws IOException {
        List<Semester> semesters = semesterService.findAll();
        ArrayNode arrayNode = JsonUtils.fromJson(JsonUtils.toJson(semesters), ArrayNode.class);
        return R.success(JsonUtils.newObjectNode().set("semester_info", arrayNode));
    }

}
