package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.entity.IaasOverview;
import com.upd.hwcloud.dao.IaasOverviewMapper;
import com.upd.hwcloud.service.IIaasOverviewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhangwy
 * @since 2019-09-09
 */
@Service
public class IaasOverviewServiceImpl extends ServiceImpl<IaasOverviewMapper, IaasOverview> implements IIaasOverviewService {

    @Override
    public IaasOverview getAll(String area) {
        IaasOverview all = this.baseMapper.sumAll(area);
        return all;
    }
}
