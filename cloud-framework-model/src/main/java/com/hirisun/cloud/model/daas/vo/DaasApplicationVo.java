package com.hirisun.cloud.model.daas.vo;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("DaaS服务申请信息")
@Data
public class DaasApplicationVo implements Serializable {

	private static final long serialVersionUID = -4762482587378383814L;

	@ApiModelProperty("id")
    private String id;

	@ApiModelProperty("申请服务id")
    private String serviceId;

	@ApiModelProperty("申请服务名")
    private String serviceName;

	@ApiModelProperty("版本号")
    private String version;

	@ApiModelProperty("服务商")
    private String provider;

	@ApiModelProperty("申请信息 id")
    private String appInfoId;

	@ApiModelProperty("购物车id")
    private String shoppingCartId;

	@ApiModelProperty("appKey")
    private String appKey;

	@ApiModelProperty("appSecret")
    private String appSecret;

	@ApiModelProperty("创建日期")
    private Date createTime;

	@ApiModelProperty("修改日期")
    private Date modifiedTime;

	@ApiModelProperty("错误信息")
    private String errorMsg;

	@ApiModelProperty("错误信息json")
    private String errorJson;

	@ApiModelProperty("实例id")
    private String instanceGuid;

	@ApiModelProperty("api列表")
    private String userData;

}
