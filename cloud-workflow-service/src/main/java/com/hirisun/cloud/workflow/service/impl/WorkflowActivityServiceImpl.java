package com.hirisun.cloud.workflow.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hirisun.cloud.common.contains.ApplicationInfoStatus;
import org.apache.catalina.User;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.api.system.SmsApi;
import com.hirisun.cloud.common.contains.WorkflowActivityStatus;
import com.hirisun.cloud.common.contains.WorkflowInstanceStatus;
import com.hirisun.cloud.common.contains.WorkflowNodeAbilityType;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.common.util.WorkflowUtil;
import com.hirisun.cloud.model.apply.FallBackVO;
import com.hirisun.cloud.model.param.ActivityParam;
import com.hirisun.cloud.model.service.AppReviewInfoVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workflow.AdvanceBeanVO;
import com.hirisun.cloud.model.workflow.WorkflowActivityVO;
import com.hirisun.cloud.workflow.bean.WorkflowActivity;
import com.hirisun.cloud.workflow.bean.WorkflowInstance;
import com.hirisun.cloud.workflow.bean.WorkflowNode;
import com.hirisun.cloud.workflow.mapper.WorkflowActivityMapper;
import com.hirisun.cloud.workflow.mapper.WorkflowNodeMapper;
import com.hirisun.cloud.workflow.service.WorkflowActivityService;
import com.hirisun.cloud.workflow.service.WorkflowInstanceService;
import com.hirisun.cloud.workflow.service.WorkflowNodeService;
import com.hirisun.cloud.workflow.vo.WorkflowCode;

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
    private WorkflowActivityMapper workflowActivityMapper;

    @Autowired
    private WorkflowActivityService workflowActivityService;

    @Autowired
    private SmsApi smsApi;

    /**
     * 初次流转  ,提交当前环节流转信息，生成下个待办环节流转信息
     * @param advanceBeanVO 流转VO
     * @param map 短信消息map
     * @return
     */
//    @Override
//    public Map<String,String> advanceCurrentActivity(AdvanceBeanVO advanceBeanVO, Map<String, String> map,WorkflowNode nextModel) {
//        Map<String, String> resultMap = new HashMap<>();
//        resultMap.put("code", "201");
//        if (advanceBeanVO==null) {
//            logger.info("流转信息不能为空");
//            resultMap.put("msg", "流转信息不能为空");
//            return resultMap;
//        }
//        String currentActivityId=advanceBeanVO.getCurrentActivityId();
//        if (StringUtils.isEmpty(currentActivityId)) {
//            logger.info("当前环节ID不能为空");
//            resultMap.put("msg", "当前环节ID不能为空");
//            return resultMap;
//        }
//        WorkflowActivity currentActivity=this.getById(currentActivityId);
//        if (currentActivity == null) {
//            logger.info("当前流程活动信息为空,流转失败!");
//            resultMap.put("msg", "当前流程活动信息为空,流转失败");
//            return resultMap;
//        }
//        //下一流转环节的处理人Map
//        Map<String, String> modelMapToPerson=advanceBeanVO.getModelMapToPerson();
//        //下级环节集合
////        List<WorkflowNode> nextModels= null;
//        String currentModelId=currentActivity.getNodeId();
//        if (currentModelId==null||"".equals(currentModelId)) {
//            logger.info("当前环节对应的流程环节为空，请传入流程定义环节信息!");
//            resultMap.put("msg", "当前环节对应的流程环节为空，请传入流程定义环节信息");
//            return resultMap;
//        }
//
//        if (!currentActivity.getActivityStatus().equals(WorkflowActivity.STATUS_WAITING)) {
//            logger.info("该任务已经处理，不能重复处理!");
//            resultMap.put("msg", "该任务已经处理，不能重复处理");
//            return resultMap;
//        }
//        //如果没有下级环节，则直接完成当前环节，完成流程实例
//        if (nextModel==null) {
//            currentActivity.setActivityStatus(WorkflowActivity.STATUS_SUBMIT);
//            currentActivity.setHandleTime(new Date());
//            this.updateById(currentActivity);
//
//            WorkflowInstance instance=workflowInstanceService.getById(currentActivity.getInstanceId());
//            instance.setCompleteTime(new Date());
//            instance.setInstanceStatus(WorkflowInstance.INSTANCE_STATUS_COMPLETE);
//            workflowInstanceService.updateById(instance);
//            resultMap.put("code", "200");
//            resultMap.put("msg", "成功");
//            return resultMap;
//        }
//
//        //取得对应环节的处理人
//        String personids=modelMapToPerson.get(nextModel.getId());
//        //如果处理人为空则取出默认处理人
//        if (personids==null||personids.trim().equals("")) {
//            personids=nextModel.getDefaultHandler();
//            //如果默认处理人也为空则直接返回提示信息给前台
//            if (personids==null||personids.trim().equals("")) {
//                logger.info("流程未配置处理人!");
//                resultMap.put("msg", "流程未配置处理人");
//                return resultMap;
//            }
//        }
//        //把办理人分割成数组
//        String personArr[]=personids.split(",");
//        if (personArr.length==0) {
//            throw new CustomException(WorkflowCode.WORKFLOW_NODE_HANDLER_MISSING);
//        }
//        //获取需要的参与人对应关系
//        Map<String, String> adviserMap=advanceBeanVO.getModelMapToAdviser();
//        String adviserIds=null;
//        if (adviserMap!=null) {
//            adviserIds=adviserMap.get(nextModel.getId());
//        }
//        //把参与人分割成数组
//        String adviserArr[]=null;
//        if (adviserIds!=null) {
//            adviserArr=adviserIds.split(",");
//        }
//    handleFlow(nextModel, currentActivity, personArr,adviserArr,map);
//
//        //当前流转信息更新为已提交,同级(其它处理人)在handleFlow已处理为已抢占
//        currentActivity.setHandleTime(new Date());
//        currentActivity.setActivityStatus(WorkflowActivity.STATUS_SUBMIT);
//        this.updateById(currentActivity);
//        resultMap.put("code", "200");
//        resultMap.put("msg", "成功");
//        return resultMap;
//    }

    /**
     * 保存流转信息
     * @param nextModel 下一环节
     * @param currentActivity 当前流转ID
     * @param personArr 处理人数组
     * @param adviserArr 参与人数组
     * @param messageMap    短信信息map
     */
    private void handleFlow(WorkflowNode nextModel, WorkflowActivity currentActivity, String[] personArr,String[] adviserArr, Map<String, String> messageMap) {
        List<WorkflowActivity> activityList = new ArrayList<>();
        //如果为抢占模式，则直接生成下级的待办任务
        for (int i = 0; i < personArr.length; i++) {
            WorkflowActivity defaultActivity=getTaskActivity(personArr[i],currentActivity,nextModel,WorkflowActivityStatus.WAITING.getCode());
            activityList.add(defaultActivity);
        }
        //生成参与人待办
        if (adviserArr!=null) {
            for (int j = 0; j < adviserArr.length; j++) {
                WorkflowActivity adviserActivity=getTaskActivity(adviserArr[j],currentActivity,nextModel,WorkflowActivityStatus.WAITING.getCode());
                adviserActivity.setActivityType("adviser");
                activityList.add(adviserActivity);
            }
        }
        if (CollectionUtils.isNotEmpty(activityList)) {
            this.saveBatch(activityList);
            if (messageMap != null&&messageMap.get("name")!=null) {
                activityList.forEach(item->{
                    smsApi.buildProcessingMessage(messageMap.get("name"), messageMap.get("order"), item.getHandlePersons());
                });
            }
        }
        //修改当前环节状态为已提交 ，如果当前环节选择了多个办理人，则activity有多个,把每个都更新为已提交
        WorkflowActivity activity = new WorkflowActivity();
        activity.setHandleTime(new Date());
        activity.setActivityStatus(WorkflowActivityStatus.PREEMPT.getCode());
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
                WorkflowActivityStatus.WAITING.getCode().equals(activity.getActivityStatus())
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
        WorkflowActivity currentActivity = null;
        if (currentActivityId!=null&&!currentActivityId.equals("")) {
            currentActivity=this.getById(currentActivityId);
        }else {
            throw new CustomException(WorkflowCode.WORKFLOW_NODE_NOT_NULL);
        }
        //下级环节集合
        WorkflowNode nextNode = null;
        if (currentActivity!=null) {
            String currentModelId=currentActivity.getNodeId();
            if (currentModelId==null||"".equals(currentModelId)) {
                throw new CustomException(WorkflowCode.WORKFLOW_ACTIVITY_NODE_ID_NOT_NULL);
            }

            if (!WorkflowActivityStatus.WAITING.getCode().equals(currentActivity.getActivityStatus())) {
                throw new CustomException(WorkflowCode.WORKFLOW_ACTIVITY_STATUS_ERROR);
            }

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
            //如果没有下级环节，则直接完成当前环节，完成流程实例
            if (nextNode==null) {
                currentActivity.setActivityStatus(WorkflowActivityStatus.SUBMIT.getCode());
                currentActivity.setHandleTime(new Date());
                this.updateById(currentActivity);
                WorkflowActivity activity = new WorkflowActivity();
                activity.setHandleTime(new Date());
                activity.setActivityStatus(WorkflowActivityStatus.PREEMPT.getCode());
                boolean updateR=this.update(activity,new QueryWrapper<WorkflowActivity>().eq("modelid",currentActivity.getNodeId())
                        .eq("instanceid",currentActivity.getInstanceId()).ne("id",currentActivity.getId()));

                WorkflowInstance instance=workflowInstanceService.getById(currentActivity.getInstanceId());
                instance.setCompleteTime(new Date());
                instance.setInstanceStatus(WorkflowInstanceStatus.COMPLETE.getCode());
                workflowInstanceService.updateById(instance);
                resultMap.put("code", "201");//成功，无下个环节
                return resultMap;
            }
        }else {
            throw new CustomException(WorkflowCode.WORKFLOW_ACTIVITY_MISSING);
        }
        //取得对应环节的处理人
        String personids =null;
        if (map.get("depApproveUserIds") != null) {
            personids = map.get("depApproveUserIds");
        }else{
            personids=nextNode.getDefaultHandler();
        }

        //如果默认处理人也为空则直接返回提示信息给前台
        boolean isApply= WorkflowUtil.compareNodeAbility(nextNode.getNodeFeature(), WorkflowNodeAbilityType.APPLY.getCode());
        if (!isApply&&(personids == null || personids.trim().equals(""))) {
            logger.error("流程未配置处理人");
            throw new CustomException(WorkflowCode.WORKFLOW_NODE_NO_HANDLER);

        }
        //把办理人分割成数组
        String personArr[] = personids.split(",");
        if (personArr.length == 0) {
            throw new CustomException(WorkflowCode.WORKFLOW_NODE_NO_HANDLER);
        }
        //获取需要的参与人对应关系
        String adviserIds = nextNode.getAdviserPerson();
        //把参与人分割成数组
        String adviserArr[] = null;
        if (adviserIds != null) {
            adviserArr = adviserIds.split(",");
        }
        handleFlow(nextNode, currentActivity, personArr, adviserArr, map);
        currentActivity.setHandleTime(new Date());
        currentActivity.setActivityStatus(WorkflowActivityStatus.SUBMIT.getCode());
        workflowActivityService.updateById(currentActivity);
        //返回下一环节的名字
        resultMap.put("code", "200");
        resultMap.put("data", JSON.toJSONString(nextNode));
        return resultMap;
    }

//    @Override
    public Map<String,String> add(AppReviewInfoVo approve, String handleIds, String currentActivityId, UserVO user) {

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
                activity.setActivityStatus(WorkflowActivityStatus.WAITING.getCode());
                activity.setCreateTime(new Date());
                activity.setCreator(currentActivity.getHandlePersons());
                workflowActivityService.save(activity);
            }
        } else {
            //取得对应环节的处理人
            String personids=null;
            if (map != null && map.get("depApproveUserIds") != null) {
                personids = map.get("depApproveUserIds");
            } else {
                personids = fallbackModel.getDefaultHandler();
            }
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
            handleFlow(fallbackModel, currentActivity, personArr,adviserArr,map);
        }
        //同时流转给多个人时当前环节有多个待办
        List<WorkflowActivity> currentactivities=workflowActivityService.list(new QueryWrapper<WorkflowActivity>().lambda().eq(WorkflowActivity::getNodeId,currentActivity.getNodeId())
                .eq(WorkflowActivity::getInstanceId,currentActivity.getInstanceId()));
        for (WorkflowActivity activity:currentactivities) {
            activity.setHandleTime(new Date());
            activity.setActivityStatus(WorkflowActivityStatus.REJECT.getCode());
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
                    nextActivity.setActivityStatus(WorkflowActivityStatus.REJECT.getCode());
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
                        .eq(WorkflowActivity::getActivityStatus,WorkflowActivityStatus.SUBMIT.getCode())
                        .or()
                        .eq(WorkflowActivity::getActivityStatus,WorkflowActivityStatus.PREEMPT.getCode())
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
        currentActivity.setActivityStatus(WorkflowActivityStatus.SUBMIT.getCode());
        this.updateById(currentActivity);
        resultMap.put("code", "200");
        resultMap.put("msg", "成功");
        return resultMap;
    }

	@Override
	public WorkflowActivityVO getActivityByParam(ActivityParam param) {
		
		WorkflowActivity workflowActivity = workflowActivityService.getOne(new QueryWrapper<WorkflowActivity>().lambda()
                .eq(WorkflowActivity::getActivityStatus,WorkflowActivityStatus.WAITING.getCode())
                .eq(WorkflowActivity::getIsStart,0)
                .eq(WorkflowActivity::getInstanceId,param.getInstanceId()));
		
		if(workflowActivity != null) {
			WorkflowActivityVO workflowActivityVO = new WorkflowActivityVO();
			BeanUtils.copyProperties(workflowActivity, workflowActivityVO);
			return workflowActivityVO;
		}
		return null;
	}

	@Override
	public void advanceCurrentActivity(AdvanceBeanVO advanceBeanVO, Map<String, String> map) {
		
		WorkflowActivity currentActivity = null;
        Map<String, String> modelMapToPerson= null;
        if (advanceBeanVO!=null) {
            String currentActivityId=advanceBeanVO.getCurrentActivityId();
            if (currentActivityId!=null&&!currentActivityId.equals("")) {
                currentActivity=getById(currentActivityId);
            }else {
                throw new CustomException(WorkflowCode.NODE_ID_CAN_NOT_NULL);
            }
            //下一流转环节的处理人Map
            modelMapToPerson=advanceBeanVO.getModelMapToPerson();
        }else {
            throw new CustomException(WorkflowCode.ADVANCE_CAN_NOT_NULL);
        }
        //下级环节集合
        List<WorkflowNode> nextModels= null;
        if (currentActivity!=null) {
            String currentModelId=currentActivity.getNodeId();
            if (currentModelId==null||"".equals(currentModelId)) {
                throw new CustomException(WorkflowCode.WORKFLOW_ACTIVITY_NODE_ID_NOT_NULL);
            }

            if (currentActivity.getActivityStatus() != 0) {
                throw new CustomException(WorkflowCode.WORKFLOW_ACTIVITY_STATUS_ERROR);
            }

            WorkflowNode nextNodeById = workflowNodeMapper.getNextNodeById(currentModelId);
//            nextModels=workFlowModelDao.getSubordinateModel(currentModelId);
            //如果没有下级环节，则直接完成当前环节，完成流程实例
            if (nextModels==null||nextModels.size()==0) {
                currentActivity.setActivityStatus(WorkflowActivityStatus.SUBMIT.getCode());
                currentActivity.setHandleTime(new Date());
                this.updateById(currentActivity);
                WorkflowInstance instance = workflowInstanceService.getById(currentActivity.getInstanceId());
//                Instance instance=instanceDao.selectById(currentActivity.getInstanceid());
                instance.setCompleteTime(new Date());
                instance.setInstanceStatus(WorkflowInstanceStatus.COMPLETE.getCode());
                workflowInstanceService.updateById(instance);
            }
        }else {
            throw new CustomException(WorkflowCode.WORKFLOW_NODE_INFO_NULL);
        }

       /* if (modelMapToPerson.size()<1) {
            throw new BaseException( "办理人不能为空", 201);
        }*/
        //用来判断是否审批不通过跳级流转到最后一个环节
        Integer jujeAdvaceToLastModel=0;
        for (WorkflowNode workflowNode:nextModels) {
            //获取当前环节指定的办理人
            //判断定义的下级环节是否存在于前台传入需要流传的下级环节中,如果不存在,直接进入下一个循环
            if (!modelMapToPerson.containsKey(workflowNode.getId())) {
                jujeAdvaceToLastModel++;
                continue;
            }
            //取得对应环节的处理人
            String personids=modelMapToPerson.get(workflowNode.getId());
            //如果处理人为空则取出默认处理人
            if (personids==null||personids.trim().equals("")) {
                personids=workflowNode.getDefaultHandler();
                //如果默认处理人也为空则直接返回提示信息给前台
                if (personids==null||personids.trim().equals("")) {
                    throw new CustomException(WorkflowCode.WORKFLOW_NODE_NO_HANDLER);
                }
            }
            //把办理人分割成数组
            String personArr[]=personids.split(",");
            if (personArr.length==0) {
                throw new CustomException(WorkflowCode.HANDLER_NULL);
            }
            //获取需要的参与人对应关系
            Map<String, String> adviserMap=advanceBeanVO.getModelMapToAdviser();
            String adviserIds=null;
            if (adviserMap!=null) {
                adviserIds=adviserMap.get(workflowNode.getId());
            }
            //把参与人分割成数组
            String adviserArr[]=null;
            if (adviserIds!=null) {
                adviserArr=adviserIds.split(",");
            }
            
            handleFlow(workflowNode, currentActivity, personArr,adviserArr,map);
            
        }
        if (jujeAdvaceToLastModel>0&&modelMapToPerson.size()==1) {
            Set<String> set=modelMapToPerson.keySet();
            String id="";
            for (String key:set) {
                id=key;
            }
            WorkflowNode model=workflowNodeService.getById(id);
            if (model!=null) {
                String [] personArr=modelMapToPerson.get(id).split(",");
                if (personArr.length==0) {
                    throw new CustomException(WorkflowCode.HANDLER_NULL);
                }
                
                handleFlow(model, currentActivity, personArr,null,map);
                
            }else {
                throw new CustomException(WorkflowCode.END_ACTIVITY_NULL);
            }
        }
        //当前流转信息更新为已提交,同级(其它处理人)在handleFlow已处理为已抢占
        currentActivity.setHandleTime(new Date());
        currentActivity.setActivityStatus(WorkflowActivityStatus.SUBMIT.getCode());
        this.updateById(currentActivity);
		
	}

	@Override
	public List<WorkflowActivityVO> findActivityByParam(ActivityParam param) {
		
		String activityType = param.getActivityType();
		List<WorkflowActivity> list = null;
		if(StringUtils.isNotBlank(activityType)) {
			list = this.list(new QueryWrapper<WorkflowActivity>().lambda()
	                .eq(WorkflowActivity::getInstanceId,param.getInstanceId())
	                .eq(WorkflowActivity::getHandlePersons,param.getHandlePersons())
	                .eq(WorkflowActivity::getActivityStatus,param.getActivitystatus())
	                .eq(WorkflowActivity::getActivityType,activityType));
		}else {
			list = this.list(new QueryWrapper<WorkflowActivity>().lambda()
	                .eq(WorkflowActivity::getInstanceId,param.getInstanceId())
	                .eq(WorkflowActivity::getHandlePersons,param.getHandlePersons())
	                .eq(WorkflowActivity::getActivityStatus,param.getActivitystatus())
	                .isNull(WorkflowActivity::getActivityType));
		}
		
		if(CollectionUtils.isNotEmpty(list)) {
			List<WorkflowActivityVO> serverList = JSON.parseObject(JSON.toJSON(list).toString(), 
	    			new TypeReference<List<WorkflowActivityVO>>(){});
			return serverList;
		}
		
		return null;
	}

    @Override
    public Map<String, String> add(String handlerPersonIds, String currentActivityId,String creatorId) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("code", "201");
        WorkflowActivity currentActivity = null;

        if (currentActivityId!=null&&!currentActivityId.equals("")) {
            currentActivity=this.getById(currentActivityId);
        }else {
            resultMap.put("msg", "当前环节ID不能为空");
            return resultMap;
        }

        currentActivity.setActivityStatus(WorkflowActivityStatus.AUDIT.getCode());
        workflowActivityService.updateById(currentActivity);
        WorkflowActivity activity = new WorkflowActivity();
        activity.setHandleTime(new Date());
        activity.setActivityStatus(WorkflowActivityStatus.PREEMPT.getCode());
        // 其它参与人改为抢占
        this.update(activity, new QueryWrapper<WorkflowActivity>().lambda()
                .eq(WorkflowActivity::getNodeId, currentActivity.getNodeId())
                .eq(WorkflowActivity::getInstanceId, currentActivity.getInstanceId()).ne(WorkflowActivity::getId, currentActivity.getId()));
        // 插入指定加办人
        WorkflowActivity advanceActivity=new WorkflowActivity();
        advanceActivity.setId(UUIDUtil.getUUID());
        advanceActivity.setActivityStatus(WorkflowActivityStatus.WAITING.getCode());
        advanceActivity.setCreateTime(new Date());
        advanceActivity.setCreator(creatorId);
        advanceActivity.setInstanceId(currentActivity.getInstanceId());
        advanceActivity.setNodeId(currentActivity.getNodeId());
        advanceActivity.setPreActivityId(currentActivity.getPreActivityId());
        advanceActivity.setHandlePersons(handlerPersonIds);
        this.save(advanceActivity);
        WorkflowNode curmodel = workflowNodeService.getById(currentActivity.getNodeId());
        resultMap.put("stepName", curmodel.getNodeName());
        resultMap.put("type","6");
        resultMap.put("code", "200");
        resultMap.put("msg","成功");
        return resultMap;
    }

    /**
     * 终止流程
     * @param applyInfoId
     * @return
     */
    @Override
    public Map<String,String> terminationOrder(String applyInfoId) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("code", "201");

        WorkflowInstance instance=workflowInstanceService.getOne(new QueryWrapper<WorkflowInstance>().lambda().eq(WorkflowInstance::getBusinessId,applyInfoId));
        if(instance==null){
            resultMap.put("msg", "未找到相关流程实例");
            return resultMap;
        }
        String instanceId=instance.getId();
        instance.setCompleteTime(new Date());
        instance.setInstanceStatus(WorkflowInstanceStatus.TERMINATE.getCode());
        workflowInstanceService.updateById(instance);
        this.update(new WorkflowActivity(), new UpdateWrapper<WorkflowActivity>().lambda()
                .eq(WorkflowActivity::getInstanceId, instanceId)
                .eq(WorkflowActivity::getActivityStatus, WorkflowActivityStatus.WAITING.getCode())
                .set(WorkflowActivity::getActivityStatus, WorkflowActivityStatus.TERMINAT.getCode()));
        resultMap.put("code", "200");
        resultMap.put("msg", "成功");
        return resultMap;
    }
    /**
     * 拒绝回退到申请
     * @param currentActivityId
     * @param fallBackModelId
     * @return
     */
    @Override
    public Map<String,String> rejectApply(String currentActivityId,String fallBackModelId) {
        Map resultMap = new HashMap();
        resultMap.put("code", "201");
        if (StringUtils.isEmpty(currentActivityId)) {
            resultMap.put("msg","当前环节ID不能为空,回退失败");
            return resultMap;
        }
        WorkflowActivity currentActivity=this.getById(currentActivityId);
        if (currentActivity==null) {
            resultMap.put("msg","未查询到当前环节信息,回退失败");
            return resultMap;
        }
        String modelIds=fallBackModelId;
        if (modelIds==null||modelIds.trim().equals("")) {
            resultMap.put("msg","回退环节ID不能为空,回退失败");
            return resultMap;
        }
        if (!currentActivity.getActivityStatus().equals(WorkflowActivityStatus.WAITING.getCode())) {
            resultMap.put("msg","该任务已经处理，不能重复处理");
            return resultMap;
        }
        //判断当前环节定义的处理模式是会签/抢占,如果为会签则当一个人处理了则不能回退,如果没有处理则直接回退
        String currentModelid=currentActivity.getNodeId();
        if (currentModelid==null) {
            resultMap.put("msg","未找到当前环节定义,回退失败");
            return resultMap;
        }
        int count=0;
        List<WorkflowActivity> activities=selectFallBackActivity(currentActivity,modelIds);
        for (WorkflowActivity activity:activities) {
            count++;
            String id=UUIDUtil.getUUID();
            activity.setId(id);
            activity.setActivityStatus(WorkflowActivityStatus.WAITING.getCode());
            activity.setCreateTime(new Date());
            activity.setCreator(currentActivity.getHandlePersons());
            activity.setPreActivityId(currentActivityId);;
            this.save(activity);
        }
        if (count==0) {
            resultMap.put("msg","回退失败,未找到上级处理人或当前环节不能回退!");
            return resultMap;
        }
        //更新当前环节为已回退
        //同时流转给多个人时当前环节有多个待办
        List<WorkflowActivity> currentactivities=this.list(new QueryWrapper<WorkflowActivity>().lambda()
                .eq(WorkflowActivity::getNodeId,currentActivity.getNodeId())
                .eq(WorkflowActivity::getInstanceId,currentActivity.getInstanceId()));
        for (WorkflowActivity activity:currentactivities) {
            activity.setHandleTime(new Date());
            activity.setActivityStatus(WorkflowActivityStatus.REJECT.getCode());
            this.updateById(activity);
            resultMap.put("nodeId", activity.getNodeId());
        }
        resultMap.put("code", "200");
        resultMap.put("msg", "成功");
        return resultMap;
    }
}
