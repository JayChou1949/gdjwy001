package com.hirisun.cloud.user.controller.manager;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.user.bean.SysRole;
import com.hirisun.cloud.user.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-23
 */
@Api(description = "系统角色管理")
@RestController
@RequestMapping("/user/sysRoleManage")
public class SysRoleManageController {

    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation("查询系统角色列表")
    @GetMapping("/list")
    public QueryResponseResult<SysRole> list() {
        return QueryResponseResult.success(sysRoleService.list());
    }

    @ApiOperation("新增角色")
    @PostMapping("/create")
    public QueryResponseResult create(@RequestBody SysRole role) {
        if (role == null) {
            return QueryResponseResult.success("缺少参数");
        }
        role.setId(null);
        sysRoleService.save(role);
        return QueryResponseResult.success(null);
    }

    @ApiOperation("修改角色")
    @PostMapping("/edit")
    public QueryResponseResult<SysRole> edit(@RequestBody SysRole role) {
        if (role == null || StringUtils.isEmpty(role.getId())) {
            return QueryResponseResult.success("缺少参数");
        }
        sysRoleService.updateById(role);
        return QueryResponseResult.success(role);
    }
    @ApiOperation("删除角色")
    @PostMapping("/delete/{roleId}")
    public QueryResponseResult delete(@ApiParam("角色id") @PathVariable String roleId) {
        sysRoleService.removeById(roleId);
        return QueryResponseResult.success(null);
    }
}

