package com.hirisun.cloud.ncov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.ncov.vo.daas.HomePageDataVo;
import com.hirisun.cloud.ncov.service.NcovDaasService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("首页-数据服务DAAS")
@RestController
@RequestMapping("/ncov/daas")
public class NcovDaasController {

	@Autowired
	private NcovDaasService ncovDaasService;
	
	@ApiOperation(value = "防疫大数据")
    @RequestMapping(value ="/antiepidemic",method = RequestMethod.GET)
    public QueryResponseResult homePage() throws Exception{
		
		HomePageDataVo homePageBigData = ncovDaasService.getHomePageBigData();
		return QueryResponseResult.success(homePageBigData);
	}
	

}
