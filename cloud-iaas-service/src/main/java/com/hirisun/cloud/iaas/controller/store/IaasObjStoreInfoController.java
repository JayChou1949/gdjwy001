package com.hirisun.cloud.iaas.controller.store;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirisun.cloud.iaas.service.IaasObjStoreInfoService;
import com.hirisun.cloud.model.iaas.vo.IaasObjStoreVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api("IaaS 对象存储申请信息")
@RequestMapping("/iaas/obj/store/info")
@RestController
public class IaasObjStoreInfoController {

	@Autowired
	private IaasObjStoreInfoService iaasObjStoreInfoService;
	
//	@ApiIgnore
//	@ApiOperation(value = "根据字段匹配数据并排序")
//    @ApiResponses(
//            @ApiResponse(code = 200, message = "success", response = IaasObjStoreVo.class)
//    )
//    @PostMapping("/list")
//    public IaasObjStoreInfo list(@RequestBody IaasObjStoreVo iaasObjStoreVo) {
//		IaasObjStoreInfo info = iaasObjStoreInfoService.getOne(iaasObjStoreVo.getAppInfoId());
//		return info;
//	}
}
