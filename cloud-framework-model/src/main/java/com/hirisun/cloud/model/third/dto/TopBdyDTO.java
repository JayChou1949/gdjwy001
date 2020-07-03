package com.hirisun.cloud.model.third.dto;

import com.hirisun.cloud.util.annotation.ExcelExplain;

public class TopBdyDTO {

    @ExcelExplain(head = "名称")
    private String name;

    @ExcelExplain(head = "被订阅数")
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
