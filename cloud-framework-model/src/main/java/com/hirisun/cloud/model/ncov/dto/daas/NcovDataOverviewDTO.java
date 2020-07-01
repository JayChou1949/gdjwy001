package com.hirisun.cloud.model.ncov.dto.daas;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("总览数据")
public class NcovDataOverviewDTO implements Serializable{
    
	private static final long serialVersionUID = 4817638782423178171L;
	
	@ApiModelProperty(value="名称")
    private String name;
    
	@ApiModelProperty(value="数量")
    private String count;
    
	@ApiModelProperty(value="单位")
    private String unit;
}
