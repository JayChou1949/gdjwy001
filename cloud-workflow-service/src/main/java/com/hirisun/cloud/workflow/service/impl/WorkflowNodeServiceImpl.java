package com.hirisun.cloud.workflow.service.impl;

import com.hirisun.cloud.workflow.bean.WorkflowNode;
import com.hirisun.cloud.workflow.mapper.WorkflowNodeMapper;
import com.hirisun.cloud.workflow.service.WorkflowNodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 流程模型 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-05
 */
@Service
public class WorkflowNodeServiceImpl extends ServiceImpl<WorkflowNodeMapper, WorkflowNode> implements WorkflowNodeService {

    @Autowired
    private WorkflowNodeMapper workflowNodeMapper;

    @Override
    public WorkflowNode getNextNodeById(String nodeId) {
        return workflowNodeMapper.getNextNodeById(nodeId);
    }
}
