package com.upd.hwcloud.controller.backend.application;


import com.upd.hwcloud.bean.contains.BusinessName;
import com.upd.hwcloud.bean.entity.SaasRegister;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.service.ISaasRegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * SAAS服务注册表 前端控制器
 * </p>
 *
 * @author zwb
 * @since 2019-05-27
 */
@Api(description = "saas服务注册")
@RestController
@RequestMapping("/saasRegister")
public class SaasRegisterController extends RegisterController<ISaasRegisterService,SaasRegister> {
    String FLOW_CODE = "ZCSAAS";

    @Autowired
    private ISaasRegisterService saasRegisterService;

    @ApiOperation("应用信息变更")
    @RequestMapping(value = "/change",method = RequestMethod.POST)
    public R change(@LoginUser User user, @RequestBody SaasRegister info,String oldName){
        saasRegisterService.change(user,info,oldName);
        return R.ok();
    }


    @Override
    public String getFlowcode() {
        return FLOW_CODE;
    }

    @Override
    public String getBusinessName() {
        return BusinessName.SAAS_REGIST;
    }
}

