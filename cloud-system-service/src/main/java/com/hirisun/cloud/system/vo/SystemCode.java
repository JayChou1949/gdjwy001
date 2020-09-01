package com.hirisun.cloud.system.vo;

import com.hirisun.cloud.common.vo.ResultCode;

public enum SystemCode implements ResultCode {
    RESOURCE_RECOVER_NULL(5001,"请选择被回收资源负责人!"),
    APPLY_PERSON_NULL(5002,"申请人姓名为空"),
    GET_APPLY_PERSON_NULL(5003,"获取申请人信息失败"),
    FEIGN_METHOD_ERROR(5000,"远程方法调用失败")
    ;
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 状态信息
     */
    private String msg;

    SystemCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer code() {
        return this.code;
    }

    @Override
    public String msg() {
        return this.msg;
    }
}
