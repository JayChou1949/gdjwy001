package com.hirisun.cloud.iaas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.iaas.bean.Iaas;
import com.hirisun.cloud.model.user.UserVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("IaaS资源管理")
@RequestMapping("/iaas/resource")
@RestController
public class IaasResourceController {

//	@ApiOperation(value = "创建 iaas 配置", notes = "接口说明")
//    @ApiResponses(
//            @ApiResponse(code = 200, message = "成功返回id", response = String.class)
//    )
//    @GetMapping("/limit/quota")
//    public QueryResponseResult create(@RequestParam(value = "formNum")String formNum,
//    								  @RequestParam(value = "area")String area,
//    								  @RequestParam(value = "policeCategory",required = false)String policeCategory,
//    								  @RequestParam(value = "nationalSpecialProject",required = false) String nationalSpecialProject,
//    								  @RequestParam(value = "clusterName",required = false) String clusterName) {
//		
//		String iaasConfigId = iaasConfigService.create(user, iaas);
//        return QueryResponseResult.success(iaasConfigId);
//		
//	}
}
