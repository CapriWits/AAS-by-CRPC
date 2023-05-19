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
import me.noctambulist.aasweb.service.TutorInfoService;
import me.noctambulist.aasweb.model.TutorInfo;
import me.noctambulist.aasweb.model.dto.TutorInfoDTO;
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
 * @Date: 2023/4/25 23:07
 */
@RestController
@RequestMapping("/tutor")
@Slf4j
@CrossOrigin
public class TutorManagementController {

    private final TutorInfoService tutorInfoService;
    private final AccountService accountService;
    private final RoleService roleService;

    @Autowired
    public TutorManagementController(TutorInfoService tutorInfoService, AccountService accountService,
                                     RoleService roleService) {
        this.tutorInfoService = tutorInfoService;
        this.accountService = accountService;
        this.roleService = roleService;
    }


    // =========================================================================================

    @PostMapping("/get_tutor_info_with_filter")
    @ResponseBody
    public R getTutorInfoList(@RequestBody @Validated final GetTutorInfoListParam param) {
        List<TutorInfoDTO> tutorInfoDTOS = tutorInfoService.getTutorInfoWithFilter(param);
        if (ObjectUtils.isEmpty(tutorInfoDTOS)) {
            return R.failure(ResultEnum.TUTOR_NOT_EXISTS);
        }
        GetStudentInfoListResponse response = new GetStudentInfoListResponse();
        response.tutorInfos = tutorInfoDTOS.toArray(new TutorInfoDTO[0]);
        return R.success(ResultEnum.SUCCESS, response);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class GetTutorInfoListParam {
        @NotNull(message = "filter 不能为空")
        GetTutorInfoFilter filter;

        @JsonIgnoreProperties(ignoreUnknown = true)
        @Data
        public class GetTutorInfoFilter {
            Long id;
            String name;
            String department;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class GetStudentInfoListResponse {
        @JsonProperty("tutor_info")
        TutorInfoDTO[] tutorInfos;
    }

    // =========================================================================================

    /**
     * Insert Tutor information.
     * </p>
     * If an insertion fails due to a unique index in MySQL, return {@link ResultEnum#CONFLICT}
     *
     * @param param {@link InsertTutorInfoParam}
     * @return {@link R#success()}
     * @see GlobalExceptionHandler#handleHandleCustomException(DataIntegrityViolationException)
     */
    @PostMapping("/insert_tutor_info")
    @ResponseBody
    public R insertTutorInfo(@RequestBody @Validated final StudentManagementController.InsertStudentInfoParam param)
            throws NoSuchAlgorithmException {
        TutorInfo tutorInfo = TutorInfo.builder().uniqueId(param.uniqueId).name(param.name).phone(param.phone)
                .gender(param.gender).nationality(param.nationality).birthday(param.birthday)
                .department(param.department).campus(param.campus).build();
        tutorInfoService.create(tutorInfo);
        Account account = Account.builder().uniqueId(param.uniqueId).email(param.email)
                .password(MD5Utils.encode(param.password)).build();
        accountService.create(account);
        Role role = Role.builder().uniqueId(param.uniqueId).role((byte) 1).build();
        roleService.create(role);
        return R.success();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class InsertTutorInfoParam {
        @JsonProperty("unique_id")
        Long uniqueId;
        String name;
        String phone;
        String gender;
        String nationality = "汉族";
        String birthday;
        String department;
        String campus;
        String email;
        String password;
    }

    // =========================================================================================

    @PostMapping("/delete_tutor_info")
    @ResponseBody
    public R deleteTutorInfo(@RequestBody @Validated final DeleteTutorInfoParam param) {
        tutorInfoService.deleteByUniqueId(param.id);
        accountService.deleteByUniqueId(param.id);
        roleService.deleteByUniqueId(param.id);
        return R.success();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class DeleteTutorInfoParam {
        @NotNull(message = "身份 id 不能为空")
        Long id;
    }

    // =========================================================================================

    @PostMapping("/update_tutor_info")
    @ResponseBody
    public R updateAccount(@RequestBody @Validated final UpdateTutorInfoParam param) throws NoSuchAlgorithmException {
        TutorInfo tutorInfo = TutorInfo.builder().uniqueId(param.uniqueId).name(param.name).phone(param.phone)
                .gender(param.gender).nationality(param.nationality).birthday(param.birthday)
                .department(param.department).campus(param.campus).build();
        tutorInfoService.update(param.uniqueId, tutorInfo);
        Account account = Account.builder().uniqueId(param.uniqueId).email(param.email)
                .password(MD5Utils.encode(param.password)).build();
        accountService.update(param.uniqueId, account);
        return R.success();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class UpdateTutorInfoParam {
        @JsonProperty("unique_id")
        Long uniqueId;
        String name;
        String phone;
        String gender;
        String nationality = "汉族";
        String birthday;
        String department;
        String campus;
        String email;
        String password;
    }
}
