package me.noctambulist.aasweb.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.noctambulist.aasweb.common.exception.GlobalExceptionHandler;
import me.noctambulist.aasweb.common.result.R;
import me.noctambulist.aasweb.common.result.ResultEnum;
import me.noctambulist.aasweb.common.util.MD5Utils;
import me.noctambulist.aasweb.model.Account;
import me.noctambulist.aasweb.model.Role;
import me.noctambulist.aasweb.service.AccountService;
import me.noctambulist.aasweb.service.RoleService;
import me.noctambulist.aasweb.service.StudentInfoService;
import me.noctambulist.aasweb.model.StudentInfo;
import me.noctambulist.aasweb.model.dto.StudentInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/23 12:17
 */
@RestController
@RequestMapping("/student")
@Slf4j
@CrossOrigin
public class StudentManagementController {

    private final StudentInfoService studentInfoService;
    private final AccountService accountService;
    private final RoleService roleService;

    @Autowired
    public StudentManagementController(StudentInfoService studentInfoService, AccountService accountService,
                                       RoleService roleService) {
        this.studentInfoService = studentInfoService;
        this.accountService = accountService;
        this.roleService = roleService;
    }

    // =========================================================================================

    @PostMapping("/get_student_info_with_filter")
    @ResponseBody
    public R getStudentInfoList(@RequestBody @Validated final GetStudentInfoListParam param) {
        List<StudentInfoDTO> studentInfoDTOs = studentInfoService.getStudentInfoWithFilter(param);
        if (ObjectUtils.isEmpty(studentInfoDTOs)) {
            return R.failure(ResultEnum.STUDENT_NOT_EXISTS);
        }
        GetStudentInfoListResponse response = new GetStudentInfoListResponse();
        response.studentInfos = studentInfoDTOs.toArray(new StudentInfoDTO[0]);
        return R.success(ResultEnum.SUCCESS, response);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class GetStudentInfoListParam {
        @NotNull(message = "filter 不能为空")
        GetStudentInfoFilter filter;

        @JsonIgnoreProperties(ignoreUnknown = true)
        @Data
        public class GetStudentInfoFilter {
            Long id;
            String name;
            String department;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class GetStudentInfoListResponse {
        @JsonProperty("student_info")
        StudentInfoDTO[] studentInfos;
    }

    // =========================================================================================

    /**
     * Insert Student information.
     * </p>
     * If an insertion fails due to a unique index in MySQL, return {@link ResultEnum#CONFLICT}
     *
     * @param param {@link InsertStudentInfoParam}
     * @return {@link R#success()}
     * @see GlobalExceptionHandler#handleHandleCustomException(DataIntegrityViolationException)
     */
    @PostMapping("/insert_student_info")
    @ResponseBody
    public R insertStudentInfo(@RequestBody @Validated final InsertStudentInfoParam param) throws NoSuchAlgorithmException {
        StudentInfo studentInfo = StudentInfo.builder().uniqueId(param.getUniqueId()).coupon(param.getCoupon())
                .name(param.getName()).idCardNum(param.getIdCardNum()).phone(param.getPhone()).gender(param.getGender())
                .nationality(param.getNationality()).birthday(param.getBirthday()).examFrom(param.getExamFrom())
                .gaokaoScore(param.getGaokaoScore()).graduatedSchool(param.getGraduatedSchool())
                .department(param.getDepartment()).major(param.getMajor()).campus(param.getCampus())
                .clazz(param.getClazz()).trainningLevel(param.getTrainningLevel()).build();
        studentInfoService.create(studentInfo);
        Account account = Account.builder().uniqueId(param.uniqueId).email(param.email)
                .password(MD5Utils.encode(param.password)).build();
        accountService.create(account);
        Role role = Role.builder().uniqueId(param.uniqueId).role((byte) 0).build();
        roleService.create(role);
        return R.success();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class InsertStudentInfoParam {
        @JsonProperty("unique_id")
        Long uniqueId;
        Double coupon = 0d;
        String name;
        @JsonProperty("id_card_num")
        String idCardNum;
        String phone;
        String gender;
        String nationality = "汉族";
        String birthday;
        @JsonProperty("exam_from")
        String examFrom;
        @JsonProperty("gaokao_score")
        Double gaokaoScore;
        @JsonProperty("graduated_school")
        String graduatedSchool;
        String department;
        String major;
        String campus;
        @JsonProperty("class")
        String clazz;
        @JsonProperty("trainning_level")
        String trainningLevel = "本科";
        String email;
        String password;
    }

    // =========================================================================================

    @PostMapping("/delete_student_info")
    @ResponseBody
    public R deleteStudentInfo(@RequestBody @Validated final DeleteStudentInfoParam param) {
        studentInfoService.deleteByUniqueId(param.id);
        roleService.deleteByUniqueId(param.id);
        accountService.deleteByUniqueId(param.id);
        return R.success();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class DeleteStudentInfoParam {
        @NotNull(message = "身份 id 不能为空")
        Long id;
    }

    // =========================================================================================

    /**
     * Update Student information
     * <p>
     * If StudentInfo is not exists, return {@link ResultEnum#STUDENT_NOT_EXISTS}
     *
     * @param param {@link UpdateStudentInfoParam}
     * @return {@link R#success()}
     */
    @PostMapping("/update_student_info")
    @ResponseBody
    public R updateAccount(@RequestBody @Validated final UpdateStudentInfoParam param) throws NoSuchAlgorithmException {
        StudentInfo studentInfo = StudentInfo.builder().uniqueId(param.getUniqueId()).coupon(param.getCoupon())
                .name(param.getName()).idCardNum(param.getIdCardNum()).phone(param.getPhone()).gender(param.getGender())
                .nationality(param.getNationality()).birthday(param.getBirthday()).examFrom(param.getExamFrom())
                .gaokaoScore(param.getGaokaoScore()).graduatedSchool(param.getGraduatedSchool())
                .department(param.getDepartment()).major(param.getMajor()).campus(param.getCampus())
                .clazz(param.getClazz()).trainningLevel(param.getTrainningLevel()).build();
        studentInfoService.update(param.uniqueId, studentInfo);
        Account account = Account.builder().uniqueId(param.uniqueId).email(param.email)
                .password(MD5Utils.encode(param.password)).build();
        accountService.update(param.uniqueId, account);
        return R.success();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class UpdateStudentInfoParam {
        @JsonProperty("unique_id")
        Long uniqueId;
        Double coupon = 0d;
        String name;
        @JsonProperty("id_card_num")
        String idCardNum;
        String phone;
        String gender;
        String nationality = "汉族";
        String birthday;
        @JsonProperty("exam_from")
        String examFrom;
        @JsonProperty("gaokao_score")
        Double gaokaoScore;
        @JsonProperty("graduated_school")
        String graduatedSchool;
        String department;
        String major;
        String campus;
        @JsonProperty("class")
        String clazz;
        @JsonProperty("trainning_level")
        String trainningLevel = "本科";
        String email;
        String password;
    }

}
