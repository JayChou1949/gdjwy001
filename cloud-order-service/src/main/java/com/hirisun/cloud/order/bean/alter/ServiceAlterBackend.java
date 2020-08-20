package com.hirisun.cloud.order.bean.alter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hirisun.cloud.model.service.BackendHostVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 服务变更-后端服务
 * @author Lenovo
 */
@TableName("TB_SERVICE_ALTER_BACKEND")
@ApiModel("服务变更-后端服务")
public class ServiceAlterBackend implements Serializable {

	private static final long serialVersionUID = -8445703113669454294L;

	@TableId(value = "ID", type = IdType.UUID)
    private String id;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

        /**
     * 服务发布ID
     */
         @TableField("ALTER_ID")
    private String alterId;

    @TableField("NAME")
    @ApiModelProperty("服务类型")
    private String name;

    @TableField("SERVICE_AUTH_TYPE")
    @ApiModelProperty("认证方式")
    private String serviceAuthType;

    @TableField("AUTH_NAME")
    @ApiModelProperty("认证用户名")
    private String authName;

    @TableField("AUTH_PASSWORD")
    @ApiModelProperty("认证密码")
    private String authPassword;

    @TableField("BACKEND_PROTOCOL")
    @ApiModelProperty("后端请求协议")
    private String backendProtocol;

    @TableField("BACKEND_PORT")
    @ApiModelProperty("后端服务端口")
    private String backendPort;

    @TableField("HEADER_HOST")
    @ApiModelProperty("头域HOST")
    private String headerHost;

    @TableField("BACKEND_TIME_OUT")
    @ApiModelProperty("后端超时时间")
    private Long backendTimeOut;

    @TableField("REMARK")
    @ApiModelProperty("备注")
    private String remark;

    @TableField("CASCADE_FLAG")
    @ApiModelProperty("级联后端网关,1:是 0:否")
    private String cascadeFlag;

    @TableField("HC_PROTOCOL")
    @ApiModelProperty("健康检查协议")
    private String hcProtocol;

    @TableField("HC_PORT")
    @ApiModelProperty("健康检查端口")
    private String hcPort;

    @TableField("HC_ADDRESS")
    @ApiModelProperty("健康检查地址")
    private String hcAddress;

    @TableField("HC_ADDRESS_TYPE")
    @ApiModelProperty("健康检查地址类型")
    private String hcAddressType;

    @TableField("HC_THRESHOLD_NORMAL")
    @ApiModelProperty("健康检查正常阈值")
    private Long hcThresholdNormal;

    @TableField("HC_THRESHOLD_ABNORMAL")
    @ApiModelProperty("健康检查异常阈值")
    private Long hcThresholdAbnormal;

    @TableField("HC_TIME_OUT")
    @ApiModelProperty("健康检查超时时间")
    private Long hcTimeOut;

    @TableField("HC_TIME_INTERVAL")
    @ApiModelProperty("健康检查间隔时间")
    private Long hcTimeInterval;

    @TableField("HC_HTTP_CODE")
    @ApiModelProperty("健康检查 HTTP响应码.复选框,当复选框选中时为 1 否则为 0,比如: 0,1,1,0 表示4个复选框中间两个为选中状态")
    private String hcHttpCode;

    @TableField("BALANCE_STRATEGY")
    @ApiModelProperty("分发算法,此项固定为:round_robin")
    private String balanceStrategy;

    @TableField("MICROGW")
    @ApiModelProperty("微网关")
    private String microgw;

    /**
     * 服务地址
     */
    @TableField(exist = false)
     private List<BackendHostVo> hostList;


    public String getId() {
        return id;
    }

    public ServiceAlterBackend setId(String id) {
        this.id = id;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ServiceAlterBackend setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public ServiceAlterBackend setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
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

    public ServiceAlterBackend setName(String name) {
        this.name = name;
        return this;
    }

    public String getServiceAuthType() {
        return serviceAuthType;
    }

    public ServiceAlterBackend setServiceAuthType(String serviceAuthType) {
        this.serviceAuthType = serviceAuthType;
        return this;
    }

    public String getAuthName() {
        return authName;
    }

    public ServiceAlterBackend setAuthName(String authName) {
        this.authName = authName;
        return this;
    }

    public String getAuthPassword() {
        return authPassword;
    }

    public ServiceAlterBackend setAuthPassword(String authPassword) {
        this.authPassword = authPassword;
        return this;
    }

    public String getBackendProtocol() {
        return backendProtocol;
    }

    public ServiceAlterBackend setBackendProtocol(String backendProtocol) {
        this.backendProtocol = backendProtocol;
        return this;
    }

    public String getBackendPort() {
        return backendPort;
    }

    public ServiceAlterBackend setBackendPort(String backendPort) {
        this.backendPort = backendPort;
        return this;
    }

    public String getHeaderHost() {
        return headerHost;
    }

    public ServiceAlterBackend setHeaderHost(String headerHost) {
        this.headerHost = headerHost;
        return this;
    }

    public Long getBackendTimeOut() {
        return backendTimeOut;
    }

    public ServiceAlterBackend setBackendTimeOut(Long backendTimeOut) {
        this.backendTimeOut = backendTimeOut;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public ServiceAlterBackend setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getCascadeFlag() {
        return cascadeFlag;
    }

    public ServiceAlterBackend setCascadeFlag(String cascadeFlag) {
        this.cascadeFlag = cascadeFlag;
        return this;
    }

    public String getHcProtocol() {
        return hcProtocol;
    }

    public ServiceAlterBackend setHcProtocol(String hcProtocol) {
        this.hcProtocol = hcProtocol;
        return this;
    }

    public String getHcPort() {
        return hcPort;
    }

    public ServiceAlterBackend setHcPort(String hcPort) {
        this.hcPort = hcPort;
        return this;
    }

    public String getHcAddress() {
        return hcAddress;
    }

    public ServiceAlterBackend setHcAddress(String hcAddress) {
        this.hcAddress = hcAddress;
        return this;
    }

    public String getHcAddressType() {
        return hcAddressType;
    }

    public ServiceAlterBackend setHcAddressType(String hcAddressType) {
        this.hcAddressType = hcAddressType;
        return this;
    }

    public Long getHcThresholdNormal() {
        return hcThresholdNormal;
    }

    public ServiceAlterBackend setHcThresholdNormal(Long hcThresholdNormal) {
        this.hcThresholdNormal = hcThresholdNormal;
        return this;
    }

    public Long getHcThresholdAbnormal() {
        return hcThresholdAbnormal;
    }

    public ServiceAlterBackend setHcThresholdAbnormal(Long hcThresholdAbnormal) {
        this.hcThresholdAbnormal = hcThresholdAbnormal;
        return this;
    }

    public Long getHcTimeOut() {
        return hcTimeOut;
    }

    public ServiceAlterBackend setHcTimeOut(Long hcTimeOut) {
        this.hcTimeOut = hcTimeOut;
        return this;
    }

    public Long getHcTimeInterval() {
        return hcTimeInterval;
    }

    public ServiceAlterBackend setHcTimeInterval(Long hcTimeInterval) {
        this.hcTimeInterval = hcTimeInterval;
        return this;
    }

    public String getHcHttpCode() {
        return hcHttpCode;
    }

    public ServiceAlterBackend setHcHttpCode(String hcHttpCode) {
        this.hcHttpCode = hcHttpCode;
        return this;
    }

    public String getBalanceStrategy() {
        return balanceStrategy;
    }

    public ServiceAlterBackend setBalanceStrategy(String balanceStrategy) {
        this.balanceStrategy = balanceStrategy;
        return this;
    }

    public String getMicrogw() {
        return microgw;
    }

    public ServiceAlterBackend setMicrogw(String microgw) {
        this.microgw = microgw;
        return this;
    }

    public List<BackendHostVo> getHostList() {
        return hostList;
    }

    public void setHostList(List<BackendHostVo> hostList) {
        this.hostList = hostList;
    }

}
