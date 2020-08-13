package com.hirisun.cloud.model.service.alter.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.hirisun.cloud.model.service.BackendHostVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("服务发布后端服务")
public class ServiceAlterBackendVo implements Serializable{

	private static final long serialVersionUID = 1216882239890686167L;

	@ApiModelProperty("id")
    private String id;

	@ApiModelProperty("创建日期")
    private Date createTime;

	@ApiModelProperty("修改日期")
    private Date modifiedTime;

	@ApiModelProperty("服务发布ID")
    private String publishId;

	@ApiModelProperty("后端服务名称")
    private String name;

	@ApiModelProperty("认证方式")
    private String serviceAuthType;

	@ApiModelProperty("认证用户名")
    private String authName;

	@ApiModelProperty("认证密码")
    private String authPassword;

	@ApiModelProperty("后端请求协议")
    private String backendProtocol;

	@ApiModelProperty("后端服务端口")
    private String backendPort;

	@ApiModelProperty("头域HOST")
    private String headerHost;

	@ApiModelProperty("后端超时时间")
    private Long backendTimeOut;

	@ApiModelProperty("备注")
    private String remark;

	@ApiModelProperty("级联后端网关,1:是 0:否")
    private String cascadeFlag;

	@ApiModelProperty("健康检查协议")
    private String hcProtocol;

	@ApiModelProperty("健康检查端口")
    private String hcPort;

	@ApiModelProperty("健康检查地址")
    private String hcAddress;

	@ApiModelProperty("健康检查地址类型")
    private String hcAddressType;

	@ApiModelProperty("健康检查正常阈值")
    private Long hcThresholdNormal;

	@ApiModelProperty("健康检查异常阈值")
    private Long hcThresholdAbnormal;

	@ApiModelProperty("健康检查超时时间")
    private Long hcTimeOut;

	@ApiModelProperty("健康检查间隔时间")
    private Long hcTimeInterval;

	@ApiModelProperty("健康检查 HTTP响应码.复选框,当复选框选中时为 1 否则为 0,比如: 0,1,1,0 表示4个复选框中间两个为选中状态")
    private String hcHttpCode;

    @ApiModelProperty("分发算法.此项固定为:round_robin")
    private String balanceStrategy;

    @ApiModelProperty("微网关")
    private String microgw;

    @ApiModelProperty("服务地址")
    private List<BackendHostVo> hostList;

}
