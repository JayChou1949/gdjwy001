package com.upd.hwcloud.controller.backend.application.form.daas;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.DaasApplication;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.service.application.IDaasApigService;
import com.upd.hwcloud.service.application.IDaasApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zwb on 2019/4/23.
 */
@Api(description = "DAAS服务申请")
@RestController
@RequestMapping("/daasApplication")
public class DaasApplicationController {
    @Autowired
    private IDaasApplicationService daasApplicationService;
    @Autowired
    private IDaasApigService daasApigService;

    @ApiOperation("删除")
    @RequestMapping("/delete/{id}")
    public R delete(@LoginUser User user, @PathVariable String id) throws Exception {
        daasApplicationService.removeById(id);
        return R.ok();
    }

    @ApiOperation("批量删除服务")
    @ApiImplicitParam(name="ids", value="服务id拼接字符串: 1,2,3", required = true, dataType="String")
    @RequestMapping(value = "/deleteBatch", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R deleteBatch(@LoginUser User user, String ids) {
        if (StringUtils.isEmpty(ids)) return R.error("未选择服务");
        daasApplicationService.remove(new QueryWrapper<DaasApplication>().in("id",ids.split(",")));
        return R.ok();
    }

    @ApiOperation("订购")
    @RequestMapping("/order/{id}")
    public R order(@LoginUser User user, @PathVariable String id) throws Exception {
        daasApigService.orderService(id);
        DaasApplication daasApplication = daasApplicationService.getById(id);
        return R.ok(daasApplication);
    }

}
