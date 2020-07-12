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
	private NcovRealtimeVo countryTotal; 
	
	@ApiModelProperty(value="今天城市统计数据")
	private NcovRealtimeVo cityTotal; 
	
	@ApiModelProperty(value="所有省份地图数据")
	private List<NcovRealtimeVo> provinceList; 
	
	@ApiModelProperty(value="广东省列表数据")
	private List<NcovRealtimeVo> cityList; 
	
	public NcovRealtimeVo getCountryTotal() {
		return countryTotal;
	}
	public void setCountryTotal(NcovRealtimeVo countryTotal) {
		this.countryTotal = countryTotal;
	}
	public NcovRealtimeVo getCityTotal() {
		return cityTotal;
	}
	public void setCityTotal(NcovRealtimeVo cityTotal) {
		this.cityTotal = cityTotal;
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
