package com.hirisun.cloud.workflow.controller.manage;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hirisun.cloud.model.workflow.AdvanceBeanVO;
import com.hirisun.cloud.workflow.bean.WorkflowActivity;
import com.hirisun.cloud.workflow.bean.WorkflowInstance;
import com.hirisun.cloud.workflow.bean.WorkflowNode;
import com.hirisun.cloud.workflow.service.WorkflowActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * <p>
 * 流程流转表 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-05
 */
@Api(tags = "工作流活动管理")
@RestController
@RequestMapping("/workflow/workflowActivityManage")
public class WorkflowActivityManageController {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowInstanceManageController.class);

    @Autowired
    private WorkflowActivityService workflowActivityService;

    /**
     * 根据参数获取流程活动
     * @param status  流程活动状态
     * @param instanceId    流程实例id
     */
    @ApiIgnore
    @ApiOperation("根据参数获取流程活动")
    @PostMapping("/feign/getWorkflowActivityByParams")
    public String getWorkflowActivityByParams(@RequestParam Integer status,@RequestParam String instanceId) {
        logger.info("/feign/getWorkflowInstanceByBusinessId：{},{}",status,instanceId);
        WorkflowActivity activity = workflowActivityService.getOne(new QueryWrapper<WorkflowActivity>().lambda()
                .eq(WorkflowActivity::getActivityStatus,status)
                .eq(WorkflowActivity::getInstanceId,instanceId));
        return JSON.toJSONString(activity);
    }

    /**
     * 环节流转
     * @param advanceBeanVO 流转VO
     * @param map 短信消息map
     * @param workflowNodeStr 下个环节json串
     */
    @ApiIgnore
    @ApiOperation("环节流转")
    @PostMapping("/feign/advanceCurrentActivity")
    public void advanceCurrentActivity(@RequestBody AdvanceBeanVO advanceBeanVO,@RequestParam Map<String, String> map,@RequestParam String workflowNodeStr) {

        WorkflowNode nextNode = JSON.parseObject(workflowNodeStr, WorkflowNode.class);
        workflowActivityService.advanceCurrentActivity(advanceBeanVO,map,nextNode);
    }
}

