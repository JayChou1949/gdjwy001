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

    List<String> clusterTabs(String appName);

    PaasZhzy paasOverviewByYarn(String area,String police);

    List<PaasZhzy> paasOverviewByElasticsearch(String area,String police);

    PaasZhzy paasOverviewByRedis(String area,String police);

    PaasZhzy paasOverviewByLibra(String area,String police);

    Map<String,Double> paasMaxByYarn(String area,String police,String day);

    Map<String,Double> paasMaxByLibra(String area,String police,String day);

    Map<String,Double> paasMaxByElasticsearch(String area,String police,String day,String clusterName);

    Map<String,Double> paasMaxByRedis(String area,String police,String day);

    List<PaasZhzy> situationByYarn(String area,String police,String day);

    List<PaasZhzy> situationByLibra(String area,String police,String day);

    List<PaasZhzy> situationByElasticsearch(String area,String police,String day,String clusterName);

    List<PaasZhzy> situationByRedis(String area,String police,String day);
}
