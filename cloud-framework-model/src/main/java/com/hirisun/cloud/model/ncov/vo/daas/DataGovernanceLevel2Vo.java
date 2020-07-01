package com.hirisun.cloud.model.ncov.vo.daas;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value="更新方式")
public class DataGovernanceLevel2Vo implements Serializable{
    
	private static final long serialVersionUID = -3593462851760297813L;
	
	@ApiModelProperty(value="类型")
    private String type;
    
	@ApiModelProperty(value="数量")
    private String num;
    
	@ApiModelProperty(value="总数")
    private String sum;
    
	@ApiModelProperty(value="百分比")
    private String percentage;
}
