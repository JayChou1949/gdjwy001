package com.upd.hwcloud.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务发布-后端服务
 * </p>
 *
 * @author wuc
 * @since 2019-10-17
 */
@TableName("TB_SERVICE_PUBLISH_BACKEND")
public class ServicePublishBackend extends Model<ServicePublishBackend> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

        /**
     * 服务发布ID
     */
         @TableField("PUBLISH_ID")
    private String publishId;

        /**
     * 服务类型
     */
         @TableField("NAME")
    private String name;

        /**
     * 认证方式
     */
         @TableField("SERVICE_AUTH_TYPE")
    private String serviceAuthType;

        /**
     * 认证用户名
     */
         @TableField("AUTH_NAME")
    private String authName;

        /**
     * 认证密码
     */
         @TableField("AUTH_PASSWORD")
    private String authPassword;

        /**
     * 后端请求协议
     */
         @TableField("BACKEND_PROTOCOL")
    private String backendProtocol;

        /**
     * 后端服务端口
     */
         @TableField("BACKEND_PORT")
    private String backendPort;

        /**
     * 头域HOST
     */
         @TableField("HEADER_HOST")
    private String headerHost;

        /**
     * 后端超时时间
     */
         @TableField("BACKEND_TIME_OUT")
    private Long backendTimeOut;

        /**
     * 备注
     */
         @TableField("REMARK")
    private String remark;

        /**
     * 级联后端网关,1:是 0:否
     */
         @TableField("CASCADE_FLAG")
    private String cascadeFlag;

        /**
     * 健康检查协议
     */
         @TableField("HC_PROTOCOL")
    private String hcProtocol;

        /**
     * 健康检查端口
     */
         @TableField("HC_PORT")
    private String hcPort;

        /**
     * 健康检查地址
     */
         @TableField("HC_ADDRESS")
    private String hcAddress;

        /**
     * 健康检查地址类型
     */
         @TableField("HC_ADDRESS_TYPE")
    private String hcAddressType;

        /**
     * 健康检查正常阈值
     */
         @TableField("HC_THRESHOLD_NORMAL")
    private Long hcThresholdNormal;

        /**
     * 健康检查异常阈值
     */
         @TableField("HC_THRESHOLD_ABNORMAL")
    private Long hcThresholdAbnormal;

        /**
     * 健康检查超时时间
     */
         @TableField("HC_TIME_OUT")
    private Long hcTimeOut;

        /**
     * 健康检查间隔时间
     */
         @TableField("HC_TIME_INTERVAL")
    private Long hcTimeInterval;

        /**
     * 健康检查 HTTP响应码.
         * 复选框,当复选框选中时为 1 否则为 0,比如: 0,1,1,0 表示4个复选框中间两个为选中状态
     */
         @TableField("HC_HTTP_CODE")
    private String hcHttpCode;

        /**
     * 分发算法.
         * 此项固定为:round_robin
     */
         @TableField("BALANCE_STRATEGY")
    private String balanceStrategy;

        /**
     * 微网关
     */
         @TableField("MICROGW")
    private String microgw;

    /**
     * 服务地址
     */
    @TableField(exist = false)
     private List<BackendHost> hostList;


    public String getId() {
        return id;
    }

    public ServicePublishBackend setId(String id) {
        this.id = id;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ServicePublishBackend setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public ServicePublishBackend setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getPublishId() {
        return publishId;
    }

    public ServicePublishBackend setPublishId(String publishId) {
        this.publishId = publishId;
        return this;
    }

    public String getName() {
        return name;
    }

    public ServicePublishBackend setName(String name) {
        this.name = name;
        return this;
    }

    public String getServiceAuthType() {
        return serviceAuthType;
    }

    public ServicePublishBackend setServiceAuthType(String serviceAuthType) {
        this.serviceAuthType = serviceAuthType;
        return this;
    }

    public String getAuthName() {
        return authName;
    }

    public ServicePublishBackend setAuthName(String authName) {
        this.authName = authName;
        return this;
    }

    public String getAuthPassword() {
        return authPassword;
    }

    public ServicePublishBackend setAuthPassword(String authPassword) {
        this.authPassword = authPassword;
        return this;
    }

    public String getBackendProtocol() {
        return backendProtocol;
    }

    public ServicePublishBackend setBackendProtocol(String backendProtocol) {
        this.backendProtocol = backendProtocol;
        return this;
    }

    public String getBackendPort() {
        return backendPort;
    }

    public ServicePublishBackend setBackendPort(String backendPort) {
        this.backendPort = backendPort;
        return this;
    }

    public String getHeaderHost() {
        return headerHost;
    }

    public ServicePublishBackend setHeaderHost(String headerHost) {
        this.headerHost = headerHost;
        return this;
    }

    public Long getBackendTimeOut() {
        return backendTimeOut;
    }

    public ServicePublishBackend setBackendTimeOut(Long backendTimeOut) {
        this.backendTimeOut = backendTimeOut;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public ServicePublishBackend setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getCascadeFlag() {
        return cascadeFlag;
    }

    public ServicePublishBackend setCascadeFlag(String cascadeFlag) {
        this.cascadeFlag = cascadeFlag;
        return this;
    }

    public String getHcProtocol() {
        return hcProtocol;
    }

    public ServicePublishBackend setHcProtocol(String hcProtocol) {
        this.hcProtocol = hcProtocol;
        return this;
    }

    public String getHcPort() {
        return hcPort;
    }

    public ServicePublishBackend setHcPort(String hcPort) {
        this.hcPort = hcPort;
        return this;
    }

    public String getHcAddress() {
        return hcAddress;
    }

    public ServicePublishBackend setHcAddress(String hcAddress) {
        this.hcAddress = hcAddress;
        return this;
    }

    public String getHcAddressType() {
        return hcAddressType;
    }

    public ServicePublishBackend setHcAddressType(String hcAddressType) {
        this.hcAddressType = hcAddressType;
        return this;
    }

    public Long getHcThresholdNormal() {
        return hcThresholdNormal;
    }

    public ServicePublishBackend setHcThresholdNormal(Long hcThresholdNormal) {
        this.hcThresholdNormal = hcThresholdNormal;
        return this;
    }

    public Long getHcThresholdAbnormal() {
        return hcThresholdAbnormal;
    }

    public ServicePublishBackend setHcThresholdAbnormal(Long hcThresholdAbnormal) {
        this.hcThresholdAbnormal = hcThresholdAbnormal;
        return this;
    }

    public Long getHcTimeOut() {
        return hcTimeOut;
    }

    public ServicePublishBackend setHcTimeOut(Long hcTimeOut) {
        this.hcTimeOut = hcTimeOut;
        return this;
    }

    public Long getHcTimeInterval() {
        return hcTimeInterval;
    }

    public ServicePublishBackend setHcTimeInterval(Long hcTimeInterval) {
        this.hcTimeInterval = hcTimeInterval;
        return this;
    }

    public String getHcHttpCode() {
        return hcHttpCode;
    }

    public ServicePublishBackend setHcHttpCode(String hcHttpCode) {
        this.hcHttpCode = hcHttpCode;
        return this;
    }

    public String getBalanceStrategy() {
        return balanceStrategy;
    }

    public ServicePublishBackend setBalanceStrategy(String balanceStrategy) {
        this.balanceStrategy = balanceStrategy;
        return this;
    }

    public String getMicrogw() {
        return microgw;
    }

    public ServicePublishBackend setMicrogw(String microgw) {
        this.microgw = microgw;
        return this;
    }

    public List<BackendHost> getHostList() {
        return hostList;
    }

    public void setHostList(List<BackendHost> hostList) {
        this.hostList = hostList;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ServicePublishBackend{" +
        "id=" + id +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", publishId=" + publishId +
        ", name=" + name +
        ", serviceAuthType=" + serviceAuthType +
        ", authName=" + authName +
        ", authPassword=" + authPassword +
        ", backendProtocol=" + backendProtocol +
        ", backendPort=" + backendPort +
        ", headerHost=" + headerHost +
        ", backendTimeOut=" + backendTimeOut +
        ", remark=" + remark +
        ", cascadeFlag=" + cascadeFlag +
        ", hcProtocol=" + hcProtocol +
        ", hcPort=" + hcPort +
        ", hcAddress=" + hcAddress +
        ", hcAddressType=" + hcAddressType +
        ", hcThresholdNormal=" + hcThresholdNormal +
        ", hcThresholdAbnormal=" + hcThresholdAbnormal +
        ", hcTimeOut=" + hcTimeOut +
        ", hcTimeInterval=" + hcTimeInterval +
        ", hcHttpCode=" + hcHttpCode +
        ", balanceStrategy=" + balanceStrategy +
        ", microgw=" + microgw +
        "}";
    }
}
