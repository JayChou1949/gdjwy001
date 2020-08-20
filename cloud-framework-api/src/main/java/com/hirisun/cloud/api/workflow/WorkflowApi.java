package com.hirisun.cloud.api.workflow;

import com.hirisun.cloud.model.apply.FallBackVO;
import com.hirisun.cloud.model.workflow.AdvanceBeanVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;


/**
 * @author wuxiaoxing
 * @date 2020-08-07
 * @description
 */
@FeignClient("cloud-workflow-service")
public interface WorkflowApi {
    static String workflowBaseUrl="/workflow/workflowManage";
    static String workflowNodeBaseUrl="/workflow/workflowNodeManage";
    static String workflowActivityBaseUrl="/workflow/workflowActivityManage";
    static String workflowInstanceUrl="/workflow/workflowInstanceManage";
    static String workflowServiceBindingUrl="";


    /**
     * 执行保存应用申请实例
     *
     * @param createPersonId 创建人身份证
     * @param flowId         流程id
     * @param businessId     服务id
     */
    @ApiIgnore
    @ApiOperation("执行保存应用申请实例")
    @PostMapping(workflowInstanceUrl+"/feign/launchInstanceOfWorkflow")
    public void launchInstanceOfWorkflow(@RequestParam String createPersonId, @RequestParam String flowId, @RequestParam String businessId);

    /**
     * 执行保存应用申请实例
     *
     * @param creatorName 创建人身份证
     * @param flowId         流程id
     * @param businessId     服务id
     */
    @ApiIgnore
    @ApiOperation("执行保存应用申请实例")
    @PostMapping(workflowInstanceUrl+"/feign/launchInstanceByArea")
    public String launchInstanceByArea(@RequestParam String creatorName, @RequestParam String flowId, @RequestParam String businessId);

    /**
     * 根据服务id获取流程实例
     *
     * @param businessId 服务id
     */
    @ApiIgnore
    @ApiOperation("执行保存应用申请实例")
    @PostMapping(workflowInstanceUrl+"/feign/getWorkflowInstanceByBusinessId")
    public String getWorkflowInstanceByBusinessId(@RequestParam String businessId);

    /**
     * 根据参数获取流程活动
     *
     * @param status     流程活动状态
     * @param instanceId 流程实例id
     */
    @ApiIgnore
    @ApiOperation("根据参数获取一个流程流转信息")
    @PostMapping(workflowActivityBaseUrl+"/feign/getOneWorkflowActivityByParams")
    public String getOneWorkflowActivityByParams(@RequestParam Integer status, @RequestParam String instanceId);

    /**
     * 根据参数获取流程活动
     *
     * @param status     流程活动状态
     * @param instanceId 流程实例id
     */
    @ApiIgnore
    @ApiOperation("根据参数获取流程流转列表")
    @PostMapping(workflowActivityBaseUrl+"/feign/getWorkflowActivityListByParams")
    public String getWorkflowActivityListByParams(@RequestParam Integer status, @RequestParam String instanceId);

    /**
     * 根据参数获取流程环节
     *
     * @param version    流程版本
     * @param workflowId 流程id
     * @param nodeSort   环节顺序
     */
    @ApiIgnore
    @ApiOperation("根据参数获取流程环节")
    @PostMapping(workflowNodeBaseUrl+"/feign/getWorkflowNodeByParams")
    public String getWorkflowNodeByParams(@RequestParam Integer version, @RequestParam String workflowId, @RequestParam(required = false) Integer nodeSort);

    /**
     * 根据参数获取流程环节
     *
     * @param version    流程版本
     * @param workflowId 流程id
     * @param instanceId 实例id
     */
    @ApiIgnore
    @ApiOperation("根据参数获取流程环节")
    @PostMapping(workflowNodeBaseUrl + "/feign/getWorkflowNodeAndActivitys")
    public String getWorkflowNodeAndActivitys(@RequestParam Integer version, @RequestParam String workflowId, @RequestParam String instanceId);


    /**
     * 环节流转
     *
     * @param advanceBeanVO   流转VO
     * @param map             短信消息map
     * @param workflowNodeStr 下个环节json串
     */
    @ApiIgnore
    @ApiOperation("环节流转")
    @PutMapping(value = workflowActivityBaseUrl + "/feign/advanceCurrentActivity",consumes = "application/json")
    public void advanceCurrentActivity(@RequestBody AdvanceBeanVO advanceBeanVO, @RequestParam Map<String, String> map, @RequestParam String workflowNodeStr);

    /**
     * 环节正常流转
     *
     * @param currentActivityId 环节id
     */
    @ApiIgnore
    @ApiOperation("环节正常流转")
    @PutMapping(workflowActivityBaseUrl + "/feign/advanceActivity")
    public Map<String, String> advanceActivity(@RequestBody String currentActivityId, @RequestParam Map<String, String> map);

    /**
     * IPDS订单选择流程（s->saasService）
     *
     * @param resourceType
     * @param area
     * @param serviceId
     * @return
     */
    @ApiIgnore
    @ApiOperation("根据参数选择并返回申请流程")
    @PostMapping(workflowBaseUrl + "/feign/chooseWorkFlow")
    public String chooseWorkFlow(@RequestParam Integer resourceType,
                                 @RequestParam(required = false) String serviceId,
                                 @RequestParam(required = false) String area,
                                 @RequestParam(required = false) String policeCategory,
                                 @RequestParam(required = false) String nationalSpecialProject);

    /**
     * 通过实例id获取处理人id
     * @param instanceIdList 实例id list
     */
    @ApiIgnore
    @ApiOperation("通过实例id获取处理人id")
    @PostMapping(workflowActivityBaseUrl+"/feign/instanceToHandleIdCards")
    public Map<String, String> instanceToHandleIdCards(@RequestParam List<String> instanceIdList);

    /**
     * 通过实例id获取处理人id
     *
     * @param activityId 流程流转id
     */
    @ApiIgnore
    @ApiOperation("通过id获取流程流转信息")
    @PostMapping(workflowActivityBaseUrl + "/feign/getActivityById")
    public String getActivityById(@RequestParam String activityId);

    /**
     * 根据流程实例id获取流程实例信息
     */
    @ApiIgnore
    @ApiOperation("根据流程实例id获取流程实例信息")
    @PostMapping(workflowInstanceUrl+"/feign/getInstanceById")
    public String getInstanceById(@RequestParam String instanceId);

    /**
     * 根据流程id获取流程信息
     */
    @ApiIgnore
    @ApiOperation("根据流程id获取流程信息")
    @PostMapping(workflowBaseUrl+"/feign/getWorkflowById")
    public String getWorkflowById(@RequestParam String workflowId);

    /**
     * 根据环节id获取环节信息
     */
    @ApiIgnore
    @ApiOperation("根据环节id获取环节信息")
    @PostMapping(workflowNodeBaseUrl+"/feign/getNodeById")
    public String getNodeById(@RequestParam String nodeId);

    /**
     * 记录申请流程驳回信息
     *
     * @param fallBackVO 流程流转id
     */
    @ApiIgnore
    @ApiOperation("记录申请流程驳回信息")
    @PostMapping(workflowActivityBaseUrl+"/feign/fallbackOnApproveNotPass")
    public Map<String, String> fallbackOnApproveNotPass(@RequestBody FallBackVO fallBackVO, @RequestParam Map map);

    /**
     * 通知人流转信息
     *
     * @param activityId 流程流转id
     */
    @ApiIgnore
    @ApiOperation("通知人流转信息")
    @PostMapping("/feign/adviseActivity")
    public Map<String, String> adviseActivity(@RequestParam String activityId);
}
