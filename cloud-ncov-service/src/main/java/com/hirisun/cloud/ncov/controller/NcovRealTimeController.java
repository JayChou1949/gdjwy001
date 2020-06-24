package com.hirisun.cloud.ncov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.model.ncov.vo.HomePageNcovRealtimeVo;
import com.hirisun.cloud.ncov.service.NcovRealtimeService;

@RequestMapping("/ncov/realtime")
@RestController
public class NcovRealTimeController {

	@Autowired
	private NcovRealtimeService ncovRealtimeService;
	
	@RequestMapping("/query")
	public QueryResponseResult realtime() {
		
		HomePageNcovRealtimeVo homePageNcovRealtimeVo = ncovRealtimeService.getHomePageNcovRealtimeVo();
		return QueryResponseResult.success(homePageNcovRealtimeVo);
	}
	
	@RequestMapping("/import")
	public ResponseResult importData(String json) {
		
		try {
			ncovRealtimeService.importNcovRealtimeData(json);
		} catch (Exception e) {
			return ResponseResult.fail();
		}
		
		return ResponseResult.success();
	}
}
