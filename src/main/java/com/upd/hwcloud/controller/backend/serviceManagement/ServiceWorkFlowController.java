package com.upd.hwcloud.controller.backend.serviceManagement;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.upd.hwcloud.bean.entity.application.ServiceWorkFlow;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.service.application.IServiceWorkFlowService;
import com.upd.hwcloud.service.wfm.IWorkflowService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author wuc
 * @date 2020/3/3
 */
@Api("服务-流程控制器")
@RestController
@RequestMapping("/service-workflow")
public class ServiceWorkFlowController {

    @Autowired
    private IServiceWorkFlowService serviceWorkFlowService;

    @Autowired
    private IWorkflowService workflowService;

    /**
     * 服务流程配置（加入地市后版本）
     * @param serviceId  服务的ID
     * @param workFlowIds 流程的ID集合字符串( , 分割)
     * @return
     */
    @ApiOperation("新-流程配置")
    @RequestMapping(value = "/set",method = {RequestMethod.GET,RequestMethod.POST})
    @Transactional
    public R setFlows(String serviceId, String workFlowIds){

        //字符串转集合
        List<String> workFlowIdList = Splitter.on(",").trimResults().splitToList(workFlowIds);

        //提交流程集，地区唯一性校验
        if(!workflowService.distinctAreaCheck(workFlowIdList)){
            return R.error("地区流程重复配置");
        }

        //删除该服务的所有关联流程
        serviceWorkFlowService.remove(new QueryWrapper<ServiceWorkFlow>().lambda().eq(ServiceWorkFlow::getServiceId,serviceId));

        List<ServiceWorkFlow> serviceWorkFlowList = Lists.newArrayList();

        //遍历设置对应关系
        workFlowIdList.forEach(item -> {
            ServiceWorkFlow serviceWorkFlow = new ServiceWorkFlow();
            serviceWorkFlow.setServiceId(serviceId);
            serviceWorkFlow.setWorkFlowId(item);
            serviceWorkFlowList.add(serviceWorkFlow);
        });

        //批量插入
        serviceWorkFlowService.saveBatch(serviceWorkFlowList);

        return R.ok();
    }

}
