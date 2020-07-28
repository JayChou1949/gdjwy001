package com.hirisun.cloud.common.vo;

/**
 * @author zhoufeng
 * @version 1.0
 * @className BusinessCode
 * @data 2020/7/28 11:12
 * @description 业务枚举
 */
public enum BusinessCode {

    NCOV_ACCESS("NCOV_ACCESS","疫情数据接入"),
    NCOV_SERVIE("NCOV_SERVIE","疫情数据服务"),
    ;
    /**
     * 状态码
     */
    private String key;
    /**
     * 状态信息
     */
    private String tag;

    BusinessCode(String key, String tag) {
        this.key = key;
        this.tag = tag;
    }

    public String key() {
        return this.key;
    }

    public String tag() {
        return this.tag;
    }
}
