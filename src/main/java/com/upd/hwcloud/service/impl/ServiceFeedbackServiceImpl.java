package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.entity.Saas;
import com.upd.hwcloud.bean.entity.ServiceFeedback;
import com.upd.hwcloud.dao.ServiceFeedbackMapper;
import com.upd.hwcloud.service.IServiceFeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务用户反馈 服务实现类
 * </p>
 *
 * @author huru
 * @since 2019-02-15
 */
@Service
public class ServiceFeedbackServiceImpl extends ServiceImpl<ServiceFeedbackMapper, ServiceFeedback> implements IServiceFeedbackService {

    @Autowired
    private ServiceFeedbackMapper serviceFeedbackMapper;

    @Override
    public IPage<ServiceFeedback> getPage(IPage<ServiceFeedback> page, String serviceId) {
        return serviceFeedbackMapper.getPage(page, serviceId);
    }

    @Override
    public void updateReplyNum(String id) {
        serviceFeedbackMapper.updateReplyNum(id);
    }

}
