package com.hirisun.cloud.order.bean.servicePublish;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-01
 */
@TableName("TB_SERVICE_ALTER_BACKEND")
@ApiModel(value="ServiceAlterBackend对象", description="")
public class ServiceAlterBackend implements Serializable {

    private static final long serialVersionUID=1L;

    @TableField("ID")
    private String id;

    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @TableField("MODIFIED_TIME")
    private LocalDateTime modifiedTime;

    @TableField("ALTER_ID")
    private String alterId;

    @TableField("NAME")
    private String name;

    @TableField("SERVICE_AUTH_TYPE")
    private String serviceAuthType;

    @TableField("AUTH_NAME")
    private String authName;

    @TableField("AUTH_PASSWORD")
    private String authPassword;

    @TableField("BACKEND_PROTOCOL")
    private String backendProtocol;

    @TableField("BACKEND_PORT")
    private String backendPort;

    @TableField("HEADER_HOST")
    private String headerHost;

    @TableField("BACKEND_TIME_OUT")
    private BigDecimal backendTimeOut;

    @TableField("REMARK")
    private String remark;

    @TableField("CASCADE_FLAG")
    private String cascadeFlag;

    @TableField("HC_PROTOCOL")
    private String hcProtocol;

    @TableField("HC_PORT")
    private String hcPort;

    @TableField("HC_ADDRESS")
    private String hcAddress;

    @TableField("HC_ADDRESS_TYPE")
    private String hcAddressType;

    @TableField("HC_THRESHOLD_NORMAL")
    private BigDecimal hcThresholdNormal;

    @TableField("HC_THRESHOLD_ABNORMAL")
    private BigDecimal hcThresholdAbnormal;

    @TableField("HC_TIME_OUT")
    private BigDecimal hcTimeOut;

    @TableField("HC_TIME_INTERVAL")
    private BigDecimal hcTimeInterval;

    @TableField("HC_HTTP_CODE")
    private String hcHttpCode;

    @TableField("BALANCE_STRATEGY")
    private String balanceStrategy;

    @TableField("MICROGW")
    private String microgw;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getAlterId() {
        return alterId;
    }

    public void setAlterId(String alterId) {
        this.alterId = alterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceAuthType() {
        return serviceAuthType;
    }

    public void setServiceAuthType(String serviceAuthType) {
        this.serviceAuthType = serviceAuthType;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getAuthPassword() {
        return authPassword;
    }

    public void setAuthPassword(String authPassword) {
        this.authPassword = authPassword;
    }

    public String getBackendProtocol() {
        return backendProtocol;
    }

    public void setBackendProtocol(String backendProtocol) {
        this.backendProtocol = backendProtocol;
    }

    public String getBackendPort() {
        return backendPort;
    }

    public void setBackendPort(String backendPort) {
        this.backendPort = backendPort;
    }

    public String getHeaderHost() {
        return headerHost;
    }

    public void setHeaderHost(String headerHost) {
        this.headerHost = headerHost;
    }

    public BigDecimal getBackendTimeOut() {
        return backendTimeOut;
    }

    public void setBackendTimeOut(BigDecimal backendTimeOut) {
        this.backendTimeOut = backendTimeOut;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCascadeFlag() {
        return cascadeFlag;
    }

    public void setCascadeFlag(String cascadeFlag) {
        this.cascadeFlag = cascadeFlag;
    }

    public String getHcProtocol() {
        return hcProtocol;
    }

    public void setHcProtocol(String hcProtocol) {
        this.hcProtocol = hcProtocol;
    }

    public String getHcPort() {
        return hcPort;
    }

    public void setHcPort(String hcPort) {
        this.hcPort = hcPort;
    }

    public String getHcAddress() {
        return hcAddress;
    }

    public void setHcAddress(String hcAddress) {
        this.hcAddress = hcAddress;
    }

    public String getHcAddressType() {
        return hcAddressType;
    }

    public void setHcAddressType(String hcAddressType) {
        this.hcAddressType = hcAddressType;
    }

    public BigDecimal getHcThresholdNormal() {
        return hcThresholdNormal;
    }

    public void setHcThresholdNormal(BigDecimal hcThresholdNormal) {
        this.hcThresholdNormal = hcThresholdNormal;
    }

    public BigDecimal getHcThresholdAbnormal() {
        return hcThresholdAbnormal;
    }

    public void setHcThresholdAbnormal(BigDecimal hcThresholdAbnormal) {
        this.hcThresholdAbnormal = hcThresholdAbnormal;
    }

    public BigDecimal getHcTimeOut() {
        return hcTimeOut;
    }

    public void setHcTimeOut(BigDecimal hcTimeOut) {
        this.hcTimeOut = hcTimeOut;
    }

    public BigDecimal getHcTimeInterval() {
        return hcTimeInterval;
    }

    public void setHcTimeInterval(BigDecimal hcTimeInterval) {
        this.hcTimeInterval = hcTimeInterval;
    }

    public String getHcHttpCode() {
        return hcHttpCode;
    }

    public void setHcHttpCode(String hcHttpCode) {
        this.hcHttpCode = hcHttpCode;
    }

    public String getBalanceStrategy() {
        return balanceStrategy;
    }

    public void setBalanceStrategy(String balanceStrategy) {
        this.balanceStrategy = balanceStrategy;
    }

    public String getMicrogw() {
        return microgw;
    }

    public void setMicrogw(String microgw) {
        this.microgw = microgw;
    }

    @Override
    public String toString() {
        return "ServiceAlterBackend{" +
        "id=" + id +
        ", createTime=" + createTime +
        ", modifiedTime=" + modifiedTime +
        ", alterId=" + alterId +
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
