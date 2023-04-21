package me.noctambulist.aasweb.common.exception;

import lombok.Getter;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/20 12:10
 */
@Getter
public abstract class BaseException extends RuntimeException {

    private final int code;

    private final Object details;

    public BaseException(int code, String message) {
        this(code, message, null);
    }

    public BaseException(int code, String message, Object details) {
        super(message);
        this.code = code;
        this.details = details;
    }

}
