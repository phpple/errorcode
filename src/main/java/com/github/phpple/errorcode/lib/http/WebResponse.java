package com.github.phpple.errorcode.lib.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class WebResponse<T> {
    public static final int SUCCESS = 200;
    public static final int ERROR = 500;
    public static final String SUCCESS_MESSAGE = "ok";
    public static final String ERROR_MESSAGE = "error";

    // ServiceErrorCode
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer rawCode;

    // WebErrorCode
    private int code;

    // 消息
    private String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> args;

    // 返回有效数据
    private T data;

    public static <T> WebResponse success(T data) {
        WebResponse<T> response = new WebResponse<>();
        response.setData(data);
        response.setCode(SUCCESS);
        response.setMessage(SUCCESS_MESSAGE);
        return response;
    }

    public static <T> WebResponse error(int code, String message) {
        WebResponse response = new WebResponse();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }
}
