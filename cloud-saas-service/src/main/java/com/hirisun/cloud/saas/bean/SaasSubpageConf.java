package com.hirisun.cloud.saas.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * SAAS服务资源配置
 * </p>
 *
 * @author wuc
 * @since 2019-08-06
 */
@TableName("TB_SAAS_SUBPAGE_CONF")
public class SaasSubpageConf extends Model<SaasSubpageConf> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 服务类型id
     */
         @TableField("MASTER_ID")
    private String masterId;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

        /**
     * 使用总数
     */
         @TableField("USE_COUNT")
    private String useCount;

        /**
     * 用户总数
     */
         @TableField("USER_COUNT")
    private String userCount;

        /**
     * 使用警种总数
     */
         @TableField("POLICE_CATEGORY_COUNT")
    private String policeCategoryCount;

        /**
     * 使用地市总数
     */
         @TableField("AREA_COUNT")
    private String areaCount;

        /**
     * 应用状态
     */
         @TableField("STATUS")
    private String status;


    public String getId() {
        return id;
    }

    public SaasSubpageConf setId(String id) {
        this.id = id;
        return this;
    }

    public String getMasterId() {
        return masterId;
    }

    public SaasSubpageConf setMasterId(String masterId) {
        this.masterId = masterId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public SaasSubpageConf setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public SaasSubpageConf setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getUseCount() {
        return useCount;
    }

    public SaasSubpageConf setUseCount(String useCount) {
        this.useCount = useCount;
        return this;
    }

    public String getUserCount() {
        return userCount;
    }

    public SaasSubpageConf setUserCount(String userCount) {
        this.userCount = userCount;
        return this;
    }

    public String getPoliceCategoryCount() {
        return policeCategoryCount;
    }

    public SaasSubpageConf setPoliceCategoryCount(String policeCategoryCount) {
        this.policeCategoryCount = policeCategoryCount;
        return this;
    }

    public String getAreaCount() {
        return areaCount;
    }

    public SaasSubpageConf setAreaCount(String areaCount) {
        this.areaCount = areaCount;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public SaasSubpageConf setStatus(String status) {
        this.status = status;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SaasSubpageConf{" +
        "id=" + id +
        ", masterId=" + masterId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", useCount=" + useCount +
        ", userCount=" + userCount +
        ", policeCategoryCount=" + policeCategoryCount +
        ", areaCount=" + areaCount +
        ", status=" + status +
        "}";
    }
}
