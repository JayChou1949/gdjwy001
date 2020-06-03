package com.upd.hwcloud.service;

import com.upd.hwcloud.bean.dto.DaasServiceOverview;
import com.upd.hwcloud.bean.dto.InnerServiceOverview;
import com.upd.hwcloud.bean.dto.ServiceOverview;
import com.upd.hwcloud.bean.entity.SaasSubpage;

import java.util.List;

/**
 * <p>
 * SAAS二级页面内容 服务类
 * </p>
 *
 * @author huru
 * @since 2019-02-11
 */
public interface ISaasSubpageService extends IBaseSubpageService<SaasSubpage> {

    List<DaasServiceOverview> daas(String name);

    List<ServiceOverview> paas(String name);

    List<InnerServiceOverview> paasOther(String name);

}
