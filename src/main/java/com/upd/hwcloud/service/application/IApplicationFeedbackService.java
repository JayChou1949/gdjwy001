package com.upd.hwcloud.service.application;

import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.application.ApplicationFeedback;

import java.util.List;

/**
 * <p>
 * 服务器申请反馈 服务类
 * </p>
 *
 * @author huru
 * @since 2019-02-22
 */
public interface IApplicationFeedbackService extends IService<ApplicationFeedback> {

    List<ApplicationFeedback> getListByAppInfoId(String id);

    int getCountByAppInfoId(String id);

}
