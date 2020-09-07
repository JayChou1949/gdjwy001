package com.hirisun.cloud.order.controller.recover;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.util.ExcelUtil;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.model.apply.FallBackVO;
import com.hirisun.cloud.model.impl.vo.ImplRequestVo;
import com.hirisun.cloud.model.system.ResourceRecoverImplExportVo;
import com.hirisun.cloud.model.system.ResourceRecoverSubmitVo;
import com.hirisun.cloud.model.user.UserVO;

import com.hirisun.cloud.order.bean.recover.ResourceRecoverAppInfo;
import com.hirisun.cloud.order.bean.recover.ResourceRecoverImpl;
import com.hirisun.cloud.order.service.apply.ApplyService;
import com.hirisun.cloud.order.service.recover.IResourceRecoverAppInfoService;
import com.hirisun.cloud.order.service.recover.IResourceRecoverImplService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 资源回收申请 前端控制器
 */
@Api("资源回收申请控制器")
@RestController
@RequestMapping("/order/resourceRecoverApplyInfo")
public class ResourceRecoverAppInfoController {

    @Autowired
    private IResourceRecoverAppInfoService appInfoService;

    @Autowired
    private IResourceRecoverImplService resourceRecoverImplService;

    @Autowired
    private ApplyService<ResourceRecoverAppInfo> applyService;

    @ApiOperation("资源回收申请提交")
    @PostMapping(value = "/submit")
    public ResponseResult submit(@LoginUser UserVO user, @RequestBody ResourceRecoverSubmitVo submit){
        return appInfoService.submit(user,submit);
    }

    @ApiOperation("资源回收申请详情")
    @GetMapping(value = "/get")
    public ResponseResult detail(@LoginUser UserVO user,@RequestParam(value = "id") String id){
        ResourceRecoverAppInfo info = appInfoService.getById(id);
        return applyService.detail(info);
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
    public ResponseResult delete(@LoginUser UserVO user, @RequestParam(value = "id") String id){
        ResourceRecoverAppInfo info = appInfoService.getById(id);
        return applyService.deleteById(info, user);
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

}

