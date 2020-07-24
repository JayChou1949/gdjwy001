package com.hirisun.cloud.common.exception;

import com.hirisun.cloud.common.vo.ResultCode;

/**
 * 自定义异常类
 */
public class CustomException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3414825343844388205L;
	/**
     * 响应码
     */
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
