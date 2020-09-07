package com.hirisun.cloud.model.app.vo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("应用场景")
public class AppSceneVo implements Serializable {

	private static final long serialVersionUID = 7568364209316337621L;

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
    
    @ApiModelProperty(value = "应用场景优势")
    private List<AppSupreVo> supres;

}
