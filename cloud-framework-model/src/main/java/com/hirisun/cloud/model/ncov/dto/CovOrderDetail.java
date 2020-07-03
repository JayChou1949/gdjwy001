package com.hirisun.cloud.model.ncov.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author junglefisher
 * @date 2020/2/27 15:17
 */
public class CovOrderDetail {
    /**
     * 序号
     */
    private Integer num;
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * 订购时间
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date orderTime;
    /**
     * 警种
     */
    private String category;
    /**
     * 实例ID
     */
    private String instanceId;
    /**
     * 服务ID
     */
    private String serviceId;
    /**
     * 调用总数
     */
    private Integer req;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

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

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getReq() {
        return req;
    }

    public void setReq(Integer req) {
        this.req = req;
    }

    @Override
    public String toString() {
        return "CovOrderDetail{" +
                "num=" + num +
                ", appName='" + appName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", category='" + category + '\'' +
                ", req=" + req +
                '}';
    }
}
