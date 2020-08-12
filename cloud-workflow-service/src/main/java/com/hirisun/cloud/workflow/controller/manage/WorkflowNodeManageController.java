package com.hirisun.cloud.workflow.controller.manage;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hirisun.cloud.workflow.bean.WorkflowActivity;
import com.hirisun.cloud.workflow.bean.WorkflowNode;
import com.hirisun.cloud.workflow.service.WorkflowActivityService;
import com.hirisun.cloud.workflow.service.WorkflowNodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

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

    /**
     * 根据参数获取流程环节
     * @param version  流程版本
     * @param workflowId    流程id
     * @param nodeSort  环节顺序
     */
    @ApiIgnore
    @ApiOperation("根据参数获取流程环节")
    @PostMapping("/feign/getWorkflowNodeByParams")
    public String getWorkflowNodeByParams(@RequestParam Integer version,@RequestParam String workflowId,@RequestParam Integer nodeSort) {
        logger.info("/feign/getWorkflowInstanceByBusinessId：{},{},{}",version,workflowId,nodeSort);
        WorkflowNode workflowNode = workflowNodeService.getOne(new QueryWrapper<WorkflowNode>().lambda()
                .eq(WorkflowNode::getVersion, version)
                .eq(WorkflowNode::getWorkflowId, workflowId)
                .eq(WorkflowNode::getNodeSort,nodeSort));
        return JSON.toJSONString(workflowNode);
    }
}

