package com.hirisun.cloud.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.model.workflow.AdvanceBeanVO;
import com.hirisun.cloud.workflow.bean.WorkflowActivity;
import com.hirisun.cloud.workflow.bean.WorkflowInstance;
import com.hirisun.cloud.workflow.bean.WorkflowNode;
import com.hirisun.cloud.workflow.mapper.WorkflowActivityMapper;
import com.hirisun.cloud.workflow.service.WorkflowActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.workflow.service.WorkflowInstanceService;
import com.hirisun.cloud.workflow.vo.WorkflowCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    /**
     * 流转
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
}
