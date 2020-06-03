package com.upd.hwcloud.bean.dto;

import com.upd.hwcloud.common.utils.annotation.ExcelExplain;

public class DaasFwTopDTO {

    @ExcelExplain(head = "服务名称")
    private String name;

    @ExcelExplain(head = "被调用数")
    private Integer value;

    @ExcelExplain(head = "服务厂商")
    private String provider;

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

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

}
