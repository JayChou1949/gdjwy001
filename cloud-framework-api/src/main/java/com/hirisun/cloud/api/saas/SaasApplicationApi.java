package com.hirisun.cloud.api.saas;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@FeignClient("cloud-saas-service")
public interface SaasApplicationApi {

	@ApiIgnore
	@ApiOperation("合并提交saas服务")
    @PostMapping(value = "/saas/application/submit/merge")
    public void submitMerge(@RequestParam String appInfoId, @RequestParam List<String> shoppingCartIdList);
}
