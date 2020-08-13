package com.hirisun.cloud.model.app.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("应用场景优势")
public class AppSupreVo implements Serializable {

	private static final long serialVersionUID = 514958956277891880L;

	@ApiModelProperty(value = "id")
    private String id;

	@ApiModelProperty(value = "masterId")
    private String masterId;

	@ApiModelProperty(value = "应用场景ID")
    private String appId;
	
	@ApiModelProperty(value = "标题")
    private String title;

	@ApiModelProperty(value = "描述")
    private String description;

	@ApiModelProperty(value = "状态")
    private String status;

	@ApiModelProperty(value = "备注")
    private String remark;


}
