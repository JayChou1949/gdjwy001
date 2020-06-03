package com.upd.hwcloud.controller.backend.application;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.contains.ApplicationInfoStatus;
import com.upd.hwcloud.bean.contains.FormNum;
import com.upd.hwcloud.bean.contains.ResourceType;
import com.upd.hwcloud.bean.contains.ReviewStatus;
import com.upd.hwcloud.bean.dto.HandlerWrapper;
import com.upd.hwcloud.bean.dto.ImplRequest;
import com.upd.hwcloud.bean.dto.UpdateApplicationInfo;
import com.upd.hwcloud.bean.entity.Files;
import com.upd.hwcloud.bean.entity.Paas;
import com.upd.hwcloud.bean.entity.Saas;
import com.upd.hwcloud.bean.entity.SaasRegister;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.ApplicationFeedback;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.entity.application.IaasYzmyzyUser;
import com.upd.hwcloud.bean.entity.application.paas.rdb.PaasRdbBase;
import com.upd.hwcloud.bean.entity.wfm.Workflow;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.common.lock.DistributeLock;
import com.upd.hwcloud.common.utils.UUIDUtil;
import com.upd.hwcloud.service.IFilesService;
import com.upd.hwcloud.service.IPaasService;
import com.upd.hwcloud.service.ISaasRegisterService;
import com.upd.hwcloud.service.ISaasService;
import com.upd.hwcloud.service.application.IApplicationHandler;
import com.upd.hwcloud.service.application.IApplicationInfoService;
import com.upd.hwcloud.service.application.IIaasYzmyzyUserImplService;
import com.upd.hwcloud.service.application.IIaasYzmyzyUserService;
import com.upd.hwcloud.service.application.IImplHandler;
import com.upd.hwcloud.service.wfm.IInstanceService;
import com.upd.hwcloud.service.wfm.IWorkflowService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 申请信息表 前端控制器
 * </p>
 *
 * @author wuc
 * @since 2018-11-30
 */
@Api(description = "服务申请接口")
@RestController
@RequestMapping("/applicationInfo")
public class ApplicationInfoController {
    @Autowired
    private IInstanceService instanceService;
    @Autowired
    private IWorkflowService workflowService;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private DistributeLock lock;
    @Autowired
    private IApplicationInfoService applicationInfoService;
    @Autowired
    private IIaasYzmyzyUserImplService iaasYzmyzyUserImplService;
    @Autowired
    private ISaasService saasService;
    @Autowired
    private IPaasService paasService;
    @Autowired
    private ISaasRegisterService saasRegisterService;

    @Autowired
    private IFilesService filesService;


    @ApiOperation(value = "新建申请,添加到购物车")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public R create(@LoginUser User user, HttpServletRequest request) throws IOException {
        String json = IOUtils.toString(request.getInputStream(), "UTF-8");
        ApplicationInfo origin = JSONObject.parseObject(json, ApplicationInfo.class);
        HandlerWrapper hw = FormNum.getHandlerWrapperByInfo(context, origin);
        ApplicationInfo info = parseApplicationInfo(json, hw.getApplicationType());
        create(user, hw.getFormNum(), info, hw.getHandler(),false);
        return R.ok(info);
    }

    @ApiOperation(value = "草稿")
    @RequestMapping(value = "/draft", method = RequestMethod.POST)
    public R draft(@LoginUser User user, HttpServletRequest request) throws IOException {
        String json = IOUtils.toString(request.getInputStream(), "UTF-8");
        ApplicationInfo origin = JSONObject.parseObject(json, ApplicationInfo.class);
        HandlerWrapper hw = FormNum.getHandlerWrapperByInfo(context, origin);
        ApplicationInfo info = parseApplicationInfo(json, hw.getApplicationType());
        String infoId = info.getId();
        ResourceType resType = hw.getFormNum().getResourceType();
        draft(user, hw.getFormNum(), info, hw.getHandler());
        if (StringUtils.isEmpty(infoId)){

            //不使用前端传的ID,统一后端处理
            //Workflow workflow = workflowService.getById(info.getWorkFlowId());

            Workflow workflow = workflowService.chooseWorkFlow(resType,info.getAreaName(),info.getPoliceCategory(),info.getServiceTypeId());

            info.setWorkFlowId(workflow.getId());
            String workFlowId = info.getWorkFlowId();
            R r= instanceService.launchInstanceOfWorkFlow(user.getIdcard(),workFlowId,info.getId());
        }
        return R.ok(info);
    }

    @ApiOperation(value = "修改申请信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R update(@LoginUser User user, HttpServletRequest request) throws IOException {
        String json = IOUtils.toString(request.getInputStream(), "UTF-8");
        UpdateApplicationInfo origin = JSONObject.parseObject(json, UpdateApplicationInfo.class);
        ApplicationInfo info = origin.getInfo();
        info.setFormNum(applicationInfoService.getById(info.getId()).getFormNum());
        HandlerWrapper hw = FormNum.getHandlerWrapperByInfo(context, info);
        UpdateApplicationInfo updateInfo = parseUpdateApplicationInfo(json, hw.getApplicationType());
        update(user, hw.getFormNum(), updateInfo, hw.getHandler());
        //如果是关系型数据库，还要更新实施信息
        if(info.getFormNum() != null){
            if(FormNum.PAAS_RELATIONAL_DATABASE.toString().equals(info.getFormNum())){
                IImplHandler implHandler = hw.getImplHandler();
                String infoJson = JSON.toJSONString(info);
                List<PaasRdbBase> rdbBasesImplList = JSON.parseArray(JSON.parseObject(infoJson).getString("implServerList"),PaasRdbBase.class);
                if(CollectionUtils.isNotEmpty(rdbBasesImplList)){
                    implHandler.update(info.getId(),rdbBasesImplList);
                }
            }
        }
        return R.ok();
    }

    @ApiOperation(value = "实施申请")
    @RequestMapping(value = "/impl/{id}", method = RequestMethod.POST)
    public R impl(@LoginUser User user, @PathVariable String id, HttpServletRequest request) throws IOException {
        ApplicationInfo info = applicationInfoService.getById(id);
        HandlerWrapper hw = FormNum.getHandlerWrapperByInfo(context, info);
        String json = IOUtils.toString(request.getInputStream(), "UTF-8");
        ImplRequest implRequest = parseImplRequest(json, hw.getImplType());
        impl(user, info, implRequest, hw.getImplHandler());
        return R.ok();
    }

    @ApiOperation(value = "反馈")
    @RequestMapping(value = "/feedback/{id}", method = RequestMethod.POST)
    public R feedback(@LoginUser User user, @PathVariable String id, @RequestBody ApplicationFeedback feedback) {
        String uuid = UUIDUtil.getUUID();
        String lockKey = id.intern();
        try {
            if (lock.lock(lockKey, uuid)) {
                applicationInfoService.feedback(user, id, feedback);
            } else {
                throw new BaseException("反馈失败");
            }
        } finally {
            lock.unlock(lockKey, uuid);
        }
        return R.ok();
    }

    @ApiOperation(value = "申请详情")
    @ApiImplicitParam(name="id", value="申请id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public R detail(@LoginUser User user, @PathVariable String id) {
        ApplicationInfo info = applicationInfoService.getById(id);
        if (info == null) {
            return R.error("该申请不存在");
        }

        HandlerWrapper hw = FormNum.getHandlerWrapperByInfo(context, info);
        IApplicationHandler handler = hw.getHandler();
        IImplHandler implHandler = hw.getImplHandler();
        ApplicationInfo detail = applicationInfoService.getDetail(user, id, handler, implHandler);
        return R.ok(detail);
    }

    @ApiOperation(value = "购物车删除")
    @ApiImplicitParam(name="id", value="申请id,多个使用逗号分隔", required = true, paramType="query", dataType="String")
    @RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
    public R delete(String id) {
        if (StringUtils.isEmpty(id)) {
            return R.error("请传入id");
        }
        List<String> idList = Arrays.asList(id.split(","));
        if (!idList.isEmpty()) {
            applicationInfoService.update(new ApplicationInfo(), new UpdateWrapper<ApplicationInfo>().lambda()
                    .set(ApplicationInfo::getStatus, ApplicationInfoStatus.SHOPPING_CART_DEL.getCode())
                    .eq(ApplicationInfo::getStatus, ApplicationInfoStatus.SHOPPING_CART.getCode())
                    .in(ApplicationInfo::getId, idList));
        }
        return R.ok();
    }

    @ApiOperation(value = "后台管理页面删除")
    @ApiImplicitParam(name="id", value="申请id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public R deleteById(@LoginUser User user, @PathVariable String id) {
        String uuid = UUIDUtil.getUUID();
        String lockKey = id.intern();
        try {
            if (lock.lock(lockKey, uuid)) {
                applicationInfoService.deleteById(user, id);
            } else {
                return R.error();
            }
        } catch (Exception e) {
            return R.error();
        } finally {
            lock.unlock(lockKey, uuid);
        }
        return R.ok();
    }

    /**
     * 后台管理页面查询
     * @param resourceType {@link ResourceType}
     */
    @ApiOperation("后台管理页面分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="resourceType", value="资源类型 1:IAAS 2:DAAS 3:PAAS 4:SAAS", defaultValue = "1", paramType="query", dataType="String"),
            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", paramType="query", dataType="String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="query", dataType="String"),
            @ApiImplicitParam(name="status", value="状态, 1:待审核 2:待实施 3:使用中 4:已删除 5:审核驳回 6:实施驳回", defaultValue = "", paramType="query", dataType="String"),
            @ApiImplicitParam(name="userName", value="申请人", paramType="query", dataType="String"),
            @ApiImplicitParam(name="processType", value="处理人,0:全部 1:我 2:其它人", defaultValue = "0", paramType="query", dataType="String")
    })
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public R page(@LoginUser User user,
                  @RequestParam(required = false, defaultValue = "1") Long resourceType,
                  @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                  @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                  @RequestParam(required = false) String userName,
                  @RequestParam(required = false, defaultValue = "") String status,
                  @RequestParam(required = false, defaultValue = "0") String processType) {
        if (ApplicationInfoStatus.SHOPPING_CART.getCode().equals(status)
                || ApplicationInfoStatus.SHOPPING_CART_DEL.getCode().equals(status)) {
            status = null; // 不能查购物车的数据
        }
        if (!ApplicationInfoStatus.REVIEW.getCode().equals(status)
                && !ApplicationInfoStatus.IMPL.getCode().equals(status)) {
            processType = null; // 不是待审核/待实施状态,不能过滤处理人
        }

        IPage<ApplicationInfo> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        page = applicationInfoService.getPage(user, page, userName, status, resourceType, processType);
        return R.ok(page);
    }

    @ApiOperation(value = "购物车列表")
    @RequestMapping(value = "/shoppingCart", method = RequestMethod.GET)
    public R shoppingCart(@LoginUser User user) {
        List<ApplicationInfo> list = applicationInfoService.shoppingCart(user);
        for (ApplicationInfo info : list) {
            HandlerWrapper hw = FormNum.getHandlerWrapperByInfo(context, info);
            IApplicationHandler handler = hw.getHandler();
            if (handler != null) {
                info.setTotalNum(handler.getTotalNum(info.getId()));
                info.setServerList(handler.getByAppInfoId(info.getId()));
            } else {
                info.setTotalNum(1);
            }
        }
        return R.ok(list);
    }

    @ApiOperation(value = "购物车数量")
    @RequestMapping(value = "/shoppingCartCount", method = RequestMethod.GET)
    public R shoppingCartCount(@LoginUser User user) {
        int count = applicationInfoService.shoppingCartCount(user);
        return R.ok(count);
    }

    @ApiOperation(value = "购物车提交")
    @ApiImplicitParams({
            @ApiImplicitParam(name="type", value="审核类型,inner:部门内审核 kx:科信审核", required = true, defaultValue = "kx", paramType="form", dataType="String"),
            @ApiImplicitParam(name="ids", value="申请单id,多个使用逗号分隔", required = true, paramType="form", dataType="String"),
            @ApiImplicitParam(name="userIds", value="审核人id,多个使用逗号分隔", paramType="form", dataType="String")
    })
    @RequestMapping(value = "/shoppingCartSubmit", method = RequestMethod.POST)
    public R shoppingCartSubmit(@LoginUser User user, @RequestParam("ids") String ids,
                                @RequestParam(value = "type", defaultValue = "kx") String type,
                                @RequestParam(value = "userIds", required = false) String userIds) {
        List<String> idList = null;
        if (StringUtils.isEmpty(ids)) {
            return R.error("请选择要提交的数据!");
        } else {
            idList = Arrays.asList(ids.split(","));
            if (idList.isEmpty()) {
                return R.error("请选择要提交的数据!");
            }
        }

        List<String> userIdList = null;
        if ("inner".equals(type)) {
            if (StringUtils.isEmpty(userIds)) {
                return R.error("请选择审核人!");
            } else {
                userIdList = Arrays.asList(userIds.split(","));
                if (userIdList.isEmpty()) {
                    return R.error("请选择审核人!");
                }
            }
        }
        Map<String, Object> param = new HashMap<>();
        param.put("ids", idList);
        param.put("type", type);
        param.put("userIds", userIdList);
        applicationInfoService.shoppingCartSubmit(user, param);
        return R.ok();
    }

    @ApiOperation(value = "审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="申请id", required = true, paramType="path", dataType="String"),
            @ApiImplicitParam(name="type", value="审核类型,inner:部门内审核 kx:科信审核 add:加办", required = true, defaultValue = "kx", paramType="form", dataType="String"),
            @ApiImplicitParam(name="result", value="审核结果 1:通过,其它:驳回", required = true, paramType="form", dataType="String"),
            @ApiImplicitParam(name="userIds[]", value="审核人id", paramType="form", dataType="String"),
            @ApiImplicitParam(name="remark", value="审核描述", paramType="form", dataType="String")
    })
    @RequestMapping(value = "/review/{id}", method = RequestMethod.POST)
    public R review(@LoginUser User user, @PathVariable String id, String result,
                    @RequestParam(required = false) String remark,
                    @RequestParam(value = "type", defaultValue = "kx") String type,
                    @RequestParam(value = "userIds[]", required = false) List<String> userIds) throws Exception {
        if (!"inner".equals(type) && !"kx".equals(type) && !"add".equals(type)) {
            return R.error("参数{type}错误!");
        }
        // 部门内审核,加办必须要选择审核人
        if (("inner".equals(type) || "add".equals(type)) && (userIds == null || userIds.isEmpty())) {
            return R.error("请选择审核人!");
        }
        if (result == null) {
            return R.error("请选择审核结果!");
        }
        // 只有审核通过才能加办
        if ("add".equals(type) && !"1".equals(result)) {
            return R.error("审核驳回不能加办!");
        }
        if (!"1".equals(result)) {
            result = "0";
        }
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        param.put("result", result);
        param.put("remark", remark);
        param.put("type", type);
        param.put("userIds", userIds);

        String uuid = UUIDUtil.getUUID();
        String lockKey = id.intern();
        try {
            if (lock.lock(lockKey, uuid)) {
                applicationInfoService.review(user, param);
            } else {
                return R.error();
            }
        } finally {
            lock.unlock(lockKey, uuid);
        }
        return R.ok();
    }

    @ApiOperation(value = "审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="申请id", required = true, paramType="path", dataType="String"),
            @ApiImplicitParam(name="type", value="审核类型,inner:部门内审核 kx:科信审核 add:加办", required = true, defaultValue = "kx", paramType="form", dataType="String"),
            @ApiImplicitParam(name="result", value="审核结果 1:通过,其它:驳回", required = true, paramType="form", dataType="String"),
            @ApiImplicitParam(name="userIds", value="审核人id,多个使用逗号分隔", paramType="form", dataType="String"),
            @ApiImplicitParam(name="remark", value="审核描述", paramType="form", dataType="String")
    })
    @RequestMapping(value = "/v2/review/{id}", method = RequestMethod.POST)
    public R reviewV2(@LoginUser User user, @PathVariable String id, String result,
                    @RequestParam(required = false) String remark,
                    @RequestParam(value = "type", defaultValue = "kx") String type,
                    @RequestParam(value = "userIds", required = false) String userIds) throws Exception {
        if (!"inner".equals(type) && !"kx".equals(type) && !"add".equals(type)) {
            return R.error("参数{type}错误!");
        }
        // 部门内审核,加办必须要选择审核人
        List<String> userIdList = null;
        if ("inner".equals(type) || "add".equals(type)) {
            if (StringUtils.isEmpty(userIds)) {
                return R.error("请选择审核人!");
            } else {
                userIdList = Arrays.asList(userIds.split(","));
                if (userIdList.isEmpty()) {
                    return R.error("请选择审核人!");
                }
            }
        }
        if (result == null) {
            return R.error("请选择审核结果!");
        }
        // 只有审核通过才能加办
        if ("add".equals(type) && !"1".equals(result)) {
            return R.error("审核驳回不能加办!");
        }
        if (!"1".equals(result)) {
            result = "0";
        }
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        param.put("result", result);
        param.put("remark", remark);
        param.put("type", type);
        param.put("userIds", userIdList);

        String uuid = UUIDUtil.getUUID();
        String lockKey = id.intern();
        try {
            if (lock.lock(lockKey, uuid)) {
                applicationInfoService.review(user, param);
            } else {
                return R.error();
            }
        } finally {
            lock.unlock(lockKey, uuid);
        }
        return R.ok();
    }

    @ApiOperation(value = "转发")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="申请id", required = true, paramType="path", dataType="String"),
            @ApiImplicitParam(name="userIds[]", value="转发审核人id", required = true, paramType="form", dataType="String")
    })
    @RequestMapping(value = "/forward/{id}", method = RequestMethod.POST)
    public R review(@LoginUser User user, @PathVariable String id,
                    @RequestParam(value = "userIds[]", required = true) List<String> userIds) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        param.put("userId", userIds.get(0));

        String uuid = UUIDUtil.getUUID();
        String lockKey = id.intern();
        try {
            if (lock.lock(lockKey, uuid)) {
                applicationInfoService.forward(user, param);
            } else {
                return R.error();
            }
        } finally {
            lock.unlock(lockKey, uuid);
        }
        return R.ok();
    }

    @ApiOperation(value = "转发")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="申请id", required = true, paramType="path", dataType="String"),
            @ApiImplicitParam(name="userIds", value="转发审核人id,多个使用逗号分隔", required = true, paramType="form", dataType="String")
    })
    @RequestMapping(value = "/v2/forward/{id}", method = RequestMethod.POST)
    public R reviewV2(@LoginUser User user, @PathVariable String id,
                    @RequestParam(value = "userIds", required = true) String userIds) {
        List<String> userIdList = Arrays.asList(userIds.split(","));
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        param.put("userId", userIdList.get(0));

        String uuid = UUIDUtil.getUUID();
        String lockKey = id.intern();
        try {
            if (lock.lock(lockKey, uuid)) {
                applicationInfoService.forward(user, param);
            } else {
                return R.error();
            }
        } finally {
            lock.unlock(lockKey, uuid);
        }
        return R.ok();
    }

    @ApiOperation(value = "待审核数量")
    @RequestMapping(value = "/todo", method = RequestMethod.GET)
    public R todo(@LoginUser User user) {
        Map<String, Integer> todo = applicationInfoService.newTodo(user);
        return R.ok(todo);
    }

    @ApiOperation(value = "应用注册列表")
    @RequestMapping(value = "/v2/queryAppName")
    public R queryAppNameV2(){
        List<SaasRegister> usedList = saasRegisterService.list(new QueryWrapper<SaasRegister>().lambda().eq(SaasRegister::getStatus,ApplicationInfoStatus.USE.getCode()));
        for(SaasRegister saasRegister : usedList){
            List<Files> files = filesService.list(new QueryWrapper<Files>().lambda().eq(Files::getRefId,saasRegister.getId()));
            saasRegister.setFileList(files);
        }
        return R.ok(usedList);
    }


    @ApiOperation(value = "通过关键字查询应用名")
    @RequestMapping(value = "/queryAppName", method = RequestMethod.GET)
    public R queryAppName() {
        // 查询SAAS使用中的应用
        List<Saas> saasList = saasService.list(new QueryWrapper<Saas>().lambda()
                .eq(Saas::getStatus, ReviewStatus.ONLINE.getCode()).eq(Saas::getServiceFlag,0));
        List<Paas> paasList = paasService.list(new QueryWrapper<Paas>().lambda()
                .eq(Paas::getStatus, ReviewStatus.ONLINE.getCode()));
        // 查询已经审批通过的申请
        Map<String, ApplicationInfo> usedMap = Maps.newHashMap();
        List<ApplicationInfo> used = applicationInfoService
                .findByAppName(ApplicationInfoStatus.USE.getCode());
        used.forEach(info -> usedMap.put(info.getAppName(), info));
        // 只保留saas应用,如果saas应用包含申请信息,带出申请信息
        List<ApplicationInfo> result = Lists.newArrayList();
        Map<String,SaasRegister> saasRgisterMap = usedSaasRegisterInfo();
        saasList.forEach(saas -> {
            ApplicationInfo info = usedMap.get(saas.getName());
            if (info == null) {
                SaasRegister registerInfo = saasRgisterMap.get(saas.getName());
                if(registerInfo == null){
                    info = new ApplicationInfo();
                }else {
                    info = new ApplicationInfo();
                    BeanUtils.copyProperties(registerInfo,info);
                }
                info.setAppName(saas.getName());
                info.setEntityType("saas");
            }
            result.add(info);
        });
        paasList.forEach(paas -> {
            ApplicationInfo info = usedMap.get(paas.getName());
            if (info == null) {
                info = new ApplicationInfo();
                info.setAppName(paas.getName());
                info.setEntityType("saas");
            }
            result.add(info);
        });
        return R.ok(result);
    }

    /**
     * 获取成果注册的应用信息
     * @return usedMap
     */
    private Map<String,SaasRegister> usedSaasRegisterInfo(){
        List<SaasRegister> usedList = saasRegisterService.list(new QueryWrapper<SaasRegister>().lambda().eq(SaasRegister::getStatus,ApplicationInfoStatus.USE.getCode()));
        Map<String,SaasRegister> usedMap = Maps.newHashMap();
        usedList.forEach(info ->{
            usedMap.put(info.getName(),info);
        });
        return usedMap;
    }


    private void create(User user, FormNum formNum,
                            ApplicationInfo info, IApplicationHandler handler,boolean special) {
        info.setResourceType(formNum.getResourceType().getCode());
        info.setFormNum(formNum.name());
        info.setDraft("0"); // 不是草稿
        applicationInfoService.save(user, info, handler,special);
    }

    private void draft(User user, FormNum formNum,
                        ApplicationInfo info, IApplicationHandler handler) {
        info.setResourceType(formNum.getResourceType().getCode());
        info.setFormNum(formNum.name());
        info.setDraft("1"); // 是草稿
        info.setFlowNew("1");
        applicationInfoService.draft(user, info, handler);
    }

    private void update(User user, FormNum formNum,
                            UpdateApplicationInfo updateInfo, IApplicationHandler handler) {
        ApplicationInfo info = updateInfo.getInfo();
        if (info == null || StringUtils.isEmpty(info.getId())) {
            throw new BaseException("操作失败,参数错误!");
        }
        String uuid = UUIDUtil.getUUID();
        String lockKey = info.getId().intern();
        try {
            if (lock.lock(lockKey, uuid)) {
                info.setResourceType(formNum.getResourceType().getCode());
                info.setFormNum(formNum.name());
                info.setDraft("0"); // 不是草稿
                info.setFlowNew("1");
                applicationInfoService.update(user, updateInfo, handler);
            } else {
                throw new BaseException("更新失败");
            }
        } finally {
            lock.unlock(lockKey, uuid);
        }
    }

    private void impl(User user, ApplicationInfo info, ImplRequest implRequest, IImplHandler implHandler) {
        if (implRequest.getResult() == null) {
            throw new BaseException("请选择实施结果");
        }
        if (!"1".equals(implRequest.getResult())) {
            implRequest.setResult("0");
        }
        Map<String, Object> param = new HashMap<>();
        param.put("info", info);
        param.put("implRequest", implRequest);

        String uuid = UUIDUtil.getUUID();
        String lockKey = info.getId().intern();
        try {
            if (lock.lock(lockKey, uuid)) {
                applicationInfoService.impl(user, param, implHandler);
                if ("1".equals(implRequest.getResult())&& implHandler instanceof IIaasYzmyzyUserService) {
                    ImplRequest<IaasYzmyzyUser> implreq = implRequest;
                     List<IaasYzmyzyUser> impls = implreq.getImplServerList();
                    iaasYzmyzyUserImplService.saveUserList(impls,info.getAppName());
                }
            } else {
                throw new BaseException("实施失败");
            }
        } finally {
            lock.unlock(lockKey, uuid);
        }
    }

    private static <S> ApplicationInfo<S, Object> parseApplicationInfo(String json, Class type) {
        return JSON.parseObject(json,
                new TypeReference<ApplicationInfo<S, Object>>(type) {});
    }

    private static <S> UpdateApplicationInfo<S> parseUpdateApplicationInfo(String json, Class type) {
        return JSON.parseObject(json,
                new TypeReference<UpdateApplicationInfo<S>>(type) {});
    }

    private static <S> ImplRequest<S> parseImplRequest(String json, Class type) {
        return JSON.parseObject(json,
                new TypeReference<ImplRequest<S>>(type) {});
    }

}

