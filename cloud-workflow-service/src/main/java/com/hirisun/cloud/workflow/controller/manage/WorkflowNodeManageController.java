package com.hirisun.cloud.workflow.controller.manage;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hirisun.cloud.common.contains.WorkflowActivityStatus;
import com.hirisun.cloud.model.param.WorkflowNodeParam;
import com.hirisun.cloud.model.workflow.WorkflowActivityVO;
import com.hirisun.cloud.model.workflow.WorkflowNodeVO;
import com.hirisun.cloud.workflow.bean.WorkflowActivity;
import com.hirisun.cloud.workflow.bean.WorkflowNode;
import com.hirisun.cloud.workflow.service.WorkflowActivityService;
import com.hirisun.cloud.workflow.service.WorkflowNodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 流程流转表 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-05
 */
@Api(tags = "工作流环节管理")
@RestController
@RequestMapping("/workflow/workflowNodeManage")
public class WorkflowNodeManageController {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowInstanceManageController.class);

    @Autowired
    private WorkflowNodeService workflowNodeService;

    @Autowired
    private WorkflowActivityService workflowActivityService;

    /**
     * 根据参数获取流程环节
     * @param version  流程版本
     * @param workflowId    流程id
     * @param nodeSort  环节顺序
     */
    @ApiIgnore
    @ApiOperation("根据参数获取流程环节")
    @PostMapping("/feign/getWorkflowNodeByParams")
    public List<WorkflowNodeVO> getWorkflowNodeByParams(@RequestParam Integer version,@RequestParam String workflowId,@RequestParam(required = false) Integer nodeSort) {
        LambdaQueryWrapper<WorkflowNode> lambdaQueryWrapper = new QueryWrapper<WorkflowNode>().lambda()
                .eq(WorkflowNode::getVersion, version)
                .eq(WorkflowNode::getWorkflowId, workflowId).orderByAsc(WorkflowNode::getNodeSort);
        if (nodeSort!=null) {
            lambdaQueryWrapper.eq(WorkflowNode::getNodeSort, nodeSort);
        }
        List<WorkflowNode> nodeList = workflowNodeService.list(lambdaQueryWrapper);
        List<WorkflowNodeVO> newList = new ArrayList<>();
        nodeList.forEach(item->{
            WorkflowNodeVO vo = new WorkflowNodeVO();
            BeanUtils.copyProperties(item, vo);
            newList.add(vo);
        });
        return newList;
    }
    /**
     * 根据环节id获取环节信息
     */
    @ApiIgnore
    @ApiOperation("根据环节id获取环节信息")
    @PostMapping("/feign/getNodeById")
    public WorkflowNodeVO getNodeById(@RequestParam String nodeId) {
        logger.info("/feign/getNodeById：{}",nodeId);

        WorkflowNode workflowNode = workflowNodeService.getById(nodeId);
        WorkflowNodeVO vo = new WorkflowNodeVO();
        BeanUtils.copyProperties(workflowNode,vo);
        return vo;
    }

    /**
     * 根据环节id获取环节信息
     */
    @ApiIgnore
    @ApiOperation("根据环节id获取下一个环节信息")
    @PostMapping("/feign/getNextNodeById")
    public String getNextNodeById(@RequestParam String nodeId) {
        logger.info("/feign/getNextNodeById：{}",nodeId);

        WorkflowNode workflowNode = workflowNodeService.getNextNodeById(nodeId);
        return JSON.toJSONString(workflowNode);
    }

    /**
     * 根据参数获取流程环节
     * @param version  流程版本
     * @param workflowId    流程id
     * @param instanceId  实例id
     */
    @ApiIgnore
    @ApiOperation("根据参数获取流程环节")
    @PostMapping("/feign/getWorkflowNodeAndActivitys")
    public List<WorkflowNodeVO> getWorkflowNodeAndActivitys(@RequestParam Integer version,
                                              @RequestParam String workflowId,
                                              @RequestParam String instanceId) {
        LambdaQueryWrapper<WorkflowNode> lambdaQueryWrapper = new QueryWrapper<WorkflowNode>().lambda()
                .eq(WorkflowNode::getVersion, version)
                .eq(WorkflowNode::getWorkflowId, workflowId).orderByAsc(WorkflowNode::getNodeSort);

        List<WorkflowNode> nodeList = workflowNodeService.list(lambdaQueryWrapper);
        List<WorkflowNodeVO> nodeVoList = new ArrayList<>();

        List<Integer> notInStatus = Arrays.asList(WorkflowActivityStatus.REJECT.getCode(),
                WorkflowActivityStatus.FORWARD.getCode(),
                WorkflowActivityStatus.AUDIT.getCode(),
                WorkflowActivityStatus.PREEMPT.getCode());
        List<WorkflowActivity> workflowActivityList = workflowActivityService.list(new QueryWrapper<WorkflowActivity>().lambda()
                .notIn(WorkflowActivity::getActivityStatus, notInStatus).eq(WorkflowActivity::getInstanceId,instanceId));
        logger.info("workflowActivityList:{}",workflowActivityList);
        if (CollectionUtils.isNotEmpty(nodeList) && CollectionUtils.isNotEmpty(workflowActivityList)) {
            nodeList.forEach(node->{
                workflowActivityList.forEach(activity->{
                    node.setNodeStatus(WorkflowActivityStatus.SUBMIT.getCode());
                    if (node.getId().equals(activity.getNodeId())) {
                        if (WorkflowActivityStatus.SUBMIT.getCode().equals(activity.getActivityStatus())) {
                            node.setNodeStatusCode("finished");
                        }else if (WorkflowActivityStatus.WAITING.getCode().equals(activity.getActivityStatus())) {
                            //查找到待办直接返回
                            node.setNodeStatusCode("underway");
                            return;
                        }else if (WorkflowActivityStatus.TERMINAT.getCode().equals(activity.getActivityStatus())) {
                            node.setNodeStatusCode("termination");
                        }else{
                            node.setNodeStatusCode("unfinished");
                        }
                    }
                });
                WorkflowNodeVO vo = new WorkflowNodeVO();
                BeanUtils.copyProperties(node,vo);
                nodeVoList.add(vo);
            });
        }
        return nodeVoList;
    }
    
    /**
     * 根据环节param获取环节信息
     */
    @ApiIgnore
    @ApiOperation("根据环节id获取环节信息")
    @PostMapping("/feign/node/get")
    public WorkflowNodeVO getNodeByParam(@RequestBody WorkflowNodeParam param) {
        WorkflowNodeVO workflowNode = workflowNodeService.getNodeByParam(param);
        return workflowNode;
    }

    @ApiIgnore
    @ApiOperation("旧数据迁移到新表")
    @GetMapping("/syncOldData")
    public void syncOldData() {
        workflowNodeService.syncOldData();
        return ;
    }
}

