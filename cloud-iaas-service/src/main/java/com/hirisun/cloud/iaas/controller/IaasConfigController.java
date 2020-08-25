package com.hirisun.cloud.iaas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.iaas.bean.IaasConfig;
import com.hirisun.cloud.iaas.service.IaasConfigService;
import com.hirisun.cloud.model.user.UserVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("后台-服务管理-基础设施服务(IaaS)")
@RequestMapping("/iaas/config")
@RestController
public class IaasConfigController {

	@Autowired
	private IaasConfigService iaasConfigService;
	
	@ApiOperation(value = "创建 iaas 配置", notes = "接口说明")
    @ApiResponses(
            @ApiResponse(code = 200, message = "成功返回id", response = String.class)
    )
    @GetMapping("/create")
    public QueryResponseResult create(@LoginUser UserVO user,@ModelAttribute IaasConfig iaas) {
		
		String iaasConfigId = iaasConfigService.create(user, iaas);
        return QueryResponseResult.success(iaasConfigId);
		
	}
	
	@ApiOperation("操作-上/下线")
	@ApiResponses(
            @ApiResponse(code = 200, message = "success", response = String.class)
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="服务id", required = true,paramType="form", dataType="String"),
            @ApiImplicitParam(name="result", value="操作结果 1:上线,其它:下线", required = true,paramType="form", dataType="String"),
            @ApiImplicitParam(name="remark", value="操作描述",paramType="form", dataType="String"),
    })
    @PostMapping(value = "/publish")
    @ResponseBody
    public ResponseResult publish(@LoginUser UserVO user, String id, Integer result,
                     String remark) {
        if (result == null) {
            return QueryResponseResult.fail("请选择操作结果");
        }
        iaasConfigService.publish(user, id, result, remark);
        return QueryResponseResult.success();
    }
	
	@ApiOperation("操作-上/下移")
	@ApiResponses(
            @ApiResponse(code = 200, message = "success", response = String.class)
    )
	@PostMapping(value = "/sort")
	@ApiImplicitParams({
        @ApiImplicitParam(name="id", value="iaas 配置 id", required = true,paramType="form", dataType="String"),
        @ApiImplicitParam(name="ope", value="操作结果 up:上移,down:下移", required = true,paramType="form", dataType="String"),
	})
    @ResponseBody
    public ResponseResult sort(String id,String ope) {
		iaasConfigService.serviceSort(id,ope);
        return QueryResponseResult.success();
    }
	
	
    @ApiOperation("分页查询iaas 服务列表")
    @ApiResponses(
            @ApiResponse(code = 200, message = "success", response = IaasConfig.class)
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", paramType="form", dataType="String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="form", dataType="String"),
            @ApiImplicitParam(name="status", value="状态, 0:审核中 1: 待上线 2: 上线 3:驳回 4:删除", required = true, paramType="form", dataType="String"),
            @ApiImplicitParam(name="name", value="服务名", paramType="form", dataType="String"),
            @ApiImplicitParam(name = "subType",value = "子类",paramType = "form", dataType = "String")
    })
    @PostMapping(value = "/page")
    @ResponseBody
    public QueryResponseResult page(@LoginUser UserVO user, @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                  @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                  @RequestParam(required = false, defaultValue = "1") Integer status,
                  @RequestParam(required = false) String name, @RequestParam(required = false) String subType) {
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 20;
        }
        IPage<IaasConfig> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page = iaasConfigService.getPage(page, user, status, name, subType);
        return QueryResponseResult.success(page);
    }
	
    @ApiOperation("删除服务")
    @ApiResponses(
            @ApiResponse(code = 200, message = "成功返回id", response = String.class)
    )
    @ApiImplicitParam(name="id", value="服务id", required = true, paramType="form",dataType="String")
    @PostMapping(value = "/delete")
    @ResponseBody
    public ResponseResult delete(@LoginUser UserVO user,String id) {
    	iaasConfigService.delete(user,id);
        return QueryResponseResult.success();
    }
    
    @ApiOperation("修改服务信息")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult edit(@LoginUser UserVO user, @ModelAttribute IaasConfig iaas) {
    	String id = iaasConfigService.edit(user, iaas);
    	return QueryResponseResult.success(id);
    }
    
    
    @ApiOperation("iaas 服务信息详情")
    @ApiResponses(
            @ApiResponse(code = 200, message = "success",response = IaasConfig.class)
    )
    @ApiImplicitParam(name="id", value="服务id", required = true,dataType="String")
    @PostMapping(value = "/detail")
    @ResponseBody
    public ResponseResult detail(@LoginUser UserVO user,String id) {
        IaasConfig iaas = iaasConfigService.getDetail(user, id);
        return QueryResponseResult.success(iaas);
    }
    
    @ApiOperation("流程配置")
    @ApiResponses(
            @ApiResponse(code = 200, message = "success",response = IaasConfig.class)
    )
    @ApiImplicitParams({ @ApiImplicitParam(name="id", value="服务id", required = true, paramType="form", dataType="String"),
            @ApiImplicitParam(name="flowId", value="流程id", required = true, paramType="form", dataType="String")})
    @PostMapping(value = "/set/workflow")
    @ResponseBody
    public ResponseResult setFlow(String id,String flowId) {
    	IaasConfig iaas = iaasConfigService.setWorkflow(id, flowId);
        return QueryResponseResult.success(iaas);
    }
	
}
