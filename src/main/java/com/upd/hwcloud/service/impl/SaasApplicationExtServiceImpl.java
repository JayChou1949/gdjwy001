package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.SaasApplicationExt;
import com.upd.hwcloud.dao.SaasApplicationExtMapper;
import com.upd.hwcloud.service.ISaasApplicationExtService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * SaaS申请原始信息扩展表 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2019-07-24
 */
@Service
public class SaasApplicationExtServiceImpl extends ServiceImpl<SaasApplicationExtMapper, SaasApplicationExt> implements ISaasApplicationExtService {

    @Override
    public List<SaasApplicationExt> getListByMasterId(String id) {
        List<SaasApplicationExt> extList = this.list(new QueryWrapper<SaasApplicationExt>().lambda()
                .eq(SaasApplicationExt::getMasterId, id));
        return extList;
    }

}
