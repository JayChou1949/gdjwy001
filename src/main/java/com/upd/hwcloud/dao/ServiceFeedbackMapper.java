package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.entity.Saas;
import com.upd.hwcloud.bean.entity.ServiceFeedback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 服务用户反馈 Mapper 接口
 * </p>
 *
 * @author huru
 * @since 2019-02-15
 */
public interface ServiceFeedbackMapper extends BaseMapper<ServiceFeedback> {

    IPage<ServiceFeedback> getPage(IPage<ServiceFeedback> page, @Param("serviceId") String serviceId);

    void updateReplyNum(String id);
}
