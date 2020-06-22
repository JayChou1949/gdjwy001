package com.hirisun.cloud.model.ncov.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 首页疫情实时数据
 * @author cjh
 *
 */
public class HomePageNcovRealtimeVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 260770784421597458L;
	
	private NcovRealtimeVo countryTotal; //全国统计数据
	private NcovRealtimeVo gdTotal; //广东统计数据
	private List<NcovRealtimeVo> provinceList; //所有省份地图数据
	private List<NcovRealtimeVo> gdList; //广东省列表数据
	
	public NcovRealtimeVo getCountryTotal() {
		return countryTotal;
	}
	public void setCountryTotal(NcovRealtimeVo countryTotal) {
		this.countryTotal = countryTotal;
	}
	public NcovRealtimeVo getGdTotal() {
		return gdTotal;
	}
	public void setGdTotal(NcovRealtimeVo gdTotal) {
		this.gdTotal = gdTotal;
	}
	public List<NcovRealtimeVo> getProvinceList() {
		return provinceList;
	}
	public void setProvinceList(List<NcovRealtimeVo> provinceList) {
		this.provinceList = provinceList;
	}
	public List<NcovRealtimeVo> getGdList() {
		return gdList;
	}
	public void setGdList(List<NcovRealtimeVo> gdList) {
		this.gdList = gdList;
	}
	
	
	
}
