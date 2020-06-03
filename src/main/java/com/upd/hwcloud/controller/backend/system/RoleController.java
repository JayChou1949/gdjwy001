package com.upd.hwcloud.controller.backend.system;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.entity.Role;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.log.OperationLog;
import com.upd.hwcloud.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author wuc
 * @since 2018-10-18
 */
@Api(description = "角色管理")
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @ApiOperation("查询角色列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public R list() {
        return R.ok(roleService.list(new QueryWrapper<>()));
    }


    @ApiOperation("新增角色")
    @OperationLog(operation = "新增角色")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public R add(@RequestBody Role role) {
        if (role == null) {
            return R.error();
        }
        role.setId(null);
        roleService.save(role);
        return R.ok();
    }

    @ApiOperation("修改角色")
    @OperationLog(operation = "修改角色")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public R edit(@RequestBody Role role) {
        if (role == null || StringUtils.isEmpty(role.getId())) {
            return R.error();
        }
        roleService.updateById(role);
        return R.ok();
    }

    @ApiOperation("删除角色")
    @ApiImplicitParam(name="roleId", value="角色id", required = true, paramType="path", dataType="String")
    @OperationLog(operation = "删除角色")
    @RequestMapping(value = "/remove/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public R remove(@PathVariable String roleId) {
        roleService.removeById(roleId);
        return R.ok();
    }

    @ApiOperation("角色详情")
    @ApiImplicitParam(name="roleId", value="角色id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public R view(@PathVariable String roleId) {
        Role role = roleService.getById(roleId);
        return R.ok(role);
    }


}

