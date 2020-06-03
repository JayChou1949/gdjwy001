package com.upd.hwcloud.service;

import com.upd.hwcloud.bean.entity.ServiceFeedbackReply;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务用户评价回复 服务类
 * </p>
 *
 * @author zwb
 * @since 2019-06-05
 */
public interface IServiceFeedbackReplyService extends IService<ServiceFeedbackReply> {
    List<ServiceFeedbackReply> getList(String feedbackId);
}
