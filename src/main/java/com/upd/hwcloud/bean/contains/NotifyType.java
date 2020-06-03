package com.upd.hwcloud.bean.contains;

public enum NotifyType {

    SMS("0", "短信"),
    EMAIL("1", "邮箱"),
    WX("2", "微信");

    private final String code;
    private final String desc;

    NotifyType(String code, String desc) {
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
