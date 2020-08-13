package com.hirisun.cloud.model.service.alter.vo;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("服务发布api产品")
public class ServiceAlterApiProductVo implements Serializable {

	private static final long serialVersionUID = 2345008486996923817L;

	@ApiModelProperty("id")
	private String id;

	@ApiModelProperty("创建日期")
    private Date createTime;

	@ApiModelProperty("修改日期")
    private Date modifiedTime;

	@ApiModelProperty("服务发布ID")
    private String publishId;

	@ApiModelProperty("API产品名称")
    private String name;

	@ApiModelProperty("版本")
    private String version;

	@ApiModelProperty("微网关")
    private String microgw;

	@ApiModelProperty("微网关域名")
    private String microgwDomainName;

	@ApiModelProperty("备注")
    private String remark;

}
