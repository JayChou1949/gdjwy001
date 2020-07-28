package com.hirisun.cloud.user.controller;


import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.util.AreaPoliceCategoryUtils;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.user.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-23
 */
@Api(description = "用户信息")
@RestController
@RequestMapping("/user")
public class UserController {

    @ApiOperation("获取登录用户信息")
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public QueryResponseResult<UserVO> getUserInfo(@LoginUser UserVO user) {
        if(user != null){
            String orgName = user.getOrgName();
            if(!orgName.isEmpty()){
                //根据用户单位名称中的信息去匹配所属地区和警种
                String policeCategory = AreaPoliceCategoryUtils.getPoliceCategory(orgName);
                String areaName = AreaPoliceCategoryUtils.getAreaName(orgName);
                user.setBelongArea(areaName);
                user.setBelongPoliceType(policeCategory);
            }
        }
        return QueryResponseResult.success(user);
    }

}

