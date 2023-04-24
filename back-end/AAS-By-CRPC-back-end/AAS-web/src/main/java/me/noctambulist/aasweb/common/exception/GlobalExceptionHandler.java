package me.noctambulist.aasweb.common.exception;

import lombok.extern.slf4j.Slf4j;
import me.noctambulist.aasweb.common.result.R;
import me.noctambulist.aasweb.common.result.ResultEnum;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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

    /**
     * To handle {@link org.springframework.web.bind.MethodArgumentNotValidException} thrown by
     * {@link org.springframework.validation.annotation.Validated} or {@link javax.validation.Valid} annotations.
     *
     * @param ex {@link org.springframework.web.bind.MethodArgumentNotValidException}
     * @return {@link ResultEnum#BAD_REQUEST}
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public R handleHandleCustomException(MethodArgumentNotValidException ex) {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        allErrors.forEach(error -> {
            log.error("Method argument not valid. Details: [{}]", error.getDefaultMessage());
        });
        return R.failure(ResultEnum.BAD_REQUEST);
    }

    /**
     * Processing {@link org.springframework.dao.DataIntegrityViolationException},
     * which violates the unique index thrown when inserting data from JPA
     *
     * @param ex {@link org.springframework.dao.DataIntegrityViolationException}
     * @return {@link ResultEnum#BAD_REQUEST}
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public R handleHandleCustomException(DataIntegrityViolationException ex) {
        log.error("Data violates integrity constraints in the database (e.g., uniqueness, foreign keys, etc.)",
                ex.getMessage());
        return R.failure(ResultEnum.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R handleException(Exception ex) {
        log.error("Exception msg: [{}]", ex.getMessage(), ex);
        return R.failure(ResultEnum.UNKNOWN_ERROR);
    }

}
