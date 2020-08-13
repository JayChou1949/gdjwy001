package com.hirisun.cloud.iaas.controller.store;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirisun.cloud.iaas.service.IaasObjStoreService;
import com.hirisun.cloud.model.iaas.vo.IaasObjStoreVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api("iaas 存储信息")
@RequestMapping("/iaas/obj/store")
@RestController
public class IaasObjStoreController {

	@Autowired
	private IaasObjStoreService iaasObjStoreService;
	
	@ApiIgnore
	@ApiOperation(value = "根据字段匹配数据并排序")
    @ApiResponses(
            @ApiResponse(code = 200, message = "success", response = IaasObjStoreVo.class)
    )
    @PostMapping("/list")
    public List<IaasObjStoreVo> list(@RequestBody IaasObjStoreVo iaasObjStoreVo) {
		List<IaasObjStoreVo> list = iaasObjStoreService.find(iaasObjStoreVo);
		return list;
	}
	
	@ApiIgnore
	@ApiOperation(value = "新增 iaas 对象存储信息", notes = "接口说明")
    @ApiResponses(
            @ApiResponse(code = 200, message = "成功返回id", response = String.class)
    )
    @PostMapping("/save")
    public void save(@RequestBody IaasObjStoreVo iaasObjStoreVo) {
		iaasObjStoreService.save(iaasObjStoreVo);
	}
	
	@ApiIgnore
	@ApiOperation(value = "修改 iaas 对象存储信息", notes = "接口说明")
    @ApiResponses(
            @ApiResponse(code = 200, message = "成功返回id", response = String.class)
    )
    @PostMapping("/update")
    public void update(@RequestBody IaasObjStoreVo iaasObjStoreVo) {
		iaasObjStoreService.update(iaasObjStoreVo);
	}
	
	@ApiIgnore
	@ApiOperation(value = "删除 应用信息 iaas 对象存储信息", notes = "接口说明")
    @ApiResponses(
            @ApiResponse(code = 200, message = "成功返回id", response = String.class)
    )
    @PostMapping("/remove/appInfo")
    public void removeByAppInfo(@RequestBody IaasObjStoreVo iaasObjStoreVo) {
		iaasObjStoreService.removeByAppInfo(iaasObjStoreVo.getAppInfoId());
	}
	
	@ApiIgnore
	@ApiOperation(value = "删除 购物车 iaas 对象存储信息", notes = "接口说明")
    @ApiResponses(
            @ApiResponse(code = 200, message = "成功返回id", response = String.class)
    )
    @PostMapping("/remove/shoppingCart")
    public void removeByShoppingCart(@RequestBody IaasObjStoreVo iaasObjStoreVo) {
		iaasObjStoreService.removeByShoppingCart(iaasObjStoreVo.getAppInfoId());
	}
	
	
}
