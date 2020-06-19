package com.hirisun.cloud.common.exception;

import com.hirisun.cloud.common.vo.ResultCode;

public class CustomException extends RuntimeException {
    ResultCode resultCode;



    public CustomException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
