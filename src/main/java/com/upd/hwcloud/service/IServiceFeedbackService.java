package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.entity.Saas;
import com.upd.hwcloud.bean.entity.ServiceFeedback;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务用户反馈 服务类
 * </p>
 *
 * @author huru
 * @since 2019-02-15
 */
public interface IServiceFeedbackService extends IService<ServiceFeedback> {

    IPage<ServiceFeedback> getPage(IPage<ServiceFeedback> page, String serviceId);

    void updateReplyNum(String id);
}
