package com.hirisun.cloud.daas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hirisun.cloud.daas.service.IDaasApplicationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@Api("daas 业务申请审批、业务办理")
@RestController
@RequestMapping("/daas/application")
public class DaasApplicationController {

	@Autowired
	private IDaasApplicationService daasApplicationService;
	
	@ApiIgnore
	@ApiOperation("合并提交saas服务")
    @PostMapping(value = "/submit/merge")
    public void submitMerge(@RequestParam String appInfoId, @RequestParam List<String> shoppingCartIdList) {
		daasApplicationService.submitMerge(appInfoId, shoppingCartIdList);
	}
}
