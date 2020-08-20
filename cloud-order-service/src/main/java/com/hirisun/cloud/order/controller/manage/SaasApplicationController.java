package com.hirisun.cloud.order.controller.manage;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * SaaS资源申请原始信息表 前端控制器
 * </p>
 *
 * @author wuc
 * @since 2019-07-24
 */
@Api(description = "saas资源申请(原始信息)")
@RestController
@RequestMapping("/saasOrigin")
public class SaasApplicationController {
//
//    @Autowired
//    private ISaasApplicationService saasApplicationService;
//    @Autowired
//    private IUserService userService;
//
//
//    @ApiOperation(value = "查询用户对应的二级管理员")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name="applyType", value="申请类型 按地区 0；按警种 1", paramType="path", dataType="String"),
//            @ApiImplicitParam(name="name", value="类型名称", paramType="path", dataType="String")
//    })
//    @RequestMapping(value = "/lv2Manager/{area}/{name}", method = RequestMethod.GET)
//    public R getLv2Manager(@PathVariable String area, @PathVariable String name) {
//        if (StringUtils.isEmpty(name)) {
//            return R.error("参数错误");
//        }
//        User lv2Manager;
//        //如果地区选择省厅，则提交给省厅下的各个警种所对应的的二级管理员
//        if ("省厅".equals(area)) {
//            lv2Manager = userService.getOne(new QueryWrapper<User>().lambda()
//                    .eq(User::getType, UserType.TENANT_MANAGER.getCode())
//                    .eq(User::getDefaultTenant, "1")//是否第一租户管理员 1 是 0 否
//                    .eq(User::getTenantPoliceCategory, name));
//        } else {//如果地区选择其他地区，则提交给其他地区下的各个警种所对应的的二级管理员
//            lv2Manager = userService.getOne(new QueryWrapper<User>().lambda()
//                    .eq(User::getType, UserType.TENANT_MANAGER.getCode())
//                    .eq(User::getDefaultTenant, "1") //是否第一租户管理员 1 是 0 否
//                    .eq(User::getTenantArea, area));
//        }
//        return R.ok(lv2Manager);
//    }
//
//    @ApiOperation(value = "新建原始申请单")
//    @RequestMapping(value = "/create", method = RequestMethod.POST)
//    public R create(@LoginUser User user, @RequestBody SaasApplication info) {
//        saasApplicationService.create(user, info);
//        return R.ok();
//    }
//
//    @ApiOperation(value = "修改原始申请单")
//    @RequestMapping(value = "/update", method = RequestMethod.POST)
//    public R update(@LoginUser User user, @RequestBody SaasApplication info) {
//        saasApplicationService.update(user, info);
//        return R.ok();
//    }
//
//    @ApiOperation(value = "驳回后提交")
//    @ApiImplicitParam(name="id", value="申请id", required = true, paramType="path", dataType="String")
//    @RequestMapping(value = "/submit/{id}", method = RequestMethod.GET)
//    public R submit(@LoginUser User user, @PathVariable String id) {
//        saasApplicationService.submit(user, id);
//        return R.ok();
//    }
//
//    @ApiOperation(value = "删除原始申请单")
//    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
//    public R create(@LoginUser User user, @PathVariable String id) {
//        saasApplicationService.removeById(id);
//        return R.ok();
//    }
//
//    @ApiOperation("我的申请单(原始单据)")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", paramType="query", dataType="String"),
//            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="query", dataType="String"),
//            @ApiImplicitParam(name="status", value="状态, 1:待审核 2:待实施 3:使用中 4:已删除 5:审核驳回 6:实施驳回", defaultValue = "", paramType="query", dataType="String"),
//            @ApiImplicitParam(name="userName", value="申请人", paramType="query", dataType="String"),
//            @ApiImplicitParam(name="orderNumber", value="申请单号", paramType="query", dataType="String"),
//            @ApiImplicitParam(name = "serviceName",value = "申请服务",paramType = "query",dataType = "String")
//    })
//    @RequestMapping(value = "/page", method = RequestMethod.GET)
//    public R page(@LoginUser User user,
//                  @RequestParam(required = false, defaultValue = "1") Integer pageNum,
//                  @RequestParam(required = false, defaultValue = "20") Integer pageSize,
//                  @RequestParam(required = false) String userName,
//                  @RequestParam(required = false) String serviceName,
//                  @RequestParam(required = false) String orderNumber,
//                  @RequestParam(required = false) String startDate,
//                  @RequestParam(required = false) String endDate,
//                  @RequestParam(required = false, defaultValue = "") String status) {
//
//        IPage<SaasApplication> page = new Page<>();
//        page.setCurrent(pageNum);
//        page.setSize(pageSize);
//
//        Map<String, Object> param = new HashMap<>();
//        param.put("user", user);
//        param.put("userName", CommonHandler.dealNameforQuery(userName));
//        param.put("orderNumber",CommonHandler.dealNameforQuery(orderNumber));
//        param.put("serviceName",CommonHandler.dealNameforQuery(serviceName));
//        param.put("status", status);
//        param.put("startDate", startDate);
//        param.put("endDate", endDate);
//        page = saasApplicationService.getPage(user, page, param);
//        return R.ok(page);
//    }
//
//    @ApiOperation(value = "申请详情(原始单据)")
//    @ApiImplicitParam(name="id", value="申请id", required = true, paramType="path", dataType="String")
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public R detail(@LoginUser User user, @PathVariable String id) {
//        Map<String, Object> map = saasApplicationService.getDetail(user, id);
//        return R.ok(map);
//    }
//
//    @ApiImplicitParams({
//            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", paramType="query", dataType="String"),
//            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="query", dataType="String"),
//            @ApiImplicitParam(name="userName", value="关键字", paramType="query", dataType="String")
//    })
//    @ApiOperation("待合并列表")
//    @RequestMapping(value = "/mergePage", method = RequestMethod.GET)
//    public R mergePage(@LoginUser User user,
//                       @RequestParam(required = false, defaultValue = "1") Integer pageNum,
//                       @RequestParam(required = false, defaultValue = "20") Integer pageSize,
//                       @RequestParam(required = false) String userName) {
//        IPage<SaasApplication> page = new Page<>();
//        page.setCurrent(pageNum);
//        page.setSize(pageSize);
//        page = saasApplicationService.mergePage(user, page, userName);
//        return R.ok(page);
//    }
//
//    @ApiOperation(value = "待合并列表驳回")
//    @ApiImplicitParam(name="id", value="申请id", required = true, paramType="path", dataType="String")
//    @RequestMapping(value = "/reject/{id}", method = RequestMethod.GET)
//    public R reject(@LoginUser User user, @PathVariable String id) {
//        saasApplicationService.reject(user, id);
//        return R.ok();
//    }

}

