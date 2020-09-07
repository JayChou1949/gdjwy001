package com.hirisun.cloud.common.contains;

/**
 * 审核状态
 */
public enum WorkflowNodeAbilityType {

    REVIEW("1", "审核"),
    ADD("2", "加办"),
    IMPL("3", "实施"),
    REJECT("4", "驳回"),
    DELETE("5", "删除"),
    FALLBACK("6", "反馈"),
    FORWARD("7", "转发"),
    FALL("8", "回退"),
    APPLY("9", "申请");

    private final String code;
    private final String name;

    WorkflowNodeAbilityType(String code, String name) {
        this.code = code;
        this.name  = name;
    }

    public String getCode() {
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
