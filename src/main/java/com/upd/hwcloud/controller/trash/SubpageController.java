package com.upd.hwcloud.controller.trash;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.entity.Subpage;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.service.ISubpageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 二级页面内容 前端控制器
 * </p>
 *
 * @author huru
 * @since 2018-12-18
 */
@Api(description = "二级页面内容管理接口")
@Controller
@RequestMapping("/subpage")
public class SubpageController {

    @Autowired
    private ISubpageService subpageService;

    @ApiOperation("新建或更新二级页面内容")
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public R create(@RequestBody Subpage subpage) {
        if(subpage.getId() != null && !"".equals(subpage.getId())) {
            subpage.updateById();
        } else {
            if(noService(subpage.getServiceId())) {
                subpage.insert();
            } else {
                return R.error("该服务已有二级页面内容");
            }
        }
        return R.ok();
    }

    @ApiOperation("内容详情")
    @RequestMapping("/info")
    @ResponseBody
    public R info(String serviceId) {
        QueryWrapper<Subpage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SERVICE_ID",serviceId);
        Subpage subpage = subpageService.getOne(queryWrapper);
        return R.ok(subpage);
    }

    @ApiOperation("删除内容")
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public R del(@PathVariable String id) {
        subpageService.removeById(id);
        return R.ok();
    }

    private boolean noService(String serviceId) {
        Subpage subpage = subpageService.getOne(
                new QueryWrapper<Subpage>().eq("SERVICE_ID",serviceId));
        if(subpage != null) {
            return false;
        }
        return true;
    }
}

