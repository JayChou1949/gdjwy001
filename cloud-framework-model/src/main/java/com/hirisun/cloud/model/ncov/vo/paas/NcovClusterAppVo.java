package com.hirisun.cloud.model.ncov.vo.paas;

import java.util.LinkedHashMap;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("疫情应用资源供给详情")
public class NcovClusterAppVo {
	
	@ApiModelProperty(value="应用名称")
	private String appName;

	@ApiModelProperty(value="cpu")
    private Integer cpu;

	@ApiModelProperty(value="内存")
    private Double memory;

	@ApiModelProperty(value="存储")
    private Double storage;

	@ApiModelProperty(value="最近cpu使用率")
    private LinkedHashMap<String,Double> cpuRecent;

	@ApiModelProperty(value="最近内存使用率")
    private LinkedHashMap<String,Double> memRecent;
	
}
