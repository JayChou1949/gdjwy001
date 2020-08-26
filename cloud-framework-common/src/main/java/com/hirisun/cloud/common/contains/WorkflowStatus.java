package com.hirisun.cloud.common.contains;

public enum WorkflowStatus {
    NORMAL(0, "正常"),
    DELETE(1, "删除");


    private final Integer code;
    private final String name;

    WorkflowStatus(Integer code, String name) {
        this.code = code;
        this.name  = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String valueOf(Long status) {
        if (status == null) {
            return "";
        } else {
            for (WorkflowNodeAbilityType s : WorkflowNodeAbilityType.values()) {
                if (s.getCode().equals(status)) {
                    return s.getName();
                }
            }
            return "";
        }
    }
}
