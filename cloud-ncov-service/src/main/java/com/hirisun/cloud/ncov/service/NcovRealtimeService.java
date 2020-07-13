package com.hirisun.cloud.ncov.service;

import java.util.List;

import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.model.ncov.vo.realtime.HomePageNcovRealtimeVo;
import com.hirisun.cloud.model.ncov.vo.realtime.NcovRealtimeVo;
import com.hirisun.cloud.ncov.bean.NcovRealtime;

public interface NcovRealtimeService {

	public NcovRealtime getNcovRealtimeById(String id);
	
	public HomePageNcovRealtimeVo getHomePageNcovRealtimeVo();
	
	public ResponseResult importNcovRealtimeData(List<NcovRealtimeVo> list)throws CustomException;
	
	public ResponseResult editNcovRealtime(NcovRealtimeVo vo);
	
	public HomePageNcovRealtimeVo setHomePageNcovRealtimeCache();
	
	public List<NcovRealtimeVo> findNcovRealtimeByRegionType(int regionType);
	
	
}
