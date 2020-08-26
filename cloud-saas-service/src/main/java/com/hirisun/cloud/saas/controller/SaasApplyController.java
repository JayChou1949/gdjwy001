package com.hirisun.cloud.saas.controller;


import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.saas.bean.SaasApply;
import com.hirisun.cloud.saas.service.SaasApplyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * SaaS资源申请原始信息表 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-24
 */
@RestController
@RequestMapping("/saas/saasApply")
public class SaasApplyController {

    @Autowired
    private SaasApplyService saasApplyService;

    @ApiOperation(value = "新建原始申请单")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public QueryResponseResult create(@LoginUser UserVO user, @RequestBody SaasApply info) {
        saasApplyService.create(user, info);
        return QueryResponseResult.success(null);
    }

}

