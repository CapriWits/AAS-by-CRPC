package me.noctambulist.aasweb.common.exception;

import me.noctambulist.aasweb.common.result.ResultEnum;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/20 12:18
 */
public class CustomException extends BaseException {

    public CustomException(int code, String message) {
        super(code, message);
    }

    public CustomException(int code, String message, Object details) {
        super(code, message, details);
    }

    public CustomException(ResultEnum e) {
        super(e.getCode(), e.getMessage());
    }

    public CustomException(ResultEnum e, Object details) {
        super(e.getCode(), e.getMessage(), details);
    }
}
