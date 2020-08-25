package com.hirisun.cloud.common.contains;

public enum WorkflowInstanceStatus {
    WORKING(0, "办理中"),
    COMPLETE(1, "已完成"),
    TERMINATE(2, "终止");


    private final Integer code;
    private final String name;

    WorkflowInstanceStatus(Integer code, String name) {
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
