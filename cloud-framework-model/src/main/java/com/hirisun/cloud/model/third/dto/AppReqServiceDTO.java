package com.hirisun.cloud.model.third.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AppReqServiceDTO {

    private String appName;

    private String serviceName;

    private Integer reqCount;

    private Integer abnormalReqCount;

    private Double latency;

    private Double innerLatency;

    private Double abnormalRate;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getReqCount() {
        return reqCount;
    }

    public void setReqCount(Integer reqCount) {
        this.reqCount = reqCount;
    }

    public Integer getAbnormalReqCount() {
        return abnormalReqCount;
    }

    public void setAbnormalReqCount(Integer abnormalReqCount) {
        this.abnormalReqCount = abnormalReqCount;
    }

    public Double getLatency() {
        if (latency == null) {
            return null;
        }
        BigDecimal bigDecimal = new BigDecimal(latency).setScale(2, RoundingMode.UP);
        return bigDecimal.doubleValue();
    }

    public void setLatency(Double latency) {
        this.latency = latency;
    }

    public Double getInnerLatency() {
        if (innerLatency == null) {
            return null;
        }
        BigDecimal bigDecimal = new BigDecimal(innerLatency).setScale(2, RoundingMode.UP);
        return bigDecimal.doubleValue();
    }

    public void setInnerLatency(Double innerLatency) {
        this.innerLatency = innerLatency;
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
