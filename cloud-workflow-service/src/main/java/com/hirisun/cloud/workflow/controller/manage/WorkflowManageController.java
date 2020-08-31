package com.hirisun.cloud.workflow.controller.manage;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.api.system.SmsApi;
import com.hirisun.cloud.api.system.SystemApi;
import com.hirisun.cloud.common.annotation.LoginUser;
import com.hirisun.cloud.common.contains.WorkflowStatus;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.system.SysDictVO;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workflow.WorkflowVO;
import com.hirisun.cloud.workflow.bean.Workflow;
import com.hirisun.cloud.workflow.bean.WorkflowNode;
import com.hirisun.cloud.workflow.service.WorkflowNodeService;
import com.hirisun.cloud.workflow.service.WorkflowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 流程定义表 前端控制器
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-05
 */
@Api(tags = "工作流管理")
@RestController
@RequestMapping("/workflow/workflowManage")
public class WorkflowManageController {

    private static final Logger logger = LoggerFactory.getLogger(WorkflowManageController.class);

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private SmsApi smsApi;

    @Autowired
    private WorkflowNodeService workflowNodeService;

    @ApiOperation("申请流程配置分页列表")
    @GetMapping("/page")
    public QueryResponseResult<Workflow> page(
            @LoginUser UserVO user,
            @ApiParam("页码") @RequestParam(required = false,defaultValue = "1") Integer pageNum,
            @ApiParam("每页大小") @RequestParam(required = false,defaultValue = "20") Integer pageSize,
            @ApiParam("流程名称") @RequestParam(required = false) String name,
            @ApiParam("地市名：类型为地市时传入地市名") @RequestParam(required = false) String area,
            @ApiParam("警种名：类型为警种时传入警种名") @RequestParam(required = false) String policeCategory,
            @ApiParam("国家专项名：类型为国家专项时传入国家专项名") @RequestParam(required = false) String nationalProject){

        LambdaQueryWrapper<Workflow> wrapper=new QueryWrapper<Workflow>().lambda();
        if (!StringUtils.isEmpty(area)) {
            wrapper.eq(Workflow::getArea, area);
        }
        if (!StringUtils.isEmpty(policeCategory)) {
            wrapper.eq(Workflow::getPoliceCategory, policeCategory);
        }
        if (!StringUtils.isEmpty(nationalProject)) {
            wrapper.eq(Workflow::getArea, nationalProject);
        }
        if (!StringUtils.isEmpty(name)) {
            wrapper.like(Workflow::getFlowName, name);
        }
        wrapper.eq(Workflow::getFlowStatus, WorkflowStatus.NORMAL.getCode());
        wrapper.orderByDesc(Workflow::getCreateTime);
        Page<Workflow> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page=workflowService.page(page,wrapper);
        return QueryResponseResult.success(page);
    }

    @ApiOperation("流程配置明细")
    @GetMapping("/detail")
    public QueryResponseResult<WorkflowVO> detail(@LoginUser UserVO user,
                                                @ApiParam(value = "流程配置id",required = true) @RequestParam String id) {
        Map detail = workflowService.getDetailById(id);
        return QueryResponseResult.success(detail);
    }

    @ApiOperation("新增/编辑申请流程配置")
    @PutMapping("/saveOrUpdate")
    public QueryResponseResult<WorkflowVO> saveOrUpdate(@LoginUser UserVO user,
                                                        @RequestBody Workflow workflow) {
        boolean flag=workflowService.saveOrUpdateWorkflow(user, workflow,JSON.toJSONString(workflow.getNodeList()));
        if(!flag){
            return QueryResponseResult.fail(null);
        }
        return QueryResponseResult.success(null);
    }

    @ApiOperation("删除申请流程配置")
    @PostMapping("/delete")
    public QueryResponseResult<Workflow> delete(@ApiParam(value = "流程配置id",required = true) @RequestParam String id) {
        Workflow workflow = workflowService.getById(id);
        workflow.setFlowStatus(WorkflowStatus.DELETE.getCode());
        workflowService.updateById(workflow);
        return QueryResponseResult.success(null);
    }

    /**
     * IPDS订单选择流程（s->saasService）
     * @param resourceType
     * @param area
     * @param serviceId
     * @return
     */
    @ApiIgnore
    @ApiOperation("根据参数选择并返回申请流程")
    @PostMapping("/feign/chooseWorkFlow")
    public WorkflowVO  chooseWorkFlow(@RequestParam Integer resourceType,
                                  @RequestParam(required = false) String serviceId,
                                  @RequestParam(required = false) String area,
                                  @RequestParam(required = false) String policeCategory,
                                  @RequestParam(required = false) String nationalSpecialProject) {
       Workflow workflow= workflowService.chooseWorkFlow(resourceType, area, policeCategory, serviceId, nationalSpecialProject);
        WorkflowVO vo = new WorkflowVO();
        BeanUtils.copyProperties(workflow,vo);
        return vo;
    }

    /**
     * 根据流程id获取流程信息
     */
    @ApiIgnore
    @ApiOperation("根据流程id获取流程信息")
    @PostMapping("/feign/getWorkflowById")
    public WorkflowVO getWorkflowById(@RequestParam String workflowId) {
        Workflow workflow = workflowService.getById(workflowId);
        WorkflowVO vo = new WorkflowVO();
        BeanUtils.copyProperties(workflow,vo);
        return vo;
    }
}

