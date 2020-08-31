package com.hirisun.cloud.api.paas;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hirisun.cloud.model.pass.vo.PaasReportVo;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@FeignClient("cloud-paas-service")
public interface PaasReportApi {

	@ApiIgnore
	@ApiOperation(value = "获取paas资源上报", notes = "接口说明")
    @GetMapping("/paas/report/find")
    public List<PaasReportVo> find(@RequestParam(value = "appInfoId")String appInfoId);
	
	@ApiIgnore
	@ApiOperation(value = "根据appInfoId删除paas资源上报", notes = "接口说明")
    @GetMapping("/paas/report/remove")
    public void remove(@RequestParam(value = "appInfoId")String appInfoId);
	
	@ApiIgnore
	@ApiOperation(value = "批量保存", notes = "接口说明")
    @GetMapping("/paas/report/batchSave")
    public void batchSave(@RequestParam(value = "voList")List<PaasReportVo> voList);
	
	@ApiIgnore
	@ApiOperation(value = "paasReport导出", notes = "接口说明")
    @GetMapping("/paas/report/statistics")
	public List<PaasReportVo> doStatistics(@RequestParam(value = "appInfoId")String appInfoId);
}
