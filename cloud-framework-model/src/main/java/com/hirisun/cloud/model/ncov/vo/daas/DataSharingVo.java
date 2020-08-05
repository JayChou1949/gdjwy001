package com.hirisun.cloud.model.ncov.vo.daas;

import lombok.Data;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Data
@ApiModel("首页daas防疫大数据-数据服务")
public class DataSharingVo {
    
	@ApiModelProperty(value="总览数据")
    private List<NcovDataOverviewVo> dataSharingOverview;
    
	@ApiModelProperty(value="单位下载排名")
    private List<NcovDataVo> unitDownload;
	
	@ApiModelProperty(value="高频使用资源排名")
    private List<NcovDataVo> highFrequencyUse;
}
