package com.upd.hwcloud.bean.dto;

import com.upd.hwcloud.common.utils.annotation.ExcelExplain;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
