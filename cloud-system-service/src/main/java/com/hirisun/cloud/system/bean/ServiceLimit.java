package com.hirisun.cloud.system.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author wuc
 * @since 2020-03-03
 */
@TableName("TB_SERVICE_LIMIT")
public class ServiceLimit extends Model<ServiceLimit> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * CPU限额
     */
         @TableField("CPU")
         @ApiModelProperty(name = "CPU限额")
    private Double cpu;

        /**
     * 内存限额
     */
         @TableField("MEMORY")
         @ApiModelProperty(name = "内存限额")
    private Double memory;

        /**
     * 存储限额
     */
         @TableField("STORAGE")
         @ApiModelProperty(name = "存储限额")
    private Double storage;

        /**
     * 地市
     */
         @TableField("AREA")
         @ApiModelProperty(name = "地区")
    private String area;

         @TableField("POLICE_CATEGORY")
         @ApiModelProperty(name = "警种")
         private String policeCategory;

         @TableField("SERVICE_NAME")
         @ApiModelProperty(name = "资源服务名")
         private String serviceName;

        /**
     * 说明
     */
         @TableField("DESCRIPTION")
         @ApiModelProperty(name = "说明")
    private String description;

    /**
     * 资源类型 1:IAAS 2:DAAS 3:PAAS 4:SAAS
     */
    @TableField("RESOURCE_TYPE")
    private Long resourceType;

    @TableField("FORM_NUM")
    private String formNum;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    /**
     * 国家专项
     */
    @TableField("NATIONAL_SPECIAL_PROJECT")
    private String nationalSpecialProject;


    public String getId() {
        return id;
    }

    public ServiceLimit setId(String id) {
        this.id = id;
        return this;
    }


    public Double getCpu() {
        return cpu;
    }

    public ServiceLimit setCpu(Double cpu) {
        this.cpu = cpu;
        return this;
    }

    public Double getMemory() {
        return memory;
    }

    public ServiceLimit setMemory(Double memory) {
        this.memory = memory;
        return this;
    }

    public Double getStorage() {
        return storage;
    }

    public ServiceLimit setStorage(Double storage) {
        this.storage = storage;
        return this;
    }

    public String getArea() {
        return area;
    }

    public ServiceLimit setArea(String area) {
        this.area = area;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ServiceLimit setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPoliceCategory() {
        return policeCategory;
    }

    public void setPoliceCategory(String policeCategory) {
        this.policeCategory = policeCategory;
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

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Long getResourceType() {
        return resourceType;
    }

    public void setResourceType(Long resourceType) {
        this.resourceType = resourceType;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ServiceLimit{" +
                "id='" + id + '\'' +
                ", cpu=" + cpu +
                ", memory=" + memory +
                ", storage=" + storage +
                ", area='" + area + '\'' +
                ", description='" + description + '\'' +
                ", resourceType=" + resourceType +
                '}';
    }
}
