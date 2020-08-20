package com.hirisun.cloud.order.bean.publish;

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

/**
 * 服务发布-API
 */
@TableName("T_SERVICE_PUBLISH_API")
public class ServicePublishApi implements Serializable {

	private static final long serialVersionUID = 103267666454421059L;

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
     * API名称
     */
         @TableField("NAME")
    private String name;

        /**
     * 版本
     */
         @TableField("VERSION")
    private String version;

        /**
     * 访问协议.
         * 取值: HTTP/HTTPS/HTTP&HTTPS/WS/WSS
     */
         @TableField("PROTOCOL")
    private String protocol;

        /**
     * 根路径
     */
         @TableField("BASE_PATH")
    private String basePath;

        /**
     * 安全认证类型
     */
         @TableField("AUTH_TYPE")
    private String authType;

        /**
     * 备注
     */
         @TableField("REMARK")
    private String remark;

        /**
     * 流量控制策略名称
     */
         @TableField("FC_NAME")
    private String fcName;

        /**
     * 流量控制策略类型.
         * shared:共享额度, exclusive:专用额度
     */
         @TableField("FC_THRESHOLD_TYPE")
    private String fcThresholdType;

        /**
     * 流量控制API流量限制
     */
         @TableField("FC_API_THRESHOLD")
    private Long fcApiThreshold;

    /**
     * 流量控制API流量限制时间
     */
    @TableField("FC_API_TIME")
    private Long fcApiTime;

        /**
     * 流量控制API流量限制单位
         * 取值: SECOND/MINUTE/HOUR/DAY
     */
         @TableField("FC_TIME_UNIT")
    private String fcTimeUnit;

        /**
     * 流量控制应用流量限制
     */
         @TableField("FC_TIME_INTERVAL")
    private Long fcTimeInterval;

    /**
     * 流量控制应用流量限制时间
     */
    @TableField("FC_APP_TIME")
    private Long fcAppTime;

        /**
     * 备注
     */
         @TableField("FC_REMARK")
    private String fcRemark;
    /**
     * 是否配置访问策略,1:是,0:否
     */
    @TableField("IS_AC")
    private String isAc;

        /**
     * 访问控制策略名称
     */
         @TableField("AC_NAME")
    private String acName;

        /**
     * 访问控制策略限制类型
     */
         @TableField("AC_TYPE")
    private String acType;

        /**
     * 访问控制策略动作，允许：ALLOW  拒绝：DENY
     */
         @TableField("AC_ACTION")
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

    public ServicePublishApi setId(String id) {
        this.id = id;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ServicePublishApi setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public ServicePublishApi setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
        return this;
    }

    public String getPublishId() {
        return publishId;
    }

    public ServicePublishApi setPublishId(String publishId) {
        this.publishId = publishId;
        return this;
    }

    public String getName() {
        return name;
    }

    public ServicePublishApi setName(String name) {
        this.name = name;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public ServicePublishApi setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getProtocol() {
        return protocol;
    }

    public ServicePublishApi setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public String getBasePath() {
        return basePath;
    }

    public ServicePublishApi setBasePath(String basePath) {
        this.basePath = basePath;
        return this;
    }

    public String getAuthType() {
        return authType;
    }

    public ServicePublishApi setAuthType(String authType) {
        this.authType = authType;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public ServicePublishApi setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getFcName() {
        return fcName;
    }

    public ServicePublishApi setFcName(String fcName) {
        this.fcName = fcName;
        return this;
    }

    public String getFcThresholdType() {
        return fcThresholdType;
    }

    public ServicePublishApi setFcThresholdType(String fcThresholdType) {
        this.fcThresholdType = fcThresholdType;
        return this;
    }

    public Long getFcApiThreshold() {
        return fcApiThreshold;
    }

    public ServicePublishApi setFcApiThreshold(Long fcApiThreshold) {
        this.fcApiThreshold = fcApiThreshold;
        return this;
    }

    public String getFcTimeUnit() {
        return fcTimeUnit;
    }

    public ServicePublishApi setFcTimeUnit(String fcTimeUnit) {
        this.fcTimeUnit = fcTimeUnit;
        return this;
    }

    public Long getFcTimeInterval() {
        return fcTimeInterval;
    }

    public ServicePublishApi setFcTimeInterval(Long fcTimeInterval) {
        this.fcTimeInterval = fcTimeInterval;
        return this;
    }

    public String getFcRemark() {
        return fcRemark;
    }

    public ServicePublishApi setFcRemark(String fcRemark) {
        this.fcRemark = fcRemark;
        return this;
    }

    public String getAcName() {
        return acName;
    }

    public ServicePublishApi setAcName(String acName) {
        this.acName = acName;
        return this;
    }

    public String getAcType() {
        return acType;
    }

    public ServicePublishApi setAcType(String acType) {
        this.acType = acType;
        return this;
    }

    public String getAcAction() {
        return acAction;
    }

    public ServicePublishApi setAcAction(String acAction) {
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
