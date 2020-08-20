package com.hirisun.cloud.model.iaas.vo;

import java.io.Serializable;
import java.util.Date;

public class IaasTxyfwVo implements Serializable {

	private static final long serialVersionUID = -8358171436032748849L;

	private String id;

    /**
     * 应用组件
     */
    private String component;

    /**
     * 规格
     */
    private String specification;

    /**
     * 存储
     */
    private String storage;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 网络
     */
    private String net;

    /**
     * 申请数量
     */
    private Long num;


    /**
     * GPU数目
     */
    private Integer gpuNum;

    /**
     * 部署应用
     */
    private String deployApp;

    /**
     * 申请信息 id
     */
    private String appInfoId;

    private String shoppingCartId;

    /**
     * 应用类型
     */
    private String appType;

    /**
     * 组件描述
     */
    private String componentDesc;

    private Date createTime;

    private Date modifiedTime;


    public String getId() {
        return id;
    }

    public IaasTxyfwVo setId(String id) {
        this.id = id;
        return this;
    }

    public String getComponent() {
        return component;
    }

    public IaasTxyfwVo setComponent(String component) {
        this.component = component;
        return this;
    }

    public String getSpecification() {
        return specification;
    }

    public IaasTxyfwVo setSpecification(String specification) {
        this.specification = specification;
        return this;
    }

    public String getStorage() {
        return storage;
    }

    public IaasTxyfwVo setStorage(String storage) {
        this.storage = storage;
        return this;
    }

    public String getOs() {
        return os;
    }

    public IaasTxyfwVo setOs(String os) {
        this.os = os;
        return this;
    }

    public String getNet() {
        return net;
    }

    public IaasTxyfwVo setNet(String net) {
        this.net = net;
        return this;
    }

    public Long getNum() {
        return num;
    }

    public IaasTxyfwVo setNum(Long num) {
        this.num = num;
        return this;
    }

    public String getDeployApp() {
        return deployApp;
    }

    public IaasTxyfwVo setDeployApp(String deployApp) {
        this.deployApp = deployApp;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public IaasTxyfwVo setAppInfoId(String appInfoId) {
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

    public IaasTxyfwVo setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public IaasTxyfwVo setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getComponentDesc() {
        return componentDesc;
    }

    public void setComponentDesc(String componentDesc) {
        this.componentDesc = componentDesc;
    }

    public Integer getGpuNum() {
        return gpuNum;
    }

    public void setGpuNum(Integer gpuNum) {
        this.gpuNum = gpuNum;
    }

}
