package com.hirisun.cloud.workflow.controller.manage;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workflow.WorkflowInstanceVO;
import com.hirisun.cloud.model.workflow.WorkflowVO;
import com.hirisun.cloud.workflow.bean.Workflow;
import com.hirisun.cloud.workflow.bean.WorkflowInstance;
import com.hirisun.cloud.workflow.service.WorkflowInstanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 * 流程实例表 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-05
 */
@Api(tags = "工作流实例管理")
@RestController
@RequestMapping("/workflow/workflowInstanceManage")
public class WorkflowInstanceManageController {

    private static final Logger logger = LoggerFactory.getLogger(WorkflowInstanceManageController.class);

    @Autowired
    private WorkflowInstanceService workflowInstanceService;


    /**
     * 执行保存应用申请实例
     * @param createPersonId 创建人身份证
     * @param flowId    流程id
     * @param businessId    服务id
     */
    @ApiIgnore
    @ApiOperation("执行保存应用申请实例")
    @PostMapping("/feign/launchInstanceOfWorkflow")
    public void  launchInstanceOfWorkflow(@RequestParam String createPersonId,@RequestParam  String flowId,@RequestParam String businessId) {
        logger.info("/feign/launchInstanceOfWorkflow：{},{},{}",createPersonId,flowId,businessId);
        String activityId = workflowInstanceService.launchInstanceOfWorkflow(createPersonId, flowId, businessId);
        return ;
    }

    /**
     * 执行保存应用申请实例
     * @param flowId    流程id
     * @param businessId    服务id
     */
    @ApiIgnore
    @ApiOperation("执行保存应用申请实例")
    @PostMapping("/feign/launchInstanceByArea")
    public String  launchInstanceByArea(@RequestParam String creatorName,@RequestParam  String flowId,@RequestParam String businessId) throws Exception {
        logger.info("/feign/launchInstanceByArea：{},{},{}",creatorName,flowId,businessId);
        workflowInstanceService.launchInstanceByArea(creatorName, flowId, businessId);
        return "success";
    }

    /**
     * 根据服务id获取流程实例
     * @param businessId    服务id
     */
    @ApiIgnore
    @ApiOperation("根据服务id获取流程实例")
    @PostMapping("/feign/getWorkflowInstanceByBusinessId")
    public String   getWorkflowInstanceByBusinessId(@RequestParam String businessId) {
        logger.info("/feign/getWorkflowInstanceByBusinessId：{}",businessId);
        WorkflowInstance instance = workflowInstanceService.getOne(new QueryWrapper<WorkflowInstance>().lambda().eq(WorkflowInstance::getBusinessId,businessId));
        return JSON.toJSONString(instance);
    }

    /**
     * 根据流程实例id获取流程实例信息
     */
    @ApiIgnore
    @ApiOperation("根据流程实例id获取流程实例信息")
    @PostMapping("/feign/getInstanceById")
    public String getInstanceById(@RequestParam String instanceId) {
        logger.info("/feign/getInstanceById：{}",instanceId);

        WorkflowInstance workflowInstance = workflowInstanceService.getById(instanceId);
        return JSON.toJSONString(workflowInstance);
    }

}

