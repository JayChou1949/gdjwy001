package com.hirisun.cloud.model.ncov.vo.daas;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("首页daas防疫大数据-数据服务")
public class DataServiceVo implements Serializable{
    
	private static final long serialVersionUID = -8534397663296866103L;
	
	@ApiModelProperty(value="疫情服务数")
    private Integer serviceCount;
    
	@ApiModelProperty(value="支撑公安部门数")
    private Integer policeCount;
	
	@ApiModelProperty(value="支撑政府单位数")
    private Integer govCount;
	
	@ApiModelProperty(value="公安部门调用数")
    private Long policeCall;
	
	@ApiModelProperty(value="政府单位调用数")
    private Long govCall;
	
	@ApiModelProperty(value="昨日调用数")
    private Long yesterdayCall;
	
	@ApiModelProperty(value="各省直单位调用数")
    private List<NcovDataLongVo> govDirect;
	
	@ApiModelProperty(value="各地市调用数")
    private List<NcovDataLongVo> city;
	
	@ApiModelProperty(value="各公安警种调用数")
    private List<CallAndNameVo> police;
	
	@ApiModelProperty(value="政府部门近七天")
    private List<CallAndTimeVo> govLately;
	
	@ApiModelProperty(value="公安部门近七天")
    private List<CallAndTimeVo> policeLately;
}
