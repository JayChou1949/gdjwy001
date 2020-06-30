package com.hirisun.cloud.model.ncov.vo.realtime;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 首页疫情实时数据
 * @author cjh
 *
 */
@ApiModel("首页疫情实时数据")
public class HomePageNcovRealtimeVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 260770784421597458L;
	
	@ApiModelProperty(value="今天全国统计数据")
	private NcovRealtimeVo toDayCountryTotal; 
	
	@ApiModelProperty(value="昨天全国统计数据")
	private NcovRealtimeVo yesterdayCountryTotal; 
	
	@ApiModelProperty(value="今天城市统计数据")
	private NcovRealtimeVo toDayCityTotal; 
	
	@ApiModelProperty(value="昨天城市统计数据")
	private NcovRealtimeVo  yesterdayCityTotal; 
	
	@ApiModelProperty(value="所有省份地图数据")
	private List<NcovRealtimeVo> provinceList; 
	
	@ApiModelProperty(value="广东省列表数据")
	private List<NcovRealtimeVo> cityList; 
	
	public NcovRealtimeVo getToDayCountryTotal() {
		return toDayCountryTotal;
	}
	public void setToDayCountryTotal(NcovRealtimeVo toDayCountryTotal) {
		this.toDayCountryTotal = toDayCountryTotal;
	}
	public NcovRealtimeVo getYesterdayCountryTotal() {
		return yesterdayCountryTotal;
	}
	public void setYesterdayCountryTotal(NcovRealtimeVo yesterdayCountryTotal) {
		this.yesterdayCountryTotal = yesterdayCountryTotal;
	}
	public NcovRealtimeVo getToDayCityTotal() {
		return toDayCityTotal;
	}
	public void setToDayCityTotal(NcovRealtimeVo toDayCityTotal) {
		this.toDayCityTotal = toDayCityTotal;
	}
	public NcovRealtimeVo getYesterdayCityTotal() {
		return yesterdayCityTotal;
	}
	public void setYesterdayCityTotal(NcovRealtimeVo yesterdayCityTotal) {
		this.yesterdayCityTotal = yesterdayCityTotal;
	}
	public List<NcovRealtimeVo> getProvinceList() {
		return provinceList;
	}
	public void setProvinceList(List<NcovRealtimeVo> provinceList) {
		this.provinceList = provinceList;
	}
	public List<NcovRealtimeVo> getCityList() {
		return cityList;
	}
	public void setCityList(List<NcovRealtimeVo> cityList) {
		this.cityList = cityList;
	}
	
}
