package com.upd.hwcloud.service.wfm;

import com.upd.hwcloud.bean.entity.wfm.WorkFlowModelVo;
import com.upd.hwcloud.bean.entity.wfm.Workflow;
import com.upd.hwcloud.bean.entity.wfm.Workflowmodel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zwb
 * @since 2019-04-10
 */
public interface IWorkflowmodelService extends IService<Workflowmodel> {

    Workflowmodel getByFlowAndAct(String flowId, String act,Integer version);
    public Workflowmodel getSubModel(String modelId);
    public WorkFlowModelVo getWorkFlowDefinition(String businessId);

    Integer getMaxVersionOfFlow(String id);
}
