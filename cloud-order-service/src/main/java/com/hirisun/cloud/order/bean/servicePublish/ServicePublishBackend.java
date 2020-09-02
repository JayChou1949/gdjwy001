package com.hirisun.cloud.order.bean.servicePublish;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 服务发布-后端服务
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-01
 */
@Data
@TableName("TB_SERVICE_PUBLISH_BACKEND")
@ApiModel(value="ServicePublishBackend对象", description="服务发布-后端服务")
public class ServicePublishBackend implements Serializable {

    private static final long serialVersionUID=1L;

    @TableField("ID")
    private String id;

    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    @TableField("MODIFIED_TIME")
    private LocalDateTime modifiedTime;

    @ApiModelProperty(value = "服务发布ID")
    @TableField("PUBLISH_ID")
    private String publishId;

    @ApiModelProperty(value = "服务类型")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "认证方式")
    @TableField("SERVICE_AUTH_TYPE")
    private String serviceAuthType;

    @ApiModelProperty(value = "认证用户名")
    @TableField("AUTH_NAME")
    private String authName;

    @ApiModelProperty(value = "认证密码")
    @TableField("AUTH_PASSWORD")
    private String authPassword;

    @ApiModelProperty(value = "后端请求协议")
    @TableField("BACKEND_PROTOCOL")
    private String backendProtocol;

    @ApiModelProperty(value = "后端服务端口")
    @TableField("BACKEND_PORT")
    private String backendPort;

    @ApiModelProperty(value = "头域HOST")
    @TableField("HEADER_HOST")
    private String headerHost;

    @ApiModelProperty(value = "后端超时时间")
    @TableField("BACKEND_TIME_OUT")
    private BigDecimal backendTimeOut;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "级联后端网关,1:是 0:否")
    @TableField("CASCADE_FLAG")
    private String cascadeFlag;

    @ApiModelProperty(value = "健康检查协议")
    @TableField("HC_PROTOCOL")
    private String hcProtocol;

    @ApiModelProperty(value = "健康检查端口")
    @TableField("HC_PORT")
    private String hcPort;

    @ApiModelProperty(value = "健康检查地址")
    @TableField("HC_ADDRESS")
    private String hcAddress;

    @ApiModelProperty(value = "健康检查地址类型")
    @TableField("HC_ADDRESS_TYPE")
    private String hcAddressType;

    @ApiModelProperty(value = "健康检查正常阈值")
    @TableField("HC_THRESHOLD_NORMAL")
    private BigDecimal hcThresholdNormal;

    @ApiModelProperty(value = "健康检查异常阈值")
    @TableField("HC_THRESHOLD_ABNORMAL")
    private BigDecimal hcThresholdAbnormal;

    @ApiModelProperty(value = "健康检查超时时间")
    @TableField("HC_TIME_OUT")
    private BigDecimal hcTimeOut;

    @ApiModelProperty(value = "健康检查间隔时间")
    @TableField("HC_TIME_INTERVAL")
    private BigDecimal hcTimeInterval;

    @ApiModelProperty(value = "健康检查 HTTP响应码,当复选框选中时为 1 否则为 0")
    @TableField("HC_HTTP_CODE")
    private String hcHttpCode;

    @ApiModelProperty(value = "分发算法,此项固定为:round_robin")
    @TableField("BALANCE_STRATEGY")
    private String balanceStrategy;

    @ApiModelProperty(value = "微网关")
    @TableField("MICROGW")
    private String microgw;

    /**
     * 服务地址
     */
    @ApiModelProperty(value = "服务地址")
    @TableField(exist = false)
    private List<BackendHost> hostList;



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
