package com.upd.hwcloud.bean.entity.dm;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yyc
 * @since 2019-08-13
 */
@TableName("TB_DM_CLOUD_RESOURCE_LEVEL1")
public class CloudResourceLevel1 extends Model<CloudResourceLevel1> {

    private static final long serialVersionUID = 1L;

    @TableField("NAME")
    private String name;

    @TableField("NAME_CN")
    private String nameCn;

    @TableField("LV")
    private String lv;

    @TableField("TYPE")
    private String type;

    @TableField("CREATE_DATE")
    private String createDate;

    @TableField("CLOUD")
    private String cloud;

    @TableField("CLOUD_NUMBER")
    private Double cloudNumber;

    @TableField("BUSINESSREGION_NUMBER")
    private Double businessregionNumber;

    @TableField("PHYSICALHOST_NUMBER")
    private Double physicalhostNumber;

    @TableField("STORAGEPOOL_NUMBER")
    private Double storagepoolNumber;

    @TableField("VM_NUMBER")
    private Double vmNumber;

    @TableField("VCPU_TOTAL")
    private Double vcpuTotal;

    @TableField("VCPU_RATIO")
    private Double vcpuRatio;

    @TableField("VCPU_USAGE")
    private Double vcpuUsage;

    @TableField("MEMORY_TOTAL")
    private Double memoryTotal;

    @TableField("MEMORY_RATIO")
    private Double memoryRatio;

    @TableField("MEMORY_USAGE")
    private Double memoryUsage;

    @TableField("STORAGE_TOTAL")
    private Double storageTotal;

    @TableField("STORAGE_RATIO")
    private Double storageRatio;

    @TableField("STORAGE_USAGE")
    private Double storageUsage;

    @TableField("UPDATE_TIME")
    private String updateTime;

    @TableField("CREATE_TIME_STR")
    private String createTimeStr;

    @TableField("TIME_STAMP")
    private Long timeStamp;

    @TableField("SITE_NAME")
    private String siteName;

    @TableField("SITE_TYPE")
    private String siteType;

    @TableField("APP_NUM")
    private Integer appNum;


    public Integer getAppNum() {
        return appNum;
    }

    public void setAppNum(Integer appNum) {
        this.appNum = appNum;
    }

    public String getName() {
        return name;
    }

    public CloudResourceLevel1 setName(String name) {
        this.name = name;
        return this;
    }

    public String getNameCn() {
        return nameCn;
    }

    public CloudResourceLevel1 setNameCn(String nameCn) {
        this.nameCn = nameCn;
        return this;
    }

    public String getLv() {
        return lv;
    }

    public CloudResourceLevel1 setLv(String lv) {
        this.lv = lv;
        return this;
    }

    public String getType() {
        return type;
    }

    public CloudResourceLevel1 setType(String type) {
        this.type = type;
        return this;
    }

    public String getCreateDate() {
        return createDate;
    }

    public CloudResourceLevel1 setCreateDate(String createDate) {
        this.createDate = createDate;
        return this;
    }

    public String getCloud() {
        return cloud;
    }

    public CloudResourceLevel1 setCloud(String cloud) {
        this.cloud = cloud;
        return this;
    }

    public Double getCloudNumber() {
        return cloudNumber;
    }

    public CloudResourceLevel1 setCloudNumber(Double cloudNumber) {
        this.cloudNumber = cloudNumber;
        return this;
    }

    public Double getBusinessregionNumber() {
        return businessregionNumber;
    }

    public CloudResourceLevel1 setBusinessregionNumber(Double businessregionNumber) {
        this.businessregionNumber = businessregionNumber;
        return this;
    }

    public Double getPhysicalhostNumber() {
        return physicalhostNumber;
    }

    public CloudResourceLevel1 setPhysicalhostNumber(Double physicalhostNumber) {
        this.physicalhostNumber = physicalhostNumber;
        return this;
    }

    public Double getStoragepoolNumber() {
        return storagepoolNumber;
    }

    public CloudResourceLevel1 setStoragepoolNumber(Double storagepoolNumber) {
        this.storagepoolNumber = storagepoolNumber;
        return this;
    }

    public Double getVmNumber() {
        return vmNumber;
    }

    public CloudResourceLevel1 setVmNumber(Double vmNumber) {
        this.vmNumber = vmNumber;
        return this;
    }

    public Double getVcpuTotal() {
        return vcpuTotal;
    }

    public CloudResourceLevel1 setVcpuTotal(Double vcpuTotal) {
        this.vcpuTotal = vcpuTotal;
        return this;
    }

    public Double getVcpuRatio() {
        return vcpuRatio;
    }

    public CloudResourceLevel1 setVcpuRatio(Double vcpuRatio) {
        this.vcpuRatio = vcpuRatio;
        return this;
    }

    public Double getVcpuUsage() {
        return vcpuUsage;
    }

    public CloudResourceLevel1 setVcpuUsage(Double vcpuUsage) {
        this.vcpuUsage = vcpuUsage;
        return this;
    }

    public Double getMemoryTotal() {
        return memoryTotal;
    }

    public CloudResourceLevel1 setMemoryTotal(Double memoryTotal) {
        this.memoryTotal = memoryTotal;
        return this;
    }

    public Double getMemoryRatio() {
        return memoryRatio;
    }

    public CloudResourceLevel1 setMemoryRatio(Double memoryRatio) {
        this.memoryRatio = memoryRatio;
        return this;
    }

    public Double getMemoryUsage() {
        return memoryUsage;
    }

    public CloudResourceLevel1 setMemoryUsage(Double memoryUsage) {
        this.memoryUsage = memoryUsage;
        return this;
    }

    public Double getStorageTotal() {
        return storageTotal;
    }

    public CloudResourceLevel1 setStorageTotal(Double storageTotal) {
        this.storageTotal = storageTotal;
        return this;
    }

    public Double getStorageRatio() {
        return storageRatio;
    }

    public CloudResourceLevel1 setStorageRatio(Double storageRatio) {
        this.storageRatio = storageRatio;
        return this;
    }

    public Double getStorageUsage() {
        return storageUsage;
    }

    public CloudResourceLevel1 setStorageUsage(Double storageUsage) {
        this.storageUsage = storageUsage;
        return this;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public CloudResourceLevel1 setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public CloudResourceLevel1 setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
        return this;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public CloudResourceLevel1 setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public String getSiteName() {
        return siteName;
    }

    public CloudResourceLevel1 setSiteName(String siteName) {
        this.siteName = siteName;
        return this;
    }

    public String getSiteType() {
        return siteType;
    }

    public CloudResourceLevel1 setSiteType(String siteType) {
        this.siteType = siteType;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }


    @Override
    public String toString() {
        return "CloudResourceLevel1{" +
                "name='" + name + '\'' +
                ", nameCn='" + nameCn + '\'' +
                ", lv='" + lv + '\'' +
                ", type='" + type + '\'' +
                ", createDate='" + createDate + '\'' +
                ", cloud='" + cloud + '\'' +
                ", cloudNumber=" + cloudNumber +
                ", businessregionNumber=" + businessregionNumber +
                ", physicalhostNumber=" + physicalhostNumber +
                ", storagepoolNumber=" + storagepoolNumber +
                ", vmNumber=" + vmNumber +
                ", vcpuTotal=" + vcpuTotal +
                ", vcpuRatio=" + vcpuRatio +
                ", vcpuUsage=" + vcpuUsage +
                ", memoryTotal=" + memoryTotal +
                ", memoryRatio=" + memoryRatio +
                ", memoryUsage=" + memoryUsage +
                ", storageTotal=" + storageTotal +
                ", storageRatio=" + storageRatio +
                ", storageUsage=" + storageUsage +
                ", updateTime='" + updateTime + '\'' +
                ", createTimeStr='" + createTimeStr + '\'' +
                ", timeStamp=" + timeStamp +
                ", siteName='" + siteName + '\'' +
                ", siteType='" + siteType + '\'' +
                ", appNum=" + appNum +
                '}';
    }
}
