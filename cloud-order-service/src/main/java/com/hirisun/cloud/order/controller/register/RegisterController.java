package com.hirisun.cloud.order.controller.register;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.api.system.SmsApi;
import com.hirisun.cloud.api.workflow.WorkflowApi;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.constant.BusinessName;
import com.hirisun.cloud.common.contains.ApplyInfoStatus;
import com.hirisun.cloud.common.contains.RequestCode;
import com.hirisun.cloud.common.contains.WorkflowActivityStatus;
import com.hirisun.cloud.common.contains.WorkflowNodeAbilityType;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.util.ExceptionPrintUtil;
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.common.util.WorkflowUtil;
import com.hirisun.cloud.common.vo.CommonCode;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.apply.ApplyReviewRecordVO;
import com.hirisun.cloud.model.apply.FallBackVO;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workflow.*;
import com.hirisun.cloud.order.bean.apply.ApplyFeedback;
import com.hirisun.cloud.order.bean.apply.ApplyReviewRecord;
import com.hirisun.cloud.order.bean.register.Register;
import com.hirisun.cloud.order.handler.CommonHandler;
import com.hirisun.cloud.order.service.apply.ApplyFeedbackService;
import com.hirisun.cloud.order.service.apply.ApplyInfoService;
import com.hirisun.cloud.order.service.apply.ApplyReviewRecordService;
import com.hirisun.cloud.order.service.register.IRegisterService;
import com.hirisun.cloud.order.vo.ApproveVO;
import com.hirisun.cloud.order.vo.OrderCode;
import com.hirisun.cloud.redis.lock.DistributeLock;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * <p>
 * SAAS服务注册表 前端控制器
 * </p>
 *
 * @author zwb
 * @since 2019-05-27
 */
@Slf4j
public abstract class RegisterController<S extends IRegisterService<T>,T extends Register> {
    @Autowired
    private DistributeLock lock;
    @Autowired
     S registerService;
    @Autowired
    private WorkflowApi workflowApi;

    @Autowired
    private SmsApi smsApi;

    @Autowired
    private ApplyReviewRecordService applyReviewRecordService;

    @Autowired
    private ApplyFeedbackService applyFeedbackService;

    @Autowired
    private ApplyInfoService applyInfoService;

    @ApiOperation(value = "新建申请")
    @PutMapping("/create")
    public QueryResponseResult<T> create(@LoginUser UserVO user, @RequestBody T info) throws IOException {
        WorkflowVO workflow = workflowApi.getWorkflowByDefaultProcess(getFlowcode());
        if(workflow==null){
            return QueryResponseResult.fail("未配置流程");
        }
        info.setWorkFlowId(workflow.getId());
        info.setWorkFlowVersion(workflow.getVersion());
        registerService.save(user,info);
        workflowApi.launchInstanceOfWorkflow(user.getIdcard(), info.getWorkFlowId(), info.getId());

        WorkflowInstanceVO instance = workflowApi.getWorkflowInstanceByBusinessId(info.getId());
        if (null==instance){
            throw new CustomException(CommonCode.FLOW_INSTANCE_NULL_ERROR);
        }
        Map<String,String> map = new HashMap<>();
        map.put("name", getBusinessName());
        map.put("order",info.getOrderNumber());
        map.put("depApproveUserIds",info.getProcessingPerson());//申请后一个流程处理人
        WorkflowActivityVO firstActivity = workflowApi.getOneWorkflowActivityByParams(WorkflowActivityStatus.WAITING.getCode(),instance.getId());
        if (firstActivity==null) {
            throw new CustomException(OrderCode.WORKFLOW_ACTIVITY_MISSING);
        }
        Map resultMap = workflowApi.advanceActivity(firstActivity.getId(),map);
        if (!RequestCode.SUCCESS.getCode().equals(resultMap.get("code"))) {
            throw new CustomException(OrderCode.FEIGN_METHOD_ERROR);
        }
        smsApi.buildSuccessMessage(user.getIdcard(), getBusinessName(), info.getOrderNumber());
        return QueryResponseResult.success("发起流程成功");
    }

    @ApiOperation(value = "修改申请信息")
    @PutMapping("/update")
    public QueryResponseResult<T> update(@LoginUser UserVO user, @RequestBody T info) throws IOException {
        registerService.update(user,info);
        return QueryResponseResult.success(null);
    }

    @ApiOperation(value = "申请详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public QueryResponseResult detail(@LoginUser UserVO user,@ApiParam(value = "申请工单id",required = true) @RequestParam String id) {

        T detail = registerService.getDetails(id);
        if (detail == null) {
            return QueryResponseResult.fail("该申请不存在");
        }
        WorkflowInstanceVO instance = workflowApi.getWorkflowInstanceByBusinessId(id);
        if (instance==null) {
            log.error("流程实例未找到");
            return QueryResponseResult.fail("流程实例未找到");
        }
        List<WorkflowNodeVO> nodeVoList = workflowApi.getWorkflowNodeAndActivitys(instance.getVersion(), instance.getWorkflowId(), instance.getId());
        List<ApplyReviewRecord> reviewList= applyReviewRecordService.list(new QueryWrapper<ApplyReviewRecord>().lambda()
                .eq(ApplyReviewRecord::getApplyId, detail.getId())
                .orderByDesc(ApplyReviewRecord::getCreateTime));

        List<ApplyFeedback> feedbackList= applyFeedbackService.list(new QueryWrapper<ApplyFeedback>().lambda()
                .eq(ApplyFeedback::getAppInfoId, detail.getId())
                .orderByDesc(ApplyFeedback::getCreateTime));

        Map map = new HashMap();
        map.put("bizData", detail);
        map.put("nodeList", nodeVoList);
        map.put("reviewList", reviewList);
        map.put("feedbackList", feedbackList);
        return QueryResponseResult.success(map);

    }


    @ApiOperation(value = "后台管理页面删除")
    @PostMapping("/delete")
    public QueryResponseResult deleteById(@LoginUser UserVO user, @ApiParam("结束日期") @RequestParam String id) {
        String uuid = UUIDUtil.getUUID();
        String lockKey = id.intern();
        try {
            if (lock.lock(lockKey, uuid)) {
                T saasRegister = registerService.getById(id);
                if (!Objects.equals(user.getIdcard(), saasRegister.getCreator())) {
                    throw new CustomException(OrderCode.USER_CANT_EDIT_OTHER_USER_APPLY);
                }
                // 逻辑删除,并设置相应的状态
                saasRegister.setStatus( ApplyInfoStatus.DELETE.getCode());
                registerService.updateById(saasRegister);
                Map<String, String> stringStringMap = workflowApi.terminationOrder(id);
                if (!RequestCode.SUCCESS.getCode().equals(stringStringMap.get("code"))) {
                    return QueryResponseResult.fail(stringStringMap.get("msg"));
                }
            } else {
                return QueryResponseResult.fail("系统繁忙,请稍后重试");
            }
        } catch (Exception e) {
            log.info("系统错误:{}", ExceptionPrintUtil.getStackTraceInfo(e));
            return QueryResponseResult.fail("系统繁忙,请稍后重试");
        } finally {
            lock.unlock(lockKey, uuid);
        }
        return QueryResponseResult.success(null);
    }

    @ApiOperation(value = "审核驳回后提交")
    @PostMapping("/submit")
    public QueryResponseResult submit(@LoginUser UserVO user,
                                      @ApiParam("申请工单id") @RequestParam String id,
                                      @ApiParam("审核类型,inner:部门内审核 kx:科信审核") @RequestParam(value = "type", defaultValue = "kx") String type,
                                      @ApiParam("审核人id,多个使用逗号分隔") @RequestParam(value = "userIds", required = false) String userIds) {
        if (StringUtils.isEmpty(id)) {
            return QueryResponseResult.fail("未找到数据!");
        }

        List<String> userIdList = null;
        if ("inner".equals(type)) {
            if (StringUtils.isEmpty(userIds)) {
                return QueryResponseResult.fail("请选择审核人!");
            } else {
                userIdList = Arrays.asList(userIds.split(","));
                if (userIdList.isEmpty()) {
                    return QueryResponseResult.fail("请选择审核人!");
                }
            }
        }
        T info = registerService.getById(id);

        WorkflowInstanceVO instance =workflowApi.getWorkflowInstanceByBusinessId(info.getId());
        if (null==instance){
            return QueryResponseResult.fail("流程实例未找到");
        }
        WorkflowActivityVO firstActivity = workflowApi.getOneWorkflowActivityByParams(WorkflowActivityStatus.WAITING.getCode(),instance.getId());
        if (firstActivity==null) {
            throw new CustomException(OrderCode.WORKFLOW_ACTIVITY_MISSING);
        }
        String flowId = instance.getWorkflowId();
        Map<String, String> modelMapToPerson = new HashMap<String, String>();
        info.setModifiedTime(new Date());
        if ("kx".equals(type)) {
            info.setStatus(ApplyInfoStatus.REVIEW.getCode());
        }else {
            info.setStatus(ApplyInfoStatus.INNER_REVIEW.getCode());
        }
        Map<String,String> map = new HashMap<>();
        map.put("name",BusinessName.PUBLISH);
        map.put("order",info.getOrderNumber());
        map.put("depApproveUserIds",userIds);

        Map resultMap = workflowApi.advanceActivity(firstActivity.getId(),map);
        if (!RequestCode.SUCCESS.getCode().equals(resultMap.get("code"))) {
            throw new CustomException(OrderCode.FEIGN_METHOD_ERROR);
        }
        registerService.updateById(info);
        return QueryResponseResult.success(null);
    }
    @ApiOperation(value = "转发")
    @RequestMapping(value = "/forward", method = RequestMethod.POST)
    public QueryResponseResult forward(@LoginUser UserVO user,
                                       @ApiParam("当前环节id") @RequestParam String activityId,
                                       @ApiParam("转发审核人id,多个使用逗号分隔") @RequestParam(value = "userIds", required = true) String userIds) {

        String uuid = UUIDUtil.getUUID();
        String lockKey = activityId.intern();
        try {
            if (lock.lock(lockKey, uuid)) {
                Map<String,String> resultMap = workflowApi.activityForward(activityId, userIds);
                if (!RequestCode.SUCCESS.getCode().equals(resultMap.get("code"))) {
                    return QueryResponseResult.fail(resultMap.get("msg"));
                }
            } else {
                return QueryResponseResult.fail("系统繁忙,请稍后重试");
            }
        } finally {
            lock.unlock(lockKey, uuid);
        }
        return QueryResponseResult.success(null);
    }


    @ApiOperation(value = "参与人意见")
    @PutMapping("/adviser")
    public QueryResponseResult<FallBackVO> adviser(@LoginUser UserVO user,
                     @RequestBody FallBackVO vo) {
        return applyInfoService.adviser(user, vo);
    }
    @ApiOperation(value = "审批")
    @PutMapping("/approve")
    public QueryResponseResult<FallBackVO> approve(@LoginUser UserVO user,
                     @RequestBody FallBackVO vo) {
        T info = registerService.getById(vo.getApplyReviewRecord().getApplyId());
        ApplyReviewRecordVO approve = vo.getApplyReviewRecord();
        approve.setCreator(user.getIdcard());
        WorkflowActivityVO activity =workflowApi.getActivityById(vo.getCurrentActivityId());
        if (activity==null) {
            throw new CustomException(OrderCode.WORKFLOW_ACTIVITY_MISSING);
        }
        if ("adviser".equals(activity.getActivityType())){
            Map<String,String> resultMap=workflowApi.adviseActivity(vo.getCurrentActivityId());
            if (resultMap.get("code") != null && resultMap.get("code").equals(RequestCode.SUCCESS.getCode())) {
                return QueryResponseResult.success(null);
            }else{
                return QueryResponseResult.fail(resultMap.get("msg"));
            }
        }
        WorkflowNodeVO curNode = workflowApi.getNodeById(activity.getNodeId());
        Map<String,String> map = new HashMap<>();
        map.put("name", getBusinessName());
        map.put("order", info.getOrderNumber());
        if ("1".equals(approve.getResult())){
            WorkflowNodeVO model=null;
            Map resultMap = workflowApi.advanceActivity(vo.getCurrentActivityId(),map);
            if (resultMap.get("code") != null && resultMap.get("code").equals(RequestCode.SUCCESS.getCode())) {
                model = JSON.parseObject(resultMap.get("data").toString(),WorkflowNodeVO.class);
                info.setStatus(ApplyInfoStatus.REVIEW.getCode());
            }else if(resultMap.get("code") != null && resultMap.get("code").equals(RequestCode.COMPLETE.getCode())){//完成,没有下级环节
                info.setStatus(ApplyInfoStatus.USE.getCode());
                registerService.saveService(info);
                smsApi.buildCompleteMessage(info.getCreator(), getBusinessName(), info.getOrderNumber());
            }else{
                return QueryResponseResult.fail(resultMap.get("msg"));
            }
            boolean isImpl= WorkflowUtil.compareNodeAbility(model.getNodeFeature(), WorkflowNodeAbilityType.IMPL.getCode());
            if (isImpl){
                info.setStatus(ApplyInfoStatus.IMPL.getCode());
            }else {
                info.setStatus(ApplyInfoStatus.REVIEW.getCode());
            }
        }else {
            String modelIds=vo.getFallBackModelIds();
            if (modelIds==null||modelIds.trim().equals("")) {
                return QueryResponseResult.fail("回退环节ID不能为空,回退失败");
            }
            // 流转到服务台复核或申请人
            WorkflowNodeVO fallbackModel = workflowApi.getNodeById(modelIds);
            boolean isApply= WorkflowUtil.compareNodeAbility(fallbackModel.getNodeFeature(), WorkflowNodeAbilityType.APPLY.getCode());
            if (isApply) {
                Map<String,String> resultMap=workflowApi.fallbackOnApproveNotPass(vo, new HashMap());
                if (resultMap.get("code") != null && !resultMap.get("code").equals(RequestCode.SUCCESS.getCode())) {
                    return QueryResponseResult.fail(resultMap.get("msg"));
                }
                //订单状态设置为 科信审核驳回
                info.setStatus(ApplyInfoStatus.REVIEW_REJECT.getCode());
                smsApi.buildRejectMessage(info.getCreator(), BusinessName.SAAS_RESOURCE);
            }else if(StringUtils.isEmpty(fallbackModel.getDefaultHandler())){
                //非申请,且无默认处理人,则使用该环节之前的处理人
                WorkflowActivityVO selectVo = new WorkflowActivityVO();
                selectVo.setInstanceId(activity.getInstanceId());
                selectVo.setNodeId(fallbackModel.getId());
                List<WorkflowActivityVO> voList = workflowApi.getActivityByObj(selectVo);
                if (CollectionUtils.isNotEmpty(voList)) {
                    StringBuffer sb = new StringBuffer();
                    List<String> userIds = voList.stream().map(WorkflowActivityVO::getHandlePersons).distinct().collect(Collectors.toList());
                    userIds.forEach(item->{
                        sb.append("," + item);
                    });
                    map.put("depApproveUserIds", sb.substring(1).toString());
                    //1.复制回退环节历史流程环节信息，设置为待办，处理人时间修改等插入流转表；2.复核后环节后的到当前环节间流转信息设置为已回退
                    Map<String,String> resultMap=workflowApi.fallbackOnApproveNotPass(vo, map);
                    if (resultMap.get("code") != null && !resultMap.get("code").equals(RequestCode.SUCCESS.getCode())) {
                        return QueryResponseResult.fail(resultMap.get("msg"));
                    }
                    //订单状态设置为 科信待审核
                    info.setStatus(ApplyInfoStatus.REVIEW.getCode());
                }
            }else {
                Map<String,String> resultMap=workflowApi.fallbackOnApproveNotPass(vo, map);
                if (resultMap.get("code") != null && !resultMap.get("code").equals(RequestCode.SUCCESS.getCode())) {
                    return QueryResponseResult.fail(resultMap.get("msg"));
                }
                info.setStatus(ApplyInfoStatus.REVIEW.getCode());
            }
        }
        //记录审核记录
        ApplyReviewRecord saveReviewRecord = new ApplyReviewRecord();
        saveReviewRecord.setRemark(approve.getRemark());
        saveReviewRecord.setResult(approve.getResult());
        saveReviewRecord.setApplyId(approve.getApplyId());
        saveReviewRecord.setCreator(user.getIdcard());
        saveReviewRecord.setStepName(curNode.getNodeName());
        saveReviewRecord.setWorkflowNodeId(curNode.getId());
        saveReviewRecord.setType(ApplyReviewRecord.TYPE_AUDIT);
        applyReviewRecordService.save(saveReviewRecord);
        registerService.updateById(info);
        return QueryResponseResult.success(null);
    }

    @ApiOperation(value = "回退")
    @PutMapping("/reject")
    public QueryResponseResult<FallBackVO> reject(@LoginUser UserVO user,
                    @RequestBody FallBackVO vo) {

        ApplyReviewRecordVO approve = vo.getApplyReviewRecord();
        approve.setCreator(user.getIdcard());
        T info = registerService.getById(approve.getApplyId());
        if(null==info){
            return QueryResponseResult.fail("未找到待办数据");
        }
        Map<String,String> rejectMsgMap=workflowApi.rejectApply(vo.getCurrentActivityId(), vo.getFallBackModelIds());
        if (!RequestCode.SUCCESS.getCode().equals(rejectMsgMap.get("code"))) {
            return QueryResponseResult.fail(rejectMsgMap.get("msg"));
        }
        if (ApplyInfoStatus.IMPL.getCode().equals(info.getStatus())){
            info.setStatus(ApplyInfoStatus.IMPL_REJECT.getCode());
        }else {
            info.setStatus(ApplyInfoStatus.REVIEW_REJECT.getCode());
        }
        registerService.updateById(info);
        return QueryResponseResult.success(null);
    }

    @ApiOperation(value = "加办")
    @PutMapping("/add")
    public QueryResponseResult review(@LoginUser UserVO user,
                                      @RequestBody ApproveVO vo) {

        return applyInfoService.add(user, vo);
    }

    @ApiOperation(value = "中止业务")
    @PostMapping("/termination")
    public QueryResponseResult termination(@ApiParam("申请工单id") String appInfoId){
        Map<String, String> stringStringMap = workflowApi.terminationOrder(appInfoId);
        if (!RequestCode.SUCCESS.getCode().equals(stringStringMap.get("code"))) {
            return QueryResponseResult.fail(stringStringMap.get("msg"));
        }
        T info = registerService.getById(appInfoId);
        info.setId(appInfoId);
        info.setStatus(ApplyInfoStatus.DELETE.getCode());
        registerService.updateById(info);
        return QueryResponseResult.success(null);
    }

    /**
     * 后台管理页面查询
     */
    @ApiOperation("后台管理页面分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public QueryResponseResult page(@LoginUser UserVO user,
                                    @ApiParam("页码") @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                    @ApiParam("每页数量") @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                                    @ApiParam("状态, 1:待审核 2:待实施 3:使用中 4:已删除 5:审核驳回") @RequestParam(required = false, defaultValue = "") String status,
                                    @ApiParam("应用名称") @RequestParam(required = false, defaultValue = "") String appName,
                                    @ApiParam("申请人") @RequestParam(required = false, defaultValue = "") String userName,
                                    @ApiParam("申请单号") @RequestParam(required = false, defaultValue = "") String orderNumber,
                                    @ApiParam("处理人,0:全部 1:我 2:其它人") @RequestParam(required = false) String processType) {
        if (!ApplyInfoStatus.REVIEW.getCode().equals(status)
                && !ApplyInfoStatus.IMPL.getCode().equals(status)) {
            processType = null; // 不是待审核/待实施状态,不能过滤处理人
        }
        IPage<T> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        Map<String, Object> param = new HashMap<>();
        param.put("user", user);
        param.put("userName", CommonHandler.dealNameforQuery(userName));
        param.put("orderNumber",CommonHandler.dealNameforQuery(orderNumber));
        param.put("status", status);
        param.put("appName", CommonHandler.dealNameforQuery(appName));
        param.put("processType",processType);
        page = registerService.getPage(user, page,param);
        return QueryResponseResult.success(page);
    }

    @ApiOperation("获取负责应用列表")
    @GetMapping("/response/page")
    public QueryResponseResult getResponsePage(@LoginUser UserVO user,
                                               @RequestParam(defaultValue = "1") Long current,
                                               @RequestParam(defaultValue = "10") Long pageSize,
                                               String appName){
        IPage<T> page = new Page<>(current,pageSize);
        page = registerService.getResponsePage(page,user,appName);
        return QueryResponseResult.success(page);
    }

    public abstract String getFlowcode();

    public abstract String getBusinessName();

}

