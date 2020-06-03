package com.upd.hwcloud.common.exception;


/**
 * 基础异常
 * Version: 1.0
 */
public class BaseException extends RuntimeException {

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误消息
     */
    private String defaultMessage;


    public BaseException(Integer code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }
    public BaseException(String defaultMessage,Integer code) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }
    public BaseException(String defaultMessage) {
        this(500, defaultMessage);
    }

    public Integer getCode() {
        return code;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    @Override
    public String getMessage() {
        return getDefaultMessage();
    }

    @Override
    public String toString() {
        return this.getClass() + "{" +
                "code='" + code + '\'' +
                ", message='" + defaultMessage + '\'' +
                '}';
    }
}
