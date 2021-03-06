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
 * SAAS??????????????? ???????????????
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

    @ApiOperation(value = "????????????")
    @PutMapping("/create")
    public QueryResponseResult<T> create(@LoginUser UserVO user, @RequestBody T info) throws IOException {
        WorkflowVO workflow = workflowApi.getWorkflowByDefaultProcess(getFlowcode());
        if(workflow==null){
            return QueryResponseResult.fail("???????????????");
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
        map.put("depApproveUserIds",info.getProcessingPerson());//??????????????????????????????
        WorkflowActivityVO firstActivity = workflowApi.getOneWorkflowActivityByParams(WorkflowActivityStatus.WAITING.getCode(),instance.getId());
        if (firstActivity==null) {
            throw new CustomException(OrderCode.WORKFLOW_ACTIVITY_MISSING);
        }
        Map resultMap = workflowApi.advanceActivity(firstActivity.getId(),map);
        if (!RequestCode.SUCCESS.getCode().equals(resultMap.get("code"))) {
            throw new CustomException(OrderCode.FEIGN_METHOD_ERROR);
        }
        smsApi.buildSuccessMessage(user.getIdcard(), getBusinessName(), info.getOrderNumber());
        return QueryResponseResult.success("??????????????????");
    }

    @ApiOperation(value = "??????????????????")
    @PutMapping("/update")
    public QueryResponseResult<T> update(@LoginUser UserVO user, @RequestBody T info) throws IOException {
        registerService.update(user,info);
        return QueryResponseResult.success(null);
    }

    @ApiOperation(value = "????????????")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public QueryResponseResult detail(@LoginUser UserVO user,@ApiParam(value = "????????????id",required = true) @RequestParam String id) {

        T detail = registerService.getDetails(id);
        if (detail == null) {
            return QueryResponseResult.fail("??????????????????");
        }
        WorkflowInstanceVO instance = workflowApi.getWorkflowInstanceByBusinessId(id);
        if (instance==null) {
            log.error("?????????????????????");
            return QueryResponseResult.fail("?????????????????????");
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


    @ApiOperation(value = "????????????????????????")
    @PostMapping("/delete")
    public QueryResponseResult deleteById(@LoginUser UserVO user, @ApiParam("????????????") @RequestParam String id) {
        String uuid = UUIDUtil.getUUID();
        String lockKey = id.intern();
        try {
            if (lock.lock(lockKey, uuid)) {
                T saasRegister = registerService.getById(id);
                if (!Objects.equals(user.getIdcard(), saasRegister.getCreator())) {
                    throw new CustomException(OrderCode.USER_CANT_EDIT_OTHER_USER_APPLY);
                }
                // ????????????,????????????????????????
                saasRegister.setStatus( ApplyInfoStatus.DELETE.getCode());
                registerService.updateById(saasRegister);
                Map<String, String> stringStringMap = workflowApi.terminationOrder(id);
                if (!RequestCode.SUCCESS.getCode().equals(stringStringMap.get("code"))) {
                    return QueryResponseResult.fail(stringStringMap.get("msg"));
                }
            } else {
                return QueryResponseResult.fail("????????????,???????????????");
            }
        } catch (Exception e) {
            log.info("????????????:{}", ExceptionPrintUtil.getStackTraceInfo(e));
            return QueryResponseResult.fail("????????????,???????????????");
        } finally {
            lock.unlock(lockKey, uuid);
        }
        return QueryResponseResult.success(null);
    }

    @ApiOperation(value = "?????????????????????")
    @PostMapping("/submit")
    public QueryResponseResult submit(@LoginUser UserVO user,
                                      @ApiParam("????????????id") @RequestParam String id,
                                      @ApiParam("????????????,inner:??????????????? kx:????????????") @RequestParam(value = "type", defaultValue = "kx") String type,
                                      @ApiParam("?????????id,????????????????????????") @RequestParam(value = "userIds", required = false) String userIds) {
        if (StringUtils.isEmpty(id)) {
            return QueryResponseResult.fail("???????????????!");
        }

        List<String> userIdList = null;
        if ("inner".equals(type)) {
            if (StringUtils.isEmpty(userIds)) {
                return QueryResponseResult.fail("??????????????????!");
            } else {
                userIdList = Arrays.asList(userIds.split(","));
                if (userIdList.isEmpty()) {
                    return QueryResponseResult.fail("??????????????????!");
                }
            }
        }
        T info = registerService.getById(id);

        WorkflowInstanceVO instance =workflowApi.getWorkflowInstanceByBusinessId(info.getId());
        if (null==instance){
            return QueryResponseResult.fail("?????????????????????");
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
    @ApiOperation(value = "??????")
    @RequestMapping(value = "/forward", method = RequestMethod.POST)
    public QueryResponseResult forward(@LoginUser UserVO user,
                                       @ApiParam("????????????id") @RequestParam String activityId,
                                       @ApiParam("???????????????id,????????????????????????") @RequestParam(value = "userIds", required = true) String userIds) {

        String uuid = UUIDUtil.getUUID();
        String lockKey = activityId.intern();
        try {
            if (lock.lock(lockKey, uuid)) {
                Map<String,String> resultMap = workflowApi.activityForward(activityId, userIds);
                if (!RequestCode.SUCCESS.getCode().equals(resultMap.get("code"))) {
                    return QueryResponseResult.fail(resultMap.get("msg"));
                }
            } else {
                return QueryResponseResult.fail("????????????,???????????????");
            }
        } finally {
            lock.unlock(lockKey, uuid);
        }
        return QueryResponseResult.success(null);
    }


    @ApiOperation(value = "???????????????")
    @PutMapping("/adviser")
    public QueryResponseResult<FallBackVO> adviser(@LoginUser UserVO user,
                     @RequestBody FallBackVO vo) {
        return applyInfoService.adviser(user, vo);
    }
    @ApiOperation(value = "??????")
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
            }else if(resultMap.get("code") != null && resultMap.get("code").equals(RequestCode.COMPLETE.getCode())){//??????,??????????????????
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
                return QueryResponseResult.fail("????????????ID????????????,????????????");
            }
            // ????????????????????????????????????
            WorkflowNodeVO fallbackModel = workflowApi.getNodeById(modelIds);
            boolean isApply= WorkflowUtil.compareNodeAbility(fallbackModel.getNodeFeature(), WorkflowNodeAbilityType.APPLY.getCode());
            if (isApply) {
                Map<String,String> resultMap=workflowApi.fallbackOnApproveNotPass(vo, new HashMap());
                if (resultMap.get("code") != null && !resultMap.get("code").equals(RequestCode.SUCCESS.getCode())) {
                    return QueryResponseResult.fail(resultMap.get("msg"));
                }
                //????????????????????? ??????????????????
                info.setStatus(ApplyInfoStatus.REVIEW_REJECT.getCode());
                smsApi.buildRejectMessage(info.getCreator(), BusinessName.SAAS_RESOURCE);
            }else if(StringUtils.isEmpty(fallbackModel.getDefaultHandler())){
                //?????????,?????????????????????,????????????????????????????????????
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
                    //1.?????????????????????????????????????????????????????????????????????????????????????????????????????????2.?????????????????????????????????????????????????????????????????????
                    Map<String,String> resultMap=workflowApi.fallbackOnApproveNotPass(vo, map);
                    if (resultMap.get("code") != null && !resultMap.get("code").equals(RequestCode.SUCCESS.getCode())) {
                        return QueryResponseResult.fail(resultMap.get("msg"));
                    }
                    //????????????????????? ???????????????
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
        //??????????????????
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

    @ApiOperation(value = "??????")
    @PutMapping("/reject")
    public QueryResponseResult<FallBackVO> reject(@LoginUser UserVO user,
                    @RequestBody FallBackVO vo) {

        ApplyReviewRecordVO approve = vo.getApplyReviewRecord();
        approve.setCreator(user.getIdcard());
        T info = registerService.getById(approve.getApplyId());
        if(null==info){
            return QueryResponseResult.fail("?????????????????????");
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

    @ApiOperation(value = "??????")
    @PutMapping("/add")
    public QueryResponseResult review(@LoginUser UserVO user,
                                      @RequestBody ApproveVO vo) {

        return applyInfoService.add(user, vo);
    }

    @ApiOperation(value = "????????????")
    @PostMapping("/termination")
    public QueryResponseResult termination(@ApiParam("????????????id") String appInfoId){
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
     * ????????????????????????
     */
    @ApiOperation("??????????????????????????????")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public QueryResponseResult page(@LoginUser UserVO user,
                                    @ApiParam("??????") @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                    @ApiParam("????????????") @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                                    @ApiParam("??????, 1:????????? 2:????????? 3:????????? 4:????????? 5:????????????") @RequestParam(required = false, defaultValue = "") String status,
                                    @ApiParam("????????????") @RequestParam(required = false, defaultValue = "") String appName,
                                    @ApiParam("?????????") @RequestParam(required = false, defaultValue = "") String userName,
                                    @ApiParam("????????????") @RequestParam(required = false, defaultValue = "") String orderNumber,
                                    @ApiParam("?????????,0:?????? 1:??? 2:?????????") @RequestParam(required = false) String processType) {
        if (!ApplyInfoStatus.REVIEW.getCode().equals(status)
                && !ApplyInfoStatus.IMPL.getCode().equals(status)) {
            processType = null; // ???????????????/???????????????,?????????????????????
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

    @ApiOperation("????????????????????????")
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

