package com.upd.hwcloud.service.wfm;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.wfm.Instance;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.response.R;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zwb
 * @since 2019-04-10
 */
public interface IInstanceService extends IService<Instance> {
    /**
     * 发起流程
     * @param createPersonId 创建人
     * @param flowId 流程id
     * @param businessId 业务ID
     * @return
     */
         R launchInstanceOfWorkFlow (String createPersonId,String flowId,String businessId);
    /**
     * 根据业务ID获取流程实例信息
     * @return 实例对象
     */
    public Instance getInstanceByBusinessId(String businessId);

}
