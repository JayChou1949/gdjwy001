package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * PaaS服务支撑应用信息
 * </p>
 *
 * @author zwb
 * @since 2019-06-11
 */
@TableName("TB_PAAS_ZCYY")
public class PaasZcyy extends Model<PaasZcyy> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 应用名称
     */
        @ApiModelProperty("应用名称")
         @TableField("APP_NAME")
    private String appName;

        /**
     * 所属地区
     */ @ApiModelProperty("所属地区")
         @TableField("AREA")
    private String area;

        /**
     * 所属警种
     */ @ApiModelProperty("所属警种")
        @TableField("POLICE_CATEGORY")
    private String policeCategory;
    @ApiModelProperty("排序")
    @TableField("SORT")
        private Long sort;
    @ApiModelProperty("应用创建时间")
    @TableField("BUILD_DATE")
        private String buildDate;
        /**
     * 服务 id
     */
         @TableField("MASTER_ID")
    private String masterId;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;


    public String getId() {
        return id;
    }

    public PaasZcyy setId(String id) {
        this.id = id;
        return this;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(String buildDate) {
        this.buildDate = buildDate;
    }

    public String getAppName() {
        return appName;
    }

    public PaasZcyy setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    public String getArea() {
        return area;
    }

    public PaasZcyy setArea(String area) {
        this.area = area;
        return this;
    }

    public String getPoliceCategory() {
        return policeCategory;
    }

    public PaasZcyy setPoliceCategory(String policeCategory) {
        this.policeCategory = policeCategory;
        return this;
    }

    public String getMasterId() {
        return masterId;
    }

    public PaasZcyy setMasterId(String masterId) {
        this.masterId = masterId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public PaasZcyy setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public PaasZcyy setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasZcyy{" +
        "id=" + id +
        ", appName=" + appName +
        ", area=" + area +
        ", policeCategory=" + policeCategory +
        ", masterId=" + masterId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
