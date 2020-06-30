package com.upd.hwcloud.service.application.manage.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.SaasApplication;
import com.upd.hwcloud.bean.entity.SaasApplicationExt;
import com.upd.hwcloud.bean.entity.application.manage.ApplicationManage;
import com.upd.hwcloud.dao.SaasApplicationExtMapper;
import com.upd.hwcloud.dao.SaasApplicationMapper;
import com.upd.hwcloud.dao.application.manage.ApplicationManageMapper;
import com.upd.hwcloud.service.application.manage.IApplicationManageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    private SaasApplicationMapper saasApplicationMapper;

    @Autowired
    private SaasApplicationExtMapper saasApplicationExtMapper;

    @Override
    public Page<ApplicationManage> getApplicationQuota(Page<ApplicationManage> page) {
        return applicationManageMapper.getApplicationQuota(page);
    }

    @Override
    public void updateQuota(String id, Integer quota) {
        ApplicationManage applicationQuota = applicationManageMapper.getApplicationQuotaById(id);
        applicationQuota.setUserQuotaNum(quota);
        applicationQuota.updateById();
        Integer total = applicationQuota.getUserTotal();
        if(quota-total>0){
            applicationQuota.setAvailableQuotaNum(quota-total);
        }
        applicationQuota.updateById();
        //写入更改记录表
    }

    @Override
    public ApplicationManage getApplicationQuotaDetail(String areaOrPolice) {
        return applicationManageMapper.getApplicationQuotaDetail(areaOrPolice);
    }

    @Override
    public IPage<ApplicationManage> getAppManage(IPage<ApplicationManage> page, String areaOrPolice) {
        return applicationManageMapper.getAppManage(page,areaOrPolice);
    }

    @Override
    public IPage<SaasApplication> getUser(IPage<SaasApplication> page, String creatorName, String creator, String orgName, String areaOrPolice) {
        return saasApplicationMapper.getUser(page, creatorName, creator, orgName, areaOrPolice);
    }

    @Override
    public IPage<SaasApplicationExt> getAppDetails(IPage<SaasApplicationExt> page, String creator, String serviceName) {
        IPage<SaasApplicationExt> appOpeningNum = saasApplicationExtMapper.getAppOpeningNum(page, creator, serviceName);
        List<SaasApplicationExt> openingNumRecords = appOpeningNum.getRecords();
        for (SaasApplicationExt opening:openingNumRecords) {
            SaasApplicationExt appRecyclingNum = saasApplicationExtMapper.getAppRecyclingNum(creator, opening.getServiceName());
            if (appRecyclingNum != null) {
                opening.setRecyclingNumber(appRecyclingNum.getRecyclingNumber());
                opening.setRecyclingTime(appRecyclingNum.getRecyclingTime());
            }
        }
        return appOpeningNum;
    }

    @Override
    public void updateAvailableQuota(String id) {
        applicationManageMapper.updateAvailableQuota(id);
    }


}
