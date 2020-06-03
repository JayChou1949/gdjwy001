package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.dto.EditStepDTO;
import com.upd.hwcloud.bean.entity.ApplyFlow;
import com.upd.hwcloud.bean.entity.User;

import java.util.Map;

/**
 * <p>
 * 资源申请流程配置 服务类
 * </p>
 *
 * @author wuc
 * @since 2018-11-26
 */
public interface IApplyFlowService extends IService<ApplyFlow> {

    ApplyFlow getDetail(String id);

    IPage<ApplyFlow> getPage(IPage<ApplyFlow> page, String name);


    /**
     * 通过资源类型 id 和密级查询流程
     */
    ApplyFlow getByResourceType(Long resourceType, String secretLevel);

    ApplyFlow update(User user, ApplyFlow flow);

    void editServiceStep(User user, EditStepDTO editStepDTO);

}
