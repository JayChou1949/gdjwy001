package com.hirisun.cloud.api.workflow;

import com.hirisun.cloud.model.workflow.AdvanceBeanVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

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
    @ApiOperation("根据参数获取流程活动")
    @PostMapping(workflowActivityBaseUrl+"/feign/getWorkflowActivityByParams")
    public String getWorkflowActivityByParams(@RequestParam Integer status, @RequestParam String instanceId);

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
    public String getWorkflowNodeByParams(@RequestParam Integer version, @RequestParam String workflowId, @RequestParam Integer nodeSort);

    /**
     * 环节流转
     *
     * @param advanceBeanVO   流转VO
     * @param map             短信消息map
     * @param workflowNodeStr 下个环节json串
     */
    @ApiIgnore
    @ApiOperation("环节流转")
    @PostMapping(value = workflowActivityBaseUrl + "/feign/advanceCurrentActivity",consumes = "application/json")
    public void advanceCurrentActivity(@RequestBody AdvanceBeanVO advanceBeanVO, @RequestParam Map<String, String> map, @RequestParam String workflowNodeStr);

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
}
