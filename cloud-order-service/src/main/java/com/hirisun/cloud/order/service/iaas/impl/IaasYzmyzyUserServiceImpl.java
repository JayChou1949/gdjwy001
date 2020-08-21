package com.hirisun.cloud.order.service.iaas.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.iaas.IaasYzmyzyUser;
import com.hirisun.cloud.order.mapper.iaas.IaasYzmyzyUserMapper;
import com.hirisun.cloud.order.service.iaas.IIaasYzmyzyUserService;

/**
 * 统一用户人员信息 服务实现类
 */
@Service
public class IaasYzmyzyUserServiceImpl extends ServiceImpl<IaasYzmyzyUserMapper, 
	IaasYzmyzyUser> implements IIaasYzmyzyUserService {

    @Override
    public List<IaasYzmyzyUser> getImplServerListByAppInfoId(String appInfoId) {
        return this.list(new QueryWrapper<IaasYzmyzyUser>().lambda()
                .eq(IaasYzmyzyUser::getAppInfoId, appInfoId));
    }

    @Override
    public void update(String appInfoId, List<IaasYzmyzyUser> serverList) {
        this.remove(new QueryWrapper<IaasYzmyzyUser>().lambda().eq(IaasYzmyzyUser::getAppInfoId, appInfoId));
        this.saveBatch(serverList);
    }
}
