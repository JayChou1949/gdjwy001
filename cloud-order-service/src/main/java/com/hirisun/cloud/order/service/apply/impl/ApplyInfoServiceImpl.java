package com.hirisun.cloud.order.service.apply.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.hirisun.cloud.api.daas.DaasShoppingCartApi;
import com.hirisun.cloud.api.iaas.IaasShoppingCartApi;
import com.hirisun.cloud.api.paas.PaasShoppingCartApi;
import com.hirisun.cloud.api.saas.SaasShoppingCartApi;
import com.hirisun.cloud.api.system.FilesApi;
import com.hirisun.cloud.api.system.SmsApi;
import com.hirisun.cloud.api.user.UserApi;
import com.hirisun.cloud.api.workflow.WorkflowApi;
import com.hirisun.cloud.common.contains.*;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.common.util.WorkflowUtil;
import com.hirisun.cloud.common.vo.CommonCode;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.apply.ApplyReviewRecordVO;
import com.hirisun.cloud.model.apply.FallBackVO;
import com.hirisun.cloud.model.apply.UpdateApplyInfoVO;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.param.FilesParam;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workflow.WorkflowActivityVO;
import com.hirisun.cloud.model.workflow.WorkflowInstanceVO;
import com.hirisun.cloud.model.workflow.WorkflowNodeVO;
import com.hirisun.cloud.order.bean.apply.ApplyFeedback;
import com.hirisun.cloud.order.bean.apply.ApplyInfo;
import com.hirisun.cloud.order.bean.apply.ApplyReviewRecord;
import com.hirisun.cloud.order.bean.apply.UpdateApplyInfo;
import com.hirisun.cloud.order.continer.FormNum;
import com.hirisun.cloud.order.continer.HandlerWrapper;
import com.hirisun.cloud.order.continer.IApplicationHandler;
import com.hirisun.cloud.order.continer.IImplHandler;
import com.hirisun.cloud.order.mapper.apply.ApplyInfoMapper;
import com.hirisun.cloud.order.service.apply.ApplyFeedbackService;
import com.hirisun.cloud.order.service.apply.ApplyInfoService;
import com.hirisun.cloud.order.service.apply.ApplyReviewRecordService;
import com.hirisun.cloud.order.vo.ApproveVO;
import com.hirisun.cloud.order.vo.ImplRequest;
import com.hirisun.cloud.order.vo.OrderCode;
import com.hirisun.cloud.redis.lock.DistributeLock;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * <p>
 * ??????????????? ???????????????
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

    @Autowired
    private FilesApi filesApi;

    @Autowired
    private IaasShoppingCartApi iaasShoppingCartApi;
    @Autowired
    private SaasShoppingCartApi saasShoppingCartApi;
    @Autowired
    private DaasShoppingCartApi daasShoppingCartApi;
    @Autowired
    private PaasShoppingCartApi paasShoppingCartApi;

    @Autowired
    private DistributeLock lock;

    @Value("${daas.auto.enable}")
    private boolean daasAutoEnable;
    @Value("${paas.auto.enable}")
    private boolean paasAutoEnable;
    @Value("${saas.auto.enable}")
    private boolean saasAutoEnable;

    /**
     * ??????????????????????????????
     * ???????????????????????????
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
         * 1.??????????????????
         * 2.???????????????????????????
         * 3.???????????????????????????????????????
         * 4.??????????????????
         * 5.??????????????????
         */
//        ApplyInfo applyInfo = applyInfoService.getById(id);
        ApplyInfo applyInfo = getNewFlowDetail(id);
        if (applyInfo == null) {
            log.error("??????????????????");
            return QueryResponseResult.fail("??????????????????");
        }
        WorkflowInstanceVO instance = workflowApi.getWorkflowInstanceByBusinessId(applyInfo.getId());
        if (instance==null) {
            log.error("?????????????????????");
            return QueryResponseResult.fail("?????????????????????");
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

    private List<JSONObject> getByAppInfoId(ApplyInfo applyInfo) {

        String formNum = applyInfo.getFormNum();
        if(formNum.startsWith("IAAS")) {
            return iaasShoppingCartApi.getByAppInfoId(formNum,applyInfo.getId());
        }else if(formNum.startsWith("DAAS")) {
            return daasShoppingCartApi.getByAppInfoId(formNum,applyInfo.getId());
        }else if(formNum.startsWith("SAAS")) {
            return saasShoppingCartApi.getByAppInfoId(formNum,applyInfo.getId());
        }else if(formNum.startsWith("PAAS")) {
            return paasShoppingCartApi.getByAppInfoId(formNum,applyInfo.getId());
        }
        return null;
    }

    private List getImplServerListByAppInfoId(ApplyInfo applyInfo) {

        String formNum = applyInfo.getFormNum();
        if(formNum.startsWith("IAAS")) {
            return iaasShoppingCartApi.getImplServerListByAppInfoId(formNum,applyInfo.getId());
        }else if(formNum.startsWith("DAAS")) {
            return daasShoppingCartApi.getImplServerListByAppInfoId(formNum,applyInfo.getId());
        }else if(formNum.startsWith("SAAS")) {
            return saasShoppingCartApi.getImplServerListByAppInfoId(formNum,applyInfo.getId());
        }else if(formNum.startsWith("PAAS")) {
            return paasShoppingCartApi.getImplServerListByAppInfoId(formNum,applyInfo.getId());
        }
        return null;
    }

    public ApplyInfo getNewFlowDetail(String id) {
        ApplyInfo info = applyInfoMapper.getAppInfo(id);
        if (info == null) {
            return null;
        }
        List serverList = getByAppInfoId(info);
        if (CollectionUtils.isNotEmpty(serverList)) {
            info.setServerList(serverList);
        }
        List implServerList = getImplServerListByAppInfoId(info);
        // ???????????????????????????
        if (CollectionUtils.isNotEmpty(implServerList)) {
            info.setImplServerList(implServerList);
        }
        SubpageParam param = new SubpageParam();
        param.setRefId(id);
        List<FilesVo> filesList = filesApi.find(param);
        info.setFileList(filesList);
        // ?????????
        UserVO creator = userApi.getUserByIdCard(info.getCreator());
        info.setUser(creator);
        ApplyInfoStatus curStatus = ApplyInfoStatus.codeOf(info.getStatus());
        if (curStatus == ApplyInfoStatus.SHOPPING_CART) {
            FormNum formNum = FormNum.getFormNumByInfo(info.getFormNum());
            String description = null;
            String instructions = null;
            if (formNum.getResourceType() == ResourceType.PAAS) {
//                Paas paas = paasService.getById(info.getServiceTypeId());
//                if (paas != null) {
//                    description = paas.getDescription();
//                    instructions = paas.getInstructions();
//                    info.setTempList(filesService.list(new QueryWrapper<Files>().lambda().eq(Files::getRefId, paas.getId())));
//                }
            } else if (formNum.getResourceType() == ResourceType.SAAS) {
//                SaasConfigVO saas = saasService.getById(info.getServiceTypeId());
//                if (saas != null) {
//                    description = saas.getDescription();
//                    instructions = saas.getInstructions();
//                }
            } else if (formNum.getResourceType() == ResourceType.IAAS) {
//                IaasNew iaas = iaasNewService.getById(info.getServiceTypeId());
//                if (iaas != null) {
//                    description = iaas.getDescription();
//                    instructions = iaas.getInstructions();
//                }
            }
            info.setDescription(description);
            info.setInstructions(instructions);
            return info; // ????????????????????????????????????,???????????????????????????
        }

        // ??????????????????
        ApplyReviewRecord implInfo = null;
        ApplyReviewRecord lastReviewInfo = applyReviewRecordService.getLastPassReviewInfoByAppInfoId(id);
        log.debug("detail lastReviewInfo -> {}",lastReviewInfo);
        if (lastReviewInfo != null && "2".equals(lastReviewInfo.getType())) {
            // ???????????????????????????????????????
            implInfo = lastReviewInfo;
            SubpageParam params = new SubpageParam();
            params.setRefId(implInfo.getId());
            List<FilesVo> fileList = filesApi.find(params);
            implInfo.setFileList(fileList);
            log.debug("detail implInfo -> {}",implInfo);
            info.setImpl(implInfo);
        }
        return info;
    }

    /**
     *
     * @param applyInfoList
     * @param user
     */
    public  void curHandlerPerson(List<ApplyInfo> applyInfoList, UserVO user){
        List<String> instanceId = applyInfoList.stream().map(ApplyInfo::getInstanceId).distinct().collect(Collectors.toList());
        //??????Id??????????????????????????????(????????????)
        Map<String,String> instance2IdCardsMap = workflowApi.instanceToHandleIdCards(instanceId);
        List<String> handlePeronIdsList = Lists.newArrayList();
        instance2IdCardsMap.forEach((k,v)->{
            if(StringUtils.isNotBlank(v)){
                handlePeronIdsList.add(v);
            }
        });
        //??????????????????????????????Map
        Map<String,String> idCard2NameMap = idCardsNameMap(handlePeronIdsList);
        //????????????-???????????????????????????Map
        for(ApplyInfo record:applyInfoList){
            if(instance2IdCardsMap.size()!=0){
                if(instance2IdCardsMap.containsKey(record.getInstanceId()) && instance2IdCardsMap.get(record.getInstanceId()) != null){
                    record.setProcessingPerson(instance2IdCardsMap.get(record.getInstanceId()));
                }
            }
        }
        for (ApplyInfo record : applyInfoList) {
            if(idCard2NameMap != null){
                //?????????????????????????????????????????????????????????
                convertIdCardToName(idCard2NameMap,record);
            }
            ApplyInfoStatus applyInfoStatus = ApplyInfoStatus.codeOf(record.getStatus());
            // ?????????????????????
            if (applyInfoStatus != ApplyInfoStatus.DELETE
                    && Objects.equals(user.getIdcard(), record.getCreator())) {
                record.setCanDelete(true);
            }
        }
    }

    /**
     * ????????????????????????????????????Map
     * @param idCardsList ?????????????????? {"5110022522,545451515","45454551515,454554"}
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
            List<UserVO> userList = userApi.getUserByIdCardList(idCardElementList);
            if (CollectionUtils.isEmpty(userList)) {
                throw new CustomException(OrderCode.USER_SELECT_ERROR);
            }
            return userList.stream().collect(Collectors.toMap(UserVO::getIdcard,UserVO::getName));
        }
        return null;
    }

    /**
     * ?????????????????????
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
     * ??????????????????????????????
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void deleteById(UserVO user, String id) {
        ApplyInfo info = this.getById(id);
        if (info == null) {
            throw new CustomException(OrderCode.APPLY_MISSING);
        }
        if (!Objects.equals(user.getIdcard(), info.getCreator())) {
            throw new CustomException(OrderCode.USER_CANT_EDIT_OTHER_USER_APPLY);
        }
        // ????????????,????????????????????????
        this.update(new ApplyInfo(), new UpdateWrapper<ApplyInfo>().lambda()
                .eq(ApplyInfo::getId, id)
                .set(ApplyInfo::getStatus, ApplyInfoStatus.DELETE.getCode()));
//        workflowApi.terminationInstanceOfWorkFlow(id);
    }

    /**
     * ????????????????????????
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
            if (resultMap.get("code") != null && resultMap.get("code").equals(RequestCode.SUCCESS.getCode())) {
                approve.setStepName(resultMap.get("nodeName"));
                approve.setWorkflowNodeId(resultMap.get("nodeId"));
                approve.setType(resultMap.get("type"));
                ApplyReviewRecord reviewRecord = new ApplyReviewRecord();
                BeanUtils.copyProperties(approve,reviewRecord);
                if (StringUtils.isEmpty(approve.getId())) {
                    applyReviewRecordService.save(reviewRecord);
                }else{
                    applyReviewRecordService.updateById(reviewRecord);
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
            //????????????????????????????????????????????????????????????????????????????????????
            //??????review??????
            Map resultMap = workflowApi.advanceActivity(vo.getCurrentActivityId(),map);
            //r???????????????
            // {code: 200,msg: "????????????",data:"??????????????????"}
            WorkflowNodeVO model=null;
            try {
                if (resultMap.get("code") != null && resultMap.get("code").equals(RequestCode.SUCCESS.getCode())) {
                    model = JSON.parseObject(resultMap.get("data").toString(),WorkflowNodeVO.class);//????????????????????????????????????????????????
                }else if(resultMap.get("code") != null && resultMap.get("code").equals(RequestCode.COMPLETE.getCode())){
                    return QueryResponseResult.success(null);
                }else{
                    return QueryResponseResult.fail(resultMap.get("msg"));
                }
            } catch (Exception e) {
                throw new CustomException(OrderCode.WORKFLOW_MISSING);
            }
            //??????????????????????????????????????????
            boolean isImpl= WorkflowUtil.compareNodeAbility(model.getNodeFeature(), WorkflowNodeAbilityType.IMPL.getCode());
            if (isImpl){
                //??????????????????????????????,????????????????????? ?????????
                info.setStatus(ApplyInfoStatus.IMPL.getCode());
            }else {
                //???????????????????????????   ???????????????
                info.setStatus(ApplyInfoStatus.REVIEW.getCode());
            }
        }else {//??????
            String modelIds=vo.getFallBackModelIds();
            if (modelIds==null||modelIds.trim().equals("")) {
                log.error("????????????ID????????????,????????????");
                throw new CustomException(OrderCode.FALLBACK_ID_NOT_NULL);
            }
            // ????????????????????????????????????
            WorkflowNodeVO fallbackModel = workflowApi.getNodeById(modelIds);
            //???????????????,????????????
            boolean isApply= WorkflowUtil.compareNodeAbility(fallbackModel.getNodeFeature(), WorkflowNodeAbilityType.APPLY.getCode());
            if (isApply) {
                Map<String,String> resultMap=workflowApi.fallbackOnApproveNotPass(vo, new HashMap());
                if (resultMap.get("code") != null && !resultMap.get("code").equals(RequestCode.SUCCESS.getCode())) {
                    return QueryResponseResult.fail(resultMap.get("msg"));
                }
                //????????????????????? ??????????????????
                info.setStatus(ApplyInfoStatus.REVIEW_REJECT.getCode());
                smsApi.buildRejectMessage(info.getCreator(), info.getServiceTypeName());
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
            }else{
//                1.?????????????????????????????????????????????????????????????????????????????????????????????????????????2.?????????????????????????????????????????????????????????????????????
                Map<String,String> resultMap=workflowApi.fallbackOnApproveNotPass(vo, map);
                if (resultMap.get("code") != null && !resultMap.get("code").equals(RequestCode.SUCCESS.getCode())) {
                    return QueryResponseResult.fail(resultMap.get("msg"));
                }
                //????????????????????? ???????????????
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
        applyInfoService.updateById(info);
        return QueryResponseResult.success(null);
    }

//    private void update(String id, ApplyInfoStatus status, String flowId) {
//        this.update(new ApplyInfo(), new UpdateWrapper<ApplyInfo>().lambda()
//                .eq(ApplyInfo::getId, id)
//                .set(ApplyInfo::getStatus, status.getCode())
//                .set(ApplyInfo::getWorkFlowId, flowId)
//                );
//    }


    /**
     * ????????????????????????
     * @param user
     * @param param
     * @param implHandler
     * @param modelId
     * @throws Exception
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public <I> void saveImpl(UserVO user, Map<String, Object> param,
    		IImplHandler implHandler, String modelId) throws Exception {
        ApplyInfo info = (ApplyInfo) param.get("info");
        // ??????????????????
        ImplRequest<I> implRequest = (ImplRequest<I>) param.get("implRequest");
        String result = implRequest.getResult();
        String remark = implRequest.getRemark();
        WorkflowNodeVO curNode = workflowApi.getNodeById(modelId);
        ApplyReviewRecord reviewInfo = new ApplyReviewRecord();
        reviewInfo.setCreator(user.getIdcard());
        reviewInfo.setResult(Integer.parseInt(result));
        reviewInfo.setRemark(remark);
        reviewInfo.setType("2");
        reviewInfo.setStepName(curNode.getNodeName());
        reviewInfo.setWorkflowNodeId(modelId);
        reviewInfo.setApplyId(info.getId());
        log.info("impl reviewInfo -> {}",reviewInfo);
        applyReviewRecordService.save(reviewInfo);
        // ????????????
        refFiles(implRequest.getFileList(), reviewInfo.getId());
        Map<String, String> orderMap = null;
        if ("0".equals(result)) {
            // ????????????
            this.update(new ApplyInfo(), new UpdateWrapper<ApplyInfo>().lambda()
                    .eq(ApplyInfo::getId, info.getId())
                    .set(ApplyInfo::getStatus, ApplyInfoStatus.REVIEW.getCode()));
        } else {
            //TODO api????????????????????????
            if (daasAutoEnable) {
                FormNum formNum = FormNum.getFormNumByInfo(info.getFormNum());
                if (formNum == FormNum.DAAS) { // daas ????????????
//                    daasApigService.orderService(info);
                }
            }
            if (paasAutoEnable) {
//                FormNum formNum = FormNum.getFormNumByInfo(info.getFormNum());
//                log.info("????????????????????????=====",formNum.getResourceType()+"-------"+info.getFormNum());
//                if (implHandler instanceof PaasFseqsqmImplService) {
//                    orderMap = paasApigService.orderService(info, implHandler);
//                } else if (formNum == FormNum.PAAS_DTSJGT || formNum == FormNum.PAAS_DTHTJY) {
//                    orderMap = paasApigService.mapOrderService(info, implHandler);
//                } else if (formNum == FormNum.PAAS_TYYH) {
//                    //???????????????????????????????????????????????????
//                    PaasTyyh paasTyyh = tyyhService.getOne(new QueryWrapper<PaasTyyh>().lambda().eq(PaasTyyh::getAppInfoId, info.getId()));
//                    if ("1".equals(paasTyyh.getBusinessType()) || "3".equals(paasTyyh.getBusinessType())) {
//                        orderMap = paasApigService.orderService(info, implHandler);
//                    }
//                    //  ??????????????????????????????
//                    if ("2".equals(paasTyyh.getBusinessType()) || "3".equals(paasTyyh.getBusinessType())) {
//                        paasApigService.addTYYHMessage(info);
//                    }
//                } else if (formNum == FormNum.PAAS_TYXX) {
//                    logger.info("????????????????????????");
//                    orderMap = paasApigService.orderService(info, implHandler);
////                    paasApigService.addTYXXMessage(info);
//                }
            }
            if (saasAutoEnable) {
                FormNum formNum = FormNum.getFormNumByInfo(info.getFormNum());
                if (formNum == FormNum.SAAS_SERVICE) { // saas?????? ????????????
//                    saasApigService.orderService(info);
                }
            }
            // ??????????????????
            smsApi.buildCompleteMessage(info.getCreator(),info.getServiceTypeName(),info.getOrderNumber());
            // ?????????????????????,???????????????????????????
            this.update(new ApplyInfo(), new UpdateWrapper<ApplyInfo>().lambda()
                    .eq(ApplyInfo::getId, info.getId())
                    .set(ApplyInfo::getStatus, ApplyInfoStatus.USE.getCode()));
        }
        if (implHandler != null) {
            // ????????????????????????????????????
            if(FormNum.PAAS_RELATIONAL_DATABASE.toString().equals(info.getFormNum()) && "0".equals(result)){
//                rdbBaseService.emptyImplServerList(info.getId());
            }else {
                //???????????????????????????????????????
                if("1".equals(result)){
                    List<Map<String, Object>> serverList = implRequest.getImplServerList();
                    implHandler.update(info.getId(), serverList, orderMap);
                }
            }
        }
    }

    /**
     * ??????
     * @param user ??????
     * @param approveVO ????????????
     * @return
     */
    @Override
    public QueryResponseResult add(UserVO user, ApproveVO approveVO) {
        ApplyInfo info = applyInfoService.getById(approveVO.getApplyReviewRecord().getApplyId());
        Map<String, String> resultMap = workflowApi.add(approveVO.getUserIds(), approveVO.getCurrentActivityId(), user.getIdcard());
        if (RequestCode.SUCCESS.getCode().equals(resultMap.get("code"))) {
            ApplyReviewRecord applyReviewRecord= approveVO.getApplyReviewRecord();
            applyReviewRecord.setType(resultMap.get("type"));
            applyReviewRecord.setStepName(resultMap.get("stepName"));
            applyReviewRecord.setCreator(user.getIdcard());
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

    @Override
    public QueryResponseResult adviser(UserVO user, FallBackVO vo) {
        ApplyReviewRecordVO approve = vo.getApplyReviewRecord();
        approve.setCreator(user.getIdcard());
        Map<String,String> resultMap=workflowApi.adviseActivity(vo.getCurrentActivityId());
        if (resultMap.get("code") != null && resultMap.get("code").equals(RequestCode.SUCCESS.getCode())) {
            approve.setStepName(resultMap.get("nodeName"));
            approve.setWorkflowNodeId(resultMap.get("nodeId"));
            approve.setType(resultMap.get("type"));
            ApplyReviewRecord reviewRecord = new ApplyReviewRecord();
            BeanUtils.copyProperties(approve,reviewRecord);
            if (StringUtils.isEmpty(approve.getId())) {
                applyReviewRecordService.save(reviewRecord);
            }else{
                applyReviewRecordService.updateById(reviewRecord);
            }
        }else{
            return QueryResponseResult.fail(resultMap.get("msg"));
        }
        return QueryResponseResult.success(null);
    }

    @Override
    public QueryResponseResult submit(UserVO user,String id, String type, String userIds) {
        if (StringUtils.isEmpty(id)) {
            return QueryResponseResult.fail("???????????????!");
        }

        List<String> userIdList = null;
        if ("inner".equals(type)) {
            if (org.apache.commons.lang3.StringUtils.isEmpty(userIds)) {
                return QueryResponseResult.fail("??????????????????!");
            } else {
                userIdList = Arrays.asList(userIds.split(","));
                if (userIdList.isEmpty()) {
                    return QueryResponseResult.fail("??????????????????!");
                }
            }
        }
        ApplyInfo info = applyInfoService.getById(id);
        if ("1".equals(info.getDraft())) {
            return QueryResponseResult.fail("??????????????????");
        }
        WorkflowInstanceVO instance =workflowApi.getWorkflowInstanceByBusinessId(info.getId());
        if (null==instance){
            return QueryResponseResult.fail("?????????????????????");
        }
        WorkflowActivityVO firstActivity = workflowApi.getOneWorkflowActivityByParams(WorkflowActivityStatus.WAITING.getCode(),instance.getId());
        if (firstActivity==null) {
            log.info("???????????????????????????????????????");
            throw new CustomException(OrderCode.WORKFLOW_ACTIVITY_MISSING);
        }
        info.setModifiedTime(new Date());
        if ("kx".equals(type)) {
            info.setStatus(ApplyInfoStatus.REVIEW.getCode());
        }else {
            info.setStatus(ApplyInfoStatus.INNER_REVIEW.getCode());
        }
        Map<String,String> map = new HashMap<>();
        map.put("name",info.getServiceTypeName());
        map.put("order",info.getOrderNumber());
        map.put("depApproveUserIds",userIds);

        Map resultMap = workflowApi.advanceActivity(firstActivity.getId(),map);
        if (!RequestCode.SUCCESS.getCode().equals(resultMap.get("code"))) {
            throw new CustomException(OrderCode.FEIGN_METHOD_ERROR);
        }
        applyInfoService.updateById(info);
        smsApi.buildSuccessMessage(user.getId(),info.getServiceTypeName(), info.getOrderNumber());
        return QueryResponseResult.success(null);
    }

    @Override
    public QueryResponseResult forward(UserVO user, String activityId, String userIds, String applyInfoId) {
        String uuid = UUIDUtil.getUUID();
        String lockKey = activityId.intern();
        try {
            if (lock.lock(lockKey, uuid)) {

                ApplyInfo info = applyInfoService.getById(applyInfoId);
                Map<String,String> resultMap =  workflowApi.activityForward(activityId, userIds);
                if (!RequestCode.SUCCESS.getCode().equals(resultMap.get("code"))) {
                    return QueryResponseResult.fail(resultMap.get("msg"));
                }
                String[] handlePersonArr = userIds.split(",");
                for (String uid : handlePersonArr) {
                    smsApi.buildProcessingMessage(info.getServiceTypeName(),info.getOrderNumber(),uid);
                }
                return QueryResponseResult.success(null);
            } else {
                return QueryResponseResult.fail("??????????????????????????????");
            }
        } finally {
            lock.unlock(lockKey, uuid);
        }
    }

    @Override
    public QueryResponseResult reject(UserVO user,FallBackVO vo) {
        ApplyReviewRecordVO approve = vo.getApplyReviewRecord();
        approve.setCreator(user.getIdcard());
        ApplyInfo info = applyInfoService.getById(approve.getApplyId());
        if(null==info){
            return QueryResponseResult.fail("?????????????????????");
        }
        Map<String,String> rejectMsgMap=workflowApi.rejectApply(vo.getCurrentActivityId(), vo.getFallBackModelIds());
        if (RequestCode.SUCCESS.getCode().equals(rejectMsgMap.get("code"))) {
            approve.setType("4");
            WorkflowNodeVO curmodel = workflowApi.getNodeById(rejectMsgMap.get("nodeId"));
            approve.setStepName(curmodel.getNodeName()+"??????");
            ApplyReviewRecord reviewRecord = new ApplyReviewRecord();
            BeanUtils.copyProperties(approve,reviewRecord);
            if (approve.getId()!=null&&!approve.getId().equals("")) {
                applyReviewRecordService.updateById(reviewRecord);
            }else {
                applyReviewRecordService.save(reviewRecord);
            }
        }else{
            return QueryResponseResult.fail(rejectMsgMap.get("msg"));
        }
        if (ApplyInfoStatus.IMPL.getCode().equals(info.getStatus())){
            info.setStatus(ApplyInfoStatus.IMPL_REJECT.getCode());
        }else {
            info.setStatus(ApplyInfoStatus.REVIEW_REJECT.getCode());
        }
        applyInfoService.updateById(info);
        return QueryResponseResult.success(null);
    }

    @Override
    public QueryResponseResult termination(String applyInfoId) {

        workflowApi.terminationOrder(applyInfoId);
        this.update(new ApplyInfo(), new UpdateWrapper<ApplyInfo>().lambda()
                .eq(ApplyInfo::getId,applyInfoId)
                .set(ApplyInfo::getStatus, ApplicationInfoStatus.DELETE.getCode()));
        return QueryResponseResult.success(null);
    }

    @Override
    public QueryResponseResult newTodo(UserVO user) {

        Map<String, Integer> map = new HashMap<>();
        int iaas = applyInfoMapper.getNewCount(user, ResourceType.IAAS.getCode());
        map.put("iaas", iaas);
        int daas = applyInfoMapper.getNewCount(user, ResourceType.DAAS.getCode());
        map.put("daas", daas);
        int paas = applyInfoMapper.getNewCount(user, ResourceType.PAAS.getCode());
        map.put("paas", paas);
        int saasService = applyInfoMapper.getNewCount(user, ResourceType.SAAS_SERVICE.getCode());
        map.put("saasService", saasService);


//        int saas = saasApplicationService.getSaasTodoCount(user);
        map.put("saas", 0);
//        int saasRegist = saasRegisterService.getTodoCount(user);
//        map.put("saasRegist", saasRegist);
//
//        int servicePublish = servicePublishService.getPublishTodoCount(user);
        map.put("servicePublish", 0);
//
//        int alterTodoCount = serviceAlterService.getAlterTodoCount(user);
//        map.put("serviceChange", alterTodoCount);
//
//        int iaasZysb = iaasZysbMapper.getTodoCount(user);
        map.put("iaasZysb", 0);
//
//        int saasRecover = saasRecoverInfoService.getTodoCount(user);
        map.put("saasRecover", 0);

        int total = iaas + daas + paas + saasService;
//        int total = iaas + daas + saas + paas + saasRegist + servicePublish + iaasZysb + saasRecover + saasService;
        map.put("total", total);

        return QueryResponseResult.success(map);
    }

    @Override
    public QueryResponseResult updateInfo(UserVO user, UpdateApplyInfo origin) throws Exception {
        String json = JSON.toJSONString(origin);
//        UpdateApplyInfo origin = JSONObject.parseObject(json, UpdateApplyInfo.class);
        ApplyInfo info = origin.getInfo();
        info.setFormNum(this.getById(info.getId()).getFormNum());
        HandlerWrapper hw = FormNum.getHandlerWrapperByName(info.getFormNum());
        UpdateApplyInfo updateInfo = parseUpdateApplicationInfo(json, hw.getApplicationType());
        updateObj(user, hw.getFormNum(), updateInfo, hw.getHandler());
        //??????????????????????????????????????????????????????
        if(info.getFormNum() != null){
            if(FormNum.PAAS_RELATIONAL_DATABASE.toString().equals(info.getFormNum())){
                IImplHandler implHandler = hw.getImplHandler();
                String infoJson = JSON.toJSONString(info);
                //TODO
//                List<PaasRdbBase> rdbBasesImplList = JSON.parseArray(JSON.parseObject(infoJson).getString("implServerList"),PaasRdbBase.class);
//                if(CollectionUtils.isNotEmpty(rdbBasesImplList)){
//                    implHandler.update(info.getId(),rdbBasesImplList);
//                }
            }
        }
        return QueryResponseResult.success(null);
    }
    private void updateObj(UserVO user, FormNum formNum,
                        UpdateApplyInfo updateInfo, IApplicationHandler handler) {
        ApplyInfo info = updateInfo.getInfo();
        if (info == null || StringUtils.isEmpty(info.getId())) {
            throw new CustomException(CommonCode.INVALID_PARAM);
        }
        String uuid = UUIDUtil.getUUID();
        String lockKey = info.getId().intern();
        try {
            if (lock.lock(lockKey, uuid)) {
                info.setResourceType(formNum.getResourceType().getCode());
                info.setFormNum(formNum.name());
                info.setDraft("0"); // ????????????
//                info.setFlowNew("1");
                this.update(user, updateInfo, handler);
            } else {
                throw new CustomException(CommonCode.SERVER_ERROR);
            }
        } finally {
            lock.unlock(lockKey, uuid);
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    public <S> void update(UserVO user, UpdateApplyInfo updateInfo, IApplicationHandler<S> handler) {
//        ApplyInfo info = updateInfo.getInfo();
//        info.setStatus(null);
//        info.setWorkFlowId(null);
//        info.setOrderNumber(null);
//
//        String type = updateInfo.getType();
//
//        boolean isRdbAddAccout = false;
//
//        ApplyInfo dbInfo = this.getById(info.getId());
//
//        ApplyInfoStatus status = ApplyInfoStatus.codeOf(dbInfo.getStatus());
//        // ????????????????????????????????????(?????????,????????????,????????????,?????????????????????)
//        if (status != ApplyInfoStatus.REVIEW_REJECT
//                && status != ApplyInfoStatus.IMPL_REJECT
//                && status != ApplyInfoStatus.SHOPPING_CART
//                && status != ApplyInfoStatus.INNER_REJECT) {
//            if (status == ApplyInfoStatus.REVIEW) {
//                boolean canEdit = false;
////                FlowStep step = flowStepService.getById(dbInfo.getWorkFlowId());
//                if (step != null && FWT_REVIEW_STEP_NAME.equals(step.getName())) {
//                    // ????????????????????????,???????????????????????????
//                    List<User> userList = flowStepUserService.findUserByFlowStepId(dbInfo.getFlowStepId());
//                    if (userList != null && !userList.isEmpty()) {
//                        for (User u : userList) {
//                            if (Objects.equals(user.getIdcard(), u.getIdcard())) {
//                                canEdit = true;
//                                break;
//                            }
//                        }
//                    }
//                }
//                if ("1".equals(dbInfo.getFlowNew())) {
//                    canEdit = true;
//                }
//                if (!canEdit) {
//                    throw new BaseException("?????????????????????");
//                }
//            } else {
//                //??????????????????????????????????????????????????????????????????????????????,???????????????
//                if(info.getFormNum().equals(FormNum.PAAS_RELATIONAL_DATABASE.toString()) && status == ApplicationInfoStatus.IMPL){
//                    List<PaasRdbBase>  baseList = (List<PaasRdbBase>)handler.getByAppInfoId(info.getId());
//                    logger.debug("update::baseList -> {}",baseList);
//                    if(baseList.size()==1){
//                        if(RdbApplyType.ADD_ACCOUNT.getCode().equals(baseList.get(0).getApplyType())){
//                            logger.debug("update::PAAS_RELATIONAL_DATABASE + IMPL + ADD_ACCOUNT");
//                            isRdbAddAccout = true;
//                        }else {
//                            logger.debug("update:::PAAS_RELATIONAL_DATABASE + IMPL + ADD_DATABASE");
//                            throw new BaseException("?????????????????????");
//                        }
//                    }else {
//                        logger.debug("update::PAAS_RELATIONAL_DATABASE + IMPL+baseList!=1");
//                        throw new BaseException("?????????????????????");
//                    }
//                }else {
//                    logger.debug("update::non (PAAS_RELATIONAL_DATABASE + IMPL) + non ???REVIEW + ...??? ");
//                    throw new BaseException("?????????????????????");
//                }
//            }
//        }
//
//        // ???????????????
//        List<Message> message = null;
//        /*
//         * ????????????
//         * 1. ????????????????????????,??????????????????????????????,?????????????????????
//         * 2. ???????????????????????????,???????????????????????????
//         * 3. ??????????????????????????????,??????????????????????????????
//         * 4. ????????????????????????,??????????????????????????????,?????????????????????
//         * 5. ?????????????????????????????????????????????????????????????????????????????????,????????????????????? ???2020-4-23???
//         */
//        if (!"1".equals(dbInfo.getFlowNew())) {
//            if (status == ApplicationInfoStatus.SHOPPING_CART) {
//                //???????????????,?????????????????????
//            } else if (status == ApplicationInfoStatus.REVIEW) {
//                // ???????????????
//            } else if(status == ApplicationInfoStatus.IMPL && isRdbAddAccout){
//                //??????????????????????????????????????????
//            }else {
//                if ("kx".equals(type)) {
//                    // ????????????
//                    List<FlowStep> flowStepList = flowStepService.getFlowStepList(info.getServiceTypeId(), info.getResourceType(), TYPE_REVIEW);
//                    String flowStepId = flowStepList.get(0).getId();
//                    this.update(info.getId(), ApplicationInfoStatus.REVIEW, flowStepId, null);
//
//                    message = buildProcessingMessage(dbInfo, flowStepUserService.findUserByFlowStepId(flowStepId));
//                } else {
//                    // ???????????????
//                    List<String> userIds = updateInfo.getUserIds();
//                    if (userIds == null || userIds.isEmpty()) {
//                        throw new BaseException("??????????????????!");
//                    }
//                    innerReviewUserService.infoUser(info.getId(), userIds);
//                    this.update(info.getId(), ApplicationInfoStatus.INNER_REVIEW, null, null);
//
//                    message = buildProcessingMessage(dbInfo, userService.findUserByIds(userIds));
//                }
//            }
//        }
//        // ?????????????????????
//        if (handler != null) {
//            handler.update(info);
//        }
//        // ??????????????????
//        refFiles(info.getFileList(), info.getId());
//        // ????????????
//        info.setServiceTypeId(null);
//        info.setHwPoliceCategory(AreaPoliceCategoryUtils.getPoliceCategory(info.getAppName()));
//        info.updateById();
//
//        // ??????????????????
//        if (message != null) {
//            messageProvider.sendMessageAsync(message);
//        }
    }
    private static <S> UpdateApplyInfo<S> parseUpdateApplicationInfo(String json, Class type) {
        return JSON.parseObject(json,
                new TypeReference<UpdateApplyInfo<S>>(type) {});
    }

    /**
     * ????????????????????????????????????
     * @param files
     * @param refId
     */
    private void refFiles(List<FilesVo> files, String refId) {
        if (StringUtils.isEmpty(refId)) {
            return;
        }

        SubpageParam param = new SubpageParam();
        param.setRefId(refId);
        filesApi.remove(param);

        if (files != null && !files.isEmpty()) {
            for (FilesVo f : files) {
                f.setId(null);
                f.setRefId(refId);
            }
            FilesParam filesParam = new FilesParam();
            param.setFiles(files);
            filesApi.saveBatch(filesParam);
        }
    }



}
