package com.hirisun.cloud.order.bean.servicePublish;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-01
 */
@TableName("TB_SERVICE_INSTANCE")
@ApiModel(value="ServiceInstance对象", description="")
public class ServiceInstance implements Serializable {

    private static final long serialVersionUID=1L;

    @TableField("ID")
    private String id;

    @ApiModelProperty(value = "实例名")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "实例别名")
    @TableField("ALIAS")
    private String alias;

    @ApiModelProperty(value = "所属服务名")
    @TableField("SERVICE_NAME")
    private String serviceName;

    @TableField("SERVICE_ALIAS")
    private String serviceAlias;

    @ApiModelProperty(value = "服务ID")
    @TableField("SERVICE_GUID")
    private String serviceGuid;

    @ApiModelProperty(value = "服务套餐名")
    @TableField("SERVICE_PLAN_NAME")
    private String servicePlanName;

    @ApiModelProperty(value = "服务实例类型")
    @TableField("INSTANCE_TYPE")
    private String instanceType;

    @ApiModelProperty(value = "堆栈ID")
    @TableField("ACTUAL_ID")
    private String actualId;

    @ApiModelProperty(value = "实例状态")
    @TableField("STATUS")
    private String status;

    @ApiModelProperty(value = "实例创建时间")
    @TableField("CREATED_AT")
    private Date createdAt;

    @ApiModelProperty(value = "实例更新时间")
    @TableField("UPDATED_AT")
    private Date updatedAt;

    @ApiModelProperty(value = "堆栈名")
    @TableField("ACTUAL_NAME")
    private String actualName;

    @ApiModelProperty(value = "实例ID")
    @TableField("GUID")
    private String guid;

    @ApiModelProperty(value = "租户ID")
    @TableField("DOMAIN_ID")
    private String domainId;

    @ApiModelProperty(value = "项目ID")
    @TableField("PROJECT_ID")
    private String projectId;

    @ApiModelProperty(value = "地区")
    @TableField("AREA_NAME")
    private String areaName;

    @ApiModelProperty(value = "警种")
    @TableField("POLICE_CATEGORY")
    private String policeCategory;

    @ApiModelProperty(value = "所属用户ID")
    @TableField("USER_ID")
    private String userId;

    @TableField("CATA_LOG")
    private Integer cataLog;

    @TableField("APP_NAME")
    private String appName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceAlias() {
        return serviceAlias;
    }

    public void setServiceAlias(String serviceAlias) {
        this.serviceAlias = serviceAlias;
    }

    public String getServiceGuid() {
        return serviceGuid;
    }

    public void setServiceGuid(String serviceGuid) {
        this.serviceGuid = serviceGuid;
    }

    public String getServicePlanName() {
        return servicePlanName;
    }

    public void setServicePlanName(String servicePlanName) {
        this.servicePlanName = servicePlanName;
    }

    public String getInstanceType() {
        return instanceType;
    }

    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
    }

    public String getActualId() {
        return actualId;
    }

    public void setActualId(String actualId) {
        this.actualId = actualId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getActualName() {
        return actualName;
    }

    public void setActualName(String actualName) {
        this.actualName = actualName;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getCataLog() {
        return cataLog;
    }

    public void setCataLog(Integer cataLog) {
        this.cataLog = cataLog;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public String toString() {
        return "ServiceInstance{" +
        "id=" + id +
        ", name=" + name +
        ", alias=" + alias +
        ", serviceName=" + serviceName +
        ", serviceAlias=" + serviceAlias +
        ", serviceGuid=" + serviceGuid +
        ", servicePlanName=" + servicePlanName +
        ", instanceType=" + instanceType +
        ", actualId=" + actualId +
        ", status=" + status +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        ", actualName=" + actualName +
        ", guid=" + guid +
        ", domainId=" + domainId +
        ", projectId=" + projectId +
        ", areaName=" + areaName +
        ", policeCategory=" + policeCategory +
        ", userId=" + userId +
        ", cataLog=" + cataLog +
        ", appName=" + appName +
        "}";
    }
}
