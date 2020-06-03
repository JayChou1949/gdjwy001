package com.upd.hwcloud.bean.entity.application;

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
 * 资源回收实施表
 * </p>
 *
 * @author yyc
 * @since 2020-05-17
 */
@TableName("TB_RESOURCE_RECOVER_IMPL")
public class ResourceRecoverImpl extends Model<ResourceRecoverImpl> {

    private static final long serialVersionUID = 1L;

        /**
     * UUID
     */
         @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 缩配类型
     */
         @TableField("SHRINK_TYPE")
    private String shrinkType;

        /**
     * 应用名称
     */
         @TableField("APPLICATION_NAME")
    private String applicationName;

        /**
     * 实例名称
     */
         @TableField("INSTANCE_NAME")
    private String instanceName;

        /**
     * 私有IP地址
     */
         @TableField("PRIVATE_IP_ADDRESS")
    private String privateIpAddress;

        /**
     * 缩配前CPU(核)
     */
         @TableField("BEFORE_SHRINK_CPU")
    private String beforeShrinkCpu;

        /**
     * 缩配前内存(GB)
     */
         @TableField("BEFORE_SHRINK_MEMORY")
    private String beforeShrinkMemory;

        /**
     * 缩配前磁盘(GB)
     */
         @TableField("BEFORE_SHRINK_DISK")
    private String beforeShrinkDisk;

        /**
     * 实际缩配CPU(核)
     */
         @TableField("ACTUAL_SHRINK_CPU")
    private String actualShrinkCpu;

        /**
     * 实际缩配内存(GB)
     */
         @TableField("ACTUAL_SHRINK_MEMORY")
    private String actualShrinkMemory;

        /**
     * 实际缩配磁盘(GB)
     */
         @TableField("ACTUAL_SHRINK_DISK")
    private String actualShrinkDisk;

        /**
     * 处理结果
     */
         @TableField("PROCESS_RESULT")
    private String processResult;

        /**
     * 申请单ID
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
        @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;


    public String getId() {
        return id;
    }

    public ResourceRecoverImpl setId(String id) {
        this.id = id;
        return this;
    }

    public String getShrinkType() {
        return shrinkType;
    }

    public ResourceRecoverImpl setShrinkType(String shrinkType) {
        this.shrinkType = shrinkType;
        return this;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public ResourceRecoverImpl setApplicationName(String applicationName) {
        this.applicationName = applicationName;
        return this;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public ResourceRecoverImpl setInstanceName(String instanceName) {
        this.instanceName = instanceName;
        return this;
    }

    public String getPrivateIpAddress() {
        return privateIpAddress;
    }

    public ResourceRecoverImpl setPrivateIpAddress(String privateIpAddress) {
        this.privateIpAddress = privateIpAddress;
        return this;
    }

    public String getBeforeShrinkCpu() {
        return beforeShrinkCpu;
    }

    public ResourceRecoverImpl setBeforeShrinkCpu(String beforeShrinkCpu) {
        this.beforeShrinkCpu = beforeShrinkCpu;
        return this;
    }

    public String getBeforeShrinkMemory() {
        return beforeShrinkMemory;
    }

    public ResourceRecoverImpl setBeforeShrinkMemory(String beforeShrinkMemory) {
        this.beforeShrinkMemory = beforeShrinkMemory;
        return this;
    }

    public String getBeforeShrinkDisk() {
        return beforeShrinkDisk;
    }

    public ResourceRecoverImpl setBeforeShrinkDisk(String beforeShrinkDisk) {
        this.beforeShrinkDisk = beforeShrinkDisk;
        return this;
    }

    public String getActualShrinkCpu() {
        return actualShrinkCpu;
    }

    public ResourceRecoverImpl setActualShrinkCpu(String actualShrinkCpu) {
        this.actualShrinkCpu = actualShrinkCpu;
        return this;
    }

    public String getActualShrinkMemory() {
        return actualShrinkMemory;
    }

    public ResourceRecoverImpl setActualShrinkMemory(String actualShrinkMemory) {
        this.actualShrinkMemory = actualShrinkMemory;
        return this;
    }

    public String getActualShrinkDisk() {
        return actualShrinkDisk;
    }

    public ResourceRecoverImpl setActualShrinkDisk(String actualShrinkDisk) {
        this.actualShrinkDisk = actualShrinkDisk;
        return this;
    }

    public String getProcessResult() {
        return processResult;
    }

    public ResourceRecoverImpl setProcessResult(String processResult) {
        this.processResult = processResult;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public ResourceRecoverImpl setAppInfoId(String appInfoId) {
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
        return "ResourceRecoverImpl{" +
        "id=" + id +
        ", shrinkType=" + shrinkType +
        ", applicationName=" + applicationName +
        ", instanceName=" + instanceName +
        ", privateIpAddress=" + privateIpAddress +
        ", beforeShrinkCpu=" + beforeShrinkCpu +
        ", beforeShrinkMemory=" + beforeShrinkMemory +
        ", beforeShrinkDisk=" + beforeShrinkDisk +
        ", actualShrinkCpu=" + actualShrinkCpu +
        ", actualShrinkMemory=" + actualShrinkMemory +
        ", actualShrinkDisk=" + actualShrinkDisk +
        ", processResult=" + processResult +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
