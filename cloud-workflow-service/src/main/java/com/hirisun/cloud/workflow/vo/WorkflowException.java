package com.hirisun.cloud.workflow.vo;

import com.hirisun.cloud.common.vo.ResultCode;

public class WorkflowException implements ResultCode {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 状态信息
     */
    private String msg;

    @Override
    public Integer code() {
        return null;
    }

    @Override
    public String msg() {
        return null;
    }

    public WorkflowException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
