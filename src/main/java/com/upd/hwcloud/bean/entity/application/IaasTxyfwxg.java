package com.upd.hwcloud.bean.entity.application;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.upd.hwcloud.common.utils.annotation.ExcelExplain;
import cn.afterturn.easypoi.excel.annotation.Excel;
import java.time.LocalDateTime;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangwy
 * @since 2019-09-11
 */
@TableName("TB_IAAS_TXYFWXG")
public class IaasTxyfwxg extends Model<IaasTxyfwxg> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

    /**
     * 申请信息 id
     */
    @TableField("APP_INFO_ID")
    private String appInfoId;


    @TableField("SHOPPING_CART_ID")
    private String shoppingCartId;

    /**
     * 虚拟机IP
     */
    @Excel(name = "虚拟机IP")
    @TableField("SERVER_IP")
    private String serverIp;

    /**
     * 网络类型
     */
    @Excel(name = "网络类型")
    @TableField("NETWORK")
    private String network;

    /**
     * 用户名
     */
    @TableField("USER_NAME")
    private String userName;

    /**
     * 密码
     */
    @TableField("PASSWORD")
    private String password;

    /**
     * 变更前规格
     */
    @Excel(name = "变更前规格(核/GB)")
    @TableField("SPECIFICATION_OLD")
    private String specificationOld;

    /**
     * 变更后规格
     */
    @Excel(name = "变更后规格(核/GB)")
    @TableField("SPECIFICATION_NEW")
    private String specificationNew;

    /**
     * 变更前存储
     */
    @Excel(name = "变更前数据盘存储(GB)")
    @TableField("STORAGE_OLD")
    private String storageOld;

    /**
     * 变更后存储
     */
    @Excel(name = "变更后数据盘存储(GB)")
    @TableField("STORAGE_NEW")
    private String storageNew;

    @TableField(value = "CREATE_TIME",fill =  FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME",fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    /**
     * 应用类型
     */
    @Excel(name = "应用类型")
    @TableField("APP_TYPE")
    private String appType;
    /**
     *  变更前操作系统
     */
    @Excel(name = "变更前操作系统")
    @TableField("OS")
    private String os;

    /**
     * 变更后操作系统
     */
    @Excel(name = "变更后操作系统")
    @TableField("OS_NEW")
    private String osNew;

    /**
     * 扩容方式 0：不扩容 1:原数据盘扩容 2:新增数据盘
     */
    @Excel(name = "扩容方式",replace = {"不需要扩容_0","原数据盘扩容_1","新增数据盘_2","_null"})
    @TableField("EXPANSION_WAY")
    private Integer expansionWay;


    public Integer getExpansionWay() {
        return expansionWay;
    }

    public void setExpansionWay(Integer expansionWay) {
        this.expansionWay = expansionWay;
    }


    public String getOsNew() {
        return osNew;
    }

    public void setOsNew(String osNew) {
        this.osNew = osNew;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }


    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getId() {
        return id;
    }

    public IaasTxyfwxg setId(String id) {
        this.id = id;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public IaasTxyfwxg setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public String getServerIp() {
        return serverIp;
    }

    public IaasTxyfwxg setServerIp(String serverIp) {
        this.serverIp = serverIp;
        return this;
    }

    public String getNetwork() {
        return network;
    }

    public IaasTxyfwxg setNetwork(String network) {
        this.network = network;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public IaasTxyfwxg setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public IaasTxyfwxg setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getSpecificationOld() {
        return specificationOld;
    }

    public IaasTxyfwxg setSpecificationOld(String specificationOld) {
        this.specificationOld = specificationOld;
        return this;
    }

    public String getSpecificationNew() {
        return specificationNew;
    }

    public IaasTxyfwxg setSpecificationNew(String specificationNew) {
        this.specificationNew = specificationNew;
        return this;
    }

    public String getStorageOld() {
        return storageOld;
    }

    public IaasTxyfwxg setStorageOld(String storageOld) {
        this.storageOld = storageOld;
        return this;
    }

    public String getStorageNew() {
        return storageNew;
    }

    public IaasTxyfwxg setStorageNew(String storageNew) {
        this.storageNew = storageNew;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


    @Override
    public String toString() {
        return "IaasTxyfwxg{" +
                "id='" + id + '\'' +
                ", appInfoId='" + appInfoId + '\'' +
                ", serverIp='" + serverIp + '\'' +
                ", network='" + network + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", specificationOld='" + specificationOld + '\'' +
                ", specificationNew='" + specificationNew + '\'' +
                ", storageOld='" + storageOld + '\'' +
                ", storageNew='" + storageNew + '\'' +
                ", createTime=" + createTime +
                ", modifiedTime=" + modifiedTime +
                ", appType='" + appType + '\'' +
                ", os='" + os + '\'' +
                ", osNew='" + osNew + '\'' +
                ", expansionWay=" + expansionWay +
                '}';
    }
}
