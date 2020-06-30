package com.hirisun.cloud.third.service.impl;

import com.hirisun.cloud.third.bean.ThreePartyInterface;
import com.hirisun.cloud.third.mapper.ThreePartyInterfaceMapper;
import com.hirisun.cloud.third.service.ThreePartyInterfaceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 第三方接口表 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-06-28
 */
@Service
public class ThreePartyInterfaceServiceImpl extends ServiceImpl<ThreePartyInterfaceMapper, ThreePartyInterface> implements ThreePartyInterfaceService {

    @Autowired
    private ThreePartyInterfaceMapper threePartyInterfaceMapper;

    @Override
    public List<ThreePartyInterface> getByParams(ThreePartyInterface threePartyInterface) {
        return threePartyInterfaceMapper.getByParams(threePartyInterface);
    }
}
