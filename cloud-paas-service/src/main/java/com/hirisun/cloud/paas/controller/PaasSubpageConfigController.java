package com.hirisun.cloud.paas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.paas.bean.PaasSubpageConfig;
import com.hirisun.cloud.paas.service.PaasSubpageConfigService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api("后台-服务管理-基础设施服务(IaaS)-二级页面")
@RequestMapping("/paas/subpage")
@RestController
public class PaasSubpageConfigController {
	
	@Autowired
    private PaasSubpageConfigService paasSubpageConfigService;

    @ApiOperation("新增")
    @PostMapping(value = "/create")
    @ResponseBody
    public ResponseResult create(@LoginUser UserVO user, @RequestBody PaasSubpageConfig paas) {
    	paasSubpageConfigService.savePaasPage(user,paas);
        return ResponseResult.success();
    }


    @ApiOperation("修改二级页面配置信息")
    @PostMapping(value = "/edit")
    @ResponseBody
    public ResponseResult edit(@LoginUser UserVO user, @RequestBody PaasSubpageConfig paas) {
    	paasSubpageConfigService.updateIaasPage(user,paas);
        return ResponseResult.success();
    }

    @ApiOperation("服务配置详情")
    @ApiImplicitParam(name="iaasId", value="服务id", required = true, dataType="String")
    @GetMapping(value = "/detail")
    @ResponseBody
    public ResponseResult detail(@LoginUser UserVO user, @PathVariable String iaasId) {
    	PaasSubpageConfig iaas = paasSubpageConfigService.getDetail(iaasId);
        return QueryResponseResult.success(iaas);
    }
	
}
