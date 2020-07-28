package com.hirisun.cloud.platform.information.controller.manage;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.platform.information.bean.Carousel;
import com.hirisun.cloud.platform.information.service.CarouselService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 轮播图资讯 前端控制器
 * TODO 后台操作轮播图接口，需验证登录
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-14
 */
@Api(description = "平台管理轮播图管理")
@RestController
@RequestMapping("/carouselManage")
public class CarouselManageController {
    @Autowired
    private CarouselService carouselService;

    private static Logger logger = LoggerFactory.getLogger(CarouselManageController.class);

    /**
     * 后台管理接口需验证登录
     */
//    this.needLogin=true;

    /**
     * 查询轮播图列表
     * @return
     */
    @ApiOperation("轮播图列表")
    @GetMapping("/page")
    public QueryResponseResult<Carousel> list(
            @ApiParam("页码") @RequestParam(required = false,defaultValue = "1") Integer pageNum,
            @ApiParam("每页大小") @RequestParam(required = false,defaultValue = "20") Integer pageSize,
            @ApiParam("状态") @RequestParam(required = false,defaultValue = "0") Integer status,
            @ApiParam("类型") @RequestParam(required = false,defaultValue = "1") Integer type,
            @ApiParam("类型所属") @RequestParam(required = false) String belong,
            @ApiParam("轮播图名称") @RequestParam(required = false) String title){
        /*
         * TODO 检测用户权限
         */
        if(!this.checkUserPermission(null)){
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
    @GetMapping("/{id}")
    public QueryResponseResult<Carousel> CarouselInfo(@PathVariable String id) {
        Carousel Carousel = carouselService.getById(id);
        return QueryResponseResult.success(Carousel);
    }
    /**
     * 创建轮播图
     */
    @ApiOperation("创建轮播图")
    @PostMapping("/create")
    public QueryResponseResult<Carousel> create(Carousel Carousel) {
        //TODO 判断管理员类型

        //TODO 判断管理员权限
        if(!this.checkUserPermission(null)){
            return QueryResponseResult.fail("无权查看该区域数据");
        }
        Carousel.setCreator(null);
        Carousel.setUpdateTime(new Date());
        Carousel.setStatus(Carousel.STATUS_WAIT_ONLINE);
        carouselService.save(Carousel);
        return QueryResponseResult.success(Carousel);
    }
    /**
     * 删除轮播图,逻辑删除
     */
    @ApiOperation("删除轮播图")
    @PostMapping("/delete/{id}")
    public QueryResponseResult<Carousel> delete(@PathVariable String id) {
        Carousel Carousel = carouselService.getById(id);
        //TODO 判断管理员权限
        if(!this.checkUserPermission(null)){
            return QueryResponseResult.fail("无权操作该区域数据");
        }
        if (Carousel != null) {
            Carousel.setStatus(Carousel.STATUS_DELETE);
            carouselService.updateById(Carousel);
        }
        //TODO 远程调用日志模块，记录操作人日志 sys_log
        return QueryResponseResult.success("删除成功");
    }
    /**
     * 编辑轮播图
     */
    @ApiOperation("编辑轮播图")
    @PostMapping("/edit")
    public QueryResponseResult<Carousel> edit(@RequestBody Carousel Carousel) {
        //TODO 判断管理员类型

        //TODO 判断管理员权限
        if(!this.checkUserPermission(null)){
            return QueryResponseResult.fail("无权查看该区域数据");
        }
        //编辑操作将轮播图状态改为待上线
        Carousel.setStatus(Carousel.STATUS_WAIT_ONLINE);
        carouselService.updateById(Carousel);
        return QueryResponseResult.success(Carousel);
    }

    /**
     * 轮播图上下线
     */
    @ApiOperation("轮播图上/下线")
    @PostMapping("/publish/{id}")
    public QueryResponseResult<Carousel> publish(@PathVariable String id,
                                                 @ApiParam("类型 1上线 0下线") @RequestParam(required = true) Integer type) {
        //判断管理员类型

        //判断管理员权限
        if(!this.checkUserPermission(null)){
            return QueryResponseResult.fail("无权查看该区域数据");
        }
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
    @PostMapping(value = "/move/{type}/{id}")
    public  QueryResponseResult<Carousel> move(
            @ApiParam("类型 up上移 down下移") @PathVariable String type,
            @ApiParam("轮播图id") @PathVariable String id,
            @ApiParam("类别") Integer provincial,
            @ApiParam("类别所属")String belong){
        //改变轮播图排序
        carouselService.movePosition(type,id,provincial,belong);

        return QueryResponseResult.success("排序成功");

    }

    /**
     * TODO 检查用户权限
     */
    public static boolean checkUserPermission(UserVO user) {
        return true;
    }

}

