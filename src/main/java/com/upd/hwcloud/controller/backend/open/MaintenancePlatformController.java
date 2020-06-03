package com.upd.hwcloud.controller.backend.open;

import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.service.application.IApplicationInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

/**
 * 对运维平台开放的接口
 * @author wuc
 * @date 2020/3/23
 */
@Api("对运维平台开放的接口")
@RestController
@RequestMapping("/open/maintenance")
public class MaintenancePlatformController {

    @Autowired
    private IApplicationInfoService applicationInfoService;

    /**
     * 处领导待办事项
     */
    @RequestMapping(value = "/leader/{idCard}/to-do",method = {RequestMethod.GET,RequestMethod.POST})
    public R todo(@PathVariable String idCard){
        return R.ok(applicationInfoService.getLeaderTodoView(idCard));
    }
}
