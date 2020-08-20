package com.hirisun.cloud.order.service.activity.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.api.system.FilesApi;
import com.hirisun.cloud.common.enumer.ModelName;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.common.vo.CommonCode;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.model.app.param.SubpageParam;
import com.hirisun.cloud.model.file.FilesVo;
import com.hirisun.cloud.model.service.AppReviewInfoVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workflow.AdvanceBeanVO;
import com.hirisun.cloud.model.workflow.ApplicationFeedbackVo;
import com.hirisun.cloud.model.workflow.FallBackVO;
import com.hirisun.cloud.order.bean.activity.Activity;
import com.hirisun.cloud.order.bean.application.AppReviewInfo;
import com.hirisun.cloud.order.bean.application.ApplicationFeedback;
import com.hirisun.cloud.order.bean.instance.Instance;
import com.hirisun.cloud.order.bean.workflow.Workflowmodel;
import com.hirisun.cloud.order.mapper.activity.ActivityMapper;
import com.hirisun.cloud.order.mapper.application.AppReviewInfoMapper;
import com.hirisun.cloud.order.mapper.application.ApplicationFeedbackMapper;
import com.hirisun.cloud.order.mapper.instance.InstanceMapper;
import com.hirisun.cloud.order.mapper.workflow.WorkflowmodelMapper;
import com.hirisun.cloud.order.service.activity.IActivityService;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *  服务实现类
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {
    @Autowired
    private ActivityMapper activityDao;
    @Autowired
    private WorkflowmodelMapper workFlowModelDao;
    @Autowired
    private InstanceMapper instanceDao;
    @Autowired
    AppReviewInfoMapper appReviewInfoMapper;
    @Autowired
    ApplicationFeedbackMapper applicationFeedbackMapper;
//    @Autowired
//    private MessageProvider messageProvider;

    @Autowired
    private FilesApi filesApi;

    private static final Logger logger = LoggerFactory.getLogger(ActivityServiceImpl.class);

    @Transactional
    @Override
    public ResponseResult advanceCurrentActivity(AdvanceBeanVO advanceBeanVO) {
      return advanceCurrentActivity(advanceBeanVO,null);
    }

    /**
     * 流转
     * @param advanceBeanVO 流转VO
     * @param map 短信消息map
     * @return
     */
    @Override
    public ResponseResult advanceCurrentActivity(AdvanceBeanVO advanceBeanVO, Map<String, String> map) {
        Activity currentActivity = null;
        Map<String, String> modelMapToPerson= null;
        if (advanceBeanVO!=null) {
            String currentActivityId=advanceBeanVO.getCurrentActivityId();
            if (currentActivityId!=null&&!currentActivityId.equals("")) {
                currentActivity=activityDao.selectById(currentActivityId);
            }else {
            	throw new CustomException(CommonCode.ACTIVITY_ID_NULL);
            }
            //下一流转环节的处理人Map
            modelMapToPerson=advanceBeanVO.getModelMapToPerson();
        }else {
        	throw new CustomException(CommonCode.CIRCULATION_INFO_NULL);
        }
        //下级环节集合
        List<Workflowmodel> nextModels= null;
        if (currentActivity!=null) {
            String currentModelId=currentActivity.getModelid();
            if (currentModelId==null||"".equals(currentModelId)) {
            	throw new CustomException(CommonCode.CIRCULATION_INFO_NULL);
            }

            if (!currentActivity.getActivitystatus().equals("待办")) {
            	throw new CustomException(CommonCode.PROCESS_LINK_NULL);
            }

            nextModels=workFlowModelDao.getSubordinateModel(currentModelId);
            //如果没有下级环节，则直接完成当前环节，完成流程实例
            if (nextModels==null||nextModels.size()==0) {
                currentActivity.setActivitystatus("已提交");
                currentActivity.setHandletime(new Date());
                activityDao.updateById(currentActivity);
                Instance instance=instanceDao.selectById(currentActivity.getInstanceid());
                instance.setCompletetime(new Date());
                instance.setInstancestatus("已完成");
                instanceDao.updateById(instance);
                return QueryResponseResult.success("流转成功");
            }
        }else {
        	throw new CustomException(CommonCode.LINK_NULL);
        }

       /* if (modelMapToPerson.size()<1) {
            throw new BaseException( "办理人不能为空", 201);
        }*/
        //用来判断是否审批不通过跳级流转到最后一个环节
        Integer jujeAdvaceToLastModel=0;
        for (Workflowmodel model:nextModels) {
            //获取当前环节指定的办理人
            //判断定义的下级环节是否存在于前台传入需要流传的下级环节中,如果不存在,直接进入下一个循环
            if (!modelMapToPerson.containsKey(model.getId())) {
                jujeAdvaceToLastModel++;
                continue;
            }
            //取得对应环节的处理人
            String personids=modelMapToPerson.get(model.getId());
            //如果处理人为空则取出默认处理人
            if (personids==null||personids.trim().equals("")) {
                personids=model.getDefaulthandleperson();
                //如果默认处理人也为空则直接返回提示信息给前台
                if (personids==null||personids.trim().equals("")) {
                	throw new CustomException(CommonCode.FLOW_HANDLER_NULL);
                }
            }
            //把办理人分割成数组
            String personArr[]=personids.split(",");
            if (personArr.length==0) {
            	throw new CustomException(CommonCode.HANDLER_NULL);
            }
            //获取需要的参与人对应关系
            Map<String, String> adviserMap=advanceBeanVO.getModelMapToAdviser();
            String adviserIds=null;
            if (adviserMap!=null) {
                adviserIds=adviserMap.get(model.getId());
            }
            //把参与人分割成数组
            String adviserArr[]=null;
            if (adviserIds!=null) {
                adviserArr=adviserIds.split(",");
            }
            if (map!=null) {
                handleFlow(model, currentActivity, personArr,adviserArr,advanceBeanVO,map);
            } else {
                handleFlow(model, currentActivity, personArr,adviserArr,advanceBeanVO);}
        }
        if (jujeAdvaceToLastModel>0&&modelMapToPerson.size()==1) {
            Set<String> set=modelMapToPerson.keySet();
            String id="";
            for (String key:set) {
                id=key;
            }
            Workflowmodel model=workFlowModelDao.selectById(id);
            if (model!=null) {
                String [] personArr=modelMapToPerson.get(id).split(",");
                if (personArr.length==0) {
                	throw new CustomException(CommonCode.HANDLER_NULL);
                }
                if (map!=null){ handleFlow(model, currentActivity, personArr,null,advanceBeanVO,map);}
                else{
                    handleFlow(model, currentActivity, personArr,null,advanceBeanVO);}
            }else {
            	throw new CustomException(CommonCode.LINK_END_NULL);
            }
        }
        //当前流转信息更新为已提交,同级(其它处理人)在handleFlow已处理为已抢占
        currentActivity.setHandletime(new Date());
        currentActivity.setActivitystatus("已提交");
        activityDao.updateById(currentActivity);
        return ResponseResult.success();
    }

    @Transactional
    @Override
    public ResponseResult advanceCurrentActivity(String currentActivityId,AppReviewInfoVo approve) {
        return advanceCurrentActivity(currentActivityId,approve,null);
    }

    @Override
    public ResponseResult advanceCurrentActivity(String currentActivityId, 
    		AppReviewInfoVo approve, Map<String, String> map) {
        logger.info("advanceCurrentActivity: currentActivityId -> {} ,approve ->{}",
                currentActivityId,approve);

        Activity currentActivity = null;
        Map<String, String> modelMapToPerson=new HashMap<String, String>();
        if (currentActivityId!=null&&!currentActivityId.equals("")) {
            currentActivity=activityDao.selectById(currentActivityId);
        }else {
        	throw new CustomException(CommonCode.ACTIVITY_ID_NULL);
        }
        if (approve==null) {
        	return QueryResponseResult.fail("审批信息不能为空");
        }
        if (approve.getAppInfoId()==null||approve.getAppInfoId().trim().equals("")) {
        	return QueryResponseResult.fail("申请信息ID不能为空");
        }
        Workflowmodel curmodel = workFlowModelDao.selectById(currentActivity.getModelid());
        approve.setStepName(curmodel.getModelname());
        approve.setFlowStepId(currentActivity.getModelid());
        logger.debug("advanceCurrentActivity approve -> {}",approve);
        
        AppReviewInfo appReviewInfo = new AppReviewInfo();
        BeanUtils.copyProperties(approve, appReviewInfo);
        
        if (approve.getId()==null||approve.getId().trim().equals("")) {
            logger.debug("advanceCurrentActivity approve insert -> {}",approve);
            appReviewInfoMapper.insert(appReviewInfo);
        }else {
            logger.debug("advanceCurrentActivity approve update -> {}",approve);
            appReviewInfoMapper.updateById(appReviewInfo);
        }
        //审批意见关联文件
        List<FilesVo> filesList = approve.getFileList();
        if(CollectionUtils.isNotEmpty(filesList)){
        	SubpageParam param = new SubpageParam();
        	param.setFiles(filesList);
        	param.setRefId(approve.getId());
			filesApi.refFiles(param );
//            filesService.refFiles(filesList,approve.getId());
        }
        //下级环节集合
        List<Workflowmodel> nextModels=new ArrayList<>();
        if (currentActivity!=null) {
            String currentModelId=currentActivity.getModelid();
            if (currentModelId==null||"".equals(currentModelId)) {
            	throw new CustomException(CommonCode.PROCESS_LINK_NULL);
            }

            if (!currentActivity.getActivitystatus().equals("待办")) {
            	throw new CustomException(CommonCode.REPEATED_PROCESSING);
            }

            logger.info("advanceCurrentActivity: currentModelId -> {}",currentModelId);
            if(approve.getRdbAddAccount() == 1 || approve.getResourceRecoveredAgree() == 1){ //如果是关系型数据库新增账号类型或者资源回收被回收人同意回收,下级环节为业务办理
                Instance instance = instanceDao.selectById(currentActivity.getInstanceid());
                nextModels = workFlowModelDao.selectList(new QueryWrapper<Workflowmodel>().lambda()
                        .eq(Workflowmodel::getWorkflowid,instance.getWorkflowid())
                        .eq(Workflowmodel::getVersion,instance.getWorkflowversion())
                        .and(i->i
                        .eq(Workflowmodel::getModelname,ModelName.CARRY.getName())
                        .or()
                        .eq(Workflowmodel::getModeltype,2)
                         )
                );
                logger.info("rdb nextModels -> {}",nextModels);
            }else {
                nextModels=workFlowModelDao.getSubordinateModel(currentModelId);
            }
            logger.info("advanceCurrentActivity: nextModels -> {}",nextModels);
            //如果没有下级环节，则直接完成当前环节，完成流程实例
            if (nextModels==null||nextModels.size()==0) {
                currentActivity.setActivitystatus("已提交");
                currentActivity.setHandletime(new Date());
                activityDao.updateById(currentActivity);
                Activity activity = new Activity();
                activity.setHandletime(new Date());
                activity.setActivitystatus("已抢占");
                int updateR=activityDao.update(activity,new QueryWrapper<Activity>().eq("modelid",currentActivity.getModelid())
                        .eq("instanceid",currentActivity.getInstanceid()).ne("id",currentActivity.getId()));

                Instance instance=instanceDao.selectById(currentActivity.getInstanceid());
                instance.setCompletetime(new Date());
                instance.setInstancestatus("已完成");
                instanceDao.updateById(instance);
                return QueryResponseResult.success("finished");
            }
        }else {
        	throw new CustomException(CommonCode.LINK_NULL);
        }
        String next = "";//next记录环节名
        for (Workflowmodel model:nextModels) {
            //取得对应环节的处理人
            String personids=model.getDefaulthandleperson();
            //如果默认处理人也为空则直接返回提示信息给前台
            if (personids==null||personids.trim().equals("")) {
            	throw new CustomException(CommonCode.FLOW_HANDLER_NULL);
            }
            //把办理人分割成数组
            String personArr[]=personids.split(",");
            if (personArr.length==0) {
            	throw new CustomException(CommonCode.HANDLER_NULL);
            }
            //获取需要的参与人对应关系
            String adviserIds=model.getAdviserperson();
            //把参与人分割成数组
            String adviserArr[]=null;
            if (adviserIds!=null) {
                adviserArr=adviserIds.split(",");
            }
            next = model.getModelname();
            if (map==null){
                //model:下一环节 ，currentActivity:当前流转ID，personArr：处理人数组，adviserArr:参与人数组
            handleFlow(model, currentActivity, personArr,adviserArr,null);
            }else{
                handleFlow(model, currentActivity, personArr,adviserArr,null,map);
            }
        }
        currentActivity.setHandletime(new Date());
        currentActivity.setActivitystatus("已提交");
        activityDao.updateById(currentActivity);
        //返回下一环节的名字
        return QueryResponseResult.success(next);
    }
    @Override
    public ResponseResult advanceCurrentActivityTemp(String currentActivityId, 
    		AppReviewInfoVo approve, Map<String, String> map) {
        logger.info("advanceCurrentActivity: currentActivityId -> {} ,approve ->{}",
                currentActivityId,approve);

        Activity currentActivity = null;
        Map<String, String> modelMapToPerson=new HashMap<String, String>();
        if (currentActivityId!=null&&!currentActivityId.equals("")) {
            currentActivity=activityDao.selectById(currentActivityId);
        }else {
        	throw new CustomException(CommonCode.ACTIVITY_ID_NULL);
        }
        if (approve==null) {
        	return QueryResponseResult.fail("审批信息不能为空");
        }
        if (approve.getAppInfoId()==null||approve.getAppInfoId().trim().equals("")) {
        	return QueryResponseResult.fail("申请信息ID不能为空");
        }
        Workflowmodel curmodel = workFlowModelDao.selectById(currentActivity.getModelid());
        approve.setStepName(curmodel.getModelname());
        approve.setFlowStepId(currentActivity.getModelid());
        
        AppReviewInfo appReviewInfo = new AppReviewInfo();
        BeanUtils.copyProperties(approve, appReviewInfo);
        
        logger.debug("advanceCurrentActivity approve -> {}",approve);
        if (approve.getId()==null||approve.getId().trim().equals("")) {
            logger.debug("advanceCurrentActivity approve insert -> {}",approve);
            appReviewInfoMapper.insert(appReviewInfo);
        }else {
            logger.debug("advanceCurrentActivity approve update -> {}",approve);
            appReviewInfoMapper.updateById(appReviewInfo);
        }
        //审批意见关联文件
        List<FilesVo> filesList = approve.getFileList();
        if(CollectionUtils.isNotEmpty(filesList)){
        	SubpageParam param = new SubpageParam();
        	param.setFiles(filesList);
        	param.setRefId(approve.getId());
			filesApi.refFiles(param);
        }
        //下级环节集合
        List<Workflowmodel> nextModels=new ArrayList<>();
        if (currentActivity!=null) {
            String currentModelId=currentActivity.getModelid();
            if (currentModelId==null||"".equals(currentModelId)) {
            	throw new CustomException(CommonCode.PROCESS_LINK_NULL);
            }

            if (!currentActivity.getActivitystatus().equals("待办")) {
            	throw new CustomException(CommonCode.REPEATED_PROCESSING);
            }

            logger.info("advanceCurrentActivity: currentModelId -> {}",currentModelId);
            if(approve.getRdbAddAccount() == 1 || approve.getResourceRecoveredAgree() == 1){ //如果是关系型数据库新增账号类型或者资源回收被回收人同意回收,下级环节为业务办理
                Instance instance = instanceDao.selectById(currentActivity.getInstanceid());
                nextModels = workFlowModelDao.selectList(new QueryWrapper<Workflowmodel>().lambda()
                        .eq(Workflowmodel::getWorkflowid,instance.getWorkflowid())
                        .eq(Workflowmodel::getVersion,instance.getWorkflowversion())
                        .and(i->i
                                .eq(Workflowmodel::getModelname,ModelName.CARRY.getName())
                                .or()
                                .eq(Workflowmodel::getModeltype,2)
                        )
                );
                logger.info("rdb nextModels -> {}",nextModels);
            }else {
                nextModels=workFlowModelDao.getSubordinateModel(currentModelId);
            }
            logger.info("advanceCurrentActivity: nextModels -> {}",nextModels);
            //如果没有下级环节，则直接完成当前环节，完成流程实例
            if (nextModels==null||nextModels.size()==0) {
                currentActivity.setActivitystatus("已提交");
                currentActivity.setHandletime(new Date());
                activityDao.updateById(currentActivity);
                Activity activity = new Activity();
                activity.setHandletime(new Date());
                activity.setActivitystatus("已抢占");
                int updateR=activityDao.update(activity,new QueryWrapper<Activity>().eq("modelid",currentActivity.getModelid())
                        .eq("instanceid",currentActivity.getInstanceid()).ne("id",currentActivity.getId()));

                Instance instance=instanceDao.selectById(currentActivity.getInstanceid());
                instance.setCompletetime(new Date());
                instance.setInstancestatus("已完成");
                instanceDao.updateById(instance);
                return QueryResponseResult.success("finished");
            }
        }else {
        	throw new CustomException(CommonCode.LINK_NULL);
        }
        String next = "";//next记录环节名
        Workflowmodel returnModel=null;
        for (Workflowmodel model:nextModels) {
            //取得对应环节的处理人
            String personids=model.getDefaulthandleperson();
            //如果默认处理人也为空则直接返回提示信息给前台
            if (personids==null||personids.trim().equals("")) {
            	throw new CustomException(CommonCode.FLOW_HANDLER_NULL);
            }
            //把办理人分割成数组
            String personArr[]=personids.split(",");
            if (personArr.length==0) {
            	throw new CustomException(CommonCode.HANDLER_NULL);
            }
            //获取需要的参与人对应关系
            String adviserIds=model.getAdviserperson();
            //把参与人分割成数组
            String adviserArr[]=null;
            if (adviserIds!=null) {
                adviserArr=adviserIds.split(",");
            }
            next = model.getModelname();
            returnModel=model;
            if (map==null){
                //model:下一环节 ，currentActivity:当前流转ID，personArr：处理人数组，adviserArr:参与人数组
                handleFlow(model, currentActivity, personArr,adviserArr,null);
            }else{
                handleFlow(model, currentActivity, personArr,adviserArr,null,map);
            }
        }
        currentActivity.setHandletime(new Date());
        currentActivity.setActivitystatus("已提交");
        activityDao.updateById(currentActivity);
        //返回下一环节的名字
        return QueryResponseResult.success(returnModel);
    }

    @Transactional
    @Override
    public ResponseResult advanceCurrentActivity(Activity currentActivity,String handeler) {
        //下级环节集合
        List<Workflowmodel> nextModels=new ArrayList<>();
        if (currentActivity!=null) {
            String currentModelId=currentActivity.getModelid();
            if (currentModelId==null||"".equals(currentModelId)) {
            	throw new CustomException(CommonCode.PROCESS_LINK_NULL);
            }

            if (!currentActivity.getActivitystatus().equals("待办")) {
            	throw new CustomException(CommonCode.REPEATED_PROCESSING);
            }

            nextModels=workFlowModelDao.getSubordinateModel(currentModelId);
            //如果没有下级环节，则直接完成当前环节，完成流程实例
            if (nextModels==null||nextModels.size()==0) {
                currentActivity.setActivitystatus("已提交");
                currentActivity.setHandletime(new Date());
                activityDao.updateById(currentActivity);

                //修改当前环节状态为已提交 ，如果当前环节选择了多个办理人，则activity有多个,把每个都更新为已提交
                Activity activity = new Activity();
                activity.setHandletime(new Date());
                activity.setActivitystatus("已抢占");
                int updateR=activityDao.update(activity,new QueryWrapper<Activity>().eq("modelid",currentActivity.getModelid())
                        .eq("instanceid",currentActivity.getInstanceid()).ne("id",currentActivity.getId()));
                Instance instance=instanceDao.selectById(currentActivity.getInstanceid());
                instance.setCompletetime(new Date());
                instance.setInstancestatus("已完成");
                instanceDao.updateById(instance);
                return QueryResponseResult.success("流转成功");
            }
        }else {
        	throw new CustomException(CommonCode.LINK_NULL);
        }

        for (Workflowmodel model:nextModels) {
            //取得对应环节的处理人
            String personids= handeler;
            if (personids==null||personids.trim().equals("")){
                personids=model.getDefaulthandleperson();
            }
            //如果默认处理人也为空则直接返回提示信息给前台
            if (personids==null||personids.trim().equals("")) {
                throw new CustomException(CommonCode.FLOW_HANDLER_NULL);
            }
            //把办理人分割成数组
            String personArr[]=personids.split(",");
            if (personArr.length==0) {
                throw new CustomException(CommonCode.HANDLER_NULL);
            }
            //获取需要的参与人对应关系
            String adviserIds=model.getAdviserperson();
            //把参与人分割成数组
            String adviserArr[]=null;
            if (adviserIds!=null) {
                adviserArr=adviserIds.split(",");
            }
            handleFlow(model, currentActivity, personArr,adviserArr,null);
        }
        currentActivity.setHandletime(new Date());
        currentActivity.setActivitystatus("已提交");
        activityDao.updateById(currentActivity);
        return QueryResponseResult.success();
    }

    @Override
    public ResponseResult adviseActivity(String currentActivityId, AppReviewInfoVo approve) {
        Activity currentActivity;
        if (currentActivityId!=null&&!currentActivityId.equals("")) {
            currentActivity=activityDao.selectById(currentActivityId);
        }else {
        	throw new CustomException(CommonCode.ACTIVITY_ID_NULL);
        }
        if (approve==null) {
        	return QueryResponseResult.fail("审批信息不能为空");
        }
        if (approve.getAppInfoId()==null||approve.getAppInfoId().trim().equals("")) {
            return QueryResponseResult.fail("申请信息ID不能为空");
        }
        Workflowmodel curmodel = workFlowModelDao.selectById(currentActivity.getModelid());
        approve.setStepName(curmodel.getModelname());
        approve.setFlowStepId(currentActivity.getModelid());
        approve.setRType("5");
        
        AppReviewInfo appReviewInfo = new AppReviewInfo();
        BeanUtils.copyProperties(approve, appReviewInfo);
        
        if (approve.getId()==null||approve.getId().trim().equals("")) {
        	appReviewInfoMapper.insert(appReviewInfo);
        }else {
        	appReviewInfoMapper.updateById(appReviewInfo);
        }
        currentActivity.setHandletime(new Date());
        currentActivity.setActivitystatus("已提交");
        activityDao.updateById(currentActivity);
        return ResponseResult.success();
    }


    public void handleFlow(Workflowmodel model,Activity currentActivity,String [] personArr,String[] adviserArr,AdvanceBeanVO advanceBeanVO){
        handleFlow(model,currentActivity,personArr,adviserArr,advanceBeanVO,null);
    }

    /**
     * 处理新增流转信息
     * @param model 下一环节
     * @param currentActivity 当前流转ID
     * @param personArr 处理人数组
     * @param adviserArr 参与人数组
     * @param advanceBeanVO
     * @param map
     */
    private void handleFlow(Workflowmodel model, Activity currentActivity, String[] personArr,String[] adviserArr, AdvanceBeanVO advanceBeanVO, Map<String, String> map) {

        //如果为抢占模式，则直接生成下级的待办任务
        for (int i = 0; i < personArr.length; i++) {
            Activity nextActivity=getTaskActivity(personArr[i],currentActivity,model,"待办");
            activityDao.insert(nextActivity);
            if (null!=map) {  //发送待办通知消息到mq
                //特殊处理，当name=资源回收时，发送特别短信消息
//                if(map.get("name")!=null&&map.get("name").equals(BusinessName.RECOVER)){
//                    //防止出错，没有月日则使用旧短信
//                    if(map.get("month")!=null&&map.get("day")!=null){
//                        messageProvider.sendMessageAsync(messageProvider.buildRecoverMessage(personArr[i], BusinessName.RECOVER, map.get("order"),map.get("month"),map.get("day")));
//                    }else{
//                        messageProvider.sendMessageAsync(messageProvider.buildProcessingMessage(map.get("name"), map.get("order"), personArr[i]));
//                    }
//                }else{
//                    messageProvider.sendMessageAsync(messageProvider.buildProcessingMessage(map.get("name"), map.get("order"), personArr[i]));
//                }
            	
            	//TODO: 消息队列改造
                //messageProvider.sendMessageAsync(messageProvider.buildProcessingMessage(map.get("name"), map.get("order"), personArr[i]));
            }
        }
        //生成参与人待办
        if (adviserArr!=null) {
            for (int j = 0; j < adviserArr.length; j++) {
                Activity adviserActivity=getTaskActivity(adviserArr[j],currentActivity,model,"待办");
                adviserActivity.setActivitytype("adviser");
                int createA=activityDao.insert(adviserActivity);
                //发送待办通知消息到mq
                if (null!=map) {
                	//TODO: 消息队列改造
                    //messageProvider.sendMessageAsync(messageProvider.buildProcessingMessage(map.get("name"), map.get("order"), personArr[j]));

                }
            }
        }

        //修改当前环节状态为已提交 ，如果当前环节选择了多个办理人，则activity有多个,把每个都更新为已提交
        Activity activity = new Activity();
        activity.setHandletime(new Date());
        activity.setActivitystatus("已抢占");
        //和当前流转信息相同环节 相同实例的数据，状态更新为已抢占
        int updateR=activityDao.update(activity,new QueryWrapper<Activity>().eq("modelid",currentActivity.getModelid())
                .eq("instanceid",currentActivity.getInstanceid()).ne("id",currentActivity.getId()));


    }

    //抽出公共的需要生成待办的activity,减少代码量

    /**
     * 生成待办的activity实体
     * @param userId 处理人ID
     * @param currentActivity 当前流转ID
     * @param model 下一个环节
     * @param status 状态
     * @return
     */
    public Activity getTaskActivity(String userId,Activity currentActivity,Workflowmodel model,String status){
        Activity activity=new Activity();
        String actId= UUIDUtil.getUUID();
        activity.setId(actId);
        activity.setCreatetime(new Date());
        activity.setHandlepersonids(userId);
        activity.setCreatepersonid(currentActivity.getCreatepersonid());
        activity.setInstanceid(currentActivity.getInstanceid());
        activity.setModelid(model.getId());
        activity.setActivitystatus(status);
        activity.setIsstart(1);
        activity.setPreviousactivityids(currentActivity.getId());
        return activity;
    }

    @Override
    public ResponseResult fallbackCurrentActivity(FallBackVO vo) {
        if (vo==null) {
        	return QueryResponseResult.fail("回退信息为空,回退失败");
        }
        String currentActivityId=vo.getCurrentActivityId();
        if (currentActivityId==null||currentActivityId.trim().equals("")) {
        	return QueryResponseResult.fail("当前环节ID不能为空,回退失败");
        }
        Activity currentActivity=activityDao.selectById(currentActivityId);
        if (currentActivity==null) {
        	return QueryResponseResult.fail("未查询到当前环节信息,回退失败");
        }
        String modelIds=vo.getFallBackModelIds();
        if (modelIds==null||modelIds.trim().equals("")) {
        	return QueryResponseResult.fail("回退环节ID不能为空,回退失败");
        }
        if (!currentActivity.getActivitystatus().equals("待办")) {
        	return QueryResponseResult.fail("该任务已经处理，不能重复处理");
        }
        //判断当前环节定义的处理模式是会签/抢占,如果为会签则当一个人处理了则不能回退,如果没有处理则直接回退
        String currentModelid=currentActivity.getModelid();
        if (currentModelid==null) {
        	return QueryResponseResult.fail("未找到当前环节定义,回退失败");
        }


        int count=0;

            List<Activity> activities=activityDao.selectFallBackActivity(modelIds, currentActivity.getInstanceid());
            for (Activity activity:activities) {
                count++;
                String id=UUIDUtil.getUUID();
                activity.setId(id);
                activity.setActivitystatus("待办");
                activity.setCreatetime(new Date());
                activity.setCreatepersonid(currentActivity.getHandlepersonids());
                activity.setPreviousactivityids(currentActivityId);;
                activityDao.insert(activity);
            }

        if (count==0) {
        	return QueryResponseResult.fail("回退失败,未找到上级处理人或当前环节不能回退!");
        }
        //更新当前环节为已回退
        //同时流转给多个人时当前环节有多个待办
        List<Activity> currentactivities=activityDao.selectList(new QueryWrapper<Activity>().eq("modelid",currentActivity.getModelid())
                .eq("instanceid",currentActivity.getInstanceid()));
        for (Activity activity:currentactivities) {
            activity.setHandletime(new Date());
            activity.setActivitystatus("已回退");
            activityDao.updateById(activity);
        }

        AppReviewInfoVo approve = vo.getApprove();
        if (approve!=null) {
            approve.setRType("4");
            Workflowmodel curmodel = workFlowModelDao.selectById(currentActivity.getModelid());
            approve.setStepName(curmodel.getModelname()+"回退");
            
            AppReviewInfo appReviewInfo = new AppReviewInfo();
            BeanUtils.copyProperties(approve, appReviewInfo);
            
            if (appReviewInfo.getId()!=null&&!appReviewInfo.getId().equals("")) {
                appReviewInfoMapper.updateById(appReviewInfo);
            }else {
                appReviewInfoMapper.insert(appReviewInfo);
            }
        }
        ApplicationFeedbackVo feedBack = vo.getFeedBack();
        if (feedBack!=null) {
        	
        	ApplicationFeedback applicationFeedback = new ApplicationFeedback();
            BeanUtils.copyProperties(feedBack, applicationFeedback);
        	
            if (applicationFeedback.getId()!=null&&!applicationFeedback.getId().equals("")) {
               applicationFeedbackMapper.updateById(applicationFeedback);
            }else {
                applicationFeedbackMapper.insert(applicationFeedback);
            }
        }
        return ResponseResult.success();
    }

    public ResponseResult updateActivityStatus(String modelId,Activity currentActivity){
        try {
            List<Workflowmodel> nextFolwModels=workFlowModelDao.getSubordinateModel(modelId);
            for(Workflowmodel model:nextFolwModels){
                String id=model.getId();
                List<Activity> activities2=activityDao.selectFallBackActivity(id, currentActivity.getInstanceid());
                if (activities2!=null) {
                    for (Activity nextActivity:activities2) {
                        nextActivity.setHandletime(new Date());
                        nextActivity.setActivitystatus("已回退");
                        activityDao.updateById(nextActivity);
                        if (nextActivity.getId().equals(currentActivity.getId())) {
                            break;
                        }
                       updateActivityStatus(nextActivity.getModelid(), currentActivity);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return QueryResponseResult.fail("流轉失敗");
        }
        return QueryResponseResult.success();
    }

    @Override
    public ResponseResult fallbackOnApproveNotPass(FallBackVO vo, Map<String, String> map) {
        if (vo==null) {
        	return QueryResponseResult.fail("流转信息不能为空");
        }

        String currentActivityId=vo.getCurrentActivityId();
        if (currentActivityId==null||currentActivityId.trim().equals("")) {
            return QueryResponseResult.fail("当前环节ID不能为空,回退失败");
        }
        //回退环节ID
        String modelIds=vo.getFallBackModelIds();
        if (modelIds==null||modelIds.trim().equals("")) {
            return QueryResponseResult.fail("当前环节ID不能为空,回退失败");
        }
        Activity currentActivity=activityDao.selectById(currentActivityId);
        if (currentActivity==null) {
            return QueryResponseResult.fail("未查询到当前环节信息,回退失败");
        }

        AppReviewInfoVo approve=vo.getApprove();
        if (approve!=null) {
            Workflowmodel curmodel = workFlowModelDao.selectById(currentActivity.getModelid());
            approve.setStepName(curmodel.getModelname());
            approve.setFlowStepId(currentActivity.getModelid());
            
            AppReviewInfo appReviewInfo = new AppReviewInfo();
            BeanUtils.copyProperties(approve, appReviewInfo);
            
            if (appReviewInfo.getId()!=null&&!appReviewInfo.getId().equals("")) {
                appReviewInfoMapper.updateById(appReviewInfo);
            }else {
                appReviewInfoMapper.insert(appReviewInfo);
            }
            List<FilesVo> filesList = approve.getFileList();
            if(CollectionUtils.isNotEmpty(filesList)){
            	
            	SubpageParam param = new SubpageParam();
            	param.setFiles(filesList);
            	param.setRefId(appReviewInfo.getId());
				filesApi.refFiles(param );
            	
            }

        }
        ApplicationFeedbackVo feedBack = vo.getFeedBack();
        if (feedBack!=null) {
        	
        	ApplicationFeedback applicationFeedback = new ApplicationFeedback();
            BeanUtils.copyProperties(feedBack, applicationFeedback);
        	
            if (feedBack.getId()!=null&&!feedBack.getId().equals("")) {
                applicationFeedbackMapper.updateById(applicationFeedback);
            }else {
                applicationFeedbackMapper.insert(applicationFeedback);
            }
        }
        // 生成待办
        Workflowmodel fallbackModel = workFlowModelDao.selectById(modelIds);
        //如果回退环节名为 申请 或 二级管理员审批 或 被回收资源负责人审批
        if (ModelName.APPLY.getName().equals(fallbackModel.getModelname())
                || ModelName.LVL2_MANAGER.getName().equals(fallbackModel.getModelname())
                || StringUtils.equals(ModelName.RECOVERED.getName(),fallbackModel.getModelname())) {
            //查找该实例 回退环节的流转信息
            List<Activity> activities=activityDao.selectFallBackActivity(modelIds, currentActivity.getInstanceid());
            if (activities.size()>0) {
                Activity activity=activities.get(0);
                String id=UUIDUtil.getUUID();
                activity.setId(id);
                activity.setActivitystatus("待办");
                activity.setCreatetime(new Date());
                activity.setCreatepersonid(currentActivity.getHandlepersonids());
                int result=activityDao.insert(activity);
            }
        } else {
            //取得对应环节的处理人
            String personids=fallbackModel.getDefaulthandleperson();
            //如果默认处理人也为空则直接返回提示信息给前台
            if (personids==null||personids.trim().equals("")) {
            	throw new CustomException(CommonCode.FLOW_HANDLER_NULL);
            }
            //把办理人分割成数组
            String personArr[]=personids.split(",");
            if (personArr.length==0) {
            	throw new CustomException(CommonCode.HANDLER_NULL);
            }
            //获取需要的参与人对应关系
            String adviserIds=fallbackModel.getAdviserperson();
            //把参与人分割成数组
            String adviserArr[]=null;
            if (adviserIds!=null) {
                adviserArr=adviserIds.split(",");
            }
            if (map==null){
                handleFlow(fallbackModel, currentActivity, personArr,adviserArr,null);
            }else{
                handleFlow(fallbackModel, currentActivity, personArr,adviserArr,null,map);
            }
        }
        //同时流转给多个人时当前环节有多个待办
        List<Activity> currentactivities=activityDao.selectList(new QueryWrapper<Activity>().eq("modelid",currentActivity.getModelid())
                .eq("instanceid",currentActivity.getInstanceid()));
        for (Activity activity:currentactivities) {
            activity.setHandletime(new Date());
            activity.setActivitystatus("已回退");
            activityDao.updateById(activity);
        }
        //获取复核环节的下级环节,把之后的环节置为已回退
        return updateActivityStatus(modelIds,currentActivity);
    }
    @Transactional
    @Override
    public ResponseResult terminationInstanceOfWorkFlow(String bizId) {
        Instance instance=instanceDao.selectOne(new QueryWrapper<Instance>().eq("BUSINESSID",bizId));
        if(instance==null){
        	throw new CustomException(CommonCode.FLOW_INSTANCE_NULL);
        }
        String instanceId=instance.getId();
        instance.setCompletetime(new Date());
        instance.setInstancestatus("终止");
        instanceDao.updateById(instance);
        activityDao.terminationActivity(instanceId);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult forward(String currentActivityId,String handlePersonIds) {
        Activity currentActivity = activityDao.selectById(currentActivityId);
        if (currentActivity == null) {
        	return QueryResponseResult.fail("不存在当前环节");
        }
        String[] handlePersonArr = handlePersonIds.split(",");
        for (String handlePerson : handlePersonArr) {
            Activity forwardActivity = new Activity();
            String id = UUIDUtil.getUUID();
            forwardActivity.setId(id);
            forwardActivity.setActivitystatus("待办");
            forwardActivity.setCreatepersonid(currentActivity.getHandlepersonids());
            forwardActivity.setCreatetime(new Date());
            //forwardActivity.setHandleDepartment(handleUser.getOrganization());
            forwardActivity.setHandlepersonids(handlePerson);
            forwardActivity.setInstanceid(currentActivity.getInstanceid());
            forwardActivity.setIsstart(1);
            forwardActivity.setModelid(currentActivity.getModelid());
            forwardActivity.setPreviousactivityids(currentActivity
                    .getPreviousactivityids());
            activityDao.insert(forwardActivity);
        }
        currentActivity.setActivitystatus("已转发");
        currentActivity.setHandletime(new Date());
        activityDao.updateById(currentActivity);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult add(AppReviewInfoVo approve,String handleIds,String currentActivityId,UserVO user) {
        Activity currentActivity = null;
        Map<String, String> modelMapToPerson=new HashMap<String, String>();

        if (currentActivityId!=null&&!currentActivityId.equals("")) {
            currentActivity=activityDao.selectById(currentActivityId);
        }else {
        	return QueryResponseResult.fail("当前环节ID不能为空");
        }
        if (approve==null) {
        	return QueryResponseResult.fail("审批信息不能为空");
        }
        if (approve.getAppInfoId()==null||approve.getAppInfoId().trim().equals("")) {
            return QueryResponseResult.fail("申请信息ID不能为空");
        }
      /*  if (approve.getrType()==null||approve.getrType().trim().equals("")) {
            return R.error(201, "审批信息类型不能为空");
        }*/
        approve.setCreator(user.getIdCard());
        Workflowmodel curmodel = workFlowModelDao.selectById(currentActivity.getModelid());
        approve.setStepName(curmodel.getModelname());
        approve.setFlowStepId(currentActivity.getModelid());
        approve.setRType("6");
        
        AppReviewInfo appReviewInfo = new AppReviewInfo();
        BeanUtils.copyProperties(approve, appReviewInfo);
        
        if (approve.getId()==null||approve.getId().trim().equals("")) {
           appReviewInfoMapper.insert(appReviewInfo);
        }else {
        	appReviewInfoMapper.updateById(appReviewInfo);
        }
        currentActivity.setActivitystatus("已呈批");
        activityDao.updateById(currentActivity);
        // 其它参与人改为抢占
        Activity activity = new Activity();
        activity.setHandletime(new Date());
        activity.setActivitystatus("已抢占");
        int updateR=activityDao.update(activity,new QueryWrapper<Activity>().eq("modelid",currentActivity.getModelid())
                .eq("instanceid",currentActivity.getInstanceid()).ne("id",currentActivity.getId()));
        // 插入指定加办人
        Activity advanceActivity=new Activity();
        advanceActivity.setId(UUIDUtil.getUUID());
        advanceActivity.setActivitystatus("待办");
        advanceActivity.setCreatetime(new Date());
        advanceActivity.setIsstart(1);
        advanceActivity.setCreatepersonid(user.getIdCard());
        advanceActivity.setInstanceid(currentActivity.getInstanceid());
        advanceActivity.setModelid(currentActivity.getModelid());
        advanceActivity.setPreviousactivityids(currentActivity.getPreviousactivityids());
        advanceActivity.setHandlepersonids(handleIds);
        activityDao.insert(advanceActivity);
        return QueryResponseResult.success();
    }

}
