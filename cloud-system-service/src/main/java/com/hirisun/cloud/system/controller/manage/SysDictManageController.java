package com.hirisun.cloud.system.controller.manage;


import com.alibaba.fastjson.JSON;
import com.hirisun.cloud.common.util.TreeUtils;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.system.bean.SysDict;
import com.hirisun.cloud.system.service.SysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-29
 */
@Api(tags = "数据字典管理")
@RestController
@RequestMapping("/system/dictManage")
public class SysDictManageController {

    @Autowired
    private SysDictService sysDictService;

    @ApiOperation("数据字典列表")
    @GetMapping("/list")
    public QueryResponseResult<SysDict> list() {
        List<SysDict> dictList = sysDictService.getSysDictList();
        dictList=(List<SysDict>)TreeUtils.listWithTree(dictList);
        return QueryResponseResult.success(dictList);
    }

    @ApiOperation("添加/修改字典")
    @PostMapping("/saveOrUpdate")
    public QueryResponseResult<SysDict> saveOrUpdate(@ModelAttribute SysDict sysDict) {
        sysDictService.saveOrUpdateDict(sysDict);
        return QueryResponseResult.success(sysDict);
    }
    @ApiOperation("删除系统字典")
    @PostMapping("/delete")
    public QueryResponseResult<SysDict> delete(@ApiParam(value = "系统字典id",required = true) @RequestParam String id) {
        sysDictService.removeDict(id);
        return QueryResponseResult.success(null);
    }
    @ApiOperation("通过父级字典值查询子集")
    @GetMapping("/getChildByValue")
    public QueryResponseResult<SysDict> getChildByValue(@ApiParam(value = "父级字典值",required = true) @RequestParam String value) {
        /**
         * 查询相关的数据
         */
        List<SysDict> sysDictList = sysDictService.getSysDictList();
        sysDictList=(List<SysDict>)TreeUtils.listWithTree(sysDictList);
        sysDictList=sysDictList.stream().filter(item->item.getValue()!=null&&item.getValue().equals(value)).collect(Collectors.toList());
        return QueryResponseResult.success(sysDictList);
    }

    @ApiIgnore
    @ApiOperation(value = "feign调用获取数据字典列表")
    @GetMapping("/feign/list")
//    @Override
    public String feignList() {
        List<SysDict> dictList = sysDictService.getSysDictList();
        dictList=(List<SysDict>)TreeUtils.listWithTree(dictList);
        return JSON.toJSONString(dictList);
    }


    @ApiIgnore
    @ApiOperation(value = "feign调用根据字典值获取数据字典列表")
    @GetMapping("/feign/getByValue")
//    @Override
    public String feignGetByValue(@RequestParam("value") String value) {
        List<SysDict> sysDictList = sysDictService.getSysDictList();
        sysDictList=(List<SysDict>)TreeUtils.listWithTree(sysDictList);
        sysDictList=sysDictList.stream().filter(item->item.getValue()!=null&&item.getValue().equals(value)).collect(Collectors.toList());
        return JSON.toJSONString(sysDictList);
    }
}

