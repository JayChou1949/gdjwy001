package com.hirisun.cloud.model.ncov.dto;

/**
 * @author junglefisher
 * @date 2020/2/27 15:16
 */
public class CovStatistic {

    /**
     * 序号
     */
    private Integer num;
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 警种
     */
    private String category;
    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * 调用次数
     */
    private Integer req;
    /**
     * 调用时间
     */
    private String time;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getReq() {
        return req;
    }

    public void setReq(Integer req) {
        this.req = req;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "CovStatistic{" +
                "num=" + num +
                ", appName='" + appName + '\'' +
                ", category='" + category + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", req=" + req +
                ", time='" + time + '\'' +
                '}';
    }
}
