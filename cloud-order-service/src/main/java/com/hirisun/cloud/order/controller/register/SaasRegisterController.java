package com.hirisun.cloud.order.controller.register;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.constant.BusinessName;
import com.hirisun.cloud.common.contains.ApplyInfoStatus;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.order.bean.register.SaasRegister;
import com.hirisun.cloud.order.handler.CommonHandler;
import com.hirisun.cloud.order.service.register.SaasRegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * SAAS服务注册表 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-02
 */
@Api(tags = "应用注册申请管理")
@Controller
@RequestMapping("/saasRegister")
public class SaasRegisterController extends RegisterController<SaasRegisterService,SaasRegister>{
    String FLOW_CODE = "ZCSAAS";

    @Autowired
    private SaasRegisterService saasRegisterService;

    @ApiOperation("应用信息变更")
    @RequestMapping(value = "/change",method = RequestMethod.POST)
    public QueryResponseResult change(@LoginUser UserVO user, @RequestBody SaasRegister info, String oldName){
        saasRegisterService.change(user,info,oldName);
        return QueryResponseResult.success(null);
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

