package com.upd.hwcloud.controller.backend.application.form.paas;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.contains.ApplicationInfoStatus;
import com.upd.hwcloud.bean.contains.FormNum;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.service.application.IApplicationInfoService;
import com.upd.hwcloud.service.application.IPaasDistributedDbInfoService;
import com.upd.hwcloud.service.application.IPaasLibraAccountService;
import com.upd.hwcloud.service.application.IPaasLibraInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yyc
 * @date 2020/6/11
 */
@RestController
@RequestMapping("/libra")
public class PaasLibraController {

    @Autowired
    private IApplicationInfoService applicationInfoService;

    @Autowired
    private IPaasDistributedDbInfoService paasDistributedDbInfoService;

    @Autowired
    private IPaasLibraInfoService libraInfoService;

    @Autowired
    private IPaasLibraAccountService libraAccountService;

    @RequestMapping(value = "/sync",method = RequestMethod.GET)
    public R  sync(){

        List<ApplicationInfo> applicationInfoList = applicationInfoService.list(new QueryWrapper<ApplicationInfo>().lambda().eq(ApplicationInfo::getFormNum,
                FormNum.PAAS_DISTRIBUTED_DB.toString()).ne(ApplicationInfo::getStatus, ApplicationInfoStatus.DELETE.getCode()));

        for(ApplicationInfo info:applicationInfoList){

        }
        return R.ok();
    }
}
