package com.upd.hwcloud.controller.backend.application;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.word.WordExportUtil;
import cn.afterturn.easypoi.word.entity.params.ExcelListEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.contains.ApplicationInfoStatus;
import com.upd.hwcloud.bean.contains.BusinessName;
import com.upd.hwcloud.bean.contains.ModelName;
import com.upd.hwcloud.bean.contains.UserType;
import com.upd.hwcloud.bean.dto.*;
import com.upd.hwcloud.bean.entity.*;
import com.upd.hwcloud.bean.entity.application.AppReviewInfo;
import com.upd.hwcloud.bean.entity.wfm.*;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.common.lock.DistributeLock;
import com.upd.hwcloud.common.utils.AreaPoliceCategoryUtils;
import com.upd.hwcloud.common.utils.ExcelUtil;
import com.upd.hwcloud.common.utils.UUIDUtil;
import com.upd.hwcloud.common.utils.WordUtil;
import com.upd.hwcloud.service.ISaasApplicationMergeService;
import com.upd.hwcloud.service.ISaasApplicationService;
import com.upd.hwcloud.service.ISaasRecoverApplicationService;
import com.upd.hwcloud.service.ISaasRecoverInfoService;
import com.upd.hwcloud.service.wfm.IActivityService;
import com.upd.hwcloud.service.wfm.IInstanceService;
import com.upd.hwcloud.service.wfm.IWorkflowService;
import com.upd.hwcloud.service.wfm.IWorkflowmodelService;
import com.upd.hwcloud.service.workbench.impl.CommonHandler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;


@Api(description = "saas资源申请(合并后流程)")
@RestController
@RequestMapping("/saasflow")
public class SaasWorkFlowController {

    private static final Logger logger = LoggerFactory.getLogger(SaasWorkFlowController.class);

    @Autowired
    private DistributeLock lock;
    @Autowired
    private IActivityService activityService;
    @Autowired
    private IInstanceService instanceService;
    @Autowired
    private IWorkflowmodelService workflowmodelService;
    @Autowired
    private IWorkflowService workflowService;
    @Autowired
    private ISaasApplicationMergeService saasApplicationMergeService;
    @Autowired
    private ISaasApplicationService saasApplicationService;
    @Autowired
    private ISaasRecoverApplicationService saasRecoverApplicationService;
    @Autowired
    private ISaasRecoverInfoService saasRecoverInfoService;
    @Value("C://import.xlsx")
    private String filePath;

    @ApiOperation("创建流程")
    @RequestMapping(value = "/createflow", method = RequestMethod.GET)
    public R createflow() {
        Workflow workflow = workflowService.getOne(new QueryWrapper<Workflow>().eq("DEFAULT_PROCESS", "SAAS"));
        if (null!=workflow) {
            return R.error("资源申请流程已经存在");
        }
        Workflow flow = new Workflow();
        flow.setFlowStatus(1);
        flow.setId(UUIDUtil.getUUID());
        flow.setDefaultProcess("SAAS");
        flow.setWorkflowname("SAAS资源申请流程");
        flow.insert();

        String flowId = flow.getId();
        List<Workflowmodel> models = new ArrayList<>();
        // 业务办理
        Workflowmodel carry =  new Workflowmodel(flowId, ModelName.CARRY.getName(),null);
        models.add(carry);
        // 大数据办审批
        Workflowmodel bigData =  new Workflowmodel(flowId, ModelName.BIG_DATA.getName(), carry.getId());
        models.add(bigData);
        // 一级管理员审批
        Workflowmodel lvl1Manager =  new Workflowmodel(flowId, ModelName.LVL1_MANAGER.getName(), bigData.getId());
        models.add(lvl1Manager);
        // 服务台复核
        Workflowmodel recheck =  new Workflowmodel(flowId, ModelName.RECHECK.getName(), lvl1Manager.getId());
        models.add(recheck);
        // 二级管理员审批
        Workflowmodel lvl2Manager =  new Workflowmodel(flowId, ModelName.LVL2_MANAGER.getName(), recheck.getId());
        models.add(lvl2Manager);
        // 申请
        Workflowmodel apply =  new Workflowmodel(flowId, ModelName.APPLY.getName(), lvl2Manager.getId());
        apply.setIsstart(0);
        models.add(apply);
        workflowmodelService.saveBatch(models);
        return R.ok();
    }

    @ApiOperation("合并")
    @ApiImplicitParam(name="ids", value="要和并的单子id,多个使用,分隔", paramType="form", dataType="String")
    @RequestMapping(value = "/merge", method = RequestMethod.POST)
    public R merge(@LoginUser User user, String ids) {
        if (StringUtils.isEmpty(ids)) {
            return R.error("请传入ids参数");
        }
        String uuid = UUIDUtil.getUUID();
        String lockKey = ids.intern();
        try {
            if (lock.lock(lockKey, uuid)) {
                SaasApplicationMerge merge = saasApplicationMergeService.merge(user, ids);
                return R.ok(merge);
            } else {
                return R.error();
            }
        } catch (Exception e) {
            return R.error();
        } finally {
            lock.unlock(lockKey, uuid);
        }
    }

    @ApiOperation(value = "撤销合并")
    @ApiImplicitParam(name="id", value="申请id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/merge/undo/{id}", method = RequestMethod.GET)
    public R deleteById(@LoginUser User user, @PathVariable String id) {
        String uuid = UUIDUtil.getUUID();
        String lockKey = id.intern();
        try {
            if (lock.lock(lockKey, uuid)) {
                saasApplicationMergeService.deleteById(user, id);
                activityService.terminationInstanceOfWorkFlow(id);
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

    @ApiOperation(value = "修改申请信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R update(@LoginUser User user, @RequestBody SaasApplicationMerge info) throws IOException {
        saasApplicationMergeService.update(user,info);
        return R.ok();
    }

    @ApiOperation(value = "申请详情")
    @ApiImplicitParam(name="id", value="申请id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public R viewDetailsApply(@LoginUser User user,@PathVariable String id) {
        SaasApplicationMerge detail = saasApplicationMergeService.getDetails(id);
        if (detail == null) {
            return R.error("该申请不存在");
        }
        Instance instance = instanceService.getInstanceByBusinessId(id);
        Activity activity = null;
        if (null!=instance){
            detail.setWorkFlowId(instance.getWorkflowid());
            List<Activity> activitys = activityService.list(new QueryWrapper<Activity>().lambda()
                    .eq(Activity::getInstanceid,instance.getId()).eq(Activity::getHandlepersonids,user.getIdcard()).eq(Activity::getActivitystatus,"待办").isNull(Activity::getActivitytype));
            if (null==activitys||activitys.size()==0){
                activitys = activityService.list(new QueryWrapper<Activity>().lambda()
                        .eq(Activity::getInstanceid,instance.getId()).eq(Activity::getHandlepersonids,user.getIdcard()).eq(Activity::getActivitystatus,"待办").eq(Activity::getActivitytype,"adviser"));
            }
            if (null!=activitys&&activitys.size()>0) activity = activitys.get(0);
        }
        String activityId = "";
        if (activity!=null) {
            activityId = activity.getId();
            if ("adviser".equals(activity.getActivitytype())){
                detail.setCanAdviser(true);
                detail.setCanReview(true);
            }else{
                Workflowmodel workflowmodel=workflowmodelService.getById(activity.getModelid());
                if (null==workflowmodel){
                    return R.error("未找到流程环节");
                }
                String  modelName=workflowmodel.getModelname();
                if (modelName.equals(ModelName.APPLY.getName())||modelName.equals(ModelName.LVL2_MANAGER.getName())) {
                    detail.setCanEdit(true);
                }
                if (modelName.equals(ModelName.LVL2_MANAGER.getName())
                        ||modelName.equals(ModelName.RECHECK.getName())
                        ||modelName.equals(ModelName.LVL1_MANAGER.getName())
                        ||modelName.equals(ModelName.BIG_DATA.getName())) {
                    detail.setCanTrans(true);
                    detail.setCanReview(true);
                    if (!modelName.equals(ModelName.LVL2_MANAGER.getName())) {
                        detail.setCanAdd(true);
                    }
                }
                if (modelName.equals(ModelName.CARRY.getName())) {
                    detail.setCanTrans(true);
                    detail.setCanImpl(true);
                }
            }

        }
        List<AppReviewInfo> allReviewInfo = detail.getReviewList();
        Map bean = new HashMap<>();
        if (allReviewInfo!=null){
            Map<String, List<AppReviewInfo>> reviews = allReviewInfo.stream().collect(Collectors.groupingBy(a->a.getFlowStepId()+""));
            bean.put("review",reviews);
        }

        WorkFlowModelVo modelVo=workflowmodelService.getWorkFlowDefinition(id);
        if (modelVo != null) {
            bean.put("model",modelVo);
            bean.put("bizData",detail);
            bean.put("activityId",activityId);
            return R.ok(bean);
        }else {
            return R.error("未找到流程");
        }
    }

    @ApiOperation(value = "转发")
    @ApiImplicitParams({
            @ApiImplicitParam(name="activityId", value="当前环节id", required = true, paramType="path", dataType="String"),
            @ApiImplicitParam(name="userIds", value="转发审核人id,多个使用逗号分隔", required = true, paramType="form", dataType="String")
    })
    @RequestMapping(value = "/forward/{activityId}", method = RequestMethod.POST)
    public R forward(@LoginUser User user, @PathVariable String activityId,
                     @RequestParam(value = "userIds", required = true) String userIds) {
        String uuid = UUIDUtil.getUUID();
        String lockKey = activityId.intern();
        try {
            if (lock.lock(lockKey, uuid)) {
                R r =  activityService.forward(activityId, userIds);
                return r;
            } else {
                return R.error();
            }
        } finally {
            lock.unlock(lockKey, uuid);
        }
    }

    @ApiOperation(value = "參與人意見")
    @ApiImplicitParams({
            @ApiImplicitParam(name="activityId", value="当前环节id", required = true, paramType="path", dataType="String"),
    })
    @RequestMapping(value = "/adviser", method = RequestMethod.POST)
    public R adviser(@LoginUser User user,
                     @RequestBody FallBackVO vo) {

        AppReviewInfo approve = vo.getApprove();
        approve.setCreator(user.getIdcard());
        activityService.adviseActivity(vo.getCurrentActivityId(), approve);
        return R.ok();
    }


    @ApiOperation(value = "审批")
    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    public R approve(@LoginUser User user,
                     @RequestBody FallBackVO vo) {
        AppReviewInfo approve = vo.getApprove();
        approve.setCreator(user.getIdcard());
        SaasApplicationMerge info = saasApplicationMergeService.getById(vo.getApprove().getAppInfoId());
        Activity activity =activityService.getById(vo.getCurrentActivityId());
        if ("adviser".equals(activity.getActivitytype())){
            activityService.adviseActivity(vo.getCurrentActivityId(), approve);
            return R.ok();
        }
        Map<String,String> map = new HashMap<>();
        map.put("name", BusinessName.SAAS_RESOURCE);
        map.put("order", info.getOrderNumber());
        if ("1".equals(approve.getResult())){
            R r = activityService.advanceCurrentActivity(vo.getCurrentActivityId(), approve, map);
            Object nextModelName = r.get("data");
            if (ModelName.CARRY.getName().equals(nextModelName)){
                info.setStatus(ApplicationInfoStatus.IMPL.getCode());
            }else {
                info.setStatus(ApplicationInfoStatus.REVIEW.getCode());
            }
            if (ModelName.LVL1_MANAGER.getName().equals(nextModelName)) {
                info.setRecheckTime(new Date());
            } else if (ModelName.CARRY.getName().equals(nextModelName)) {
                info.setBigDataTime(new Date());
            }
        }else {
            String modelIds=vo.getFallBackModelIds();
            if (modelIds==null||modelIds.trim().equals("")) {
                return R.error(201, "回退环节ID不能为空,回退失败");
            }
            // 流转到服务台复核或申请人
            Workflowmodel fallbackModel = workflowmodelService.getById(modelIds);
            if (ModelName.RECHECK.getName().equals(fallbackModel.getModelname())) {
                activityService.fallbackOnApproveNotPass(vo, map);
                info.setStatus(ApplicationInfoStatus.REVIEW.getCode());
            } else {
                activityService.fallbackOnApproveNotPass(vo, null);
                info.setStatus(ApplicationInfoStatus.INNER_REVIEW.getCode());
            }
        }
        saasApplicationMergeService.updateById(info);
        saasApplicationService.updateStatus(info.getId(), info.getStatus());
        return R.ok();
    }

    @ApiOperation(value = "业务办理")
    @ApiImplicitParams({
            @ApiImplicitParam(name="activityId", value="当前环节id", required = true, paramType="path", dataType="String"),
    })
    @RequestMapping(value = "/impl/{id}/{activityId}/{modelId}", method = RequestMethod.POST)
    public R impl(@LoginUser User user,
                  @PathVariable(value = "id") String id, @PathVariable(value = "modelId") String modelId,
                  @PathVariable(value = "activityId") String activityId, @RequestBody ImplRequest implRequest) throws Exception {
        SaasApplicationMerge info = saasApplicationMergeService.getById(id);
        Activity activity =activityService.getById(activityId);
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
                saasApplicationMergeService.saveImpl(user, param, activity.getModelid());
            } else {
                throw new BaseException("实施失败");
            }
        }catch (Exception e){
            throw e;
        }finally {
            lock.unlock(lockKey, uuid);
        }
        if ("1".equals(implRequest.getResult())){
            activityService.advanceCurrentActivity(activity,info.getCreator());
            saasApplicationMergeService.update(new SaasApplicationMerge(), new UpdateWrapper<SaasApplicationMerge>().lambda()
                    .eq(SaasApplicationMerge::getId, info.getId())
                    .set(SaasApplicationMerge::getCarryTime, new Date()));
        }else {
            if (modelId==null||modelId.trim().equals("")) {
                return R.error(201, "回退环节ID不能为空,回退失败");
            }
            FallBackVO vo = new FallBackVO();
            vo.setCurrentActivityId(activityId);
            vo.setFallBackModelIds(modelId);
            Map<String,String> map = new HashMap<>();
            map.put("name", BusinessName.SAAS_RESOURCE);
            map.put("order", info.getOrderNumber());
            activityService.fallbackOnApproveNotPass(vo, map);
        }

        return R.ok();
    }

    @ApiOperation(value = "加办")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public R review(@LoginUser User user, @RequestBody ApproveVo appVo) {
        String uuid = UUIDUtil.getUUID();
        String lockKey = appVo.getCurrentActivityId().intern();
        R r = R.error();
        try {
            if (lock.lock(lockKey, uuid)) {
                r =  activityService.add(appVo.getApprove(),appVo.getUserIds(),appVo.getCurrentActivityId(), user);
            } else {
                return R.error();
            }
        }finally {
            lock.unlock(lockKey, uuid);
        }
        return r;
    }


    @ApiOperation("已合并列表分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", paramType="query", dataType="String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="query", dataType="String"),
            @ApiImplicitParam(name="status", value="状态, 1:待审核 2:待实施 3:使用中 4:已删除 5:审核驳回 6:实施驳回", defaultValue = "", paramType="query", dataType="String"),
            @ApiImplicitParam(name="userName", value="申请人", paramType="query", dataType="String"),
            @ApiImplicitParam(name="serviceName", value="申请服务", paramType="query", dataType="String"),
            @ApiImplicitParam(name="orderNumber", value="订单号", paramType="query", dataType="String"),
            @ApiImplicitParam(name="processType", value="处理人,0:全部 1:我 2:其它人", defaultValue = "0", paramType="query", dataType="String")
    })
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public R page(@LoginUser User user,
                  @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                  @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                  @RequestParam(required = false) String userName,
                  @RequestParam(required = false) String serviceName,
                  @RequestParam(required = false) String orderNumber,
                  @RequestParam(required = false) String startDate,
                  @RequestParam(required = false) String endDate,
                  @RequestParam(required = false, defaultValue = "") String status,
                  @RequestParam(required = false, defaultValue = "0") String processType) {
        if (!ApplicationInfoStatus.REVIEW.getCode().equals(status)
                && !ApplicationInfoStatus.IMPL.getCode().equals(status)) {
            processType = null; // 不是待审核/待实施状态,不能过滤处理人
        }

        IPage<SaasApplicationMerge> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        Map<String, Object> param = new HashMap<>();
        param.put("user", user);
        param.put("userName", CommonHandler.dealNameForEqualQuery(userName));
       // param.put("appName",CommonHandler.dealNameforQuery(appName));
        param.put("status", status);
        param.put("orderNumber",CommonHandler.dealNameforQuery(orderNumber));
        param.put("processType", processType);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        //要查询serviceName需要多关联两张表，需要时才关联，提高查询效率
        if(StringUtils.isBlank(serviceName)){
            page = saasApplicationMergeService.getFlowPage(user, page, param);
        }else {
            param.put("serviceName",CommonHandler.dealNameforQuery(serviceName));
            page = saasApplicationMergeService.getFlowPageWithServiceName(user, page, param);
        }
        return R.ok(page);
    }

    @ApiOperation("合并后申请人员列表导出")
    @RequestMapping(value = "/export/user/{id}", method = RequestMethod.GET)
    public void userExport(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<SaasApplication> list = saasApplicationService.getListByMergeId(id);
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                SaasApplication sa = list.get(i);
                sa.setId(String.valueOf(i + 1));
            }
        }
        Map<String, Object> map = Maps.newHashMap();
        map.put("sa", new ExcelListEntity(list, SaasApplication.class));
        XWPFDocument doc = WordExportUtil.exportWord07("template/saas_application.docx", map);
        WordUtil.export(request, response, "广东省公安厅警务云平台通用应用权限审批表", doc);
    }

    @ApiOperation("SaaS应用总表工单统计表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="areas", value="地区", paramType="query", dataType="String"),
            @ApiImplicitParam(name="policeCategory", value="警种", paramType="query", dataType="String"),
            @ApiImplicitParam(name="submitStartDate", value="提交申请开始日期", required = true, paramType="query", dataType="String"),
            @ApiImplicitParam(name="submitEndDate", value="提交申请结束日期", required = true, paramType="query", dataType="String"),
            @ApiImplicitParam(name="bigdataStartDate", value="大数据办审核开始日期", required = true, paramType="query", dataType="String"),
            @ApiImplicitParam(name="bigdataEndDate", value="大数据办审核结束日期", required = true, paramType="query", dataType="String"),
            @ApiImplicitParam(name="carryStartDate", value="业务办理开始日期", required = true, paramType="query", dataType="String"),
            @ApiImplicitParam(name="carryEndDate", value="业务办理结束日期", required = true, paramType="query", dataType="String")
    })
    @RequestMapping(value = "/export/saasTotal", method = RequestMethod.GET)
    public void saasTotal(@LoginUser User user, HttpServletRequest request, HttpServletResponse response,
                          @RequestParam(required = false) String areas,
                          @RequestParam(required = false) String policeCategory,
                          String submitStartDate, String submitEndDate,
                          String bigdataStartDate, String bigdataEndDate,
                          String carryStartDate, String carryEndDate) throws Exception {
        if (UserType.TENANT_MANAGER.getCode().equals(user.getType())) {
            areas = user.getTenantArea();
            policeCategory = user.getTenantPoliceCategory();
        }
        Map<String, String> params = getParamsMap(submitStartDate, submitEndDate, bigdataStartDate, bigdataEndDate, carryStartDate, carryEndDate);
        List<SaasTotal> data = saasApplicationMergeService.saasTotal(areas, policeCategory, params);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"SaaS应用总表工单统计"), SaasTotal.class, data);
        ExcelUtil.export(request, response, "SaaS应用总表工单统计表", workbook);
    }

    @ApiOperation("SaaS应用申请统计表")
    @RequestMapping(value = "/export/saasOrderTotal", method = RequestMethod.GET)
    public void saasOrderTotal(@LoginUser User user, HttpServletRequest request, HttpServletResponse response,
                          @RequestParam(required = false) String areas,
                          @RequestParam(required = false) String policeCategory,
                               String submitStartDate, String submitEndDate,
                               String bigdataStartDate, String bigdataEndDate,
                               String carryStartDate, String carryEndDate) throws Exception {
        if (UserType.TENANT_MANAGER.getCode().equals(user.getType())) {
            areas = user.getTenantArea();
            policeCategory = user.getTenantPoliceCategory();
        }
        Map<String, String> params = getParamsMap(submitStartDate, submitEndDate, bigdataStartDate, bigdataEndDate, carryStartDate, carryEndDate);
        List<SaasOrderTotal> data = saasApplicationMergeService.saasOrderTotal(areas, policeCategory, params);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"SaaS应用申请统计"), SaasOrderTotal.class, data);
        ExcelUtil.export(request, response, "SaaS应用申请统计表", workbook);
    }

    @ApiOperation("SaaS应用工单查询")
    @RequestMapping(value = "/export/saasOrderQuery", method = RequestMethod.GET)
    public void saasOrderQuery(@LoginUser User user, HttpServletRequest request, HttpServletResponse response,
                               @RequestParam(required = false) String areas,
                               @RequestParam(required = false) String policeCategory,
                               String submitStartDate, String submitEndDate,
                               String bigdataStartDate, String bigdataEndDate,
                               String carryStartDate, String carryEndDate) throws Exception {
        if (UserType.TENANT_MANAGER.getCode().equals(user.getType())) {
            areas = user.getTenantArea();
            policeCategory = user.getTenantPoliceCategory();
        }
        Map<String, String> params = getParamsMap(submitStartDate, submitEndDate, bigdataStartDate, bigdataEndDate, carryStartDate, carryEndDate);
        ArrayList<SaasOrderTotal> data = (ArrayList<SaasOrderTotal>) saasApplicationMergeService.saasOrderQuery(areas, policeCategory, params);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"SaaS应用工单查询"), SaasOrderTotal.class, data);
        ExcelUtil.export(request, response, "SaaS应用工单查询", workbook);
    }

    @ApiOperation("SaaS应用人员使用状况统计表")
    @RequestMapping(value = "/export/saasUseTotal", method = RequestMethod.GET)
    public void saasUseTotal(@LoginUser User user, HttpServletRequest request, HttpServletResponse response,
                               @RequestParam(required = false) String areas,
                               @RequestParam(required = false) String policeCategory,
                             String submitStartDate, String submitEndDate,
                             String bigdataStartDate, String bigdataEndDate,
                             String carryStartDate, String carryEndDate) throws Exception {
        if (UserType.TENANT_MANAGER.getCode().equals(user.getType())) {
            areas = user.getTenantArea();
            policeCategory = user.getTenantPoliceCategory();
        }
        Map<String, String> params = getParamsMap(submitStartDate, submitEndDate, bigdataStartDate, bigdataEndDate, carryStartDate, carryEndDate);
        List<SaasUseTotal> data = saasApplicationMergeService.saasUseTotal(areas, policeCategory, params);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"SaaS应用人员使用状况统计"), SaasUseTotal.class, data);
        ExcelUtil.export(request, response, "SaaS应用人员使用状况统计表", workbook);
    }

    @ApiOperation("SaaS应用权限回收工单统计")
    @RequestMapping(value = "/export/saasRecoverTotal", method = RequestMethod.GET)
    public void saasRecoverTotal(@LoginUser User user, HttpServletRequest request, HttpServletResponse response,
                             @RequestParam(required = false) String areas,
                             @RequestParam(required = false) String policeCategory,
                             String submitStartDate, String submitEndDate,
                             String bigdataStartDate, String bigdataEndDate,
                             String carryStartDate, String carryEndDate) throws Exception {
        if (UserType.TENANT_MANAGER.getCode().equals(user.getType())) {
            areas = user.getTenantArea();
            policeCategory = user.getTenantPoliceCategory();
        }
        Map<String, String> params = getParamsMap(submitStartDate, submitEndDate, bigdataStartDate, bigdataEndDate, carryStartDate, carryEndDate);
        List<SaasRecoverTotal> data = saasRecoverApplicationService.saasRecoverExport(areas, policeCategory, params);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,"SaaS应用权限回收工单统计"), SaasRecoverTotal.class, data);
        ExcelUtil.export(request, response, "SaaS应用权限回收工单统计", workbook);
    }

    public static Map<String, String> getParamsMap(String submitStartDate, String submitEndDate, String bigdataStartDate, String bigdataEndDate, String carryStartDate, String carryEndDate) {
        if (StringUtils.isEmpty(submitStartDate) || StringUtils.isEmpty(submitEndDate)) {
            submitStartDate = null;
            submitEndDate = null;
        }
        if (StringUtils.isEmpty(bigdataStartDate) || StringUtils.isEmpty(bigdataEndDate)) {
            bigdataStartDate = null;
            bigdataEndDate = null;
        }
        if (StringUtils.isEmpty(carryStartDate) || StringUtils.isEmpty(carryEndDate)) {
            carryStartDate = null;
            carryEndDate = null;
        }
        Map<String, String> params = Maps.newHashMap();
        params.put("submitStartDate", submitStartDate);
        params.put("submitEndDate", submitEndDate);
        params.put("bigdataStartDate", bigdataStartDate);
        params.put("bigdataEndDate", bigdataEndDate);
        params.put("carryStartDate", carryStartDate);
        params.put("carryEndDate", carryEndDate);
        return params;
    }


    @ApiOperation("SaaS旧数据Excel导入")
    @RequestMapping(value = "/import/oldData", method = RequestMethod.GET)
    public R importOldData() throws Exception {
        // TODO: 2019/10/29 修改目标文件路径
        File file = new File(filePath);
        //File file = new File("F://test.xlsx");
        if (!file.exists()) {
            return R.error("文件不存在");
        }
        ImportParams params = new ImportParams();
        List<ImportSaasApplication> list = ExcelImportUtil.importExcel(file, ImportSaasApplication.class, params);
        saveData(list);
        return R.ok("数据导入成功");
    }

    private void saveData(List<ImportSaasApplication> list) throws Exception {
        if (list == null) {
            return;
        }
        for (ImportSaasApplication impt :list){
            logger.error("正在导入,序号:[{}]", impt.getId());

            SaasApplicationMerge merge = getSaasApplicationMerge(impt);
            merge.insert();

            SaasApplication saasApplication = getSaasApplication(impt, merge);
            if ("是".equals(impt.getLvl2Recycle())) {
                saasApplication.setRecoverFlag("-1");
                // TODO: 2019/10/31 回收记录
                insertRevocerData(impt,saasApplication);
            } else switch (impt.getBigDataRecycle()) {
                case "否":
                    saasApplication.setRecoverFlag("0"); // 导出表格中使用权限状态为:是
                    break;
                case "是":
                    saasApplication.setRecoverFlag("-1"); // 导出表格中使用权限状态为:否
                    // TODO: 2019/10/31 回收记录
                    insertRevocerData(impt, saasApplication);
                    break;
                case "是，一键申请重复":
                    saasApplication.setRecoverFlag("0");
                    createNewRecord(impt);
                    // TODO: 2019/10/31 回收记录
                    insertRevocerData(impt, saasApplication);
                    break;
                case "再开通":
                    saasApplication.setRecoverFlag("0");
                    // TODO: 2019/10/31 回收记录
                    insertRevocerData(impt, saasApplication);
                    break;
                case "再开通，一键申请重复":
                    saasApplication.setRecoverFlag("0");
                    createNewRecord(impt);
                    // TODO: 2019/10/31 回收记录
                    insertRevocerData(impt, saasApplication);
                    break;
                default:
                    break;
            }
            saasApplication.insert();

            saveExt(impt, saasApplication);
        }
    }

    /**
     * 回收信息记录
     * @param impt
     * @throws Exception
     */
    private void insertRevocerData( ImportSaasApplication impt, SaasApplication saasApplication) throws Exception {
        SaasRecoverApplication recoverApplication = getSaasRecoverApplication(impt);
        recoverApplication.insert();//回收主表插入数据
        saasApplication.setRecoverId(recoverApplication.getId());// 原始申请单和回收申请单关联
        getSaasRecoverInfo(impt,recoverApplication);//回收明细表插入数据
    }

    /**
     * 往回收信息主表中插入数据
     * @param impt 导入的数据实体
     * @return
     */
    private SaasRecoverApplication getSaasRecoverApplication(ImportSaasApplication impt) throws Exception {
        //List<SaasRecoverApplication> saasRecoverApplicationList = new ArrayList<>();
        SaasRecoverApplication recover = new SaasRecoverApplication();
        recover.setIsImport("1");
        recover.setStatus(ApplicationInfoStatus.USE.getCode());
        recover.setCreator(impt.getIdcard());
        recover.setCreatorName(impt.getName());
        recover.setOrgName(impt.getWorkOrgName());
        recover.setPost(impt.getPostType());
        recover.setPhone(impt.getPhone());
        String orgName = impt.getRealOrgName();//所属地区或警种
        String area = AreaPoliceCategoryUtils.getAreaName(orgName);
        if("省厅".equals(area)){//是省厅的所属地区为省厅 警种为所属警种，非省厅的只有地区
            recover.setAreas(area);
            recover.setApplyType("1");
            String policecategory = AreaPoliceCategoryUtils.getPoliceCategory(orgName);
            recover.setPoliceCategory(policecategory);
        }else{
            recover.setApplyType("0");
            recover.setAreas(area);
        }
        /*String param1 = impt.getPoliceCategory();//所属警种
        String policeCategory = AreaPoliceCategoryUtils.getPoliceCategory(param1);
        if(StringUtils.isNotEmpty(policeCategory)){
            recover.setApplyType("0");
            recover.setAreas(area);
        }else{
            recover.setApplyType("1");
            recover.setPoliceCategory(param);
        }*/

        if (!"/".equals(impt.getRecycleTime())) {
            SimpleDateFormat sdf  = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",Locale.US);
            //String date = ;
            //Date date1 = new Date();
            Date date = sdf.parse(impt.getRecycleTime());
            sdf=new SimpleDateFormat("yyyy/MM/dd");
            String mydate  =  sdf.format(date);
            Date recycleTime  = sdf.parse(mydate);
            recover.setCreateTime(recycleTime);
            recover.setModifiedTime(recycleTime);
            recover.setRecheckTime(recycleTime);
            recover.setBigDataTime(recycleTime);
            recover.setCarryTime(recycleTime);
        }
        //saasRecoverApplicationList.add(recover);
        return recover;
    }

    /**
     * 往回收信息明细表中插入数据
     * @param impt 导入的数据实体
     * @param recoverApp  回收信息主表实体
     * @return
     */
    private void getSaasRecoverInfo(ImportSaasApplication impt,SaasRecoverApplication recoverApp) throws ParseException, IllegalAccessException {
        Field[] fileds = ImportSaasApplication.class.getDeclaredFields();
        List<SaasRecoverInfo> recoverInfoList = new ArrayList<>();
        SaasRecoverInfo saasRecoverInfo = null;
        for(Field field : fileds ){
            field.setAccessible(true);
            if(field.getName().startsWith("service")){
               Object val  = field.get(impt);
               //服务为1的需要导入
                if("1".equals(val)){
                    saasRecoverInfo = new SaasRecoverInfo();
                    saasRecoverInfo.setIsImport("1");
                    saasRecoverInfo.setAppInfoId(recoverApp.getId());
                    saasRecoverInfo.setReName(impt.getName());
                    saasRecoverInfo.setReIdcard(impt.getIdcard());
                    saasRecoverInfo.setReOrgName(impt.getWorkOrgName());
                    if (!"/".equals(impt.getRecycleTime())) {
                        SimpleDateFormat sdf  = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",Locale.US);
                        Date date = sdf.parse(impt.getRecycleTime());
                        sdf=new SimpleDateFormat("yyyy/MM/dd");
                        String mydate  =  sdf.format(date);
                        Date recycleTime  = sdf.parse(mydate);
                        //Date recycleTime = sdf.parse(impt.getRecycleTime());
                        saasRecoverInfo.setCreateTime(recycleTime);
                        saasRecoverInfo.setModifiedTime(recycleTime);
                    }
                    recoverInfoList.add(saasRecoverInfo) ;
                }
            }
        }
        if(!recoverInfoList.isEmpty()){
            saasRecoverInfoService.saveBatch(recoverInfoList);
        }
    }
    /**
     * 新增一条记录
     */
    private void createNewRecord(ImportSaasApplication impt) throws Exception {
        Instant instant = LocalDate.of(2019, 8, 8)
                .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        SaasApplicationMerge merge = getSaasApplicationMerge(impt);
        merge.setApplicationTime(date);
        merge.setCreateTime(date);
        merge.setModifiedTime(date);
        merge.setRecheckTime(date);
        merge.setBigDataTime(date);
        merge.setCarryTime(date);
        merge.insert();

        SaasApplication saasApplication = getSaasApplication(impt, merge);
        saasApplication.setApplicationTime(date);
        saasApplication.setCreateTime(date);
        saasApplication.setModifiedTime(date);
        saasApplication.setRecoverFlag("0");
        saasApplication.insert();

        saveExt(impt, saasApplication);
    }

    private SaasApplicationMerge getSaasApplicationMerge(ImportSaasApplication impt) {
        SaasApplicationMerge merge = new SaasApplicationMerge();
        merge.setIsImport("1");
        merge.setStatus(ApplicationInfoStatus.USE.getCode());
        merge.setCreator(impt.getIdcard());
        merge.setCreatorName(impt.getName());
        merge.setOrgName(impt.getWorkOrgName());
        merge.setPostType(impt.getPostType());
        merge.setMobileWork(impt.getPhone());
        merge.setApplicationTime(impt.getSubmitTime());
        merge.setCreateTime(impt.getSubmitTime());
        merge.setModifiedTime(impt.getSubmitTime());
        merge.setPoliceCategory(impt.getPoliceCategory());
        merge.setRecheckTime(impt.getSubmitTime());
        merge.setBigDataTime(impt.getBigDataTime());
        merge.setCarryTime(impt.getCarryTime());
        return merge;
    }

    private SaasApplication getSaasApplication(ImportSaasApplication impt, SaasApplicationMerge merge) {
        SaasApplication saasApplication = new SaasApplication();
        saasApplication.setIsImport("1");
        saasApplication.setStatus(ApplicationInfoStatus.USE.getCode());
        saasApplication.setCreator(impt.getIdcard());
        saasApplication.setCreatorName(impt.getName());
        saasApplication.setOrgName(impt.getWorkOrgName());
        saasApplication.setPostType(impt.getPostType());
        saasApplication.setMobileWork(impt.getPhone());
        saasApplication.setApplicationTime(impt.getSubmitTime());
        String orgName = impt.getRealOrgName();//所属地区或警种
        String area = AreaPoliceCategoryUtils.getAreaName(orgName);
        if("省厅".equals(area)){//是省厅的所属地区为省厅 警种为所属警种，非省厅的只有地区
            saasApplication.setAreas(area);
            saasApplication.setApplyType("1");
            String policecategory = AreaPoliceCategoryUtils.getPoliceCategory(orgName);
            saasApplication.setPoliceCategory(policecategory);
        }else{
            saasApplication.setApplyType("0");
            saasApplication.setAreas(area);
        }
       /* String param = impt.getPoliceCategory();//二级管理单位（地市/警种）
        String area = AreaPoliceCategoryUtils.getAreaName(param);
        if(StringUtils.isNotEmpty(area)){
            saasApplication.setApplyType("0");
            saasApplication.setAreas(area);
        }else{
            saasApplication.setApplyType("1");
            saasApplication.setPoliceCategory(param);
        }*/
        saasApplication.setCreateTime(impt.getSubmitTime());
        saasApplication.setModifiedTime(impt.getSubmitTime());
        saasApplication.setIp(impt.getIp());
        saasApplication.setMergeId(merge.getId());
        return saasApplication;
    }

    private void saveExt(ImportSaasApplication impt, SaasApplication saasApplication) throws Exception {
        Field[] fields = ImportSaasApplication.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            if (name.startsWith("service")) {
                Object val = field.get(impt);
                if ("1".equals(val)) {
                    Excel excel = field.getAnnotation(Excel.class);
                    SaasApplicationExt ext = new SaasApplicationExt();
                    ext.setServiceName(excel.name());
                    ext.setMasterId(saasApplication.getId());
                    ext.insert();
                }
            }
        }
    }

}

