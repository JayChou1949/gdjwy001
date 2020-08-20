package com.hirisun.cloud.workflow.controller.manage;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hirisun.cloud.model.apply.FallBackVO;
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

import java.util.List;
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
    @ApiOperation("根据参数获取一个流程流转信息")
    @PostMapping("/feign/getOneWorkflowActivityByParams")
    public String getOneWorkflowActivityByParams(@RequestParam Integer status,@RequestParam String instanceId) {
        logger.info("/feign/getOneWorkflowActivityByParams：{},{}",status,instanceId);
        WorkflowActivity activity = workflowActivityService.getOne(new QueryWrapper<WorkflowActivity>().lambda()
                .eq(WorkflowActivity::getActivityStatus,status)
                .eq(WorkflowActivity::getInstanceId,instanceId));
        return JSON.toJSONString(activity);
    }

    /**
     * 根据参数获取流程活动
     * @param status  流程活动状态
     * @param instanceId    流程实例id
     */
    @ApiIgnore
    @ApiOperation("根据参数获取流程流转列表")
    @PostMapping("/feign/getWorkflowActivityListByParams")
    public String getWorkflowActivityListByParams(@RequestParam Integer status,@RequestParam String instanceId) {
        logger.info("/feign/getWorkflowActivityListByParams：{},{}",status,instanceId);
        List<WorkflowActivity> activityList = workflowActivityService.list(new QueryWrapper<WorkflowActivity>().lambda()
                .eq(WorkflowActivity::getActivityStatus,status)
                .eq(WorkflowActivity::getInstanceId,instanceId));
        return JSON.toJSONString(activityList);
    }

    /**
     * 环节初次流转
     * @param advanceBeanVO 流转VO
     * @param map 短信消息map
     * @param workflowNodeStr 下个环节json串
     */
    @ApiIgnore
    @ApiOperation("环节初次流转")
    @PutMapping("/feign/advanceCurrentActivity")
    public void advanceCurrentActivity(@RequestBody AdvanceBeanVO advanceBeanVO,@RequestParam Map<String, String> map,@RequestParam String workflowNodeStr) {

        WorkflowNode nextNode = JSON.parseObject(workflowNodeStr, WorkflowNode.class);
        workflowActivityService.advanceCurrentActivity(advanceBeanVO,map,nextNode);
    }
    /**
     * 环节正常流转
     * @param currentActivityId 环节id
     */
    @ApiIgnore
    @ApiOperation("环节正常流转")
    @PutMapping("/feign/advanceActivity")
    public Map<String, String> advanceActivity(@RequestBody String currentActivityId,@RequestParam Map<String, String> map) {
        return workflowActivityService.advanceActivity(currentActivityId,map);
    }
    /**
     * 通过实例id获取处理人id
     * @param instanceIdList 实例id list
     */
    @ApiIgnore
    @ApiOperation("通过实例id获取处理人id")
    @PostMapping("/feign/instanceToHandleIdCards")
    public Map<String, String> instanceToHandleIdCards(@RequestParam List<String> instanceIdList) {

        return workflowActivityService.instanceToHandleIdCards(instanceIdList);
    }

    /**
     * 通过实例id获取处理人id
     * @param activityId 流程流转id
     */
    @ApiIgnore
    @ApiOperation("通过id获取流程流转信息")
    @PostMapping("/feign/getActivityById")
    public String getActivityById(@RequestParam String activityId) {

        WorkflowActivity workflowActivity = workflowActivityService.getById(activityId);
        return JSON.toJSONString(workflowActivity);
    }

    /**
     * 记录申请流程驳回信息
     * @param fallBackVO 流程流转id
     */
    @ApiIgnore
    @ApiOperation("记录申请流程驳回信息")
    @PostMapping("/feign/fallbackOnApproveNotPass")
    public Map<String,String> fallbackOnApproveNotPass(@RequestBody FallBackVO fallBackVO,@RequestParam Map map) {
        Map resultMap= workflowActivityService.fallbackOnApproveNotPass(fallBackVO,map);
        return resultMap;
    }

    /**
     * 通知人流转信息
     * @param activityId 流程流转id
     */
    @ApiIgnore
    @ApiOperation("通知人流转信息")
    @PostMapping("/feign/adviseActivity")
    public Map<String,String> adviseActivity(@RequestParam String activityId) {
        Map resultMap = workflowActivityService.adviseActivity(activityId);
        return resultMap;
    }

}

