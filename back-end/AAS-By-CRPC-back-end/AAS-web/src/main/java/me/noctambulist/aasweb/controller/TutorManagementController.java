package me.noctambulist.aasweb.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.noctambulist.aasweb.common.exception.GlobalExceptionHandler;
import me.noctambulist.aasweb.common.result.R;
import me.noctambulist.aasweb.common.result.ResultEnum;
import me.noctambulist.aasweb.entity.TutorInfo;
import me.noctambulist.aasweb.entity.dto.TutorInfoDTO;
import me.noctambulist.aasweb.service.TutorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/25 23:07
 */
@RestController
@RequestMapping("/tutor")
@Slf4j
public class TutorManagementController {

    private final TutorInfoService tutorInfoService;

    @Autowired
    public TutorManagementController(TutorInfoService tutorInfoService) {
        this.tutorInfoService = tutorInfoService;
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
    public R insertTutorInfo(@RequestBody @Validated final StudentManagementController.InsertStudentInfoParam param) {
        TutorInfo tutorInfo = TutorInfo.builder().uniqueId(param.uniqueId).name(param.name).phone(param.phone).gender(param.gender)
                .nationality(param.nationality).birthday(param.birthday).department(param.department)
                .campus(param.campus).build();
        tutorInfoService.create(tutorInfo);
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
    }

    // =========================================================================================

    @PostMapping("/delete_tutor_info")
    @ResponseBody
    public R deleteTutorInfo(@RequestBody @Validated final DeleteTutorInfoParam param) {
        tutorInfoService.deleteByUniqueId(param.id);
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
    public R updateAccount(@RequestBody @Validated final UpdateTutorInfoParam param) {
        TutorInfo tutorInfo = TutorInfo.builder().uniqueId(param.uniqueId).name(param.name).phone(param.phone)
                .gender(param.gender).nationality(param.nationality).birthday(param.birthday)
                .department(param.department).campus(param.campus).build();
        tutorInfoService.update(param.uniqueId, tutorInfo);
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
    }
}
