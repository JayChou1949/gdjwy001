package com.hirisun.cloud.iaas.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 对象存储实施信息扩展表
 * </p>
 *
 * @author wuc
 * @since 2019-10-16
 */
@TableName("TB_IAAS_DXCC_IMPL_EXT")
public class IaasDxccImplExt extends Model<IaasDxccImplExt> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 应用组件
     */
         @TableField("COMPONENT")
    private String component;

        /**
     * 申请容量
     */
         @TableField("APPLY_STORAGE")
    private String applyStorage;

        /**
     * 组件描述
     */
         @TableField("DESCRIPTION")
    private String description;

        /**
     * 容量
     */
         @TableField("STORAGE")
    private String storage;

        /**
     * 桶名称
     */
         @TableField("BUCKET_NAME")
    private String bucketName;

        /**
     * 申请信息 id
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;


    public String getId() {
        return id;
    }

    public IaasDxccImplExt setId(String id) {
        this.id = id;
        return this;
    }

    public String getComponent() {
        return component;
    }

    public IaasDxccImplExt setComponent(String component) {
        this.component = component;
        return this;
    }

    public String getApplyStorage() {
        return applyStorage;
    }

    public IaasDxccImplExt setApplyStorage(String applyStorage) {
        this.applyStorage = applyStorage;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public IaasDxccImplExt setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getStorage() {
        return storage;
    }

    public IaasDxccImplExt setStorage(String storage) {
        this.storage = storage;
        return this;
    }

    public String getBucketName() {
        return bucketName;
    }

    public IaasDxccImplExt setBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public IaasDxccImplExt setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public IaasDxccImplExt setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public IaasDxccImplExt setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasDxccImplExt{" +
        "id=" + id +
        ", component=" + component +
        ", applyStorage=" + applyStorage +
        ", description=" + description +
        ", storage=" + storage +
        ", bucketName=" + bucketName +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
