package com.hirisun.cloud.order.service.servicePublish.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hirisun.cloud.api.file.FileApi;
import com.hirisun.cloud.api.system.FilesApi;
import com.hirisun.cloud.api.system.SmsApi;
import com.hirisun.cloud.api.user.OrgApi;
import com.hirisun.cloud.api.user.UserApi;
import com.hirisun.cloud.api.workflow.WorkflowApi;
import com.hirisun.cloud.common.constant.BusinessName;
import com.hirisun.cloud.common.consts.RedisKey;
import com.hirisun.cloud.common.contains.*;
import com.hirisun.cloud.common.enumer.ModelName;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.util.ExceptionPrintUtil;
import com.hirisun.cloud.common.util.ServiceCodeUtil;
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.common.util.WorkflowUtil;
import com.hirisun.cloud.common.vo.CommonCode;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.apig.vo.ServiceReturnBeanVo;
import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.apply.ApplyReviewRecordVO;
import com.hirisun.cloud.model.apply.FallBackVO;
import com.hirisun.cloud.model.common.WorkOrder;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.param.ActivityParam;
import com.hirisun.cloud.model.saas.vo.SaasApplicationMergeVO;
import com.hirisun.cloud.model.saas.vo.SaasApplicationVO;
import com.hirisun.cloud.model.service.ApiAcIpVo;
import com.hirisun.cloud.model.service.ApiOperationVo;
import com.hirisun.cloud.model.service.publish.vo.ServicePublishVo;
import com.hirisun.cloud.model.user.OrgVO;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workbench.vo.QueryVO;
import com.hirisun.cloud.model.workflow.WorkflowActivityVO;
import com.hirisun.cloud.model.workflow.WorkflowInstanceVO;
import com.hirisun.cloud.model.workflow.WorkflowNodeVO;
import com.hirisun.cloud.model.workflow.WorkflowVO;
import com.hirisun.cloud.order.bean.apply.ApplyFeedback;
import com.hirisun.cloud.order.bean.apply.ApplyReviewRecord;
import com.hirisun.cloud.order.bean.servicePublish.*;
import com.hirisun.cloud.order.handler.CommonHandler;
import com.hirisun.cloud.order.mapper.servicePublish.ServicePublishMapper;
import com.hirisun.cloud.order.service.apply.ApplyFeedbackService;
import com.hirisun.cloud.order.service.apply.ApplyReviewRecordService;
import com.hirisun.cloud.order.service.servicePublish.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.util.WorkOrderUtils;
import com.hirisun.cloud.order.vo.ApproveVO;
import com.hirisun.cloud.order.vo.ImplRequest;
import com.hirisun.cloud.order.vo.OrderCode;
import com.hirisun.cloud.redis.lock.DistributeLock;
import com.hirisun.cloud.redis.service.RedisService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务发布 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-01
 */
@Service
public class ServicePublishServiceImpl extends ServiceImpl<ServicePublishMapper, ServicePublish> implements ServicePublishService {

    @Autowired
    private DistributeLock lock;

    @Autowired
    private UserApi userApi;

    @Autowired
    private WorkflowApi workflowApi;

    @Autowired
    private RedisService redisService;

    @Autowired
    private SmsApi smsApi;

    @Autowired
    private FileApi fileApi;

    @Autowired
    private FilesApi filesApi;

    @Autowired
    private WorkOrderUtils workOrderUtils;

    @Autowired
    private OrgApi orgApi;

    @Autowired
    private ServicePublishApiProductService servicePublishApiProductService;

    @Autowired
    private ServicePublishBackendService servicePublishBackendService;

    @Autowired
    private BackendHostService backendHostService;

    @Autowired
    private ServicePublishApiService servicePublishApiService;

    @Autowired
    private ApiOperationService apiOperationService;

    @Autowired
    private ApiAcIpService apiAcIpService;

    @Autowired
    private ApplyReviewRecordService applyReviewRecordService;

    @Autowired
    private ApplyFeedbackService applyFeedbackService;


    @Override
    public IPage<ServicePublish> getPage(UserVO user, IPage<ServicePublish> page, Map<String, Object> param) {
        page = baseMapper.getPage(page, param);
        List<ServicePublish> records = page.getRecords();
        if (records != null && !records.isEmpty()) {
            workOrderUtils.curHandlerPerson(records,user);
        }
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveServicePublish(UserVO user, ServicePublish info) {
        info.setId(null);
        info.setCreator(user.getIdcard());
        info.setCreatorName(user.getName());
        info.setStatus(ApplicationInfoStatus.INNER_REVIEW.getCode());
        String orderNum = redisService.genOrderNum(RedisKey.KEY_PUBLISH_PREFIX);
        info.setOrderNumber(orderNum);
        //DAAS服务不进行编码
        if (!StringUtils.equals(info.getServiceType(),"DAAS")) {
            String serviceCode = genServiceCode(info);
            info.setServiceCode(serviceCode);
        }
        this.save(info);
        // 关联文件信息
        SubpageParam param = new SubpageParam();
        param.setFiles(info.getFileList());
        param.setRefId(info.getId());
        filesApi.refFiles(param );
        // 保存 api 产品
        saveApiProduct(info);
        // 保存后端服务
        saveBackend(info);
        // 保存 api
        saveApi(info);
    }

    @Override
    public QueryResponseResult<SaasApplicationVO> detail(String id) {
        ServicePublish detail = getDetails(id);
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
    /**
     * 服务发布详情
     * @param id
     * @return
     */
    @Override
    public ServicePublish getDetails(String id) {
        ServicePublish servicePublish = this.getById(id);
        if (servicePublish == null) {
            throw new CustomException(OrderCode.WORK_ORDER_NOT_NULL);
        }
        // 获取文件
        SubpageParam param = new SubpageParam();
        param.setRefId(id);
        List<FilesVo> files = filesApi.find(param);
        List<FilesVo> fileList= Lists.newArrayList();
        List<FilesVo> interfaceFileList= Lists.newArrayList();
        List<FilesVo> developmentFileList= Lists.newArrayList();
        if (files!=null&&files.size()>0){
            for (FilesVo file : files) {
                String path=file.getPath();
                if (path!=null){
                    if (path.contains("interface")){
                        interfaceFileList.add(file);
                    }else if (path.contains("development")){
                        developmentFileList.add(file);
                    }else {
                        fileList.add(file);
                    }
                }
            }
        }
        servicePublish.setFileList(fileList);
        servicePublish.setInterfaceFileList(interfaceFileList);
        servicePublish.setDevelopmentFileList(developmentFileList);
        // api产品
        ServicePublishApiProduct apiProduct = servicePublishApiProductService.getOne(new QueryWrapper<ServicePublishApiProduct>().lambda()
                .eq(ServicePublishApiProduct::getPublishId, id));
        servicePublish.setApiProduct(apiProduct);
        // 后端服务
        List<ServicePublishBackend> backendList = getBackendList(id);
        servicePublish.setBackendList(backendList);
        // api
        List<ServicePublishApi> apiList = getApiList(id);
        servicePublish.setApiList(apiList);
        // 申请人
        UserVO creator = userApi.getUserByIdCard(servicePublish.getCreator());
        servicePublish.setUser(creator);
        // 审核信息
        List<ApplyReviewRecord> allReviewInfo = applyReviewRecordService.getAllReviewInfoByAppInfoId(id);
        if(CollectionUtils.isNotEmpty(allReviewInfo)) {
            List<ApplyReviewRecordVO> list = JSON.parseObject(JSON.toJSON(allReviewInfo).toString(),
                    new TypeReference<List<ApplyReviewRecordVO>>(){});
            servicePublish.setReviewList(list);
        }
        // 实施审批信息
        ApplyReviewRecordVO implInfo = new ApplyReviewRecordVO();
        ApplyReviewRecord lastReviewInfo = applyReviewRecordService.getLastPassReviewInfoByAppInfoId(id);
        BeanUtils.copyProperties(lastReviewInfo, implInfo);
        if (lastReviewInfo != null && "2".equals(lastReviewInfo.getType())) {
            // 最近一条审核记录为实施记录
            SubpageParam params = new SubpageParam();
            params.setRefId(implInfo.getId());
            List<FilesVo> fileList2 = filesApi.find(params);
            implInfo.setFileList(fileList2);
            servicePublish.setImpl(implInfo);
        }
        return servicePublish;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(UserVO user, ServicePublish info) {
        info.setStatus(null);
        info.setOrderNumber(null);
        this.updateById(info);

        servicePublishBackendService.remove(new QueryWrapper<ServicePublishBackend>().lambda()
                .eq(ServicePublishBackend::getPublishId, info.getId()));
        servicePublishApiService.remove(new QueryWrapper<ServicePublishApi>().lambda()
                .eq(ServicePublishApi::getPublishId, info.getId()));
        servicePublishApiProductService.remove(new QueryWrapper<ServicePublishApiProduct>().lambda()
                .eq(ServicePublishApiProduct::getPublishId, info.getId()));
        List<ServicePublishApi> servicePublishApis=info.getApiList();
        for (ServicePublishApi servicePublishApi : servicePublishApis) {
            apiOperationService.remove(new QueryWrapper<ApiOperation>().lambda()
                    .eq(ApiOperation::getMasterId, servicePublishApi.getId()));
        }
        List<FilesVo> fileList = info.getFileList();
        List<FilesVo> interfaceFileList = info.getInterfaceFileList();
        List<FilesVo> developmentFileList = info.getDevelopmentFileList();
        if (interfaceFileList!=null&&interfaceFileList.size()>0){
            fileList.addAll(interfaceFileList);
        }
        if (developmentFileList!=null&&developmentFileList.size()>0){
            fileList.addAll(developmentFileList);
        }
        // 关联文件信息
        SubpageParam param = new SubpageParam();
        param.setFiles(info.getFileList());
        param.setRefId(info.getId());
        filesApi.refFiles(param );
        // 保存 api 产品
        saveApiProduct(info);
        // 保存后端服务
        saveBackend(info);
        // 保存 api
        saveApi(info);
    }

    /**
     * 后台逻辑删除
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(UserVO user, String id) {
        ServicePublish info = this.getById(id);
        if (info == null) {
            throw new CustomException(OrderCode.WORK_ORDER_NOT_NULL);
        }
        if (!Objects.equals(user.getIdcard(), info.getCreator())) {
            throw new CustomException(OrderCode.USER_CANT_EDIT_OTHER_USER_APPLY);
        }
        // 逻辑删除,并设置相应的状态
        this.update(new ServicePublish(), new UpdateWrapper<ServicePublish>().lambda()
                .eq(ServicePublish::getId, id)
                .set(ServicePublish::getStatus, ApplicationInfoStatus.DELETE.getCode()));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveImpl(UserVO user, Map<String, Object> param, String modelId) throws Exception{
        ServicePublish info = (ServicePublish) param.get("info");
        // 添加实施信息
        ImplRequest implRequest = (ImplRequest) param.get("implRequest");
        String result = implRequest.getResult();
        String remark = implRequest.getRemark();

        ApplyReviewRecord reviewInfo = new ApplyReviewRecord();
        reviewInfo.setCreator(user.getIdcard());
        reviewInfo.setResult(Integer.parseInt(result));
        reviewInfo.setRemark(remark);
        reviewInfo.setType("2");
        reviewInfo.setStepName(ModelName.CARRY.getName());
        reviewInfo.setWorkflowNodeId(modelId);
        reviewInfo.setApplyId(info.getId());
        applyReviewRecordService.save(reviewInfo);
        // 实施附件
        SubpageParam fileParam = new SubpageParam();
        fileParam.setFiles(info.getFileList());
        fileParam.setRefId(info.getId());
        filesApi.refFiles(fileParam);
        ApplicationInfoStatus status;
        if ("0".equals(result)) {
            // 驳回申请
            status = ApplicationInfoStatus.REVIEW;
        } else {
            //TODO 实施
//            ServiceReturnBeanVo returnBean = paasApigService.apigOrderService(info);
//            //LoggerFactory.getLogger(ServiceReturnBean.class);
//            System.out.println(returnBean.toString());
//            if(returnBean.isError()){
//                throw  new BaseException("服务创建失败");
//            }
//            System.out.println( "创建服务类型——————"+info.getServiceType());
//            boolean isSuccess = info.getServiceType().equals("SAAS")?convertIntoSaas(info,returnBean.getServiceId()):(info.getServiceType().equals("PAAS")?convertIntoPaas(info,returnBean):convertIntoDaas(info,returnBean));
//            // 实施步骤已完成,修改申请为使用状态
//            if(!isSuccess){
//                log.error("apig_guid:"+returnBean.getServiceId());
//                throw new BaseException("同步到服务目录失败，请联系管理员");
//            }
            status = ApplicationInfoStatus.USE;
        }
        this.update(new ServicePublish(), new UpdateWrapper<ServicePublish>().lambda()
                .eq(ServicePublish::getId, info.getId())
                .set(ServicePublish::getStatus, status.getCode()));
    }


    @Override
    public int getReviewCount(UserVO user, QueryVO queryVO) {
        Map<String,Object> param = Maps.newHashMap();
        if(CommonHandler.isTenantManager(user)){
            queryVO.setArea(user.getTenantArea());
            queryVO.setPoliceCategory(CommonHandler.dealNameforQuery(user.getTenantPoliceCategory()));
        }else {
            queryVO.setCreator(user.getIdcard());
        }
        param.put("queryVO",queryVO);

        return baseMapper.getReviewCount(user,param);
    }


    @Override
    public int getImplCount(UserVO user, QueryVO queryVO) {

        Map<String,Object> param = Maps.newHashMap();
        if(CommonHandler.isTenantManager(user)){
            queryVO.setArea(user.getTenantArea());
            queryVO.setPoliceCategory(CommonHandler.dealNameforQuery(user.getTenantPoliceCategory()));
        }else {
            queryVO.setCreator(user.getIdcard());
        }
        param.put("queryVO",queryVO);

        return baseMapper.getImplCount(user,param);
    }

    @Override
    public int getRejectCount(UserVO user, QueryVO queryVO) {

        Map<String,Object> param = Maps.newHashMap();
        if(CommonHandler.isTenantManager(user)){
            queryVO.setArea(user.getTenantArea());
            queryVO.setPoliceCategory(CommonHandler.dealNameforQuery(user.getTenantPoliceCategory()));
        }else {
            queryVO.setCreator(user.getIdcard());
        }
        param.put("queryVO",queryVO);

        return baseMapper.getRejectCount(user,param);
    }

    @Override
    public int getUseCount(UserVO user, QueryVO queryVO) {
        Map<String,Object> param = Maps.newHashMap();
        if(CommonHandler.isTenantManager(user)){
            queryVO.setArea(user.getTenantArea());
            queryVO.setPoliceCategory(CommonHandler.dealNameforQuery(user.getTenantPoliceCategory()));
        }else {
            queryVO.setCreator(user.getIdcard());
        }
        param.put("queryVO",queryVO);

        return baseMapper.getUseCount(user,param);
    }


    @Override
    public QueryResponseResult servicePublishApply(UserVO user, QueryVO queryVO){
        int reviewCount = this.getReviewCount(user,queryVO);
        int implCount = this.getImplCount(user,queryVO);
        int rejectCount = this.getRejectCount(user,queryVO);
        int useCount = this.getUseCount(user,queryVO);
        Map<String, Integer> data = Maps.newHashMap();
        data.put("reviewCount", reviewCount);
        data.put("implCount", implCount);
        data.put("rejectCount", rejectCount);
        data.put("useCount", useCount);
        return QueryResponseResult.success(data);
    }

    @Override
    public QueryResponseResult create(UserVO user,  ServicePublish info) {
        WorkflowVO workFlow = getWorkFlowId(info.getServiceType());
        if(workFlow==null){
            return QueryResponseResult.fail("未配置流程");
        }
        info.setWorkFlowId(workFlow.getId());
        info.setWorkFlowVersion(workFlow.getVersion());
        info.setWhereFrom("1");
        saveServicePublish(user,info);
        workflowApi.launchInstanceOfWorkflow(user.getIdcard(), info.getWorkFlowId(), info.getId());

        WorkflowInstanceVO instance = workflowApi.getWorkflowInstanceByBusinessId(info.getId());
        if (null==instance){
            throw new CustomException(CommonCode.FLOW_INSTANCE_NULL_ERROR);
        }
//        ActivityParam param = new ActivityParam();
//        param.setActivitystatus(0);
//        param.setIsstart(0);
//        param.setInstanceId(instance.getId());

        Map<String,String> map = new HashMap<>();
        map.put("name", BusinessName.SAAS_RESOURCE);
        map.put("order",info.getOrderNumber());
        map.put("depApproveUserIds",info.getProcessingPerson());//申请后一个流程处理人
        WorkflowActivityVO firstActivity = workflowApi.getOneWorkflowActivityByParams(WorkflowActivityStatus.WAITING.getCode(),instance.getId());
        if (firstActivity==null) {
            log.error("未找到对应的流程活动信息！");
            throw new CustomException(OrderCode.WORKFLOW_ACTIVITY_MISSING);
        }
        Map resultMap = workflowApi.advanceActivity(firstActivity.getId(),map);
        if (!RequestCode.SUCCESS.getCode().equals(resultMap.get("code"))) {
            throw new CustomException(OrderCode.FEIGN_METHOD_ERROR);
        }
        smsApi.buildSuccessMessage(user.getIdcard(), BusinessName.SAAS_RESOURCE, info.getOrderNumber());
        return QueryResponseResult.success("发起流程成功");
    }

    @Override
    public QueryResponseResult deleteObj(UserVO user, String id) {
        String uuid = UUIDUtil.getUUID();
        String lockKey = id.intern();
        try {
            if (lock.lock(lockKey, uuid)) {
                deleteById(user, id);
                Map<String, String> stringStringMap = workflowApi.terminationOrder(id);
                if (!RequestCode.SUCCESS.getCode().equals(stringStringMap.get("code"))) {
                    return QueryResponseResult.fail(stringStringMap.get("msg"));
                }
            } else {
                return QueryResponseResult.fail("系统繁忙,请稍后重试");
            }
        } catch (Exception e) {
            log.error(ExceptionPrintUtil.getStackTraceInfo(e));
            return QueryResponseResult.fail("系统错误,请稍后重试");
        } finally {
            lock.unlock(lockKey, uuid);
        }
        return QueryResponseResult.success(null);
    }

    @Override
    public QueryResponseResult submit(UserVO user, String id, String type, String userIds) {
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
        ServicePublish info = getById(id);
        WorkflowInstanceVO instance =workflowApi.getWorkflowInstanceByBusinessId(info.getId());
        if (null==instance){
            return QueryResponseResult.fail("流程实例未找到");
        }
        WorkflowActivityVO firstActivity = workflowApi.getOneWorkflowActivityByParams(WorkflowActivityStatus.WAITING.getCode(),instance.getId());
        if (firstActivity==null) {
            throw new CustomException(OrderCode.WORKFLOW_ACTIVITY_MISSING);
        }
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
        updateById(info);
        smsApi.buildSuccessMessage(user.getId(),BusinessName.PUBLISH, info.getOrderNumber());
        return QueryResponseResult.success(null);
    }

    @Override
    public QueryResponseResult forward(UserVO user, String activityId, String userIds, String applyInfoId) {
        String uuid = UUIDUtil.getUUID();
        String lockKey = activityId.intern();
        try {
            if (lock.lock(lockKey, uuid)) {
                Map<String,String> resultMap = workflowApi.activityForward(activityId, userIds);
                if (!RequestCode.SUCCESS.getCode().equals(resultMap.get("code"))) {
                    return QueryResponseResult.fail(resultMap.get("msg"));
                }
                ServicePublish servicePublish = getById(applyInfoId);
                if (servicePublish == null) {
                    return QueryResponseResult.fail("申请单不存在");
                }
                smsApi.buildProcessingMessage(BusinessName.SAAS_RESOURCE, servicePublish.getOrderNumber(), userIds);
                return QueryResponseResult.success(null);
            } else {
                return QueryResponseResult.fail("系统繁忙,请稍后重试");
            }
        } finally {
            lock.unlock(lockKey, uuid);
        }
    }

    @Override
    public QueryResponseResult approve(UserVO user, FallBackVO vo) {
        ServicePublish info = this.getById(vo.getApplyReviewRecord().getApplyId());
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
        map.put("name",BusinessName.SAAS_RESOURCE);
        map.put("order",info.getOrderNumber());
        if ("1".equals(approve.getResult())){
            WorkflowNodeVO model=null;
            Map resultMap = workflowApi.advanceActivity(vo.getCurrentActivityId(),map);
            if (resultMap.get("code") != null && resultMap.get("code").equals(RequestCode.SUCCESS.getCode())) {
                model = JSON.parseObject(resultMap.get("data").toString(),WorkflowNodeVO.class);//此处转换失败，则直接抛方法内错误
            }else if(resultMap.get("code") != null && resultMap.get("code").equals(RequestCode.COMPLETE.getCode())){
                return QueryResponseResult.success(null);
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
        this.updateById(info);
        return QueryResponseResult.success(null);
    }

    @Override
    public QueryResponseResult reject(UserVO user, FallBackVO vo) {
        ApplyReviewRecordVO approve = vo.getApplyReviewRecord();
        approve.setCreator(user.getIdcard());
        ServicePublish info = this.getById(approve.getApplyId());
        if(null==info){
            return QueryResponseResult.fail("未找到待办数据");
        }
        Map<String,String> rejectMsgMap=workflowApi.rejectApply(vo.getCurrentActivityId(), vo.getFallBackModelIds());
        if (RequestCode.SUCCESS.getCode().equals(rejectMsgMap.get("code"))) {
            approve.setType("4");
            WorkflowNodeVO curmodel = workflowApi.getNodeById(rejectMsgMap.get("nodeId"));
            approve.setStepName(curmodel.getNodeName()+"回退");
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
        this.updateById(info);
        return QueryResponseResult.success(null);
    }

    @Override
    public QueryResponseResult impl(UserVO user,String id, String modelId, String activityId, ImplRequest implRequest)throws Exception {
        ServicePublish info = this.getDetails(id);
        WorkflowActivityVO activity =workflowApi.getActivityById(activityId);
        if (activity==null) {
            throw new CustomException(OrderCode.WORKFLOW_ACTIVITY_MISSING);
        }
        if (implRequest.getResult() == null) {
            return QueryResponseResult.fail("请选择实施结果！");
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
                this.saveImpl(user, param, activity.getNodeId());
            } else {
                return QueryResponseResult.fail("系统繁忙,请稍后重试!");
            }
        }catch (Exception e){
            throw e;
        }finally {
            lock.unlock(lockKey, uuid);
        }
        if ("1".equals(implRequest.getResult())){
            Map map = new HashMap();
//            map.put("depApproveUserIds", info.getCreator());
            Map resultMap = workflowApi.advanceActivity(activityId,map);
            if (!RequestCode.SUCCESS.getCode().equals(resultMap.get("code"))) {
                throw new CustomException(OrderCode.FEIGN_METHOD_ERROR);
            }
            smsApi.buildCompleteMessage(info.getCreator(),BusinessName.SAAS_RESOURCE,info.getOrderNumber());
        }else {
            if (modelId==null||modelId.trim().equals("")) {
                return QueryResponseResult.fail("回退环节ID不能为空,回退失败");
            }
            Map<String,String> map = new HashMap<>();
            map.put("name",BusinessName.SAAS_RESOURCE);
            map.put("order",info.getOrderNumber());
            FallBackVO vo = new FallBackVO();
            vo.setCurrentActivityId(activityId);
            vo.setFallBackModelIds(modelId);
            Map<String,String> resultMap=workflowApi.fallbackOnApproveNotPass(vo, map);
            if (resultMap.get("code") != null && !resultMap.get("code").equals(RequestCode.SUCCESS.getCode())) {
                return QueryResponseResult.fail(resultMap.get("msg"));
            }
        }
        return QueryResponseResult.success(null);
    }

    @Override
    public QueryResponseResult termination(String appInfoId) {
        workflowApi.terminationOrder(appInfoId);
        ServicePublish info = new ServicePublish();
        info.setId(appInfoId);
        info.setStatus(ApplyInfoStatus.DELETE.getCode());
        this.updateById(info);
        return QueryResponseResult.success(null);
    }

    /**
     * 将服务发布申请转换为saas服务
     * @return
     */
//    public boolean convertIntoSaas(ServicePublish servicePublish,String serviceGuid){
//        return saasService.servicePublish2SaaS(servicePublish,serviceGuid);
//    }
//    /**
//     * 将服务发布申请转换为Paas服务
//     * @param servicePublish 申请信息
//     * @return
//     */
//    public boolean convertIntoPaas(ServicePublish servicePublish,ServiceReturnBean returnBean){
//        return paasService.servicePublish2PaaS(servicePublish,returnBean);
//    }
//    /**
//     * 将服务发布申请转换为Daas服务
//     * @param servicePublish 申请信息
//     * @return
//     */
//    public boolean convertIntoDaas(ServicePublishVo servicePublish, ServiceReturnBeanVo returnBean){
//        return daasService.servicePublish2DaaS(servicePublish,returnBean);
//    }



    private String genServiceCode(ServicePublish info) {
        OrgVO org = new OrgVO();
        org.setFullName(info.getJsUnit());
        List<OrgVO> orgList = orgApi.getOrgByParams(org);
        if (CollectionUtils.isNotEmpty(orgList)) {
            org = orgList.get(0);
            StringBuilder sb = new StringBuilder();
            String codePrefix = org.getCode();
            String codeMiddle = null;
            codeMiddle = ServiceCodeUtil.getCode(info.getCategory());
            //todo 查询在iaas、服务发布和应用注册中该机构已创建的申请数量，用于生成5位流水号
//            int iCount = iaasService.count(new QueryWrapper<Iaas>().lambda().eq(Iaas::getResOrg, info.getJsUnit()).isNotNull(Iaas::getServiceCode));
            int pdsCount = this.count(new QueryWrapper<ServicePublish>().lambda()
                    .eq(ServicePublish::getJsUnit, info.getJsUnit())
                    .isNotNull(ServicePublish::getServiceCode));
//            int appCount = saasRegisterMapper.selectCount(new QueryWrapper<SaasRegister>().lambda().eq(SaasRegister::getJsUnit, info.getJsUnit()).isNotNull(Register::getServiceCode));
//            int total = iCount+pdsCount+appCount;
            int total = pdsCount;
            String codeSuffix = String.format("%05d", total+1);
            if (codePrefix != null && codeMiddle != null && codeSuffix != null) {
                sb.append(codePrefix).append(codeMiddle).append(codeSuffix);
            }
            if (sb != null) {
                return sb.toString();
            }
        }
        return null;
    }

    private void saveApi(ServicePublish info) {
        List<ServicePublishApi> apiList = info.getApiList();
        if (apiList != null) {
            for (ServicePublishApi api : apiList) {
                api.setId(null);
                api.setPublishId(info.getId());
                servicePublishApiService.save(api);
                // 保存IP信息
                saveAcIp(api);
                // 保存 API 操作
                saveApiOperation(api);
            }
        }
    }

    private void saveApiOperation(ServicePublishApi api) {
        List<ApiOperationVo> apiOperationList = api.getApiOperationList();
        if (apiOperationList != null) {
            for (ApiOperationVo apiOperation : apiOperationList) {
                apiOperation.setId(null);
                apiOperation.setMasterId(api.getId());
                ApiOperation obj = new ApiOperation();
                BeanUtils.copyProperties(apiOperation, obj);
                apiOperationService.save(obj);
            }
        }
    }

    private void saveAcIp(ServicePublishApi api) {
        List<ApiAcIpVo> ipList = api.getIpList();
        if (ipList != null) {
            for (ApiAcIpVo ip : ipList) {
                ip.setId(null);
                ip.setMasterId(api.getId());
                ApiAcIp obj = new ApiAcIp();
                BeanUtils.copyProperties(ip, obj);
                apiAcIpService.save(obj);
            }
        }
    }

    private void saveBackend(ServicePublish info) {
        List<ServicePublishBackend> backendList = info.getBackendList();
        if (backendList != null) {
            for (ServicePublishBackend backend : backendList) {
                backend.setId(null);
                backend.setPublishId(info.getId());
                servicePublishBackendService.save(backend);
                // 保存服务地址
                saveBackendHost(backend);
            }
        }
    }

    private void saveBackendHost(ServicePublishBackend backend) {
        List<BackendHost> hostList = backend.getHostList();
        if (hostList != null) {
            for (BackendHost host : hostList) {
                host.setId(null);
                host.setMasterId(backend.getId());
                backendHostService.save(host);
            }
        }
    }

    private void saveApiProduct(ServicePublish info) {
        ServicePublishApiProduct apiProduct = info.getApiProduct();
        if (apiProduct != null) {
            apiProduct.setId(null);
            apiProduct.setPublishId(info.getId());
            servicePublishApiProductService.save(apiProduct);
        }
    }
    private List<ServicePublishBackend> getBackendList(String id) {
        List<ServicePublishBackend> backendList = servicePublishBackendService.list(new QueryWrapper<ServicePublishBackend>().lambda()
                .eq(ServicePublishBackend::getPublishId, id));
        if (backendList != null) {
            for (ServicePublishBackend backend : backendList) {
                // 服务地址
                List<BackendHost> hostList = backendHostService.list(new QueryWrapper<BackendHost>().lambda()
                        .eq(BackendHost::getMasterId, backend.getId()));
                backend.setHostList(hostList);
            }
        }
        return backendList;
    }
    private List<ServicePublishApi> getApiList(String id) {
        List<ServicePublishApi> apiList = servicePublishApiService.list(new QueryWrapper<ServicePublishApi>().lambda()
                .eq(ServicePublishApi::getPublishId, id));
        if (apiList != null) {
            for (ServicePublishApi api : apiList) {
                // IP信息
                List<ApiAcIp> ipList = apiAcIpService.list(new QueryWrapper<ApiAcIp>().lambda()
                        .eq(ApiAcIp::getMasterId, api.getId()));
                if(CollectionUtils.isNotEmpty(ipList)) {
                    List<ApiAcIpVo> list = JSON.parseObject(JSON.toJSON(ipList).toString(),
                            new TypeReference<List<ApiAcIpVo>>(){});
                    api.setIpList(list);
                }
                // API操作
                List<ApiOperation> apiOperationList = apiOperationService.list(new QueryWrapper<ApiOperation>().lambda()
                        .eq(ApiOperation::getMasterId, api.getId()));
                if(CollectionUtils.isNotEmpty(apiOperationList)) {
                    List<ApiOperationVo> list = JSON.parseObject(JSON.toJSON(apiOperationList).toString(),
                            new TypeReference<List<ApiOperationVo>>(){});
                    api.setApiOperationList(list);
                }
            }
        }
        return apiList;
    }

    private WorkflowVO getWorkFlowId(String serviceType){
        StringBuilder sb = new StringBuilder("PUBLISH_");
        if("DAAS".equals(serviceType)){
            sb.append("DAAS");
        }else if("SAAS".equals(serviceType)){
            sb.append("SAAS");
        }else if("PAAS".equals(serviceType)){
            sb.append("PAAS");
        }
        WorkflowVO workflow = workflowApi.getWorkflowByDefaultProcess(sb.toString());
        if(workflow !=null){
            return workflow;
        }else {
            return null;
        }
    }

}
