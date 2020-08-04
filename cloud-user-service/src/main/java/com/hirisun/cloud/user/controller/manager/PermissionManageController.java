package com.hirisun.cloud.user.controller.manager;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.util.TreeUtils;
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.user.bean.Permission;
import com.hirisun.cloud.user.bean.RolePermission;
import com.hirisun.cloud.user.bean.UserRole;
import com.hirisun.cloud.user.service.PermissionService;
import com.hirisun.cloud.user.service.RolePermissionService;
import com.hirisun.cloud.user.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * permission 权限表 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-23
 */
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/user/permissionManage")
public class PermissionManageController {
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserService userService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @ApiOperation("获取所有菜单列表")
    @GetMapping("/list")
    public QueryResponseResult<Permission> list() {
        List<Permission> list = permissionService.list();
        list=(List<Permission>) TreeUtils.listWithTree(list);
        return QueryResponseResult.success(list);
    }

    @ApiOperation("根据角色id查询角色所拥有的菜单")
    @GetMapping("/getPermissionByRoleId")
    public QueryResponseResult<Permission> listOfRole(@ApiParam(value = "角色id",required = true) @RequestParam String roleId) {
        Map map = new HashMap<>();
        map.put("rid",roleId);
        List<Permission> permissions = permissionService.listRoleMenu(map);
        return  QueryResponseResult.success(permissions);
    }

    @ApiOperation("修改角色的菜单")
    @PostMapping("/editRolePermission")
    public QueryResponseResult edit(@ApiParam(value = "角色id",required = true) @RequestParam String role,
                                    @ApiParam(value = "权限id,多个使用逗号分隔",required = true) @RequestParam String permission) {
        /*
         * 1.移除角色权限
         * 2.批量保存角色权限
         */
        rolePermissionService.remove(new QueryWrapper<RolePermission>().lambda().eq(RolePermission::getRid,role));
        List<RolePermission> rolePermissionList=new ArrayList<>();
        String[] permissions=permission.split(",");
        for(String p:permissions){
            RolePermission rolePermission=new RolePermission();
            rolePermission.setRid(role);
            rolePermission.setPid(p);
            rolePermissionList.add(rolePermission);
        }
        rolePermissionService.saveBatch(rolePermissionList);
        return QueryResponseResult.success("修改权限");
    }

    @ApiOperation("获取当前登录用户菜单")
    @GetMapping("/menu")
    public QueryResponseResult<Permission> menu(@LoginUser UserVO user){
        Map map = new HashMap<>();
        map.put("uid",user.getIdCard());
        map.put("type", Permission.PERMISSION_TYPE_MENU);
        List<Permission> menu=permissionService.listUserMenu(map);
        menu=(List<Permission>) TreeUtils.listWithTree(menu);
        return  QueryResponseResult.success(menu);
    }

    @ApiOperation("新增菜单")
    @PostMapping("/create")
    public QueryResponseResult<Permission> create(
            @ModelAttribute Permission permission){
        permission.setStatus(Permission.PERMISSION_STATUS_ENABLED);
        permission.setType(Permission.PERMISSION_TYPE_MENU);
        permissionService.save(permission);
        return  QueryResponseResult.success(null);
    }
    @ApiOperation("编辑菜单")
    @PostMapping("/editPermission")
    public QueryResponseResult<Permission> edit(
            @ModelAttribute Permission permission){
        permissionService.updateById(permission);
        return  QueryResponseResult.success(null);
    }
    @ApiOperation("删除菜单")
    @PostMapping("/delete")
    public QueryResponseResult<Permission> delete(@ApiParam(value = "菜单id",required = true) @RequestParam String id){
        /**
         * 1.删除菜单
         * 2.删除子节点
         */
        permissionService.removeById(id);
        permissionService.remove(new QueryWrapper<Permission>().lambda().eq(Permission::getPid,id));
        return  QueryResponseResult.success(null);
    }
}

