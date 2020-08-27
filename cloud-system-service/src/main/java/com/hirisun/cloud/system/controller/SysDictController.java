package com.hirisun.cloud.system.controller;


import com.alibaba.fastjson.JSON;
import com.hirisun.cloud.common.util.TreeUtils;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.system.bean.SysDict;
import com.hirisun.cloud.system.service.SysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-25
 */
@Api(tags = "门户网站-数据字典")
@RestController
@RequestMapping("/api/dict")
public class SysDictController {

    @Autowired
    private SysDictService sysDictService;

    @ApiOperation("通过父级字典值查询子集")
    @GetMapping("/getChildByValue")
    public QueryResponseResult<SysDict> getChildByValue(@ApiParam(value = "父级字典值",required = true) @RequestParam String value) {
        /**
         * 查询相关的数据
         */
        List<SysDict> sysDictList = sysDictService.getSysDictList();
        SysDict targetSysDict=null;
        Optional<SysDict> target=sysDictList.stream().filter(item->item.getValue()!=null&&item.getValue().equals(value)).findFirst();
        if(target.isPresent()){
            targetSysDict= target.get();
        }
        if(StringUtils.isEmpty(targetSysDict.getPid())){
            sysDictList=(List<SysDict>)TreeUtils.listWithTree(sysDictList);
        }else{
            sysDictList=(List<SysDict>)TreeUtils.listWithTreeByPid(sysDictList,targetSysDict.getPid());
        }
        sysDictList=sysDictList.stream().filter(item->item.getValue()!=null&&item.getValue().equals(value)).collect(Collectors.toList());
        return QueryResponseResult.success(sysDictList);
    }

    @ApiOperation("判断paas服务所属子类是否基础服务类别")
    @GetMapping("/checkBaseService")
    public QueryResponseResult checkBaseService(@ApiParam(value = "paas服务id",required = true) @RequestParam String id){
        return QueryResponseResult.success(sysDictService.isBasePaaSService(id));
    }
}

