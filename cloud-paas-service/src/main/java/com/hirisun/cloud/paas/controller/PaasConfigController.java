package com.hirisun.cloud.paas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.contains.ReviewStatus;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.paas.bean.Paas;
import com.hirisun.cloud.paas.service.PaasService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("后台-服务管理-平台服务(PaaS)")
@RequestMapping("/paas/config")
@RestController
public class PaasConfigController {

	@Autowired
	private PaasService paasConfigService;
	
	@ApiOperation("新增服务配置")
	@ApiResponses(
            @ApiResponse(code = 200, message = "成功返回id", response = String.class)
    )
    @PutMapping(value = "/create")
    @ResponseBody
    public QueryResponseResult create(@LoginUser UserVO user, @RequestBody Paas paas) {
		paas.setCreator(user.getIdcard());
		String passId = paasConfigService.create(paas);
        return QueryResponseResult.success(passId);
    }
	
	@ApiImplicitParams({
        @ApiImplicitParam(name="id", value="iaas 配置 id", required = true,dataType="String"),
        @ApiImplicitParam(name="ope", value="操作结果 up:上移,down:下移", required = true,dataType="String"),
	})
	@ApiResponses(
            @ApiResponse(code = 200, message = "success", response = ResponseResult.class)
    )
	@PostMapping(value = "/sort")
    @ResponseBody
    public ResponseResult sort(String id,String ope) {
		paasConfigService.serviceSort(id,ope);
		return QueryResponseResult.success();
    }
	
	@ApiOperation("上/下线")
	@ApiResponses(
            @ApiResponse(code = 200, message = "success", response = ResponseResult.class)
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="服务id", required = true, dataType="String"),
            @ApiImplicitParam(name="result", value="操作结果 1:上线,其它:下线", required = true, dataType="String"),
            @ApiImplicitParam(name="remark", value="操作描述", dataType="String"),
    })
	@PostMapping(value = "/publish")
    @ResponseBody
    public ResponseResult publish(@LoginUser UserVO user,String id, Integer result,
                     String remark) {
        if (result == null) {
            return QueryResponseResult.fail("请选择操作结果");
        }
        paasConfigService.publish(user, id, result, remark);
        return QueryResponseResult.success();
    }
	
	/**
     * 分页
     * @param status {@link ReviewStatus}
     */
    @ApiOperation("分页查询服务列表")
    @ApiResponses(
            @ApiResponse(code = 200, message = "分页数据", response = Paas.class)
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", dataType="String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", dataType="String"),
            @ApiImplicitParam(name="status", value="状态,  1: 待上线 2: 上线 ", required = true, dataType="String"),
            @ApiImplicitParam(name="name", value="服务名", dataType="String"),
            @ApiImplicitParam(name = "subType",value = "子类",dataType = "String")
    })
    @GetMapping(value = "/page")
    @ResponseBody
    public QueryResponseResult page(@LoginUser UserVO user, @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                  @RequestParam(required = false, defaultValue = "20") Integer pageSize, Integer status,
                  @RequestParam(required = false) String name,@RequestParam(required = false) String subType) {
        IPage<Paas> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page = paasConfigService.getPage(page, user, status, name,subType);
        return QueryResponseResult.success(page);
    }
    
    @ApiOperation("删除服务")
    @ApiResponses(
            @ApiResponse(code = 200, message = "success", response = ResponseResult.class)
    )
    @ApiImplicitParam(name="id", value="服务id", required = true,dataType="String")
    @PostMapping(value = "/delete")
    @ResponseBody
    public ResponseResult delete(@LoginUser UserVO user,String id) {
    	paasConfigService.delete(user, id);
    	return QueryResponseResult.success();
    }
    
    @ApiOperation("修改服务信息")
    @ApiResponses(
            @ApiResponse(code = 200, message = "成功返回id", response = ResponseResult.class)
    )
    @PutMapping(value = "/edit")
    @ResponseBody
    public ResponseResult edit(@LoginUser UserVO user, @RequestBody Paas paas) {
    	paasConfigService.edit(paas);
    	return QueryResponseResult.success();
    }
    
    @ApiOperation("服务信息详情")
    @ApiResponses(
            @ApiResponse(code = 200, message = "返回 paas 配置", response = Paas.class)
    )
    @ApiImplicitParam(name="id", value="服务id", required = true,dataType="String")
    @GetMapping(value = "/detail")
    @ResponseBody
    public QueryResponseResult detail(@LoginUser UserVO user,String id) {
    	Paas detail = paasConfigService.getDetail(user, id);
    	return QueryResponseResult.success(detail);
    }
    
    @ApiOperation("流程配置-废弃")
    @ApiResponses(
            @ApiResponse(code = 200, message = "返回 paas 配置", response = Paas.class)
    )
    @ApiImplicitParams({ @ApiImplicitParam(name="id", value="服务id", required = true, dataType="String"),
            @ApiImplicitParam(name="flowId", value="流程id", required = true, dataType="String")})
    @PostMapping(value = "/set/workflow")
    @ResponseBody
    public QueryResponseResult setflow(@LoginUser UserVO user, String id,String flowId) {
    	Paas paasConfig = paasConfigService.setWorkflow(id, flowId);
        return QueryResponseResult.success(paasConfig);
    }
	
}
