package com.hirisun.cloud.common.contains;

public enum ResourceType {

    IAAS(1),DAAS(2),PAAS(3),SAAS(4),SAAS_SERVICE(5);

    private final Integer code;

    ResourceType(Integer l) {
        this.code = l;
    }

    public Integer getCode() {
        return code;
    }

}
