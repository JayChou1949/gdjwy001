package com.hirisun.cloud.workflow.controller.manage;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.api.system.SystemApi;
import com.hirisun.cloud.common.annotation.LoginUser;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private WorkflowService workflowService;

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
        wrapper.eq(Workflow::getFlowStatus, Workflow.STATUS_NORMAL);
        Page<Workflow> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page=workflowService.page(page,wrapper);
        return QueryResponseResult.success(page);
    }

    @ApiOperation("流程配置明细")
    @GetMapping("/detail")
    public QueryResponseResult<Workflow> detail(@LoginUser UserVO user,
                                                @ApiParam(value = "流程配置id",required = true) @RequestParam String id) {
        /**
         * 1.查询流程配置
         * 2.查询对应环节
         */
        Workflow workflow = workflowService.getById(id);
        List<WorkflowNode> nodeList = workflowNodeService.list(new QueryWrapper<WorkflowNode>().lambda()
                .eq(WorkflowNode::getWorkflowId,workflow.getId())
                .orderByAsc(WorkflowNode::getNodeSort));
        Map map = new HashMap<>();
        map.put("workflow", workflow);
        map.put("nodeList", nodeList);
        return QueryResponseResult.success(map);
    }

    @ApiOperation("新增/编辑申请流程配置")
    @PostMapping("/saveOrUpdate")
    public QueryResponseResult<WorkflowVO> saveOrUpdate(@LoginUser UserVO user,
                                                        @ModelAttribute Workflow workflow ,
                                                        @ApiParam(value = "环节列表,需要封装成json数组传入",required = true) @RequestParam String nodeList) {
        boolean flag=workflowService.saveOrUpdateWorkflow(user, workflow,nodeList);
        if(!flag){
            return QueryResponseResult.fail(null);
        }
        return QueryResponseResult.success(null);
    }
    @ApiOperation("删除申请流程配置")
    @PostMapping("/delete")
    public QueryResponseResult<Workflow> delete(@ApiParam(value = "流程配置id",required = true) @RequestParam String id) {

        Workflow workflow = workflowService.getById(id);
        workflow.setFlowStatus(Workflow.STATUS_DELETE);
        workflowService.updateById(workflow);
        return QueryResponseResult.success(null);
    }
}

