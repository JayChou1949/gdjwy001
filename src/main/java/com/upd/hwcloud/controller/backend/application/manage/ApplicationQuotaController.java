package com.upd.hwcloud.controller.backend.application.manage;


import com.upd.hwcloud.bean.entity.Files;
import com.upd.hwcloud.bean.entity.application.manage.ApplicationQuota;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.service.application.manage.IApplicationQuotaService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;



/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lqm
 * @since 2020-06-29
 */
@Controller
@RequestMapping("/applicationQuota")
public class ApplicationQuotaController {

    @Autowired
    private IApplicationQuotaService applicationQuotaService;

    @ApiOperation(value = "查看单一一个配额信息")
    @RequestMapping(value = "/v1/updateQuota",method = RequestMethod.GET)
    public R getApplicationQuotaById(@RequestParam(value = "id") String id){
        ApplicationQuota applicationQuota = applicationQuotaService.getApplicationQuotaById(id);
        return R.ok(applicationQuota);
    }

    @ApiOperation(value = "新增配额设置")
    @RequestMapping(value = "/v1/addApplicationQuota",method = RequestMethod.POST)
    public R addApplicationQuota(@RequestBody ApplicationQuota applicationQuota, @RequestBody Files files){
        applicationQuotaService.addApplicationQuota(files,applicationQuota);
        return R.ok();
    }



}

