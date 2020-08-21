package com.hirisun.cloud.iaas.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.upd.hwcloud.common.utils.annotation.ExcelExplain;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 裸金属服务实施信息表
 * </p>
 *
 * @author xqp
 * @since 2020-08-12
 */
@TableName("TB_IAAS_LJSFW_IMPL")
public class IaasLjsfwImpl extends Model<IaasLjsfwImpl> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID",type = IdType.UUID)
    private String id;

        /**
     * 应用名称
     */
         @TableField("APP_NAME")
    private String appName;

        /**
     * 应用组件
     */
        @Excel(name = "应用组件" )
        @ExcelExplain(head = "应用组件")
         @TableField("COMPONENT")
    private String component;

    /**
     * 应用类型
     */
    @Excel(name = "应用类型" )
    @ExcelExplain(head = "应用类型")
    @TableField("APP_TYPE")
    private String appType;

        /**
     * 规格
     */
        @Excel(name = "规格名称" )
        @ExcelExplain(head = "规格名称")
         @TableField("SPECIFICATION")
    private String specification;

        /**
     * 操作系统
     */
        @Excel(name = "系统" )
        @ExcelExplain(head = "系统")
         @TableField("OS")
    private String os;

        /**
     * 网络
     */
        @Excel(name = "网络" )
        @ExcelExplain(head = "网络")
         @TableField("NET")
    private String net;

        /**
     * 服务器 ID
     */
        @Excel(name = "实例ID" )
        @ExcelExplain(head = "实例ID")
         @TableField("SERVER_ID")
    private String serverId;

    /**
     * 公安网访问IP
     */
    @Excel(name = "公安网访问IP" )
    @ExcelExplain(head = "公安网访问IP")
    @TableField("ACCESS_IP")
    private String accessIp;

        /**
     * 服务器 IP
     */
         @TableField("SERVER_IP")
    private String serverIp;

        /**
     * 开放端口
     */
        @Excel(name = "开放端口" )
        @ExcelExplain(head = "开放端口")
         @TableField("SERVER_PORT")
    private String serverPort;

        /**
     * 用户名
     */
        @Excel(name = "用户名" )
        @ExcelExplain(head = "用户名")
         @TableField("USER_NAME")
    private String userName;

        /**
     * 密码
     */
        @Excel(name = "初始密码" )
        @ExcelExplain(head = "初始密码")
         @TableField("PASSWORD")
    private String password;

        /**
     * 申请服务信息id
     */
         @TableField("APP_INFO_ID")
    private String appInfoId;

    @TableField(value = "CREATE_TIME",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME",fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    /**
     * IP
     */
    @Excel(name = "IP")
    @ExcelExplain(head = "IP")
    @TableField("SERVER_IP1")
    private String serverIp1;

    /**
     * 是否发放
     */
    @Excel(name = "是否发放")
    @ExcelExplain(head = "是否发放")
    @TableField("IS_FF")
    private String isFf;


    public String getId() {
        return id;
    }

    public IaasLjsfwImpl setId(String id) {
        this.id = id;
        return this;
    }

    public String getAppName() {
        return appName;
    }

    public IaasLjsfwImpl setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    public String getComponent() {
        return component;
    }

    public IaasLjsfwImpl setComponent(String component) {
        this.component = component;
        return this;
    }

    public String getSpecification() {
        return specification;
    }

    public IaasLjsfwImpl setSpecification(String specification) {
        this.specification = specification;
        return this;
    }

    public String getOs() {
        return os;
    }

    public IaasLjsfwImpl setOs(String os) {
        this.os = os;
        return this;
    }

    public String getNet() {
        return net;
    }

    public IaasLjsfwImpl setNet(String net) {
        this.net = net;
        return this;
    }

    public String getServerId() {
        return serverId;
    }

    public IaasLjsfwImpl setServerId(String serverId) {
        this.serverId = serverId;
        return this;
    }

    public String getServerIp() {
        return serverIp;
    }

    public IaasLjsfwImpl setServerIp(String serverIp) {
        this.serverIp = serverIp;
        return this;
    }

    public String getServerPort() {
        return serverPort;
    }

    public IaasLjsfwImpl setServerPort(String serverPort) {
        this.serverPort = serverPort;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public IaasLjsfwImpl setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public IaasLjsfwImpl setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getAppInfoId() {
        return appInfoId;
    }

    public IaasLjsfwImpl setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public IaasLjsfwImpl setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public IaasLjsfwImpl setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getAppType() {
        return appType;
    }

    public IaasLjsfwImpl setAppType(String appType) {
        this.appType = appType;
        return this;
    }

    public String getAccessIp() {
        return accessIp;
    }

    public IaasLjsfwImpl setAccessIp(String accessIp) {
        this.accessIp = accessIp;
        return this;
    }

    public String getServerIp1() {
        return serverIp1;
    }

    public IaasLjsfwImpl setServerIp1(String serverIp1) {
        this.serverIp1 = serverIp1;
        return this;
    }

    public String getIsFf() {
        return isFf;
    }

    public IaasLjsfwImpl setIsFf(String isFf) {
        this.isFf = isFf;
        return this;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "IaasLjsfwImpl{" +
        "id=" + id +
        ", appName=" + appName +
        ", component=" + component +
        ", specification=" + specification +
        ", os=" + os +
        ", net=" + net +
        ", serverId=" + serverId +
        ", serverIp=" + serverIp +
        ", serverPort=" + serverPort +
        ", userName=" + userName +
        ", password=" + password +
        ", appInfoId=" + appInfoId +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", appType=" + appType +
        ", accessIp=" + accessIp +
        ", serverIp1=" + serverIp1 +
        ", isFf=" + isFf +
        "}";
    }
}
