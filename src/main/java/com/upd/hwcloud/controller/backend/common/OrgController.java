package com.upd.hwcloud.controller.backend.common;


import com.upd.hwcloud.bean.dto.ZTreeNode;
import com.upd.hwcloud.bean.entity.Org;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.service.IOrgService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 机构表 前端控制器
 * </p>
 *
 * @author wuc
 * @since 2018-10-12
 */
@Api(description = "组织机构管理")
@RestController
@RequestMapping("/org")
public class OrgController {

    @Autowired
    private IOrgService orgService;

    @ApiOperation("获取组织人员树形菜单")
    @ApiImplicitParams({
        @ApiImplicitParam(name="orgOnly", value="只包含组织,没有人员", defaultValue = "false", paramType="form", dataType="Boolean"),
        @ApiImplicitParam(name="unitOnly", value="只包含单位,没有服务商", defaultValue = "false", paramType="form", dataType="Boolean"),
    })
    @RequestMapping(value = "/tree", method = {RequestMethod.POST,RequestMethod.GET})
    public R tree(@LoginUser User user,
                  @RequestParam(required = false, defaultValue = "false") Boolean orgOnly,
                  @RequestParam(required = false, defaultValue = "false") Boolean unitOnly) {
        if (orgOnly == null) {
            orgOnly = false;
        }
        List<ZTreeNode> zTreeNodeList = orgService.tree(user, orgOnly, unitOnly);
        return R.ok(zTreeNodeList);
    }

    @ApiOperation("获取某个组织节点的菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="组织id", required = true, paramType="form", dataType="String"),
            @ApiImplicitParam(name="orgOnly", value="只包含组织,没有人员", defaultValue = "false", paramType="form", dataType="Boolean"),
            @ApiImplicitParam(name="unitOnly", value="只包含单位,没有服务商", defaultValue = "false", paramType="form", dataType="Boolean")
    })
    @RequestMapping(value = "/detail", method = {RequestMethod.POST,RequestMethod.GET})
    public R getNodeDetail(@RequestParam(required = true) String id,
                           @RequestParam(required = false, defaultValue = "false") Boolean orgOnly,
                           @RequestParam(required = false, defaultValue = "false") Boolean unitOnly) {
        if (orgOnly == null) {
            orgOnly = false;
        }
        List<ZTreeNode> zTreeNodeList = orgService.getNodeDetail(id, orgOnly, unitOnly);
        return R.ok(zTreeNodeList);
    }

    @ApiOperation("搜索单位")
    @RequestMapping(value = "/getOrg",method = RequestMethod.GET)
    public R getOrg(@RequestParam String name) {
        if(name == null || "".equals(name)) {
            return R.error("单位名称不能未空");
        }
        List<Org> list = orgService.search(name);
        return R.ok(list);
    }

}

