package me.noctambulist.aasweb.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.noctambulist.aasweb.common.exception.CustomException;
import me.noctambulist.aasweb.common.exception.GlobalExceptionHandler;
import me.noctambulist.aasweb.common.result.R;
import me.noctambulist.aasweb.common.result.ResultEnum;
import me.noctambulist.aasweb.common.util.MD5Utils;
import me.noctambulist.aasweb.service.AccountService;
import me.noctambulist.aasweb.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    /**
     * Insert account
     * <p>
     * If an insertion fails due to a unique index in MySQL, return {@link ResultEnum#CONFLICT}
     *
     * @param param {@link InsertAccountParam}
     * @return {@link R#success()}
     * @throws NoSuchAlgorithmException MD5 encode failed. {@link MD5Utils#encode(String)}
     * @see GlobalExceptionHandler#handleHandleCustomException(DataIntegrityViolationException)
     */
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

    // =========================================================================================

    @PostMapping("/delete_account")
    @ResponseBody
    public R deleteAccount(@RequestBody @Validated final DeleteAccountParam param) {
        accountService.deleteByUniqueId(param.id);
        return R.success();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class DeleteAccountParam {
        @NotNull(message = "身份 id 不能为空")
        Long id;
    }

    // =========================================================================================

    /**
     * Update account.
     * <p>
     * If account is not exists, return {@link ResultEnum#ACCOUNT_NOT_EXISTS}.
     * If MD5 encode failed, return {@link ResultEnum#INTERNAL_SERVER_ERROR}
     *
     * @param param {@link UpdateAccountParam}
     * @return {@link R#success()}
     */
    @PostMapping("/update_account")
    @ResponseBody
    public R updateAccount(@RequestBody @Validated final UpdateAccountParam param) {
        Account account;
        try {
            account = Account.builder().uniqueId(param.id).email(param.email)
                    .password(MD5Utils.encode(param.password)).build();
        } catch (NoSuchAlgorithmException e) {
            log.error("MD5 encode error", e);
            throw new CustomException(ResultEnum.INTERNAL_SERVER_ERROR);
        }
        accountService.update(param.id, account);
        return R.success();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class UpdateAccountParam {
        @NotNull(message = "身份 id 不能为空")
        Long id;
        @NotNull(message = "邮箱不能为空")
        String email;
        @NotNull(message = "密码不能为空")
        String password;
    }

}
