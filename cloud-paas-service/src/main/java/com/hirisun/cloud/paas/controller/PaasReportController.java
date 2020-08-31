package com.hirisun.cloud.paas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hirisun.cloud.model.pass.vo.PaasReportVo;
import com.hirisun.cloud.paas.service.IPaasReportService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@Api("PaaS资源报表")
@RequestMapping("/paas/report")
@RestController
public class PaasReportController {

	@Autowired
	private IPaasReportService paasReportService;
	
	@ApiIgnore
	@ApiOperation(value = "paas资源上报", notes = "接口说明")
    @GetMapping("/find")
    public List<PaasReportVo> find(@RequestParam(value = "appInfoId")String appInfoId){
    	List<PaasReportVo> list = paasReportService.findPaasReportByAppInfoId(appInfoId);
    	return list;
	}
	
	@ApiIgnore
	@ApiOperation(value = "根据appInfoId删除paas资源上报", notes = "接口说明")
    @GetMapping("/remove")
    public void remove(@RequestParam(value = "appInfoId")String appInfoId){
    	paasReportService.remove(appInfoId);
	}
	
	@ApiIgnore
	@ApiOperation(value = "批量保存", notes = "接口说明")
    @GetMapping("/batchSave")
    public void batchSave(@RequestParam(value = "voList")List<PaasReportVo> voList){
    	paasReportService.saveBatch(voList);
	}
	
	@ApiIgnore
	@ApiOperation(value = "paasReport导出", notes = "接口说明")
    @GetMapping("/statistics")
	public List<PaasReportVo> doStatistics(@RequestParam(value = "appInfoId")String appInfoId){
		List<PaasReportVo> doStatistics = paasReportService.doStatistics(appInfoId);
		return doStatistics;
		
	}
	
}
