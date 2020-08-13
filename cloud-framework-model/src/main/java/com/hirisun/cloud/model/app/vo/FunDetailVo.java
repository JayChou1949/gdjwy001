package com.hirisun.cloud.model.app.vo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("功能特点")
public class FunDetailVo implements Serializable {

	private static final long serialVersionUID = 9138868750915553138L;

	@ApiModelProperty(value = "id")
    private String id;

	@ApiModelProperty("MASTER_ID")
    private String masterId;

    @ApiModelProperty(value = "图标")
    private String image;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("备注")
    private String remark;
    
    @ApiModelProperty(value = "功能详情展开解释")
    private List<FunDetailExpVo> detailExps;
    

}
