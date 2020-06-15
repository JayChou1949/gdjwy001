package com.upd.hwcloud.controller.backend.serviceManagement;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.ServiceLimit;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.service.IUserService;
import com.upd.hwcloud.service.application.IServiceLimitService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author wuc
 * @date 2020/3/3
 */
@Api(description = "服务限额")
@RestController
@RequestMapping("/service-limit")
public class ServiceLimitController {

    @Autowired
    private IServiceLimitService serviceLimitService;

    @Autowired
    private IUserService userService;


    @ApiOperation("获取总配额")
    @RequestMapping(value = "/quota",method = RequestMethod.GET)
    public R quota(String formNum,String area,String policeCategory,@RequestParam(value = "nationalSpecialProject",required = false) String nationalSpecialProject){
            return R.ok(serviceLimitService.getQuota(formNum,area,policeCategory,nationalSpecialProject));
    }

    @ApiOperation("新增或更新")
    @RequestMapping(value = "/saveOrUpdate",method = RequestMethod.POST)
    public R save(@LoginUser User user,@RequestBody ServiceLimit serviceLimit){
        if(!userService.checkLimitPermission(user,1,serviceLimit.getArea())){
            return R.error("无权操作数据");
        }
        if(serviceLimit.getId()==null){
            int count = serviceLimitService.count(new QueryWrapper<ServiceLimit>().lambda().eq(ServiceLimit::getArea,serviceLimit.getArea()).eq(ServiceLimit::getResourceType,serviceLimit.getResourceType()));
            if(count>0){
                return R.error("该限额配置已存在");
            }
        }
       Boolean res =  serviceLimitService.saveOrUpdate(serviceLimit);
       if(res){
           return R.ok("操作成功");
       }else {
           return R.error("操作失败");
       }
    }

    @ApiOperation("删除")
    @RequestMapping(value = "delete/{id}",method = {RequestMethod.GET,RequestMethod.POST})
    public R delete(@LoginUser User user,@PathVariable String id){
        if(!userService.checkLimitPermission(user,1,null)){
            return  R.error("无权操作");
        }

        boolean res = serviceLimitService.removeById(id);
        if(res){
            return R.ok();
        }
        return R.error("删除失败");
    }


    @ApiOperation("分页")
    @RequestMapping(value = "/page",method = {RequestMethod.GET,RequestMethod.POST})
    public R page(@LoginUser User user,@RequestParam(required = false,defaultValue = "10") long pageSize, @RequestParam(required = false,defaultValue = "1") long current, String area){
        IPage<ServiceLimit> page = new Page<>(current,pageSize);
        if(!userService.checkLimitPermission(user,0,area)){
            return R.error("无权查看该地区数据");
        }
        if(StringUtils.isNotBlank(area)){
            page = serviceLimitService.page(page,new QueryWrapper<ServiceLimit>().lambda().eq(ServiceLimit::getArea,area).orderByDesc(ServiceLimit::getArea).orderByDesc(ServiceLimit::getResourceType));
        }else {
            page = serviceLimitService.page(page,new QueryWrapper<ServiceLimit>().lambda().orderByDesc(ServiceLimit::getArea).orderByDesc(ServiceLimit::getResourceType));
        }
        return R.ok(page);

    }




}
