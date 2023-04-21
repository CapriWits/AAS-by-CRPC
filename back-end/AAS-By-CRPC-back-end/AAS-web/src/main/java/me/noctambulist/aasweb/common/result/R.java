package me.noctambulist.aasweb.common.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Common http response definition
 *
 * @Author: Hypocrite30
 * @Date: 2023/4/19 22:55
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class R<T> {

    private Integer code;
    private String msg;
    private T data;

    public static R success() {
        return R.builder()
                .code(ResultEnum.SUCCESS.getCode())
                .msg(ResultEnum.SUCCESS.getMessage())
                .data(null).build();
    }

    public static R success(Integer code, String msg) {
        return R.builder().code(code).msg(msg).data(null).build();
    }

    public static <T> R success(Integer code, String msg, T data) {
        return R.builder().code(code).msg(msg).data(data).build();
    }

    public static <T> R success(ResultEnum e, T data) {
        return R.builder().code(e.getCode()).msg(e.getMessage()).data(data).build();
    }

    public static R failure() {
        return R.builder()
                .code(ResultEnum.UNKNOWN_ERROR.getCode())
                .msg(ResultEnum.UNKNOWN_ERROR.getMessage())
                .data(null).build();
    }

    public static R failure(Integer code, String msg) {
        return R.builder().code(code).msg(msg).data(null).build();
    }

    public static <T> R failure(Integer code, String msg, T data) {
        return R.builder().code(code).msg(msg).data(data).build();
    }

    public static <T> R failure(ResultEnum e, T data) {
        return R.builder().code(e.getCode()).msg(e.getMessage()).data(data).build();
    }

}
