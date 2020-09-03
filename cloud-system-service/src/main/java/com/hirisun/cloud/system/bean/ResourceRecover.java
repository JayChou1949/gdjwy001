package com.hirisun.cloud.system.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 资源回收列表
 * </p>
 *
 * @author yyc
 * @since 2020-05-14
 */
@TableName("TB_RESOURCE_RECOVER")
public class ResourceRecover extends Model<ResourceRecover> {

    private static final long serialVersionUID = 1L;

        /**
     * UUID
     */
         @TableId(value = "ID",type = IdType.UUID)
    private String id;

    /**
     * 缩配时间
     */
    @TableField("SHRINK_TIME")
    private String shrinkTime;

        /**
     * 缩配类型
     */
         @TableField("SHRINK_TYPE")
    private String shrinkType;

        /**
     * 可用分区
     */
         @TableField("AVAILABLE_PARTITION")
    private String availablePartition;

        /**
     * 警种
     */
         @TableField("POLICE_CATEGORY")
    private String policeCategory;

        /**
     * 厂商
     */
         @TableField("MANUFACTURER")
    private String manufacturer;

        /**
     * 使用人
     */
         @TableField("INSTANCE_USER")
    private String instanceUser;

        /**
     * 申请人
     */
         @TableField("APPLICANT")
    private String applicant;

        /**
     * 申请人电话
     */
         @TableField("APPLICANT_PHONE")
    private String applicantPhone;

        /**
     * 项目名称
     */
         @TableField("PROJECT_NAME")
    private String projectName;

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
     * 实例创建时间
     */
         @TableField("INSTANCE_CREATED_TIME")
    private String instanceCreatedTime;

        /**
     * 操作系统
     */
         @TableField("OS")
    private String os;

        /**
     * 规格
     */
         @TableField("SPECIFICATION")
    private String specification;

        /**
     * 云硬盘
     */
         @TableField("CLOUD_HARD_DISK")
    private String cloudHardDisk;

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
     * 缩配后CPU(核)
     */
         @TableField("AFTER_SHRINK_CPU")
    private String afterShrinkCpu;

        /**
     * 缩配后内存(GB)
     */
         @TableField("AFTER_SHRINK_MEMORY")
    private String afterShrinkMemory;

        /**
     * 缩配后磁盘(GB)
     */
         @TableField("AFTER_SHRINK_DISK")
    private String afterShrinkDisk;

        /**
     * CPU使用率平均值(%)
     */
         @TableField("CPU_AVG_RATIO")
    private String cpuAvgRatio;

        /**
     * CPU使用率最大值(%)
     */
         @TableField("CPU_MAX_RATIO")
    private String cpuMaxRatio;

        /**
     * 内存使用率平均值(%)
     */
         @TableField("MEMORY_AVG_RATIO")
    private String memoryAvgRatio;

        /**
     * 内存使用率最大值(%)
     */
         @TableField("MEMORY_MAX_RATIO")
    private String memoryMaxRatio;

        /**
     * 云磁盘使用率平均值(%)
     */
         @TableField("CLOUD_DISK_AVG_RATIO")
    private String cloudDiskAvgRatio;

        /**
     * 云磁盘使用率最大值(%)
     */
         @TableField("CLOUD_DISK_MAX_RATIO")
    private String cloudDiskMaxRatio;

        /**
     * 导入时间
     */
         @TableField("IMPORT_TIME")
    private Date importTime;

        /**
     * 导入时间字符串 年月日 时分秒
     */
         @TableField("IMPORT_TIME_STR")
    private String importTimeStr;

        /**
     * 0  未处理 1 处理中 2处理完成
     */
         @TableField("STATUS")
    private Integer status;

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
     * 创建人身份证号
     */
         @TableField("CREATOR_ID_CARD")
    private String creatorIdCard;

        /**
     * 关联回收单ID
     */
         @TableField("REF_ID")
    private String refId;

    /**
     * 被回收资源单位负责人单位名
     */
    @TableField("APPLICANT_ORG_NAME")
    private String applicantOrgName;


    public String getId() {
        return id;
    }

    public ResourceRecover setId(String id) {
        this.id = id;
        return this;
    }

    public String getShrinkTime() {
        return shrinkTime;
    }

    public ResourceRecover setShrinkTime(String shrinkTime) {
        this.shrinkTime = shrinkTime;
        return this;
    }

    public String getShrinkType() {
        return shrinkType;
    }

    public ResourceRecover setShrinkType(String shrinkType) {
        this.shrinkType = shrinkType;
        return this;
    }

    public String getAvailablePartition() {
        return availablePartition;
    }

    public ResourceRecover setAvailablePartition(String availablePartition) {
        this.availablePartition = availablePartition;
        return this;
    }

    public String getPoliceCategory() {
        return policeCategory;
    }

    public ResourceRecover setPoliceCategory(String policeCategory) {
        this.policeCategory = policeCategory;
        return this;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public ResourceRecover setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public String getInstanceUser() {
        return instanceUser;
    }

    public void setInstanceUser(String instanceUser) {
        this.instanceUser = instanceUser;
    }

    public String getApplicant() {
        return applicant;
    }

    public ResourceRecover setApplicant(String applicant) {
        this.applicant = applicant;
        return this;
    }

    public String getApplicantPhone() {
        return applicantPhone;
    }

    public ResourceRecover setApplicantPhone(String applicantPhone) {
        this.applicantPhone = applicantPhone;
        return this;
    }

    public String getProjectName() {
        return projectName;
    }

    public ResourceRecover setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public ResourceRecover setApplicationName(String applicationName) {
        this.applicationName = applicationName;
        return this;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public ResourceRecover setInstanceName(String instanceName) {
        this.instanceName = instanceName;
        return this;
    }

    public String getPrivateIpAddress() {
        return privateIpAddress;
    }

    public ResourceRecover setPrivateIpAddress(String privateIpAddress) {
        this.privateIpAddress = privateIpAddress;
        return this;
    }

    public String getInstanceCreatedTime() {
        return instanceCreatedTime;
    }

    public ResourceRecover setInstanceCreatedTime(String instanceCreatedTime) {
        this.instanceCreatedTime = instanceCreatedTime;
        return this;
    }

    public String getOs() {
        return os;
    }

    public ResourceRecover setOs(String os) {
        this.os = os;
        return this;
    }

    public String getSpecification() {
        return specification;
    }

    public ResourceRecover setSpecification(String specification) {
        this.specification = specification;
        return this;
    }

    public String getCloudHardDisk() {
        return cloudHardDisk;
    }

    public ResourceRecover setCloudHardDisk(String cloudHardDisk) {
        this.cloudHardDisk = cloudHardDisk;
        return this;
    }

    public String getBeforeShrinkCpu() {
        return beforeShrinkCpu;
    }

    public ResourceRecover setBeforeShrinkCpu(String beforeShrinkCpu) {
        this.beforeShrinkCpu = beforeShrinkCpu;
        return this;
    }

    public String getBeforeShrinkMemory() {
        return beforeShrinkMemory;
    }

    public ResourceRecover setBeforeShrinkMemory(String beforeShrinkMemory) {
        this.beforeShrinkMemory = beforeShrinkMemory;
        return this;
    }

    public String getBeforeShrinkDisk() {
        return beforeShrinkDisk;
    }

    public ResourceRecover setBeforeShrinkDisk(String beforeShrinkDisk) {
        this.beforeShrinkDisk = beforeShrinkDisk;
        return this;
    }

    public String getAfterShrinkCpu() {
        return afterShrinkCpu;
    }

    public ResourceRecover setAfterShrinkCpu(String afterShrinkCpu) {
        this.afterShrinkCpu = afterShrinkCpu;
        return this;
    }

    public String getAfterShrinkMemory() {
        return afterShrinkMemory;
    }

    public ResourceRecover setAfterShrinkMemory(String afterShrinkMemory) {
        this.afterShrinkMemory = afterShrinkMemory;
        return this;
    }

    public String getAfterShrinkDisk() {
        return afterShrinkDisk;
    }

    public ResourceRecover setAfterShrinkDisk(String afterShrinkDisk) {
        this.afterShrinkDisk = afterShrinkDisk;
        return this;
    }

    public String getCpuAvgRatio() {
        return cpuAvgRatio;
    }

    public ResourceRecover setCpuAvgRatio(String cpuAvgRatio) {
        this.cpuAvgRatio = cpuAvgRatio;
        return this;
    }

    public String getCpuMaxRatio() {
        return cpuMaxRatio;
    }

    public ResourceRecover setCpuMaxRatio(String cpuMaxRatio) {
        this.cpuMaxRatio = cpuMaxRatio;
        return this;
    }

    public String getMemoryAvgRatio() {
        return memoryAvgRatio;
    }

    public ResourceRecover setMemoryAvgRatio(String memoryAvgRatio) {
        this.memoryAvgRatio = memoryAvgRatio;
        return this;
    }

    public String getMemoryMaxRatio() {
        return memoryMaxRatio;
    }

    public ResourceRecover setMemoryMaxRatio(String memoryMaxRatio) {
        this.memoryMaxRatio = memoryMaxRatio;
        return this;
    }

    public String getCloudDiskAvgRatio() {
        return cloudDiskAvgRatio;
    }

    public ResourceRecover setCloudDiskAvgRatio(String cloudDiskAvgRatio) {
        this.cloudDiskAvgRatio = cloudDiskAvgRatio;
        return this;
    }

    public String getCloudDiskMaxRatio() {
        return cloudDiskMaxRatio;
    }

    public ResourceRecover setCloudDiskMaxRatio(String cloudDiskMaxRatio) {
        this.cloudDiskMaxRatio = cloudDiskMaxRatio;
        return this;
    }

    public Date getImportTime() {
        return importTime;
    }

    public void setImportTime(Date importTime) {
        this.importTime = importTime;
    }

    public String getImportTimeStr() {
        return importTimeStr;
    }

    public ResourceRecover setImportTimeStr(String importTimeStr) {
        this.importTimeStr = importTimeStr;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public ResourceRecover setStatus(Integer status) {
        this.status = status;
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

    public String getCreatorIdCard() {
        return creatorIdCard;
    }

    public ResourceRecover setCreatorIdCard(String creatorIdCard) {
        this.creatorIdCard = creatorIdCard;
        return this;
    }

    public String getRefId() {
        return refId;
    }

    public ResourceRecover setRefId(String refId) {
        this.refId = refId;
        return this;
    }

    public String getApplicantOrgName() {
        return applicantOrgName;
    }

    public void setApplicantOrgName(String applicantOrgName) {
        this.applicantOrgName = applicantOrgName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ResourceRecover{" +
        "id=" + id +
        ", shrinkTime=" + shrinkTime +
        ", shrinkType=" + shrinkType +
        ", availablePartition=" + availablePartition +
        ", policeCategory=" + policeCategory +
        ", manufacturer=" + manufacturer +
        ", instanceUser=" + instanceUser +
        ", applicant=" + applicant +
        ", applicantPhone=" + applicantPhone +
        ", projectName=" + projectName +
        ", applicationName=" + applicationName +
        ", instanceName=" + instanceName +
        ", privateIpAddress=" + privateIpAddress +
        ", instanceCreatedTime=" + instanceCreatedTime +
        ", os=" + os +
        ", specification=" + specification +
        ", cloudHardDisk=" + cloudHardDisk +
        ", beforeShrinkCpu=" + beforeShrinkCpu +
        ", beforeShrinkMemory=" + beforeShrinkMemory +
        ", beforeShrinkDisk=" + beforeShrinkDisk +
        ", afterShrinkCpu=" + afterShrinkCpu +
        ", afterShrinkMemory=" + afterShrinkMemory +
        ", afterShrinkDisk=" + afterShrinkDisk +
        ", cpuAvgRatio=" + cpuAvgRatio +
        ", cpuMaxRatio=" + cpuMaxRatio +
        ", memoryAvgRatio=" + memoryAvgRatio +
        ", memoryMaxRatio=" + memoryMaxRatio +
        ", cloudDiskAvgRatio=" + cloudDiskAvgRatio +
        ", cloudDiskMaxRatio=" + cloudDiskMaxRatio +
        ", importTime=" + importTime +
        ", importTimeStr=" + importTimeStr +
        ", status=" + status +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", creatorIdCard=" + creatorIdCard +
        ", refId=" + refId +
        "}";
    }
}
