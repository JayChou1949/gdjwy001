package com.upd.hwcloud.service.application.manage.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.application.manage.ApplicationManage;
import com.upd.hwcloud.dao.application.manage.ApplicationManageMapper;
import com.upd.hwcloud.service.application.manage.IApplicationManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 应用运营管理 服务实现类
 * </p>
 *
 * @author lqm
 * @since 2020-06-29
 */
@Service
public class ApplicationManageServiceImpl extends ServiceImpl<ApplicationManageMapper, ApplicationManage> implements IApplicationManageService {
    @Autowired
    private ApplicationManageMapper applicationManageMapper;

    @Override
    public Page<ApplicationManage> getApplicationQuota(Page<ApplicationManage> page) {
        return applicationManageMapper.getApplicationQuota(page);
    }

    @Override
    public void updateQuota(String id, String quota) {
        applicationManageMapper.updateQuota(id,quota);
        //写入更改记录表
    }

    @Override
    public ApplicationManage getApplicationQuotaDetail(String areaOrPolice) {
        return applicationManageMapper.getApplicationQuotaDetail(areaOrPolice);
    }
}
