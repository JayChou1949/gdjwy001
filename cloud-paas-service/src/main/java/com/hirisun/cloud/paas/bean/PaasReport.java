package com.hirisun.cloud.paas.bean;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import cn.afterturn.easypoi.excel.annotation.Excel;

@TableName("TB_REPORT_PAAS")
public class PaasReport extends Model<PaasReport> {

    private static final long serialVersionUID = 1L;

        /**
     * UUID
     */
         @TableId(value = "ID",type = IdType.UUID)
    private String id;


    /**
     * 资源名称
     */
    @Excel(name = "资源名称")
    @TableField("RESOURCE_NAME")
    private String resourceName;

    /**
     * CPU 核
     */
    @Excel(name = "CPU(核)",type = 10)
    @TableField("CPU")
    private Double cpu;

    /**
     * 内存 GB
     */
    @Excel(name = "内存(GB)",type = 10)
    @TableField("MEMORY")
    private Double memory;

    /**
     * 存储 TB
     */
    @Excel(name = "存储(TB)",type = 10)
    @TableField("DISK")
    private Double disk;
        /**
     * 备注
     */
         @TableField("REMARK")
    private String remark;

        /**
     * 应用信息ID
     */
         @TableField("PROJECT_ID")
    private String projectId;

        /**
     * 订单ID
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

        /**
     * 创建时间
     */
        @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

        /**
     * 修改时间
     */
         @TableField(value = "MODIFIED_TIME",fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;


    public String getId() {
        return id;
    }

    public PaasReport setId(String id) {
        this.id = id;
        return this;
    }

    public String getResourceName() {
        return resourceName;
    }

    public PaasReport setResourceName(String resourceName) {
        this.resourceName = resourceName;
        return this;
    }

    public Double getCpu() {
        return cpu;
    }

    public PaasReport setCpu(Double cpu) {
        this.cpu = cpu;
        return this;
    }

    public Double getMemory() {
        return memory;
    }

    public PaasReport setMemory(Double memory) {
        this.memory = memory;
        return this;
    }

    public Double getDisk() {
        return disk;
    }

    public PaasReport setDisk(Double disk) {
        this.disk = disk;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public PaasReport setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getProjectId() {
        return projectId;
    }

    public PaasReport setProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public PaasReport setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
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
        return "ReportPaas{" +
        "id=" + id +
        ", resourceName=" + resourceName +
        ", cpu=" + cpu +
        ", memory=" + memory +
        ", disk=" + disk +
        ", remark=" + remark +
        ", projectId=" + projectId +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
