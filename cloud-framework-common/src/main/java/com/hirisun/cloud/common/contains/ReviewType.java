package com.hirisun.cloud.common.contains;

/**
 * 审核状态
 */
public enum ReviewType {

    AUDIT("1", "审核"),
    IMPL("2", "上线");

    private final String code;
    private final String name;

    ReviewType(String code, String name) {
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
            for (ReviewType s : ReviewType.values()) {
                if (s.getCode().equals(status)) {
                    return s.getName();
                }
            }
            return "";
        }
    }

}
