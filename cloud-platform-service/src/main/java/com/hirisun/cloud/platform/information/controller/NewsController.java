package com.hirisun.cloud.platform.information.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.platform.information.bean.News;
import com.hirisun.cloud.platform.information.service.NewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 新闻资讯 前端控制器
 * 前台接口，无需登录
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-14
 */
@Api(description = "新闻资讯")
@RestController
@RequestMapping("/api/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    private static Logger logger = LoggerFactory.getLogger(NewsController.class);

    /**
     * 查询新闻列表
     * @return
     */
    @GetMapping("/list")
    public QueryResponseResult<News> list(@ApiParam("页码") @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                                          @ApiParam("每页大小") @RequestParam(required = false,defaultValue = "10") Integer pageSize,
                                          @ApiParam("类型") @RequestParam(required = false,defaultValue = "1") Integer type,
                                          @ApiParam("类型所属") @RequestParam(required = false) String belong){
        //列表不查询新闻详情
        LambdaQueryWrapper<News> wrapper=new QueryWrapper<News>().lambda()
                .select(News.class, info -> !info.getColumn().equals("CONTENT"));
        //
        switch(type){
            case 1:
                break;
            case 2:
                wrapper.like(News::getArea,belong);
                break;
            case 3:
                wrapper.like(News::getPoliceCategory,belong);
                break;
            case 4:
                wrapper.like(News::getProject, belong);
                break;
            default:
                break;
        }
        wrapper.eq(News::getStatus,News.STATUS_ONLINE);
        wrapper.eq(News::getProvincial, type);
        wrapper.orderByDesc(News::getIsTop,News::getTime);
        wrapper.orderByAsc(News::getSortNum);
        Page<News> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page=newsService.page(page,wrapper);
        return QueryResponseResult.success(page);
    }

    /**
     * 新闻详情
     */
    @ApiOperation("新闻详情")
    @GetMapping("/{id}")
    public QueryResponseResult<News> newsInfo(@PathVariable String id) {
        News news = newsService.getById(id);
        news.setViewCount(news.getViewCount()+1);
        newsService.updateById(news);
        return QueryResponseResult.success(news);
    }

}

