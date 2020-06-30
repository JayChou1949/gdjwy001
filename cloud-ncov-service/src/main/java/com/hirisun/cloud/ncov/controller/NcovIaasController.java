package com.hirisun.cloud.ncov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.ncov.service.NcovIaasService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("基础设施服务（IAAS）")
@RestController
@RequestMapping("/ncov/iaas")
public class NcovIaasController {

	@Autowired
	private NcovIaasService ncovIaasService;
	
    @ApiOperation("虚拟机")
    @GetMapping(value = "/overview")
    public QueryResponseResult overview(){
        return QueryResponseResult.success(ncovIaasService.getOverview());
    }

    @ApiOperation("虚拟机-支撑情况")
    @GetMapping(value = "/support")
    public QueryResponseResult support(){
        return QueryResponseResult.success(ncovIaasService.getSupport());
    }

}
