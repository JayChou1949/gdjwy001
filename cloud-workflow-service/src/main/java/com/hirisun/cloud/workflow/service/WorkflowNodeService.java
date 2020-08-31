package com.hirisun.cloud.workflow.service;

import com.hirisun.cloud.model.param.WorkflowNodeParam;
import com.hirisun.cloud.model.workflow.WorkflowNodeVO;
import com.hirisun.cloud.workflow.bean.WorkflowNode;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 流程模型 服务类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-05
 */
public interface WorkflowNodeService extends IService<WorkflowNode> {

    WorkflowNode getNextNodeById(String nodeId);
    
    WorkflowNodeVO getNodeByParam(WorkflowNodeParam param);

    void syncOldData();

}
