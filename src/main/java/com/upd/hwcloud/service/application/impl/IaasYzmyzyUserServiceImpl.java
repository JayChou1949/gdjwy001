package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.entity.application.IaasYzmyzyUser;
import com.upd.hwcloud.dao.application.IaasYzmyzyUserMapper;
import com.upd.hwcloud.service.application.IIaasYzmyzyUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class IaasYzmyzyUserServiceImpl extends ServiceImpl<IaasYzmyzyUserMapper, IaasYzmyzyUser> implements IIaasYzmyzyUserService {

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
