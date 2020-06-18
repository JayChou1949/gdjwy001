package com.upd.hwcloud.controller.backend.application;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.upd.hwcloud.bean.contains.*;
import com.upd.hwcloud.bean.dto.EcsStatistics;
import com.upd.hwcloud.bean.dto.EcsStatisticsTotal;
import com.upd.hwcloud.bean.dto.HandlerWrapper;
import com.upd.hwcloud.bean.dto.ImplRequest;
import com.upd.hwcloud.bean.entity.Log;
import com.upd.hwcloud.bean.entity.Paas;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.WorkFlow;
import com.upd.hwcloud.bean.entity.application.AppReviewInfo;
import com.upd.hwcloud.bean.entity.application.ApplicationFeedback;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.entity.application.IaasYzmyzyUser;
import com.upd.hwcloud.bean.entity.application.paas.firewall.PaasFirewallOpen;
import com.upd.hwcloud.bean.entity.application.paas.rdb.PaasRdbBase;
import com.upd.hwcloud.bean.entity.wfm.*;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.common.config.LoginUser;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.common.lock.DistributeLock;
import com.upd.hwcloud.common.utils.AreaPoliceCategoryUtils;
import com.upd.hwcloud.common.utils.IpUtil;
import com.upd.hwcloud.common.utils.UUIDUtil;
import com.upd.hwcloud.service.application.*;
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

import org.apache.commons.collections4.Put;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.upd.hwcloud.bean.contains.ApplicationInfoStatus.INNER_REVIEW;
import static com.upd.hwcloud.bean.contains.ApplicationInfoStatus.REVIEW_REJECT;

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
@RequestMapping("/newflow")
public class ApplicationWorkFlowController {

    private final static Logger log = LoggerFactory.getLogger(ApplicationWorkFlowController.class);

    @Autowired
    private ApplicationContext context;
    @Autowired
    private DistributeLock lock;
    @Autowired
    private IApplicationInfoService applicationInfoService;
    @Autowired
    private IActivityService activityService;
    @Autowired
    private IInstanceService instanceService;
    @Autowired
    private IWorkflowmodelService workflowmodelService;
    @Autowired
    private IWorkflowService workflowService;
    @Autowired
    private IApplicationFeedbackService applicationFeedbackService;
    @Autowired
    IIaasYzmyzyUserImplService iaasYzmyzyUserImplService;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    private IPaasFirewallOpenService paasFirewallOpenService;




    private static final Logger logger = LoggerFactory.getLogger(ApplicationWorkFlowController.class);

    @ApiOperation(value = "新建申请,添加到购物车")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @Transactional
    public R create(@LoginUser User user, HttpServletRequest request) throws IOException {
        String json = IOUtils.toString(request.getInputStream(), "UTF-8");
        ApplicationInfo origin = JSONObject.parseObject(json, ApplicationInfo.class);
        HandlerWrapper hw = FormNum.getHandlerWrapperByInfo(context, origin);
        ApplicationInfo info = parseApplicationInfo(json, hw.getApplicationType());
        info.setFlowNew("1");
        ResourceType resType = hw.getFormNum().getResourceType();
        //流程选择
        Workflow workflow = workflowService.chooseWorkFlow(resType,info.getAreaName(),info.getPoliceCategory(),info.getServiceTypeId(),info.getNationalSpecialProject());
        if(workflow == null){
            return R.error("流程未配置");
        }
        info.setWorkFlowId(workflow.getId());
        String workFlowId = info.getWorkFlowId();
        //保存表单数据到数据库
        create(user, hw.getFormNum(), info, hw.getHandler(),false);
        log.info("=== {} use  flow: {}===",info.getId(),workflow.getWorkflowname());
        //使用身份证号 +  流程ID + 订单ID 发起流程实例
        R r= instanceService.launchInstanceOfWorkFlow(user.getIdcard(),workFlowId,info.getId());
        return R.ok("发起流程成功");
    }

    @ApiOperation("创建桌面云订单")
    @RequestMapping(value = "/cloudDesktop/{userId}", method = RequestMethod.POST)
    @Transactional
    public R cloudDesktopOrder(@LoginUser User user, HttpServletRequest request ,@PathVariable String userId) throws IOException {
        String json = IOUtils.toString(request.getInputStream(), "UTF-8");
        ApplicationInfo origin = JSONObject.parseObject(json, ApplicationInfo.class);
        HandlerWrapper hw = FormNum.getHandlerWrapperByInfo(context, origin);
        ApplicationInfo info = parseApplicationInfo(json, hw.getApplicationType());
        info.setFlowNew("1");
        ResourceType resType = hw.getFormNum().getResourceType();
        //流程选择
        Workflow workflow = workflowService.chooseWorkFlow(resType,info.getAreaName(),info.getPoliceCategory(),info.getServiceTypeId(),info.getNationalSpecialProject());
        if(workflow == null){
            return R.error("流程未配置");
        }
        info.setWorkFlowId(workflow.getId());
        String workFlowId = info.getWorkFlowId();
        //保存表单数据到数据库
        create(user, hw.getFormNum(), info, hw.getHandler(),true);
        log.info("=== {} use  flow: {}===",info.getId(),workflow.getWorkflowname());
        //使用身份证号 +  流程ID + 订单ID 发起流程实例
        R r= instanceService.launchInstanceOfWorkFlow(user.getIdcard(),workFlowId,info.getId());

        Workflowmodel workflowmodel = workflowmodelService.getOne(new QueryWrapper<Workflowmodel>().eq("WORKFLOWID",workflow.getId()).eq("modelname", ModelName.DEP_APPROVE.getName()).eq("VERSION",workflow.getVersion()));

        Map<String, String> modelMapToPerson = new HashMap<>();
        modelMapToPerson.put(workflowmodel.getId(), userId);
        AdvanceBeanVO advanceBeanVO = new AdvanceBeanVO();
        advanceBeanVO.setCurrentActivityId(r.get("data").toString());
        advanceBeanVO.setModelMapToPerson(modelMapToPerson);

        Map<String,String> map = new HashMap<>();
        map.put("name", info.getServiceTypeName());
        map.put("order", info.getOrderNumber());
        activityService.advanceCurrentActivity(advanceBeanVO, map);
        messageProvider.sendMessageAsync(messageProvider.buildSuccessMessage(user, info.getServiceTypeName(), info.getOrderNumber()));
        return R.ok("发起流程成功");
    }

    @ApiOperation(value = "申请详情")
    @ApiImplicitParam(name="id", value="申请id", required = true, paramType="path", dataType="String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public R viewDetailsApply(@LoginUser User user,@PathVariable String id) {

        ApplicationInfo info = applicationInfoService.getById(id);
        if (info == null) {
            return R.error("该申请不存在");
        }

        HandlerWrapper hw = FormNum.getHandlerWrapperByInfo(context, info);
        IApplicationHandler handler = hw.getHandler();
        IImplHandler implHandler = hw.getImplHandler();
        //bizData数据,订单详细信息：服务列表，文件列表等
        ApplicationInfo detail = applicationInfoService.getNewFlowDetail(user, id, handler, implHandler);
        //寻找改订单流程实例
        Instance instance = instanceService.getInstanceByBusinessId(id);
        Activity activity = null;
        if (null!=instance){
            //取得流程ID
            detail.setWorkFlowId(instance.getWorkflowid());
            detail.setFlowVersion(instance.getWorkflowversion());
            //该实例处理人自己，Activitystatus为待办，activitytypetype为空的数据
            List<Activity> activitys = activityService.list(new QueryWrapper<Activity>().lambda()
                .eq(Activity::getInstanceid,instance.getId()).eq(Activity::getHandlepersonids,user.getIdcard()).eq(Activity::getActivitystatus,"待办").isNull(Activity::getActivitytype));
            if (null==activitys||activitys.size()==0){
                //若查不到，寻找该实例处理人自己，Activitystatus为待办，activitytypetype为adviser的数据
                activitys = activityService.list(new QueryWrapper<Activity>().lambda()
                        .eq(Activity::getInstanceid,instance.getId()).eq(Activity::getHandlepersonids,user.getIdcard()).eq(Activity::getActivitystatus,"待办").eq(Activity::getActivitytype,"adviser"));
            }
            //流转信息列表不为空，取第一条数据
            if (null!=activitys&&activitys.size()>0) activity = activitys.get(0);
        }
      String activityId = "";
        //流转信息不为空
       if (activity!=null) {
           activityId = activity.getId();
           //如果为activitytype为adviser，设置可以Adviser和审核
           if ("adviser".equals(activity.getActivitytype())){
               detail.setCanAdviser(true);
               detail.setCanReview(true);
           }else{
               //查找流程环节信息
               Workflowmodel workflowmodel=workflowmodelService.getById(activity.getModelid());
               if (null==workflowmodel){
                   return R.error("未找到流程环节");
               }
               //环节名
               String  modelName=workflowmodel.getModelname();
               //环节名为申请 或者 服务台复核 设置订单为可编辑状态
               if (modelName.equals(ModelName.APPLY.getName())||modelName.equals(ModelName.RECHECK.getName())) {
                   detail.setCanEdit(true);
               }
               //环节名为本单位审批 或者 服务台复核 或者 大数据办审批 设置订单可转发和审核
               if (modelName.equals(ModelName.DEP_APPROVE.getName())||modelName.equals(ModelName.RECHECK.getName())||modelName.equals(ModelName.APPROVE.getName())) {
                    detail.setCanTrans(true);
                    detail.setCanReview(true);
                    if (!modelName.equals(ModelName.DEP_APPROVE.getName())) {
                        detail.setCanAdd(true);
                    }
               }
               //环节名为本单位审批 设置可回退
               if (modelName.equals(ModelName.DEP_APPROVE.getName())) {
                   detail.setCanFall(true);
               }
               //环节名为业务办理，设置可转发可实施
               if (modelName.equals(ModelName.CARRY.getName())) {
                   //如果是关系型数据库新增账号的环节，设置可加办
                   rdbAddAccountTypeAddAble(detail);
                   detail.setCanTrans(true);
                   detail.setCanImpl(true);
               }
               //环节为反馈，设置可反馈
               if (modelName.equals(ModelName.FEEDBACK.getName())) {
                   detail.setCanFeedback(true);
               }
           }

       }
       //获取审核记录(包含实施记录)
        List<AppReviewInfo> allReviewInfo = detail.getReviewList();
        Map bean = new HashMap<>();
        //List转Map 通过FlowStepId(环节 id)分组
        if (allReviewInfo!=null){
            Map<String, List<AppReviewInfo>> reviews = allReviewInfo.stream().collect(Collectors.groupingBy(a->a.getFlowStepId()+""));
            bean.put("review",reviews);
        }

        //查找环节信息
        WorkFlowModelVo modelVo=workflowmodelService.getWorkFlowDefinition(id);
        // 反馈记录
        List<ApplicationFeedback> feedback = applicationFeedbackService.getListByAppInfoId(info.getId());
        if (modelVo!=null&&detail!=null) {
            //如果是关系型数据库服务,且申请类型为新增账号，只要本单位和业务办理环节
            if(StringUtils.equals(FormNum.PAAS_RELATIONAL_DATABASE.toString(),info.getFormNum())){
                List<PaasRdbBase> baseList = detail.getServerList();
                if(baseList.size() == 1){
                    PaasRdbBase base = baseList.get(0);
                    if(base.getApplyType().equals(RdbApplyType.ADD_ACCOUNT.getCode())){
                        //WorkFlowModelVo workFlowModelVo = new WorkFlowModelVo();
                        //dealRdbAccountModelVo(modelVo,workFlowModelVo);
                        bean.put("model",createRdbAddAccountModel(modelVo));
                    }else {
                        bean.put("model",modelVo);
                    }
                }
            }

            if(!StringUtils.equals(FormNum.PAAS_RELATIONAL_DATABASE.toString(),info.getFormNum())){
                bean.put("model",modelVo);
            }
            bean.put("bizData",detail);
            bean.put("activityId",activityId);
            bean.put("feedback",feedback);
           return R.ok(bean);
        }else {
           return R.error("未找到流程");
        }

    }

    private void rdbAddAccountTypeAddAble(ApplicationInfo detail){
        if(StringUtils.equals(detail.getFormNum().toString(),"PAAS_RELATIONAL_DATABASE")){
            List<PaasRdbBase> baseList = detail.getServerList();
            if(baseList.size() == 1){
                PaasRdbBase base =  baseList.get(0);
                if(RdbApplyType.ADD_ACCOUNT.getCode().equals(base.getApplyType())){
                    detail.setCanAdd(true);
                }
            }
        }
    }
    @ApiOperation(value = "反馈")
    @RequestMapping(value = "/feedback/{id}/{activityId}", method = RequestMethod.POST)
    public R feedback(@LoginUser User user, @PathVariable String id,@PathVariable String activityId, @RequestBody ApplicationFeedback feedback) {
        String uuid = UUIDUtil.getUUID();
        String lockKey = id.intern();
        try {
            if (lock.lock(lockKey, uuid)) {
                Activity activity = activityService.getById(activityId);
                applicationInfoService.feedback(user, id, feedback);
               return activityService.advanceCurrentActivity(activity,"");
            } else {
                throw new BaseException("反馈失败");
            }
        } finally {
            lock.unlock(lockKey, uuid);
        }
    }




    /**
     * 构造关系型数据库新增账号临时流程
     * @param oldModels 原流程模型
     * @return 关系型数据库新增账号临时流程模型
     */
    private WorkFlowModelVo createRdbAddAccountModel(WorkFlowModelVo oldModels){
        //申请
        logger.debug("oldModels -> {}",oldModels);
        WorkFlowModelVo workFlowModelVo = new WorkFlowModelVo();
        workFlowModelVo.setId(oldModels.getId());
        workFlowModelVo.setModelName(oldModels.getModelName());
        workFlowModelVo.setPreModelId(oldModels.getPreModelId());
        workFlowModelVo.setVersion(oldModels.getVersion());
        workFlowModelVo.setHandleModel(oldModels.getHandleModel());
        workFlowModelVo.setDefaultHandlePerson(oldModels.getDefaultHandlePerson());
        workFlowModelVo.setModelStatus(oldModels.getModelStatus());
        workFlowModelVo.setModelStatusCode(oldModels.getModelStatusCode());
        workFlowModelVo.setModelStatusClassNme(oldModels.getModelStatusClassNme());
        workFlowModelVo.setHandleDate(oldModels.getHandleDate());
        workFlowModelVo.setReciveDate(oldModels.getReciveDate());
        workFlowModelVo.setActivityId(oldModels.getActivityId());
        workFlowModelVo.setIsOnLine(oldModels.getIsOnLine());

        //本单位审批
        WorkFlowModelVo oldDepartment = oldModels.getNextModels().get(0);
        WorkFlowModelVo department = new WorkFlowModelVo();
        department.setId(oldDepartment.getId());
        department.setModelName(oldDepartment.getModelName());
        department.setPreModelId(oldDepartment.getPreModelId());
        department.setVersion(oldDepartment.getVersion());
        department.setHandleModel(oldDepartment.getHandleModel());
        department.setDefaultHandlePerson(oldDepartment.getDefaultHandlePerson());
        department.setModelStatus(oldDepartment.getModelStatus());
        department.setModelStatusCode(oldDepartment.getModelStatusCode());
        department.setModelStatusClassNme(oldModels.getModelStatusClassNme());
        department.setHandleDate(oldDepartment.getHandleDate());
        department.setReciveDate(oldDepartment.getReciveDate());
        department.setActivityId(oldDepartment.getActivityId());
        department.setIsOnLine(oldDepartment.getIsOnLine());

        List<WorkFlowModelVo> oldRecheckList  = oldDepartment.getNextModels();
        WorkFlowModelVo oldReck = oldRecheckList.get(0);

        List<WorkFlowModelVo> oldBigDataList = oldReck.getNextModels();
        WorkFlowModelVo oldBigData  = oldBigDataList.get(0);

        List<WorkFlowModelVo> oldImplList = oldBigData.getNextModels();
        WorkFlowModelVo oldImpl = oldImplList.get(0);
        WorkFlowModelVo impl = new WorkFlowModelVo();
        impl.setId(oldImpl.getId());
        impl.setModelName(oldImpl.getModelName());
        //注意前置环节为本单位审批
        impl.setPreModelId(oldDepartment.getId());
        impl.setVersion(oldImpl.getVersion());
        impl.setHandleModel(oldImpl.getHandleModel());
        impl.setDefaultHandlePerson(oldImpl.getDefaultHandlePerson());
        impl.setModelStatus(oldImpl.getModelStatus());
        impl.setModelStatusCode(oldImpl.getModelStatusCode());
        impl.setModelStatusClassNme(oldImpl.getModelStatusClassNme());
        impl.setHandleDate(oldImpl.getHandleDate());
        impl.setReciveDate(oldImpl.getReciveDate());
        impl.setActivityId(oldImpl.getActivityId());
        impl.setIsOnLine(oldImpl.getIsOnLine());


        List<WorkFlowModelVo> oldFkList = oldImpl.getNextModels();
        WorkFlowModelVo oldFk = oldFkList.get(0);
        WorkFlowModelVo fk = new WorkFlowModelVo();
        fk.setId(oldFk.getId());
        fk.setModelName(oldFk.getModelName());
        fk.setPreModelId(oldFk.getPreModelId());
        fk.setVersion(oldFk.getVersion());
        fk.setHandleModel(oldFk.getHandleModel());
        fk.setDefaultHandlePerson(oldFk.getDefaultHandlePerson());
        fk.setModelStatus(oldFk.getModelStatus());
        fk.setModelStatusCode(oldFk.getModelStatusCode());
        fk.setModelStatusClassNme(oldFk.getModelStatusClassNme());
        fk.setHandleDate(oldFk.getHandleDate());
        fk.setReciveDate(oldFk.getReciveDate());
        fk.setActivityId(oldFk.getActivityId());
        fk.setIsOnLine(oldFk.getIsOnLine());

        fk.setNextModels(Lists.newArrayList());
        List<WorkFlowModelVo> fkList = Lists.newArrayList();
        fkList.add(fk);

        impl.setNextModels(fkList);


        List<WorkFlowModelVo> implList = Lists.newArrayList();
        implList.add(impl);

        department.setNextModels(implList);

        List<WorkFlowModelVo> departmentList = Lists.newArrayList();
        departmentList.add(department);

        workFlowModelVo.setNextModels(departmentList);

        return workFlowModelVo;

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
            for (String bid:idList){
                activityService.terminationInstanceOfWorkFlow(bid);
            }
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

        List<ApplicationInfo> list = applicationInfoService.list(new QueryWrapper<ApplicationInfo>().lambda()
                .eq(ApplicationInfo::getStatus, ApplicationInfoStatus.SHOPPING_CART.getCode())
                .eq(ApplicationInfo::getCreator, user.getIdcard())
                .in(ApplicationInfo::getId, idList));
        if (list == null || list.isEmpty()) {
            return R.error("未找到申请数据");
        }
        Date now = new Date();

        for (ApplicationInfo info : list) {
            if ("1".equals(info.getDraft())) {
                throw new BaseException("草稿不能提交");
            }
            new Log(user.getIdcard(),"服务名称："+info.getServiceTypeName()+";申请单号："+info.getOrderNumber(),"提交申请", IpUtil.getIp()).insert();

            Instance instance =instanceService.getInstanceByBusinessId(info.getId());
            if (null==instance){
                throw new BaseException("流程实例未找到");
            }
            Activity firstActivity = activityService.getOne(new QueryWrapper<Activity>().eq("activitystatus","待办")
                    .eq("isstart",0).eq("instanceid",instance.getId()));
            String flowId = instance.getWorkflowid();
            Map<String, String> modelMapToPerson = new HashMap<String, String>();
            info.setCreateTime(now);
            info.setModifiedTime(now);
            Workflowmodel workflowmodel = new Workflowmodel();
            if ("kx".equals(type)) {
                //科信待审核
                info.setStatus(ApplicationInfoStatus.REVIEW.getCode());
                //查找名为服务台复核的环节
                workflowmodel = workflowmodelService.getOne(new QueryWrapper<Workflowmodel>().eq("WORKFLOWID",flowId).eq("modelname", ModelName.RECHECK.getName()).eq("VERSION",instance.getWorkflowversion()));

            }else {
                //部门内待审核
                info.setStatus(INNER_REVIEW.getCode());
                //环节表找该流程名为本单位审批的环节
                workflowmodel = workflowmodelService.getOne(new QueryWrapper<Workflowmodel>().eq("WORKFLOWID",flowId).eq("modelname", ModelName.DEP_APPROVE.getName()).eq("VERSION",instance.getWorkflowversion()));

            }
            //环节处理人map key:环节ID value:审核人id ，分割
            modelMapToPerson.put(workflowmodel.getId(),
                    userIds);
            AdvanceBeanVO advanceBeanVO = new AdvanceBeanVO();
            //当前要流程的数据
            advanceBeanVO.setCurrentActivityId(firstActivity.getId());
            //流程定义环节id与处理人对应关系
            advanceBeanVO.setModelMapToPerson(modelMapToPerson);
            Map<String,String> map = new HashMap<>();
            map.put("name",info.getServiceTypeName());
            map.put("order",info.getOrderNumber());
            activityService.advanceCurrentActivity(advanceBeanVO,map);
            messageProvider.sendMessageAsync(messageProvider.buildSuccessMessage(user, info.getServiceTypeName(), info.getOrderNumber()));
        }
        applicationInfoService.updateBatchById(list);

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
        ApplicationInfo info = applicationInfoService.getById(id);
            if ("1".equals(info.getDraft())) {
                throw new BaseException("草稿不能提交");
            }
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
                info.setStatus(INNER_REVIEW.getCode());
                workflowmodel = workflowmodelService.getOne(new QueryWrapper<Workflowmodel>().eq("WORKFLOWID",flowId).eq("modelname", ModelName.DEP_APPROVE.getName()).eq("VERSION",instance.getWorkflowversion()));

            }
            modelMapToPerson.put(workflowmodel.getId(),
                    userIds);
            AdvanceBeanVO advanceBeanVO = new AdvanceBeanVO();
            advanceBeanVO.setCurrentActivityId(firstActivity.getId());
            advanceBeanVO.setModelMapToPerson(modelMapToPerson);
            Map<String,String> map = new HashMap<>();
            map.put("name",info.getServiceTypeName());
            map.put("order",info.getOrderNumber());
            activityService.advanceCurrentActivity(advanceBeanVO,map);
        applicationInfoService.updateById(info);
        messageProvider.sendMessageAsync(messageProvider.buildSuccessMessage(user, info.getServiceTypeName(), info.getOrderNumber()));
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
                ApplicationInfo info = applicationInfoService.getByActId(activityId);
                r =  activityService.forward(activityId, userIds);
                messageProvider.sendMessageAsync(messageProvider.buildProcessingMessage(info.getServiceTypeName(),info.getOrderNumber(),userIds));
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
        logger.debug("Approve from request -> {}",approve);
        approve.setCreator(user.getIdcard());
        ApplicationInfo info = applicationInfoService.getById(vo.getApprove().getAppInfoId());
        Activity activity =activityService.getById(vo.getCurrentActivityId());
        if ("adviser".equals(activity.getActivitytype())){
            activityService.adviseActivity(vo.getCurrentActivityId(), approve);
            return R.ok();
        }
        Map<String,String> map = new HashMap<>();
        map.put("name",info.getServiceTypeName());
        map.put("order",info.getOrderNumber());
        if ("1".equals(approve.getResult())){
            //流转后返回的是下一环节名
            R r = activityService.advanceCurrentActivity(vo.getCurrentActivityId(), approve,map);
            Object model = r.get("data");
            if (ModelName.CARRY.getName().equals(model)){
                //下一环节名为业务办理,设置订单状态为 待实施
                info.setStatus(ApplicationInfoStatus.IMPL.getCode());
            }else {
                //否则设置订单状态为   科信待审核
                info.setStatus(ApplicationInfoStatus.REVIEW.getCode());
            }
        }else {
            String modelIds=vo.getFallBackModelIds();
            if (modelIds==null||modelIds.trim().equals("")) {
                return R.error(201, "回退环节ID不能为空,回退失败");
            }
            // 流转到服务台复核或申请人
            Workflowmodel fallbackModel = workflowmodelService.getById(modelIds);
            //回退环节为服务台复核
            if (ModelName.RECHECK.getName().equals(fallbackModel.getModelname())) {
                //1.复制回退环节历史流程环节信息，设置为待办，处理人时间修改等插入流转表；2.复核后环节后的到当前环节间流转信息设置为已回退
                activityService.fallbackOnApproveNotPass(vo, map);
                //订单状态设置为 科信待审核
                info.setStatus(ApplicationInfoStatus.REVIEW.getCode());
            } else {
                activityService.fallbackOnApproveNotPass(vo, null);
                //订单状态设置为 科信审核驳回
                info.setStatus(REVIEW_REJECT.getCode());
                messageProvider.sendMessageAsync(messageProvider.buildRejectMessage(info.getCreator(), info.getServiceTypeName()));
            }
        }
        applicationInfoService.updateById(info);
        return R.ok();
    }
    @ApiOperation(value = "回退")
    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    public R reject(@LoginUser User user,
                     @RequestBody FallBackVO vo) {

        AppReviewInfo approve = vo.getApprove();
        approve.setCreator(user.getIdcard());
        ApplicationInfo info = applicationInfoService.getById(approve.getAppInfoId());
        if(null==info){
            return R.error("未找到待办数据");
        }
        activityService.fallbackCurrentActivity(vo);
        if (ApplicationInfoStatus.IMPL.getCode().equals(info.getStatus())){
            info.setStatus(ApplicationInfoStatus.IMPL_REJECT.getCode());
        }else {
            info.setStatus(REVIEW_REJECT.getCode());
        }
        applicationInfoService.updateById(info);
        return R.ok();
    }
    @ApiOperation(value = "业务办理")
    @ApiImplicitParams({
            @ApiImplicitParam(name="activityId", value="当前环节id", required = true, paramType="path", dataType="String"),
    })
    @RequestMapping(value = "/impl/{id}/{activityId}/{modelId}", method = RequestMethod.POST)
    public R impl(@LoginUser User user,
                  @PathVariable(value = "id") String id, @PathVariable(value = "modelId") String modelId, @PathVariable(value = "activityId") String activityId, HttpServletRequest request) throws Exception {
        ApplicationInfo info = applicationInfoService.getById(id);
        Activity activity =activityService.getById(activityId);
        HandlerWrapper hw = FormNum.getHandlerWrapperByInfo(context, info);
        String json = IOUtils.toString(request.getInputStream(), "UTF-8");
        ImplRequest implRequest = parseImplRequest(json, hw.getImplType());
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
                //服务审核信息表 自动订购 实施表
                applicationInfoService.saveImpl(user, param, hw.getImplHandler(),activity.getModelid());
                if ("1".equals(implRequest.getResult())&& hw.getImplHandler() instanceof IIaasYzmyzyUserService) {
                    ImplRequest<IaasYzmyzyUser> implreq = implRequest;
                    List<IaasYzmyzyUser> impls = implreq.getImplServerList();
                    iaasYzmyzyUserImplService.saveUserList(impls,info.getAppName());
                }
                //防火墙零时开通时间设置
                if("1".equals(implRequest.getResult()) && StringUtils.equals(FormNum.PAAS_FIREWALL_OPEN.toString(),info.getFormNum())){
                    PaasFirewallOpen firewall = paasFirewallOpenService.getOne(new QueryWrapper<PaasFirewallOpen>().lambda().eq(PaasFirewallOpen::getAppInfoId,id));
                    if(firewall != null){
                        if(firewall.getOpeningHours() == 1){
                            logger.debug("防火墙零时开通");
                            LocalDateTime now = LocalDateTime.now();
                            LocalDateTime endDateTime = now.plusMonths(3);
                            try{
                                Date startTime = localDateTime2Date(now);
                                Date endTime = localDateTime2Date(endDateTime);
                                paasFirewallOpenService.update(new PaasFirewallOpen(),new UpdateWrapper<PaasFirewallOpen>().lambda()
                                        .eq(PaasFirewallOpen::getAppInfoId,id)
                                        .set(PaasFirewallOpen::getStartTime,startTime)
                                        .set(PaasFirewallOpen::getEndTime,endTime));
                            }catch (Exception e){
                                logger.error("Fire Wall Time Set fail!");
                            }
                        }
                    }
                }
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
        }else {
            if (modelId==null||modelId.trim().equals("")) {
                return R.error(201, "回退环节ID不能为空,回退失败");
            }
            Map<String,String> map = new HashMap<>();
            map.put("name",info.getServiceTypeName());
            map.put("order",info.getOrderNumber());
            FallBackVO vo = new FallBackVO();
            vo.setCurrentActivityId(activityId);
            vo.setFallBackModelIds(modelId);
            activityService.fallbackOnApproveNotPass(vo, map);
        }

        return R.ok();
    }

    private Date localDateTime2Date(LocalDateTime localDateTime){
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());

    }
    @ApiOperation(value = "加办")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public R review(@LoginUser User user, @RequestBody ApproveVo appVo) {

        String uuid = UUIDUtil.getUUID();
        String lockKey = appVo.getCurrentActivityId().intern();
        R r = R.error();
        try {
            if (lock.lock(lockKey, uuid)) {
                ApplicationInfo info = applicationInfoService.getById(appVo.getApprove().getAppInfoId());
              r =  activityService.add(appVo.getApprove(),appVo.getUserIds(),appVo.getCurrentActivityId(), user);
                messageProvider.sendMessageAsync(messageProvider.buildProcessingMessage(info.getServiceTypeName(),info.getOrderNumber(),appVo.getUserIds()));
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
        ApplicationInfo info = new ApplicationInfo();
        info.setId(appInfoId);
        info.setStatus(ApplicationInfoStatus.DELETE.getCode());
        applicationInfoService.updateById(info);
        return R.ok();
    }

    @ApiOperation(value = "待办数量")
    @RequestMapping(value = "/todo", method = RequestMethod.GET)
    public R todo(@LoginUser User user) {
        Map<String, Integer> todo = applicationInfoService.newTodo(user);
        return R.ok(todo);
    }

    /**
     * 对运维平台开放的接口（需要单点登录后使用）
     * @param idCard
     * @return
     */
    @ApiOperation(value = "待办数量")
    @RequestMapping(value = "/todo/{idCard}", method = RequestMethod.GET)
    public R todo(@PathVariable String idCard) {
        if(StringUtils.isBlank(idCard)){
            return R.error("idcard is empty");
        }
        User user = new User();
        user.setIdcard(idCard);
        Map<String, Integer> todo = applicationInfoService.newTodo(user);
        return R.ok(todo);
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
            @ApiImplicitParam(name="processType", value="处理人,0:全部 1:我 2:其它人", defaultValue = "0", paramType="query", dataType="String"),
            @ApiImplicitParam(name="orderNumber", value="订单号", paramType="query", dataType="String"),
            @ApiImplicitParam(name="serviceName", value="申请服务", paramType="query", dataType="String"),
            @ApiImplicitParam(name="appName", value="应用名", paramType="query", dataType="String")
    })
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public R page(@LoginUser User user,
                  @RequestParam(required = false, defaultValue = "1") Long resourceType,
                  @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                  @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                  @RequestParam(required = false) String userName,
                  @RequestParam(required = false) String startDate,
                  @RequestParam(required = false) String endDate,
                  @RequestParam(required = false, defaultValue = "") String status,
                  @RequestParam(required = false, defaultValue = "0") String processType,
                  @RequestParam(required = false) String orderNumber,
                  @RequestParam(required = false) String appName,
                  @RequestParam(required = false) String serviceName) {
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

        Map<String, Object> param = new HashMap<>();
        param.put("user", user);
        param.put("userName", CommonHandler.dealNameforQuery(userName));
        param.put("status", status);
        param.put("orderNumber",CommonHandler.dealNameforQuery(orderNumber));
        param.put("appName",CommonHandler.dealNameforQuery(appName));
        param.put("resourceType", resourceType);
        param.put("serviceName",CommonHandler.dealNameforQuery(serviceName));
        param.put("processType", processType);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        if(StringUtils.isBlank(serviceName)){
            page = applicationInfoService.getFlowPage(user, page, param);
        }else {
            if(resourceType.equals(ResourceType.PAAS.getCode()) || resourceType.equals(ResourceType.IAAS.getCode())){
                page = applicationInfoService.getFlowPage(user,page,param);
            }else{
                page = getPageHandler(resourceType,user,page,param);
            }
        }
        return R.ok(page);
    }

    /**
     * 根据类型，调用不同的分页
     * @param resourceType
     * @param user
     * @param page
     * @param param
     * @return
     */
    private IPage<ApplicationInfo> getPageHandler(Long resourceType,User user,IPage<ApplicationInfo> page,Map<String,Object> param){
        if(resourceType.equals(ResourceType.PAAS.getCode()) || resourceType.equals(ResourceType.IAAS.getCode())){
            page = applicationInfoService.getFlowPage(user,page,param);
        }else{
            if(resourceType.equals(ResourceType.DAAS.getCode())){
                page = applicationInfoService.getFlowPageOfDaas(user, page, param);
            }else if(resourceType.equals(ResourceType.SAAS_SERVICE.getCode())){
                page = applicationInfoService.getFlowPageOfSaasService(user,page,param);
            }else{
                throw  new BaseException("未知类型");
            }
        }
        return page;
    }

    private void create(User user, FormNum formNum,
                            ApplicationInfo info, IApplicationHandler handler,boolean special) {
        info.setResourceType(formNum.getResourceType().getCode());
        info.setFormNum(formNum.name());
        info.setDraft("0"); // 不是草稿
        applicationInfoService.save(user, info, handler,special);
    }

    @ApiOperation(value = "子菜单待办数量")
    @RequestMapping(value = "/clhidTodo", method = RequestMethod.GET)
    public R childTodo(@LoginUser User user,String resourceType) {

        Map<String, Integer> todo = applicationInfoService.newTodo(user);
        return R.ok(todo);
    }


    private static <S> ImplRequest<S> parseImplRequest(String json, Class type) {
        return JSON.parseObject(json,
                new TypeReference<ImplRequest<S>>(type) {});
    }


    private static <S> ApplicationInfo<S, Object> parseApplicationInfo(String json, Class type) {
        return JSON.parseObject(json,
                new TypeReference<ApplicationInfo<S, Object>>(type) {});
    }
    @ApiOperation(value = "老数据对接")
    @RequestMapping(value = "/oldData", method = RequestMethod.GET)
    public R oldData(String resourceType) {
        int rt = 4;
        if ("IAAS".equals(resourceType)){
            rt =1;
        }else {
            return R.error(resourceType+"---");
        }
        List<ApplicationInfo> todo = applicationInfoService.list(new QueryWrapper<ApplicationInfo>().lambda()
                .isNull(ApplicationInfo::getFlowNew).eq(ApplicationInfo::getResourceType,rt).ne(ApplicationInfo::getStatus,"-1"));
        Workflow workflow = workflowService.getOne(new QueryWrapper<Workflow>().eq("DEFAULT_PROCESS",resourceType));
        if (workflow==null){
            return R.error("未配置流程");
        }
        // 得到流程定义的开始环节
        Workflowmodel workFlowModel = workflowmodelService.getOne(new QueryWrapper<Workflowmodel>().eq("workflowid",workflow.getId()).eq("isstart",0));
        for (ApplicationInfo info:todo){
            ApplicationInfoStatus curStatus = ApplicationInfoStatus.codeOf(info.getStatus());
            info.setFlowNew("1");
            applicationInfoService.updateById(info);

            // 办理进度
            int pro = 0;
            switch (curStatus) {
                case INNER_REVIEW:
                   pro=2;
                    break;
                case REVIEW:
                case ADD:
                case FORWARD:
                    pro=3;
                    break;
                case IMPL:
                    pro=5;
                    break;
                case USE:
                    pro=7;
                    int count = applicationFeedbackService.getCountByAppInfoId(info.getId());
                    if (count <= 0) {
                        pro=6;
                    }
                    break;
                case SHOPPING_CART:
                case INNER_REJECT:
                case REVIEW_REJECT:
                case IMPL_REJECT:
                    pro=1;
                    break;
            }
            // 生成实例的ID
            String insId = UUIDUtil.getUUID();
            // 创建流程实例对象
            Instance instance = new Instance();
            instance.setId(insId);
            instance.setCreateperson(info.getCreator());
            instance.setCreatetime(new Date());
            instance.setInstancestatus("办理中");
            if (pro==7){
                instance.setInstancestatus("已完成");
            }
            instance.setWorkflowid(workflow.getId());
            instance.setBusinessid(info.getId());
            // 保存流程实例
            instanceService.save(instance);
            Workflowmodel nextModel = workFlowModel;
            for (int i=0;i<pro;i++) {
                if (nextModel==null) continue;
                // 创建开始环节信息
                Activity firstActivity = new Activity();
                firstActivity.setCreatepersonid(info.getCreator());
                if (i>1&i<5){
                firstActivity.setHandlepersonids(nextModel.getDefaulthandleperson());
                }else if (i==0||i==5){
                    firstActivity.setHandlepersonids(info.getCreator());
                }else if (i==1){
                    firstActivity.setHandlepersonids(applicationInfoService.getRePerson(info.getId()));
                }
                if (i==pro-1) {
                    firstActivity.setActivitystatus("待办");
                } else {
                    firstActivity.setActivitystatus("已提交");
                }
                firstActivity.setCreatetime(new Date());
                firstActivity.setInstanceid(insId);
                firstActivity.setModelid(nextModel.getId());
                if (i==0){
                firstActivity.setIsstart(0);}else {
                    firstActivity.setIsstart(1);
                }
                firstActivity.setRecivetime(new Date());
                String actId = UUIDUtil.getUUID();
                firstActivity.setId(actId);
                // 创建环节
                activityService.save(firstActivity);
                 nextModel = workflowmodelService.getSubModel(nextModel.getId());

            }
        }
        return R.ok(todo.size());
    }

    @ApiOperation(value = "根据应用名称生成表单的地区和警种")
    @RequestMapping(value = "/genAreaPoliceCategory", method = RequestMethod.POST)
    public R genAreaPoliceCategory(String appName) {
        List<ApplicationInfo> list = applicationInfoService.list(new QueryWrapper<>());
       // list.forEach(info -> {
            String areaName = AreaPoliceCategoryUtils.getAreaName(appName);
            String policeCategory = AreaPoliceCategoryUtils.getPoliceCategory(appName);
            if(StringUtils.equals(policeCategory,"情报")){
                policeCategory = "情报指挥";
            }
            if(StringUtils.equals(policeCategory,"督察")){
                policeCategory = "警务督察";
            }
            Map<String,String> map = new HashMap<>();
            if(StringUtils.isNotEmpty(areaName)||StringUtils.isNotEmpty(policeCategory)){
                map.put("areaName",areaName);
                map.put("policeCategory",policeCategory);
                return R.ok(map);
            }else{
                return R.error("未匹配到所属地区和用户");
            }
            //user.setBelongPoliceType(policeCategory);
            //user.setBelongArea(areaName);
            /*applicationInfoService.update(new ApplicationInfo(),
                    new UpdateWrapper<ApplicationInfo>().lambda()
                            .eq(ApplicationInfo::getId, info.getId())
                            .set(ApplicationInfo::getAreaName, areaName)
                            .set(ApplicationInfo::getHwPoliceCategory, policeCategory));*/

       // });

    }

    @ApiOperation(value = "地图服务地图类型")
    @RequestMapping(value = "/getMapType", method = RequestMethod.GET)
    public R mapType() {

        return R.ok(MapType.getMap());
    }

    @ApiOperation(value = "近一个月ECS申请(iaas三级页面)")
    @ApiImplicitParams({
            @ApiImplicitParam(name="type", value="查询类型 area:地区 policeCategory:警种", paramType="path", dataType="String"),
            @ApiImplicitParam(name="name", value="警种或地区名称,如:科信,揭阳", paramType="path", dataType="String"),
            @ApiImplicitParam(name="pageNum", value="页码", defaultValue = "1", paramType="query", dataType="String"),
            @ApiImplicitParam(name="pageSize", value="一页的数据量", defaultValue = "20", paramType="query", dataType="String")
    })
    @RequestMapping(value = "/ecsRecent/page/{type}/{name}", method = RequestMethod.GET)
    public R ecsRecent(@PathVariable String type, @PathVariable String name,
                       @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                       @RequestParam(required = false, defaultValue = "20") Integer pageSize) {
        IPage<EcsStatistics> page = new Page<>(pageNum, pageSize);
        //科信合并为大数据办
        if ("大数据办".equals(name)) {
            page = applicationInfoService.ecsList4Recent(page, type, name);
        } else {
            page = applicationInfoService.ecsList4Recent(page, type, name);
        }
        return R.ok(page);
    }

    @ApiOperation(value = "近一个月ECS申请总数(iaas三级页面)")
    @RequestMapping(value = "/ecsRecent/total/{type}/{name}", method = RequestMethod.GET)
    public R ecsRecentTotal(@PathVariable String type, @PathVariable String name) {
        EcsStatisticsTotal total = applicationInfoService.ecsStatistics4Recent(type, name);
        return R.ok(total);
    }

    @ApiOperation(value = "已发放ECS资源(iaas三级页面)")
    @RequestMapping(value = "/ecsApplied/page/{type}/{name}", method = RequestMethod.GET)
    public R ecsApplied(@PathVariable String type, @PathVariable String name,
                        @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                        @RequestParam(required = false, defaultValue = "20") Integer pageSize) {
        IPage<EcsStatistics> page = new Page<>(pageNum, pageSize);
        if ("大数据办".equals(name)) {
            page = applicationInfoService.ecsList4Applied(page, type, "科信");
        } else {
            page = applicationInfoService.ecsList4Applied(page, type, name);
        }
        return R.ok(page);
    }

    @ApiOperation(value = "已发放ECS资源总数(iaas三级页面)")
    @RequestMapping(value = "/ecsApplied/total/{type}/{name}", method = RequestMethod.GET)
    public R ecsAppliedTotal(@PathVariable String type, @PathVariable String name) {
        EcsStatisticsTotal total = applicationInfoService.ecsStatistics4Applied(type, name);
        return R.ok(total);
    }

    @ApiOperation(value = "待审批ECS资源(iaas三级页面)")
    @RequestMapping(value = "/ecsReview/page/{type}/{name}", method = RequestMethod.GET)
    public R ecsReview(@PathVariable String type, @PathVariable String name,
                       @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                       @RequestParam(required = false, defaultValue = "20") Integer pageSize) {
        IPage<EcsStatistics> page = new Page<>(pageNum, pageSize);
        page = applicationInfoService.ecsList4Review(page, type, name);
        return R.ok(page);
    }

    @ApiOperation(value = "待审批ECS资源总数(iaas三级页面)")
    @RequestMapping(value = "/ecsReview/total/{type}/{name}", method = RequestMethod.GET)
    public R ecsReviewTotal(@PathVariable String type, @PathVariable String name) {
        EcsStatisticsTotal total = applicationInfoService.ecsStatistics4Review(type, name);
        return R.ok(total);
    }


}

