package com.upd.hwcloud.bean.contains;

public enum ResourceType {

    IAAS(1L),DAAS(2L),PAAS(3L),SAAS(4L),SAAS_SERVICE(5L);

    private final Long code;

    ResourceType(Long l) {
        this.code = l;
    }

    public Long getCode() {
        return code;
    }

}
