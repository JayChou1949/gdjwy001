package com.hirisun.cloud.order.service.epidemic.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.epidemic.EpidemicApplicationExt;
import com.hirisun.cloud.order.mapper.epidemic.EpidemicApplicationExtMapper;
import com.hirisun.cloud.order.service.epidemic.EpidemicApplicationExtService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * SaaS申请原始信息扩展表 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-03
 */
@Service
public class EpidemicApplicationExtServiceImpl extends ServiceImpl<EpidemicApplicationExtMapper, EpidemicApplicationExt> implements EpidemicApplicationExtService {

    @Override
    public List<EpidemicApplicationExt> getListByMasterId(String id) {

        List<EpidemicApplicationExt> epidemicApplicationExtList = this.list(new QueryWrapper<EpidemicApplicationExt>().lambda()
                .eq(EpidemicApplicationExt::getMasterId,id));
        return epidemicApplicationExtList;
    }


}
