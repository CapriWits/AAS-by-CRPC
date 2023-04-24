package me.noctambulist.aasweb.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.noctambulist.aasweb.common.result.R;
import me.noctambulist.aasweb.common.util.MD5Utils;
import me.noctambulist.aasweb.entity.Account;
import me.noctambulist.aasweb.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/24 22:35
 */
@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // =========================================================================================

    @PostMapping("/insert_account")
    @ResponseBody
    public R insertAccount(@RequestBody @Validated final InsertAccountParam param) throws NoSuchAlgorithmException {
        Account account = Account.builder().uniqueId(param.id).email(param.email)
                .password(MD5Utils.encode(param.password)).build();
        accountService.create(account);
        return R.success();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class InsertAccountParam {
        @NotNull(message = "身份 id 不能为空")
        Long id;
        @NotNull(message = "邮箱不能为空")
        String email;
        @NotNull(message = "密码不能为空")
        String password;
    }
}
