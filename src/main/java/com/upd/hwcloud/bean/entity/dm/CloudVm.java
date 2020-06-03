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
@TableName("TB_DM_CLOUD_VM")
public class CloudVm extends Model<CloudVm> {

    private static final long serialVersionUID = 1L;

    @TableField("NAME")
    private String name;

    @TableField("NAME_CN")
    private String nameCn;

    @TableField("VID")
    private String vid;

    @TableField("TYPE")
    private String type;

    @TableField("VCPU_SIZE")
    private Double vcpuSize;

    @TableField("CPU_USAGE")
    private Double cpuUsage;


    //MB
    @TableField("RAM_SIZE")
    private Double ramSize;

    @TableField("EVS_DISK_USAGE")
    private Double evsDiskUsage;

    @TableField("MEMORY_USAGE")
    private Double memoryUsage;


    //GB
    @TableField("DISK_SIZE")
    private Double diskSize;

    @TableField("UPDATE_TIME")
    private String updateTime;

    @TableField("CREATE_TIME")
    private String createTime;

    @TableField("CREATE_DATE")
    private String createDate;

    @TableField("TIME_STAMP")
    private Long timeStamp;

    @TableField("TENANT")
    private String tenant;

    @TableField("SITE_NAME")
    private String siteName;

    @TableField("SITE_TYPE")
    private String siteType;

    @TableField("CI_STATUS")
    private String ciStatus;

    @TableField("COMMENTS")
    private String comments;

    @TableField("IP_ADDRESS")
    private String ipAddress;

    @TableField("OS_VERSION")
    private String osVersion;

    @TableField("CLOUD1")
    private String cloud1;

    @TableField("CLOUD2")
    private String cloud2;

    @TableField("BUSINESS_UNIT1")
    private String businessUnit1;

    @TableField("BUSINESS_UNIT2")
    private String businessUnit2;

    @TableField("ODS_IP")
    private String odsIp;

    @TableField(exist = false)
    private Double cpuMaxUsage;

    @TableField(exist = false)
    private Double memMaxUsage;


    public String getName() {
        return name;
    }

    public CloudVm setName(String name) {
        this.name = name;
        return this;
    }

    public String getNameCn() {
        return nameCn;
    }

    public CloudVm setNameCn(String nameCn) {
        this.nameCn = nameCn;
        return this;
    }

    public String getVid() {
        return vid;
    }

    public CloudVm setVid(String vid) {
        this.vid = vid;
        return this;
    }

    public String getType() {
        return type;
    }

    public CloudVm setType(String type) {
        this.type = type;
        return this;
    }

    public Double getVcpuSize() {
        return vcpuSize;
    }

    public CloudVm setVcpuSize(Double vcpuSize) {
        this.vcpuSize = vcpuSize;
        return this;
    }

    public Double getCpuUsage() {
        return cpuUsage;
    }

    public CloudVm setCpuUsage(Double cpuUsage) {
        this.cpuUsage = cpuUsage;
        return this;
    }

    public Double getRamSize() {
        return ramSize;
    }

    public CloudVm setRamSize(Double ramSize) {
        this.ramSize = ramSize;
        return this;
    }

    public Double getEvsDiskUsage() {
        return evsDiskUsage;
    }

    public CloudVm setEvsDiskUsage(Double evsDiskUsage) {
        this.evsDiskUsage = evsDiskUsage;
        return this;
    }

    public Double getMemoryUsage() {
        return memoryUsage;
    }

    public CloudVm setMemoryUsage(Double memoryUsage) {
        this.memoryUsage = memoryUsage;
        return this;
    }

    public Double getDiskSize() {
        return diskSize;
    }

    public CloudVm setDiskSize(Double diskSize) {
        this.diskSize = diskSize;
        return this;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public CloudVm setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public CloudVm setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getCreateDate() {
        return createDate;
    }

    public CloudVm setCreateDate(String createDate) {
        this.createDate = createDate;
        return this;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public CloudVm setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public String getTenant() {
        return tenant;
    }

    public CloudVm setTenant(String tenant) {
        this.tenant = tenant;
        return this;
    }

    public String getSiteName() {
        return siteName;
    }

    public CloudVm setSiteName(String siteName) {
        this.siteName = siteName;
        return this;
    }

    public String getSiteType() {
        return siteType;
    }

    public CloudVm setSiteType(String siteType) {
        this.siteType = siteType;
        return this;
    }

    public String getCiStatus() {
        return ciStatus;
    }

    public CloudVm setCiStatus(String ciStatus) {
        this.ciStatus = ciStatus;
        return this;
    }

    public String getComments() {
        return comments;
    }

    public CloudVm setComments(String comments) {
        this.comments = comments;
        return this;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public CloudVm setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public CloudVm setOsVersion(String osVersion) {
        this.osVersion = osVersion;
        return this;
    }

    public String getCloud1() {
        return cloud1;
    }

    public CloudVm setCloud1(String cloud1) {
        this.cloud1 = cloud1;
        return this;
    }

    public String getCloud2() {
        return cloud2;
    }

    public CloudVm setCloud2(String cloud2) {
        this.cloud2 = cloud2;
        return this;
    }

    public String getBusinessUnit1() {
        return businessUnit1;
    }

    public CloudVm setBusinessUnit1(String businessUnit1) {
        this.businessUnit1 = businessUnit1;
        return this;
    }

    public String getBusinessUnit2() {
        return businessUnit2;
    }

    public CloudVm setBusinessUnit2(String businessUnit2) {
        this.businessUnit2 = businessUnit2;
        return this;
    }

    public String getOdsIp() {
        return odsIp;
    }

    public CloudVm setOdsIp(String odsIp) {
        this.odsIp = odsIp;
        return this;
    }

    public Double getCpuMaxUsage() {
        return cpuMaxUsage;
    }

    public CloudVm setCpuMaxUsage(Double cpuMaxUsage) {
        this.cpuMaxUsage = cpuMaxUsage;
        return this;
    }

    public Double getMemMaxUsage() {
        return memMaxUsage;
    }

    public CloudVm setMemMaxUsage(Double memMaxUsage) {
        this.memMaxUsage = memMaxUsage;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "CloudVm{" +
        "name=" + name +
        ", nameCn=" + nameCn +
        ", vid=" + vid +
        ", type=" + type +
        ", vcpuSize=" + vcpuSize +
        ", cpuUsage=" + cpuUsage +
        ", ramSize=" + ramSize +
        ", evsDiskUsage=" + evsDiskUsage +
        ", memoryUsage=" + memoryUsage +
        ", diskSize=" + diskSize +
        ", updateTime=" + updateTime +
        ", createTime=" + createTime +
        ", createDate=" + createDate +
        ", timeStamp=" + timeStamp +
        ", tenant=" + tenant +
        ", siteName=" + siteName +
        ", siteType=" + siteType +
        ", ciStatus=" + ciStatus +
        ", comments=" + comments +
        ", ipAddress=" + ipAddress +
        ", osVersion=" + osVersion +
        ", cloud1=" + cloud1 +
        ", cloud2=" + cloud2 +
        ", businessUnit1=" + businessUnit1 +
        ", businessUnit2=" + businessUnit2 +
        ", odsIp=" + odsIp +
        ", cpuMaxUsage=" + cpuMaxUsage +
        ", memMaxUsage=" + memMaxUsage +
        "}";
    }
}
