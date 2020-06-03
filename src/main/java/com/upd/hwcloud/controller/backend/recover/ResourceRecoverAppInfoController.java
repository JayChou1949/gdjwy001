package com.upd.hwcloud.controller.backend.recover;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.upd.hwcloud.bean.dto.ImplRequest;
import com.upd.hwcloud.bean.entity.SaasRecoverInfo;
import com.upd.hwcloud.bean.entity.ServicePublish;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.AppReviewInfo;
import com.upd.hwcloud.bean.entity.application.ResourceRecover;
import com.upd.hwcloud.bean.entity.application.ResourceRecoverAppInfo;
import com.upd.hwcloud.bean.entity.application.ResourceRecoverImpl;
import com.upd.hwcloud.bean.entity.wfm.Activity;
import com.upd.hwcloud.bean.entity.wfm.FallBackVO;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.bean.vo.resourceRecover.ResourceRecoverImplExport;
import com.upd.hwcloud.bean.vo.resourceRecover.ResourceRecoverSubmit;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.common.utils.ExcelUtil;
import com.upd.hwcloud.common.utils.UUIDUtil;
import com.upd.hwcloud.service.application.IResourceRecoverAppInfoService;
import com.upd.hwcloud.service.application.IResourceRecoverImplService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 资源回收 前端控制器
 * </p>
 *
 * @author yyc
 * @since 2020-05-14
 */
@Api(description = "资源回收申请控制器")
@RestController
@RequestMapping("/resourceRecoverAppInfo")
public class ResourceRecoverAppInfoController {


    @Autowired
    private IResourceRecoverAppInfoService appInfoService;

    @Autowired
    private IResourceRecoverImplService  resourceRecoverImplService;



    @ApiOperation("资源回收申请提交")
    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    public R submit(@LoginUser User user, @RequestBody ResourceRecoverSubmit submit){

        return appInfoService.submit(user,submit);

    }

    @ApiOperation("资源回收申请详情")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public R detail(@LoginUser User user,@PathVariable String id){
        return R.ok(appInfoService.detail(id,user));
    }

    @ApiOperation("审批")
    @RequestMapping(value = "/approve",method = RequestMethod.POST)
    public R approve(@LoginUser User user, @RequestBody FallBackVO vo){
        return appInfoService.approve(user,vo);
    }

    @ApiOperation(value = "业务办理")
    @ApiImplicitParams({
            @ApiImplicitParam(name="activityId", value="当前环节id", required = true, paramType="path", dataType="String"),
    })
    @RequestMapping(value = "/impl/{id}/{activityId}/{modelId}", method = RequestMethod.POST)
    public R impl(@LoginUser User user,
                  @PathVariable(value = "id") String id, @PathVariable(value = "modelId") String modelId,
                  @PathVariable(value = "activityId") String activityId, @RequestBody ImplRequest implRequest) throws Exception {

        return appInfoService.impl(user,id,modelId,activityId,implRequest);
    }

    @ApiOperation("分页")
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    public R page(@LoginUser User user, String name,@RequestParam(defaultValue = "1") Long pageNum, @RequestParam(defaultValue = "10") Long pageSize, String orderNum, String status){
        IPage<ResourceRecoverAppInfo> page = new Page<>(pageNum,pageSize);
        page =  appInfoService.getPage(page,user,name,orderNum,status);
        return R.ok(page);
    }

    @ApiOperation("删除")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public R delete(@PathVariable String id){
        return appInfoService.delete(id);
    }

    @ApiOperation("业务办理导出")
    @RequestMapping(value = "/impl-export/{id}",method = RequestMethod.GET)
    public void implExport (@PathVariable String id, HttpServletRequest request, HttpServletResponse response){
        List<ResourceRecoverImpl> implList = resourceRecoverImplService.list(new QueryWrapper<ResourceRecoverImpl>().lambda().eq(ResourceRecoverImpl::getAppInfoId,id));

        List<ResourceRecoverImplExport> exportList = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(implList)){
            for(ResourceRecoverImpl impl:implList){
                ResourceRecoverImplExport export = new ResourceRecoverImplExport();
                BeanUtils.copyProperties(impl,export);
                exportList.add(export);
            }
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"资源回收实施表"), ResourceRecoverImplExport.class, exportList);
        ExcelUtil.export(request,response,"资源回收实施表",workbook);
    }








    @ApiOperation("流程初始化")
    @RequestMapping(value = "/work-flow/init",method = RequestMethod.GET)
    public R workFlowInit(){
        return appInfoService.initWorkFlow();
    }


}

