package com.hirisun.cloud.ncov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.model.ncov.vo.HomePageNcovRealtimeVo;
import com.hirisun.cloud.ncov.service.NcovRealtimeService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

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
	
	@ApiImplicitParams({
		@ApiImplicitParam(name="json",value="疫情实时数据json",dataType="String",required=true),
	})
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
