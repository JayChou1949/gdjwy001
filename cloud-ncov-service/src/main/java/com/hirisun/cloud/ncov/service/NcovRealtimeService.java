package com.hirisun.cloud.ncov.service;

import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.model.ncov.vo.HomePageNcovRealtimeVo;
import com.hirisun.cloud.ncov.bean.NcovRealtime;

public interface NcovRealtimeService {

	public ResponseResult updateNcovRealtimeById(NcovRealtime ncovRealtime)throws CustomException;
	
	public NcovRealtime getNcovRealtimeById(String id);
	
	public ResponseResult deleteNcovRealtimeById(String id)throws CustomException;
	
	public HomePageNcovRealtimeVo getHomePageNcovRealtimeVo();
	
	public ResponseResult importNcovRealtimeData(String json)throws CustomException;
	
	
}
