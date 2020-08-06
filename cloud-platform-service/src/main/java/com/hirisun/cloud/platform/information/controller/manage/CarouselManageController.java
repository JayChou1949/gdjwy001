package com.hirisun.cloud.platform.information.controller.manage;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.platform.information.bean.Carousel;
import com.hirisun.cloud.platform.information.service.CarouselService;
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
 * 轮播图资讯 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-14
 */
@Api(tags = "平台管理轮播图管理")
@RestController
@RequestMapping("/carouselManage")
public class CarouselManageController {
    @Autowired
    private CarouselService carouselService;

    private static Logger logger = LoggerFactory.getLogger(CarouselManageController.class);

    /**
     * 查询轮播图列表
     * @return
     */
    @ApiOperation("轮播图列表")
    @GetMapping("/page")
    public QueryResponseResult<Carousel> list(
            @LoginUser UserVO user,
            @ApiParam("页码") @RequestParam(required = false,defaultValue = "1") Integer pageNum,
            @ApiParam("每页大小") @RequestParam(required = false,defaultValue = "20") Integer pageSize,
            @ApiParam("状态") @RequestParam(required = false,defaultValue = "0") Integer status,
            @ApiParam("类型") @RequestParam(required = false,defaultValue = "1") Integer type,
            @ApiParam("类型所属") @RequestParam(required = false) String belong,
            @ApiParam("轮播图名称") @RequestParam(required = false) String title){
        // 检测用户权限
        if(NewsParamUtil.checkUserInfomationPermission(user,type,belong)){
            return QueryResponseResult.fail("无权查看该区域数据");
        }
        //列表不查询轮播图详情
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
        //查询未上线
        if(status==null||status.equals(0)){
            wrapper.in(Carousel::getStatus,Carousel.STATUS_AUDIT,Carousel.STATUS_REJECT);
        }else{//已上线
            wrapper.eq(Carousel::getStatus,status);
        }
        wrapper.like(Carousel::getTitle,title);
        wrapper.eq(Carousel::getProvincial, type);
        wrapper.orderByAsc(Carousel::getSortNum);
        Page<Carousel> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page=carouselService.page(page,wrapper);
        return QueryResponseResult.success(page);
    }

    /**
     * 轮播图详情
     */
    @ApiOperation("轮播图详情")
    @GetMapping("/carouselDetail")
    public QueryResponseResult<Carousel> detail(@ApiParam(value = "轮播图id",required = true) @RequestParam String id) {
        Carousel carousel = carouselService.getById(id);
        return QueryResponseResult.success(carousel);
    }
    /**
     * 创建轮播图
     */
    @ApiOperation("创建轮播图")
    @PostMapping("/create")
    public QueryResponseResult<Carousel> create(@LoginUser UserVO user, @ModelAttribute Carousel carousel) {
        /**
         * 1.判断管理员类型
         * 2.判断管理员是否越权
         */
        if(!NewsParamUtil.infomationPermission(user.getType())){
            return QueryResponseResult.fail("无权限操作新闻");
        }
        String newsBelong = StringUtils.isEmpty(carousel.getArea())
                ? StringUtils.isEmpty(carousel.getPoliceCategory())?carousel.getProject():carousel.getPoliceCategory() : carousel.getArea();
        // 判断管理员权限
        if(NewsParamUtil.checkUserInfomationPermission(user,carousel.getProvincial(),newsBelong)){
            return QueryResponseResult.fail("无权操作该区域数据");
        }
        carousel.setCreator(user.getIdCard());
        carousel.setUpdateTime(new Date());
        carousel.setStatus(Carousel.STATUS_WAIT_ONLINE);
        carouselService.save(carousel);
        return QueryResponseResult.success(carousel);
    }
    /**
     * 删除轮播图,逻辑删除
     */
    @ApiOperation("删除轮播图")
    @PostMapping("/delete")
    public QueryResponseResult<Carousel> delete(@LoginUser UserVO user,
                                                @ApiParam(value = "轮播图id",required = true) @RequestParam String id) {
        Carousel carousel = carouselService.getById(id);
        if(carousel==null){
            return QueryResponseResult.fail("轮播图信息不存在");
        }
        String newsBelong = StringUtils.isEmpty(carousel.getArea())
                ? StringUtils.isEmpty(carousel.getPoliceCategory())?carousel.getProject():carousel.getPoliceCategory() : carousel.getArea();
        // 判断管理员权限
        if(NewsParamUtil.checkUserInfomationPermission(user,carousel.getProvincial(),newsBelong)){
            return QueryResponseResult.fail("无权操作该区域数据");
        }
        carousel.setStatus(Carousel.STATUS_DELETE);
        carouselService.updateById(carousel);
        //TODO 远程调用日志模块，记录操作人日志 sys_log
        return QueryResponseResult.success("删除成功");
    }
    /**
     * 编辑轮播图
     */
    @ApiOperation("编辑轮播图")
    @PostMapping("/edit")
    public QueryResponseResult<Carousel> edit(@LoginUser UserVO user,@ModelAttribute Carousel carousel) {
        /**
         * 1.判断管理员类型
         * 2.判断管理员是否越权
         * 3.编辑操作将轮播图状态改为待上线
         */
        if(!NewsParamUtil.infomationPermission(user.getType())){
            return QueryResponseResult.fail("无权限操作轮播图");
        }
        String newsBelong = StringUtils.isEmpty(carousel.getArea())
                ? StringUtils.isEmpty(carousel.getPoliceCategory())?carousel.getProject():carousel.getPoliceCategory() : carousel.getArea();
        if(NewsParamUtil.checkUserInfomationPermission(user,carousel.getProvincial(),newsBelong)){
            return QueryResponseResult.fail("无权操作该区域数据");
        }
        carousel.setStatus(Carousel.STATUS_WAIT_ONLINE);
        carouselService.updateById(carousel);
        return QueryResponseResult.success(carousel);
    }

    /**
     * 轮播图上下线
     */
    @ApiOperation("轮播图上/下线")
    @PostMapping("/publish")
    public QueryResponseResult<Carousel> publish(@ApiParam(value = "轮播图id" ,required = true) @RequestParam String id,
                                                 @ApiParam(value = "类型 1上线 0下线" ,required = true) @RequestParam Integer type) {

        Carousel Carousel = new Carousel();
        Carousel.setId(id);
        if(type.equals(1)){
            Carousel.setStatus(Carousel.STATUS_ONLINE);
        }else{
            Carousel.setStatus(Carousel.STATUS_WAIT_ONLINE);
        }
        carouselService.updateById(Carousel);
        return QueryResponseResult.success(Carousel);
    }

    @ApiOperation("手动排序上/下移动")
    @PostMapping(value = "/move")
    public  QueryResponseResult<Carousel> move(
            @ApiParam(value = "类型 up上移 down下移",required = true) @RequestParam String type,
            @ApiParam(value = "轮播图id",required = true) @RequestParam String id,
            @ApiParam(value = "新闻类别,1省厅 2地市 3警种 4国家专项",required = true) @RequestParam Integer provincial,
            @ApiParam("类别所属") @RequestParam String belong){
        //改变轮播图排序
        carouselService.movePosition(type,id,provincial,belong);

        return QueryResponseResult.success("排序成功");

    }

}

