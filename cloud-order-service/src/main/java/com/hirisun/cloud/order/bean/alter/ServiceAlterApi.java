package com.hirisun.cloud.order.bean.alter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hirisun.cloud.model.service.ApiAcIpVo;
import com.hirisun.cloud.model.service.ApiOperationVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@TableName("T_SERVICE_ALTER_API")
@ApiModel("服务变更-api")
public class ServiceAlterApi implements Serializable {

	private static final long serialVersionUID = -7892324250444790015L;

	@TableId(value = "ID", type = IdType.UUID)
    private String id;

    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "MODIFIED_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedTime;

    @TableField("ALTER_ID")
    @ApiModelProperty("服务发布ID")
    private String alterId;

    @TableField("NAME")
    @ApiModelProperty("API名称")
    private String name;

    @TableField("VERSION")
    @ApiModelProperty("版本")
    private String version;

    @TableField("PROTOCOL")
    @ApiModelProperty("访问协议.取值: HTTP/HTTPS/HTTP&HTTPS/WS/WSS")
    private String protocol;

    @TableField("BASE_PATH")
    @ApiModelProperty("根路径")
    private String basePath;

    @TableField("AUTH_TYPE")
    @ApiModelProperty("安全认证类型")
    private String authType;

    @TableField("REMARK")
    @ApiModelProperty("备注")
    private String remark;

    @TableField("FC_NAME")
    @ApiModelProperty("流量控制策略名称")
    private String fcName;

    @TableField("FC_THRESHOLD_TYPE")
    @ApiModelProperty("流量控制策略类型.shared:共享额度, exclusive:专用额度")
    private String fcThresholdType;

    @TableField("FC_API_THRESHOLD")
    @ApiModelProperty("流量控制API流量限制")
    private Long fcApiThreshold;

    @TableField("FC_API_TIME")
    @ApiModelProperty("流量控制API流量限制时间")
    private Long fcApiTime;

    @TableField("FC_TIME_UNIT")
    @ApiModelProperty("流量控制API流量限制单位,取值: SECOND/MINUTE/HOUR/DAY")
    private String fcTimeUnit;

    @TableField("FC_TIME_INTERVAL")
    @ApiModelProperty("流量控制应用流量限制")
    private Long fcTimeInterval;

    @TableField("FC_APP_TIME")
    @ApiModelProperty("流量控制应用流量限制时间")
    private Long fcAppTime;

    @TableField("FC_REMARK")
    @ApiModelProperty("备注")
    private String fcRemark;

    @TableField("IS_AC")
    @ApiModelProperty("是否配置访问策略,1:是,0:否")
    private String isAc;

    @TableField("AC_NAME")
    @ApiModelProperty("访问控制策略名称")
    private String acName;

    @TableField("AC_TYPE")
    @ApiModelProperty("访问控制策略限制类型")
    private String acType;

    @TableField("AC_ACTION")
    @ApiModelProperty("访问控制策略动作，允许：ALLOW  拒绝：DENY")
    private String acAction;

    /**
     * ip信息
     */
    @TableField(exist = false)
    private List<ApiAcIpVo> ipList;

    /**
     * api操作
     */
    @TableField(exist = false)
    @ApiModelProperty("api操作")
    private List<ApiOperationVo> apiOperationList;
    
    public Long getFcApiTime() {
        return fcApiTime;
    }

    public void setFcApiTime(Long fcApiTime) {
        this.fcApiTime = fcApiTime;
    }

    public Long getFcAppTime() {
        return fcAppTime;
    }

    public void setFcAppTime(Long fcAppTime) {
        this.fcAppTime = fcAppTime;
    }

    public String getIsAc() {
        return isAc;
    }

    public void setIsAc(String isAc) {
        this.isAc = isAc;
    }

    public String getId() {
        return id;
    }

    public ServiceAlterApi setId(String id) {
        this.id = id;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ServiceAlterApi setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public ServiceAlterApi setModifiedTime(Date modifiedTime) {
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

    public ServiceAlterApi setName(String name) {
        this.name = name;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public ServiceAlterApi setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getProtocol() {
        return protocol;
    }

    public ServiceAlterApi setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public String getBasePath() {
        return basePath;
    }

    public ServiceAlterApi setBasePath(String basePath) {
        this.basePath = basePath;
        return this;
    }

    public String getAuthType() {
        return authType;
    }

    public ServiceAlterApi setAuthType(String authType) {
        this.authType = authType;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public ServiceAlterApi setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getFcName() {
        return fcName;
    }

    public ServiceAlterApi setFcName(String fcName) {
        this.fcName = fcName;
        return this;
    }

    public String getFcThresholdType() {
        return fcThresholdType;
    }

    public ServiceAlterApi setFcThresholdType(String fcThresholdType) {
        this.fcThresholdType = fcThresholdType;
        return this;
    }

    public Long getFcApiThreshold() {
        return fcApiThreshold;
    }

    public ServiceAlterApi setFcApiThreshold(Long fcApiThreshold) {
        this.fcApiThreshold = fcApiThreshold;
        return this;
    }

    public String getFcTimeUnit() {
        return fcTimeUnit;
    }

    public ServiceAlterApi setFcTimeUnit(String fcTimeUnit) {
        this.fcTimeUnit = fcTimeUnit;
        return this;
    }

    public Long getFcTimeInterval() {
        return fcTimeInterval;
    }

    public ServiceAlterApi setFcTimeInterval(Long fcTimeInterval) {
        this.fcTimeInterval = fcTimeInterval;
        return this;
    }

    public String getFcRemark() {
        return fcRemark;
    }

    public ServiceAlterApi setFcRemark(String fcRemark) {
        this.fcRemark = fcRemark;
        return this;
    }

    public String getAcName() {
        return acName;
    }

    public ServiceAlterApi setAcName(String acName) {
        this.acName = acName;
        return this;
    }

    public String getAcType() {
        return acType;
    }

    public ServiceAlterApi setAcType(String acType) {
        this.acType = acType;
        return this;
    }

    public String getAcAction() {
        return acAction;
    }

    public ServiceAlterApi setAcAction(String acAction) {
        this.acAction = acAction;
        return this;
    }

    public List<ApiAcIpVo> getIpList() {
        return ipList;
    }

    public void setIpList(List<ApiAcIpVo> ipList) {
        this.ipList = ipList;
    }

    public List<ApiOperationVo> getApiOperationList() {
        return apiOperationList;
    }

    public void setApiOperationList(List<ApiOperationVo> apiOperationList) {
        this.apiOperationList = apiOperationList;
    }

}
