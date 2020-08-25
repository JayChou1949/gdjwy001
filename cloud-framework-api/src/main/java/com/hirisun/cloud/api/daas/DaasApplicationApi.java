package com.hirisun.cloud.api.daas;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.ApiOperation;

@FeignClient("cloud-daas-service")
public interface DaasApplicationApi {
	
	@ApiOperation("daas提交合并")
    @PostMapping(value = "/daas/application/submit/merge")
    public void submitMerge(@RequestParam String appInfoId, @RequestParam List<String> shoppingCartIdList);

}
