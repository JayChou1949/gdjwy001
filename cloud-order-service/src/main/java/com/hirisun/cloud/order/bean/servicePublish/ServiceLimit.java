package com.hirisun.cloud.order.bean.servicePublish;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
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
@TableName("TB_SERVICE_LIMIT")
@ApiModel(value="ServiceLimit对象", description="")
public class ServiceLimit implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "UUID")
    @TableField("ID")
    private String id;

    @ApiModelProperty(value = "CPU限额")
    @TableField("CPU")
    private BigDecimal cpu;

    @ApiModelProperty(value = "内存限额")
    @TableField("MEMORY")
    private BigDecimal memory;

    @ApiModelProperty(value = "存储限额（TB）")
    @TableField("STORAGE")
    private BigDecimal storage;

    @ApiModelProperty(value = "地市")
    @TableField("AREA")
    private String area;

    @ApiModelProperty(value = "说明")
    @TableField("DESCRIPTION")
    private String description;

    @ApiModelProperty(value = "资源类型 1Iaas 2daas 3 paas")
    @TableField("RESOURCE_TYPE")
    private Integer resourceType;

    @ApiModelProperty(value = "资源服务名称 ECS")
    @TableField("SERVICE_NAME")
    private String serviceName;

    @ApiModelProperty(value = "警种")
    @TableField("POLICE_CATEGORY")
    private String policeCategory;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField("MODIFIED_TIME")
    private LocalDateTime modifiedTime;

    @ApiModelProperty(value = "表单编码")
    @TableField("FORM_NUM")
    private String formNum;

    @ApiModelProperty(value = "国家专项")
    @TableField("NATIONAL_SPECIAL_PROJECT")
    private String nationalSpecialProject;

    @ApiModelProperty(value = "地市id")
    @TableField("REGION_ID")
    private Integer regionId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getCpu() {
        return cpu;
    }

    public void setCpu(BigDecimal cpu) {
        this.cpu = cpu;
    }

    public BigDecimal getMemory() {
        return memory;
    }

    public void setMemory(BigDecimal memory) {
        this.memory = memory;
    }

    public BigDecimal getStorage() {
        return storage;
    }

    public void setStorage(BigDecimal storage) {
        this.storage = storage;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getPoliceCategory() {
        return policeCategory;
    }

    public void setPoliceCategory(String policeCategory) {
        this.policeCategory = policeCategory;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getFormNum() {
        return formNum;
    }

    public void setFormNum(String formNum) {
        this.formNum = formNum;
    }

    public String getNationalSpecialProject() {
        return nationalSpecialProject;
    }

    public void setNationalSpecialProject(String nationalSpecialProject) {
        this.nationalSpecialProject = nationalSpecialProject;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    @Override
    public String toString() {
        return "ServiceLimit{" +
        "id=" + id +
        ", cpu=" + cpu +
        ", memory=" + memory +
        ", storage=" + storage +
        ", area=" + area +
        ", description=" + description +
        ", resourceType=" + resourceType +
        ", serviceName=" + serviceName +
        ", policeCategory=" + policeCategory +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", formNum=" + formNum +
        ", nationalSpecialProject=" + nationalSpecialProject +
        ", regionId=" + regionId +
        "}";
    }
}
