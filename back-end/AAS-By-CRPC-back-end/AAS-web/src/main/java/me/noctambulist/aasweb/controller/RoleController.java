package me.noctambulist.aasweb.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.noctambulist.aasweb.common.exception.GlobalExceptionHandler;
import me.noctambulist.aasweb.common.result.R;
import me.noctambulist.aasweb.common.result.ResultEnum;
import me.noctambulist.aasweb.service.RoleService;
import me.noctambulist.aasweb.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/24 20:59
 */
@RestController
@RequestMapping("/role")
@Slf4j
@CrossOrigin
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // =========================================================================================

    /**
     * Insert role information.
     * <p>
     * If an insertion fails due to a unique index in MySQL, return {@link ResultEnum#CONFLICT}
     *
     * @param param {@link InsertRoleParam}
     * @return {@link R#success()}
     * @see GlobalExceptionHandler#handleHandleCustomException(DataIntegrityViolationException)
     */
    @PostMapping("/insert_role")
    @ResponseBody
    public R insertRole(@RequestBody @Validated final InsertRoleParam param) {
        Role role = Role.builder().uniqueId(param.id).role(param.role).build();
        roleService.create(role);
        return R.success();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class InsertRoleParam {
        @NotNull(message = "身份 id 不能为空")
        Long id;
        @NotNull(message = "角色不能为空")
        Byte role;
    }

    // =========================================================================================

    @PostMapping("/delete_role")
    @ResponseBody
    public R deleteRole(@RequestBody @Validated final DeleteRoleParam param) {
        roleService.deleteByUniqueId(param.id);
        return R.success();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class DeleteRoleParam {
        @NotNull(message = "身份 id 不能为空")
        Long id;
    }

}
