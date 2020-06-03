package com.upd.hwcloud.controller.trash;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.entity.PaasZcyy;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.utils.UUIDUtil;
import com.upd.hwcloud.service.IPaasZcyyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * PaaS服务支撑应用信息 前端控制器
 * </p>
 *
 * @author zwb
 * @since 2019-06-11
 */
@RestController
@Api(description = "PaaS服务支撑应用")
@RequestMapping("/paasZcyy")
public class PaasZcyyController {
    @Autowired
    IPaasZcyyService paasZcyyService;

    @ApiOperation("新增服务")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public R create(@LoginUser User user,@RequestBody PaasZcyy zcyy) {
       paasZcyyService.save(zcyy);
        return R.ok(zcyy.getId());
    }

    @ApiOperation("删除服务支撑应用")
    @ApiImplicitParam(name="id", value="支撑应用id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R delete(@LoginUser User user, @PathVariable String id) {
       paasZcyyService.removeById(id);
        return R.ok();
    }

    @ApiOperation("修改支撑应用信息")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public R edit(@LoginUser User user,  @RequestBody PaasZcyy zcyy) {
        paasZcyyService.updateById(zcyy);
        return R.ok();
    }

    @ApiOperation("支撑应用详情")
    @ApiImplicitParam(name="id", value="支撑应用id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R detail(@LoginUser User user, @PathVariable String id) {
        return R.ok(paasZcyyService.getById( id));
    }

    @ApiOperation("支撑应用列表")
    @ApiImplicitParam(name="masterid", value="服务id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "list/{masterid}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R list(@LoginUser User user, @PathVariable String masterid) {

        List<PaasZcyy> list = paasZcyyService.list(new QueryWrapper<PaasZcyy>().lambda().eq(PaasZcyy::getMasterId, masterid));
        return R.ok(list);
    }
}

