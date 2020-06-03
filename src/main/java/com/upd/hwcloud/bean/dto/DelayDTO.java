package com.upd.hwcloud.bean.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DelayDTO {

    private String currentTime;

    private Double latency;

    private Double innerLatency;

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
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

}
