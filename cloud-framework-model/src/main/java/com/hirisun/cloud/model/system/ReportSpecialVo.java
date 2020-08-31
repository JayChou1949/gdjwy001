package com.hirisun.cloud.model.system;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("特殊需求Vo")
@Data
public class ReportSpecialVo implements Serializable {

	private static final long serialVersionUID = -189123354709313181L;

	@ApiModelProperty("id")
    private String id;

	@ApiModelProperty("需求名字")
    private String demandName;

	@ApiModelProperty("说明")
    private String explanation;

	@ApiModelProperty("应用(项目)ID ")
    private String projectId;

	@ApiModelProperty("申请信息ID")
    private String appInfoId;

	@ApiModelProperty("创建时间")
    private Date createTime;

	@ApiModelProperty("修改时间")
    private Date modifiedTime;

}
