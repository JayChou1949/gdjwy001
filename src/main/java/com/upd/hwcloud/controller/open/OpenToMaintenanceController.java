package com.upd.hwcloud.controller.open;

import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.service.application.IApplicationInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 对运维平台开发的接口
 * @author yyc
 * @date 2020/6/2
 */
@Api("对运维平台开发的接口")
@RestController
@RequestMapping("/open/maintenance")
public class OpenToMaintenanceController {


    @Autowired
    private IApplicationInfoService applicationInfoService;

    @ApiOperation("运维平台：IPDS+应用待办")
    @RequestMapping(value = "/to-do/{idCard}",method = RequestMethod.GET)
    public R todo(@PathVariable String idCard){
        return R.ok(applicationInfoService.getMaintenanceTodoVo(idCard));
    }

}
