package com.hirisun.cloud.model.ncov.vo.daas;

import lombok.Data;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Data
@ApiModel("首页daas防疫大数据-数据建模")
public class DataModelingVo {
    
	@ApiModelProperty(value="总览数据")
    private List<NcovDataOverviewVo> dataModelingOverview;
	
	@ApiModelProperty(value="单位建模排名")
    private List<NcovDataVo> unitModeling;
	
	@ApiModelProperty(value="公共模型建设单位排名")
    private List<NcovDataVo> publicModelConstructionUnit;
	
	@ApiModelProperty(value="模型热度排名")
    private List<NcovDataVo> modelPopularity;
}
