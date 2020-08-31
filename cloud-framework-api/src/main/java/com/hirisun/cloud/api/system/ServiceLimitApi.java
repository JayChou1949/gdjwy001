package com.hirisun.cloud.api.system;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@FeignClient("cloud-system-service")
public interface ServiceLimitApi {
	
	@ApiIgnore
    @ApiOperation("增加额度")
    @GetMapping(value = "/system/resource/limit/increaseQuota")
    public void increaseQuota(@RequestParam(value = "appInfoId") String appInfoId,
    		@RequestParam(value = "area")String area,
    		@RequestParam(value = "policeCategory")String policeCategory,
    		@RequestParam(value = "nationalSpecialProject")String nationalSpecialProject);
	
}
