package com.upd.hwcloud.controller.backend.system;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.Monitor;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.request.MonitorPageRequest;
import com.upd.hwcloud.service.IMonitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 接口监控 前端控制器
 * </p>
 *
 * @author huru
 * @since 2019-04-03
 */
@Api(description = "接口监控接口")
@Controller
@RequestMapping("/monitor")
public class MonitorController {

    @Autowired
    private IMonitorService monitorService;

    /**
     * 接口监控分页
     * @param monitorPageRequest
     * @return
     */
    @ApiOperation("接口监控分页")
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    @ResponseBody
    public R page(@ApiParam @RequestBody MonitorPageRequest monitorPageRequest) {
        Page<Monitor> page = monitorService.getPage(monitorPageRequest);
        return R.ok(page);
    }

    /**
     * 接口监控新增
     * @param monitor
     * @return
     */
    @ApiOperation("接口监控新增")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public R save(@ApiParam @RequestBody Monitor monitor) {
        monitorService.save(monitor);
        return R.ok();
    }

    /**
     * 接口监控编辑
     * @param monitor
     * @return
     */
    @ApiOperation("接口监控编辑")
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public R edit(@ApiParam @RequestBody Monitor monitor) {
        monitorService.updateById(monitor);
        return R.ok();
    }

    /**
     * 删除接口监控
     * @param id
     * @return
     */
    @ApiOperation("删除接口监控")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public R delete(@PathVariable String id) {
        monitorService.removeById(id);
        return R.ok();
    }

    /**
     * 获取接口监控详情
     * @param id
     * @return
     */
    @ApiOperation("获取接口监控详情")
    @RequestMapping(value = "/info/{id}",method = RequestMethod.GET)
    @ResponseBody
    public R info(@PathVariable String id) {
        Monitor monitor = monitorService.getById(id);
        return R.ok(monitor);
    }

}

