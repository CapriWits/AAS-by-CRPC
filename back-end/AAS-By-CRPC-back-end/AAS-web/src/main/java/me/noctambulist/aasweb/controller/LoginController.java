package me.noctambulist.aasweb.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.noctambulist.aasweb.common.exception.CustomException;
import me.noctambulist.aasweb.common.result.R;
import me.noctambulist.aasweb.common.result.ResultEnum;
import me.noctambulist.aasweb.common.util.JsonUtils;
import me.noctambulist.aasweb.common.util.MD5Utils;
import me.noctambulist.aasweb.entity.Account;
import me.noctambulist.aasweb.entity.Role;
import me.noctambulist.aasweb.service.AccountService;
import me.noctambulist.aasweb.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/18 16:55
 */
@RestController
@Slf4j
public class LoginController {

    private final AccountService accountService;
    private final RoleService roleService;

    @Autowired
    public LoginController(AccountService accountService, RoleService roleService) {
        this.accountService = accountService;
        this.roleService = roleService;
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
        Role role = roleService.findByUniqueId(param.id);
        return R.success(ResultEnum.SUCCESS, JsonUtils.newObjectNode().put("role", role.getRole()));
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
