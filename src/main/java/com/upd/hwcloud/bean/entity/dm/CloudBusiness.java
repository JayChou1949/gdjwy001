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
@TableName("TB_DM_CLOUD_BUSINESS")
public class CloudBusiness extends Model<CloudBusiness> {

    private static final long serialVersionUID = 1L;

    @TableField("NAME")
    private String name;

    @TableField("NAME_CN")
    private String nameCn;

    @TableField("TYPE")
    private String type;

    @TableField("LV")
    private String lv;

    @TableField("CREATE_DATE")
    private String createDate;

    @TableField("CLOUD")
    private String cloud;

    @TableField("BUSINESS_NUMBER")
    private Double businessNumber;

    @TableField("VM_NUMBER")
    private Double vmNumber;

    @TableField("VCPU_TOTAL")
    private Double vcpuTotal;

    @TableField("VCPU_USAGE")
    private Double vcpuUsage;

    @TableField("MEMEORY_TOTAL")
    private Double memeoryTotal;

    @TableField("MEMEORY_USAGE")
    private Double memeoryUsage;

    @TableField("DISK_TOTAL")
    private Double diskTotal;

    @TableField("EVS_DISK_USAGE")
    private Double evsDiskUsage;

    @TableField("UPDATE_TIME")
    private String updateTime;

    @TableField("TIME_STAMP")
    private Long timeStamp;

    @TableField("TENANT")
    private String tenant;

    @TableField("SITE_NAME")
    private String siteName;

    @TableField("SITE_TYPE")
    private String siteType;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameCn() {
        return nameCn;
    }

    public CloudBusiness setNameCn(String nameCn) {
        this.nameCn = nameCn;
        return this;
    }

    public String getType() {
        return type;
    }

    public CloudBusiness setType(String type) {
        this.type = type;
        return this;
    }

    public String getLv() {
        return lv;
    }

    public CloudBusiness setLv(String lv) {
        this.lv = lv;
        return this;
    }

    public String getCreateDate() {
        return createDate;
    }

    public CloudBusiness setCreateDate(String createDate) {
        this.createDate = createDate;
        return this;
    }

    public String getCloud() {
        return cloud;
    }

    public CloudBusiness setCloud(String cloud) {
        this.cloud = cloud;
        return this;
    }

    public Double getBusinessNumber() {
        return businessNumber;
    }

    public CloudBusiness setBusinessNumber(Double businessNumber) {
        this.businessNumber = businessNumber;
        return this;
    }

    public Double getVmNumber() {
        return vmNumber;
    }

    public CloudBusiness setVmNumber(Double vmNumber) {
        this.vmNumber = vmNumber;
        return this;
    }

    public Double getVcpuTotal() {
        return vcpuTotal;
    }

    public CloudBusiness setVcpuTotal(Double vcpuTotal) {
        this.vcpuTotal = vcpuTotal;
        return this;
    }

    public Double getVcpuUsage() {
        return vcpuUsage;
    }

    public CloudBusiness setVcpuUsage(Double vcpuUsage) {
        this.vcpuUsage = vcpuUsage;
        return this;
    }

    public Double getMemeoryTotal() {
        return memeoryTotal;
    }

    public CloudBusiness setMemeoryTotal(Double memeoryTotal) {
        this.memeoryTotal = memeoryTotal;
        return this;
    }

    public Double getMemeoryUsage() {
        return memeoryUsage;
    }

    public CloudBusiness setMemeoryUsage(Double memeoryUsage) {
        this.memeoryUsage = memeoryUsage;
        return this;
    }

    public Double getDiskTotal() {
        return diskTotal;
    }

    public CloudBusiness setDiskTotal(Double diskTotal) {
        this.diskTotal = diskTotal;
        return this;
    }

    public Double getEvsDiskUsage() {
        return evsDiskUsage;
    }

    public CloudBusiness setEvsDiskUsage(Double evsDiskUsage) {
        this.evsDiskUsage = evsDiskUsage;
        return this;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public CloudBusiness setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public CloudBusiness setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public String getTenant() {
        return tenant;
    }

    public CloudBusiness setTenant(String tenant) {
        this.tenant = tenant;
        return this;
    }

    public String getSiteName() {
        return siteName;
    }

    public CloudBusiness setSiteName(String siteName) {
        this.siteName = siteName;
        return this;
    }

    public String getSiteType() {
        return siteType;
    }

    public CloudBusiness setSiteType(String siteType) {
        this.siteType = siteType;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "CloudBusiness{" +
        "name=" + name +
        ", nameCn=" + nameCn +
        ", type=" + type +
        ", lv=" + lv +
        ", createDate=" + createDate +
        ", cloud=" + cloud +
        ", businessNumber=" + businessNumber +
        ", vmNumber=" + vmNumber +
        ", vcpuTotal=" + vcpuTotal +
        ", vcpuUsage=" + vcpuUsage +
        ", memeoryTotal=" + memeoryTotal +
        ", memeoryUsage=" + memeoryUsage +
        ", diskTotal=" + diskTotal +
        ", evsDiskUsage=" + evsDiskUsage +
        ", updateTime=" + updateTime +
        ", timeStamp=" + timeStamp +
        ", tenant=" + tenant +
        ", siteName=" + siteName +
        ", siteType=" + siteType +
        "}";
    }
}
