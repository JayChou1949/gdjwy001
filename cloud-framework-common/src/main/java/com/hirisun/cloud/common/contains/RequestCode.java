package com.hirisun.cloud.common.contains;

public enum RequestCode {

    SUCCESS("0", "成功"),
    COMPLETE("1", "完成"),
    FAIL("2", "异常");

    private final String code;
    private final String desc;

    RequestCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
