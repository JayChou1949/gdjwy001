package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.FlowStepUser;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.dao.FlowStepUserMapper;
import com.upd.hwcloud.service.IFlowStepUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 处理环节审核人关联表 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2018-11-26
 */
@Service
public class FlowStepUserServiceImpl extends ServiceImpl<FlowStepUserMapper, FlowStepUser> implements IFlowStepUserService {

    @Autowired
    private FlowStepUserMapper flowStepUserMapper;

    @Override
    public List<User> findUserByFlowStepId(String flowStepId) {
        return flowStepUserMapper.findUserByFlowStepId(flowStepId);
    }

    @Override
    public void deleteUserByFlowId(String flowId) {
        flowStepUserMapper.deleteUserByFlowId(flowId);
    }

}
