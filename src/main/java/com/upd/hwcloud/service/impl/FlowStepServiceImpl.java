package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.contains.ResourceType;
import com.upd.hwcloud.bean.entity.ApplyFlow;
import com.upd.hwcloud.bean.entity.FlowStep;
import com.upd.hwcloud.bean.entity.FlowStepUser;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.dao.FlowStepMapper;
import com.upd.hwcloud.dao.UserMapper;
import com.upd.hwcloud.service.IApplyFlowService;
import com.upd.hwcloud.service.IFlowStepService;
import com.upd.hwcloud.service.IFlowStepUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 处理环节 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2018-11-26
 */
@Service
public class FlowStepServiceImpl extends ServiceImpl<FlowStepMapper, FlowStep> implements IFlowStepService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FlowStepMapper flowStepMapper;
    @Autowired
    private IApplyFlowService applyFlowService;
    @Autowired
    private IFlowStepUserService flowStepUserService;

    @Transactional
    @Override
    public void updateRef(User user, String flowId, List<FlowStep> stepList) {
        // 删除关联的处理人
        flowStepUserService.deleteUserByFlowId(flowId);
        // 删除步骤
        this.remove(new QueryWrapper<FlowStep>().lambda().eq(FlowStep::getFlowId, flowId));
        if (stepList == null || stepList.isEmpty()) {
            return;
        }
        varifyData(stepList);
        // 插入数据
        for (FlowStep step : stepList) {
            step.setCreator(user.getIdcard()); // 创建人
            step.setFlowId(flowId);
            step.insert();
            refUser(step);
        }
    }

    private void varifyData(List<FlowStep> stepList) {
        Set<Long> sortSet = new HashSet<>();
        int implStepCount = 0;
        for (FlowStep step : stepList) {
            if (step.getSort() != null) {
                sortSet.add(step.getSort());
            }
            if (Objects.equals(step.getType(), Long.valueOf(2L))) {
                implStepCount++;
            }
        }
        if (sortSet.size() != stepList.size()) {
            throw new BaseException("环节排序不能重复!");
        }
        if (implStepCount > 1) {
            throw new BaseException("实施环节有且只有一个!");
        }
    }

    /**
     * 与处理人建立关系
     */
    private void refUser(FlowStep step) {
        List<User> userList = step.getUserList();
        if (userList == null || userList.isEmpty()) {
            throw new BaseException("请选择处理人");
        }
        // 去重
        Set<String> userIdSet = new HashSet<>(userList.size());
        for (User user : userList) {
            userIdSet.add(user.getIdcard());
        }

        FlowStepUser stepUser;
        for (String id : userIdSet) {
            stepUser = new FlowStepUser();
            stepUser.setStepId(step.getId());
            stepUser.setUserId(id);
            stepUser.insert();
        }
    }

    @Override
    public List<FlowStep> getListByFlowId(String flowId, boolean includeUser) {
        Wrapper<FlowStep> qw = new QueryWrapper<FlowStep>().lambda()
                .eq(FlowStep::getFlowId, flowId)
                .orderByAsc(FlowStep::getSort)
                .orderByDesc(FlowStep::getModifiedTime);
        List<FlowStep> list = this.list(qw);
        if (includeUser) {
            for (FlowStep step : list) {
                List<User> userList = userMapper.getUserListByStepId(step.getId());
                step.setUserList(userList);
            }
        }
        return list;
    }

    @Override
    public List<FlowStep> getUserFlowStepList(User user, String serviceId, Long resourceType, String stepType) {
        if (StringUtils.isEmpty(serviceId) && resourceType == null) {
            throw new BaseException("参数错误");
        }
        List<FlowStep> flowStepList;
        if (ResourceType.DAAS.getCode().equals(resourceType)) {
            flowStepList = flowStepMapper.getUserFlowStepListByResourceType(user, resourceType, stepType);
        } else {
            if (StringUtils.isEmpty(serviceId)) {
                throw new BaseException("参数错误");
            }
            flowStepList = flowStepMapper.getUserFlowStepListByServiceId(user, serviceId, stepType);
        }
        if (flowStepList == null) {
            flowStepList = new ArrayList<>();
        }
        return flowStepList;
    }

    @Override
    public List<FlowStep> getUserAllFlowStepList(User user, Long resourceType) {
        if (resourceType == null) {
            throw new BaseException("参数错误");
        }
        List<FlowStep> flowStepList = flowStepMapper.getUserFlowStepListByResourceType(user, resourceType, null);
        if (flowStepList == null) {
            flowStepList = new ArrayList<>();
        }
        return flowStepList;
    }

    @Override
    public List<FlowStep> getFlowStepList(String serviceId, Long resourceType, String stepType) {
        if (StringUtils.isEmpty(serviceId) && resourceType == null) {
            throw new BaseException("参数错误");
        }
        List<FlowStep> flowStepList;
        if (ResourceType.DAAS.getCode().equals(resourceType)) {
            ApplyFlow applyFlow = applyFlowService.getByResourceType(resourceType, null);
            if (applyFlow == null) {
                throw new BaseException("该服务科信审核流程配置错误，请联系服务管理员");
            }
            flowStepList = this.getListByFlowIdAndType(applyFlow.getId(), stepType);
        } else {
            if (StringUtils.isEmpty(serviceId)) {
                throw new BaseException("参数错误");
            }
            flowStepList = this.getListByFlowIdAndType(serviceId, stepType);
        }
        if (flowStepList == null || flowStepList.isEmpty()) {
            throw new BaseException("该服务科信审核流程配置错误，请联系服务管理员");
        }
        return flowStepList;
    }

    private List<FlowStep> getListByFlowIdAndType(String flowId, String stepType) {
        LambdaQueryWrapper<FlowStep> qw = new QueryWrapper<FlowStep>().lambda()
                .eq(FlowStep::getFlowId, flowId)
                .orderByAsc(FlowStep::getSort)
                .orderByDesc(FlowStep::getModifiedTime);
        if (StringUtils.isNotEmpty(stepType)) {
            qw.eq(FlowStep::getType, stepType);
        }
        return this.list(qw);
    }

}
