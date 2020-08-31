package com.hirisun.cloud.iaas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hirisun.cloud.iaas.service.IIaasReportService;
import com.hirisun.cloud.model.iaas.vo.IaasReportVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@Api("IaaS资源报表")
@RequestMapping("/iaas/report")
@RestController
public class IaasReportController {

	@Autowired
	private IIaasReportService iaasReportService;
	
	@ApiIgnore
	@ApiOperation(value = "iaas资源上报", notes = "接口说明")
    @GetMapping("/find")
    public List<IaasReportVo> find(@RequestParam(value = "appInfoId")String appInfoId){
    	List<IaasReportVo> list = iaasReportService.findIaasReportByAppInfoId(appInfoId);
    	return list;
	}
}
