package com.upd.hwcloud.bean.contains;

/**
 * 统一用户类型
 */
public enum UnifledUserType {

    /**
     * 民警
     */
    MJ("10"),
    /**
     * 辅警
     */
    FJ("20"),
    /**
     * 施工人员
     */
    WB("30");

    private final String code;

    UnifledUserType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
