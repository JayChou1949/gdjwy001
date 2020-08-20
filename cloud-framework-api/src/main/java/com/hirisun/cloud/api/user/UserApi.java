package com.hirisun.cloud.api.user;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


/**
 * @author wuxiaoxing
 * @date 2020-08-07
 * @description
 */
@FeignClient("cloud-user-service")
public interface UserApi {

    /**
     * 根据身份证查询用户
     * @param idCard
     * @return
     */
    @ApiOperation("根据用户身份证获取用户角色")
    @GetMapping("/user/userManage/feign/getUserByIdCard")
    public String  getUserByIdCard(@ApiParam(value = "用户身份证",required = true) @RequestParam String idCard);

    /**
     * feign调用，提供查询用户方法，根据id list查询多个用户
     *
     * @param idCardList
     * @return
     */
    @ApiIgnore
    @ApiOperation("根据用户身份证列表获取用户列表")
    @GetMapping("/user/userManage/feign/getUserByIdCardList")
    public String getUserByIdCardList(@ApiParam(value = "用户身份证列表", required = true) @RequestParam List<String> idCardList);
}
