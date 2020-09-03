package com.hirisun.cloud.paas.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 大数据组件申请组件信息表
 * </p>
 *
 * @author junglefisher
 * @since 2020-05-08
 */
@TableName("TB_PAAS_COMPONENT_DETAIL")
public class PaasComponentDetail extends Model<PaasComponentDetail> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 已有Hadoop目录、HBase Namespace、Hive数据库权限需求
     */
         @TableField("IF_AUTH")
    private String ifAuth;

        /**
     * 是否新建Hadoop目录、HBase Namespace、Hive数据库
     */
         @TableField("IF_CREATE")
    private String ifCreate;

        /**
     * CPU(核)
     */
         @TableField("CPU")
    private String cpu;

        /**
     * 内存（GB）
     */
         @TableField("MEMORY")
    private String memory;

        /**
     * 存储（TB）
     */
         @TableField("DISK")
    private String disk;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

        /**
     * 大数据组件申请信息ID
     */
         @TableField("COMPONENT_ID")
    private String componentId;

    /**
     * 组件名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 组件版本
     */
    @TableField("VERSION")
    private String version;

    /**
     * 备注
     * @return
     */
    @TableField("REMARK")
    private String remark;

    /**
     * 存储周期
     */
    @TableField("STORAGE_CYCLE")
    private Integer storageCycle;

    public Integer getStorageCycle() {
        return storageCycle;
    }

    public void setStorageCycle(Integer storageCycle) {
        this.storageCycle = storageCycle;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public PaasComponentDetail setId(String id) {
        this.id = id;
        return this;
    }

    public String getIfAuth() {
        return ifAuth;
    }

    public PaasComponentDetail setIfAuth(String ifAuth) {
        this.ifAuth = ifAuth;
        return this;
    }

    public String getIfCreate() {
        return ifCreate;
    }

    public PaasComponentDetail setIfCreate(String ifCreate) {
        this.ifCreate = ifCreate;
        return this;
    }

    public String getCpu() {
        return cpu;
    }

    public PaasComponentDetail setCpu(String cpu) {
        this.cpu = cpu;
        return this;
    }

    public String getMemory() {
        return memory;
    }

    public PaasComponentDetail setMemory(String memory) {
        this.memory = memory;
        return this;
    }

    public String getDisk() {
        return disk;
    }

    public PaasComponentDetail setDisk(String disk) {
        this.disk = disk;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public PaasComponentDetail setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public PaasComponentDetail setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getComponentId() {
        return componentId;
    }

    public PaasComponentDetail setComponentId(String componentId) {
        this.componentId = componentId;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasComponentDetail{" +
        "id=" + id +
        ", ifAuth=" + ifAuth +
        ", ifCreate=" + ifCreate +
        ", cpu=" + cpu +
        ", memory=" + memory +
        ", disk=" + disk +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", componentId=" + componentId +
        "}";
    }
}
