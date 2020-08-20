package com.hirisun.cloud.order.bean.saas;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * SaaS申请原始信息扩展表
 */
@TableName("TB_SAAS_APPLICATION_EXT")
public class SaasApplicationExt extends Model<SaasApplicationExt> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 服务名称
     */
         @TableField("SERVICE_NAME")
    private String serviceName;

        /**
     * 服务ID
     */
         @TableField("SERVICE_ID")
    private String serviceId;

        /**
     * 申请信息 id
     */
         @TableField("MASTER_ID")
    private String masterId;

    /**
     * 访问总数
     */
    @TableField(value = "VIEW_COUNT",exist = false)
    private Integer viewCount;

        /**
     * 应用开通次数
     */
         @TableField(value = "OPENING_NUMBER",exist = false)
    private Integer openingNumber;

        /**
     * 应用开通时间
     */
         @TableField(value = "OPENING_TIME",exist = false)
    private Date openingTime;

        /**
     * 权限被回收次数
     */
         @TableField(value = "RECYCLING_NUMBER",exist = false)
    private Integer recyclingNumber;

    /**
     * 最后回收时间
     */
    @TableField(value = "RECYCLING_TIME",exist = false)
    private Date recyclingTime;


    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
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

    public SaasApplicationExt setId(String id) {
        this.id = id;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public SaasApplicationExt setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getServiceId() {
        return serviceId;
    }

    public SaasApplicationExt setServiceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public String getMasterId() {
        return masterId;
    }

    public SaasApplicationExt setMasterId(String masterId) {
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SaasApplicationExt{" +
                "id='" + id + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", masterId='" + masterId + '\'' +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                '}';
    }

}
