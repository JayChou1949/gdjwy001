package com.hirisun.cloud.model.service;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("api操作")
public class ApiOperationVo implements Serializable{

	private static final long serialVersionUID = 6807609186815595996L;

	@ApiModelProperty("id")
    private String id;

	@ApiModelProperty("创建日期")
    private Date createTime;

	@ApiModelProperty("修改日期")
    private Date modifiedTime;

	@ApiModelProperty("masterId")
    private String masterId;

    @ApiModelProperty("API操作名称")
    private String name;

    @ApiModelProperty("路径")
    private String path;

    @ApiModelProperty("请求方法")
    private String method;

    @ApiModelProperty("是否编排,1:是 0:否")
    private String withOrchestration;

    @ApiModelProperty("精确:NORMAL  前缀:PREFIX")
    private String matchMode;

    @ApiModelProperty("后端服务名称")
    private String backendName;

    @ApiModelProperty("后端请求协议.当前仅支持 HTTP，表示 http 和 https 服务")
    private String backendType;

    @ApiModelProperty("后端请求路径")
    private String backendPath;

    @ApiModelProperty("后端请求方法")
    private String backendMethod;

    @ApiModelProperty("后端服务id")
    private String backendId;

}
