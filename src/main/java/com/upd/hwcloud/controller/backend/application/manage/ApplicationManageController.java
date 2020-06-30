package com.upd.hwcloud.controller.backend.application.manage;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.SaasApplication;
import com.upd.hwcloud.bean.entity.SaasApplicationExt;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.manage.ApplicationManage;
import com.upd.hwcloud.bean.entity.application.manage.ApplicationRecords;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.service.application.manage.IApplicationManageService;
import com.upd.hwcloud.service.application.manage.IApplicationRecordsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 应用运营管理 前端控制器
 * </p>
 *
 * @author lqm
 * @since 2020-06-29
 */
@Api(description = "配额设置")
@RestController
@RequestMapping("/applicationManage")
public class ApplicationManageController {

    @Autowired
    private IApplicationManageService iApplicationManageService;
    @Autowired
    private IApplicationRecordsService iApplicationRecordsService;


    @ApiOperation(value = "配额修改")
    @RequestMapping(value = "/v1/updateQuota",method = RequestMethod.GET)
    public R updateQuota(@LoginUser User user, @RequestParam(value = "id") String id, @RequestParam(value = "quota")
            Integer quota, @RequestParam(value = "oldQuota") Integer oldQuota, @RequestParam(value = "areaOrPolice") String areaOrPolice){
        ApplicationRecords applicationRecords=new ApplicationRecords();
        //设置限额
        iApplicationManageService.updateQuota(id,quota);
        applicationRecords.setRefId(id);
        applicationRecords.setNewUserQuota(quota);
        applicationRecords.setUserQuota(oldQuota);
        if(user!=null){
            applicationRecords.setUserName(user.getName());
        }
        applicationRecords.setModfiyTime(new Date());
        applicationRecords.setAreaOrPolice(areaOrPolice);
        //记录修改记录
        iApplicationRecordsService.addQuotaRecord(applicationRecords);
        return R.ok();
    }

    @ApiOperation(value = "查询配额信息列表")
    @RequestMapping(value = "/v1/queryQuotaList",method = RequestMethod.GET)
    public R getApplicationQuotaDetail(@RequestParam(value = "current") Long current,@RequestParam(value = "size") Long size) {
        Page<ApplicationManage> page=new Page<>(current, size);
        Page<ApplicationManage> applicationQuota = iApplicationManageService.getApplicationQuota(page);
        return R.ok(applicationQuota);
    }

    @ApiOperation(value = "查询单个配额信息")
    @RequestMapping(value = "/v1/queryQuota",method = RequestMethod.GET)
    public R getApplicationQuota(@RequestParam(value = "areaOrPolice") String areaOrPolice){
        ApplicationManage applicationQuotaDetail = iApplicationManageService.getApplicationQuotaDetail(areaOrPolice);
        return R.ok(applicationQuotaDetail);
    }

    @ApiOperation(value = "应用运营管理")
    @ApiImplicitParam(name = "areaOrPolice",value = "地市/警种",paramType = "query",dataType = "String")
    @RequestMapping(value = "/getAppManage",method = RequestMethod.GET)
    public R getAppManage(@RequestParam(value = "areaOrPolice",required = false) String areaOrPolice,Long current,Long size){
        IPage<ApplicationManage> page = new Page<>(current,size);
        try {
            page = iApplicationManageService.getAppManage(page,areaOrPolice);
        }catch (Exception e) {
            return R.error();
        }
        return R.ok(page);
    }

    @ApiOperation(value = "已有用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "creatorName",value = "姓名",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "creator",value = "身份证号",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "orgName",value = "所属单位",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "areaOrPolice",value = "地市/警种",paramType = "query",dataType = "String")
    })
    @RequestMapping(value = "/getUser",method = RequestMethod.GET)
    public R getUser(@RequestParam(value = "creatorName",required = false) String creatorName,
                     @RequestParam(value = "creator",required = false) String creator,
                     @RequestParam(value = "orgName",required = false) String orgName,
                     @RequestParam(value = "areaOrPolice",required = false) String areaOrPolice,
                     @RequestParam(defaultValue = "1") Long current,
                     @RequestParam(defaultValue = "10") Long size){
        IPage<SaasApplication> page = new Page<>(current,size);
        try {
            page = iApplicationManageService.getUser(page,creatorName,creator,orgName,areaOrPolice);
        }catch (Exception e) {
            return R.error();
        }
        return R.ok(page);
    }

    @ApiOperation(value = "权限应用详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "creator",value = "身份证号",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "serviceName",value = "应用名称",paramType = "query",dataType = "String"),
            })
    @RequestMapping(value = "/getAppDetails",method = RequestMethod.GET)
    public R getAppDetails(@RequestParam(value = "creator") String creator,
                     @RequestParam(value = "serviceName",required = false) String serviceName,
                           @RequestParam(defaultValue = "1") Long current,
                           @RequestParam(defaultValue = "10") Long size){
        IPage<SaasApplicationExt> page = new Page<>(current,size);
        try {
            page = iApplicationManageService.getAppDetails(page,creator,serviceName);
        }catch (Exception e) {
            return R.error();
        }
        return R.ok(page);
    }


    @ApiOperation(value = "当前账号总限额量,剩余量")
    @ApiImplicitParam(name = "areaOrPolice",value = "地市/警种",paramType = "query",dataType = "String")
    @RequestMapping(value = "/v1/getQuota",method = RequestMethod.GET)
    public R getQuota(@RequestParam(value = "areaOrPolice") String areaOrPolice){

        ApplicationManage applicationQuotaDetail = iApplicationManageService.getApplicationQuotaDetail(areaOrPolice);
        Map map=new HashMap();
        if(applicationQuotaDetail!=null){
            Integer availableQuotaNum = applicationQuotaDetail.getAvailableQuotaNum();
            Integer userTotal = applicationQuotaDetail.getUserQuotaNum();
            map.put("availableQuotaNum",availableQuotaNum);
            map.put("quotaTotal",userTotal);
        }

        return R.ok(map);
    }
}

