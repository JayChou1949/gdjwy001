package com.upd.hwcloud.controller.backend.application.manage;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.manage.ApplicationManage;
import com.upd.hwcloud.bean.entity.application.manage.ApplicationRecords;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.utils.UUIDUtil;
import com.upd.hwcloud.service.application.manage.IApplicationManageService;
import com.upd.hwcloud.service.application.manage.IApplicationRecordsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

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
    public R getPaasZhzy(@LoginUser User user, @RequestParam(value = "id") String id, @RequestParam(value = "quota")
            Integer quota, @RequestParam(value = "oldQuota") Integer oldQuota){
        ApplicationRecords applicationRecords=new ApplicationRecords();
        iApplicationManageService.updateQuota(id,quota);
        applicationRecords.setRefId(id);
        applicationRecords.setId(UUIDUtil.getUUID());
        applicationRecords.setNewUserQuota(quota);
        applicationRecords.setUserQuota(oldQuota);
        if(user!=null){
            applicationRecords.setUserName(user.getName());
        }
        applicationRecords.setModfiyTime(LocalDateTime.now());
        iApplicationRecordsService.addQuotaRecord(applicationRecords);
        return R.ok();
    }

    @ApiOperation(value = "查询配额信息列表")
    @RequestMapping(value = "/v1/queryQuotaList",method = RequestMethod.GET)
    public R getApplicationQuotaDetail() {
        Page<ApplicationManage> page=new Page<>();
        page.setCurrent(1);
        page.setSize(20);
        Page<ApplicationManage> applicationQuota = iApplicationManageService.getApplicationQuota(page);
        return R.ok(applicationQuota);
    }

    @ApiOperation(value = "查询单个配额信息")
    @RequestMapping(value = "/v1/queryQuota",method = RequestMethod.GET)
    public R getApplicationQuota(@RequestParam(value = "areaOrPolice") String areaOrPolice){
        ApplicationManage applicationQuotaDetail = iApplicationManageService.getApplicationQuotaDetail(areaOrPolice);
        return R.ok(applicationQuotaDetail);
    }
}

