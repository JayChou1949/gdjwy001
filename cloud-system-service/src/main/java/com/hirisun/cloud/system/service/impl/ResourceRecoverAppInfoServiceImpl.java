package com.hirisun.cloud.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.hirisun.cloud.api.system.FilesApi;
import com.hirisun.cloud.api.system.SmsApi;
import com.hirisun.cloud.api.user.UserApi;
import com.hirisun.cloud.common.constant.BusinessName;
import com.hirisun.cloud.common.constant.RedisKey;
import com.hirisun.cloud.common.contains.ApplicationInfoStatus;
import com.hirisun.cloud.common.enumer.ResourceRecoverStatus;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.util.DateUtil;
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.common.vo.CommonCode;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.impl.vo.ImplRequestVo;
import com.hirisun.cloud.model.system.ResourceRecoverSubmitVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workflow.AdvanceBeanVO;
import com.hirisun.cloud.model.workflow.FallBackVO;
import com.hirisun.cloud.model.workflow.WorkflowNodeVO;
import com.hirisun.cloud.redis.lock.DistributeLock;
import com.hirisun.cloud.redis.service.RedisService;
import com.hirisun.cloud.system.bean.ResourceRecover;
import com.hirisun.cloud.system.bean.ResourceRecoverAppInfo;
import com.hirisun.cloud.system.mapper.ResourceRecoverAppInfoMapper;
import com.hirisun.cloud.system.service.IResourceRecoverAppInfoService;
import com.hirisun.cloud.system.service.IResourceRecoverImplService;
import com.hirisun.cloud.system.service.IResourceRecoverService;
import com.hirisun.cloud.system.timer.Timer;
import com.hirisun.cloud.system.vo.SystemCode;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * <p>
 * ???????????????????????? ???????????????
 * </p>
 *
 * @author yyc
 * @since 2020-05-14
 */
@Service
public class ResourceRecoverAppInfoServiceImpl extends ServiceImpl<ResourceRecoverAppInfoMapper, 
	ResourceRecoverAppInfo> implements IResourceRecoverAppInfoService {

    private static final Logger logger = LoggerFactory.getLogger(ResourceRecoverAppInfoServiceImpl.class);

    @Autowired
    private DistributeLock lock;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IResourceRecoverService resourceRecoverService;
//    TODO ??????????????????
//    @Autowired
//    private IWorkflowService workflowService;
//
//    @Autowired
//    private IWorkflowmodelService workflowmodelService;
//
//    @Autowired
//    private IInstanceService instanceService;
//
//    @Autowired
//    private IActivityService activityService;

    @Autowired
    private ResourceRecoverAppInfoMapper resourceRecoverAppInfoMapper;

    @Autowired
    private SmsApi smsApi;
//  TODO ??????????????????
//    @Autowired
//    private ISpeedUpService speedUpService;
//
//    @Autowired
//    private IAppReviewInfoService appReviewInfoService;
    @Autowired
    private FilesApi filesApi;

    @Autowired
    private IResourceRecoverImplService implService;

    @Autowired
    private RedisService redisService;
    
    @Autowired
    private UserApi userApi;

    @Autowired
    private Timer timer;


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public ResponseResult submit(UserVO user, ResourceRecoverSubmitVo submit) {
//      TODO ??????????????????
//        logger.debug("submit -> {}",submit);
//        List<ResourceRecoverSubmitVo.RecoveredUserInfo> recoveredUserInfoList = submit.getRecoveredUserInfoList();
//        if(CollectionUtils.isEmpty(recoveredUserInfoList)){
//            throw new CustomException(SystemCode.RESOURCE_RECOVER_NULL);
//        }
//
//        List<String> recoveredUserName = recoveredUserInfoList.stream().map(ResourceRecoverSubmitVo.RecoveredUserInfo::getApplicant).distinct().collect(Collectors.toList());
//        if(CollectionUtils.isEmpty(recoveredUserName)){
//            throw new CustomException(SystemCode.APPLY_PERSON_NULL);
//        }
//        
//		List<UserVO> allRecoverdUser = userApi.findUserByUserName(recoveredUserName);
//        
//        if(CollectionUtils.isEmpty(allRecoverdUser)){
//            throw new CustomException(SystemCode.GET_APPLY_PERSON_NULL);
//        }
//
//        Workflow workFlow = workflowService.getOne(new QueryWrapper<Workflow>().eq("DEFAULT_PROCESS","RECOVER"));
//        if(workFlow == null){
//            throw new BaseException("??????????????????!");
//        }
//        //?????? ??? ??? ?????? ?????? ???????????????(????????????)
//        for(ResourceRecoverSubmitVo.RecoveredUserInfo recoveredUserInfo:recoveredUserInfoList){
//
//            ResourceRecoverAppInfo info = new ResourceRecoverAppInfo();
//            info.setId(null);
//            info.setCreator(user.getName());
//            info.setCreatorPhone(user.getMobileWork());
//            info.setCreatorIdCard(user.getIdcard());
//            info.setStatus(ApplicationInfoStatus.INNER_REVIEW.getCode());
//            
//            redisService.genOrderNum(RedisKey.KEY_RESOURCE_RECOVER);
//            
//            info.setRecoveredPerson(recoveredUserInfo.getApplicant());
//            info.setRecoveredPersonPhone(recoveredUserInfo.getApplicantPhone());
//
//            Optional<UserVO> recoveredUserOp =allRecoverdUser.stream().filter(item->
//                StringUtils.equals(item.getName(),recoveredUserInfo.getApplicant())
//                        && StringUtils.equals(item.getOrgName(),recoveredUserInfo.getApplicantOrgName())).findFirst();
//
//            if(!recoveredUserOp.isPresent()){
//                logger.error("Can not found user:{} {}",recoveredUserInfo.getApplicant(),recoveredUserInfo.getApplicantOrgName());
//                //?????? ?????????????????????
//                throw new BaseException("???????????????"+recoveredUserInfo.getApplicant()+":"+recoveredUserInfo.getApplicantOrgName());
//            }
//
//            info.setRecoveredPersonIdCard(recoveredUserOp.get().getIdcard());
//            info.setWorkFlowId(workFlow.getId());
//            info.setWorkFlowVersion(workFlow.getVersion());
//
//            resourceRecoverAppInfoMapper.insert(info);
//
//            logger.debug("app id -> {}",info.getId());
//
//            //?????????????????????????????????
//            resourceRecoverService.update(new ResourceRecover(),new UpdateWrapper<ResourceRecover>().lambda()
//                                            .eq(ResourceRecover::getApplicant,recoveredUserInfo.getApplicant())
//                                            .eq(ResourceRecover::getApplicantPhone,recoveredUserInfo.getApplicantPhone())
//                                            .eq(ResourceRecover::getApplicantOrgName,recoveredUserInfo.getApplicantOrgName())
//                                            .eq(ResourceRecover::getImportTimeStr,recoveredUserInfo.getImportTime())
//                                            .eq(ResourceRecover::getStatus,ResourceRecoverStatus.UN_PROCESSED.getCode())
//                                            .set(ResourceRecover::getStatus, ResourceRecoverStatus.PROCESSING.getCode())
//                                            .set(ResourceRecover::getRefId,info.getId()));
//
//            //????????????????????????
//            SubpageParam param = new SubpageParam();
//            param.setFiles(submit.getFileList());
//            param.setRefId(info.getId());
//			filesApi.refFiles(param);
//
//           R r =  instanceService.launchInstanceOfWorkFlow(user.getIdcard(), info.getWorkFlowId(),info.getId());
//
//
//            Workflowmodel workflowmodel = workflowmodelService.getOne(new QueryWrapper<Workflowmodel>().eq("WORKFLOWID",workFlow.getId()).eq("modelname", ModelName.RECOVERED.getName()).eq("VERSION",workFlow.getVersion()));
//
//            Map<String, String> modelMapToPerson = new HashMap<>();
//            //todo???????????????????????????????????????
//            modelMapToPerson.put(workflowmodel.getId(), recoveredUserOp.get().getIdcard());
//            AdvanceBeanVO advanceBeanVO = new AdvanceBeanVO();
//            advanceBeanVO.setCurrentActivityId(r.get("data").toString());
//            advanceBeanVO.setModelMapToPerson(modelMapToPerson);
//
//            Map<String,String> map = new HashMap<>();
//            map.put("name", BusinessName.RECOVER);
//            map.put("order", info.getOrderNumber());
//            getMsgTime(info,map);//??????map
//            activityService.advanceCurrentActivity(advanceBeanVO, map);
//            smsApi.buildSuccessMessage(user.getIdcard(), BusinessName.RECOVER, info.getOrderNumber());
//            //            sendMsg(info.getRecoveredPersonIdCard(),info);
//            //????????????????????????48??????????????????????????????????????????
//            //1.??????????????????48???????????????
//            //2.????????????????????????????????????????????????????????????
//            try{
//                String expendTime="48:00:00";
//                timer.startRecoverCheck(DateUtil.dateAdd(info.getCreateTime(),expendTime),info.getRecoveredPersonIdCard(),info);//?????????????????????????????????
//            }catch (Exception e){
//               e.printStackTrace();
//            }
//        }
//
        return ResponseResult.success();
    }

    @Override
    public ResponseResult initWorkFlow() {
//      TODO ??????????????????
//        Workflow workFlow = workflowService.getOne(new QueryWrapper<Workflow>().eq("DEFAULT_PROCESS","RECOVER"));
//        if(workFlow != null){
//            throw new BaseException("??????????????????");
//        }
//        Workflow workflow = new Workflow();
//        workflow.setFlowStatus(1);
//        workflow.setId(UUIDUtil.getUUID());
//        workflow.setDefaultProcess("RECOVER");
//        workflow.setWorkflowname("??????????????????");
//        workflow.insert();
//
//        String workflowId = workflow.getId();
//
//        List<Workflowmodel> models = new ArrayList<>();
//
//        // ????????????
//        Workflowmodel carry =  new Workflowmodel(workflowId, ModelName.CARRY.getName(),null);
//        carry.setDefaulthandleperson("410184198209096919");
//        carry.setVersion(0);
//        models.add(carry);
//        // ??????????????????
//        Workflowmodel bigData =  new Workflowmodel(workflowId, ModelName.APPROVE.getName(), carry.getId());
//        bigData.setDefaulthandleperson("410184198209096919");
//        bigData.setVersion(0);
//        models.add(bigData);
//
//        //??????????????????????????????
//        Workflowmodel recovered =new Workflowmodel(workflowId,ModelName.RECOVERED.getName(),bigData.getId());
//        recovered.setVersion(0);
//        models.add(recovered);
//
//        //??????
//        Workflowmodel apply = new Workflowmodel(workflowId,ModelName.APPLY.getName(),recovered.getId());
//        apply.setVersion(0);
//        apply.setIsstart(0);
//        models.add(apply);
//
//        workflowmodelService.saveBatch(models);
        return ResponseResult.success();
    }

    /**
     * ??????
     *
     * @param id ?????????ID
     * @return map
     */
    @Override
    public Map<String, Object> detail(String id,UserVO user) {
		return null;
//      TODO ??????????????????
//        ResourceRecoverAppInfo info = getBizData(id);
//        Instance instance = instanceService.getInstanceByBusinessId(id);
//        logger.debug("detail instanceId -> {}",instance.getId());
//
//        Activity activity = null;
//        if (null!=instance){
//            info.setWorkFlowId(instance.getWorkflowid());
//            info.setWorkFlowVersion(instance.getWorkflowversion());
//            List<Activity> activitys = activityService.list(new QueryWrapper<Activity>().lambda()
//                    .eq(Activity::getInstanceid,instance.getId()).eq(Activity::getHandlepersonids,user.getIdcard()).eq(Activity::getActivitystatus,"??????").isNull(Activity::getActivitytype));
//            if (null==activitys||activitys.size()==0){
//                activitys = activityService.list(new QueryWrapper<Activity>().lambda()
//                        .eq(Activity::getInstanceid,instance.getId()).eq(Activity::getHandlepersonids,user.getIdcard()).eq(Activity::getActivitystatus,"??????").eq(Activity::getActivitytype,"adviser"));
//            }
//            if (null!=activitys&&activitys.size()>0) activity = activitys.get(0);
//        }
//        String activityId = "";
//        if (activity!=null) {
//            activityId = activity.getId();
//            logger.debug("detail activityId -> {}",activity.getId());
//            Workflowmodel workflowmodel=workflowmodelService.getById(activity.getModelid());
//            if (null==workflowmodel){
//                return R.error("?????????????????????");
//            }
//            String  modelName=workflowmodel.getModelname();
//            if (modelName.equals(ModelName.RECOVERED.getName())||modelName.equals(ModelName.APPROVE.getName())) {
//                info.setCanReview(true);
//            }
//            if (modelName.equals(ModelName.CARRY.getName())) {
//                info.setCanImpl(true);
//            }
//
//        }
//
//        List<AppReviewInfo> allReviewInfo = appReviewInfoService.getAllReviewInfoByAppInfoId(id);
//        Map bean = new HashMap<>();
//        if (allReviewInfo!=null){
//            Map<String, List<AppReviewInfo>> reviews = allReviewInfo.stream().collect(Collectors.groupingBy(a->a.getFlowStepId()+""));
//            bean.put("review",reviews);
//        }
//
//        WorkFlowModelVo modelVo=workflowmodelService.getWorkFlowDefinition(id);
//        Map<String,String> status = Maps.newHashMap();
//        status = modelStatusMap(modelVo,status);
//        String carryStatus = status.get(ModelName.CARRY.getName());
//        if(StringUtils.equals(carryStatus,"underway") || StringUtils.equals(carryStatus,"finished")){
//            if(!StringUtils.equals(status.get(ModelName.APPROVE.getName()),"finished")){
//                setApproveModelFinished(modelVo);
//            }
//        }
//        // ????????????
//        if (modelVo!=null&&info!=null) {
//            bean.put("model",modelVo);
//            bean.put("bizData",info);
//            bean.put("activityId",activityId);
//            return R.ok(bean);
//        }else {
//            return R.error("???????????????");
//        }

    }

    /**
     * ??????????????????????????????
     * @param id ??????ID
     * @return
     */
    private ResourceRecoverAppInfo getBizData(String id){
//        ResourceRecoverAppInfo info = this.getById(id);
//        Instance instance = instanceService.getInstanceByBusinessId(id);
//        logger.debug("detail instanceId -> {}",instance.getId());
//        List<ResourceRecover> serverList = resourceRecoverService.list(new QueryWrapper<ResourceRecover>().lambda().eq(ResourceRecover::getRefId,id));
//        info.setServerList(serverList);
//
//        List<ResourceRecoverImpl> implServerList = implService.list(new QueryWrapper<ResourceRecoverImpl>().lambda().eq(ResourceRecoverImpl::getAppInfoId,id));
//        info.setImplServerList(implServerList);
//
//        List<Files> filesList = filesService.list(new QueryWrapper<Files>().lambda().eq(Files::getRefId, id));
//        info.setFileList(filesList);
//
//        // ????????????
//        List<AppReviewInfo> allReviewInfo = appReviewInfoService.getAllReviewInfoByAppInfoId(id);
//        for (AppReviewInfo reviewInfo:allReviewInfo){
//            List<Files> reviewFileList = filesService.list(new QueryWrapper<Files>().lambda().eq(Files::getRefId, reviewInfo.getId()));
//            reviewInfo.setFileList(reviewFileList);
//        }
//        info.setReviewList(allReviewInfo);
//        // ??????????????????
//        AppReviewInfo implInfo = null;
//        AppReviewInfo lastReviewInfo = appReviewInfoService.getLastPassReviewInfoByAppInfoId(id);
//        if (lastReviewInfo != null && "2".equals(lastReviewInfo.getrType())) {
//            // ???????????????????????????????????????
//            implInfo = lastReviewInfo;
//            List<Files> implFileList = filesService.list(new QueryWrapper<Files>().lambda().eq(Files::getRefId, implInfo.getId()));
//            implInfo.setFileList(implFileList);
//            info.setImpl(implInfo);
//        }
//        return info;
    	return null;
    }

    /**
     * ??????????????????????????????
     * @param vo
     * @return
     */
    private Map<String,String> modelStatusMap(WorkflowNodeVO vo,Map<String,String> status){
//        List<WorkFlowModelVo> nextVoList = vo.getNextModels();
//        if(CollectionUtils.isNotEmpty(nextVoList)){
//            WorkFlowModelVo nextVo = nextVoList.get(0);
//            if(nextVo != null){
//                if(StringUtils.equals(nextVo.getModelName(),ModelName.APPROVE.getName()) ||
//                        StringUtils.equals(nextVo.getModelName(),ModelName.CARRY.getName())){
//                    status.put(nextVo.getModelName(),nextVo.getModelStatusCode());
//                }
//                modelStatusMap(nextVo,status);
//            }
//        }
        return status;
    }

    /**
     * ????????????????????????????????????
     * @param vo
     */
    private void setApproveModelFinished(WorkflowNodeVO vo){
//        List<WorkFlowModelVo> nextVoList = vo.getNextModels();
//        if(CollectionUtils.isNotEmpty(nextVoList)){
//            WorkFlowModelVo nextVo = nextVoList.get(0);
//            if(nextVo != null && !StringUtils.equals(nextVo.getModelName(),ModelName.APPROVE.getName())){
//                setApproveModelFinished(nextVo);
//            }
//            nextVo.setModelStatusCode("finished");
//            nextVo.setModelStatus("?????????");
//        }
    }


    /**
     * ??????
     * @param user ????????????
     * @param vo ??????VO
     * @return R
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public ResponseResult approve(UserVO user, FallBackVO vo){


//        AppReviewInfo approve = vo.getApprove();
//        approve.setCreator(user.getIdcard());
//
//
//        ResourceRecoverAppInfo info = this.getById(vo.getApprove().getAppInfoId());
//
//        Activity activity =activityService.getById(vo.getCurrentActivityId());
//        Workflowmodel currentModel = workflowmodelService.getById(activity.getModelid());
//
//
//
//        Map<String,String> map = new HashMap<>();
//        map.put("name",BusinessName.RECOVER);
//        map.put("order",info.getOrderNumber());
//        getMsgTime(info,map);//??????map
//
//        //????????????????????????
//        if(StringUtils.equals(ModelName.RECOVERED.getName(),currentModel.getModelname())){
//            //????????????
//            if("1".equals(approve.getResult())){
//                //?????????????????????????????????????????????????????????????????????????????????
//                approve.setResourceRecoveredAgree(1);
//                info.setRecoveredAgree(1);
//            }else if("0".equals(approve.getResult())){
//                //????????????????????????????????????????????????????????????????????????????????????????????????
//                approve.setResourceRecoveredAgree(0);
//                info.setRecoveredAgree(0);
//            }
//            R r = activityService.advanceCurrentActivity(vo.getCurrentActivityId(), approve,map);
//            Object model = r.get("data");
//            if (ModelName.CARRY.getName().equals(model)){
//                info.setStatus(ApplicationInfoStatus.IMPL.getCode());
//            }else {
//                info.setStatus(ApplicationInfoStatus.RESENT.getCode());
//            }
//        }else if(StringUtils.equals(ModelName.APPROVE.getName(),currentModel.getModelname())){ //????????????
//            //?????????????????????????????????????????????????????????????????????
//            if(info.getRecoveredAgree()==0){
//                //?????????????????????????????????,???????????????????????????????????????????????????
//                if("1".equals(approve.getResult())){
////                    Workflowmodel fallbackModel = workflowmodelService.getById(vo.getFallBackModelIds());
////                    if(StringUtils.equals(ModelName.RECOVERED.getName(),fallbackModel.getModelname())){
////                        activityService.fallbackOnApproveNotPass(vo, null);
////                        info.setStatus(ApplicationInfoStatus.REVIEW_REJECT.getCode());
////
////                        messageProvider.sendMessageAsync(messageProvider.buildRejectMessage(info.getCreator(), BusinessName.RECOVER));
////                    }
//                    R r = activityService.advanceCurrentActivity(vo.getCurrentActivityId(), approve,map);
//                    Object model = r.get("data");
//                    if (ModelName.CARRY.getName().equals(model)){
//                        info.setStatus(ApplicationInfoStatus.IMPL.getCode());
//                    }else {
//                        info.setStatus(ApplicationInfoStatus.RESENT.getCode());
//                    }
//                }else if("0".equals(approve.getResult())){ //?????????????????????,?????????????????????
//                    //???????????????????????????????????????????????????
//                    activityService.fallbackOnApproveNotPass(vo, null);
//                    info.setStatus(ApplicationInfoStatus.REVIEW_REJECT.getCode());
//                    smsApi.buildRejectMessage(info.getCreator(), BusinessName.RECOVER);
//                }
//            }
//        }
//
//        resourceRecoverAppInfoMapper.updateById(info);
        return ResponseResult.success();
    }

    /**
     * ??????
     *
     * @param page     ????????????
     * @param name   ?????????
     * @param orderNum ????????? 1 ????????? 2 ????????? 3?????????
     * @param status   ??????
     * @return
     */
    @Override
    public IPage<ResourceRecoverAppInfo> getPage(IPage<ResourceRecoverAppInfo> page, UserVO user,String name, String orderNum, String status) {

//        Map<String,String> params = Maps.newHashMap();
//        params.put("name",name);
//        params.put("orderNum",orderNum);
//        params.put("status",status);
//        page =  resourceRecoverAppInfoMapper.getPage(page,params);
//        List<ResourceRecoverAppInfo> records = page.getRecords();
//        if(CollectionUtils.isNotEmpty(records)){
//            speedUpService.dealProcessingPersonResourceRecovered(records,user);
//        }
        return page;
    }


    @Transactional(rollbackFor = Throwable.class)
    @Override
    public ResponseResult impl(UserVO user, String id, String modelId, String activityId, ImplRequestVo implRequest) throws Exception {
//        ResourceRecoverAppInfo info = getBizData(id);
//        Activity activity =activityService.getById(activityId);
//        if (implRequest.getResult() == null) {
//            throw new BaseException("?????????????????????");
//        }
//        if (!"1".equals(implRequest.getResult())) {
//            implRequest.setResult("0");
//        }
//        Map<String, Object> param = new HashMap<>();
//        param.put("info", info);
//        param.put("implRequest", implRequest);
//
//        String uuid = UUIDUtil.getUUID();
//        String lockKey = info.getId().intern();
//        try {
//            if (lock.lock(lockKey, uuid)) {
//                implService.saveImpl(user, param, activity.getModelid());
//            } else {
//                throw new BaseException("????????????");
//            }
//        }catch (Exception e){
//            throw e;
//        }finally {
//            lock.unlock(lockKey, uuid);
//        }
//        if ("1".equals(implRequest.getResult())){
//            //ServiceReturnBean returnBean = getTestReturnBean(false);
//            activityService.advanceCurrentActivity(activity,info.getCreator());
////            sendMsg(user,info);
//            smsApi.buildCompleteMessage(info.getCreator(), BusinessName.RECOVER, info.getOrderNumber());
//        }else {
//            if (modelId==null||modelId.trim().equals("")) {
//                return R.error(201, "????????????ID????????????,????????????");
//            }
//            Map<String,String> map = new HashMap<>();
//            map.put("name",BusinessName.RECOVER);
//            map.put("order",info.getOrderNumber());
//            FallBackVO vo = new FallBackVO();
//            vo.setCurrentActivityId(activityId);
//            vo.setFallBackModelIds(modelId);
//            activityService.fallbackOnApproveNotPass(vo, map);
//        }
        return ResponseResult.success();
    }

    /**
     * ??????
     *
     * @param id ??????ID
     * @return
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public ResponseResult delete(String id) {
//       if(StringUtils.isBlank(id)){
//           throw new BaseException("???????????????");
//       }
//       //???????????????
//       this.update(new ResourceRecoverAppInfo(),new UpdateWrapper<ResourceRecoverAppInfo>().lambda()
//                            .eq(ResourceRecoverAppInfo::getId,id)
//                            .set(ResourceRecoverAppInfo::getStatus,ApplicationInfoStatus.DELETE.getCode()));
//       activityService.terminationInstanceOfWorkFlow(id);
       return ResponseResult.success();
    }

    /**
     * ????????????????????????????????????????????????,????????????????????????????????????????????????
     */
    public void queryUntreatedRecover(){
//        logger.debug("??????????????????????????????????????????????????????");
//        List<ResourceRecoverAppInfo> recoverList = resourceRecoverAppInfoMapper.queryUntreatedRecover();
//        if(recoverList==null||recoverList.size()==0){return ;}
//        //??????????????????????????????????????????????????????
//        ConcurrentMap<String,User> userMap=new ConcurrentHashMap<String,User>();
//        //?????????????????????????????????????????????????????????????????????????????????????????????????????????
//        recoverList.forEach(entry->{
//            //??????????????????
//            try {
//                Date indate=DateUtil.parseDate(DateUtil.dateAdd(entry.getCreateTime(),48*60),"yyyy-MM-dd HH:mm:ss");
//                long timeLong=DateUtil.dateDiff("millsecond",indate,new Date());
//                if(timeLong<=999){//???????????????????????????????????????????????????
//                    //?????????????????????
//                    sendMsg(entry.getRecoveredPersonIdCard(),entry);
//                    //????????????
//                    entry.setStatus(ApplicationInfoStatus.RESENT.getCode());
//                    this.updateById(entry);
//                    logger.debug("??????????????????,id:"+entry.getId());
//                }else{//??????????????????????????????????????????
//                    Date date=DateUtil.parseDate(DateUtil.dateAdd(entry.getCreateTime(),48*60),"yyyy-MM-dd HH:mm:ss");
//                    long l = date.getTime()-new Date().getTime();
//                    int hour=48-(((int)l)/(1000*60*60));//??????????????????????????????????????????????????????
//                    String expendTime="01:00:00";//00:00:00
//                    if(hour>0&&hour<10){
//                        expendTime="0"+hour+":00:00";
//                    }else if(hour>=10){
//                        expendTime=hour+":00:00";
//                    }
////                    User user=null;
////                    if(userMap.get(entry.getRecoveredPersonIdCard())!=null){
////                        user=userMap.get(entry.getRecoveredPersonIdCard());
////                    }else{
////                        user=userService.getById(entry.getRecoveredPersonIdCard());
////                        if(user!=null){
////                            userMap.put(user.getIdcard(),user);
////                        }
////                    }
//                    logger.debug("??????????????????????????????"+expendTime+"????????????????????????");
//                    //??????????????????????????????????????????????????????
////                    if(user!=null) {
//                        timer.startRecoverCheck(DateUtil.dateAdd(entry.getCreateTime(), expendTime), entry.getRecoveredPersonIdCard(), entry);//?????????????????????????????????
////                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//        });
    }

    /**
     * ??????????????????????????????
     * @param info
     */
    public void sendMsg(String userId,ResourceRecoverAppInfo info){
        //?????????????????????????????????????????????
//        ResourceRecover resourceRecover=resourceRecoverService.getOne(new QueryWrapper<ResourceRecover>().eq("ref_Id",info.getId()));
//        if(resourceRecover==null){
//            return ;
//        }
//        //????????????
//        String[] shrinkTime=null;
//        String month = "";
//        String day = "";
//        if(resourceRecover.getShrinkTime()!=null){
//            shrinkTime=resourceRecover.getShrinkTime().split(" ")[0].split("-");
//        }else{
//            shrinkTime=DateUtil.formateDate(info.getCreateTime(),"yyyy-MM-dd").split("-");
//        }
//        month = shrinkTime[1];
//        day = shrinkTime[2];
        //????????????
//        messageProvider.sendMessageAsync(messageProvider.buildRecoverMessage(userId, BusinessName.RECOVER, info.getOrderNumber(),month,day));
        UserVO user = userApi.getUserById(userId);
        if(user!=null){
            smsApi.buildSuccessMessage(user.getIdcard(), BusinessName.RECOVER, info.getOrderNumber());
        }
    }
    /**
     * ??????????????????????????????????????????,?????????map
     * @param info
     */
    public void getMsgTime(ResourceRecoverAppInfo info,Map<String,String> map){
        //?????????????????????????????????????????????
        ResourceRecover resourceRecover=resourceRecoverService.getOne(new QueryWrapper<ResourceRecover>().eq("ref_Id",info.getId()));
        //????????????
        String[] shrinkTime=null;
        String month = "";
        String day = "";
        if(resourceRecover!=null&&resourceRecover.getShrinkTime()!=null){
            shrinkTime=resourceRecover.getShrinkTime().split(" ")[0].split("-");
        }else{
            shrinkTime=DateUtil.formateDate(info.getCreateTime(),"yyyy-MM-dd").split("-");
        }
        month = shrinkTime[1];
        day = shrinkTime[2];
        map.put("month",month);
        map.put("day",day);
    }
}
