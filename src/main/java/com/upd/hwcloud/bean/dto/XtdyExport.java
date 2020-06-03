package com.upd.hwcloud.bean.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.util.Date;

public class XtdyExport {

    @Excel(name = "ID")
    private String id;

    @Excel(name = "应用名称")
    private String appName;

    @Excel(name = "服务名称")
    private String serviceAlias;

    @Excel(name = "服务上线时间", exportFormat = "yyyy-MM-dd HH:mm:ss")
    private Date serviceCreateTime;

    @Excel(name = "日期")
    private String currentTime;

    @Excel(name = "调用次数",type = 10)
    private Integer reqCount;

    @Excel(name = "请求的最大时延(ms)",type = 10)
    private Long maxLatency;

    @Excel(name = "请求的平均时延(ms)",type = 10)
    private Double avgLatency;

    @Excel(name = "访问后端最大处理时延(ms)",type = 10)
    private Long maxBackendLatency;

    @Excel(name = "访问后端平均处理时延(ms)",type = 10)
    private Double avgBackendLatency;

    @Excel(name = "访问网关最大处理时延(ms)",type = 10)
    private Long maxInnerLatency;

    @Excel(name = "访问网关平均处理时延(ms)",type = 10)
    private Double avgInnerLatency;

    @Excel(name = "异常数",type = 10)
    private Integer abnormal;

    @Excel(name = "警种")
    private String policeCategory;

    @Excel(name = "地市")
    private String areaName;

    @Excel(name = "建设厂商")
    private String buildVendor;

    @Excel(name = "应用订购时间", exportFormat = "yyyy-MM-dd HH:mm:ss")
    private Date subscribeTime;

    @Excel(name = "应用订购申请人")
    private String subscribeUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getServiceAlias() {
        return serviceAlias;
    }

    public void setServiceAlias(String serviceAlias) {
        this.serviceAlias = serviceAlias;
    }

    public Date getServiceCreateTime() {
        return serviceCreateTime;
    }

    public void setServiceCreateTime(Date serviceCreateTime) {
        this.serviceCreateTime = serviceCreateTime;
    }

    public Integer getReqCount() {
        return reqCount;
    }

    public void setReqCount(Integer reqCount) {
        this.reqCount = reqCount;
    }

    public Long getMaxLatency() {
        return maxLatency;
    }

    public void setMaxLatency(Long maxLatency) {
        this.maxLatency = maxLatency;
    }

    public Long getMaxBackendLatency() {
        return maxBackendLatency;
    }

    public void setMaxBackendLatency(Long maxBackendLatency) {
        this.maxBackendLatency = maxBackendLatency;
    }

    public Long getMaxInnerLatency() {
        return maxInnerLatency;
    }

    public void setMaxInnerLatency(Long maxInnerLatency) {
        this.maxInnerLatency = maxInnerLatency;
    }

    public Double getAvgLatency() {
        return avgLatency;
    }

    public void setAvgLatency(Double avgLatency) {
        this.avgLatency = avgLatency;
    }

    public Double getAvgBackendLatency() {
        return avgBackendLatency;
    }

    public void setAvgBackendLatency(Double avgBackendLatency) {
        this.avgBackendLatency = avgBackendLatency;
    }

    public Double getAvgInnerLatency() {
        return avgInnerLatency;
    }

    public void setAvgInnerLatency(Double avgInnerLatency) {
        if (avgInnerLatency<0){
            this.avgInnerLatency = 0d;
        }else {
            this.avgInnerLatency = avgInnerLatency;
        }
    }

    public Integer getAbnormal() {
        return abnormal;
    }

    public void setAbnormal(Integer abnormal) {
        this.abnormal = abnormal;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getAreaName() {
        if ("省厅".equals(areaName)) {
            return null;
        }
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getPoliceCategory() {
        return policeCategory;
    }

    public void setPoliceCategory(String policeCategory) {
        this.policeCategory = policeCategory;
    }

    public String getBuildVendor() {
        return buildVendor;
    }

    public void setBuildVendor(String buildVendor) {
        this.buildVendor = buildVendor;
    }

    public Date getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public String getSubscribeUser() {
        return subscribeUser;
    }

    public void setSubscribeUser(String subscribeUser) {
        this.subscribeUser = subscribeUser;
    }

}
