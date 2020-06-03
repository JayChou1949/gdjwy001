package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.entity.application.IaasYzmyzyUser;
import com.upd.hwcloud.bean.entity.application.IaasYzmyzyUserImpl;
import com.upd.hwcloud.dao.application.IaasYzmyzyUserImplMapper;
import com.upd.hwcloud.service.application.IIaasYzmyzyUserImplService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 统一用户人员信息 服务实现类
 * </p>
 *
 * @author zwb
 * @since 2019-05-09
 */
@Service
public class IaasYzmyzyUserImplServiceImpl extends ServiceImpl<IaasYzmyzyUserImplMapper, IaasYzmyzyUserImpl> implements IIaasYzmyzyUserImplService {

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
