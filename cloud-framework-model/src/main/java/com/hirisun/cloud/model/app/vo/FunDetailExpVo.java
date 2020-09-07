package com.hirisun.cloud.model.app.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("功能详情展开解释")
public class FunDetailExpVo implements Serializable {

	private static final long serialVersionUID = 8054694937424223943L;

	@ApiModelProperty(value = "id")
    private String id;

	@ApiModelProperty(value = "iaasId")
	private String iaasId;

	@ApiModelProperty("appId")
    private String appId;

	@ApiModelProperty("标题")
    private String title;

	@ApiModelProperty("描述")
    private String description;

	@ApiModelProperty("状态")
    private String status;

	@ApiModelProperty("备注")
    private String remark;

}
