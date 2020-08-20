package com.hirisun.cloud.order.mapper.workflow;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hirisun.cloud.order.bean.workflow.Workflowmodel;

/**
 *  Mapper 接口
 */
public interface WorkflowmodelMapper extends BaseMapper<Workflowmodel> {

    List<Workflowmodel> getSubordinateModel(String currentModelId);

    Integer getMaxVersion(String id);

    List<Workflowmodel> getByParams(Workflowmodel workflowmodel);
}
