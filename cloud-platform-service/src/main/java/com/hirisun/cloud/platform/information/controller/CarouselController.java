package com.hirisun.cloud.platform.information.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hirisun.cloud.api.file.FileApi;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.file.FileSystemVO;
import com.hirisun.cloud.platform.information.bean.Carousel;
import com.hirisun.cloud.platform.information.service.CarouselService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 轮播图 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-15
 */
@Api(tags = "轮播资讯")
@RestController
@RequestMapping("/api/carousel")
public class CarouselController {

    @Autowired
    private CarouselService carouselService;



    /**
     * 轮播图分页接口
     * @return
     */
    @ApiOperation("获取轮播图列表")
    @GetMapping("/list")
    public QueryResponseResult<Carousel> list(@ApiParam(value = "类型 1省厅 2地市 3警种 4 国家专项",required = true) @RequestParam(required = false,defaultValue = "1")Integer type,
                                              @ApiParam("类型所属") @RequestParam(required = false) String belong) {

        List<Carousel> carouselList = carouselService.allList(type, belong);
        return QueryResponseResult.success(carouselList);
    }
    /**
     * 轮播图详情
     */
    @ApiOperation("轮播图详情")
    @GetMapping("/{id}")
    public QueryResponseResult<Carousel> carouselInfo(@PathVariable String id) {
        Carousel carousel = carouselService.getById(id);
        return QueryResponseResult.success(carousel);
    }

}

