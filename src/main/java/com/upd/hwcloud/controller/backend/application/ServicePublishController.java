package com.upd.hwcloud.controller.backend.application;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.contains.ApplicationInfoStatus;
import com.upd.hwcloud.bean.contains.BusinessName;
import com.upd.hwcloud.bean.contains.ModelName;
import com.upd.hwcloud.bean.contains.RedisKey;
import com.upd.hwcloud.bean.dto.ImplRequest;
import com.upd.hwcloud.bean.entity.ServicePublish;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.AppReviewInfo;
import com.upd.hwcloud.bean.entity.application.ApplicationFeedback;
import com.upd.hwcloud.bean.entity.wfm.*;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.common.lock.DistributeLock;
import com.upd.hwcloud.common.utils.OrderNum;
import com.upd.hwcloud.common.utils.UUIDUtil;
import com.upd.hwcloud.service.*;
import com.upd.hwcloud.service.application.IApplicationFeedbackService;
import com.upd.hwcloud.service.msg.MessageProvider;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务发布 前端控制器
 * </p>
 *
 * @author wuc
 * @since 2019-10-17
 */
@Api(description = "服务发布申请接口")
@RestController
@RequestMapping("/servicePublish")
public class ServicePublishController {

    @Autowired
    private DistributeLock lock;
    @Autowired
    private IWorkflowmodelService workflowmodelService;
    @Autowired
    private IWorkflowService workflowService;
    @Autowired
    private IActivityService activityService;
    @Autowired
    private IInstanceService instanceService;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    private IServicePublishService servicePublishService;
    @Autowired
    private IApplicationFeedbackService applicationFeedbackService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private String getBusinessName() {
        return BusinessName.PUBLISH;
    }

    @ApiOperation("创建流程")
    @RequestMapping(value = "/createflow", method = RequestMethod.GET)
    public R createflow() {
        Workflow workflow = workflowService.getOne(new QueryWrapper<Workflow>().eq("DEFAULT_PROCESS", "PUBLISH"));
        if (null!=workflow) {
            return R.error("资源申请流程已经存在");
        }
        Workflow flow = new Workflow();
        flow.setFlowStatus(1);
        flow.setId(UUIDUtil.getUUID());
        flow.setDefaultProcess("PUBLISH");
        flow.setWorkflowname("服务发布申请流程");
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


    @ApiOperation("创建流程")
    @RequestMapping(value = "/createflow/{defaultProcess}", method = RequestMethod.GET)
    public R createflowWithValue(@PathVariable String defaultProcess) {
        Workflow workflow = workflowService.getOne(new QueryWrapper<Workflow>().eq("DEFAULT_PROCESS", defaultProcess));
        if (null!=workflow) {
            return R.error("资源申请流程已经存在");
        }
        Workflow flow = new Workflow();
        flow.setFlowStatus(1);
        flow.setId(UUIDUtil.getUUID());
        flow.setDefaultProcess(defaultProcess);
        flow.setWorkflowname("服务发布申请流程");
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
    public R flow() {
        Workflow workflow = workflowService.getOne(new QueryWrapper<Workflow>().eq("DEFAULT_PROCESS","PUBLISH"));
        workflow.setRecheck(workflowmodelService.getByFlowAndAct(workflow.getId(),ModelName.RECHECK.getName(),workflow.getVersion()));
        workflow.setApprove(workflowmodelService.getByFlowAndAct(workflow.getId(),ModelName.APPROVE.getName(),workflow.getVersion()));
        workflow.setCarry(workflowmodelService.getByFlowAndAct(workflow.getId(),ModelName.CARRY.getName(),workflow.getVersion()));
        return R.ok(workflow);
    }

    @ApiOperation("获取流程(通过ID和版本)")
    @RequestMapping(value = "/workflow/{workFlowId}", method = RequestMethod.GET)
    public R flowByID(@PathVariable String workFlowId,Integer version) {
        if(version == null){
            return R.error("流程版本号缺失");
        }
        Workflow workflow = workflowService.getOne(new QueryWrapper<Workflow>().eq("ID",workFlowId));
        workflow.setRecheck(workflowmodelService.getByFlowAndAct(workFlowId,ModelName.RECHECK.getName(),version));
        workflow.setApprove(workflowmodelService.getByFlowAndAct(workFlowId,ModelName.APPROVE.getName(),version));
        workflow.setCarry(workflowmodelService.getByFlowAndAct(workFlowId,ModelName.CARRY.getName(),version));
        workflow.setVersion(version);
        return R.ok(workflow);
    }



    @ApiOperation(value = "新建申请")
    @ApiImplicitParam(name="userId", value="部门内审核人身份证号", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/create/{userId}", method = RequestMethod.POST)
    public R create(@LoginUser User user, @RequestBody ServicePublish info, @PathVariable String userId) {
       /** Workflow workflow = workflowService.getOne(new QueryWrapper<Workflow>().eq("DEFAULT_PROCESS","PUBLISH"));
        if (workflow==null){
            return R.error("未配置流程");
        }
        info.setWorkFlowId(workflow.getId());**/
       Workflow workFlow = getWorkFlowId(info.getServiceType());
       if(workFlow==null){
           return R.error("未配置流程");
       }
       info.setWorkFlowId(workFlow.getId());
       info.setWorkFlowVersion(workFlow.getVersion());
        if (!"2".equals(info.getWhereFrom())){
            info.setWhereFrom("1");
            servicePublishService.save(user,info);
        }else {
            servicePublishService.update(info,new QueryWrapper<ServicePublish>().lambda().eq(ServicePublish::getId,info.getId()));
        }
        R r= instanceService.launchInstanceOfWorkFlow(user.getIdcard(), info.getWorkFlowId(), info.getId());

       // Workflowmodel workflowmodel = workflowmodelService.getOne(new QueryWrapper<Workflowmodel>().eq("WORKFLOWID",workflow.getId()).eq("modelname", ModelName.DEP_APPROVE.getName()));

        Workflowmodel workflowmodel = workflowmodelService.getOne(new QueryWrapper<Workflowmodel>().eq("WORKFLOWID",workFlow.getId()).eq("modelname", ModelName.DEP_APPROVE.getName()).eq("VERSION",workFlow.getVersion()));

        Map<String, String> modelMapToPerson = new HashMap<>();
        modelMapToPerson.put(workflowmodel.getId(), userId);
        AdvanceBeanVO advanceBeanVO = new AdvanceBeanVO();
        advanceBeanVO.setCurrentActivityId(r.get("data").toString());
        advanceBeanVO.setModelMapToPerson(modelMapToPerson);

        Map<String,String> map = new HashMap<>();
        map.put("name", getBusinessName());
        map.put("order", info.getOrderNumber());
        activityService.advanceCurrentActivity(advanceBeanVO, map);
        messageProvider.sendMessageAsync(messageProvider.buildSuccessMessage(user, getBusinessName(), info.getOrderNumber()));
        return R.ok("发起流程成功");
    }

    @ApiOperation(value = "批量导入申请数据")
    @RequestMapping(value = "/batchImport", method = RequestMethod.POST)
    public R create(@RequestBody ServicePublish info) {

        return R.ok("发起流程成功");
    }

    @ApiOperation(value = "申请详情")
    @ApiImplicitParam(name="id", value="申请id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public R viewDetailsApply(@LoginUser User user, @PathVariable String id) {
        ServicePublish detail = servicePublishService.getDetails(id);
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

    @ApiOperation(value = "申请详情-美亚")
    @ApiImplicitParam(name="id", value="申请id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "MY/{id}", method = RequestMethod.GET)
    public R getDetailsMY(@PathVariable String id) {
        ServicePublish detail = servicePublishService.getDetails(id);
        return R.ok(detail);
    }

    @ApiOperation(value = "修改申请信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R update(@LoginUser User user, @RequestBody ServicePublish info) throws IOException {
        servicePublishService.update(user,info);
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
                servicePublishService.deleteById(user, id);
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

    @ApiOperation(value = "后台管理页面删除-美亚")
    @ApiImplicitParam(name="id", value="申请id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/deleteMY/{id}", method = RequestMethod.GET)
    public R deleteByIdMY(@LoginUser User user, @PathVariable String id) {
        String uuid = UUIDUtil.getUUID();
        String lockKey = id.intern();
        try {
            if (lock.lock(lockKey, uuid)) {
                servicePublishService.deleteById(user, id);
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
        ServicePublish info = servicePublishService.getById(id);
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
            workflowmodel = workflowmodelService.getOne(new QueryWrapper<Workflowmodel>().eq("WORKFLOWID",flowId).eq("modelname", ModelName.RECHECK.getName()));
        }else {
            info.setStatus(ApplicationInfoStatus.INNER_REVIEW.getCode());
            workflowmodel = workflowmodelService.getOne(new QueryWrapper<Workflowmodel>().eq("WORKFLOWID",flowId).eq("modelname", ModelName.DEP_APPROVE.getName()));
        }
        modelMapToPerson.put(workflowmodel.getId(), userIds);
        AdvanceBeanVO advanceBeanVO = new AdvanceBeanVO();
        advanceBeanVO.setCurrentActivityId(firstActivity.getId());
        advanceBeanVO.setModelMapToPerson(modelMapToPerson);
        Map<String,String> map = new HashMap<>();
        map.put("name",getBusinessName());
        map.put("order",info.getOrderNumber());
        activityService.advanceCurrentActivity(advanceBeanVO,map);
        servicePublishService.updateById(info);
        messageProvider.sendMessageAsync(messageProvider.buildSuccessMessage(user, getBusinessName(), info.getOrderNumber()));
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
                ServicePublish info = servicePublishService.getByActId(activityId);
                r =  activityService.forward(activityId, userIds);
                messageProvider.sendMessageAsync(messageProvider.buildProcessingMessage(getBusinessName(),info.getOrderNumber(),userIds));
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
        ServicePublish info = servicePublishService.getById(vo.getApprove().getAppInfoId());
        Activity activity =activityService.getById(vo.getCurrentActivityId());
        if ("adviser".equals(activity.getActivitytype())){
            activityService.adviseActivity(vo.getCurrentActivityId(), approve);
            return R.ok();
        }
        Map<String,String> map = new HashMap<>();
        map.put("name",getBusinessName());
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
                messageProvider.sendMessageAsync(messageProvider.buildRejectMessage(info.getCreator(), getBusinessName()));
            }
        }
        servicePublishService.updateById(info);
        return R.ok();
    }


    @ApiOperation(value = "回退")
    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    public R reject(@LoginUser User user,
                    @RequestBody FallBackVO vo) {
        AppReviewInfo approve = vo.getApprove();
        approve.setCreator(user.getIdcard());
        ServicePublish info = servicePublishService.getById(approve.getAppInfoId());
        if(null==info){
            return R.error("未找到待办数据");
        }
        activityService.fallbackCurrentActivity(vo);
        if (ApplicationInfoStatus.IMPL.getCode().equals(info.getStatus())){
            info.setStatus(ApplicationInfoStatus.IMPL_REJECT.getCode());
        }else {
            info.setStatus(ApplicationInfoStatus.REVIEW_REJECT.getCode());
        }
        servicePublishService.updateById(info);
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
        //ServicePublish info = servicePublishService.getById(id);
        ServicePublish info = servicePublishService.getDetails(id);
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
                servicePublishService.saveImpl(user, param, activity.getModelid());
            } else {
                throw new BaseException("实施失败");
            }
        }catch (Exception e){
            throw e;
        }finally {
            lock.unlock(lockKey, uuid);
        }
        if ("1".equals(implRequest.getResult())){
            //ServiceReturnBean returnBean = getTestReturnBean(false);
            activityService.advanceCurrentActivity(activity,info.getCreator());
            messageProvider.sendMessageAsync(messageProvider.buildCompleteMessage(info.getCreator(), getBusinessName(), info.getOrderNumber()));
        }else {
            if (modelId==null||modelId.trim().equals("")) {
                return R.error(201, "回退环节ID不能为空,回退失败");
            }
            Map<String,String> map = new HashMap<>();
            map.put("name",getBusinessName());
            map.put("order",info.getOrderNumber());
            FallBackVO vo = new FallBackVO();
            vo.setCurrentActivityId(activityId);
            vo.setFallBackModelIds(modelId);
            activityService.fallbackOnApproveNotPass(vo, map);
        }
        return R.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "一键发布")
    @RequestMapping(value = "/publish/{id}/{userId}", method = RequestMethod.POST)
    public R publish(@PathVariable(value = "id") String id,@PathVariable(value = "userId") String userId) throws Exception{
        ServicePublish info = servicePublishService.getDetailsMY(id);
        String orderNum = OrderNum.gen(stringRedisTemplate, RedisKey.KEY_PUBLISH_PREFIX);
        info.setOrderNumber(orderNum);
        info.setStatus("1");
        servicePublishService.update(info,new QueryWrapper<ServicePublish>().lambda().eq(ServicePublish::getId, id));
        User user=new User();
        user.setName(info.getCreatorName());
        user.setIdcard(info.getCreator());
        create(user,info,userId);
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
                ServicePublish info = servicePublishService.getById(appVo.getApprove().getAppInfoId());
                r =  activityService.add(appVo.getApprove(),appVo.getUserIds(),appVo.getCurrentActivityId(), user);
                messageProvider.sendMessageAsync(messageProvider.buildProcessingMessage(getBusinessName(),info.getOrderNumber(),appVo.getUserIds()));
            } else {
                return R.error();
            }
        }finally {
            lock.unlock(lockKey, uuid);
        }
        return r;
    }


    @ApiOperation(value = "中止业务")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="业务id", required = true, paramType="path", dataType="String"),
    })
    @RequestMapping(value = "/termination/{id}", method = RequestMethod.POST)
    public R termination(String appInfoId){
        activityService.terminationInstanceOfWorkFlow(appInfoId);
        ServicePublish info = new ServicePublish();
        info.setId(appInfoId);
        info.setStatus(ApplicationInfoStatus.DELETE.getCode());
        servicePublishService.updateById(info);
        return R.ok();
    }


    @ApiOperation("后台管理页面分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", paramType="query", dataType="String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="query", dataType="String"),
            @ApiImplicitParam(name="status", value="状态, 1:待审核 2:待实施 3:使用中 4:已删除 5:审核驳回 6:实施驳回", defaultValue = "", paramType="query", dataType="String"),
            @ApiImplicitParam(name="userName", value="申请人", paramType="query", dataType="String"),
            @ApiImplicitParam(name="processType", value="处理人,0:全部 1:我 2:其它人", defaultValue = "0", paramType="query", dataType="String")
    })
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public R page(@LoginUser User user,
                  @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                  @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                  @RequestParam(required = false) String userName,
                  @RequestParam(required = false) String orderNumber,
                  @RequestParam(required = false) String serviceName,
                  @RequestParam(required = false) String startDate,
                  @RequestParam(required = false) String endDate,
                  @RequestParam(required = false, defaultValue = "") String status,
                  @RequestParam(required = false, defaultValue = "0") String processType) {
        if (!ApplicationInfoStatus.REVIEW.getCode().equals(status)
                && !ApplicationInfoStatus.IMPL.getCode().equals(status)) {
            processType = null; // 不是待审核/待实施状态,不能过滤处理人
        }
        IPage<ServicePublish> page = new Page<>(pageNum, pageSize);

        Map<String, Object> param = Maps.newHashMap();
        param.put("user", user);
        param.put("userName", CommonHandler.dealNameforQuery(userName));
        param.put("orderNumber",CommonHandler.dealNameforQuery(orderNumber));
        param.put("serviceName",CommonHandler.dealNameforQuery(serviceName));
        param.put("status", status);
        param.put("processType", processType);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        page = servicePublishService.getPage(user, page, param);
        return R.ok(page);
    }

    @ApiOperation("后台管理页面分页查询-美亚")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", paramType="query", dataType="String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="query", dataType="String"),
            @ApiImplicitParam(name="name", value="服务名称", paramType="query", dataType="String"),
            })
    @RequestMapping(value = "/pageFromMY", method = RequestMethod.GET)
    public R pageFromMY(@LoginUser User user,
                  @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                  @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                  @RequestParam(required = false) String name,
                  @RequestParam(required = false) String startDate,
                  @RequestParam(required = false) String endDate,
                  @RequestParam(required = false) String subType,
                        @RequestParam(required = false) String status) {
        IPage<ServicePublish> page = new Page<>(pageNum, pageSize);
        if(StringUtils.isNotEmpty(name)){
            StringBuilder sb = new StringBuilder().append("%").append(name).append("%");
            name = sb.toString();
        }
        Map<String, Object> param = Maps.newHashMap();
        param.put("user", user);
        param.put("name", name);
        if(StringUtils.isNotEmpty(status)){
            param.put("status",status);
        }
        param.put("processType", subType);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        page = servicePublishService.getPageFromMY(user, page, param,subType);
        return R.ok(page);
    }


    private Workflow getWorkFlowId(String serviceType){
        StringBuilder sb = new StringBuilder("PUBLISH_");

        if("DAAS".equals(serviceType)){
            sb.append("DAAS");
        }else if("SAAS".equals(serviceType)){
            sb.append("SAAS");
        }else if("PAAS".equals(serviceType)){
            sb.append("PAAS");
        }
        Workflow workflow = workflowService.getOne(new QueryWrapper<Workflow>().eq("DEFAULT_PROCESS", sb.toString()));
        if(workflow !=null){
            return workflow;
        }else {
            return null;
        }
    }





}

