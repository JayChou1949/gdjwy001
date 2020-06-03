package com.upd.hwcloud.controller.backend.system;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.contains.SessionName;
import com.upd.hwcloud.bean.contains.UserType;
import com.upd.hwcloud.bean.entity.Role;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.log.OperationLog;
import com.upd.hwcloud.service.IRoleService;
import com.upd.hwcloud.service.IUserRoleService;
import com.upd.hwcloud.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author wuc
 * @since 2018-10-12
 */
@Api(description = "用户管理")
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IRoleService roleService;
    @ApiOperation("分页查询用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", paramType="form", dataType="String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="form", dataType="String"),
            @ApiImplicitParam(name="name", value="关键字", paramType="form", dataType="String"),
            @ApiImplicitParam(name="type", value="用户类型  0：普通用户 1: 服务厂商 20:租户管理员  100：管理用户", paramType="form", dataType="String")
    })

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    public R page(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                  @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                  @RequestParam(required = false) String name,
                  @RequestParam(required = false) Long type) {
        IPage<User> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page = userService.getPage(page, name, type);
        return R.ok(page);
    }

    @ApiOperation("获取用户角色")
    @ApiImplicitParam(name="userId", value="用户身份证", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/roles/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public R editRole(@PathVariable String userId) {
        List<Role> userRoles = userService.getRolesByUserId(userId);
        List<Role> allRoles = roleService.getList();
        Map<String, List<Role>> roleMap = new HashMap<>();
        roleMap.put("userRoles", userRoles);
        roleMap.put("allRoles", allRoles);
        return R.ok(roleMap);
    }

    @ApiOperation("修改用户角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户身份证", required = true, paramType="path", dataType="String"),
            @ApiImplicitParam(name="roleId", value="角色id,多个使用逗号(,)分隔", required = true, paramType="query", dataType="String")
    })
    @OperationLog(operation = "修改用户角色")
    @RequestMapping(value = "/editRole/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public R editRole(@PathVariable String userId, String roleId) {

        userRoleService.userRole(userId,roleId);
        return R.ok();
    }
    @ApiOperation("查询用户")
    @ApiImplicitParams({@ApiImplicitParam(name="userId", value="用户身份证", required = true, paramType="path", dataType="String")
    })
    @RequestMapping(value = "/details/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public R getUser(@PathVariable String userId) {
        return R.ok( userService.findUserByIdcard(userId));
    }
    @ApiOperation("筛选用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name="orgId", value="组织id", paramType="query", dataType="String"),
            @ApiImplicitParam(name="name", value="姓名,至少两个汉字", paramType="query", dataType="String")
    })
    @RequestMapping(value = "/getReviewer", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R getReviewer(@RequestParam(required = false) String orgId,
                         @RequestParam(required = false) String name) {
        if (name == null || name.length() < 2) {
            return R.error("请输入至少两个字进行筛选");
        }
        List<User> list = userService.search(orgId, name);
        return R.ok(list);
    }

    @ApiOperation("修改用户类型(数据权限)")
    @ApiImplicitParams({
            @ApiImplicitParam(name="idcard", value="用户身份证", required = true, paramType="path", dataType="String"),
            @ApiImplicitParam(name="type", value="类型,0：普通用户 1: 服务厂商 20:租户管理员 100：管理用户", required = true, paramType="path", dataType="String"),
            @ApiImplicitParam(name="tenantArea", value="地区", paramType="query", dataType="String"),
            @ApiImplicitParam(name="tenantPoliceCategory", value="警种", paramType="query", dataType="String"),
            @ApiImplicitParam(name="defaultTenant", value="是否第一租户管理员 1-是", paramType="query", dataType="String")
    })
    @OperationLog(operation = "修改用户类型")
    @RequestMapping(value = "/{idcard}/{type}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R changeType(@PathVariable String idcard, @PathVariable Long type,
                        @RequestParam(required = false) String tenantArea,
                        @RequestParam(required = false) String tenantPoliceCategory,
                        @RequestParam(required = false) String nationalProject,
                        @RequestParam(required = false) String defaultTenant) {
        if (StringUtils.isEmpty(idcard) || type == null) {
            return R.error("参数错误!");
        }
        if (UserType.TENANT_MANAGER.getCode().equals(type)
                && StringUtils.isEmpty(tenantArea) && StringUtils.isEmpty(tenantPoliceCategory)&&StringUtils.isEmpty(nationalProject)) {
            return R.error("请选择地区、警种或国家专项!");
        }
        userService.changeType(idcard, type, tenantArea, tenantPoliceCategory,nationalProject,defaultTenant);
        return R.ok();
    }

    @ApiOperation("消息通知设置(自己)")
    @ApiImplicitParam(name="notifyType", value="通知类型  0：短信 1:邮箱 2:微信,多个以逗号分隔", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/notify/{notifyType}", method = RequestMethod.GET)
    @ResponseBody
    public R changeNotifyType(HttpServletRequest request, @LoginUser User user, @PathVariable String notifyType) {
        userService.update(new User(), new UpdateWrapper<User>().lambda()
                .eq(User::getIdcard, user.getIdcard())
                .set(User::getNotifyType, notifyType));

        // 修改成功后修改 session 中的值
        user.setNotifyType(notifyType);
        request.getSession().setAttribute(SessionName.USER, user);
        return R.ok();
    }

}

