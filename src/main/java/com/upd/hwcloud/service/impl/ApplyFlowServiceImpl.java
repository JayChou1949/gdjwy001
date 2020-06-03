package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.contains.ResourceType;
import com.upd.hwcloud.bean.dto.EditStepDTO;
import com.upd.hwcloud.bean.entity.ApplyFlow;
import com.upd.hwcloud.bean.entity.FlowStep;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.common.exception.BaseException;
import com.upd.hwcloud.dao.ApplyFlowMapper;
import com.upd.hwcloud.service.IApplyFlowService;
import com.upd.hwcloud.service.IFlowStepService;
import com.upd.hwcloud.service.application.IApplicationInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 资源申请流程配置 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2018-11-26
 */
@Service
public class ApplyFlowServiceImpl extends ServiceImpl<ApplyFlowMapper, ApplyFlow> implements IApplyFlowService {

    @Autowired
    private IFlowStepService flowStepService;
    @Autowired
    private ApplyFlowMapper applyFlowMapper;
    @Autowired
    private IApplicationInfoService applicationInfoService;

    @Override
    public ApplyFlow getDetail(String id) {
        ApplyFlow applyFlow = this.getById(id);
        if (applyFlow != null) {
            List<FlowStep> stepList = flowStepService.getListByFlowId(applyFlow.getId(), true);
            applyFlow.setStepList(stepList);
        }
        return applyFlow;
    }

    @Override
    public IPage<ApplyFlow> getPage(IPage<ApplyFlow> page, String name) {
        return applyFlowMapper.getPage(page, name);
    }

    @Override
    public ApplyFlow getByResourceType(Long resourceType, String secretLevel) {
        return applyFlowMapper.getByResourceType(resourceType, secretLevel);
    }

    @Transactional
    @Override
    public ApplyFlow update(User user, ApplyFlow flow) {
        flow.setSecretLevel(null);
        flow.setTargetResource(ResourceType.DAAS.getCode());
        flow.updateById();
        // 关联数据
        flowStepService.updateRef(user, flow.getId(), flow.getStepList());
        // 恢复待审核的
        applicationInfoService.revertStatusByResourceType(ResourceType.DAAS.getCode());
        return flow;
    }

    @Transactional
    @Override
    public void editServiceStep(User user, EditStepDTO editStepDTO) {
        String id = editStepDTO.getId();
        if (StringUtils.isEmpty(id)) {
            throw new BaseException("参数{id}错误!");
        }

        List<FlowStep> stepList = editStepDTO.getStepList();
        // 关联数据
        flowStepService.updateRef(user, id, stepList);
        // 恢复待审核的
        applicationInfoService.revertStatusByServiceId(id);
    }

}
