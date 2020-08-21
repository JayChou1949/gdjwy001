package com.hirisun.cloud.iaas.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.iaas.bean.IaasYzmyzyUser;
import com.hirisun.cloud.iaas.bean.IaasYzmyzyUserImpl;
import com.hirisun.cloud.iaas.mapper.IaasYzmyzyUserImplMapper;
import com.hirisun.cloud.iaas.service.IIaasYzmyzyUserImplService;

/**
 * 统一用户人员信息 服务实现类
 */
@Service
public class IaasYzmyzyUserImplServiceImpl extends ServiceImpl<IaasYzmyzyUserImplMapper, 
	IaasYzmyzyUserImpl> implements IIaasYzmyzyUserImplService {

    @Override
    public void saveUserList(List<IaasYzmyzyUser> impls, String appName) {
        List<IaasYzmyzyUserImpl> userImpls = new ArrayList<>();
        for (IaasYzmyzyUser user:impls){
            user.setProjectName(appName);
            IaasYzmyzyUserImpl user1 = new IaasYzmyzyUserImpl(user);
            boolean flag = this.update(user1, new QueryWrapper<IaasYzmyzyUserImpl>().lambda()
                    .eq(IaasYzmyzyUserImpl::getProjectName,appName)
                    .eq(IaasYzmyzyUserImpl::getIdcard,user1.getIdcard())) || this.save(user1);
        }
    }
}
