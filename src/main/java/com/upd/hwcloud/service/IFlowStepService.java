package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.FlowStep;
import com.upd.hwcloud.bean.entity.User;

import java.util.List;

/**
 * <p>
 * 处理环节 服务类
 * </p>
 *
 * @author wuc
 * @since 2018-11-26
 */
public interface IFlowStepService extends IService<FlowStep> {

    void updateRef(User user, String flowId, List<FlowStep> stepList);

    /**
     * 根据流程id 获取步骤
     * @param flowId 流程 id,或服务 id
     * @param includeUser 是否包含用户
     * @return
     */
    List<FlowStep> getListByFlowId(String flowId, boolean includeUser);

    /**
     * 查询用户的适用资源类型的流程步骤(如果资源类型不是daas,则serviceId必填)
     * @param serviceId 服务 id
     * @param resourceType 适用资源类型
     * @param stepType 类型 1:审核类型 2:实施类型
     */
    List<FlowStep> getUserFlowStepList(User user, String serviceId, Long resourceType, String stepType);

    /**
     * 查询用户的适用资源类型的流程步骤(包含审核和实施)
     */
    List<FlowStep> getUserAllFlowStepList(User user, Long resourceType);

    /**
     * 查询适用资源类型的流程步骤(如果resourceType是daas类型,则使用resourceType查,否则使用serviceId查)
     * @param serviceId 服务 id
     * @param resourceType 适用资源类型
     * @param stepType 类型 1:审核类型 2:实施类型
     */
    List<FlowStep> getFlowStepList(String serviceId, Long resourceType, String stepType);

}
