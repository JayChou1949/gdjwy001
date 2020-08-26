package com.hirisun.cloud.paas.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 大数据组件组件业务办理表
 * </p>
 *
 * @author yyc
 * @since 2020-05-11
 */
@TableName("TB_PAAS_COMPONENT_DETAIL_IMPL")
public class PaasComponentDetailImpl extends Model<PaasComponentDetailImpl> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
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

        /**
     * 创建时间
     */
        @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

        /**
     * 修改时间
     */
        @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

        /**
     * 组件类型
     */
         @TableField("TYPE")
    private String type;

        /**
     * 办理链接信息ID
     */
         @TableField("LINK_ID")
    private String linkId;

        /**
     * 组件名称
     */
         @TableField("NAME")
    private String name;

        /**
     * 版本
     */
         @TableField("VERSION")
    private String version;

        /**
     * 备注
     */
         @TableField("REMARK")
    private String remark;
    /**
     * 存储周期
     */
    @TableField("STORAGE_CYCLE")
    private Integer storageCycle;



        /**
     * 订单ID
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;



    public Integer getStorageCycle() {
        return storageCycle;
    }

    public void setStorageCycle(Integer storageCycle) {
        this.storageCycle = storageCycle;
    }
    public String getId() {
        return id;
    }

    public PaasComponentDetailImpl setId(String id) {
        this.id = id;
        return this;
    }

    public String getIfAuth() {
        return ifAuth;
    }

    public PaasComponentDetailImpl setIfAuth(String ifAuth) {
        this.ifAuth = ifAuth;
        return this;
    }

    public String getIfCreate() {
        return ifCreate;
    }

    public PaasComponentDetailImpl setIfCreate(String ifCreate) {
        this.ifCreate = ifCreate;
        return this;
    }

    public String getCpu() {
        return cpu;
    }

    public PaasComponentDetailImpl setCpu(String cpu) {
        this.cpu = cpu;
        return this;
    }

    public String getMemory() {
        return memory;
    }

    public PaasComponentDetailImpl setMemory(String memory) {
        this.memory = memory;
        return this;
    }

    public String getDisk() {
        return disk;
    }

    public PaasComponentDetailImpl setDisk(String disk) {
        this.disk = disk;
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

    public String getType() {
        return type;
    }

    public PaasComponentDetailImpl setType(String type) {
        this.type = type;
        return this;
    }

    public String getLinkId() {
        return linkId;
    }

    public PaasComponentDetailImpl setLinkId(String linkId) {
        this.linkId = linkId;
        return this;
    }

    public String getName() {
        return name;
    }

    public PaasComponentDetailImpl setName(String name) {
        this.name = name;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public PaasComponentDetailImpl setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public PaasComponentDetailImpl setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public PaasComponentDetailImpl setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasComponentDetailImpl{" +
        "id=" + id +
        ", ifAuth=" + ifAuth +
        ", ifCreate=" + ifCreate +
        ", cpu=" + cpu +
        ", memory=" + memory +
        ", disk=" + disk +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", type=" + type +
        ", linkId=" + linkId +
        ", name=" + name +
        ", version=" + version +
        ", remark=" + remark +
        ", appInfoId=" + appInfoId +
        "}";
    }
}
