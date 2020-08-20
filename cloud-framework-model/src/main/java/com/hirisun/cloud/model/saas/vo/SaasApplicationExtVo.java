package com.hirisun.cloud.model.saas.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * SaaS申请原始信息扩展表
 */
public class SaasApplicationExtVo implements Serializable {

	private static final long serialVersionUID = -2265557651097339174L;

	private String id;

        /**
     * 服务名称
     */
    private String serviceName;

        /**
     * 服务ID
     */
    private String serviceId;

        /**
     * 申请信息 id
     */
    private String masterId;

    /**
     * 访问总数
     */
    private Integer viewCount;

        /**
     * 应用开通次数
     */
    private Integer openingNumber;

        /**
     * 应用开通时间
     */
    private Date openingTime;

        /**
     * 权限被回收次数
     */
    private Integer recyclingNumber;

    /**
     * 最后回收时间
     */
    private Date recyclingTime;


    private Date createTime;

    private Date modifiedTime;

    public Date getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Date openingTime) {
        this.openingTime = openingTime;
    }

    public Date getRecyclingTime() {
        return recyclingTime;
    }

    public void setRecyclingTime(Date recyclingTime) {
        this.recyclingTime = recyclingTime;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getOpeningNumber() {
        return openingNumber;
    }

    public void setOpeningNumber(Integer openingNumber) {
        this.openingNumber = openingNumber;
    }

    public Integer getRecyclingNumber() {
        return recyclingNumber;
    }

    public void setRecyclingNumber(Integer recyclingNumber) {
        this.recyclingNumber = recyclingNumber;
    }

    public String getId() {
        return id;
    }

    public SaasApplicationExtVo setId(String id) {
        this.id = id;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public SaasApplicationExtVo setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getServiceId() {
        return serviceId;
    }

    public SaasApplicationExtVo setServiceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public String getMasterId() {
        return masterId;
    }

    public SaasApplicationExtVo setMasterId(String masterId) {
        this.masterId = masterId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

}
