package com.hirisun.cloud.daas.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.daas.bean.Bigdata;
import com.hirisun.cloud.daas.service.BigdataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * 大数据库服务目录 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-02
 */
@Controller
@Api(tags = "大数据目录管理")
@RequestMapping("/bigdata")
public class BigdataController {

    @Autowired
    private BigdataService bigdataService;

    @ApiOperation("分页查询大数据目录")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", paramType="query", dataType="String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="query", dataType="String"),
            @ApiImplicitParam(name="name", value="服务名", paramType="query", dataType="String"),
            @ApiImplicitParam(name="dataFrom", value="数据来源", paramType="query", dataType="String"),
            @ApiImplicitParam(name="collectionUnit", value="采集单位", paramType="query", dataType="String"),
            @ApiImplicitParam(name="category", value="所属分类", paramType="query", dataType="String")
    })
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public QueryResponseResult page(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                    @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) String dataFrom,
                                    @RequestParam(required = false) String collectionUnit,
                                    @RequestParam(required = false) String category) {
        Page<Bigdata> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(pageNum);
        page = bigdataService.getPage(page, name, dataFrom, collectionUnit, category);
        return QueryResponseResult.success(page);
    }
}

