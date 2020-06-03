package com.upd.hwcloud.service.impl;

import com.upd.hwcloud.bean.entity.ServiceFeedbackReply;
import com.upd.hwcloud.dao.ServiceFeedbackReplyMapper;
import com.upd.hwcloud.service.IServiceFeedbackReplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务用户评价回复 服务实现类
 * </p>
 *
 * @author zwb
 * @since 2019-06-05
 */
@Service
public class ServiceFeedbackReplyServiceImpl extends ServiceImpl<ServiceFeedbackReplyMapper, ServiceFeedbackReply> implements IServiceFeedbackReplyService {

    @Override
    public List<ServiceFeedbackReply> getList(String feedbackId) {
        return baseMapper.getList(feedbackId);
    }
}
