package com.hirisun.cloud.saas.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * SaaS申请原始信息扩展表——可视化建模平台资源信息
 * </p>
 *
 * @author xqp
 * @since 2020-07-21
 */
@TableName("TB_SAAS_APP_EXT_RESOURCE")
public class SaasAppExtResource extends Model<SaasAppExtResource> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 申请信息 id
     */
         @TableField("MASTER_ID")
    private String masterId;

        /**
     * 目录中文名称
     */
         @TableField("FOLDER_NAME")
    private String folderName;

        /**
     * 数据资源中文名称
     */
         @TableField("RESOURCE_NAME")
    private String resourceName;

    @TableField(value = "CREATE_TIME",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME",fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    /**
     * 数据资源id
     */
    @TableField("RESOURCE_ID")
    private String resourceId;

    public String getId() {
        return id;
    }

    public SaasAppExtResource setId(String id) {
        this.id = id;
        return this;
    }

    public String getMasterId() {
        return masterId;
    }

    public SaasAppExtResource setMasterId(String masterId) {
        this.masterId = masterId;
        return this;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public SaasAppExtResource setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public SaasAppExtResource setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SaasAppExtResource{" +
        "id=" + id +
        ", masterId=" + masterId +
        ", folderName=" + folderName +
        ", resourceName=" + resourceName +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
