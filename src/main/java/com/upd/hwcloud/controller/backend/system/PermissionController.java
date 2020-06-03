package com.upd.hwcloud.controller.backend.system;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.dto.PermissionTree;
import com.upd.hwcloud.bean.entity.Permission;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.service.IPermissionService;
import com.upd.hwcloud.service.IRolePermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * permission 权限表 前端控制器
 * </p>
 *
 * @author wuc
 * @since 2018-11-19
 */
@Api(description = "权限管理")
@RestController
@RequestMapping("/permission")
public class PermissionController {


    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private IRolePermissionService rolePermissionService;

    @ApiOperation("权限列表")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public R list() {
        return  R.ok(new PermissionTree(permissionService.list(new QueryWrapper().orderBy(true,true,"sort"))).getTree());
    }

    @ApiOperation("角色权限列表")
    @ApiImplicitParam(name="role", value="角色id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/list/{role}", method = {RequestMethod.GET, RequestMethod.POST})
    public R listOfRole(@PathVariable("role")String role) {
        List<Permission> permissions = permissionService.selectRoleMenu(role, null, null);
        return  R.ok(permissions);
    }

    @ApiOperation("修改角色权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name="role", value="角色id", required = true, paramType="path", dataType="String"),
            @ApiImplicitParam(name="permission", value="权限id,多个使用逗号分隔", required = true, paramType="path", dataType="String")
    })
    @RequestMapping(value = "/save/{role}/{permission}", method = {RequestMethod.GET, RequestMethod.POST})
    public R save(@PathVariable("role")String role,@PathVariable("permission")String permission) {
        //保存权限
        rolePermissionService.rolePermission(role, permission);
        
        return R.ok("修改权限");
    }

    @ApiOperation("获取登录用户菜单")
    @RequestMapping(value = "/menu", method = {RequestMethod.GET, RequestMethod.POST})
    public R menu(@LoginUser User user){
        List<Permission> menu=permissionService.selectUserMenu(user.getIdcard(), null,0);
        return  R.ok(new PermissionTree(menu).getTree());
    }

    @ApiOperation("获取登录用户菜单和按钮")
    @RequestMapping(value = "/menuAndButton", method = {RequestMethod.GET, RequestMethod.POST})
    public R menuAndButton(@LoginUser User user){
        List<Permission> menu=permissionService.selectUserMenu(user.getIdcard(), null,null);
        return  R.ok(new PermissionTree(menu).getTree());
    }

    @ApiOperation("获取登录用户菜单对应按钮权限")
    @ApiImplicitParam(name="pid", value="菜单id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/button/{pid}", method = {RequestMethod.GET, RequestMethod.POST})
    public R button(@LoginUser User user,@PathVariable("pid")String pid){
        List<Permission> menu=permissionService.selectUserMenu(user.getIdcard(), pid, 1);
        return  R.ok(menu);
    }

}

