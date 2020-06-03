package com.upd.hwcloud.controller.backend.serviceManagement;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.contains.ApplicationInfoStatus;
import com.upd.hwcloud.bean.contains.ModelName;
import com.upd.hwcloud.bean.entity.*;
import com.upd.hwcloud.bean.entity.application.AppReviewInfo;
import com.upd.hwcloud.bean.entity.wfm.*;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.common.lock.DistributeLock;
import com.upd.hwcloud.common.utils.UUIDUtil;
import com.upd.hwcloud.service.IBaseSubpageService;
import com.upd.hwcloud.service.IIaasSubpageService;
import com.upd.hwcloud.service.IRegisterService;
import com.upd.hwcloud.service.wfm.IActivityService;
import com.upd.hwcloud.service.wfm.IInstanceService;
import com.upd.hwcloud.service.wfm.IWorkflowService;
import com.upd.hwcloud.service.wfm.IWorkflowmodelService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.upd.hwcloud.bean.contains.ApplicationInfoStatus.INNER_REVIEW;
import static com.upd.hwcloud.bean.contains.ApplicationInfoStatus.REVIEW_REJECT;

/**
 * <p>
 * SAAS服务注册表 前端控制器
 * </p>
 *
 * @author zwb
 * @since 2019-05-27
 */

public class BaseSubpageController<S extends IBaseSubpageService<T>,T extends BaseSubpage> {
    @Autowired
    private S iaasSubpageService;

    @ApiOperation("新增")
    @RequestMapping(value = "/create/{iaasId}", method = RequestMethod.POST)
    @ResponseBody
    public R create(@LoginUser User user, @RequestBody T iaas, @PathVariable String iaasId) {
        iaasSubpageService.saveIaasPage(user,iaas,iaasId);
        return R.ok();
    }


    @ApiOperation("修改二级页面配置信息")
    @RequestMapping(value = "/edit/{iaasId}", method = RequestMethod.POST)
    @ResponseBody
    public R edit(@LoginUser User user, @RequestBody T iaas,@PathVariable String iaasId) {
        iaasSubpageService.updateIaasPage(user,iaas,iaasId);
        return R.ok();
    }

    @ApiOperation("服务配置详情")
    @ApiImplicitParam(name="iaasId", value="服务id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/{iaasId}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R detail(@LoginUser User user, @PathVariable String iaasId) {
        T iaas = iaasSubpageService.getDetail(iaasId);
        return R.ok(iaas);
    }
}

