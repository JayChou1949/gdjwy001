package com.upd.hwcloud.service.application.manage.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.application.manage.ApplicationRecords;
import com.upd.hwcloud.dao.application.manage.ApplicationRecordsMapper;
import com.upd.hwcloud.service.application.manage.IApplicationRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lqm
 * @since 2020-06-29
 */
@Service
public class ApplicationRecordsServiceImpl extends ServiceImpl<ApplicationRecordsMapper, ApplicationRecords> implements IApplicationRecordsService {

    @Autowired
    private ApplicationRecordsMapper applicationRecordsMapper;

    @Override
    public void addQuotaRecord(ApplicationRecords applicationRecords) {
        applicationRecords.insert();
    }

    @Override
    public Page<ApplicationRecords> getApplicationRecords(Page<ApplicationRecords>  page,String id) {
        return applicationRecordsMapper.getApplicationRecords(page,id);
    }
}
