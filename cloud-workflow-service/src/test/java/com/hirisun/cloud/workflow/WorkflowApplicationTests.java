package com.hirisun.cloud.workflow;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hirisun.cloud.common.contains.WorkflowActivityStatus;
import com.hirisun.cloud.model.workflow.WorkflowNodeVO;
import com.hirisun.cloud.workflow.bean.WorkflowActivity;
import com.hirisun.cloud.workflow.bean.WorkflowNode;
import com.hirisun.cloud.workflow.service.WorkflowActivityService;
import com.hirisun.cloud.workflow.service.WorkflowNodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootTest
class WorkflowApplicationTests {

    @Autowired
    private WorkflowNodeService workflowNodeService;

    @Autowired
    private WorkflowActivityService workflowActivityService;

    @Test
    void contextLoads() {
        List<WorkflowNodeVO> voList=getWorkflowNodeAndActivitys(0,"72BAE8DE0744483F9F1E0281F1280E05","67103E7786EB406BB2C948A3F0955978");
        voList.forEach(item->{
            System.out.println(item);
        });
    }
    @Test
//    @PostMapping("/feign/getWorkflowNodeAndActivitys")
    public List<WorkflowNodeVO> getWorkflowNodeAndActivitys(@RequestParam Integer version,
                                              @RequestParam String workflowId,
                                              @RequestParam String instanceId) {
        LambdaQueryWrapper<WorkflowNode> lambdaQueryWrapper = new QueryWrapper<WorkflowNode>().lambda()
                .eq(WorkflowNode::getVersion, version)
                .eq(WorkflowNode::getWorkflowId, workflowId).orderByAsc(WorkflowNode::getNodeSort);

        List<WorkflowNode> nodeList = workflowNodeService.list(lambdaQueryWrapper);
        List<WorkflowNodeVO> nodeVoList = new ArrayList<>();

        List<Integer> notInStatus = Arrays.asList(WorkflowActivityStatus.SUBMIT.getCode(),
                WorkflowActivityStatus.FORWARD.getCode(),
                WorkflowActivityStatus.AUDIT.getCode(),
                WorkflowActivityStatus.PREEMPT.getCode());
        List<WorkflowActivity> workflowActivityList = workflowActivityService.list(new QueryWrapper<WorkflowActivity>().lambda()
                .notIn(WorkflowActivity::getActivityStatus, notInStatus).eq(WorkflowActivity::getInstanceId,instanceId));
        log.info("workflowActivityList:{}",workflowActivityList);
        if (CollectionUtils.isNotEmpty(nodeList) && CollectionUtils.isNotEmpty(workflowActivityList)) {
            nodeList.forEach(node->{
                workflowActivityList.forEach(activity->{
                    if (node.getId().equals(activity.getNodeId())) {
                        node.setNodeStatus(activity.getActivityStatus());
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

}
