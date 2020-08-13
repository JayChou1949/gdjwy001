package com.hirisun.cloud.model.service;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("后端服务地址")
public class BackendHostVo implements Serializable{

	private static final long serialVersionUID = 978319305460101602L;

	@ApiModelProperty("id")
    private String id;

	@ApiModelProperty("创建日期")
    private Date createTime;

	@ApiModelProperty("修改日期")
    private Date modifiedTime;

	@ApiModelProperty("后端服务ID")
    private String masterId;

	@ApiModelProperty("后端服务地址")
    private String hostValue;

	@ApiModelProperty("权重")
    private Long weight;
    
}
