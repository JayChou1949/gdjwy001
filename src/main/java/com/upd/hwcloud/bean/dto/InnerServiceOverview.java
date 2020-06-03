package com.upd.hwcloud.bean.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class InnerServiceOverview {

    @Excel(name = "服务名称")
    private String name;

    @Excel(name = "所属类别")
    private String category;

    private String subType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

}
