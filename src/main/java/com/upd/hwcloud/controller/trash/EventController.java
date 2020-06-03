package com.upd.hwcloud.controller.trash;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.Event;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.service.IEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 大事记 前端控制器
 * </p>
 *
 * @author huru
 * @since 2018-12-14
 */
@Api(description = "大事记管理接口")
@Controller
@RequestMapping("/event")
public class EventController {

    @Autowired
    private IEventService iEventService;

    @ApiOperation("新建大事记")
    @RequestMapping("/create")
    @ResponseBody
    public R create(@LoginUser User user, @RequestBody Event event) {
        event.setCreator(user.getIdcard());
        event.insert();
        return R.ok();
    }

    @ApiOperation("分页查询大事记")
    @RequestMapping("/page")
    @ResponseBody
    public R page(Integer pageNum,Integer pageSize,String name) {
        if(pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        if(pageSize == null || pageNum <=0) {
            pageSize = 10;
        }
        Page<Event> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page = iEventService.getPage(page,name);
        return R.ok(page);
    }

    @ApiOperation("编辑大事记")
    @RequestMapping("/edit")
    @ResponseBody
    public R edit(@RequestBody Event event) {
        event.updateById();
        return R.ok();
    }

    @ApiOperation("删除大事记")
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public R del(@PathVariable String id) {
        iEventService.removeById(id);
        return R.ok();
    }

    @ApiOperation("大事记详情")
    @RequestMapping("/{id}")
    @ResponseBody
    public R info(@PathVariable String id) {
        Event event = iEventService.getById(id);
        return R.ok(event);
    }
}

