package com.hirisun.cloud.user.controller.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.annotation.support.LoginUserHandlerMethodArgumentResolver;
import com.hirisun.cloud.common.util.AreaPoliceCategoryUtils;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.user.bean.SysRole;
import com.hirisun.cloud.user.bean.User;
import com.hirisun.cloud.user.bean.UserRole;
import com.hirisun.cloud.user.service.SysRoleService;
import com.hirisun.cloud.user.service.UserRoleService;
import com.hirisun.cloud.user.service.UserService;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author wuxiaoxing
 * @since 2020-07-24
 */
@Api(description = "用户后台管理")
@RestController
@RequestMapping("/user/userManage")
public class UserManageController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 查询列表
     * @return
     * TOTO 优化sql查询
     */
    @ApiOperation("分页查询")
    @GetMapping("/page")
    public QueryResponseResult<User> list(
            @ApiParam("页码") @RequestParam(required = false,defaultValue = "1") Integer pageNum,
            @ApiParam("每页大小") @RequestParam(required = false,defaultValue = "20") Integer pageSize,
            @ApiParam("关键字") @RequestParam(required = false) String name,
            @ApiParam("用户类型  0：普通用户 1: 服务厂商 20:租户管理员  100：管理用户") @RequestParam(required = false) String type){
        IPage<User> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        Map map = new HashMap<>();
        map.put("name",name);
        map.put("type",type);
        page = userService.getPage(page,map);
        return QueryResponseResult.success(page);
    }

    /**
     * 获取用户拥有的角色、系统所有角色
     * @param userId
     * @return
     */
    @ApiOperation("获取用户角色")
    @GetMapping("/roles/{userId}")
    public QueryResponseResult<SysRole> editRole(@ApiParam("用户身份证") @PathVariable String userId) {
        List<SysRole> sysRoleList = sysRoleService.list();
        List<SysRole> roleList = new ArrayList<>();
        List<UserRole> userRoleList = userRoleService.list(new QueryWrapper<UserRole>().lambda().select(UserRole::getRoleId).eq(UserRole::getUserId, userId));
        userRoleList.forEach(userRole->{
            sysRoleList.forEach(sysRole->{
                if (userRole.getRoleId() != null && userRole.getRoleId().equals(sysRole.getId())) {
                    roleList.add(sysRole);
                    return;
                }
            });
        });
        Map<String, List<SysRole>> roleMap = new HashMap<>();
        roleMap.put("userRoles", roleList);
        roleMap.put("allRoles", sysRoleList);
        return QueryResponseResult.success(roleMap);
    }

    @ApiOperation("修改用户角色")
    @PostMapping("/editRole/{userId}")
    public QueryResponseResult<SysRole> editRole(
            @ApiParam("用户身份证") @PathVariable String userId,
            @ApiParam("角色id,多个使用逗号(,)分隔") @RequestParam(required = true) String roleId) {
        /*
         * 1.移除用户角色
         * 2.批量保存用户角色
         */
        userRoleService.remove(new QueryWrapper<UserRole>().lambda().eq(UserRole::getUserId,userId));
        List<UserRole> userRoleList=new ArrayList<>();
        String[] userRoles=roleId.split(",");
        for(String role:userRoles){
            UserRole userRole=new UserRole();
            userRole.setRoleId(role);
            userRole.setUserId(userId);
            userRoleList.add(userRole);
        }
        userRoleService.saveBatch(userRoleList);
        return QueryResponseResult.success("成功");
    }

    /**
     * 修改用户类型
     */
    @ApiOperation("修改用户类型")
    @PostMapping("/{userId}/{type}")
    public QueryResponseResult editUserType(
            @ApiParam("用户身份证") @PathVariable(required = true) String userId,
            @ApiParam("类型,0：普通用户 1: 服务厂商 20:租户管理员 100：管理用户") @PathVariable(required = true) Long type,
            @ApiParam("地区") @RequestParam(required = false) String tenantArea,
            @ApiParam("警种") @RequestParam(required = false) String tenantPoliceCategory,
            @ApiParam("国家专项") @RequestParam(required = false) String nationalProject,
            @ApiParam("是否第一租户管理员 1-是") @RequestParam(required = false) String defaultTenant) {
        if (UserVO.USER_TYPE_TENANT_MANAGER.equals(type)
                && org.apache.commons.lang.StringUtils.isEmpty(tenantArea)
                && org.apache.commons.lang.StringUtils.isEmpty(tenantPoliceCategory)
                && StringUtils.isEmpty(nationalProject)) {
            return QueryResponseResult.fail("请选择地区、警种或国家专项!");
        }
        userService.editUserType(userId, type, tenantArea, tenantPoliceCategory,nationalProject,defaultTenant);
        return QueryResponseResult.success("成功");
    }

    @ApiOperation("消息通知设置")
    @GetMapping("/notify/{notifyType}")
    public QueryResponseResult changeNotifyType(HttpServletRequest request,
                                                @LoginUser UserVO user,
                                                @ApiParam("通知类型  0：短信 1:邮箱 2:微信,多个以逗号分隔") @PathVariable String notifyType) {
        userService.update(new User(), new UpdateWrapper<User>().lambda()
                .eq(User::getIdCard, user.getIdCard())
                .set(User::getNotifyType, notifyType));

        // 修改成功后修改 session 中的值
        user.setNotifyType(notifyType);
        request.getSession().setAttribute(LoginUserHandlerMethodArgumentResolver.USER, user);
        return QueryResponseResult.success("成功");
    }

}
