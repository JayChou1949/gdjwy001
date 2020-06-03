package com.upd.hwcloud.dao;

import com.upd.hwcloud.bean.entity.ServiceFeedbackReply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 服务用户评价回复 Mapper 接口
 * </p>
 *
 * @author zwb
 * @since 2019-06-05
 */
public interface ServiceFeedbackReplyMapper extends BaseMapper<ServiceFeedbackReply> {

    List<ServiceFeedbackReply> getList(String feedbackId);
}
