package com.hirisun.cloud.order.service.apply.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.word.entity.params.ExcelListEntity;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.google.common.collect.Maps;
import com.hirisun.cloud.api.saas.SaasApplicationApi;
import com.hirisun.cloud.api.system.FilesApi;
import com.hirisun.cloud.api.system.SmsApi;
import com.hirisun.cloud.api.workflow.WorkflowApi;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.constant.BusinessName;
import com.hirisun.cloud.common.contains.*;
import com.hirisun.cloud.common.enumer.ModelName;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.util.ExcelUtil;
import com.hirisun.cloud.common.util.ExceptionPrintUtil;
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.common.util.WorkflowUtil;
import com.hirisun.cloud.common.vo.CommonCode;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.apply.ApplyReviewRecordVO;
import com.hirisun.cloud.model.apply.FallBackVO;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.param.FilesParam;
import com.hirisun.cloud.model.saas.vo.SaasApplicationMergeVO;
import com.hirisun.cloud.model.saas.vo.SaasApplicationVO;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workflow.WorkflowActivityVO;
import com.hirisun.cloud.model.workflow.WorkflowInstanceVO;
import com.hirisun.cloud.model.workflow.WorkflowNodeVO;
import com.hirisun.cloud.order.bean.apply.ApplyFeedback;
import com.hirisun.cloud.order.bean.apply.ApplyReviewRecord;
import com.hirisun.cloud.order.bean.epidemic.EpidemicApplication;
import com.hirisun.cloud.order.bean.reported.IaasZysb;
import com.hirisun.cloud.order.service.apply.ApplyFeedbackService;
import com.hirisun.cloud.order.service.apply.ApplyInfoService;
import com.hirisun.cloud.order.service.apply.ApplyReviewRecordService;
import com.hirisun.cloud.order.service.apply.ApplyService;
import com.hirisun.cloud.order.service.saas.SaasWorkflowService;
import com.hirisun.cloud.order.vo.ApproveVO;
import com.hirisun.cloud.order.vo.ImplRequest;
import com.hirisun.cloud.order.vo.OrderCode;
import com.hirisun.cloud.order.vo.WorkOrder;
import com.hirisun.cloud.redis.lock.DistributeLock;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ApplyServiceImpl<T extends WorkOrder> implements ApplyService<T> {

    @Autowired
    private DistributeLock lock;

    @Autowired
    private ApplyReviewRecordService applyReviewRecordService;

    @Autowired
    private ApplyFeedbackService applyFeedbackService;


    @Autowired
    private WorkflowApi workflowApi;

    @Autowired
    private FilesApi filesApi;

    @Autowired
    private ApplyInfoService applyInfoService;

    @Autowired
    private SmsApi smsApi;

    /**
     * ??????????????? ??????????????????
     * @param user
     * @param vo
     * @return
     */
    @Override
    public QueryResponseResult adviser(UserVO user, FallBackVO vo) {
        return applyInfoService.adviser(user, vo);
    }

    /**
     * ??????
     * @param user ??????
     * @param approveVO ????????????
     * @return
     */
    @Override
    public QueryResponseResult add(UserVO user, ApproveVO approveVO) {
        return applyInfoService.add(user, approveVO);
    }

    /**
     * ??????
     */
    @Override
    public QueryResponseResult forward(UserVO user, String activityId, String userIds) {
        String uuid = UUIDUtil.getUUID();
        String lockKey = activityId.intern();
        try {
            if (lock.lock(lockKey, uuid)) {
                Map<String,String> resultMap =  workflowApi.activityForward(activityId, userIds);
                if (!RequestCode.SUCCESS.getCode().equals(resultMap.get("code"))) {
                    return QueryResponseResult.fail(resultMap.get("msg"));
                }
                return QueryResponseResult.success(null);
            } else {
                return QueryResponseResult.fail("????????????,???????????????");
            }
        } finally {
            lock.unlock(lockKey, uuid);
        }
    }

    /**
     * ???????????????
     * @param order
     * @param user
     * @param id
     * @param type
     * @param userIds
     * @return
     */
    @Override
    public QueryResponseResult submit(T order,
                                        @LoginUser UserVO user,
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

        WorkflowInstanceVO instance =workflowApi.getWorkflowInstanceByBusinessId(id);
        if (null==instance){
            return QueryResponseResult.fail("?????????????????????");
        }
        WorkflowActivityVO firstActivity = workflowApi.getOneWorkflowActivityByParams(WorkflowActivityStatus.WAITING.getCode(),instance.getId());
        if (firstActivity==null) {
            throw new CustomException(OrderCode.WORKFLOW_ACTIVITY_MISSING);
        }
        order.setModifiedTime(new Date());
        if ("kx".equals(type)) {
            order.setStatus(ApplyInfoStatus.REVIEW.getCode());
        }else {
            order.setStatus(ApplyInfoStatus.INNER_REVIEW.getCode());
        }
        Map<String,String> map = new HashMap<>();
        map.put("name", order.getBusinessName());
        map.put("order",order.getOrderNumber());
        map.put("depApproveUserIds",userIds);

        Map resultMap = workflowApi.advanceActivity(firstActivity.getId(),map);
        if (!RequestCode.SUCCESS.getCode().equals(resultMap.get("code"))) {
            throw new CustomException(OrderCode.FEIGN_METHOD_ERROR);
        }
        order.updateById();
        return QueryResponseResult.success(null);
    }

    @Override
    public QueryResponseResult<FallBackVO> reject(T info,
                                                    UserVO user,
                                                    FallBackVO vo) {

        ApplyReviewRecordVO approve = vo.getApplyReviewRecord();
        approve.setCreator(user.getIdcard());
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
        info.updateById();
        return QueryResponseResult.success(null);
    }

    @Override
    public QueryResponseResult deleteById(T info,
                                          @LoginUser UserVO user) {
        String uuid = UUIDUtil.getUUID();
        String lockKey = info.getId().intern();
        try {
            if (lock.lock(lockKey, uuid)) {
                if (!Objects.equals(user.getIdcard(), info.getCreator())) {
                    throw new CustomException(OrderCode.USER_CANT_EDIT_OTHER_USER_APPLY);
                }
                // ????????????,????????????????????????
                info.setStatus( ApplyInfoStatus.DELETE.getCode());
                info.updateById();
                Map<String, String> stringStringMap = workflowApi.terminationOrder(info.getId());
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


    /**
     * ??????????????????,????????????????????????
     * @param info
     * @param user
     */
    public void workflowStart(T info,UserVO user){
        workflowApi.launchInstanceOfWorkflow(user.getIdcard(), info.getWorkFlowId(), info.getId());
        WorkflowInstanceVO instance = workflowApi.getWorkflowInstanceByBusinessId(info.getId());
        if (null==instance){
            throw new CustomException(CommonCode.FLOW_INSTANCE_NULL_ERROR);
        }
        Map<String,String> map = new HashMap<>();
        map.put("name", info.getBusinessName());
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
        smsApi.buildSuccessMessage(user.getIdcard(), info.getBusinessName(), info.getOrderNumber());
    }

    /**
     * ??????????????????
     * @param info
     * @return
     */
    public QueryResponseResult detail(T info){
        WorkflowInstanceVO instance = workflowApi.getWorkflowInstanceByBusinessId(info.getId());
        if (instance==null) {
            log.error("?????????????????????");
            return QueryResponseResult.fail("?????????????????????");
        }
        List<WorkflowNodeVO> nodeVoList = workflowApi.getWorkflowNodeAndActivitys(instance.getVersion(), instance.getWorkflowId(), instance.getId());
        List<ApplyReviewRecord> reviewList= applyReviewRecordService.list(new QueryWrapper<ApplyReviewRecord>().lambda()
                .eq(ApplyReviewRecord::getApplyId, info.getId())
                .orderByDesc(ApplyReviewRecord::getCreateTime));

        List<ApplyFeedback> feedbackList= applyFeedbackService.list(new QueryWrapper<ApplyFeedback>().lambda()
                .eq(ApplyFeedback::getAppInfoId, info.getId())
                .orderByDesc(ApplyFeedback::getCreateTime));

        Map map = new HashMap();
        map.put("bizData", info);
        map.put("nodeList", nodeVoList);
        map.put("reviewList", reviewList);
        map.put("feedbackList", feedbackList);
        return QueryResponseResult.success(map);
    }


    /**
     * ????????????????????????,??????????????????
     * @param info
     * @param user
     * @param implRequest
     */
    public void saveImpl(T info,UserVO user,ImplRequest implRequest) {
        WorkflowActivityVO activity =workflowApi.getActivityById(implRequest.getActivityId());
        if (activity==null) {
            throw new CustomException(OrderCode.WORKFLOW_ACTIVITY_MISSING);
        }
        WorkflowNodeVO curNode = workflowApi.getNodeById(activity.getNodeId());
        if (curNode == null) {
            throw new CustomException(OrderCode.WORKFLOW_NODE_MISSING);
        }
        String result = implRequest.getResult();
        String remark = implRequest.getRemark();

        ApplyReviewRecord reviewInfo = new ApplyReviewRecord();
        reviewInfo.setCreator(user.getIdcard());
        reviewInfo.setResult(Integer.parseInt(result));
        reviewInfo.setRemark(remark);
        reviewInfo.setType(ReviewType.IMPL.getCode());
        reviewInfo.setStepName(curNode.getNodeName());
        reviewInfo.setWorkflowNodeId(curNode.getId());
        reviewInfo.setApplyId(info.getId());
        applyReviewRecordService.save(reviewInfo);
        // ????????????
        filesApi.refFiles(new SubpageParam(reviewInfo.getId(),implRequest.getFileList()));
        ApplyInfoStatus status;
        if ("0".equals(result)) {
            // ????????????
            status = ApplyInfoStatus.REVIEW;
        } else {
            // ?????????????????????,???????????????????????????
            status = ApplyInfoStatus.USE;
            smsApi.buildCompleteMessage(info.getCreator(), info.getBusinessName(), info.getOrderNumber());
        }
        info.update(new UpdateWrapper<T>().lambda()
                .eq(T::getId, info.getId())
                .set(T::getStatus, status.getCode()));
    }

    /**
     * ????????????
     * @param info ????????????
     * @param user  ??????
     * @param vo  ????????????
     * @return
     */
    public QueryResponseResult approve(T info,UserVO user,FallBackVO vo){
        ApplyReviewRecordVO approve = vo.getApplyReviewRecord();
        approve.setCreator(user.getIdcard());
        WorkflowActivityVO activity = workflowApi.getActivityById(vo.getCurrentActivityId());
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
        Map<String,String> map = new HashMap<>();
        map.put("name",BusinessName.EPIDEMIC);
        map.put("order",info.getOrderNumber());
        if ("1".equals(approve.getResult())){
            WorkflowNodeVO model=null;
            Map resultMap = workflowApi.advanceActivity(vo.getCurrentActivityId(),map);
            if (resultMap.get("code") != null && resultMap.get("code").equals(RequestCode.SUCCESS.getCode())) {
                model = JSON.parseObject(resultMap.get("data").toString(),WorkflowNodeVO.class);//????????????????????????????????????????????????
            }else if(resultMap.get("code") != null && resultMap.get("code").equals(RequestCode.COMPLETE.getCode())){
                info.setStatus(ApplyInfoStatus.USE.getCode());
                return QueryResponseResult.success("finished");
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
                smsApi.buildRejectMessage(info.getCreator(), info.getBusinessName());
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
        info.updateById();
        return QueryResponseResult.success(null);
    }

    /**
     * ????????????,??????????????????
     * @param info
     * @param
     * @return
     */
    @Override
    public QueryResponseResult termination(T info,UserVO user){
        workflowApi.terminationOrder(info.getId());
        info.setStatus(ApplicationInfoStatus.DELETE.getCode());
        info.updateById();
        return QueryResponseResult.success(null);
    }

}
