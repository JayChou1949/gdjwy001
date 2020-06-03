package com.upd.hwcloud.controller.backend.system;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.entity.Dict;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.log.OperationLog;
import com.upd.hwcloud.service.IDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 字典 前端控制器
 * </p>
 *
 * @author wuc
 * @since 2018-10-18
 */
@Api(description = "字典管理")
@Controller
@RequestMapping("/dict")
public class DictController {

    @Autowired
    private IDictService dictService;

    @ApiOperation("添加/修改字典")
    @OperationLog(operation = "添加/修改字典")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public R add(@RequestBody Dict dict) {
        if (dict.getId() == null) {
            dict = dictService.add(dict);
        } else {
            dict = dictService.edit(dict);
        }
        return R.ok(dict);
    }

    @ApiOperation("查询字典树")

    @RequestMapping(value = "/tree", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R getTree() {
        List<Dict> tree = dictService.getTree();
        return R.ok(tree);
    }

    @ApiOperation("查询字典详情")
    @ApiImplicitParam(name="id", value="字典id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/info/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R info(@PathVariable String id) {
        return R.ok(dictService.getById(id));
    }

    @ApiOperation("删除字典")
    @ApiImplicitParam(name="id", value="字典id", required = true, paramType="path", dataType="String")
    @Transactional(rollbackFor = Exception.class)
    @OperationLog(operation = "删除字典")
    @RequestMapping(value = "delete/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R delete(@PathVariable String id) {
        dictService.removeById(id);
        dictService.remove(new QueryWrapper<Dict>().lambda().eq(Dict::getPid, id));
        return R.ok(id);
    }

    @ApiOperation("通过父级id查询子集")
    @ApiImplicitParam(name="pid", value="父级字典id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/getChild/{pid}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R getChild(@PathVariable String pid) {
        List<Dict> list = dictService.list(new QueryWrapper<Dict>()
                .eq("PID", pid).orderByAsc("SORT"));
        return R.ok(list);
    }

    @ApiOperation("通过父级字典值查询子集")
    @ApiImplicitParam(name="value", value="父级字典值", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/getChildByValue/{value}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R getChildByValue(@PathVariable String value) {
        List<Dict> list = dictService.getChildByValue(value);
        return R.ok(list);
    }

    @ApiOperation("通过父级字典名查询子集")
    @ApiImplicitParam(name="name", value="父级字典名", required = true, paramType="query", dataType="String")
    @RequestMapping(value = "/getChildByName", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R getChildByName(String name) {
        List<Dict> list = dictService.getChildByName(name);
        return R.ok(list);
    }

}

