package com.upd.hwcloud.service.wfm;

import com.upd.hwcloud.bean.contains.ResourceType;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.wfm.Workflow;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.vo.wfm.WorkFlowVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zwb
 * @since 2019-04-10
 */
public interface IWorkflowService extends IService<Workflow> {
    public Workflow saveFlow(User user, Workflow flow);

    //实例流程设置
    Workflow chooseWorkFlow(ResourceType type,String area,String policeCategory,String serviceId);

    //地区唯一性确认
    boolean distinctAreaCheck(List<String> workFlowIds);

    Map<String,Boolean> checkZysbAndRecover(String idCard);

    Workflow updateFlow(User user, Workflow flow);

    Workflow getDetail(String id,Integer version);

    void deleteFlow(String id);

    WorkFlowVo getFlowVo(String id, Integer version);

    boolean updateFlowBeta(Workflow flow);
}
