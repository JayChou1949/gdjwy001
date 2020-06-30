package com.upd.hwcloud.service.application.manage.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.entity.application.manage.ApplicationViolation;
import com.upd.hwcloud.dao.application.manage.ApplicationViolationMapper;
import com.upd.hwcloud.service.application.manage.ApplicationViolationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xqp
 * @since 2020-06-30
 */
@Service
public class ApplicationViolationServiceImpl extends ServiceImpl<ApplicationViolationMapper, ApplicationViolation> implements ApplicationViolationService {

    @Autowired
    private ApplicationViolationMapper applicationViolationMapper;

    @Override
    public IPage<ApplicationViolation> getViolationUser(IPage<ApplicationViolation> page, String areaOrPolice, String creatorName, String orgName, String violationTime) {
        return applicationViolationMapper.getViolationUser(page, areaOrPolice, creatorName, orgName, violationTime);
    }
}
