package com.upd.hwcloud.bean.entity.application;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangbao
 * @since 2019-10-18
 */
@TableName("TB_IAAS_TXYFWXG_IMPL")
public class IaasTxyfwxgImpl extends Model<IaasTxyfwxgImpl> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

        /**
     * 申请信息 id
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

        /**
     * 虚拟机IP
     */
         @TableField("SERVER_IP")
    private String serverIp;

        /**
     * 网络类型
     */
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
         @TableField("SPECIFICATION_OLD")
    private String specificationOld;

        /**
     * 变更后规格
     */
         @TableField("SPECIFICATION_NEW")
    private String specificationNew;

        /**
     * 变更前存储
     */
         @TableField("STORAGE_OLD")
    private String storageOld;

        /**
     * 变更后存储
     */
         @TableField("STORAGE_NEW")
    private String storageNew;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

        /**
     * 应用类型
     */
         @TableField("APP_TYPE")
    private String appType;
    /**
     * 变更前操作系统
     */
    @TableField("OS")
    private String os;

    /**
     * 公安网访问ip
     */
    @TableField("SERVER_IP1")
    private String serverIp1;

    /**
     * IP
     */
    @TableField("SERVER_IP2")
    private String serverIp2;

    @TableField("INSTANCE_ID")
    private String instanceId;

    @TableField("OPEN_PORT")
    private String openPort;
    /**
     * 变更后操作系统
     */
    @TableField("OS_NEW")
    private String osNew;

    /**
     * 扩容方式 0：不扩容 1:原数据盘扩容 2:新增数据盘
     */
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

    public String getId() {
        return id;
    }

    public IaasTxyfwxgImpl setId(String id) {
        this.id = id;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public IaasTxyfwxgImpl setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public String getServerIp() {
        return serverIp;
    }

    public IaasTxyfwxgImpl setServerIp(String serverIp) {
        this.serverIp = serverIp;
        return this;
    }

    public String getNetwork() {
        return network;
    }

    public IaasTxyfwxgImpl setNetwork(String network) {
        this.network = network;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public IaasTxyfwxgImpl setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public IaasTxyfwxgImpl setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getSpecificationOld() {
        return specificationOld;
    }

    public IaasTxyfwxgImpl setSpecificationOld(String specificationOld) {
        this.specificationOld = specificationOld;
        return this;
    }

    public String getSpecificationNew() {
        return specificationNew;
    }

    public IaasTxyfwxgImpl setSpecificationNew(String specificationNew) {
        this.specificationNew = specificationNew;
        return this;
    }

    public String getStorageOld() {
        return storageOld;
    }

    public IaasTxyfwxgImpl setStorageOld(String storageOld) {
        this.storageOld = storageOld;
        return this;
    }

    public String getStorageNew() {
        return storageNew;
    }

    public IaasTxyfwxgImpl setStorageNew(String storageNew) {
        this.storageNew = storageNew;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public IaasTxyfwxgImpl setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public IaasTxyfwxgImpl setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getAppType() {
        return appType;
    }

    public IaasTxyfwxgImpl setAppType(String appType) {
        this.appType = appType;
        return this;
    }

    public String getOs() {
        return os;
    }

    public IaasTxyfwxgImpl setOs(String os) {
        this.os = os;
        return this;
    }

    public String getServerIp1() {
        return serverIp1;
    }

    public void setServerIp1(String serverIp1) {
        this.serverIp1 = serverIp1;
    }

    public String getServerIp2() {
        return serverIp2;
    }

    public void setServerIp2(String serverIp2) {
        this.serverIp2 = serverIp2;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getOpenPort() {
        return openPort;
    }

    public void setOpenPort(String openPort) {
        this.openPort = openPort;
    }

    @Override
    protected Serializable pkVal() {
        return id;
    }


    @Override
    public String toString() {
        return "IaasTxyfwxgImpl{" +
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
                ", serverIp1='" + serverIp1 + '\'' +
                ", serverIp2='" + serverIp2 + '\'' +
                ", instanceId='" + instanceId + '\'' +
                ", openPort='" + openPort + '\'' +
                ", osNew='" + osNew + '\'' +
                ", expansionWay=" + expansionWay +
                '}';
    }

}
