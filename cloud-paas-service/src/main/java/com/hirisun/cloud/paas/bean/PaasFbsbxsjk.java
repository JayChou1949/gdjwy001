package com.hirisun.cloud.paas.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * 分布式并行数据库申请信息表
 */
@TableName("TB_PAAS_FBSBXSJK")
public class PaasFbsbxsjk extends Model<PaasFbsbxsjk> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 应用组件
     */
         @TableField("COMPONENT")
    private String component;

        /**
     * IP地址
     */
         @TableField("IP")
    private String ip;

        /**
     * 模式名
     */
         @TableField("SCHEMA")
    private String schema;

        /**
     * 数据库英文名
     */
         @TableField("TABLE_NAME_EN")
    private String tableNameEn;

        /**
     * 数据库中文名
     */
         @TableField("TABLE_NAME_CN")
    private String tableNameCn;

        /**
     * 权限描述
     */
         @TableField("AUTHORIZED")
    private String authorized;

        /**
     * 账号
     */
         @TableField("ACCOUNT")
    private String account;

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

    public PaasFbsbxsjk setId(String id) {
        this.id = id;
        return this;
    }

    public String getComponent() {
        return component;
    }

    public PaasFbsbxsjk setComponent(String component) {
        this.component = component;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public PaasFbsbxsjk setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getSchema() {
        return schema;
    }

    public PaasFbsbxsjk setSchema(String schema) {
        this.schema = schema;
        return this;
    }

    public String getTableNameEn() {
        return tableNameEn;
    }

    public PaasFbsbxsjk setTableNameEn(String tableNameEn) {
        this.tableNameEn = tableNameEn;
        return this;
    }

    public String getTableNameCn() {
        return tableNameCn;
    }

    public PaasFbsbxsjk setTableNameCn(String tableNameCn) {
        this.tableNameCn = tableNameCn;
        return this;
    }

    public String getAuthorized() {
        return authorized;
    }

    public PaasFbsbxsjk setAuthorized(String authorized) {
        this.authorized = authorized;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public PaasFbsbxsjk setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public PaasFbsbxsjk setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public PaasFbsbxsjk setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public PaasFbsbxsjk setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasFbsbxsjk{" +
        "id=" + id +
        ", component=" + component +
        ", ip=" + ip +
        ", schema=" + schema +
        ", tableNameEn=" + tableNameEn +
        ", tableNameCn=" + tableNameCn +
        ", authorized=" + authorized +
        ", account=" + account +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
