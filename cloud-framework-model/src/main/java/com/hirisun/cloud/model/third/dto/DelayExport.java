package com.hirisun.cloud.model.third.dto;


import java.math.BigDecimal;
import java.math.RoundingMode;

import com.hirisun.cloud.util.annotation.ExcelExplain;

public class DelayExport {

    @ExcelExplain(head = "服务名称")
    private String name;

    @ExcelExplain(head = "时延")
    private Double delay;

    @ExcelExplain(head = "总数")
    private Integer total;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDelay() {
        if (delay == null) {
            return null;
        }
        BigDecimal bigDecimal = new BigDecimal(delay).setScale(2, RoundingMode.UP);
        return bigDecimal.doubleValue();
    }

    public void setDelay(Double delay) {
        this.delay = delay;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
