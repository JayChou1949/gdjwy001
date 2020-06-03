package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * IAAS资源上报虚拟机
 * </p>
 *
 * @author wuc
 * @since 2019-09-09
 */
@TableName("TB_IAAS_ZYSB_XNJ")
public class IaasZysbXnj extends Model<IaasZysbXnj> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 组件名称
     */
         @TableField("RESOURCE_NAME")
    private String resourceName;

        /**
     * 说明
     */
         @TableField("REMARK")
    private String remark;

        /**
     * 申请信息 id
     */
         @TableField("MASTER_ID")
    private String masterId;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

        /**
     * 应用ID
     */
         @TableField("PROJECT_ID")
    private String projectId;

    @TableField("CPU")
    private Double cpu;

        /**
     * 内存
     */
         @TableField("MEMORYS")
    private Double memorys;

        /**
     * 存储
     */
         @TableField("STORAGES")
    private Double storages;


    public String getId() {
        return id;
    }

    public IaasZysbXnj setId(String id) {
        this.id = id;
        return this;
    }

    public String getResourceName() {
        return resourceName;
    }

    public IaasZysbXnj setResourceName(String resourceName) {
        this.resourceName = resourceName;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public IaasZysbXnj setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getMasterId() {
        return masterId;
    }

    public IaasZysbXnj setMasterId(String masterId) {
        this.masterId = masterId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public IaasZysbXnj setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public IaasZysbXnj setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getProjectId() {
        return projectId;
    }

    public IaasZysbXnj setProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public Double getCpu() {
        return cpu;
    }

    public IaasZysbXnj setCpu(Double cpu) {
        this.cpu = cpu;
        return this;
    }

    public Double getMemorys() {
        return memorys;
    }

    public IaasZysbXnj setMemorys(Double memorys) {
        this.memorys = memorys;
        return this;
    }

    public Double getStorages() {
        return storages;
    }

    public IaasZysbXnj setStorages(Double storages) {
        this.storages = storages;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasZysbXnj{" +
        "id=" + id +
        ", resourceName=" + resourceName +
        ", remark=" + remark +
        ", masterId=" + masterId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", projectId=" + projectId +
        ", cpu=" + cpu +
        ", memorys=" + memorys +
        ", storages=" + storages +
        "}";
    }
}
