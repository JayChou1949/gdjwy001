package com.upd.hwcloud.controller.backend.application;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.contains.ApplicationInfoStatus;
import com.upd.hwcloud.bean.contains.BusinessName;
import com.upd.hwcloud.bean.contains.ModelName;
import com.upd.hwcloud.bean.dto.ImplRequest;
import com.upd.hwcloud.bean.entity.EpidemicApplication;
import com.upd.hwcloud.bean.entity.SaasApplication;
import com.upd.hwcloud.bean.entity.SaasApplicationMerge;
import com.upd.hwcloud.bean.entity.ServicePublish;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.AppReviewInfo;
import com.upd.hwcloud.bean.entity.application.ApplicationFeedback;
import com.upd.hwcloud.bean.entity.wfm.Activity;
import com.upd.hwcloud.bean.entity.wfm.AdvanceBeanVO;
import com.upd.hwcloud.bean.entity.wfm.ApproveVo;
import com.upd.hwcloud.bean.entity.wfm.FallBackVO;
import com.upd.hwcloud.bean.entity.wfm.Instance;
import com.upd.hwcloud.bean.entity.wfm.WorkFlowModelVo;
import com.upd.hwcloud.bean.entity.wfm.Workflow;
import com.upd.hwcloud.bean.entity.wfm.Workflowmodel;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.common.lock.DistributeLock;
import com.upd.hwcloud.common.utils.UUIDUtil;
import com.upd.hwcloud.service.IEpidemicApplicationService;
import com.upd.hwcloud.service.application.IApplicationFeedbackService;
import com.upd.hwcloud.service.msg.MessageProvider;
import com.upd.hwcloud.service.wfm.IActivityService;
import com.upd.hwcloud.service.wfm.IInstanceService;
import com.upd.hwcloud.service.wfm.IWorkflowService;
import com.upd.hwcloud.service.wfm.IWorkflowmodelService;
import com.upd.hwcloud.service.workbench.impl.CommonHandler;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * SaaS资源申请原始信息表 前端控制器
 * </p>
 *
 * @author wuc
 * @since 2020-02-27
 */
@Api(description = "疫情资源申请")
@RestController
@RequestMapping("/epidemicApplication")
public class EpidemicApplicationController {

    @Autowired
    private IWorkflowService workflowService;

    @Autowired
    private IWorkflowmodelService workflowmodelService;

    @Autowired
    private IEpidemicApplicationService epidemicApplicationService;

    @Autowired
    private IActivityService activityService;
    @Autowired
    private IInstanceService instanceService;
    @Autowired
    private MessageProvider messageProvider;

    @Autowired
    private DistributeLock lock;

    @Autowired
    private IApplicationFeedbackService applicationFeedbackService;

    @ApiOperation("创建流程")
    @RequestMapping(value = "/createflow", method = RequestMethod.GET)
    public R createflow() {
        Workflow workflow = workflowService.getOne(new QueryWrapper<Workflow>().eq("DEFAULT_PROCESS", "EPIDEMIC"));
        if (null!=workflow) {
            return R.error("资源申请流程已经存在");
        }
        Workflow flow = new Workflow();
        flow.setFlowStatus(1);
        flow.setId(UUIDUtil.getUUID());
        flow.setDefaultProcess("EPIDEMIC");
        flow.setWorkflowname("疫情应用申请流程");
        flow.insert();

        String flowId = flow.getId();
        List<Workflowmodel> models = new ArrayList<>();
        // 业务办理
        Workflowmodel carry =  new Workflowmodel(flowId, ModelName.CARRY.getName(),null);
        carry.setDefaulthandleperson("410184198209096919");
        models.add(carry);
        // 大数据办审批
        Workflowmodel bigData =  new Workflowmodel(flowId, ModelName.APPROVE.getName(), carry.getId());
        bigData.setDefaulthandleperson("410184198209096919");
        models.add(bigData);
        // 服务台复核
        Workflowmodel recheck =  new Workflowmodel(flowId, ModelName.RECHECK.getName(), bigData.getId());
        recheck.setDefaulthandleperson("410184198209096919");
        models.add(recheck);
        // 本单位审批
        Workflowmodel depApprove =  new Workflowmodel(flowId, ModelName.DEP_APPROVE.getName(), recheck.getId());
        models.add(depApprove);
        // 申请
        Workflowmodel apply =  new Workflowmodel(flowId, ModelName.APPLY.getName(), depApprove.getId());
        apply.setIsstart(0);
        models.add(apply);
        workflowmodelService.saveBatch(models);
        return R.ok();
    }

    @ApiOperation("获取流程")
    @RequestMapping(value = "/workflow", method = RequestMethod.GET)
    public R flow(Integer version) {
        Workflow workflow = workflowService.getOne(new QueryWrapper<Workflow>().eq("DEFAULT_PROCESS","EPIDEMIC"));
        workflow.setRecheck(workflowmodelService.getByFlowAndAct(workflow.getId(),ModelName.RECHECK.getName(),version));
        workflow.setApprove(workflowmodelService.getByFlowAndAct(workflow.getId(),ModelName.APPROVE.getName(),version));
        workflow.setCarry(workflowmodelService.getByFlowAndAct(workflow.getId(),ModelName.CARRY.getName(),version));
        workflow.setVersion(version);
        return R.ok(workflow);
    }

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

        IPage<EpidemicApplication> page = new Page<>();
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
            page = epidemicApplicationService.getFlowPage(user, page, param);
        }else {
            param.put("serviceName",CommonHandler.dealNameforQuery(serviceName));
            page = epidemicApplicationService.getFlowPageWithServiceName(user, page, param);
        }
        return R.ok(page);
    }

    @ApiOperation(value = "新建申请单")
    @RequestMapping(value = "/create/{userId}", method = RequestMethod.POST)
    public R create(@LoginUser User user, @RequestBody EpidemicApplication info, @PathVariable String userId) {
        Workflow workflow = workflowService.getOne(new QueryWrapper<Workflow>().eq("DEFAULT_PROCESS","EPIDEMIC"));
        if(workflow==null){
            return R.error("未配置流程");
        }
        info.setWorkFlowId(workflow.getId());
        info.setWorkFlowVersion(workflow.getVersion());
        epidemicApplicationService.create(user, info);
        R r= instanceService.launchInstanceOfWorkFlow(user.getIdcard(), info.getWorkFlowId(), info.getId());

        Workflowmodel workflowmodel = workflowmodelService.getOne(new QueryWrapper<Workflowmodel>().eq("WORKFLOWID",workflow.getId()).eq("modelname", ModelName.DEP_APPROVE.getName()).eq("VERSION",workflow.getVersion()));

        Map<String, String> modelMapToPerson = new HashMap<>();
        modelMapToPerson.put(workflowmodel.getId(),userId);
        AdvanceBeanVO advanceBeanVO = new AdvanceBeanVO();
        advanceBeanVO.setCurrentActivityId(r.get("data").toString());
        advanceBeanVO.setModelMapToPerson(modelMapToPerson);

        Map<String,String> map = new HashMap<>();
        map.put("name", BusinessName.EPIDEMIC);
        map.put("order", info.getOrderNumber());
        activityService.advanceCurrentActivity(advanceBeanVO, map);
        messageProvider.sendMessageAsync(messageProvider.buildSuccessMessage(user, BusinessName.EPIDEMIC, info.getOrderNumber()));
        return R.ok("发起流程成功");
    }

    @ApiOperation(value = "申请详情")
    @ApiImplicitParam(name="id", value="申请id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public R viewDetailsApply(@LoginUser User user, @PathVariable String id) {
        EpidemicApplication detail = epidemicApplicationService.getDetails(id);
        Instance instance = instanceService.getInstanceByBusinessId(id);
        Activity activity = null;
        if (null!=instance){
            detail.setWorkFlowId(instance.getWorkflowid());
            detail.setWorkFlowVersion(instance.getWorkflowversion());
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
                if (modelName.equals(ModelName.APPLY.getName())||modelName.equals(ModelName.RECHECK.getName())) {
                    detail.setCanEdit(true);
                }
                if (modelName.equals(ModelName.DEP_APPROVE.getName())||modelName.equals(ModelName.RECHECK.getName())||modelName.equals(ModelName.APPROVE.getName())) {
                    detail.setCanTrans(true);
                    detail.setCanReview(true);
                    if (!modelName.equals(ModelName.DEP_APPROVE.getName())) {
                        detail.setCanAdd(true);
                    }
                }
                if (modelName.equals(ModelName.DEP_APPROVE.getName())) {
                    detail.setCanFall(true);
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
        // 反馈记录
        List<ApplicationFeedback> feedback = applicationFeedbackService.getListByAppInfoId(detail.getId());
        if (modelVo!=null&&detail!=null) {
            bean.put("model",modelVo);
            bean.put("bizData",detail);
            bean.put("activityId",activityId);
            bean.put("feedback",feedback);
            return R.ok(bean);
        }else {
            return R.error("未找到流程");
        }
    }

    @ApiOperation(value = "修改申请信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R update(@LoginUser User user, @RequestBody EpidemicApplication info) throws IOException {
        epidemicApplicationService.update(user,info);
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
                epidemicApplicationService.deleteById(user, id);
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

    @ApiOperation(value = "审核驳回后提交")
    @ApiImplicitParams({
            @ApiImplicitParam(name="type", value="审核类型,inner:部门内审核 kx:科信审核", required = true, defaultValue = "kx", paramType="form", dataType="String"),
            @ApiImplicitParam(name="id", value="申请单id,多个使用逗号分隔", required = true, paramType="form", dataType="String"),
            @ApiImplicitParam(name="userIds", value="审核人id,多个使用逗号分隔", paramType="form", dataType="String")
    })
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public R submit(@LoginUser User user, @RequestParam("id") String id,
                    @RequestParam(value = "type", defaultValue = "kx") String type,
                    @RequestParam(value = "userIds", required = false) String userIds) {
        if (StringUtils.isEmpty(id)) {
            return R.error("未找到数据!");
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
        EpidemicApplication info = epidemicApplicationService.getById(id);
        Instance instance =instanceService.getInstanceByBusinessId(info.getId());
        if (null==instance){
            throw new BaseException("流程实例未找到");
        }
        Activity firstActivity = activityService.getOne(new QueryWrapper<Activity>().eq("activitystatus","待办")
                .eq("isstart",0).eq("instanceid",instance.getId()));
        String flowId = instance.getWorkflowid();
        Map<String, String> modelMapToPerson = new HashMap<String, String>();
        info.setModifiedTime(new Date());
        Workflowmodel workflowmodel = new Workflowmodel();
        if ("kx".equals(type)) {
            info.setStatus(ApplicationInfoStatus.REVIEW.getCode());
            workflowmodel = workflowmodelService.getOne(new QueryWrapper<Workflowmodel>().eq("WORKFLOWID",flowId).eq("modelname", ModelName.RECHECK.getName()).eq("VERSION",instance.getWorkflowversion()));
        }else {
            info.setStatus(ApplicationInfoStatus.INNER_REVIEW.getCode());
            workflowmodel = workflowmodelService.getOne(new QueryWrapper<Workflowmodel>().eq("WORKFLOWID",flowId).eq("modelname", ModelName.DEP_APPROVE.getName()).eq("VERSION",instance.getWorkflowversion()));
        }
        modelMapToPerson.put(workflowmodel.getId(), userIds);
        AdvanceBeanVO advanceBeanVO = new AdvanceBeanVO();
        advanceBeanVO.setCurrentActivityId(firstActivity.getId());
        advanceBeanVO.setModelMapToPerson(modelMapToPerson);
        Map<String,String> map = new HashMap<>();
        map.put("name",BusinessName.EPIDEMIC);
        map.put("order",info.getOrderNumber());
        activityService.advanceCurrentActivity(advanceBeanVO,map);
        epidemicApplicationService.updateById(info);
        messageProvider.sendMessageAsync(messageProvider.buildSuccessMessage(user, BusinessName.EPIDEMIC, info.getOrderNumber()));
        return R.ok();
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
                R r = R.ok();
                EpidemicApplication info = epidemicApplicationService.getByActId(activityId);
                r =  activityService.forward(activityId, userIds);
                messageProvider.sendMessageAsync(messageProvider.buildProcessingMessage(BusinessName.EPIDEMIC,info.getOrderNumber(),userIds));
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
    @ApiImplicitParams({
            @ApiImplicitParam(name="activityId", value="当前环节id", required = true, paramType="path", dataType="String"),
            @ApiImplicitParam(name="userIds", value="转发审核人id,多个使用逗号分隔", required = true, paramType="form", dataType="String")
    })
    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    public R approve(@LoginUser User user,
                     @RequestBody FallBackVO vo) {
        AppReviewInfo approve = vo.getApprove();
        approve.setCreator(user.getIdcard());
        EpidemicApplication info = epidemicApplicationService.getById(vo.getApprove().getAppInfoId());
        Activity activity =activityService.getById(vo.getCurrentActivityId());
        if ("adviser".equals(activity.getActivitytype())){
            activityService.adviseActivity(vo.getCurrentActivityId(), approve);
            return R.ok();
        }
        Map<String,String> map = new HashMap<>();
        map.put("name",BusinessName.EPIDEMIC);
        map.put("order",info.getOrderNumber());
        if ("1".equals(approve.getResult())){
            R r = activityService.advanceCurrentActivity(vo.getCurrentActivityId(), approve,map);
            Object model = r.get("data");
            if (ModelName.CARRY.getName().equals(model)){
                info.setStatus(ApplicationInfoStatus.IMPL.getCode());
            }else {
                info.setStatus(ApplicationInfoStatus.REVIEW.getCode());
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
                info.setStatus(ApplicationInfoStatus.REVIEW_REJECT.getCode());
                messageProvider.sendMessageAsync(messageProvider.buildRejectMessage(info.getCreator(), BusinessName.EPIDEMIC));
            }
        }
        epidemicApplicationService.updateById(info);
        return R.ok();
    }

    @ApiOperation(value = "回退")
    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    public R reject(@LoginUser User user,
                    @RequestBody FallBackVO vo) {
        AppReviewInfo approve = vo.getApprove();
        approve.setCreator(user.getIdcard());
        EpidemicApplication info = epidemicApplicationService.getById(approve.getAppInfoId());
        if(null==info){
            return R.error("未找到待办数据");
        }
        activityService.fallbackCurrentActivity(vo);
        if (ApplicationInfoStatus.IMPL.getCode().equals(info.getStatus())){
            info.setStatus(ApplicationInfoStatus.IMPL_REJECT.getCode());
        }else {
            info.setStatus(ApplicationInfoStatus.REVIEW_REJECT.getCode());
        }
        epidemicApplicationService.updateById(info);
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
        EpidemicApplication info = epidemicApplicationService.getById(id);
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
                epidemicApplicationService.saveImpl(user, param, activity.getModelid());
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
            messageProvider.sendMessageAsync(messageProvider.buildCompleteMessage(info.getCreator(), BusinessName.EPIDEMIC, info.getOrderNumber()));
        }else {
            if (modelId==null||modelId.trim().equals("")) {
                return R.error(201, "回退环节ID不能为空,回退失败");
            }
            FallBackVO vo = new FallBackVO();
            vo.setCurrentActivityId(activityId);
            vo.setFallBackModelIds(modelId);
            Map<String,String> map = new HashMap<>();
            map.put("name", BusinessName.EPIDEMIC);
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

}

