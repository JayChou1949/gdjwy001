package com.upd.hwcloud.controller.backend.application.form.iaas;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.IaasYzmyzyUserImpl;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.service.application.IIaasYzmyzyUserImplService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * IaaS 云桌面云资源申请信息 前端控制器
 * </p>
 *
 * @author zwb
 * @since 2019-05-09
 */
@Api(description = "云桌面云资源申请用户信息")
@RestController
@RequestMapping("/iaasYzmyzy")
public class IaasYzmyzyController {

    @Autowired
    IIaasYzmyzyUserImplService iaasYzmyzyUserImplService;
    @ApiOperation(value = "用户信息")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public R list(@LoginUser User user,String appName) throws IOException {
        List<IaasYzmyzyUserImpl> data = iaasYzmyzyUserImplService.list(new QueryWrapper<IaasYzmyzyUserImpl>().lambda().eq(IaasYzmyzyUserImpl::getProjectName, appName));
        return R.ok(data);
    }

    /**
     * 检查添加的用户是否已经申请了桌面云
     * @param idCard
     * @return
     */
    @ApiOperation("用户申请检测")
    @RequestMapping(value = "/check/{idCard}")
    public R check(@PathVariable String idCard){
        int count = iaasYzmyzyUserImplService.count(new QueryWrapper<IaasYzmyzyUserImpl>().lambda().eq(IaasYzmyzyUserImpl::getIdcard,idCard));
        if(count>0){
            return R.ok(true);
        }
        return R.ok(false);
    }
}

