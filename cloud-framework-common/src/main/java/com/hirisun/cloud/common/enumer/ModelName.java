package com.hirisun.cloud.common.enumer;

/**
 * Created by zwb on 2019/4/10.
 */
public enum ModelName {

    APPLY("申请"),
    DEP_APPROVE("本单位审批"),
    RECHECK("服务台复核"),
    APPROVE("大数据办审批"),
    CARRY("业务办理"),
    FEEDBACK("反馈"),

    RECOVERED("被回收资源负责人审批"),
//    APPLY("申请"),
    LVL2_MANAGER("二级管理员审批"),
//    RECHECK("服务台复核"),
    LVL1_MANAGER("一级管理员审批"),
    BIG_DATA("大数据办审批"),
    ONLINE("服务上线");
//    CARRY("业务办理")


    private String name;
    ModelName(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }
}
