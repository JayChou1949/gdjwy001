package com.hirisun.cloud.workflow.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hirisun.cloud.api.user.UserApi;
import com.hirisun.cloud.common.contains.WorkflowActivityStatus;
import com.hirisun.cloud.common.contains.WorkflowInstanceStatus;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.workflow.bean.Workflow;
import com.hirisun.cloud.workflow.bean.WorkflowActivity;
import com.hirisun.cloud.workflow.bean.WorkflowInstance;
import com.hirisun.cloud.workflow.bean.WorkflowNode;
import com.hirisun.cloud.workflow.mapper.WorkflowInstanceMapper;
import com.hirisun.cloud.workflow.service.WorkflowActivityService;
import com.hirisun.cloud.workflow.service.WorkflowInstanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.workflow.service.WorkflowNodeService;
import com.hirisun.cloud.workflow.service.WorkflowService;
import com.hirisun.cloud.workflow.vo.WorkflowCode;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 流程实例表 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-05
 */
@Service
public class WorkflowInstanceServiceImpl extends ServiceImpl<WorkflowInstanceMapper, WorkflowInstance> implements WorkflowInstanceService {

    private static Logger logger = LoggerFactory.getLogger(WorkflowInstanceServiceImpl.class);

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private WorkflowNodeService workflowNodeService;

    @Autowired
    private WorkflowInstanceService workflowInstanceService;

    @Autowired
    private WorkflowActivityService workflowActivityService;

    @Autowired
    private UserApi userApi;

    /**
     * 创建起始流程实例，创建实例和第一个流程活动
     * @param createPersonId 创建人
     * @param flowId    流程id
     * @param businessId    应用id
     */
    @Override
    public String launchInstanceOfWorkflow(String createPersonId, String flowId, String businessId) {

        Workflow workFlow = workflowService.getById(flowId);
        logger.info("launch version :{}",workFlow.getVersion());
        if (workFlow == null) {
            logger.debug("没有流程配置信息");
            new CustomException(WorkflowCode.WORKFLOW_MISSING);
        }
        // 得到流程定义的开始环节
        WorkflowNode workFlowModel = workflowNodeService.getOne(new QueryWrapper<WorkflowNode>().lambda()
                .eq(WorkflowNode::getWorkflowId,flowId)
                .eq(WorkflowNode::getNodeSort,WorkflowNode.NODE_SORT)
                .eq(WorkflowNode::getVersion,workFlow.getVersion()));
        if (workFlowModel == null) {
            logger.info("此流程没有定义开始环节,请联系管理员!");
            new CustomException(WorkflowCode.WORKFLOW_MISSING_START_NODE);
        }
        UserVO user= userApi.getUserByIdCard(createPersonId);
        logger.debug("创建流程实例的用户：{}",user);
        // 生成实例的ID
        String insId = UUIDUtil.getUUID();
        // 创建流程实例对象
        WorkflowInstance instance = new WorkflowInstance();
        instance.setId(insId);
        instance.setCreator(createPersonId);
        instance.setCreateTime(new Date());
        instance.setCreatorOrgId(user==null?null:user.getOrgId());
        instance.setInstanceStatus(WorkflowInstanceStatus.WORKING.getCode());
        instance.setWorkflowId(flowId);
        instance.setBusinessId(businessId);
        instance.setVersion(workFlowModel.getVersion());

        // 创建开始环节信息
        WorkflowActivity firstActivity = new WorkflowActivity();

        //如果是外部提交则user为空，直接存传递过来的申请人身份证,否则存登陆人身份证
        if (user==null) {
            firstActivity.setCreator(createPersonId);
            firstActivity.setHandlePersons(null);
            firstActivity.setActivityStatus(WorkflowActivityStatus.SUBMIT.getCode());
        }else {
            firstActivity.setCreator(user.getIdcard());
            firstActivity.setHandlePersons(createPersonId);
            firstActivity.setActivityStatus(WorkflowActivityStatus.WAITING.getCode());
        }
        firstActivity.setCreateTime(new Date());
        firstActivity.setInstanceId(insId);
        firstActivity.setNodeId(workFlowModel.getId());
        firstActivity.setIsStart(0);
        firstActivity.setReciveTime(new Date());
        String actId = UUIDUtil.getUUID();
        firstActivity.setId(actId);

        // 保存流程实例
        boolean createInstanceResult = workflowInstanceService.save(instance);
        // 发起流程的第一个环节,申请环节
        boolean createActiviteResult = workflowActivityService.save(firstActivity);
        if (createActiviteResult && createInstanceResult) {
            return firstActivity.getId();
        } else {
            logger.info("发起流程失败");
            new CustomException(WorkflowCode.WORKFLOW_CREATE_FAIL);
        }
		return actId;
    }

    /**
     * 创建起始流程实例，创建实例和第一个流程活动
     * @param creatorName 创建人
     * @param businessId    应用id
     *
     * 新建一个流程类型，所有地市都使用该流程
     */
    @Override
    public void launchInstanceByArea(String creatorName, String businessId,String resourceType ) {
        Workflow workflow = workflowService.getOne(new QueryWrapper<Workflow>().lambda().eq(Workflow::getDefaultProcess, resourceType));

//        Workflow workFlow = workflowService.getById(flowId);
        logger.info("launch version :{}",workflow.getVersion());
        if (workflow == null) {
            logger.debug("没有流程配置信息");
            new CustomException(WorkflowCode.WORKFLOW_MISSING);
        }
        // 得到流程定义的开始环节
        WorkflowNode workFlowModel = workflowNodeService.getOne(new QueryWrapper<WorkflowNode>().lambda()
                .eq(WorkflowNode::getWorkflowId,workflow.getId())
                .eq(WorkflowNode::getNodeSort,WorkflowNode.NODE_SORT)
                .eq(WorkflowNode::getVersion,workflow.getVersion()));
        if (workFlowModel == null) {
            logger.info("此流程没有定义开始环节,请联系管理员!");
            new CustomException(WorkflowCode.WORKFLOW_MISSING_START_NODE);
        }
        UserVO user=null;
//        String userStr = userApi.getUserByIdCard(createPersonId);
//        if (!StringUtils.isEmpty(userStr)) {
//            user=JSON.parseObject(userStr, UserVO.class);
//        }
        logger.debug("创建流程实例的用户：{}",user);
        // 生成实例的ID
        String insId = UUIDUtil.getUUID();
        // 创建流程实例对象
        WorkflowInstance instance = new WorkflowInstance();
        instance.setId(insId);
        instance.setCreator(creatorName);
        instance.setCreateTime(new Date());
        instance.setCreatorOrgId(user==null?null:user.getOrgId());
        instance.setInstanceStatus(WorkflowInstanceStatus.WORKING.getCode());
        instance.setWorkflowId(workflow.getId());
        instance.setBusinessId(businessId);
        instance.setVersion(workflow.getVersion());

        // 创建开始环节信息
        WorkflowActivity firstActivity = new WorkflowActivity();

        //如果是外部提交则user为空，直接存传递过来的申请人身份证,否则存登陆人身份证
        firstActivity.setCreator(creatorName);
        firstActivity.setHandlePersons(null);
        firstActivity.setActivityStatus(WorkflowActivityStatus.WAITING.getCode());

        firstActivity.setCreateTime(new Date());
        firstActivity.setInstanceId(insId);
        firstActivity.setNodeId(workFlowModel.getId());
        firstActivity.setIsStart(0);
        firstActivity.setReciveTime(new Date());
        String actId = UUIDUtil.getUUID();
        firstActivity.setId(actId);

        // 保存流程实例
        boolean createInstanceResult = workflowInstanceService.save(instance);
        // 发起流程的第一个环节,申请环节
        boolean createActiviteResult = workflowActivityService.save(firstActivity);
        if (createActiviteResult && createInstanceResult) {
            return ;
        } else {
            logger.info("发起流程失败");
            new CustomException(WorkflowCode.WORKFLOW_CREATE_FAIL);
        }
    }
}
