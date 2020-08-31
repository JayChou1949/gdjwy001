package com.hirisun.cloud.api.iaas;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hirisun.cloud.model.iaas.vo.IaasReportVo;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@FeignClient("cloud-iaas-service")
public interface IaasReportApi {

	@ApiIgnore
	@ApiOperation(value = "获取iaas资源上报", notes = "接口说明")
    @GetMapping("/find")
    public List<IaasReportVo> find(@RequestParam(value = "appInfoId")String appInfoId);
}
