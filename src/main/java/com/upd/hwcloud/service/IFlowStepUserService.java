package com.upd.hwcloud.service;

import com.upd.hwcloud.bean.entity.FlowStepUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.User;

import java.util.List;

/**
 * <p>
 * 处理环节审核人关联表 服务类
 * </p>
 *
 * @author wuc
 * @since 2018-11-26
 */
public interface IFlowStepUserService extends IService<FlowStepUser> {


    List<User> findUserByFlowStepId(String flowStepId);

    void deleteUserByFlowId(String flowId);

}
