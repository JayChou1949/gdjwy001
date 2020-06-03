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
@TableName("TB_DM_CLOUD_RESOURCE_LEVEL2")
public class CloudResourceLevel2 extends Model<CloudResourceLevel2> {

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

    @TableField("DISPLAY")
    private Integer display;


    public Integer getAppNum() {
        return appNum;
    }

    public void setAppNum(Integer appNum) {
        this.appNum = appNum;
    }

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    public String getName() {
        return name;
    }

    public CloudResourceLevel2 setName(String name) {
        this.name = name;
        return this;
    }

    public String getNameCn() {
        return nameCn;
    }

    public CloudResourceLevel2 setNameCn(String nameCn) {
        this.nameCn = nameCn;
        return this;
    }

    public String getLv() {
        return lv;
    }

    public CloudResourceLevel2 setLv(String lv) {
        this.lv = lv;
        return this;
    }

    public String getType() {
        return type;
    }

    public CloudResourceLevel2 setType(String type) {
        this.type = type;
        return this;
    }

    public String getCreateDate() {
        return createDate;
    }

    public CloudResourceLevel2 setCreateDate(String createDate) {
        this.createDate = createDate;
        return this;
    }

    public String getCloud() {
        return cloud;
    }

    public CloudResourceLevel2 setCloud(String cloud) {
        this.cloud = cloud;
        return this;
    }

    public Double getCloudNumber() {
        return cloudNumber;
    }

    public CloudResourceLevel2 setCloudNumber(Double cloudNumber) {
        this.cloudNumber = cloudNumber;
        return this;
    }

    public Double getBusinessregionNumber() {
        return businessregionNumber;
    }

    public CloudResourceLevel2 setBusinessregionNumber(Double businessregionNumber) {
        this.businessregionNumber = businessregionNumber;
        return this;
    }

    public Double getPhysicalhostNumber() {
        return physicalhostNumber;
    }

    public CloudResourceLevel2 setPhysicalhostNumber(Double physicalhostNumber) {
        this.physicalhostNumber = physicalhostNumber;
        return this;
    }

    public Double getStoragepoolNumber() {
        return storagepoolNumber;
    }

    public CloudResourceLevel2 setStoragepoolNumber(Double storagepoolNumber) {
        this.storagepoolNumber = storagepoolNumber;
        return this;
    }

    public Double getVmNumber() {
        return vmNumber;
    }

    public CloudResourceLevel2 setVmNumber(Double vmNumber) {
        this.vmNumber = vmNumber;
        return this;
    }

    public Double getVcpuTotal() {
        return vcpuTotal;
    }

    public CloudResourceLevel2 setVcpuTotal(Double vcpuTotal) {
        this.vcpuTotal = vcpuTotal;
        return this;
    }

    public Double getVcpuRatio() {
        return vcpuRatio;
    }

    public CloudResourceLevel2 setVcpuRatio(Double vcpuRatio) {
        this.vcpuRatio = vcpuRatio;
        return this;
    }

    public Double getVcpuUsage() {
        return vcpuUsage;
    }

    public CloudResourceLevel2 setVcpuUsage(Double vcpuUsage) {
        this.vcpuUsage = vcpuUsage;
        return this;
    }

    public Double getMemoryTotal() {
        return memoryTotal;
    }

    public CloudResourceLevel2 setMemoryTotal(Double memoryTotal) {
        this.memoryTotal = memoryTotal;
        return this;
    }

    public Double getMemoryRatio() {
        return memoryRatio;
    }

    public CloudResourceLevel2 setMemoryRatio(Double memoryRatio) {
        this.memoryRatio = memoryRatio;
        return this;
    }

    public Double getMemoryUsage() {
        return memoryUsage;
    }

    public CloudResourceLevel2 setMemoryUsage(Double memoryUsage) {
        this.memoryUsage = memoryUsage;
        return this;
    }

    public Double getStorageTotal() {
        return storageTotal;
    }

    public CloudResourceLevel2 setStorageTotal(Double storageTotal) {
        this.storageTotal = storageTotal;
        return this;
    }

    public Double getStorageRatio() {
        return storageRatio;
    }

    public CloudResourceLevel2 setStorageRatio(Double storageRatio) {
        this.storageRatio = storageRatio;
        return this;
    }

    public Double getStorageUsage() {
        return storageUsage;
    }

    public CloudResourceLevel2 setStorageUsage(Double storageUsage) {
        this.storageUsage = storageUsage;
        return this;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public CloudResourceLevel2 setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public CloudResourceLevel2 setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
        return this;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public CloudResourceLevel2 setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public String getSiteName() {
        return siteName;
    }

    public CloudResourceLevel2 setSiteName(String siteName) {
        this.siteName = siteName;
        return this;
    }

    public String getSiteType() {
        return siteType;
    }

    public CloudResourceLevel2 setSiteType(String siteType) {
        this.siteType = siteType;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }


    @Override
    public String toString() {
        return "CloudResourceLevel2{" +
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
                ", display=" + display +
                '}';
    }
}
