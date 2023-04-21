package me.noctambulist.aasweb.common.result;

import lombok.Getter;

/**
 * @Author: Hypocrite30
 * @Date: 2023/4/19 23:02
 */
@Getter
public enum ResultEnum {
    SUCCESS(200, "成功"),
    BAD_REQUEST(400, "请求参数有误"),
    UNAUTHORIZED(401, "未经授权访问"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "请求资源不存在"),
    METHOD_NOT_ALLOWED(405, "不支持该请求方法"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),
    UNKNOWN_ERROR(-1, "未知错误");

    private final int code;
    private final String message;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
