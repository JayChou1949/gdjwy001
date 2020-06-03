package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 */
@TableName("TB_SERVICE_INSTANCE")
public class ServiceInstance extends Model<ServiceInstance> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

    @TableField("NAME")
    private String name;

    @TableField("ALIAS")
    private String alias;

    @TableField("SERVICE_NAME")
    private String serviceName;

    @TableField("SERVICE_ALIAS")
    private String serviceAlias;

    @TableField("SERVICE_GUID")
    private String serviceGuid;

    @TableField("SERVICE_PLAN_NAME")
    private String servicePlanName;

    @TableField("INSTANCE_TYPE")
    private String instanceType;

    @TableField("ACTUAL_ID")
    private String actualId;

    @TableField("STATUS")
    private String status;

    @TableField("CREATED_AT")
    private Date createdAt;

    @TableField("UPDATED_AT")
    private Date updatedAt;

    @TableField("ACTUAL_NAME")
    private String actualName;

    @TableField("GUID")
    private String guid;

    @TableField("DOMAIN_ID")
    private String domainId;
    @TableField("PROJECT_ID")
    private String projectId;

    @TableField("AREA_NAME")
    private String areaName;

    @TableField("POLICE_CATEGORY")
    private String policeCategory;

    @TableField("USER_ID")
    private String userId;


    /**
     * 分类
     */
    @TableField("CATA_LOG")
    private Integer cataLog;

    /**
     * appName
     * @return
     */

    @TableField("APP_NAME")
    private String appName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ServiceInstance setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ServiceInstance setName(String name) {
        this.name = name;
        return this;
    }

    public String getAlias() {
        return alias;
    }

    public ServiceInstance setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public ServiceInstance setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getServiceAlias() {
        return serviceAlias;
    }

    public ServiceInstance setServiceAlias(String serviceAlias) {
        this.serviceAlias = serviceAlias;
        return this;
    }

    public String getServiceGuid() {
        return serviceGuid;
    }

    public ServiceInstance setServiceGuid(String serviceGuid) {
        this.serviceGuid = serviceGuid;
        return this;
    }

    public String getServicePlanName() {
        return servicePlanName;
    }

    public ServiceInstance setServicePlanName(String servicePlanName) {
        this.servicePlanName = servicePlanName;
        return this;
    }

    public String getInstanceType() {
        return instanceType;
    }

    public ServiceInstance setInstanceType(String instanceType) {
        this.instanceType = instanceType;
        return this;
    }

    public String getActualId() {
        return actualId;
    }

    public ServiceInstance setActualId(String actualId) {
        this.actualId = actualId;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public ServiceInstance setStatus(String status) {
        this.status = status;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public ServiceInstance setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public ServiceInstance setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getActualName() {
        return actualName;
    }

    public ServiceInstance setActualName(String actualName) {
        this.actualName = actualName;
        return this;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getAreaName() {
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

    public Integer getCataLog() {
        return cataLog;
    }

    public void setCataLog(Integer cataLog) {
        this.cataLog = cataLog;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ServiceInstance{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", serviceAlias='" + serviceAlias + '\'' +
                ", serviceGuid='" + serviceGuid + '\'' +
                ", servicePlanName='" + servicePlanName + '\'' +
                ", instanceType='" + instanceType + '\'' +
                ", actualId='" + actualId + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", actualName='" + actualName + '\'' +
                ", guid='" + guid + '\'' +
                ", domainId='" + domainId + '\'' +
                ", projectId='" + projectId + '\'' +
                ", areaName='" + areaName + '\'' +
                ", policeCategory='" + policeCategory + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
