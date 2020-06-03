package com.upd.hwcloud.bean.dto;

import com.upd.hwcloud.bean.entity.application.DaasApplication;

public class DaasApplicationExt extends DaasApplication {


    private String appName;

    private String orderNumber;

    private String creatorName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }


}
