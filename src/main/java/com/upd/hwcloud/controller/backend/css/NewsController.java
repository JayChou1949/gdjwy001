package com.upd.hwcloud.controller.backend.css;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.contains.ReviewStatus;
import com.upd.hwcloud.bean.entity.Log;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.webManage.News;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.utils.IpUtil;
import com.upd.hwcloud.service.IOperateRecordService;
import com.upd.hwcloud.service.IUserService;
import com.upd.hwcloud.service.webManage.INewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import javax.lang.model.element.NestingKind;

/**
 * <p>
 * 新闻资讯 前端控制器
 * </p>
 *
 * @author huru
 * @since 2018-10-26
 */
@Api(description = "新闻管理接口")
@Controller
@RequestMapping("/news")
public class NewsController {

    @Autowired
    INewsService iNewsService;
    @Autowired
    IOperateRecordService iOperateRecordService;
    @Autowired
    private IUserService userService;

    @ApiOperation("新建新闻")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public R create(@LoginUser User user, @RequestBody News news) {
        //是否是管理员或租户管理员
        if(!userService.newsAndCarouselAbility(user.getType())){
            return R.error("无权限操作新闻");
        }
        //是否越权
        if(userService.isNewsAndCarouselCrossPermission(user,news.getProvincial(),news.getArea(),news.getPoliceCategory(),news.getProject())){
            return R.error("越权操作");
        }

        news.setId(null);
        news.setCreator(user.getIdcard());
        news.setStatus(ReviewStatus.PRO_ONLINE.getCode());
        if (news.getTime() == null) {
            news.setTime(new Date());
        }
        news.insert();
        return R.ok();
    }

    /**
     * 上/下线
     * @param result 操作结果 1:上线,其它:下线
     * @param remark 操作描述
     */
    @ApiOperation("新闻上/下线")
    @RequestMapping(value = "/publish/{id}")
    @ResponseBody
    public R publish(@LoginUser User user, @PathVariable String id, Integer result,
                     @RequestParam(required = false) String remark) {
        if (result == null) {
            return R.error();
        }
        iNewsService.publish(user, id, result, remark);
        return R.ok();
    }

    /**
     * 分页
     * @param status {@link com.upd.hwcloud.bean.contains.ReviewStatus}
     */
    @ApiOperation("新闻分页")
    @RequestMapping(value = "/page")
    @ResponseBody
    public R page(@LoginUser User user, Integer pageNum, Integer pageSize, Integer status,
                  @RequestParam(required = false) String title,@RequestParam(required = false,defaultValue = "1") Integer type,String belong) {
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 20;
        }
        if(userService.isNewsAndCarouselCrossPermission(user,type,belong,belong,belong)){
            return R.error("无权查看该区域数据");
        }
        Page<News> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page = iNewsService.getPage(page,user,status,title,type,belong);
        return R.ok(page);
    }

    @ApiOperation("删除新闻")
    @RequestMapping(value = "/delete/{id}")
    @ResponseBody
    public R delete(@LoginUser User user, @PathVariable String id) {


        News news = iNewsService.getById(id);
        if(userService.isNewsAndCarouselCrossPermission(user,news.getProvincial(),news.getArea(),news.getPoliceCategory(),news.getProject())){
            return R.error("无权删除该新闻");
        }
        news.setStatus(ReviewStatus.DELETE.getCode());
        news.updateById();
        new Log(user.getIdcard(),"新闻id："+id,"删除新闻", IpUtil.getIp()).insert();
        return R.ok();
    }

    @ApiOperation("新闻详情")
    @RequestMapping(value = "/{id}")
    @ResponseBody
    public R detail(@LoginUser User user, @PathVariable String id) {
        News news = iNewsService.getById(id);
        return R.ok(news);
    }

    @ApiOperation("修改新闻")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public R edit(@LoginUser User user, @RequestBody News news) {

        //是否是管理员或租户管理员
        if(!userService.newsAndCarouselAbility(user.getType())){
            return R.error("无权限操作新闻");
        }
        //是否越权
        if(userService.isNewsAndCarouselCrossPermission(user,news.getProvincial(),news.getArea(),news.getPoliceCategory(),news.getProject())){
            return R.error("越权操作");
        }

        news.setStatus(ReviewStatus.PRO_ONLINE.getCode());
        news.updateById();
        return R.ok();
    }

    @ApiOperation("新闻置顶")
    @RequestMapping(value = "/top/{id}", method = RequestMethod.GET)
    @ResponseBody
    public R top(@PathVariable String id) {
        News origin = iNewsService.getById(id);
        //省厅
        if(origin.getProvincial().intValue()==1){
            iNewsService.update(new News(), new UpdateWrapper<News>().lambda().eq(News::getProvincial,origin.getProvincial()).set(News::getIsTop, 0));
        }else if(origin.getProvincial().intValue()==2){ //地市
            iNewsService.update(new News(), new UpdateWrapper<News>().lambda().eq(News::getProvincial,origin.getProvincial()).eq(News::getArea,origin.getArea()).set(News::getIsTop, 0));
        }else if(origin.getProvincial().intValue()==3){ //警种
            iNewsService.update(new News(), new UpdateWrapper<News>().lambda().eq(News::getProvincial,origin.getProvincial()).eq(News::getPoliceCategory,origin.getPoliceCategory()).set(News::getIsTop, 0));
        }else if(origin.getProvincial().intValue()==4){//国家专项
            iNewsService.update(new News(),new UpdateWrapper<News>().lambda().eq(News::getProvincial,origin.getProvincial()).eq(News::getProject,origin.getProject()).set(News::getIsTop,0));
        }
        News news = new News();
        news.setId(id);
        news.setIsTop(1L);
        return R.ok(news.updateById());
    }

    @ApiOperation("新闻取消置顶")
    @RequestMapping(value = "/top/undo/{id}", method = RequestMethod.GET)
    @ResponseBody
    public R undoTop(@PathVariable String id) {
        News news = new News();
        news.setId(id);
        news.setIsTop(0L);
        return R.ok(news.updateById());
    }

}

