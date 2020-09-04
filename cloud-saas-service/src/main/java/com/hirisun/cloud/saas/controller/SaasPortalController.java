package com.hirisun.cloud.saas.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.saas.service.SaasService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "saas门户首页")
@RestController
@RequestMapping("/saas/portal")
public class SaasPortalController {

	@Autowired
	private SaasService saasService;
	
	@ApiOperation("门户-应用市场-通用市场/警种应用/地市应用/试用应用")
	@GetMapping(value = "/application")
	public QueryResponseResult saasApplication(@RequestParam(required = false,defaultValue = "") 
				String projectName) {
		List<Map<String, Object>> data = saasService.getApplicationFrontData(projectName);
        return QueryResponseResult.success(data);
	}
}
