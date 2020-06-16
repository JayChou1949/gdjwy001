package com.upd.hwcloud.service;

import com.upd.hwcloud.bean.entity.PaasZhzy;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * PAAS租户资源 服务类
 * </p>
 *
 * @author xqp
 * @since 2020-06-16
 */
public interface IPaasZhzyService extends IService<PaasZhzy> {

    Map<String,PaasZhzy> getPaasZhzy(String area, String police);

    List<String> appFromAreaOrPolice(String area, String police);

    PaasZhzy appClusterDetails(String appName, String clusterName);
}
