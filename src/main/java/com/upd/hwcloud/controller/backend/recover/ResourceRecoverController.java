package com.upd.hwcloud.controller.backend.recover;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.contains.ResourceRecoverStatus;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.ResourceRecover;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.bean.vo.resourceRecover.ResourceRecoverResponse;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.service.application.IResourceRecoverService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 资源回收列表 前端控制器
 * </p>
 *
 * @author yyc
 * @since 2020-05-14
 */
@Api(description = "资源回收数据控制器")
@RestController
@RequestMapping("/resourceRecover")
public class ResourceRecoverController {

    @Autowired
    private IResourceRecoverService resourceRecoverService;

    @ApiOperation("导入回收数据")
    @RequestMapping(value = "/import",method = RequestMethod.POST)
    public R importData(@LoginUser User user,@RequestParam(value = "file") MultipartFile file){
        resourceRecoverService.importData(file,user.getIdcard());
        return R.ok();
    }

    @ApiOperation("分页")
    @RequestMapping(value = "/group/page",method = {RequestMethod.GET,RequestMethod.POST})
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
    public R page(@LoginUser User user,@RequestParam(defaultValue = "1") Long pageNum,@RequestParam(defaultValue = "10") Long pageSize,
                  String applicant,String applicantPhone,String appName,String ip,String startTime,String endTime){
        IPage<ResourceRecoverResponse> page = new Page<>(pageNum,pageSize);
        Map<String,String> params = Maps.newHashMap();
        params.put("applicant",applicant);
        params.put("applicantPhone",applicantPhone);
        params.put("appName",appName);
        params.put("ip",ip);
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        params.put("idCard",user.getIdcard());
        page = resourceRecoverService.getGroupPage(page,params);
        return R.ok(page);
    }

    @ApiOperation("详情分页")
    @RequestMapping(value = "/detail/{applicant}/{phone}/{importTime}/page",method = {RequestMethod.GET,RequestMethod.POST})
    public R detailPage(@LoginUser User user, @RequestParam(defaultValue = "1") Long pageNum, @RequestParam(defaultValue = "10") Long pageSize,
                        @PathVariable String applicant,@PathVariable String phone,@PathVariable String importTime){
        IPage<ResourceRecover> page = new Page<>(pageNum,pageSize);
        page = resourceRecoverService.page(page,new QueryWrapper<ResourceRecover>().lambda()
                .eq(ResourceRecover::getCreatorIdCard,user.getIdcard())
                .eq(ResourceRecover::getApplicant,applicant)
                .eq(ResourceRecover::getApplicantPhone,phone)
                .eq(ResourceRecover::getImportTimeStr,importTime)
                .eq(ResourceRecover::getStatus, ResourceRecoverStatus.UN_PROCESSED.getCode())
                .orderByDesc(ResourceRecover::getImportTime));
        return R.ok(page);
    }

    @ApiOperation("删除")
    @RequestMapping(value = "/delete/{applicant}/{phone}/{importTime}",method = RequestMethod.GET)
    @Transactional(rollbackFor = Throwable.class)
    public R delete(@PathVariable String applicant,@PathVariable String phone,@PathVariable String importTime){
        resourceRecoverService.remove(new QueryWrapper<ResourceRecover>().lambda()
                .eq(ResourceRecover::getApplicant,applicant)
                .eq(ResourceRecover::getApplicantPhone,phone)
                .eq(ResourceRecover::getImportTimeStr,importTime));
        return R.ok();

    }

}

