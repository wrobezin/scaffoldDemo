package io.github.wrobezin.framework.example.validator.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yuan
 * date: 2019/12/16
 */
@Data
@AllArgsConstructor
public class RequestResult {
    private Boolean successful;
    private String message;
    private Object data;

    private static final String DEFAULT_SUCCESS_MESSAGE = "操作成功";

    public static final RequestResult SUCCESS = new RequestResult(true, DEFAULT_SUCCESS_MESSAGE, null);

    public static RequestResult succeed(String message, Object data) {
        return new RequestResult(true, message, data);
    }

    public static RequestResult succeed(String message) {
        return new RequestResult(true, message, null);
    }

    public static RequestResult succeedWithData(Object data) {
        return new RequestResult(true, DEFAULT_SUCCESS_MESSAGE, data);
    }

    public static RequestResult fail(String message, Object data) {
        return new RequestResult(false, message, data);
    }

    public static RequestResult fail(String message) {
        return new RequestResult(false, message, null);
    }
}
