package com.hirisun.cloud.workflow.service.impl;

import com.alibaba.fastjson.JSON;
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.workflow.bean.Workflow;
import com.hirisun.cloud.workflow.bean.WorkflowNode;
import com.hirisun.cloud.workflow.mapper.WorkflowMapper;
import com.hirisun.cloud.workflow.service.WorkflowNodeService;
import com.hirisun.cloud.workflow.service.WorkflowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 流程定义表 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-05
 */
@Service
public class WorkflowServiceImpl extends ServiceImpl<WorkflowMapper, Workflow> implements WorkflowService {

    @Autowired
    private WorkflowNodeService workflowNodeService;

    private static Integer TYPE_APPLY=1;
    private static Integer TYPE_DEP_APPROVE=2;
    private static Integer TYPE_FEEDBACK = 9;




    /**
     * 根据预设类型获取指定的环节
     * @param type 1 申请 2本单位审批 3二级管理员审批 4反馈
     * @param workflowId 工作流id
     * @return
     */
    public WorkflowNode getWorkflowByType(Integer type,String workflowId){
        WorkflowNode node = new WorkflowNode();
        node.setId(UUIDUtil.getUUID());
        String nodeName="";
        Integer nodeSort=1;
        String nodeFeature="1";
        //nodeFeature 环节功能 1可审核 2可加办 3可实施 4可删除 5可修改 6可反馈 7可转发 8可回退
        switch (type) {
            case 1:
                nodeName="申请";
                nodeSort=1;
                nodeFeature="1,5";
                break;
            case 2:
                nodeName="本单位审批";
                nodeSort=2;
                nodeFeature = "1,2,7,8";
                break;
            case 3:
                nodeName="二级管理员审批";
                nodeFeature="1";
                nodeSort=2;
                break;
            case 9:
                nodeName="反馈";
                nodeFeature="6";
                break;
            default:
                break;
        }
        node.setWorkflowId(workflowId);
        node.setNodeName(nodeName);
        node.setNodeSort(nodeSort);
        node.setVersion(0);
        node.setNodeFeature(nodeFeature);
        node.setModelType(0);
        return node;
    }

    /**
     * 更新或保存工作流
     * @param user 登录用户
     * @param workflow  工作流
     */
    @Transactional
    @Override
    public boolean saveOrUpdateWorkflow(UserVO user, Workflow workflow,String list) {
        if (StringUtils.isEmpty(workflow.getId())) {
            return saveWorkFlow(user,workflow,list);
        }else{
            return updateWorkfLow(user,workflow,list);
        }
    }

    /**
     * 修改工作流
     * @param user
     * @param workflow
     */
    private boolean updateWorkfLow(UserVO user, Workflow workflow,String list) {
        /**
         * 1.修改流程，变更流程版本
         * 2.重新排序
         * 3.系统固定流程不变
         */
        Integer version = workflow.getVersion() == null ? 0 : workflow.getVersion() + 1;
        List<WorkflowNode> nodeList = JSON.parseArray(list, WorkflowNode.class);
        int sort=1;
        for (WorkflowNode node : nodeList) {
            node.setWorkflowId(workflow.getId());
            node.setNodeSort(sort);
            node.setVersion(version);
            sort++;
        }
        workflow.setVersion(version);
        this.updateById(workflow);
        workflowNodeService.saveBatch(nodeList);
//        workflowNodeService.updateBatchById(nodeList);
        return true;
    }

    /**
     * 保存工作流
     * @param user
     * @param workflow
     */

    private boolean saveWorkFlow(UserVO user, Workflow workflow,String list) {
        String workflowId = UUIDUtil.getUUID();
        List<WorkflowNode> nodeList = JSON.parseArray(list, WorkflowNode.class);
        LinkedList<WorkflowNode> allNodeList = new LinkedList<>();
        if (nodeList == null || nodeList.size() == 0) {
            return false;
        }
        /**
         * 0.新增工作流
         * 1.新增申请环节
         * 2.新增本单位审批或二级管理员审批
         * 3.新增普通环节
         * 4.新增反馈环节
         */
        allNodeList.add(getWorkflowByType(TYPE_APPLY, workflowId));
        allNodeList.add(getWorkflowByType(TYPE_DEP_APPROVE, workflowId));
        Integer sort=3;
        for (WorkflowNode node : nodeList) {
            node.setNodeSort(sort);
            node.setWorkflowId(workflowId);
            node.setModelType(1);
            allNodeList.add(node);
            sort++;
        }
        WorkflowNode feedbackNode = getWorkflowByType(TYPE_FEEDBACK, workflowId);
        feedbackNode.setNodeSort(sort);
        allNodeList.add(feedbackNode);

        workflow.setCreator(user.getName());
        workflow.setCreatorId(user.getIdCard());
        workflow.setCreatorOrgId(user.getOrgId());
        workflow.setId(workflowId);
        this.save(workflow);
        workflowNodeService.saveBatch(allNodeList);
        return true;
    }
}
