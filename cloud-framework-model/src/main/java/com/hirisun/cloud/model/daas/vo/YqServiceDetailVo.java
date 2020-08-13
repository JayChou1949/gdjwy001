package com.hirisun.cloud.model.daas.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 疫情服务订阅详情类
 * @author junglefisher
 * @date 2020/2/20 9:12
 */
public class YqServiceDetailVo {
    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * 发布时间
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date serviceTime;
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 订购时间
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date orderTime;
    /**
     * 调用次数
     */
    private Integer reqCount;
    /**
     * 平均时延
     */
    private Double avgLatency;
    /**
     * api产品ID
     */
    private String apiId;
    /**
     * 堆栈ID
     */
    private String actualId;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Date getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(Date serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getReqCount() {
        return reqCount;
    }

    public void setReqCount(Integer reqCount) {
        this.reqCount = reqCount;
    }

    public Double getAvgLatency() {
        return avgLatency;
    }

    public void setAvgLatency(Double avgLatency) {
        this.avgLatency = avgLatency;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getActualId() {
        return actualId;
    }

    public void setActualId(String actualId) {
        this.actualId = actualId;
    }

    @Override
    public String toString() {
        return "YqServiceDetail{" +
                "serviceName='" + serviceName + '\'' +
                ", serviceTime=" + serviceTime +
                ", appName='" + appName + '\'' +
                ", orderTime=" + orderTime +
                ", reqCount=" + reqCount +
                ", avgLatency=" + avgLatency +
                ", apiId='" + apiId + '\'' +
                ", actualId='" + actualId + '\'' +
                '}';
    }
}
