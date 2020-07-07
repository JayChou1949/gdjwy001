package com.hirisun.cloud.model.ncov.vo.daas;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("首页daas防疫大数据")
public class HomePageDataVo implements Serializable{
    
	private static final long serialVersionUID = -7938234581407239808L;
	
	@ApiModelProperty(value="总条数")
    private Long totalCount;
    
	@ApiModelProperty(value="总资源数")
    private Integer resourceCount;
    
	@ApiModelProperty(value="昨日增量")
    private Long yesterdayCount;
    
	@ApiModelProperty(value="疫情服务数")
    private Integer serviceCount;
    
	@ApiModelProperty(value="支撑单位数")
    private Integer unitCount;
    
	@ApiModelProperty(value="总调用数")
    private Long allCall;
    
	@ApiModelProperty(value="昨日调用数")
    private Long yesterdayCall;

	@ApiModelProperty(value="更新方式")
    private List<DataGovernanceLevel2Vo> updateType;
    
	@ApiModelProperty(value="更新周期")
    private List<DataGovernanceLevel2Vo> updateCycle;

	@ApiModelProperty(value="总览数据")
    private List<NcovDataOverviewVo> dataModelingOverview;

	@ApiModelProperty(value="总览数据")
    private List<NcovDataOverviewVo> dataSharingOverview;
}
