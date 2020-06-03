package com.upd.hwcloud.bean.dto;

import com.upd.hwcloud.common.utils.annotation.ExcelExplain;

public class TopDyDTO {

    @ExcelExplain(head = "名称")
    private String name;

    @ExcelExplain(head = "订阅数")
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
