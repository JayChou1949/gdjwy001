package com.hirisun.cloud.platform.information.controller.manage;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.platform.information.bean.News;
import com.hirisun.cloud.platform.information.service.NewsService;
import com.hirisun.cloud.platform.information.util.NewsParamUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 新闻资讯 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-14
 */
@Api(tags = "平台管理新闻资讯管理")
@RestController
@RequestMapping("/newsManage")
public class NewsManageController {
    @Autowired
    private NewsService newsService;

    private static Logger logger = LoggerFactory.getLogger(NewsManageController.class);

    /**
     * 查询新闻列表
     * @return
     */
    @ApiOperation("新闻列表")
    @GetMapping("/page")
    public QueryResponseResult<News> list(
            @LoginUser UserVO user,
            @ApiParam("页码") @RequestParam(required = false,defaultValue = "1") Integer pageNum,
            @ApiParam("每页大小") @RequestParam(required = false,defaultValue = "20") Integer pageSize,
            @ApiParam("状态 0未上线 2已上线") @RequestParam(required = false,defaultValue = "0") Integer status,
            @ApiParam("类型") @RequestParam(required = false,defaultValue = "1") Integer type,
            @ApiParam("类型所属") @RequestParam(required = false) String belong,
            @ApiParam("新闻名称") @RequestParam(required = false) String title){
        // 检测用户权限
        if(NewsParamUtil.checkUserInfomationPermission(user,type,belong)){
            return QueryResponseResult.fail("无权查看该区域数据");
        }
        //列表不查询新闻详情
        LambdaQueryWrapper<News> wrapper=new QueryWrapper<News>().lambda()
                .select(News.class, info -> !info.getColumn().equals("CONTENT"));
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
        //查询未上线
        if(status==null||status.equals(0)){
            wrapper.in(News::getStatus,News.STATUS_AUDIT,News.STATUS_REJECT);
        }else{//已上线
            wrapper.eq(News::getStatus,status);
        }
        if(!StringUtils.isEmpty(title)){
            wrapper.like(News::getTitle,title);
        }
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
    @GetMapping("/newsDetail")
    public QueryResponseResult<News> newsInfo(@ApiParam(value = "新闻id",required = true) @RequestParam String id) {
        News news = newsService.getById(id);
        return QueryResponseResult.success(news);
    }
    /**
     * 创建新闻
     */
    @ApiOperation("创建新闻")
    @PostMapping("/create")
    public QueryResponseResult<News> create(@LoginUser UserVO user, @ModelAttribute News news) {
        // 判断管理员类型
        if(!NewsParamUtil.infomationPermission(user.getType())){
            return QueryResponseResult.fail("无权限操作新闻");
        }

        String newsBelong = StringUtils.isEmpty(news.getArea())
                ? StringUtils.isEmpty(news.getPoliceCategory())?news.getProject():news.getPoliceCategory() : news.getArea();
        // 判断管理员权限
        if(NewsParamUtil.checkUserInfomationPermission(user,news.getProvincial(),newsBelong)){
            return QueryResponseResult.fail("无权操作该区域数据");
        }
        news.setCreator(user.getIdCard());
        news.setUpdateTime(new Date());
        news.setViewCount(0L);
        news.setStatus(News.STATUS_WAIT_ONLINE);
        newsService.save(news);
        return QueryResponseResult.success(news);
    }
    /**
     * 删除新闻,逻辑删除
     */
    @ApiOperation("删除新闻")
    @PostMapping("/delete")
    public QueryResponseResult<News> delete(@LoginUser UserVO user,
                                            @ApiParam(value = "新闻id",required = true) @RequestParam String id) {
        News news = newsService.getById(id);
        if(news==null){
            return QueryResponseResult.fail("新闻信息不存在");
        }
        String newsBelong = StringUtils.isEmpty(news.getArea())
                ? StringUtils.isEmpty(news.getPoliceCategory())?news.getProject():news.getPoliceCategory() : news.getArea();
        // 判断管理员权限
        if(NewsParamUtil.checkUserInfomationPermission(user,news.getProvincial(),newsBelong)){
            return QueryResponseResult.fail("无权操作该区域数据");
        }
        news.setStatus(News.STATUS_DELETE);
        newsService.deleteNews(news,user);
        return QueryResponseResult.success("删除成功");
    }
    /**
     * 编辑新闻
     */
    @ApiOperation("编辑新闻")
    @PostMapping("/edit")
    public QueryResponseResult<News> edit(@LoginUser UserVO user,@ModelAttribute News news) {
        /**
         * 1.判断管理员类型
         * 2.判断管理员是否越权
         * 3.编辑操作将新闻状态改为待上线
         */
        if(!NewsParamUtil.infomationPermission(user.getType())){
            return QueryResponseResult.fail("无权限操作新闻");
        }
        String newsBelong = StringUtils.isEmpty(news.getArea())
                ? StringUtils.isEmpty(news.getPoliceCategory())?news.getProject():news.getPoliceCategory() : news.getArea();
        if(NewsParamUtil.checkUserInfomationPermission(user,news.getProvincial(),newsBelong)){
            return QueryResponseResult.fail("无权操作该区域数据");
        }
        news.setStatus(News.STATUS_WAIT_ONLINE);
        newsService.updateById(news);
        return QueryResponseResult.success(news);
    }

    /**
     * 新闻上下线
     */
    @ApiOperation("新闻上/下线")
    @PostMapping("/publish")
    public QueryResponseResult<News> publish(
                                             @ApiParam(value = "新闻id",required = true) @RequestParam String id,
                                             @ApiParam(value = "类型 1上线 0下线",required = true) @RequestParam Integer type) {
        News news = new News();
        news.setId(id);
        if(type.equals(1)){
            news.setStatus(News.STATUS_ONLINE);
        }else{
            news.setStatus(News.STATUS_WAIT_ONLINE);
        }
        newsService.updateById(news);
        return QueryResponseResult.success(news);
    }
    /**
     * 新闻置顶、取消置顶接口
     * 1.新闻有不同类型，每个类型只有一个新闻可以设置成置顶，同类型其余已置顶的状态会被更新
     */
    @ApiOperation("新闻置顶操作")
    @PostMapping("/top")
    public QueryResponseResult<News> edit(@ApiParam(value = "新闻id",required = true) @RequestParam String id,
                                          @ApiParam(value = "类型 1置顶 0取消置顶",required = true) @RequestParam Integer type) {
        News news = newsService.getById(id);
        if (news==null) {
            return QueryResponseResult.fail("新闻信息不存在");
        }
        newsService.setTop(news,type);
        return QueryResponseResult.success(null);
    }
}

