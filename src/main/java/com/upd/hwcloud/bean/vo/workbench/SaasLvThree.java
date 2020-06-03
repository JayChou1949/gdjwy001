package com.upd.hwcloud.bean.vo.workbench;

import java.math.BigDecimal;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author yyc
 * @date 2019/12/4
 * @description: Saas应用三级页面统计VO（从API网关获取的数据）
 */
public class SaasLvThree {

    /**
     * 服务名(若关联失败,取产品族名)
     */
    @Excel(name = "服务名称")
    private String serviceName;

    /**
     * 厂商
     */
    @Excel(name = "服务厂商")
    private String provider;


    /**
     * 应用名
     */
    private String appName;


    /**
     *产品族ID
     */
    private String productFamilyID;

    /**
     * 产品族名
     */
    private String productFamilyName;


    /**
     * 请求次数
     */
    @Excel(name = "调用总数")
    private Integer req;

    /**
     * 异常次数(4xx+5xx)
     */
    @Excel(name = "异常调用")
    private Integer abnormal;


    /**
     * 时延
     */
    @Excel(name = "时延")
    private BigDecimal delay;

    /**
     *异常率
     */
    @Excel(name = "异常率")
    private BigDecimal abnormalRate;



    /**
     *用于PaaS,SaaS跳转的ID
     */
    private String id;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getProductFamilyID() {
        return productFamilyID;
    }

    public void setProductFamilyID(String productFamilyID) {
        this.productFamilyID = productFamilyID;
    }

    public String getProductFamilyName() {
        return productFamilyName;
    }

    public void setProductFamilyName(String productFamilyName) {
        this.productFamilyName = productFamilyName;
    }

    public Integer getReq() {
        return req;
    }

    public void setReq(Integer req) {
        this.req = req;
    }

    public Integer getAbnormal() {
        return abnormal;
    }

    public void setAbnormal(Integer abnormal) {
        this.abnormal = abnormal;
    }

    public BigDecimal getDelay() {
        return delay;
    }

    public void setDelay(BigDecimal delay) {
        this.delay = delay;
    }

    public BigDecimal getAbnormalRate() {
        return abnormalRate;
    }

    public void setAbnormalRate(BigDecimal abnormalRate) {
        this.abnormalRate = abnormalRate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SaasLvThree{" +
                "serviceName='" + serviceName + '\'' +
                ", provider='" + provider + '\'' +
                ", appName='" + appName + '\'' +
                ", productFamilyID='" + productFamilyID + '\'' +
                ", productFamilyName='" + productFamilyName + '\'' +
                ", req=" + req +
                ", abnormal=" + abnormal +
                ", delay=" + delay +
                ", abnormalRate=" + abnormalRate +
                ", id='" + id + '\'' +
                '}';
    }
}
