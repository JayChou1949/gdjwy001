package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 数据接入表
 * </p>
 *
 * @author huru
 * @since 2019-03-13
 */
@TableName("TB_DATA_ACCESS")
public class DataAccess extends Model<DataAccess> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    /**
     * 创建人
     */
    @TableField("CREATOR")
    private String creator;

    /**
     * 发起人
     */
    @TableField("ORIGINATOR")
    private String originator;

    /**
     * 发起时间
     */
    @TableField("LAUNCH_TIME")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date launchTime;

    /**
     * 发起人所属单位
     */
    @TableField("ORIGINATOR_ORG")
    private String originatorOrg;

    /**
     * 发起人手机号码
     */
    @TableField("ORIGINATOR_PHONE")
    private String originatorPhone;

    /**
     * 需接入资源名称
     */
    @TableField("RESOURCES_NAME")
    private String resourcesName;

    /**
     * 资源所属网域
     */
    @TableField("RESOURCES_DOMAIN")
    private String resourcesDomain;

    /**
     * 资源所属行业
     */
    @TableField("RESOURCES_INDUSTRY")
    private String resourcesIndustry;

    /**
     * 资源所属警种
     */
    @TableField("RESOURCES_POLICE")
    private String resourcesPolice;

    /**
     * 资源所属单位
     */
    @TableField("RESOURCES_ORG")
    private String resourcesOrg;

    /**
     * 更新频率要求
     */
    @TableField("UPDATE_FREQUENCY")
    private String updateFrequency;

    /**
     * 是否需要历史数据 0.是 1.否
     */
    @TableField("NEED_HISTORY")
    private Integer needHistory;

    /**
     * 数据资源描述
     */
    @TableField("DATA_RESOURCES")
    private String dataResources;

    /**
     * 需求场景描述
     */
    @TableField("DEMAND")
    private String demand;

    /**
     * 需求紧急程度
     */
    @TableField("URGENT")
    private String urgent;

    /**
     * 期望提供时间
     */
    @TableField("HOPE_TIME")
    private String hopeTime;


    public String getId() {
        return id;
    }

    public DataAccess setId(String id) {
        this.id = id;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public DataAccess setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public DataAccess setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public DataAccess setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public String getOriginator() {
        return originator;
    }

    public DataAccess setOriginator(String originator) {
        this.originator = originator;
        return this;
    }

    public Date getLaunchTime() {
        return launchTime;
    }

    public DataAccess setLaunchTime(Date launchTime) {
        this.launchTime = launchTime;
        return this;
    }

    public String getOriginatorOrg() {
        return originatorOrg;
    }

    public DataAccess setOriginatorOrg(String originatorOrg) {
        this.originatorOrg = originatorOrg;
        return this;
    }

    public String getOriginatorPhone() {
        return originatorPhone;
    }

    public DataAccess setOriginatorPhone(String originatorPhone) {
        this.originatorPhone = originatorPhone;
        return this;
    }

    public String getResourcesName() {
        return resourcesName;
    }

    public DataAccess setResourcesName(String resourcesName) {
        this.resourcesName = resourcesName;
        return this;
    }

    public String getResourcesDomain() {
        return resourcesDomain;
    }

    public DataAccess setResourcesDomain(String resourcesDomain) {
        this.resourcesDomain = resourcesDomain;
        return this;
    }

    public String getResourcesIndustry() {
        return resourcesIndustry;
    }

    public DataAccess setResourcesIndustry(String resourcesIndustry) {
        this.resourcesIndustry = resourcesIndustry;
        return this;
    }

    public String getResourcesPolice() {
        return resourcesPolice;
    }

    public DataAccess setResourcesPolice(String resourcesPolice) {
        this.resourcesPolice = resourcesPolice;
        return this;
    }

    public String getResourcesOrg() {
        return resourcesOrg;
    }

    public DataAccess setResourcesOrg(String resourcesOrg) {
        this.resourcesOrg = resourcesOrg;
        return this;
    }

    public String getUpdateFrequency() {
        return updateFrequency;
    }

    public DataAccess setUpdateFrequency(String updateFrequency) {
        this.updateFrequency = updateFrequency;
        return this;
    }

    public Integer getNeedHistory() {
        return needHistory;
    }

    public DataAccess setNeedHistory(Integer needHistory) {
        this.needHistory = needHistory;
        return this;
    }

    public String getDataResources() {
        return dataResources;
    }

    public DataAccess setDataResources(String dataResources) {
        this.dataResources = dataResources;
        return this;
    }

    public String getDemand() {
        return demand;
    }

    public DataAccess setDemand(String demand) {
        this.demand = demand;
        return this;
    }

    public String getUrgent() {
        return urgent;
    }

    public DataAccess setUrgent(String urgent) {
        this.urgent = urgent;
        return this;
    }

    public String getHopeTime() {
        return hopeTime;
    }

    public DataAccess setHopeTime(String hopeTime) {
        this.hopeTime = hopeTime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "DataAccess{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                ", creator=" + creator +
                ", originator=" + originator +
                ", launchTime=" + launchTime +
                ", originatorOrg=" + originatorOrg +
                ", originatorPhone=" + originatorPhone +
                ", resourcesName=" + resourcesName +
                ", resourcesDomain=" + resourcesDomain +
                ", resourcesIndustry=" + resourcesIndustry +
                ", resourcesPolice=" + resourcesPolice +
                ", resourcesOrg=" + resourcesOrg +
                ", updateFrequency=" + updateFrequency +
                ", needHistory=" + needHistory +
                ", dataResources=" + dataResources +
                ", demand=" + demand +
                ", urgent=" + urgent +
                ", hopeTime=" + hopeTime +
                "}";
    }
}
