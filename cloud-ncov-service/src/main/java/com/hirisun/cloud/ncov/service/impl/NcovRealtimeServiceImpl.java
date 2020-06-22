package com.hirisun.cloud.ncov.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirisun.cloud.model.ncov.vo.HomePageNcovRealtimeVo;
import com.hirisun.cloud.ncov.mapper.NcovRealtimeMapper;
import com.hirisun.cloud.ncov.service.NcovRealtimeService;

@Service
public class NcovRealtimeServiceImpl implements NcovRealtimeService {

	@Autowired
	private NcovRealtimeMapper ncovRealtimeMapper;
	
	@Override
	public HomePageNcovRealtimeVo getHomePageNcovRealtimeVo() {
		
		return null;
	}

	@Override
	public boolean importNcovRealtimeData() {
		// TODO Auto-generated method stub
		return false;
	}

}
