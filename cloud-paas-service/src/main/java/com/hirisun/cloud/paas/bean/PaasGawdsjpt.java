package com.hirisun.cloud.paas.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 公安网大数据平台资源申请信息
 * </p>
 *
 * @author huru
 * @since 2019-03-26
 */
@TableName("TB_PAAS_GAWDSJPT")
public class PaasGawdsjpt extends Model<PaasGawdsjpt> {

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
     * 目录
     */
         @TableField("PATH")
    private String path;

        /**
     * 申请信息 id
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    @TableField("SHOPPING_CART_ID")
    private String shoppingCartId;

    public String getId() {
        return id;
    }

    public PaasGawdsjpt setId(String id) {
        this.id = id;
        return this;
    }

    public String getComponent() {
        return component;
    }

    public PaasGawdsjpt setComponent(String component) {
        this.component = component;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public PaasGawdsjpt setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getAuthorized() {
        return authorized;
    }

    public PaasGawdsjpt setAuthorized(String authorized) {
        this.authorized = authorized;
        return this;
    }


    public String getCreateNew() {
        return createNew;
    }

    public PaasGawdsjpt setCreateNew(String createNew) {
        this.createNew = createNew;
        return this;
    }

    public String getCpu() {
        return cpu;
    }

    public PaasGawdsjpt setCpu(String cpu) {
        this.cpu = cpu;
        return this;
    }

    public String getRam() {
        return ram;
    }

    public PaasGawdsjpt setRam(String ram) {
        this.ram = ram;
        return this;
    }

    public String getStorage() {
        return storage;
    }

    public PaasGawdsjpt setStorage(String storage) {
        this.storage = storage;
        return this;
    }

    public String getPath() {
        return path;
    }

    public PaasGawdsjpt setPath(String path) {
        this.path = path;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public PaasGawdsjpt setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public PaasGawdsjpt setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public PaasGawdsjpt setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PaasGawdsjpt{" +
        "id=" + id +
        ", component=" + component +
        ", version=" + version +
        ", authorized=" + authorized +
        ", createNew=" + createNew +
        ", cpu=" + cpu +
        ", ram=" + ram +
        ", storage=" + storage +
        ", path=" + path +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
