package com.hirisun.cloud.system.controller.manage;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.system.bean.AbnormalLog;
import com.hirisun.cloud.system.bean.MonitorLog;
import com.hirisun.cloud.system.service.AbnormalLogService;
import com.hirisun.cloud.system.service.MonitorLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 调用三方接口异常记录表 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-04
 */
@Api(tags = "异常日志管理")
@RestController
@RequestMapping("/system/abnormalManage")
public class AbnormalLogManageController {
    @Autowired
    private AbnormalLogService abnormalLogService;

    @ApiOperation("异常日志分页列表")
    @GetMapping("/page")
    public QueryResponseResult<AbnormalLog> page(
            @ApiParam("页码") @RequestParam(required = false,defaultValue = "1") Integer pageNum,
            @ApiParam("每页大小") @RequestParam(required = false,defaultValue = "20") Integer pageSize,
            @ApiParam("接口名称") @RequestParam(required = false) String name){

        LambdaQueryWrapper<AbnormalLog> wrapper=new QueryWrapper<AbnormalLog>().lambda();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like(AbnormalLog::getName,name);
        }
//        if (!StringUtils.isEmpty(startDate)) {
//            wrapper.like(AbnormalLog::getAbnoemalReqTime,startDate);
//        }

        Page<AbnormalLog> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page=abnormalLogService.page(page,wrapper);
        return QueryResponseResult.success(page);
    }

    /**
     * 保存系统日志
     * @param interfaceName 接口名
     * @param url 接口地址
     * @param lastResponse 最后请求说明
     * @param module 所属模块
     */
//    @Override
    @ApiIgnore
    @ApiOperation("保存异常接口日志")
    @GetMapping("/saveLog")
    public void saveLog(@RequestParam("interfaceName") String interfaceName,
                        @RequestParam("url")String url,
                        @RequestParam("lastResponse")String lastResponse,
                        @RequestParam("module")String module) {
        AbnormalLog abnormalLog = new AbnormalLog();
        abnormalLog.setName(interfaceName);
        abnormalLog.setUrl(url);
        abnormalLog.setAbnoemalReqTime(new Date());
        abnormalLog.setResponse(lastResponse);
        abnormalLog.setModule(module);
        abnormalLogService.save(abnormalLog);
        return ;
    }
}

