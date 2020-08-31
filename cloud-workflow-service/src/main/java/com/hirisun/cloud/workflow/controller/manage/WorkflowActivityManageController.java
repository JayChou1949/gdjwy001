package com.hirisun.cloud.workflow.controller.manage;


import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.fastjson.TypeReference;
import com.hirisun.cloud.common.contains.WorkflowActivityStatus;
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.model.workflow.WorkflowVO;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hirisun.cloud.model.apply.FallBackVO;
import com.hirisun.cloud.model.param.ActivityParam;
import com.hirisun.cloud.model.workflow.AdvanceBeanVO;
import com.hirisun.cloud.model.workflow.WorkflowActivityVO;
import com.hirisun.cloud.workflow.bean.WorkflowActivity;
import com.hirisun.cloud.workflow.service.WorkflowActivityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

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
     * @param instanceId 流程实例id
     */
    @ApiIgnore
    @ApiOperation("根据参数获取一个流程流转信息")
    @PostMapping("/feign/getOneWorkflowActivityByParams")
    public WorkflowActivityVO getOneWorkflowActivityByParams(@RequestParam Integer status,@RequestParam String instanceId) {
        WorkflowActivity activity = workflowActivityService.getOne(new QueryWrapper<WorkflowActivity>().lambda()
                .eq(WorkflowActivity::getActivityStatus,status)
                .eq(WorkflowActivity::getInstanceId,instanceId));
        WorkflowActivityVO vo = new WorkflowActivityVO();
        if (activity != null) {
            BeanUtils.copyProperties(activity,vo);
        }
        return vo;
    }

    /**
     * 流程转发
     * @param currentActivityId  流程活动状态
     * @param handlePersonIds 流程实例id
     */
    @ApiIgnore
    @ApiOperation("流程转发")
    @PostMapping("/feign/activityForward")
    public Map<String,String> activityForward(@RequestParam String currentActivityId,@RequestParam String handlePersonIds) {
        Map<String, String> resultMap = new HashMap();
        resultMap.put("code", "201");
        WorkflowActivity currentActivity = workflowActivityService.getById(currentActivityId);
        if (currentActivity == null) {
            resultMap.put("msg","不存在当前环节");
            return resultMap;
        }
        String[] handlePersonArr = handlePersonIds.split(",");
        for (String handlePerson : handlePersonArr) {
            WorkflowActivity forwardActivity = new WorkflowActivity();
            String id = UUIDUtil.getUUID();
            forwardActivity.setId(id);
            forwardActivity.setActivityStatus(WorkflowActivityStatus.WAITING.getCode());
            forwardActivity.setCreator(currentActivity.getHandlePersons());
            forwardActivity.setCreateTime(new Date());
//            forwardActivity.setCreatorOrgId(handleUser.getOrganization());
            forwardActivity.setHandlePersons(handlePerson);
            forwardActivity.setInstanceId(currentActivity.getInstanceId());
            forwardActivity.setNodeId(currentActivity.getNodeId());
            forwardActivity.setPreActivityId(currentActivity
                    .getPreActivityId());
            workflowActivityService.save(forwardActivity);
        }
        currentActivity.setActivityStatus(WorkflowActivityStatus.FORWARD.getCode());
        currentActivity.setHandleTime(new Date());
        workflowActivityService.updateById(currentActivity);
        resultMap.put("code", "200");
        resultMap.put("msg","不存在当前环节");
        return resultMap;
    }

    /**
     * 根据参数获取流程活动
     * @param status  流程活动状态
     * @param instanceId    流程实例id
     */
    @ApiIgnore
    @ApiOperation("根据参数获取流程流转列表")
    @PostMapping("/feign/getWorkflowActivityListByParams")
    public List<WorkflowActivityVO> getWorkflowActivityListByParams(@RequestParam Integer status,@RequestParam String instanceId) {
        List<WorkflowActivity> activityList = workflowActivityService.list(new QueryWrapper<WorkflowActivity>().lambda()
                .eq(WorkflowActivity::getActivityStatus,status)
                .eq(WorkflowActivity::getInstanceId,instanceId));

        if(CollectionUtils.isNotEmpty(activityList)) {
            List<WorkflowActivityVO> list = JSON.parseObject(JSON.toJSON(activityList).toString(),
                    new TypeReference<List<WorkflowActivityVO>>(){});
            return list;
        }
        return null;
    }

    /**
     * 环节初次流转
     * @param advanceBeanVO 流转VO
     * @param map 短信消息map
     * @param workflowNodeStr 下个环节json串
     */
//    @ApiIgnore
//    @ApiOperation("环节初次流转")
//    @PutMapping("/feign/advanceCurrentActivity")
//    public Map<String,String> advanceCurrentActivity(@RequestBody AdvanceBeanVO advanceBeanVO,@RequestParam Map<String, String> map,@RequestParam String workflowNodeStr) {
//        WorkflowNode nextNode = JSON.parseObject(workflowNodeStr, WorkflowNode.class);
//        return workflowActivityService.advanceCurrentActivity(advanceBeanVO,map,nextNode);
//    }
    /**
     * 环节正常流转
     * @param currentActivityId 环节id
     */
    @ApiIgnore
    @ApiOperation("环节正常流转")
    @PutMapping("/feign/advanceActivity")
    public Map<String, String> advanceActivity(@RequestParam String currentActivityId,@RequestParam Map<String, String> map) {
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
    public WorkflowActivityVO getActivityById(@RequestParam String activityId) {

        WorkflowActivity workflowActivity = workflowActivityService.getById(activityId);
        WorkflowActivityVO vo = new WorkflowActivityVO();
        BeanUtils.copyProperties(workflowActivity,vo);
        return vo;
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

    @ApiIgnore
    @ApiOperation("根据参数获取单个 WorkflowActivity ")
    @PostMapping("/feign/activity/get")
    public WorkflowActivityVO getActivityByParam(@RequestBody ActivityParam param) {
    	WorkflowActivityVO activity = workflowActivityService.getActivityByParam(param);
    	return activity;
    }
    
    @ApiIgnore
    @ApiOperation("根据参数获取 WorkflowActivity 集合")
    @PostMapping("/feign/activity/find")
    public List<WorkflowActivityVO> findActivityByParam(@RequestBody ActivityParam param) {
    	List<WorkflowActivityVO> list = workflowActivityService.findActivityByParam(param);
    	return list;
    }
    
    /**
     * 环节流转
     *
     * @param advanceBeanVO   流转VO
     * @param map             短信消息map
     */
    @ApiIgnore
    @ApiOperation("环节流转")
    @PutMapping(value = "/feign/activity/advance",consumes = "application/json")
    public void activityAdvance(@RequestBody AdvanceBeanVO advanceBeanVO, @RequestParam Map<String, String> map) {
    	workflowActivityService.advanceCurrentActivity(advanceBeanVO, map);
    }
    /**
     * 加办
     */
    @ApiIgnore
    @ApiOperation("加办")
    @PostMapping("/feign/add")
    public Map<String,String> add(@RequestParam String handlerPersonIds,
                                  @RequestParam String currentActivityId,
                                  @RequestParam String creatorId) {
        Map resultMap = workflowActivityService.add(handlerPersonIds,currentActivityId,creatorId);
        return resultMap;
    }

    /**
     * 拒绝，回退到申请
     */
    @ApiIgnore
    @ApiOperation("回退到申请")
    @PostMapping("/feign/rejectApply")
    public Map<String,String> rejectApply(
                                  @RequestParam String currentActivityId,
                                  @RequestParam String fallBackModelId) {
        return workflowActivityService.rejectApply(currentActivityId,fallBackModelId);
    }

    /**
     * 终止流程
     * @param applyInfoId
     * @return
     */
    @ApiIgnore
    @ApiOperation("终止流程")
    @PostMapping("/feign/terminationOrder")
    public Map<String,String> terminationOrder(
            @RequestParam String applyInfoId) {
        return workflowActivityService.terminationOrder(applyInfoId);
    }

}

