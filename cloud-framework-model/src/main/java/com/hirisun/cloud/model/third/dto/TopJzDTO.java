package com.hirisun.cloud.model.third.dto;

import com.hirisun.cloud.common.util.annotation.ExcelExplain;

public class TopJzDTO {

    @ExcelExplain(head = "警种名称")
    private String name;

    @ExcelExplain(head = "支撑应用数")
    private Integer value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
