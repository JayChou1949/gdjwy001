package com.hirisun.cloud.ncov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.ncov.vo.paas.NcovClusterAppVo;
import com.hirisun.cloud.model.ncov.vo.paas.NcovClusterOverviewVo;
import com.hirisun.cloud.model.ncov.vo.paas.NcovClusterResourceVo;
import com.hirisun.cloud.ncov.service.NcovPaasService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(" 平台服务（PAAS）疫情大数据集群")
@RestController
@RequestMapping("/ncov/paas")
public class NcovPaasController {

	@Autowired
	private NcovPaasService ncovPaasService;
	
	@SuppressWarnings("unchecked")
	@ApiOperation("数据概况")
    @ApiResponses(//响应参数说明
            @ApiResponse(code=200,message="success")
    )
	@GetMapping(value = "/overview")
	public QueryResponseResult<NcovClusterOverviewVo> overview() {
		return QueryResponseResult.success(ncovPaasService.getOverview());
	}

	@SuppressWarnings("unchecked")
	@ApiOperation("集群资源分配详情")
    @ApiResponses(//响应参数说明
            @ApiResponse(code=200,message="success")
    )
	@GetMapping(value = "/resource")
	public QueryResponseResult<NcovClusterResourceVo> resource() {
		return QueryResponseResult.success(ncovPaasService.getResource());
	}

	@SuppressWarnings("unchecked")
	@ApiOperation("疫情防控,流动人口")
	@ApiResponses(//响应参数说明
            @ApiResponse(code=200,message="success")
    )
	@GetMapping(value = "/app")
	public QueryResponseResult<Page<NcovClusterAppVo>> app(@RequestParam(required = false, defaultValue = "3") long pageSize,
			@RequestParam(required = false, defaultValue = "1") long current) {
		return QueryResponseResult.success(ncovPaasService.getAppDetailList(pageSize, current));
	}
	
	
}
