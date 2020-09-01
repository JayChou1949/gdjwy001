package com.hirisun.cloud.api.user;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.hirisun.cloud.model.user.UserVO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;


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
    public UserVO getUserByIdCard(@ApiParam(value = "用户身份证",required = true) @RequestParam String idCard);
    
    /**
     * 根据用户id查询用户
     * @param id
     * @return
     */
    @ApiOperation("根据用户id获取用户")
    @GetMapping("/user/userManage/feign/getUserById")
    public UserVO getUserById(@ApiParam(value = "用户id",required = true) @RequestParam String id);

    /**
     * feign调用，提供查询用户方法，根据id list查询多个用户
     *
     * @param idCardList
     * @return
     */
    @ApiIgnore
    @ApiOperation("根据用户身份证列表获取用户列表")
    @GetMapping("/user/userManage/feign/getUserByIdCardList")
    public List<UserVO> getUserByIdCardList(@ApiParam(value = "用户身份证列表", required = true) @RequestParam List<String> idCardList);

    /**
     * feign调用，提供查询用户方法
     * @param user
     * @return
     */
    @ApiIgnore
    @ApiOperation("根据参数查询用户")
    @PutMapping("/user/userManage/feign/getUserByParams")
    public List<UserVO> getUserByParams(@RequestBody UserVO user);
    
    /**
     * feign调用，根据名称集合查询用户
     * @param user
     * @return
     */
    @ApiIgnore
    @ApiOperation("根据名称集合查询用户")
    @GetMapping("/user/userManage/feign/findUserByUserName")
    public List<UserVO> findUserByUserName(@ApiParam(value = "用户名称集合", required = true) 
    	@RequestParam List<String> nameList);
}
