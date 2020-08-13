package com.hirisun.cloud.model.service;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("API ip信息")
public class ApiAcIpVo implements Serializable {

	private static final long serialVersionUID = 6355732484114755248L;

	@ApiModelProperty("id")
    private String id;

	@ApiModelProperty("创建日期")
    private Date createTime;

	@ApiModelProperty("修改日期")
    private Date modifiedTime;

	@ApiModelProperty("API ID")
    private String masterId;

	@ApiModelProperty("IP 地址")
    private String ip;

	@ApiModelProperty("IP 地址范围")
    private String ipRange;

}
