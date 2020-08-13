package com.hirisun.cloud.common.contains;

public enum UserType {

    NORMAL(0L, "普通用户"),
    COMPANY(1L, "服务厂商"),
    TENANT_MANAGER(20L, "租户管理员"),
    PROVINCIAL_MANAGER(30L,"省厅管理员"),
    MANAGER(100L, "管理用户");

    private final Long code;
    private final String desc;

    UserType(Long code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Long getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
