package com.hirisun.cloud.model.ncov.vo.daas;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("首页daas防疫大数据-数据治理")
public class DataGovernanceVo implements Serializable{

	private static final long serialVersionUID = -7497543992187814927L;

	@ApiModelProperty(value="采集单位名称")
    private Set<String> names;

	@ApiModelProperty(value="数据汇总")
    private List<NcovDataVo> dataSummary;

	@ApiModelProperty(value="更新方式")
    private List<DataGovernanceLevel2Vo> updateType;

	@ApiModelProperty(value="更新周期")
    private List<DataGovernanceLevel2Vo> updateCycle;
}
