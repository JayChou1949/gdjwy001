package com.hirisun.cloud.platform.information.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.platform.information.bean.Carousel;
import com.hirisun.cloud.platform.information.service.CarouselService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 轮播图 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-15
 */
@Api(description = "轮播资讯")
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
    public QueryResponseResult<Carousel> list(@ApiParam("类型") @RequestParam(required = false,defaultValue = "1")Integer type,
                                              @ApiParam("类型所属") @RequestParam(required = false) String belong) {
        //列表不查询新闻详情
        LambdaQueryWrapper<Carousel> wrapper=new QueryWrapper<Carousel>().lambda()
                .select(Carousel.class, info -> !info.getColumn().equals("CONTENT"));
        switch(type){
            case 1:
                break;
            case 2:
                wrapper.like(Carousel::getArea,belong);
                break;
            case 3:
                wrapper.like(Carousel::getPoliceCategory,belong);
                break;
            case 4:
                wrapper.like(Carousel::getProject, belong);
                break;
            default:
                break;
        }
        wrapper.eq(Carousel::getStatus,Carousel.STATUS_ONLINE);
        wrapper.eq(Carousel::getProvincial, type);
        wrapper.orderByAsc(Carousel::getSortNum);
        wrapper.orderByDesc(Carousel::getUpdateTime);
        List<Carousel> list=carouselService.list(wrapper);
        //TODO 循环查出实际图片路径

        return QueryResponseResult.success(list);
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

