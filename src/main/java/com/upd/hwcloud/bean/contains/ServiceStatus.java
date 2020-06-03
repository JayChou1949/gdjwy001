package com.upd.hwcloud.bean.contains;

/**
 * paas、daas服务状态枚举
 */
public enum ServiceStatus {

    NOT_SHOW_HOME("不首页展示","0"),
    CAN_SHOW_HOME("首页展示","1"),
    CANNOT_APPLY("不可申请","0"),
    CAN_APPLY("可申请","1");

    private String desc;

    private String code;

    ServiceStatus(String desc,String code){
        this.desc = desc;
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
