package com.upd.hwcloud.controller.backend.css;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.contains.ReviewStatus;
import com.upd.hwcloud.bean.entity.Log;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.webManage.Carousel;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.utils.IpUtil;
import com.upd.hwcloud.service.IUserService;
import com.upd.hwcloud.service.webManage.ICarouselService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 轮播图 前端控制器
 * </p>
 *
 * @author huru
 * @since 2018-10-26
 */
@Api(description = "轮播图管理接口")
@Controller
@RequestMapping("/carousel")
public class CarouselController {

    @Autowired
    ICarouselService iCarouselService;

    @Autowired
    private IUserService userService;

    @ApiOperation("新建轮播图")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public R create(@LoginUser User user, @RequestBody Carousel carousel) {
        //权限校验
        if(!permissionCheck(user,carousel)){
            return  R.error("无权/越权操作");
        }
        carousel.setId(null);
        carousel.setCreator(user.getIdcard());
        carousel.setStatus(ReviewStatus.PRO_ONLINE.getCode());
        carousel.insert();
        return R.ok();
    }

    /**
     * 上/下线
     * @param result 操作结果 1:上线,其它:下线
     * @param remark 操作描述
     */
    @ApiOperation("轮播图上/下线")
    @RequestMapping(value = "/publish/{id}")
    @ResponseBody
    public R publish(@LoginUser User user, @PathVariable String id, Integer result,
                     @RequestParam(required = false) String remark) {
        if (result == null) {
            return R.error();
        }
        iCarouselService.publish(user, id, result, remark);
        return R.ok();
    }

    /**
     * 分页
     * @param status {@link com.upd.hwcloud.bean.contains.ReviewStatus}
     */
    @ApiOperation("分页查询轮播图")
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
        Page<Carousel> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page = iCarouselService.getPage(page,user,status,title,type,belong);
        return R.ok(page);
    }

    @ApiOperation("删除轮播图")
    @RequestMapping(value = "/delete/{id}")
    @ResponseBody
    public R delete(@LoginUser User user, @PathVariable String id) {
        Carousel carousel = iCarouselService.getById(id);
        if(!permissionCheck(user,carousel)){
            return R.error("无权删除该轮播图");
        }
        carousel.setStatus(ReviewStatus.DELETE.getCode());
        carousel.updateById();
        new Log(user.getIdcard(),"轮播图id："+id,"删除轮播图", IpUtil.getIp()).insert();
        return R.ok();
    }

    @ApiOperation("轮播图详情")
    @RequestMapping(value = "/{id}")
    @ResponseBody
    public R detail(@LoginUser User user, @PathVariable String id) {
        Carousel carousel = iCarouselService.getById(id);
        return R.ok(carousel);
    }

    @ApiOperation("修改轮播图")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public R edit(@LoginUser User user, @RequestBody Carousel carousel) {

        //权限校验
        if(!permissionCheck(user,carousel)){
            return  R.error("无权/越权操作");
        }
        carousel.setStatus(ReviewStatus.PRO_ONLINE.getCode());
        carousel.updateById();
        return R.ok();
    }


    @ApiOperation("手动排序上/下移动")
    @RequestMapping(value = "/move/{type}/{id}",method = RequestMethod.GET)
    @ResponseBody
    public  R move(@PathVariable String type,@PathVariable String id,Integer provincial,String belong){
        return iCarouselService.move(type,id,provincial,belong);

    }



    /**
     * 权限校验
     * @param user
     * @param carousel
     * @return false无权 true有权
     */
    private boolean permissionCheck(User user,Carousel carousel){
        if(!userService.newsAndCarouselAbility(user.getType())){
            //不是管理员 省厅管理员 租户管理员 => 无权
            return false;
        }
        if(userService.isNewsAndCarouselCrossPermission(user,carousel.getProvincial(),carousel.getArea(),carousel.getPoliceCategory(),carousel.getProject())){
                //越权 =》 无权限
                return false;
        }
        return true;
    }

}

