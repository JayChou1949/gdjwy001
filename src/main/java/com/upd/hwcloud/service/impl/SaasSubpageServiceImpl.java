package com.upd.hwcloud.service.impl;

import com.upd.hwcloud.bean.dto.DaasServiceOverview;
import com.upd.hwcloud.bean.dto.InnerServiceOverview;
import com.upd.hwcloud.bean.dto.ServiceOverview;
import com.upd.hwcloud.bean.entity.Dict;
import com.upd.hwcloud.bean.entity.SaasSubpage;
import com.upd.hwcloud.dao.SaasSubpageMapper;
import com.upd.hwcloud.service.IDictService;
import com.upd.hwcloud.service.ISaasSubpageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * SAAS二级页面内容 服务实现类
 * </p>
 *
 * @author huru
 * @since 2019-02-11
 */
@Service
public class SaasSubpageServiceImpl extends BaseSubpageServiceImpl<SaasSubpageMapper, SaasSubpage> implements ISaasSubpageService {

    @Autowired
    private IDictService dictService;

    @Override
    public List<DaasServiceOverview> daas(String name) {
        return baseMapper.daas(name);
    }

    @Override
    public List<ServiceOverview> paas(String name) {
        return baseMapper.paas(name);
    }

    @Override
    public List<InnerServiceOverview> paasOther(String name) {
        List<InnerServiceOverview> serviceOverviews = baseMapper.paasOther(name);
        if (serviceOverviews != null && !serviceOverviews.isEmpty()) {
            List<Dict> paasType = dictService.getChildByValue("paasType");
            if (paasType != null) {
                Map<String, String> map = paasType.stream().collect(Collectors.toMap(Dict::getId, Dict::getName));
                serviceOverviews.forEach(it -> it.setCategory(map.get(it.getSubType())));
            }
        }
        return serviceOverviews;
    }

}
