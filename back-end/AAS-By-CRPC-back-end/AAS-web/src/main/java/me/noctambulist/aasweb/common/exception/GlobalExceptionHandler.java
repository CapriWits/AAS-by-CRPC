package me.noctambulist.aasweb.common.exception;

import lombok.extern.slf4j.Slf4j;
import me.noctambulist.aasweb.common.result.R;
import me.noctambulist.aasweb.common.result.ResultEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/20 19:51
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public R handleCustomException(CustomException ex) {
        log.error("Custom Exception msg: [{}], details: [{}]", ex.getMessage(), ex.getDetails(), ex);
        return R.failure(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R handleException(Exception ex) {
        log.error("Exception msg: [{}]", ex.getMessage(), ex);
        return R.failure(ResultEnum.UNKNOWN_ERROR, null);
    }

}
