package com.upd.hwcloud.controller.backend.system;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.Abnormal;
import com.upd.hwcloud.bean.entity.Monitor;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.request.AbnormalPageRequest;
import com.upd.hwcloud.request.MonitorPageRequest;
import com.upd.hwcloud.service.IAbnormalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  异常记录 前端控制器
 * </p>
 *
 * @author huru
 * @since 2019-04-03
 */
@Api(description = "异常记录接口")
@Controller
@RequestMapping("/abnormal")
public class AbnormalController {

    @Autowired
    private IAbnormalService abnormalService;

    /**
     * 异常记录分页
     * @param abnormalPageRequest
     * @return
     */
    @ApiOperation("接口监控分页")
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    @ResponseBody
    public R page(@ApiParam @RequestBody AbnormalPageRequest abnormalPageRequest) {
        Page<Abnormal> page = abnormalService.getPage(abnormalPageRequest);
        return R.ok(page);
    }

    /**
     * 异常记录新增
     * @param abnormal
     * @return
     */
    @ApiOperation("异常记录新增")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public R save(@ApiParam @RequestBody Abnormal abnormal) {
        abnormalService.save(abnormal);
        return R.ok();
    }

    /**
     * 异常记录编辑
     * @param abnormal
     * @return
     */
    @ApiOperation("异常记录编辑")
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public R edit(@ApiParam @RequestBody Abnormal abnormal) {
        abnormalService.updateById(abnormal);
        return R.ok();
    }

    /**
     * 删除异常记录
     * @param id
     * @return
     */
    @ApiOperation("删除异常记录")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public R delete(@PathVariable String id) {
        abnormalService.removeById(id);
        return R.ok();
    }

    /**
     * 获取异常记录详情
     * @param id
     * @return
     */
    @ApiOperation("获取异常记录详情")
    @RequestMapping(value = "/info/{id}",method = RequestMethod.GET)
    @ResponseBody
    public R info(@PathVariable String id) {
        Abnormal abnormal = abnormalService.getById(id);
        return R.ok(abnormal);
    }
}

