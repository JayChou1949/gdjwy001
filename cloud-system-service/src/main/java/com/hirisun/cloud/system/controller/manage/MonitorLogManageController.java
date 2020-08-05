package com.hirisun.cloud.system.controller.manage;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.system.bean.MonitorLog;
import com.hirisun.cloud.system.bean.SysLog;
import com.hirisun.cloud.system.service.MonitorLogService;
import com.hirisun.cloud.system.service.SysLogService;
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
 * 第三方接口调用监控表 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-04
 */
@Api(tags = "接口监控日志管理")
@RestController
@RequestMapping("/system/monitorManage")
public class MonitorLogManageController {
    @Autowired
    private MonitorLogService monitorLogService;

    @ApiOperation("监控日志分页列表")
    @GetMapping("/page")
    public QueryResponseResult<MonitorLog> page(
            @ApiParam("页码") @RequestParam(required = false,defaultValue = "1") Integer pageNum,
            @ApiParam("每页大小") @RequestParam(required = false,defaultValue = "20") Integer pageSize,
            @ApiParam("接口名称") @RequestParam(required = false) String name,
            @ApiParam("状态，由数据字典定义") @RequestParam(required = false) String status){

        LambdaQueryWrapper<MonitorLog> wrapper=new QueryWrapper<MonitorLog>().lambda();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like(MonitorLog::getName,name);
        }
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq(MonitorLog::getStatus,status);
        }
//        if (!StringUtils.isEmpty(startDate)) {
//            wrapper.like(MonitorLog::getLastReqTime,startDate);
//        }

        Page<MonitorLog> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page=monitorLogService.page(page,wrapper);
        return QueryResponseResult.success(page);
    }

    /**
     * 保存接口监控日志
     * @param interfaceName 接口名
     * @param url 接口地址
     * @param status 数据字典定义的状态
     * @param producer 接口厂商
     * @param lastResponse 最后请求说明
     * @param module 所属模块
     */
//    @Override
    @ApiIgnore
    @ApiOperation("保存接口监控日志")
    @GetMapping("/saveLog")
    public void saveLog(@RequestParam("interfaceName") String interfaceName,
                        @RequestParam("url")String url,
                        @RequestParam("status")String status,
                        @RequestParam("producer")String producer,
                        @RequestParam("lastResponse")String lastResponse,
                        @RequestParam("module")String module) {
        MonitorLog monitorLog = new MonitorLog();
        monitorLog.setLastReqTime(new Date());
        monitorLog.setName(interfaceName);
        monitorLog.setUrl(url);
        monitorLog.setStatus(status);
        monitorLog.setProducer(producer);
        monitorLog.setModule(module);
        monitorLog.setLastResponse(lastResponse);
        monitorLogService.save(monitorLog);
        return ;
    }
}

