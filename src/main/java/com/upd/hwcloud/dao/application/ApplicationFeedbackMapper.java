package com.upd.hwcloud.dao.application;

import com.upd.hwcloud.bean.entity.application.ApplicationFeedback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 服务器申请反馈 Mapper 接口
 * </p>
 *
 * @author huru
 * @since 2019-02-22
 */
public interface ApplicationFeedbackMapper extends BaseMapper<ApplicationFeedback> {

    List<ApplicationFeedback> getListByAppInfoId(@Param("id") String id);

}
