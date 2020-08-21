package com.hirisun.cloud.common.enumer;

/**
 * 关系型数据库类型
 * @author yyc
 * @date 2020/4/20
 */
public enum RdbApplyType {

    ADD_DATABASE(0),ADD_ACCOUNT(1);

    private Integer code;

    RdbApplyType(Integer code){
        this.code = code;
    }


    public Integer getCode() {
        return code;
    }
}

