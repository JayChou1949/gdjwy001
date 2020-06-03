package com.upd.hwcloud.bean.dto;

import java.io.Serializable;

/**
 * Created by zwb on 2019/5/31.
 */
public class ChartBean implements Serializable {

    private Integer value;
    private String name;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
