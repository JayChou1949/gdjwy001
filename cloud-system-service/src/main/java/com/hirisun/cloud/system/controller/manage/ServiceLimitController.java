package com.hirisun.cloud.system.controller.manage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.util.PermissionUtil;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.model.system.ServiceLimitVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.system.bean.ServiceLimit;
import com.hirisun.cloud.system.service.ServiceLimitService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@Api("服务限额")
@RestController
@RequestMapping("/system/resource/limit")
public class ServiceLimitController {

    @Autowired
    private ServiceLimitService serviceLimitService;

    @ApiOperation("获取总配额")
    @GetMapping(value = "/quota")
    public QueryResponseResult quota(@RequestParam(value = "formNum")String formNum,
    		@RequestParam(value = "area")String area,
    		@RequestParam(value = "policeCategory",required = false)String policeCategory,
    		@RequestParam(value = "nationalSpecialProject",required = false) String nationalSpecialProject,
    		@RequestParam(value = "clusterName",required = false) String clusterName){
    	
    	ServiceLimitVo serviceLimitVo = serviceLimitService.getQuota(formNum,area,policeCategory,nationalSpecialProject,clusterName);
        return QueryResponseResult.success(serviceLimitVo);
    }

    @ApiOperation("新增或更新")
    @PostMapping(value = "/saveOrUpdate")
    public ResponseResult save(@LoginUser UserVO user,@RequestBody ServiceLimit serviceLimit){
        if(!PermissionUtil.checkLimitPermission(user,1,serviceLimit.getArea())){
            return QueryResponseResult.fail("无权操作数据");
        }
        if(serviceLimit.getId()==null){
            int count = serviceLimitService.count(new QueryWrapper<ServiceLimit>().lambda().eq(ServiceLimit::getArea,serviceLimit.getArea()).eq(ServiceLimit::getResourceType,serviceLimit.getResourceType()));
            if(count>0)return QueryResponseResult.fail("该限额配置已存在");
        }
       Boolean res =  serviceLimitService.saveOrUpdate(serviceLimit);
       if(res)return QueryResponseResult.success();
       return QueryResponseResult.fail("操作失败");    
    }

    @ApiOperation("删除")
    @GetMapping(value = "delete")
    public ResponseResult delete(@LoginUser UserVO user,
    		@RequestParam(value = "id",required = true)String id){
    	
        if(!PermissionUtil.checkLimitPermission(user,1,null)){
        	return QueryResponseResult.fail("无权操作数据");
        }

        boolean res = serviceLimitService.removeById(id);
        if(res)return QueryResponseResult.success();
        return QueryResponseResult.fail("删除失败");
    }


    @ApiOperation("分页")
    @GetMapping(value = "/page")
    public ResponseResult page(@LoginUser UserVO user,
    		@RequestParam(required = false,defaultValue = "10") long pageSize, 
    		@RequestParam(required = false,defaultValue = "1") long current,
    		@RequestParam(required = false)String area){
    	
        IPage<ServiceLimit> page = new Page<>(current,pageSize);
        if(!PermissionUtil.checkLimitPermission(user,0,area)){
        	return QueryResponseResult.fail("无权查看该地区数据");
        }
        if(StringUtils.isNotBlank(area)){
            page = serviceLimitService.page(page,new QueryWrapper<ServiceLimit>().lambda().eq(ServiceLimit::getArea,area).orderByDesc(ServiceLimit::getArea).orderByDesc(ServiceLimit::getResourceType));
        }else {
            page = serviceLimitService.page(page,new QueryWrapper<ServiceLimit>().lambda().orderByDesc(ServiceLimit::getArea).orderByDesc(ServiceLimit::getResourceType));
        }
        return QueryResponseResult.success(page);

    }

    @ApiIgnore
    @ApiOperation("增加额度")
    @GetMapping(value = "/increaseQuota")
    public void increaseQuota(@RequestParam(value = "appInfoId") String appInfoId,
    		@RequestParam(value = "area")String area,
    		@RequestParam(value = "policeCategory")String policeCategory,
    		@RequestParam(value = "nationalSpecialProject")String nationalSpecialProject) {
    	serviceLimitService.increaseQuota(appInfoId, area, policeCategory, nationalSpecialProject);
    }


}
