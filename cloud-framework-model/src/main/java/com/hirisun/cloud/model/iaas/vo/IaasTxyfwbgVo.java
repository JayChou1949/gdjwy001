package com.hirisun.cloud.model.iaas.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 弹性云服务器变更申请表
 */
public class IaasTxyfwbgVo implements Serializable {

	private static final long serialVersionUID = 1262547660970710422L;

    private String id;

        /**
     * 应用组件
     */
    private String component;

        /**
     * 网络
     */
    private String net;

        /**
     * 部署应用
     */
    private String deployApp;

        /**
     * 虚拟IP
     */
    private String virtualIp;

        /**
     * 账号
     */
    private String account;

        /**
     * 密码
     */
    private String password;

        /**
     * 变更原因
     */
    private String reason;

        /**
     * 变更后状态
     */
    private String changeStatus;

        /**
     * 变更前内存
     */
    private String beforRam;

        /**
     * 变更前数据盘存储
     */
    private String beforStorage;

        /**
     * 变更前操作系统
     */
    private String beforOs;

        /**
     * 变更后内存
     */
    private String ram;

        /**
     * 变更后数据盘存储
     */
    private String storage;

        /**
     * 变更后操作系统
     */
    private String os;

        /**
     * 申请服务信息id
     */
    private String appInfoId;

    private Date createTime;

    private Date modifiedTime;


    public String getId() {
        return id;
    }

    public IaasTxyfwbgVo setId(String id) {
        this.id = id;
        return this;
    }

    public String getComponent() {
        return component;
    }

    public IaasTxyfwbgVo setComponent(String component) {
        this.component = component;
        return this;
    }

    public String getNet() {
        return net;
    }

    public IaasTxyfwbgVo setNet(String net) {
        this.net = net;
        return this;
    }

    public String getDeployApp() {
        return deployApp;
    }

    public IaasTxyfwbgVo setDeployApp(String deployApp) {
        this.deployApp = deployApp;
        return this;
    }

    public String getVirtualIp() {
        return virtualIp;
    }

    public IaasTxyfwbgVo setVirtualIp(String virtualIp) {
        this.virtualIp = virtualIp;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public IaasTxyfwbgVo setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public IaasTxyfwbgVo setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public IaasTxyfwbgVo setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public String getChangeStatus() {
        return changeStatus;
    }

    public IaasTxyfwbgVo setChangeStatus(String changeStatus) {
        this.changeStatus = changeStatus;
        return this;
    }

    public String getBeforRam() {
        return beforRam;
    }

    public IaasTxyfwbgVo setBeforRam(String beforRam) {
        this.beforRam = beforRam;
        return this;
    }

    public String getBeforStorage() {
        return beforStorage;
    }

    public IaasTxyfwbgVo setBeforStorage(String beforStorage) {
        this.beforStorage = beforStorage;
        return this;
    }

    public String getBeforOs() {
        return beforOs;
    }

    public IaasTxyfwbgVo setBeforOs(String beforOs) {
        this.beforOs = beforOs;
        return this;
    }

    public String getRam() {
        return ram;
    }

    public IaasTxyfwbgVo setRam(String ram) {
        this.ram = ram;
        return this;
    }

    public String getStorage() {
        return storage;
    }

    public IaasTxyfwbgVo setStorage(String storage) {
        this.storage = storage;
        return this;
    }

    public String getOs() {
        return os;
    }

    public IaasTxyfwbgVo setOs(String os) {
        this.os = os;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public IaasTxyfwbgVo setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public IaasTxyfwbgVo setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public IaasTxyfwbgVo setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

}
