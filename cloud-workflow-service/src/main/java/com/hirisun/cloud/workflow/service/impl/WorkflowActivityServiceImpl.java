package com.hirisun.cloud.workflow.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hirisun.cloud.common.contains.WorkflowNodeAbilityType;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.common.util.WorkflowUtil;
import com.hirisun.cloud.model.apply.ApplyReviewRecordVO;
import com.hirisun.cloud.model.apply.FallBackVO;
import com.hirisun.cloud.model.service.AppReviewInfoVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workflow.AdvanceBeanVO;
import com.hirisun.cloud.model.workflow.WorkflowActivityVO;
import com.hirisun.cloud.model.workflow.WorkflowNodeVO;
import com.hirisun.cloud.workflow.bean.WorkflowActivity;
import com.hirisun.cloud.workflow.bean.WorkflowInstance;
import com.hirisun.cloud.workflow.bean.WorkflowNode;
import com.hirisun.cloud.workflow.mapper.WorkflowActivityMapper;
import com.hirisun.cloud.workflow.mapper.WorkflowNodeMapper;
import com.hirisun.cloud.workflow.service.WorkflowActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.workflow.service.WorkflowInstanceService;
import com.hirisun.cloud.workflow.service.WorkflowNodeService;
import com.hirisun.cloud.workflow.vo.WorkflowCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * <p>
 * 流程流转表 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-05
 */
@Service
public class WorkflowActivityServiceImpl extends ServiceImpl<WorkflowActivityMapper, WorkflowActivity> implements WorkflowActivityService {

    private static Logger logger = LoggerFactory.getLogger(WorkflowActivityServiceImpl.class);

    @Autowired
    private WorkflowInstanceService workflowInstanceService;

    @Autowired
    private WorkflowNodeService workflowNodeService;

    @Autowired
    private WorkflowNodeMapper workflowNodeMapper;

    @Autowired
    private WorkflowActivityService workflowActivityService;

    /**
     * 初次流转  ,提交当前环节流转信息，生成下个待办环节流转信息
     * @param advanceBeanVO 流转VO
     * @param map 短信消息map
     * @return
     */
    @Override
    public void advanceCurrentActivity(AdvanceBeanVO advanceBeanVO, Map<String, String> map,WorkflowNode nextModel) {
        if (advanceBeanVO==null) {
            logger.info("流转信息不能为空");
            throw new CustomException(WorkflowCode.ADVANCE_CAN_NOT_NULL);
        }
        String currentActivityId=advanceBeanVO.getCurrentActivityId();
        if (StringUtils.isEmpty(currentActivityId)) {
            logger.info("当前环节ID不能为空");
            throw new CustomException(WorkflowCode.NODE_ID_CAN_NOT_NULL);
        }
        WorkflowActivity currentActivity=this.getById(currentActivityId);
        if (currentActivity == null) {
            logger.info("当前流程活动信息为空,流转失败!");
            throw new CustomException(WorkflowCode.WORKFLOW_ACTIVITY_MISSING);
        }
        //下一流转环节的处理人Map
        Map<String, String> modelMapToPerson=advanceBeanVO.getModelMapToPerson();
        //下级环节集合
//        List<WorkflowNode> nextModels= null;
        String currentModelId=currentActivity.getNodeId();
        if (currentModelId==null||"".equals(currentModelId)) {
            logger.info("当前环节对应的流程环节为空，请传入流程定义环节信息!");
            throw new CustomException(WorkflowCode.WORKFLOW_ACTIVITY_ERROR);
        }

        if (!currentActivity.getActivityStatus().equals(WorkflowActivity.STATUS_WAITING)) {
            logger.info("该任务已经处理，不能重复处理!");
            throw new CustomException(WorkflowCode.WORKFLOW_ACTIVITY_HANDLING);
        }
        //如果没有下级环节，则直接完成当前环节，完成流程实例
        if (nextModel==null) {
            currentActivity.setActivityStatus(WorkflowActivity.STATUS_SUBMIT);
            currentActivity.setHandleTime(new Date());
            this.updateById(currentActivity);

            WorkflowInstance instance=workflowInstanceService.getById(currentActivity.getInstanceId());
            instance.setCompleteTime(new Date());
            instance.setInstanceStatus(WorkflowInstance.INSTANCE_STATUS_COMPLETE);
            workflowInstanceService.updateById(instance);
            return;
        }

        //取得对应环节的处理人
        String personids=modelMapToPerson.get(nextModel.getId());
        //如果处理人为空则取出默认处理人
        if (personids==null||personids.trim().equals("")) {
            personids=nextModel.getDefaultHandler();
            //如果默认处理人也为空则直接返回提示信息给前台
            if (personids==null||personids.trim().equals("")) {
                logger.info("流程未配置处理人!");
                throw new CustomException(WorkflowCode.WORKFLOW_NODE_HANDLER_MISSING);
            }
        }
        //把办理人分割成数组
        String personArr[]=personids.split(",");
        if (personArr.length==0) {
            throw new CustomException(WorkflowCode.WORKFLOW_NODE_HANDLER_MISSING);
        }
        //获取需要的参与人对应关系
        Map<String, String> adviserMap=advanceBeanVO.getModelMapToAdviser();
        String adviserIds=null;
        if (adviserMap!=null) {
            adviserIds=adviserMap.get(nextModel.getId());
        }
        //把参与人分割成数组
        String adviserArr[]=null;
        if (adviserIds!=null) {
            adviserArr=adviserIds.split(",");
        }
        if (map!=null) {
            handleFlow(nextModel, currentActivity, personArr,adviserArr,advanceBeanVO,map);
        } else {
            handleFlow(nextModel, currentActivity, personArr, adviserArr, advanceBeanVO,null);
        }

        //当前流转信息更新为已提交,同级(其它处理人)在handleFlow已处理为已抢占
        currentActivity.setHandleTime(new Date());
        currentActivity.setActivityStatus(WorkflowActivity.STATUS_SUBMIT);
        this.updateById(currentActivity);
//        return QueryResponseResult.success(null);
    }

    /**
     * 处理新增流转信息
     * @param nextModel 下一环节
     * @param currentActivity 当前流转ID
     * @param personArr 处理人数组
     * @param adviserArr 参与人数组
     * @param advanceBeanVO
     * @param map
     */
    private void handleFlow(WorkflowNode nextModel, WorkflowActivity currentActivity, String[] personArr,String[] adviserArr, AdvanceBeanVO advanceBeanVO, Map<String, String> map) {

        //如果为抢占模式，则直接生成下级的待办任务
        for (int i = 0; i < personArr.length; i++) {
            WorkflowActivity nextActivity=getTaskActivity(personArr[i],currentActivity,nextModel,WorkflowActivity.STATUS_WAITING);
            this.save(nextActivity);
            if (null!=map) {
//                messageProvider.sendMessageAsync(messageProvider.buildProcessingMessage(map.get("name"), map.get("order"), personArr[i]));
            }
        }
        //生成参与人待办
        if (adviserArr!=null) {
            for (int j = 0; j < adviserArr.length; j++) {
                WorkflowActivity adviserActivity=getTaskActivity(adviserArr[j],currentActivity,nextModel,WorkflowActivity.STATUS_WAITING);
                adviserActivity.setActivityType("adviser");
                this.save(adviserActivity);
                //发送待办通知消息到mq
                if (null!=map) {
//                    messageProvider.sendMessageAsync(messageProvider.buildProcessingMessage(map.get("name"), map.get("order"), personArr[j]));
                }
            }
        }

        //修改当前环节状态为已提交 ，如果当前环节选择了多个办理人，则activity有多个,把每个都更新为已提交
        WorkflowActivity activity = new WorkflowActivity();
        activity.setHandleTime(new Date());
        activity.setActivityStatus(WorkflowActivity.STATUS_PREEMPT);
        this.update(activity, new QueryWrapper<WorkflowActivity>().lambda()
                .eq(WorkflowActivity::getNodeId, currentActivity.getNodeId())
                .eq(WorkflowActivity::getInstanceId, currentActivity.getInstanceId())
                .ne(WorkflowActivity::getId, currentActivity.getId()));

    }

    /**
     * 生成待办的activity实体
     * @param userId 处理人ID
     * @param currentActivity 当前流转ID
     * @param model 下一个环节
     * @param status 状态
     * @return
     */
    public WorkflowActivity getTaskActivity(String userId,WorkflowActivity currentActivity,WorkflowNode model,Integer status){
        WorkflowActivity activity=new WorkflowActivity();
        String actId= UUIDUtil.getUUID();
        activity.setId(actId);
        activity.setCreateTime(new Date());
        activity.setHandlePersons(userId);
        activity.setCreator(currentActivity.getCreator());
        activity.setInstanceId(currentActivity.getInstanceId());
        activity.setNodeId(model.getId());
        activity.setActivityStatus(status);
        activity.setPreActivityId(currentActivity.getId());
        return activity;
    }

    /**
     * 传入实例ID集合获取    实例ID-待办人身份证号集合映射关系
     * @param instanceIdList 实例ID集合
     * @return map
     */
    @Override
    public Map<String,String> instanceToHandleIdCards(List<String> instanceIdList){
        //获取实例对应流转信息，
        List<WorkflowActivity> activities = this.list(new QueryWrapper<WorkflowActivity>()
                .lambda()
                .in(WorkflowActivity::getInstanceId,instanceIdList));

        //获取待办流转信息
        List<WorkflowActivity> toDoActivities = activities.parallelStream().filter(activity ->
                WorkflowActivity.STATUS_WAITING.equals(activity.getActivityStatus())
                        && activity.getActivityType()==null).collect(Collectors.toList());

        // instanceId-IdCards
        Map<String,String> instanceTodoIdCardsMap = toDoActivities.parallelStream().collect(Collectors.groupingBy(WorkflowActivity::getInstanceId,
                Collectors.mapping(WorkflowActivity::getHandlePersons,Collectors.joining(","))));

        return instanceTodoIdCardsMap;
    }

    /**
     * 正常流转 提交当前环节流转信息，生成下个环节流转信息
     * @param currentActivityId
     * @param map
     * @return
     */
    @Override
    public Map<String,String> advanceActivity(String currentActivityId, Map<String, String> map) {

        Map<String, String> resultMap = new ConcurrentHashMap<>();
//        logger.info("advanceCurrentActivity: currentActivityId -> {} ,approve ->{}",
//                currentActivityId,approve);

        WorkflowActivity currentActivity = null;
        Map<String, String> modelMapToPerson=new HashMap<String, String>();
        if (currentActivityId!=null&&!currentActivityId.equals("")) {
            currentActivity=this.getById(currentActivityId);
        }else {
            throw new CustomException(WorkflowCode.WORKFLOW_NODE_NOT_NULL);
        }

//        WorkflowNode curmodel = workflowNodeService.getById(currentActivity.getNodeId());
//        approve.setStepName(curmodel.getModelname());
//        approve.setFlowStepId(currentActivity.getModelid());
//        logger.debug("advanceCurrentActivity approve -> {}",approve);
//        if (approve.getId()==null||approve.getId().trim().equals("")) {
//            logger.debug("advanceCurrentActivity approve insert -> {}",approve);
//            approve.insert();
//        }else {
//            logger.debug("advanceCurrentActivity approve update -> {}",approve);
//            approve.updateById();
//        }
        //审批意见关联文件
//        List<Files> filesList = approve.getFileList();
//        if(CollectionUtils.isNotEmpty(filesList)){
//            filesService.refFiles(filesList,approve.getId());
//        }
        //下级环节集合
        WorkflowNode nextNode = null;
        if (currentActivity!=null) {
            String currentModelId=currentActivity.getNodeId();
            if (currentModelId==null||"".equals(currentModelId)) {
                throw new CustomException(WorkflowCode.WORKFLOW_ACTIVITY_NODE_ID_NOT_NULL);
            }

            if (!WorkflowActivity.STATUS_WAITING.equals(currentActivity.getActivityStatus())) {
                throw new CustomException(WorkflowCode.WORKFLOW_ACTIVITY_STATUS_ERROR);
            }

            logger.info("advanceCurrentActivity: currentModelId -> {}",currentModelId);

//            if(approve.getRdbAddAccount() == 1 || approve.getResourceRecoveredAgree() == 1){ //如果是关系型数据库新增账号类型或者资源回收被回收人同意回收,下级环节为业务办理
            if(map.get("gotoImpl")!=null){
                WorkflowInstance instance = workflowInstanceService.getById(currentActivity.getInstanceId());
                nextNode = workflowNodeService.getOne(new QueryWrapper<WorkflowNode>().lambda()
                        .eq(WorkflowNode::getWorkflowId,instance.getWorkflowId())
                        .eq(WorkflowNode::getVersion,instance.getVersion())
                        .like(WorkflowNode::getNodeFeature,WorkflowNode.NODE_ABILITY_IMPL)
                );
                logger.info("rdb nextNode -> {}",nextNode);
            }else {
                nextNode=workflowNodeMapper.getNextNodeById(currentModelId);
            }
            logger.info("advanceCurrentActivity: nextModels -> {}",nextNode);
            //如果没有下级环节，则直接完成当前环节，完成流程实例
            if (nextNode==null) {
                currentActivity.setActivityStatus(WorkflowActivity.STATUS_SUBMIT);
                currentActivity.setHandleTime(new Date());
                this.updateById(currentActivity);
                WorkflowActivity activity = new WorkflowActivity();
                activity.setHandleTime(new Date());
                activity.setActivityStatus(WorkflowActivity.STATUS_PREEMPT);
                boolean updateR=this.update(activity,new QueryWrapper<WorkflowActivity>().eq("modelid",currentActivity.getNodeId())
                        .eq("instanceid",currentActivity.getInstanceId()).ne("id",currentActivity.getId()));

                WorkflowInstance instance=workflowInstanceService.getById(currentActivity.getInstanceId());
                instance.setCompleteTime(new Date());
                instance.setInstanceStatus(WorkflowInstance.INSTANCE_STATUS_COMPLETE);
                workflowInstanceService.updateById(instance);
                resultMap.put("code", "201");//成功，无下个环节
                return resultMap;
            }
        }else {
            throw new CustomException(WorkflowCode.WORKFLOW_ACTIVITY_MISSING);
        }
        String next = "";//next记录环节名
        WorkflowNode returnModel=null;
//        for (WorkflowNode model:nextModels) {
            //取得对应环节的处理人
            String personids=nextNode.getDefaultHandler();
            //如果默认处理人也为空则直接返回提示信息给前台
            if (personids==null||personids.trim().equals("")) {
                logger.error("流程未配置处理人");
                throw new CustomException(WorkflowCode.WORKFLOW_NODE_NO_HANDLER);

            }
            //把办理人分割成数组
            String personArr[]=personids.split(",");
            if (personArr.length==0) {
                throw new CustomException(WorkflowCode.WORKFLOW_NODE_NO_HANDLER);
            }
            //获取需要的参与人对应关系
            String adviserIds=nextNode.getAdviserPerson();
            //把参与人分割成数组
            String adviserArr[]=null;
            if (adviserIds!=null) {
                adviserArr=adviserIds.split(",");
            }
            next = nextNode.getNodeName();
            returnModel=nextNode;
            if (map==null){
                //model:下一环节 ，currentActivity:当前流转ID，personArr：处理人数组，adviserArr:参与人数组
                handleFlow(nextNode, currentActivity, personArr,adviserArr,null,null);
            }else{
                handleFlow(nextNode, currentActivity, personArr,adviserArr,null,map);
            }
//        }
        currentActivity.setHandleTime(new Date());
        currentActivity.setActivityStatus(WorkflowActivity.STATUS_SUBMIT);
        workflowActivityService.updateById(currentActivity);
        //返回下一环节的名字
        resultMap.put("code", "200");
        resultMap.put("data", JSON.toJSONString(returnModel));
        return resultMap;
    }

//    @Override
    public Map<String,String> add(AppReviewInfoVo approve, String handleIds, String currentActivityId, UserVO user) {
//        WorkflowActivity currentActivity = null;
//        Map<String, String> modelMapToPerson=new HashMap<String, String>();
//
//        if (currentActivityId!=null&&!currentActivityId.equals("")) {
//            currentActivity=this.getById(currentActivityId);
//        }else {
//            return R.error(201, "当前环节ID不能为空");
//        }
//        if (approve==null) {
//            return R.error(201, "审批信息不能为空");
//        }
//        if (approve.getAppInfoId()==null||approve.getAppInfoId().trim().equals("")) {
//            return R.error(201, "申请信息ID不能为空");
//        }
//      /*  if (approve.getrType()==null||approve.getrType().trim().equals("")) {
//            return R.error(201, "审批信息类型不能为空");
//        }*/
//        approve.setCreator(user.getIdcard());
//        Workflowmodel curmodel = workFlowModelDao.selectById(currentActivity.getModelid());
//        approve.setStepName(curmodel.getModelname());
//        approve.setFlowStepId(currentActivity.getModelid());
//        approve.setrType("6");
//        if (approve.getId()==null||approve.getId().trim().equals("")) {
//            approve.insert();
//        }else {
//            approve.updateById();
//        }
//        currentActivity.setActivitystatus("已呈批");
//        activityDao.updateById(currentActivity);
//        // 其它参与人改为抢占
//        Activity activity = new Activity();
//        activity.setHandletime(new Date());
//        activity.setActivitystatus("已抢占");
//        int updateR=activityDao.update(activity,new QueryWrapper<Activity>().eq("modelid",currentActivity.getModelid())
//                .eq("instanceid",currentActivity.getInstanceid()).ne("id",currentActivity.getId()));
//        // 插入指定加办人
//        Activity advanceActivity=new Activity();
//        advanceActivity.setId(UUIDUtil.getUUID());
//        advanceActivity.setActivitystatus("待办");
//        advanceActivity.setCreatetime(new Date());
//        advanceActivity.setIsstart(1);
//        advanceActivity.setCreatepersonid(user.getIdcard());
//        advanceActivity.setInstanceid(currentActivity.getInstanceid());
//        advanceActivity.setModelid(currentActivity.getModelid());
//        advanceActivity.setPreviousactivityids(currentActivity.getPreviousactivityids());
//        advanceActivity.setHandlepersonids(handleIds);
//        activityDao.insert(advanceActivity);
//
//        return R.ok();
        return null;
    }

    @Override
    public Map<String,String> fallbackOnApproveNotPass(FallBackVO vo, Map<String, String> map) {
        Map<String,String> resultMap = new HashMap();
        resultMap.put("code", "201");
        if (vo==null) {
            resultMap.put("msg", "流转信息不能为空");
            return resultMap;
        }

        String currentActivityId=vo.getCurrentActivityId();
        if (currentActivityId==null||currentActivityId.trim().equals("")) {
            resultMap.put("msg", "当前环节ID不能为空,回退失败");
            return resultMap;
        }
        //回退环节ID
        String modelIds=vo.getFallBackModelIds();
        if (modelIds==null||modelIds.trim().equals("")) {
            resultMap.put("msg", "当前环节ID不能为空,回退失败");
            return resultMap;
        }
        WorkflowActivity currentActivity=this.getById(currentActivityId);
        if (currentActivity==null) {
            resultMap.put("msg", "未查询到当前环节信息,回退失败");
            return resultMap;
        }
        // 生成待办
        WorkflowNode fallbackModel = workflowNodeService.getById(modelIds);
        //如果回退环节名为 申请 或 二级管理员审批 或 被回收资源负责人审批
//        if (ModelName.APPLY.getName().equals(fallbackModel.getModelname())
//                || ModelName.LVL2_MANAGER.getName().equals(fallbackModel.getModelname())
//                || org.apache.commons.lang.StringUtils.equals(ModelName.RECOVERED.getName(),fallbackModel.getModelname())) {
        if (WorkflowUtil.compareNodeAbility(fallbackModel.getNodeFeature(), WorkflowNodeAbilityType.APPLY.getCode())) {
            //查找该实例 回退环节的流转信息
            List<WorkflowActivity> activities=selectFallBackActivity(currentActivity,modelIds);
            if (activities.size()>0) {
                WorkflowActivity activity=activities.get(0);
                String id=UUIDUtil.getUUID();
                activity.setId(id);
                activity.setActivityStatus(WorkflowActivity.STATUS_WAITING);
                activity.setCreateTime(new Date());
                activity.setCreator(currentActivity.getHandlePersons());
                workflowActivityService.save(activity);
            }
        } else {
            //取得对应环节的处理人
            String personids=fallbackModel.getDefaultHandler();
            //如果默认处理人也为空则直接返回提示信息给前台
            if (personids==null||personids.trim().equals("")) {
                resultMap.put("msg", "流程未配置处理人");
                return resultMap;
            }
            //把办理人分割成数组
            String personArr[]=personids.split(",");
            if (personArr.length==0) {
                resultMap.put("msg", "办理人不能为空");
                return resultMap;
            }
            //获取需要的参与人对应关系
            String adviserIds=fallbackModel.getAdviserPerson();
            //把参与人分割成数组
            String adviserArr[]=null;
            if (adviserIds!=null) {
                adviserArr=adviserIds.split(",");
            }
            if (map==null){
                handleFlow(fallbackModel, currentActivity, personArr,adviserArr,null,null);
            }else{
                handleFlow(fallbackModel, currentActivity, personArr,adviserArr,null,map);
            }
        }
        //同时流转给多个人时当前环节有多个待办
        List<WorkflowActivity> currentactivities=workflowActivityService.list(new QueryWrapper<WorkflowActivity>().lambda().eq(WorkflowActivity::getNodeId,currentActivity.getNodeId())
                .eq(WorkflowActivity::getInstanceId,currentActivity.getInstanceId()));
        for (WorkflowActivity activity:currentactivities) {
            activity.setHandleTime(new Date());
            activity.setActivityStatus(WorkflowActivity.STATUS_REJECT);
            workflowActivityService.updateById(activity);
        }
        //获取复核环节的下级环节,把之后的环节置为已回退
        return updateActivityStatus(modelIds,currentActivity);
    }

    public Map<String,String> updateActivityStatus(String modelId,WorkflowActivity currentActivity){
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("code", "201");
        try {
            WorkflowNode model=workflowNodeService.getNextNodeById(modelId);
            String id = model.getId();
            List<WorkflowActivity> activities2 = selectFallBackActivity(currentActivity, id);
            if (activities2 != null) {
                for (WorkflowActivity nextActivity : activities2) {
                    nextActivity.setHandleTime(new Date());
                    nextActivity.setActivityStatus(WorkflowActivity.STATUS_REJECT);
                    workflowActivityService.updateById(nextActivity);
                    if (nextActivity.getId().equals(currentActivity.getId())) {
                        break;
                    }
                    updateActivityStatus(nextActivity.getNodeId(), currentActivity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("msg", "流转失败");
            return resultMap;
        }
        resultMap.put("code", "200");
        resultMap.put("msg","成功");
        return resultMap;
    }

    public List<WorkflowActivity> selectFallBackActivity(WorkflowActivity workflowActivity,String nodeId){
        List<WorkflowActivity> activities = workflowActivityService.list(new QueryWrapper<WorkflowActivity>().lambda()
                .eq(WorkflowActivity::getInstanceId, workflowActivity.getInstanceId())
                .eq(WorkflowActivity::getNodeId, nodeId)
                .and(i->i
                        .eq(WorkflowActivity::getActivityStatus,WorkflowActivity.STATUS_SUBMIT)
                        .or()
                        .eq(WorkflowActivity::getActivityStatus,WorkflowActivity.STATUS_PREEMPT)
                ));
        return activities;
    }

    @Override
    public Map<String,String> adviseActivity(String currentActivityId) {
        Map resultMap = new HashMap();
        resultMap.put("code", "201");
        WorkflowActivity currentActivity;
        if (currentActivityId!=null&&!currentActivityId.equals("")) {
            currentActivity=this.getById(currentActivityId);
        }else {
            resultMap.put("msg", "当前环节ID不能为空");
            return resultMap;
        }
        WorkflowNode curmodel = workflowNodeService.getById(currentActivity.getNodeId());
        resultMap.put("nodeName", curmodel.getNodeName());
        resultMap.put("nodeId", currentActivity.getNodeId());
        resultMap.put("type", "5");
        currentActivity.setHandleTime(new Date());
        currentActivity.setActivityStatus(WorkflowActivity.STATUS_SUBMIT);
        this.updateById(currentActivity);
        resultMap.put("code", "200");
        resultMap.put("msg", "成功");
        return resultMap;
    }
}
