package com.hirisun.cloud.order.service.apply.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.battcn.boot.swagger.model.Order;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.hirisun.cloud.api.system.SmsApi;
import com.hirisun.cloud.api.user.UserApi;
import com.hirisun.cloud.api.workflow.WorkflowApi;
import com.hirisun.cloud.common.constant.BusinessName;
import com.hirisun.cloud.common.contains.ApplyInfoStatus;
import com.hirisun.cloud.common.contains.WorkflowNodeAbilityType;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.util.WorkflowUtil;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.apply.ApplyFeedbackVO;
import com.hirisun.cloud.model.apply.ApplyReviewRecordVO;
import com.hirisun.cloud.model.apply.FallBackVO;
import com.hirisun.cloud.model.service.AppReviewInfoVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workflow.WorkflowActivityVO;
import com.hirisun.cloud.model.workflow.WorkflowInstanceVO;
import com.hirisun.cloud.model.workflow.WorkflowNodeVO;
import com.hirisun.cloud.order.bean.apply.ApplyFeedback;
import com.hirisun.cloud.order.bean.apply.ApplyInfo;
import com.hirisun.cloud.order.bean.apply.ApplyReviewRecord;
import com.hirisun.cloud.order.continer.IImplHandler;
import com.hirisun.cloud.order.mapper.apply.ApplyInfoMapper;
import com.hirisun.cloud.order.service.apply.ApplyFeedbackService;
import com.hirisun.cloud.order.service.apply.ApplyInfoService;
import com.hirisun.cloud.order.service.apply.ApplyReviewRecordService;
import com.hirisun.cloud.order.vo.ApproveVO;
import com.hirisun.cloud.order.vo.OrderCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * <p>
 * 申请信息表 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-08
 */
@Slf4j
@Service
public class ApplyInfoServiceImpl extends ServiceImpl<ApplyInfoMapper, ApplyInfo> implements ApplyInfoService {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private ApplyInfoService applyInfoService;

    @Autowired
    private ApplyReviewRecordService applyReviewRecordService;

    @Autowired
    private ApplyFeedbackService applyFeedbackService;

    @Autowired
    private ApplyInfoMapper applyInfoMapper;

    @Autowired
    private WorkflowApi workflowApi;

    @Autowired
    private UserApi userApi;

    @Autowired
    private SmsApi smsApi;

    /**
     * 分页查询工单审批记录
     * 拼装工单当前处理人
     */
    @Override
    public Page<ApplyInfo> getPage(Page<ApplyInfo> page, UserVO user, Map map) {
        page=applyInfoMapper.getPage(page,user,map);
        if (CollectionUtils.isNotEmpty(page.getRecords())) {
            List<ApplyInfo> applyInfoList = page.getRecords();
            curHandlerPerson(applyInfoList,user);
        }
        return page;
    }

    @Override
    public QueryResponseResult<ApplyInfo> detail(String id) {
        /**
         * 1.查询申请工单
         * 2.查询对应的流程实例
         * 3.查询流程实例对应的流转信息
         * 4.查询审批记录
         * 5.查询反馈记录
         */
        ApplyInfo applyInfo = applyInfoService.getById(id);
        if (applyInfo == null) {
            log.error("申请单不存在");
            return QueryResponseResult.fail("申请单不存在");
        }
        WorkflowInstanceVO instance = workflowApi.getWorkflowInstanceByBusinessId(applyInfo.getId());
        if (instance==null) {
            log.error("流程实例未找到");
            return QueryResponseResult.fail("流程实例未找到");
        }
        List<WorkflowNodeVO> nodeVoList = workflowApi.getWorkflowNodeAndActivitys(instance.getVersion(), instance.getWorkflowId(), instance.getId());
        List<ApplyReviewRecord> reviewList= applyReviewRecordService.list(new QueryWrapper<ApplyReviewRecord>().lambda()
                .eq(ApplyReviewRecord::getApplyId, applyInfo.getId())
                .orderByDesc(ApplyReviewRecord::getCreateTime));

        List<ApplyFeedback> feedbackList= applyFeedbackService.list(new QueryWrapper<ApplyFeedback>().lambda()
                .eq(ApplyFeedback::getAppInfoId, applyInfo.getId())
                .orderByDesc(ApplyFeedback::getCreateTime));

        Map map = new HashMap();
        map.put("applyInfo", applyInfo);
        map.put("instance", instance);
        map.put("nodeList", nodeVoList);
        map.put("reviewList", reviewList);
        map.put("feedbackList", feedbackList);
        return QueryResponseResult.success(map);
    }

    /**
     *
     * @param applyInfoList
     * @param user
     */
    public  void curHandlerPerson(List<ApplyInfo> applyInfoList, UserVO user){
        List<String> instanceId = applyInfoList.stream().map(ApplyInfo::getInstanceId).distinct().collect(Collectors.toList());
        //实例Id到处理人身份证的集合(单表查询)
        Map<String,String> instance2IdCardsMap = workflowApi.instanceToHandleIdCards(instanceId);
        List<String> handlePeronIdsList = Lists.newArrayList();
        instance2IdCardsMap.forEach((k,v)->{
            if(StringUtils.isNotBlank(v)){
                handlePeronIdsList.add(v);
            }
        });
        //获取身份证号到名字的Map
        Map<String,String> idCard2NameMap = idCardsNameMap(handlePeronIdsList);
        //获取实例-待办处理人身份证号Map
        for(ApplyInfo record:applyInfoList){
            if(instance2IdCardsMap.size()!=0){
                if(instance2IdCardsMap.containsKey(record.getInstanceId()) && instance2IdCardsMap.get(record.getInstanceId()) != null){
                    record.setProcessingPerson(instance2IdCardsMap.get(record.getInstanceId()));
                }
            }
        }
        for (ApplyInfo record : applyInfoList) {
            if(idCard2NameMap != null){
                //身份证号集合字符串替换为名字集合字符串
                convertIdCardToName(idCard2NameMap,record);
            }
            ApplyInfoStatus applyInfoStatus = ApplyInfoStatus.codeOf(record.getStatus());
            // 判断是否能删除
            if (applyInfoStatus != ApplyInfoStatus.DELETE
                    && Objects.equals(user.getIdCard(), record.getCreator())) {
                record.setCanDelete(true);
            }
        }
    }

    /**
     * 获取身份证号与名字关联的Map
     * @param idCardsList 身份证号集合 {"5110022522,545451515","45454551515,454554"}
     * @return  {"5110022522":"jack"}
     */
    public Map<String,String> idCardsNameMap(List<String> idCardsList){
        List<String> idCardElementList = Lists.newArrayList();
        idCardsList.forEach(idCards->{
            if(StringUtils.isNotEmpty(idCards)){
                List<String> idCardList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(idCards);
                if(CollectionUtils.isNotEmpty(idCardList)){
                    idCardElementList.addAll(idCardList);
                }
            }
        });
        if(CollectionUtils.isNotEmpty(idCardElementList)){
            String userListStr = userApi.getUserByIdCardList(idCardElementList);
            if (StringUtils.isEmpty(userListStr)) {
                throw new CustomException(OrderCode.USER_SELECT_ERROR);
            }
            List<UserVO> userList = JSON.parseArray(userListStr, UserVO.class);
            return userList.stream().collect(Collectors.toMap(UserVO::getIdCard,UserVO::getName));
        }
        return null;
    }

    /**
     * 身份证号转名字
     * @param idCardToNameMap
     * @param record
     */
    public void convertIdCardToName(Map<String,String> idCardToNameMap,ApplyInfo record){
        if(idCardToNameMap != null){
            if(StringUtils.isNotBlank(record.getProcessingPerson())){
                List<String> nameList = Lists.newArrayList();
                List<String> idCardList = Splitter.on(",")
                        .trimResults().omitEmptyStrings().splitToList(record.getProcessingPerson());

                idCardList.forEach(idCard->{
                    if(idCardToNameMap.containsKey(idCard)){
                        nameList.add(idCardToNameMap.get(idCard));
                    }
                });

                String names = Joiner.on(",").skipNulls().join(nameList);
                record.setProcessingPerson(names);
            }
        }
    }
    /**
     * 逻辑删除申请审批工单
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void deleteById(UserVO user, String id) {
        ApplyInfo info = this.getById(id);
        if (info == null) {
            throw new CustomException(OrderCode.APPLY_MISSING);
        }
        if (!Objects.equals(user.getIdCard(), info.getCreator())) {
            throw new CustomException(OrderCode.USER_CANT_EDIT_OTHER_USER_APPLY);
        }
        // 逻辑删除,并设置相应的状态
        this.update(new ApplyInfo(), new UpdateWrapper<ApplyInfo>().lambda()
                .eq(ApplyInfo::getId, id)
                .set(ApplyInfo::getStatus, ApplyInfoStatus.DELETE.getCode()));
//        workflowApi.terminationInstanceOfWorkFlow(id);
    }

    /**
     * 工单审批批准流转
     * @param user
     * @param vo
     * @return
     */
    @Override
    public QueryResponseResult approve(UserVO user, FallBackVO vo) {

        ApplyReviewRecordVO approve = vo.getApplyReviewRecord();
        log.debug("Approve from request -> {}",approve);
        ApplyInfo info = applyInfoService.getById(vo.getApplyReviewRecord().getApplyId());
        WorkflowActivityVO activity =workflowApi.getActivityById(vo.getCurrentActivityId());

        if (activity==null) {
            throw new CustomException(OrderCode.WORKFLOW_ACTIVITY_MISSING);
        }
        if ("adviser".equals(activity.getActivityType())){
            Map<String,String> resultMap=workflowApi.adviseActivity(vo.getCurrentActivityId());
            if (resultMap.get("code") != null && resultMap.get("code").equals("200")) {
                approve.setStepName(resultMap.get("nodeName"));
                approve.setWorkflowNodeId(resultMap.get("nodeId"));
                approve.setType(resultMap.get("type"));
                ApplyReviewRecord reviewRecord=reviewViewObjectConvert(approve);
                if (StringUtils.isEmpty(approve.getId())) {
                    applyReviewRecordService.save(reviewRecord);
                }
            }else{
                return QueryResponseResult.fail(resultMap.get("msg"));
            }
            return QueryResponseResult.success(null);
        }
        WorkflowNodeVO curNode = workflowApi.getNodeById(activity.getNodeId());


        Map<String,String> map = new HashMap<>();
        map.put("name",info.getServiceTypeName());
        map.put("order",info.getOrderNumber());
        if (approve.getResult()!=null&&approve.getResult().equals(1)){
            //流转后返回的是下一环节名，此处已将数据处理，处理环节审批
            //处理review信息
            Map resultMap = workflowApi.advanceActivity(vo.getCurrentActivityId(),map);
            //r数据格式：
            // {code: 200,msg: "操作成功",data:"大数据办审批"}
            WorkflowNodeVO model=null;
            try {
                if (resultMap.get("code") != null && resultMap.get("code").equals("200")) {
                    model = JSON.parseObject(resultMap.get("data").toString(),WorkflowNodeVO.class);//此处转换失败，则直接抛方法内错误
                }else if(resultMap.get("code") != null && resultMap.get("code").equals("201")){
                    return QueryResponseResult.success(null);
                }else{
                    return QueryResponseResult.fail(resultMap.get("msg"));
                }
            } catch (Exception e) {
                throw new CustomException(OrderCode.WORKFLOW_MISSING);
            }
            //检查环节名，是否是业务办理，
            if (model.getNodeFeature().indexOf("3")>=0){
                //下一环节名为业务办理,设置订单状态为 待实施
                info.setStatus(ApplyInfoStatus.IMPL.getCode());
            }else {
                //否则设置订单状态为   科信待审核
                info.setStatus(ApplyInfoStatus.REVIEW.getCode());
            }
        }else {//驳回,如果到达第一个动态环节后，驳回都回到第一个动态环节，如果是第一个动态环节，驳回到申请环节
            String modelIds=vo.getFallBackModelIds();
            if (modelIds==null||modelIds.trim().equals("")) {
                log.error("回退环节ID不能为空,回退失败");
                throw new CustomException(OrderCode.FALLBACK_ID_NOT_NULL);
            }
            // 流转到服务台复核或申请人
            WorkflowNodeVO fallbackModel = workflowApi.getNodeById(modelIds);
            //驳回到申请,重新申请
            boolean isApply= WorkflowUtil.compareNodeAbility(fallbackModel.getNodeFeature(), WorkflowNodeAbilityType.APPLY.getCode());
            if (isApply) {
                workflowApi.fallbackOnApproveNotPass(vo, null);
                //订单状态设置为 科信审核驳回
                info.setStatus(ApplyInfoStatus.REVIEW_REJECT.getCode());
                smsApi.buildRejectMessage(info.getCreator(), info.getServiceTypeName());
            }else{
//                1.复制回退环节历史流程环节信息，设置为待办，处理人时间修改等插入流转表；2.复核后环节后的到当前环节间流转信息设置为已回退
                workflowApi.fallbackOnApproveNotPass(vo, map);
                //订单状态设置为 科信待审核
                info.setStatus(ApplyInfoStatus.REVIEW.getCode());
            }
        }
        //记录审核记录
        ApplyReviewRecord saveReviewRecord = new ApplyReviewRecord();
        saveReviewRecord.setRemark(approve.getRemark());
        saveReviewRecord.setResult(approve.getResult());
        saveReviewRecord.setApplyId(approve.getApplyId());
        saveReviewRecord.setCreator(user.getIdCard());
        saveReviewRecord.setStepName(curNode.getNodeName());
        saveReviewRecord.setWorkflowNodeId(curNode.getId());
        saveReviewRecord.setType(ApplyReviewRecord.TYPE_AUDIT);
        applyReviewRecordService.save(saveReviewRecord);
        applyInfoService.updateById(info);
        return QueryResponseResult.success(null);
    }


    /**
     * 工单审批业务实施
     * @param user
     * @param param
     * @param implHandler
     * @param modelId
     * @throws Exception
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveImpl(UserVO user, Map<String, Object> param, 
    		IImplHandler implHandler, String modelId) throws Exception {
        ApplyInfo info = (ApplyInfo) param.get("info");
        // 添加实施信息
//        ImplRequest<I> implRequest = (ImplRequest<I>) param.get("implRequest");
//        String result = implRequest.getResult();
//        String remark = implRequest.getRemark();
//
//        AppReviewInfo reviewInfo = new AppReviewInfo();
//        reviewInfo.setCreator(user.getIdcard());
//        reviewInfo.setResult(result);
//        reviewInfo.setRemark(remark);
//        reviewInfo.setrType("2");
//        reviewInfo.setStepName(ModelName.CARRY.getName());
//        reviewInfo.setFlowStepId(modelId);
//        reviewInfo.setAppInfoId(info.getId());
//        logger.debug("impl reviewInfo -> {}",reviewInfo);
//        reviewInfo.insert();
//        // 实施附件
//        refFiles(implRequest.getFileList(), reviewInfo.getId());
//        Message message = null;
//        Map<String, String> orderMap = null;
//        if ("0".equals(result)) {
//            // 驳回申请
//            this.update(info.getId(), ApplicationInfoStatus.REVIEW, null, null);
//        } else {
//
//            if (daasAutoEnable) {
//                FormNum formNum = FormNum.getFormNumByInfo(info);
//                if (formNum == FormNum.DAAS) { // daas 自动订购
//
//                    daasApigService.orderService(info);
//
//                }
//            }
//            if (paasAutoEnable) {
//                FormNum formNum = FormNum.getFormNumByInfo(info);
//                logger.info("自动订购服务类型=====",formNum.getResourceType()+"-------"+info.getFormNum());
//                if (implHandler instanceof IPaasFseqsqmImplService) {
//                    orderMap = paasApigService.orderService(info, implHandler);
//                } else if (formNum == FormNum.PAAS_DTSJGT || formNum == FormNum.PAAS_DTHTJY) {
//                    orderMap = paasApigService.mapOrderService(info, implHandler);
//                } else if (formNum == FormNum.PAAS_TYYH) {
//                    //统一用户勾了系统对接才进行自动订购
//                    PaasTyyh paasTyyh = tyyhService.getOne(new QueryWrapper<PaasTyyh>().lambda().eq(PaasTyyh::getAppInfoId, info.getId()));
//                    if ("1".equals(paasTyyh.getBusinessType()) || "3".equals(paasTyyh.getBusinessType())) {
//                        orderMap = paasApigService.orderService(info, implHandler);
//                    }
//                    //  勾选了添加人员才执行
//                    if ("2".equals(paasTyyh.getBusinessType()) || "3".equals(paasTyyh.getBusinessType())) {
//                        paasApigService.addTYYHMessage(info);
//                    }
//                } else if (formNum == FormNum.PAAS_TYXX) {
//                    logger.info("统一消息自动订购");
//                    orderMap = paasApigService.orderService(info, implHandler);
////                    paasApigService.addTYXXMessage(info);
//                }
//            }
//            if (saasAutoEnable) {
//                FormNum formNum = FormNum.getFormNumByInfo(info);
//                if (formNum == FormNum.SAAS_SERVICE) { // saas服务 自动订购
//
//                    saasApigService.orderService(info);
//
//                }
//            }
//            // 发送通知消息
//            message = buildCompleteMessage(info);
//            // 实施步骤已完成,修改申请为使用状态
//            this.update(info.getId(), ApplicationInfoStatus.USE, null, null);
//        }
//        if (implHandler != null) {
//            // 与实施服务器信息建立关系
//            if(FormNum.PAAS_RELATIONAL_DATABASE.toString().equals(info.getFormNum()) && "0".equals(result)){
//                rdbBaseService.emptyImplServerList(info.getId());
//
//            }else {
//                //业务办理通过才保存实施信息
//                if("1".equals(result)){
//                    List<I> serverList = implRequest.getImplServerList();
//                    implHandler.update(info.getId(), serverList, orderMap);
//                }
//            }
//
//        }
//        // 异步发送消息
//        if (message != null) {
//            messageProvider.sendMessageAsync(message);
//        }
    }

    /**
     * 加办
     * @param user 用户
     * @param approveVO 审核信息
     * @return
     */
    @Override
    public QueryResponseResult add(UserVO user, ApproveVO approveVO) {
        ApplyInfo info = applyInfoService.getById(approveVO.getApplyReviewRecord().getApplyId());
        Map<String, String> resultMap = workflowApi.add(approveVO.getUserIds(), approveVO.getCurrentActivityId(), user.getIdCard());
        if ("200".equals(resultMap.get("code"))) {
            ApplyReviewRecord applyReviewRecord= approveVO.getApplyReviewRecord();
            applyReviewRecord.setType(resultMap.get("type"));
            applyReviewRecord.setStepName(resultMap.get("stepName"));
            applyReviewRecord.setCreator(user.getIdCard());
            if (StringUtils.isEmpty(applyReviewRecord.getId())) {
                applyReviewRecordService.save(applyReviewRecord);
            }else{
                applyReviewRecordService.updateById(applyReviewRecord);
            }
        }else{
            return QueryResponseResult.fail(resultMap.get("msg"));
        }
        smsApi.buildProcessingMessage(info.getServiceTypeName(),info.getOrderNumber(),approveVO.getUserIds());
        return QueryResponseResult.success(null);
    }

    /**
     * VO转换成实体
     * @return
     */
    public ApplyReviewRecord reviewViewObjectConvert(ApplyReviewRecordVO applyReviewRecordVO) {
        ApplyReviewRecord applyReviewRecord = new ApplyReviewRecord();
        if (StringUtils.isNotEmpty(applyReviewRecordVO.getId())) {
            applyReviewRecord.setId(applyReviewRecordVO.getId());
        }
        if (StringUtils.isNotEmpty(applyReviewRecordVO.getApplyId())) {
            applyReviewRecord.setApplyId(applyReviewRecordVO.getApplyId());
        }
        if (StringUtils.isNotEmpty(applyReviewRecordVO.getCreator())) {
            applyReviewRecord.setCreator(applyReviewRecordVO.getCreator());
        }
        if (StringUtils.isNotEmpty(applyReviewRecordVO.getRemark())) {
            applyReviewRecord.setRemark(applyReviewRecordVO.getRemark());
        }
        if (applyReviewRecordVO.getResult()!=null) {
            applyReviewRecord.setResult(applyReviewRecordVO.getResult());
        }
        if (StringUtils.isNotEmpty(applyReviewRecordVO.getType())) {
            applyReviewRecord.setType(applyReviewRecordVO.getType());
        }
        if (StringUtils.isNotEmpty(applyReviewRecordVO.getWorkflowNodeId())) {
            applyReviewRecord.setWorkflowNodeId(applyReviewRecordVO.getWorkflowNodeId());
        }
        if (StringUtils.isNotEmpty(applyReviewRecordVO.getStepName())) {
            applyReviewRecord.setStepName(applyReviewRecordVO.getStepName());
        }
        return applyReviewRecord;
    }

}
