package com.hirisun.cloud.order.bean.paas;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * PaaS 数据库申请信息
 */
@TableName("TB_PAAS_DATABASE")
public class PaasDatabase extends Model<PaasDatabase> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 应用组件
     */
         @TableField("COMPONENT")
    private String component;

        /**
     * 版本号
     */
         @TableField("VERSION")
    private String version;

        /**
     * 已有Hadoop,HBase,Hive,Namespace权限
     */
         @TableField("AUTHORIZED")
    private String authorized;

        /**
     * 是否新建Hadoop,HBase,Hive,Namespace
     */
         @TableField("CREATE_NEW")
    private String createNew;

        /**
     * cpu(核)
     */
         @TableField("CPU")
    private String cpu;

        /**
     * 内存(GB)
     */
         @TableField("RAM")
    private String ram;

        /**
     * 存储(TB)
     */
         @TableField("STORAGE")
    private String storage;

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

    public PaasDatabase setId(String id) {
        this.id = id;
        return this;
    }

    public String getComponent() {
        return component;
    }

    public PaasDatabase setComponent(String component) {
        this.component = component;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public PaasDatabase setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getAuthorized() {
        return authorized;
    }

    public PaasDatabase setAuthorized(String authorized) {
        this.authorized = authorized;
        return this;
    }

    public String getCreateNew() {
        return createNew;
    }

    public PaasDatabase setCreateNew(String createNew) {
        this.createNew = createNew;
        return this;
    }

    public String getCpu() {
        return cpu;
    }

    public PaasDatabase setCpu(String cpu) {
        this.cpu = cpu;
        return this;
    }

    public String getRam() {
        return ram;
    }

    public PaasDatabase setRam(String ram) {
        this.ram = ram;
        return this;
    }

    public String getStorage() {
        return storage;
    }

    public PaasDatabase setStorage(String storage) {
        this.storage = storage;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public PaasDatabase setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public PaasDatabase setAppInfoId(String appInfoId) {
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

    public PaasDatabase setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public PaasDatabase setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasDatabase{" +
        "id=" + id +
        ", component=" + component +
        ", version=" + version +
        ", authorized=" + authorized +
        ", createNew=" + createNew +
        ", cpu=" + cpu +
        ", ram=" + ram +
        ", storage=" + storage +
        ", account=" + account +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
