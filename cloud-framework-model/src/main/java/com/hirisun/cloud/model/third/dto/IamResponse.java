package com.hirisun.cloud.model.third.dto;

public class IamResponse {


    /**
     * statusCode : 200
     * message : 创建用户成功!
     * success : true
     */

    private String statusCode;
    private String message;
    private boolean success;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
