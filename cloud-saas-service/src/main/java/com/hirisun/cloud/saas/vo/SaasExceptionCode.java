package com.hirisun.cloud.saas.vo;

import com.hirisun.cloud.common.vo.ResultCode;

public enum SaasExceptionCode implements ResultCode {
    APPLY_NULL(3001,"未找到申请信息!"),
    IMPORT_DATA_ERROR(3002,"导入数据异常!"),
    RECORD_NULL_ERROR(3003,"该记录不存在!"),
    DELETE_ERROR(3004,"只能删除自己的申请!"),
    FALLBACK_ID_NOT_NULL(2508,"回退环节ID不能为空,回退失败!")
    ;
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 状态信息
     */
    private String msg;

    SaasExceptionCode(Integer code, String msg) {
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
