package com.hirisun.cloud.ncov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.ncov.vo.daas.DataAccessVo;
import com.hirisun.cloud.model.ncov.vo.daas.DataGovernanceVo;
import com.hirisun.cloud.model.ncov.vo.daas.DataModelingVo;
import com.hirisun.cloud.model.ncov.vo.daas.DataServiceVo;
import com.hirisun.cloud.model.ncov.vo.daas.DataSharingVo;
import com.hirisun.cloud.model.ncov.vo.daas.HomePageDataVo;
import com.hirisun.cloud.ncov.service.NcovDaasService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("首页-数据服务DAAS")
@RestController
@RequestMapping("/ncov/daas")
public class NcovDaasController {

	@Autowired
	private NcovDaasService ncovDaasService;
	
	@ApiOperation(value = "防疫大数据")
	@ApiResponses(//响应参数说明
            @ApiResponse(code=200,message="success",response= HomePageDataVo.class)
    )
    @RequestMapping(value ="/antiepidemic",method = RequestMethod.GET)
    public QueryResponseResult homePage() throws Exception{
		
		HomePageDataVo homePageBigData = ncovDaasService.getHomePageBigData();
		return QueryResponseResult.success(homePageBigData);
	}
	
	@ApiOperation(value = "数据接入")
	@ApiResponses(//响应参数说明
            @ApiResponse(code=200,message="success",response= DataAccessVo.class)
    )
    @RequestMapping(value ="/access",method = RequestMethod.GET)
    public QueryResponseResult access() throws Exception{
		
		DataAccessVo dataAccessVo = ncovDaasService.getDataAccessVo();
		return QueryResponseResult.success(dataAccessVo);
	}
	
	@ApiOperation(value = "数据治理")
	@ApiResponses(//响应参数说明
            @ApiResponse(code=200,message="success",response= DataGovernanceVo.class)
    )
    @RequestMapping(value ="/governance",method = RequestMethod.GET)
    public QueryResponseResult governance() throws Exception{
		
		DataGovernanceVo dataGovernance = ncovDaasService.getDataGovernance();
		return QueryResponseResult.success(dataGovernance);
	}
	
	@ApiOperation(value = "数据服务")
	@ApiResponses(//响应参数说明
            @ApiResponse(code=200,message="success",response= DataServiceVo.class)
    )
    @RequestMapping(value ="/service",method = RequestMethod.GET)
    public QueryResponseResult service() throws Exception{
		
		DataServiceVo dataService = ncovDaasService.getDataService();
		return QueryResponseResult.success(dataService);
	}
	
	@ApiOperation(value = "数据建模")
	@ApiResponses(//响应参数说明
            @ApiResponse(code=200,message="success",response= DataModelingVo.class)
    )
    @RequestMapping(value ="/modeling",method = RequestMethod.GET)
    public QueryResponseResult modeling() throws Exception{
		
		DataModelingVo dataModeling = ncovDaasService.getDataModeling();
		return QueryResponseResult.success(dataModeling);
	}
	
	@ApiOperation(value = "数据共享")
	@ApiResponses(//响应参数说明
            @ApiResponse(code=200,message="success",response= DataSharingVo.class)
    )
    @RequestMapping(value ="/sharing",method = RequestMethod.GET)
    public QueryResponseResult sharing() throws Exception{
		
		DataSharingVo dataSharing = ncovDaasService.getDataSharing();
		return QueryResponseResult.success(dataSharing);
	}
	

}
