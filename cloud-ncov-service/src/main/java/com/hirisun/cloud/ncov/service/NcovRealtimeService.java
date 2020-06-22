package com.hirisun.cloud.ncov.service;

import com.hirisun.cloud.model.ncov.vo.HomePageNcovRealtimeVo;

public interface NcovRealtimeService {

	public HomePageNcovRealtimeVo getHomePageNcovRealtimeVo();
	
	public boolean importNcovRealtimeData();
	
	
}
