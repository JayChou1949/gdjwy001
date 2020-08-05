package com.hirisun.cloud.model.ncov.vo.daas;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("首页daas防疫大数据-数据接入情况")
public class DataAccessVo implements Serializable{

	private static final long serialVersionUID = 7013936612276048049L;
	
	@ApiModelProperty(value="总条数")
    private Long totalCount;

	@ApiModelProperty(value="总资源数")
    private Integer resourceCount;
    
	@ApiModelProperty(value="昨日增量")
    private Long yesterdayCount;
    
	@ApiModelProperty(value="来源公安")
    private Integer policeCount;
    
	@ApiModelProperty(value="来源政府")
    private Integer govermentCount;
	
	@ApiModelProperty(value="数据类型")
    private Map<String, Map<String,Long>> typeMap;
	
	@ApiModelProperty(value="政府部门数据共享情况")
    private List<NcovDataLongVo> govData;
	
	@ApiModelProperty(value="近七天接入总量")
    private List<Long> lately7Days;
	
	@ApiModelProperty(value="近七天重点数据接入总量")
    private List<Long> latelyFocus;

	@ApiModelProperty(value="时间轴")
    private List<String> time;
	
	@ApiModelProperty(value="三级页面公安")
    private List<List<Object>> Level3Police;
	
	@ApiModelProperty(value="三级页面其他")
    private List<List<Object>> Level3Other;
	
}
