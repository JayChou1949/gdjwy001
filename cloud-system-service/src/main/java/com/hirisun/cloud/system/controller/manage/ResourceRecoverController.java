package com.hirisun.cloud.system.controller.manage;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.enumer.ResourceRecoverStatus;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.model.system.ResourceRecoverResponseVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.system.bean.ResourceRecover;
import com.hirisun.cloud.system.service.IResourceRecoverService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 资源回收列表 前端控制器
 */
@Api("资源回收数据控制器")
@RestController
@RequestMapping("/resourceRecover")
public class ResourceRecoverController {

    @Autowired
    private IResourceRecoverService resourceRecoverService;

    @ApiOperation("导入回收数据")
    @PostMapping(value = "/import")
    public ResponseResult importData(@LoginUser UserVO user,@RequestParam(value = "file") MultipartFile file){
        resourceRecoverService.importData(file,user.getIdcard());
        return QueryResponseResult.success();
    }

    @ApiOperation("分页")
    @GetMapping(value = "/group/page")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", paramType="query", dataType="String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="query", dataType="String"),
            @ApiImplicitParam(name="applicant", value="申请人", paramType="query", dataType="String"),
            @ApiImplicitParam(name="applicantPhone", value="申请人电话", paramType="query", dataType="String"),
            @ApiImplicitParam(name="appName", value="应用名", paramType="query", dataType="String"),
            @ApiImplicitParam(name="ip", value="ip地址", paramType="query", dataType="String"),
            @ApiImplicitParam(name="startTime", value="导入时间-开始", paramType="query", dataType="String"),
            @ApiImplicitParam(name="endTime", value="导入时间-结束", paramType="query", dataType="String"),
    })
    public ResponseResult page(@LoginUser UserVO user,
    		@RequestParam(defaultValue = "1") Long pageNum,
    		@RequestParam(defaultValue = "10") Long pageSize,
    		@RequestParam(required = false) String applicant,
    		@RequestParam(required = false) String applicantPhone,
    		@RequestParam(required = false) String appName,
    		@RequestParam(required = false) String ip,
    		@RequestParam(required = false) String startTime,
    		@RequestParam(required = false) String endTime){
    	
        IPage<ResourceRecoverResponseVo> page = new Page<>(pageNum,pageSize);
        Map<String,String> params = Maps.newHashMap();
        params.put("applicant",applicant);
        params.put("applicantPhone",applicantPhone);
        params.put("appName",appName);
        params.put("ip",ip);
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        params.put("idCard",user.getIdcard());
        page = resourceRecoverService.getGroupPage(page,params);
        return QueryResponseResult.success(page);
    }

    @ApiOperation("详情分页")
    @GetMapping(value = "/detail/page")
    public ResponseResult detailPage(@LoginUser UserVO user, 
    		@RequestParam(defaultValue = "1") Long pageNum, 
    		@RequestParam(defaultValue = "10") Long pageSize,
    		@RequestParam(required = false) String applicant,
    		@RequestParam(required = false) String phone,
    		@RequestParam(required = false) String importTime){
    	
        IPage<ResourceRecover> page = new Page<>(pageNum,pageSize);
        page = resourceRecoverService.page(page,new QueryWrapper<ResourceRecover>().lambda()
                .eq(ResourceRecover::getCreatorIdCard,user.getIdcard())
                .eq(ResourceRecover::getApplicant,applicant)
                .eq(ResourceRecover::getApplicantPhone,phone)
                .eq(ResourceRecover::getImportTimeStr,importTime)
                .eq(ResourceRecover::getStatus, ResourceRecoverStatus.UN_PROCESSED.getCode())
                .orderByDesc(ResourceRecover::getImportTime));
        return QueryResponseResult.success(page);
    }

    @ApiOperation("删除")
    @GetMapping(value = "/delete")
    public ResponseResult delete(@RequestParam(required = true) String applicant,
    		@RequestParam(required = true) String phone,
    		@RequestParam(required = true) String importTime){
    	
        resourceRecoverService.remove(new QueryWrapper<ResourceRecover>().lambda()
                .eq(ResourceRecover::getApplicant,applicant)
                .eq(ResourceRecover::getApplicantPhone,phone)
                .eq(ResourceRecover::getImportTimeStr,importTime));
        return QueryResponseResult.success();

    }

}

