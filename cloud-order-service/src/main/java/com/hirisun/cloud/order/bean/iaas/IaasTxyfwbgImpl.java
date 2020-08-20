package com.hirisun.cloud.order.bean.iaas;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * 弹性云服务器变更申请实施信息表
 */
@TableName("TB_IAAS_TXYFWBG_IMPL")
public class IaasTxyfwbgImpl extends Model<IaasTxyfwbgImpl> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 应用组件
     */
         @TableField("COMPONENT")
    private String component;

        /**
     * 网络
     */
         @TableField("NET")
    private String net;

        /**
     * 部署应用
     */
         @TableField("DEPLOY_APP")
    private String deployApp;

        /**
     * 虚拟IP
     */
         @TableField("VIRTUAL_IP")
    private String virtualIp;

        /**
     * 账号
     */
         @TableField("ACCOUNT")
    private String account;

        /**
     * 密码
     */
         @TableField("PASSWORD")
    private String password;

        /**
     * 变更原因
     */
         @TableField("REASON")
    private String reason;

        /**
     * 变更后状态
     */
         @TableField("CHANGE_STATUS")
    private String changeStatus;

        /**
     * 变更前内存
     */
         @TableField("BEFOR_RAM")
    private String beforRam;

        /**
     * 变更前数据盘存储
     */
         @TableField("BEFOR_STORAGE")
    private String beforStorage;

        /**
     * 变更前操作系统
     */
         @TableField("BEFOR_OS")
    private String beforOs;

        /**
     * 变更后内存
     */
         @TableField("RAM")
    private String ram;

        /**
     * 变更后数据盘存储
     */
         @TableField("STORAGE")
    private String storage;

        /**
     * 变更后操作系统
     */
         @TableField("OS")
    private String os;

        /**
     * 申请服务信息id
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

    public IaasTxyfwbgImpl setId(String id) {
        this.id = id;
        return this;
    }

    public String getComponent() {
        return component;
    }

    public IaasTxyfwbgImpl setComponent(String component) {
        this.component = component;
        return this;
    }

    public String getNet() {
        return net;
    }

    public IaasTxyfwbgImpl setNet(String net) {
        this.net = net;
        return this;
    }

    public String getDeployApp() {
        return deployApp;
    }

    public IaasTxyfwbgImpl setDeployApp(String deployApp) {
        this.deployApp = deployApp;
        return this;
    }

    public String getVirtualIp() {
        return virtualIp;
    }

    public IaasTxyfwbgImpl setVirtualIp(String virtualIp) {
        this.virtualIp = virtualIp;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public IaasTxyfwbgImpl setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public IaasTxyfwbgImpl setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public IaasTxyfwbgImpl setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public String getChangeStatus() {
        return changeStatus;
    }

    public IaasTxyfwbgImpl setChangeStatus(String changeStatus) {
        this.changeStatus = changeStatus;
        return this;
    }

    public String getBeforRam() {
        return beforRam;
    }

    public IaasTxyfwbgImpl setBeforRam(String beforRam) {
        this.beforRam = beforRam;
        return this;
    }

    public String getBeforStorage() {
        return beforStorage;
    }

    public IaasTxyfwbgImpl setBeforStorage(String beforStorage) {
        this.beforStorage = beforStorage;
        return this;
    }

    public String getBeforOs() {
        return beforOs;
    }

    public IaasTxyfwbgImpl setBeforOs(String beforOs) {
        this.beforOs = beforOs;
        return this;
    }

    public String getRam() {
        return ram;
    }

    public IaasTxyfwbgImpl setRam(String ram) {
        this.ram = ram;
        return this;
    }

    public String getStorage() {
        return storage;
    }

    public IaasTxyfwbgImpl setStorage(String storage) {
        this.storage = storage;
        return this;
    }

    public String getOs() {
        return os;
    }

    public IaasTxyfwbgImpl setOs(String os) {
        this.os = os;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public IaasTxyfwbgImpl setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public IaasTxyfwbgImpl setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public IaasTxyfwbgImpl setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasTxyfwbgImpl{" +
        "id=" + id +
        ", component=" + component +
        ", net=" + net +
        ", deployApp=" + deployApp +
        ", virtualIp=" + virtualIp +
        ", account=" + account +
        ", password=" + password +
        ", reason=" + reason +
        ", changeStatus=" + changeStatus +
        ", beforRam=" + beforRam +
        ", beforStorage=" + beforStorage +
        ", beforOs=" + beforOs +
        ", ram=" + ram +
        ", storage=" + storage +
        ", os=" + os +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        "}";
    }
}
