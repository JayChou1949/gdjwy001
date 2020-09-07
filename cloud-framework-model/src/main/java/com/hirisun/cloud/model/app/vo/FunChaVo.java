package com.hirisun.cloud.model.app.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("功能特点")
public class FunChaVo implements Serializable {

	private static final long serialVersionUID = 2768549971676073034L;

	@ApiModelProperty(value = "id")
    private String id;

	@ApiModelProperty(value = "iaasId")
	private String iaasId;

    @ApiModelProperty(value = "图标")
    private String image;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "描述")
    private String description;

     @ApiModelProperty(value = "状态")
    private String status;

     @ApiModelProperty(value = "备注")
    private String remark;



}
