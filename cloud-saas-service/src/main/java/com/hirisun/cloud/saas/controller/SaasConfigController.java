package com.hirisun.cloud.saas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.contains.ReviewStatus;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.saas.bean.Saas;
import com.hirisun.cloud.saas.service.SaasService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags ="后台-服务管理-基础设施服务(SaaS)")
@RestController
@RequestMapping("/saas/config")
public class SaasConfigController {

	@Autowired
    private SaasService saasConfigService;

    @ApiOperation("新增服务")
    @ApiResponses(
            @ApiResponse(code = 200, message = "success", response = String.class)
    )
    @PutMapping(value = "/create")
    public QueryResponseResult create(@LoginUser UserVO user, 
    		@RequestBody Saas saasConfig) {
    	
    	String saasId = saasConfigService.create(user, saasConfig);
    	return QueryResponseResult.success(saasId);
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
    public ResponseResult sort(String id,String ope) {
    	saasConfigService.serviceSort(id, ope);
        return QueryResponseResult.success();
    }
    
    @ApiOperation("上/下线")
    @ApiResponses(
            @ApiResponse(code = 200, message = "success", response = String.class)
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="服务id", required = true,  dataType="String"),
            @ApiImplicitParam(name="result", value="操作结果 1:上线,其它:下线", required = true, dataType="String"),
            @ApiImplicitParam(name="remark", value="操作描述", dataType="String"),
    })
    @GetMapping(value = "/publish")
    public ResponseResult publish(@LoginUser UserVO user, String id, Integer result,
                     @RequestParam(required = false) String remark) {
        if (result == null) {
            return QueryResponseResult.fail("请选择操作结果");
        }
        saasConfigService.publish(user, id, result, remark);
        return QueryResponseResult.success();
    }
    
    /**
     * 分页
     * @param status {@link ReviewStatus}
     */
    @ApiOperation("分页查询服务列表")
    @ApiResponses(
            @ApiResponse(code = 200, message = "success", response = Saas.class)
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", dataType="String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", dataType="String"),
            @ApiImplicitParam(name="status", value="状态, 0:审核中 1: 待上线 2: 上线 3:驳回 4:删除", required = true,dataType="String"),
            @ApiImplicitParam(name="name", value="服务名", dataType="String"),
            @ApiImplicitParam(name = "subType",value = "子类", dataType = "String"),
            @ApiImplicitParam(name = "subType",value = "子类", dataType = "String"),
    })
    @GetMapping(value = "/page")
    public QueryResponseResult page(@LoginUser UserVO user, 
    		@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                  @RequestParam(required = false, defaultValue = "20") Integer pageSize, Integer status,
                  @RequestParam(required = false) String name,
                  @RequestParam(required = false) String subType,
                  @RequestParam(required = false,defaultValue = "0") Integer serviceFlag,
                 @RequestParam(required = false) Integer pilotApp) {
        IPage<Saas> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page = saasConfigService.getPage(page, user, status, name, subType,serviceFlag,pilotApp);
        return QueryResponseResult.success(page);
    }
    
    @ApiOperation("删除服务")
    @ApiResponses(
            @ApiResponse(code = 200, message = "success", response = ResponseResult.class)
    )
    @ApiImplicitParam(name="id", value="服务id", required = true, dataType="String")
    @GetMapping(value = "/delete")
    public ResponseResult delete(@LoginUser UserVO user,String id) {
    	saasConfigService.delete(user, id);
    	return QueryResponseResult.success();
    }
    
    @ApiOperation("修改服务信息")
    @ApiResponses(
            @ApiResponse(code = 200, message = "success", response = ResponseResult.class)
    )
    @PutMapping(value = "/edit")
    public ResponseResult edit(@RequestBody Saas saas) {
    	saasConfigService.edit(saas);
    	return QueryResponseResult.success();
    }
    
    @ApiOperation("服务信息详情")
    @ApiResponses(
            @ApiResponse(code = 200, message = "success", response = Saas.class)
    )
    @ApiImplicitParam(name="id", value="服务id", required = true, dataType="String")
    @GetMapping(value = "/detail")
    public ResponseResult detail(@LoginUser UserVO user,String id) {
        Saas saas = saasConfigService.getDetail(user,id);
        return QueryResponseResult.success(saas);
    }
    
    @ApiOperation("流程配置")
    @ApiResponses(
            @ApiResponse(code = 200, message = "success", response = Saas.class)
    )
    @ApiImplicitParams({ 
    			@ApiImplicitParam(name="id", value="服务id", required = true, dataType="String"),
    			@ApiImplicitParam(name="flowId", value="流程id", required = true, dataType="String")
    })
    @PostMapping(value = "/set/workflow")
    public ResponseResult setflow(@LoginUser UserVO user, String id,String flowId) {
    	
    	Saas saasConfig = saasConfigService.setflow(user, id, flowId);
    	return QueryResponseResult.success(saasConfig);
    }
	
    @ApiOperation("通过名字搜索SaaS服务")
    @ApiResponses(
            @ApiResponse(code = 200, message = "success", response = Saas.class)
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name="name", value="服务名",dataType="String")
    })
    @GetMapping(value = "/list")
    public ResponseResult list(String name) {
    	List<Saas> list = saasConfigService.findSaasConfigByName(name);
    	return QueryResponseResult.success(list);
    }
    
    
}
