package com.upd.hwcloud.controller.backend.application.form.paas;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.contains.ApplicationInfoStatus;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.entity.application.paas.rdb.PaasRdbBase;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.service.application.IApplicationInfoService;
import com.upd.hwcloud.service.application.IPaasRdbBaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 关系型数据库组申请信息 前端控制器
 * </p>
 *
 * @author yyc
 * @since 2020-04-19
 */
@RestController
@RequestMapping("/paasRdbBase")
public class PaasRdbBaseController {

    @Autowired
    private IApplicationInfoService applicationInfoService;

    @Autowired
    private IPaasRdbBaseService paasRdbBaseService;

    @RequestMapping("/{orderNum}")
    public R check(@PathVariable String orderNum){
        ApplicationInfo info = applicationInfoService.getOne(new QueryWrapper<ApplicationInfo>().lambda().eq(ApplicationInfo::getOrderNumber,orderNum).eq(ApplicationInfo::getStatus, ApplicationInfoStatus.USE.getCode()));
        if(info != null){
            PaasRdbBase base = paasRdbBaseService.getOne(new QueryWrapper<PaasRdbBase>().lambda().eq(PaasRdbBase::getAppInfoId,info.getId()));
            return R.ok(base);
        }
        return R.error("该单号不存在或未通过审批");
    }

}

