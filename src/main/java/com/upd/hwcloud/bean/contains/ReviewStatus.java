package com.upd.hwcloud.bean.contains;

/**
 * 审核状态
 */
public enum ReviewStatus {

    REVIEWING(0L, "审核中"),
    PRO_ONLINE(1L, "待上线"),
    ONLINE(2L, "上线"),
    REJECT(3L, "驳回"),
    DELETE(4L, "删除");

    private final Long code;
    private final String name;

    ReviewStatus(Long code, String name) {
        this.code = code;
        this.name  = name;
    }

    public Long getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String valueOf(Long status) {
        if (status == null) {
            return "";
        } else {
            for (ReviewStatus s : ReviewStatus.values()) {
                if (s.getCode().equals(status)) {
                    return s.getName();
                }
            }
            return "";
        }
    }

}
