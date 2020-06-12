package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.IaasPoliceOverview;
import com.upd.hwcloud.dao.IaasPoliceOverviewMapper;
import com.upd.hwcloud.service.IIaasPoliceOverviewService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 警种IAAS资源总览 服务实现类
 * </p>
 *
 * @author xqp
 * @since 2020-06-04
 */
@Service
public class IaasPoliceOverviewServiceImpl extends ServiceImpl<IaasPoliceOverviewMapper, IaasPoliceOverview> implements IIaasPoliceOverviewService {

    @Override
    public IaasPoliceOverview getAllByPolice(String police) {
        IaasPoliceOverview iaasPoliceOverview = this.baseMapper.sumAllByPolice(police);
        return iaasPoliceOverview;
    }
}
