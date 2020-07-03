package com.hirisun.cloud.model.third.dto;


import java.math.BigDecimal;
import java.math.RoundingMode;

import com.hirisun.cloud.util.annotation.ExcelExplain;

public class AbnoramlExport {

    @ExcelExplain(head = "服务名称")
    private String name;

    @ExcelExplain(head = "异常次数")
    private Integer abnormal;

    @ExcelExplain(head = "异常率")
    private Double abnormalRate;

    @ExcelExplain(head = "总数")
    private Integer total;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getAbnormal() {
        return abnormal;
    }

    public void setAbnormal(Integer abnormal) {
        this.abnormal = abnormal;
    }

    public Double getAbnormalRate() {
        if (abnormalRate == null) {
            return null;
        }
        BigDecimal bigDecimal = new BigDecimal(abnormalRate).setScale(2, RoundingMode.UP);
        return bigDecimal.doubleValue();
    }

    public void setAbnormalRate(Double abnormalRate) {
        this.abnormalRate = abnormalRate;
    }

}
