package com.hirisun.cloud.model.third.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class DaasServiceOverview extends ServiceOverview {

    @Excel(name = "服务类别", orderNum = "20")
    private String category;

    private String label;

    private final String[] categoryArr = {"比对订阅服务", "查询检索服务", "数据建模服务", "数据推送服务"};

    public String getCategory() {
        if (label != null) {
            for (String s : categoryArr) {
                if (label.contains(s)) {
                    category = s;
                    break;
                }
            }
        }
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
