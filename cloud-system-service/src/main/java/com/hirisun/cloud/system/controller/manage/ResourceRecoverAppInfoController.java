package com.hirisun.cloud.system.controller.manage;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.util.ExcelUtil;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.model.impl.vo.ImplRequestVo;
import com.hirisun.cloud.model.system.ResourceRecoverImplExportVo;
import com.hirisun.cloud.model.system.ResourceRecoverSubmitVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workflow.FallBackVO;
import com.hirisun.cloud.system.bean.ResourceRecoverAppInfo;
import com.hirisun.cloud.system.bean.ResourceRecoverImpl;
import com.hirisun.cloud.system.service.IResourceRecoverAppInfoService;
import com.hirisun.cloud.system.service.IResourceRecoverImplService;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 资源回收申请 前端控制器
 */
@Api("资源回收申请控制器")
@RestController
@RequestMapping("/system/resource/recover/appinfo")
public class ResourceRecoverAppInfoController {

    @Autowired
    private IResourceRecoverAppInfoService appInfoService;

    @Autowired
    private IResourceRecoverImplService  resourceRecoverImplService;

    @ApiOperation("资源回收申请提交")
    @PostMapping(value = "/submit")
    public ResponseResult submit(@LoginUser UserVO user, @RequestBody ResourceRecoverSubmitVo submit){
        return appInfoService.submit(user,submit);
    }

    @ApiOperation("资源回收申请详情")
    @GetMapping(value = "/get")
    public ResponseResult detail(@LoginUser UserVO user,@RequestParam(value = "id") String id){
        return QueryResponseResult.success(appInfoService.detail(id,user));
    }

    @ApiOperation("审批")
    @PostMapping(value = "/approve")
    public ResponseResult approve(@LoginUser UserVO user, @RequestBody FallBackVO vo){
        return appInfoService.approve(user,vo);
    }

    @ApiOperation(value = "业务办理")
    @ApiImplicitParams({
            @ApiImplicitParam(name="activityId", value="当前环节id", required = true, paramType="path", dataType="String"),
    })
    @PostMapping(value = "/impl")
    public ResponseResult impl(@LoginUser UserVO user,
    		@RequestParam(value = "id") String id, 
    		@RequestParam(value = "modelId") String modelId,
    		@RequestParam(value = "activityId") String activityId, 
            @RequestBody ImplRequestVo implRequest) throws Exception {

        return appInfoService.impl(user,id,modelId,activityId,implRequest);
    }

    @ApiOperation("分页")
    @GetMapping(value = "/page")
    public ResponseResult page(@LoginUser UserVO user, 
    		String name,
    		@RequestParam(defaultValue = "1") Long pageNum, 
    		@RequestParam(defaultValue = "10") Long pageSize, 
    		String orderNum, 
    		String status){
        IPage<ResourceRecoverAppInfo> page = new Page<>(pageNum,pageSize);
        page =  appInfoService.getPage(page,user,name,orderNum,status);
        return QueryResponseResult.success(page);
    }

    @ApiOperation("删除")
    @GetMapping(value = "/delete")
    public ResponseResult delete(@RequestParam(value = "id") String id){
        return appInfoService.delete(id);
    }

    @ApiOperation("业务办理导出")
    @GetMapping(value = "/impl/export")
    public void implExport (@RequestParam(value = "id") String id, HttpServletRequest request, HttpServletResponse response){
        List<ResourceRecoverImpl> implList = resourceRecoverImplService.list(new QueryWrapper<ResourceRecoverImpl>().lambda().eq(ResourceRecoverImpl::getAppInfoId,id));

        List<ResourceRecoverImplExportVo> exportList = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(implList)){
            for(ResourceRecoverImpl impl:implList){
                ResourceRecoverImplExportVo export = new ResourceRecoverImplExportVo();
                BeanUtils.copyProperties(impl,export);
                exportList.add(export);
            }
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"资源回收实施表"), ResourceRecoverImplExportVo.class, exportList);
        ExcelUtil.export(request,response,"资源回收实施表",workbook);
    }

    @ApiOperation("流程初始化")
    @GetMapping(value = "/workflow/init")
    public ResponseResult workFlowInit(){
        return appInfoService.initWorkFlow();
    }


}

