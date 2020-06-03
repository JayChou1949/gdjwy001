package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.application.ApplicationFeedback;
import com.upd.hwcloud.dao.application.ApplicationFeedbackMapper;
import com.upd.hwcloud.service.application.IApplicationFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务器申请反馈 服务实现类
 * </p>
 *
 * @author huru
 * @since 2019-02-22
 */
@Service
public class ApplicationFeedbackServiceImpl extends ServiceImpl<ApplicationFeedbackMapper, ApplicationFeedback> implements IApplicationFeedbackService {

    @Autowired
    private ApplicationFeedbackMapper feedbackMapper;

    @Override
    public List<ApplicationFeedback> getListByAppInfoId(String id) {
        return feedbackMapper.getListByAppInfoId(id);
    }

    @Override
    public int getCountByAppInfoId(String id) {
        int count = this.count(new QueryWrapper<ApplicationFeedback>().lambda()
                .eq(ApplicationFeedback::getAppInfoId, id));
        return count;
    }

}
