package com.upd.hwcloud.bean.dto;

public class ReqDTO {

    private String currentTime;

    private Integer reqCount;

    private Integer abnormalReqCount;

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
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

}
