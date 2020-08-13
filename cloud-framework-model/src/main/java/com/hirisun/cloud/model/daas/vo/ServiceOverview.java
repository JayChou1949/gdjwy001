package com.hirisun.cloud.model.daas.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ServiceOverview {

    @Excel(name = "服务名称")
    private String name;
    @Excel(name = "厂商")
    private String provider;
    @Excel(name = "调用总数")
    private Long reqCount;
    @Excel(name = "异常调用")
    private Long abnormalReqCount;
    @Excel(name = "时延")
    private Double avgLatency;
    @Excel(name = "异常率")
    private Double abnormalRate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Long getReqCount() {
        return reqCount;
    }

    public void setReqCount(Long reqCount) {
        this.reqCount = reqCount;
    }

    public Long getAbnormalReqCount() {
        return abnormalReqCount;
    }

    public void setAbnormalReqCount(Long abnormalReqCount) {
        this.abnormalReqCount = abnormalReqCount;
    }

    public Double getAvgLatency() {
        if (avgLatency == null) {
            return null;
        }
        BigDecimal bigDecimal = new BigDecimal(avgLatency).setScale(2, RoundingMode.UP);
        return bigDecimal.doubleValue();
    }

    public void setAvgLatency(Double avgLatency) {
        this.avgLatency = avgLatency;
    }

    public Double getAbnormalRate() {
        if (reqCount == null || reqCount == 0 || abnormalReqCount == 0) {
            return 0d;
        }
        double rate = abnormalReqCount / Double.valueOf(reqCount) * 100;
        abnormalRate = new BigDecimal(rate).setScale(2, RoundingMode.UP).doubleValue();
        return abnormalRate;
    }

    public void setAbnormalRate(Double abnormalRate) {
        this.abnormalRate = abnormalRate;
    }

}
