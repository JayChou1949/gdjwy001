package com.hirisun.cloud.order.mapper.application;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hirisun.cloud.order.bean.application.ApplicationFeedback;

/**
 * 服务器申请反馈 Mapper 接口
 */
public interface ApplicationFeedbackMapper extends BaseMapper<ApplicationFeedback> {

    List<ApplicationFeedback> getListByAppInfoId(@Param("id") String id);

}
