package com.hirisun.cloud.iaas.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * IAAS资源上报大数据
 * </p>
 *
 * @author wuc
 * @since 2019-09-09
 */
@TableName("TB_IAAS_ZYSB_DSJ")
public class IaasZysbDsj extends Model<IaasZysbDsj> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 组件名称
     */
        @Excel(name = "需求组件")
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

    @Excel(name = "CPU(核)", type = 10)
    @TableField("CPU")
    private Double cpu;

        /**
     * 内存
     */
        @Excel(name = "内存(GB)", type = 10)
         @TableField("MEMORYS")
    private Double memorys;

        /**
     * 存储
     */
        @Excel(name = "存储(TB)", type = 10)
         @TableField("STORAGES")
    private Double storages;

    public String getId() {
        return id;
    }

    public IaasZysbDsj setId(String id) {
        this.id = id;
        return this;
    }

    public String getResourceName() {
        return resourceName;
    }

    public IaasZysbDsj setResourceName(String resourceName) {
        this.resourceName = resourceName;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public IaasZysbDsj setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getMasterId() {
        return masterId;
    }

    public IaasZysbDsj setMasterId(String masterId) {
        this.masterId = masterId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public IaasZysbDsj setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public IaasZysbDsj setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getProjectId() {
        return projectId;
    }

    public IaasZysbDsj setProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public Double getCpu() {
        return cpu;
    }

    public IaasZysbDsj setCpu(Double cpu) {
        this.cpu = cpu;
        return this;
    }

    public Double getMemorys() {
        return memorys;
    }

    public IaasZysbDsj setMemorys(Double memorys) {
        this.memorys = memorys;
        return this;
    }

    public Double getStorages() {
        return storages;
    }

    public IaasZysbDsj setStorages(Double storages) {
        this.storages = storages;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasZysbDsj{" +
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
