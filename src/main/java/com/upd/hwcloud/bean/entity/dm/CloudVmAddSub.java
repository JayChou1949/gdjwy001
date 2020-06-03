package com.upd.hwcloud.bean.entity.dm;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yyc
 * @since 2019-09-04
 */
@TableName("TB_DM_CLOUD_VM_ADD_SUB")
public class CloudVmAddSub extends Model<CloudVmAddSub> {

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

    @TableField("RAM_SIZE")
    private Double ramSize;

    @TableField("EVS_DISK_USAGE")
    private Double evsDiskUsage;

    @TableField("MEMORY_USAGE")
    private Double memoryUsage;

    @TableField("DISK_SIZE")
    private Double diskSize;

    @TableField("UPDATE_TIME")
    private String updateTime;

    @TableField("CREATE_TIME")
    private Integer createTime;

    @TableField("CREATE_DATE")
    private Integer createDate;

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

    @TableField("CPU_MAX_USAGE")
    private Double cpuMaxUsage;

    @TableField("MEM_MAX_USAGE")
    private Double memMaxUsage;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

    @TableField("DISK_MAX_USAGE")
    private Double diskMaxUsage;

        /**
     * 0:CPU扩容 1:CPU缩容 2:内存扩容 3:内存缩容 4:存储扩容 5:存储缩容
     */
         @TableField("DATA_TYPE")
    private Integer dataType;


    public String getName() {
        return name;
    }

    public CloudVmAddSub setName(String name) {
        this.name = name;
        return this;
    }

    public String getNameCn() {
        return nameCn;
    }

    public CloudVmAddSub setNameCn(String nameCn) {
        this.nameCn = nameCn;
        return this;
    }

    public String getVid() {
        return vid;
    }

    public CloudVmAddSub setVid(String vid) {
        this.vid = vid;
        return this;
    }

    public String getType() {
        return type;
    }

    public CloudVmAddSub setType(String type) {
        this.type = type;
        return this;
    }

    public Double getVcpuSize() {
        return vcpuSize;
    }

    public CloudVmAddSub setVcpuSize(Double vcpuSize) {
        this.vcpuSize = vcpuSize;
        return this;
    }

    public Double getCpuUsage() {
        return cpuUsage;
    }

    public CloudVmAddSub setCpuUsage(Double cpuUsage) {
        this.cpuUsage = cpuUsage;
        return this;
    }

    public Double getRamSize() {
        return ramSize;
    }

    public CloudVmAddSub setRamSize(Double ramSize) {
        this.ramSize = ramSize;
        return this;
    }

    public Double getEvsDiskUsage() {
        return evsDiskUsage;
    }

    public CloudVmAddSub setEvsDiskUsage(Double evsDiskUsage) {
        this.evsDiskUsage = evsDiskUsage;
        return this;
    }

    public Double getMemoryUsage() {
        return memoryUsage;
    }

    public CloudVmAddSub setMemoryUsage(Double memoryUsage) {
        this.memoryUsage = memoryUsage;
        return this;
    }

    public Double getDiskSize() {
        return diskSize;
    }

    public CloudVmAddSub setDiskSize(Double diskSize) {
        this.diskSize = diskSize;
        return this;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public CloudVmAddSub setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Integer createDate) {
        this.createDate = createDate;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public CloudVmAddSub setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public String getTenant() {
        return tenant;
    }

    public CloudVmAddSub setTenant(String tenant) {
        this.tenant = tenant;
        return this;
    }

    public String getSiteName() {
        return siteName;
    }

    public CloudVmAddSub setSiteName(String siteName) {
        this.siteName = siteName;
        return this;
    }

    public String getSiteType() {
        return siteType;
    }

    public CloudVmAddSub setSiteType(String siteType) {
        this.siteType = siteType;
        return this;
    }

    public String getCiStatus() {
        return ciStatus;
    }

    public CloudVmAddSub setCiStatus(String ciStatus) {
        this.ciStatus = ciStatus;
        return this;
    }

    public String getComments() {
        return comments;
    }

    public CloudVmAddSub setComments(String comments) {
        this.comments = comments;
        return this;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public CloudVmAddSub setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public CloudVmAddSub setOsVersion(String osVersion) {
        this.osVersion = osVersion;
        return this;
    }

    public String getCloud1() {
        return cloud1;
    }

    public CloudVmAddSub setCloud1(String cloud1) {
        this.cloud1 = cloud1;
        return this;
    }

    public String getCloud2() {
        return cloud2;
    }

    public CloudVmAddSub setCloud2(String cloud2) {
        this.cloud2 = cloud2;
        return this;
    }

    public String getBusinessUnit1() {
        return businessUnit1;
    }

    public CloudVmAddSub setBusinessUnit1(String businessUnit1) {
        this.businessUnit1 = businessUnit1;
        return this;
    }

    public String getBusinessUnit2() {
        return businessUnit2;
    }

    public CloudVmAddSub setBusinessUnit2(String businessUnit2) {
        this.businessUnit2 = businessUnit2;
        return this;
    }

    public String getOdsIp() {
        return odsIp;
    }

    public CloudVmAddSub setOdsIp(String odsIp) {
        this.odsIp = odsIp;
        return this;
    }

    public Double getCpuMaxUsage() {
        return cpuMaxUsage;
    }

    public CloudVmAddSub setCpuMaxUsage(Double cpuMaxUsage) {
        this.cpuMaxUsage = cpuMaxUsage;
        return this;
    }

    public Double getMemMaxUsage() {
        return memMaxUsage;
    }

    public CloudVmAddSub setMemMaxUsage(Double memMaxUsage) {
        this.memMaxUsage = memMaxUsage;
        return this;
    }

    public String  getId() {
        return id;
    }

    public CloudVmAddSub setId(String id) {
        this.id = id;
        return this;
    }

    public Double getDiskMaxUsage() {
        return diskMaxUsage;
    }

    public CloudVmAddSub setDiskMaxUsage(Double diskMaxUsage) {
        this.diskMaxUsage = diskMaxUsage;
        return this;
    }

    public Integer getDataType() {
        return dataType;
    }

    public CloudVmAddSub setDataType(Integer dataType) {
        this.dataType = dataType;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CloudVmAddSub{" +
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
        ", id=" + id +
        ", diskMaxUsage=" + diskMaxUsage +
        ", dataType=" + dataType +
        "}";
    }
}
