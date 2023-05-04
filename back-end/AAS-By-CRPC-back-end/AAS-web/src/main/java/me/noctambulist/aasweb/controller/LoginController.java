package me.noctambulist.aasweb.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.noctambulist.aasweb.common.exception.CustomException;
import me.noctambulist.aasweb.common.result.R;
import me.noctambulist.aasweb.common.result.ResultEnum;
import me.noctambulist.aasweb.common.util.JsonUtils;
import me.noctambulist.aasweb.common.util.MD5Utils;
import me.noctambulist.aasweb.model.Account;
import me.noctambulist.aasweb.model.Semester;
import me.noctambulist.aasweb.model.StudentInfo;
import me.noctambulist.aasweb.model.TutorInfo;
import me.noctambulist.aasweb.service.AccountService;
import me.noctambulist.aasweb.service.RoleService;
import me.noctambulist.aasweb.service.SemesterService;
import me.noctambulist.aasweb.service.StudentInfoService;
import me.noctambulist.aasweb.service.TutorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static me.noctambulist.aasweb.common.constant.RedisConstants.CLASS_SELECTION_CONTROLLER;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/18 16:55
 */
@RestController
@Slf4j
@CrossOrigin
public class LoginController {

    private final AccountService accountService;
    private final RoleService roleService;
    private final SemesterService semesterService;
    private final StudentInfoService studentInfoService;
    private final TutorInfoService tutorInfoService;
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public LoginController(AccountService accountService, RoleService roleService, SemesterService semesterService,
                           StudentInfoService studentInfoService, TutorInfoService tutorInfoService,
                           RedisTemplate<String, String> redisTemplate) {
        this.accountService = accountService;
        this.roleService = roleService;
        this.semesterService = semesterService;
        this.studentInfoService = studentInfoService;
        this.tutorInfoService = tutorInfoService;
        this.redisTemplate = redisTemplate;
    }

    // =========================================================================================

    @PostMapping("/login")
    @ResponseBody
    public R login(@RequestBody @Validated final LoginParam param) {
        Account account = accountService.findByUniqueId(param.id);
        if (ObjectUtils.isEmpty(account)) {
            return R.failure(ResultEnum.ACCOUNT_NOT_EXISTS);
        }
        String encodedPassword;
        try {
            encodedPassword = MD5Utils.encode(param.password);
        } catch (NoSuchAlgorithmException e) {
            log.error("MD5 encode error. Param password: [{}], database md5: [{}]",
                    param.password, account.getPassword(), e);
            throw new CustomException(ResultEnum.INTERNAL_SERVER_ERROR);
        }
        if (!ObjectUtils.nullSafeEquals(account.getPassword(), encodedPassword)) {
            return R.failure(ResultEnum.PASSWORD_ERROR);
        }
        // password is true
        ObjectNode response = JsonUtils.newObjectNode();
        Byte role = roleService.findByUniqueId(param.id).getRole();
        response.put("role", role);
        JsonNode currentSemester;
        try {
            Semester semester = JsonUtils.fromJson(semesterService.getCurrentSemester(), Semester.class);
            currentSemester = JsonUtils.objectToJsonNode(semester);
        } catch (IOException e) {
            log.error("Json process error", e);
            throw new CustomException(ResultEnum.INTERNAL_SERVER_ERROR);
        }
        response.set("current_semester", currentSemester);
        // student
        if (ObjectUtils.nullSafeEquals(role, ((byte) 0))) {
            StudentInfo studentInfo = studentInfoService.getStudentInfoByUniqueId(param.id);
            response.set("student_info", JsonUtils.objectToJsonNode(StudentInfo.entityToDTO(studentInfo)));
            // get course selection department list
            ListOperations<String, String> listOperations = redisTemplate.opsForList();
            Long len = listOperations.size(CLASS_SELECTION_CONTROLLER);
            List<String> departments = listOperations.range(CLASS_SELECTION_CONTROLLER, 0, len - 1);
            response.put("can_select_class", !ObjectUtils.isEmpty(departments)
                    && !ObjectUtils.isEmpty(departments.get(0))
                    && departments.contains(studentInfo.getDepartment()));
        } else if (ObjectUtils.nullSafeEquals(role, ((byte) 1))) {
            // tutor
            TutorInfo tutorInfo = tutorInfoService.getTutorInfoByUniqueId(param.id);
            response.set("tutor_info", JsonUtils.objectToJsonNode(TutorInfo.entity2DTO(tutorInfo)));
        }
        return R.success(ResultEnum.SUCCESS, response);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class LoginParam {
        @NotNull(message = "账号名不能为空")
        Long id;
        @NotNull(message = "密码不能为空")
        String password;
    }

}
