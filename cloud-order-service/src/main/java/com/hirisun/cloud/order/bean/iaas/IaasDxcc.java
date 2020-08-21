package com.hirisun.cloud.order.bean.iaas;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * IaaS 对象存储申请信息
 * </p>
 *
 * @author wuc
 * @since 2019-01-07
 */
@TableName("TB_IAAS_DXCC")
public class IaasDxcc extends Model<IaasDxcc> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 应用组件
     */
         @TableField("COMPONENT")
    private String component;

        /**
     * 容量大小GB
     */
         @TableField("STORAGE")
    private String storage;

    /**
     * 组件描述
     */
    @TableField("DESCRIPTION")
    private String description;

        /**
     * 申请信息 id
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

         @TableField("SHOPPING_CART_ID")
         private String shoppingCartId;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;


    public String getId() {
        return id;
    }

    public IaasDxcc setId(String id) {
        this.id = id;
        return this;
    }

    public String getComponent() {
        return component;
    }

    public IaasDxcc setComponent(String component) {
        this.component = component;
        return this;
    }

    public String getStorage() {
        return storage;
    }

    public IaasDxcc setStorage(String storage) {
        this.storage = storage;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public IaasDxcc setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public IaasDxcc setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public IaasDxcc setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasDxcc{" +
        "id=" + id +
        ", component=" + component +
        ", storage=" + storage +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
