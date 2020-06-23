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


    List<PaasZhzy> getPaasYarnResource(String appName, String area, String police, Integer day);


    List<PaasZhzy>  getPaasLibraResource( String appName, String area, String police, Integer day);

    List<PaasZhzy>  getPaasEsResource( String appName, String area, String police, Integer day);

    List<PaasZhzy>  getPaasRedisResource(String appName, String area, String police, Integer day);


    /**
     * 查询Yarn  CPU的峰值
     * @param appName
     * @param area
     * @param police
     * @param day
     * @return
     */
    PaasZhzy maxYarnCpu(String appName, String area, String police, Integer day);

    /**
     * yarn  内存峰值
     * @param appName
     * @param area
     * @param police
     * @param day
     * @return
     */
    PaasZhzy maxYarnMemory(String appName, String area, String police, Integer day);

    /**
     * yarn  总cpu
     * @param appName
     * @param area
     * @param police
     * @param day
     * @return
     */
    PaasZhzy totalYarnCpu(String appName, String area, String police, Integer day);

    /**
     * libra  cpu峰值
     * @param appName
     * @param area
     * @param police
     * @param day
     * @return
     */
    PaasZhzy maxLibraCpu(String appName, String area, String police, Integer day);


    /**
     * libra 总内存
     * @param appName
     * @param area
     * @param police
     * @param day
     * @return
     */
    PaasZhzy totalLibraMemary(String appName, String area, String police, Integer day);

    /**
     * libra  内存峰值
     * @param appName
     * @param area
     * @param police
     * @param day
     * @return
     */
    PaasZhzy maxLibraMemary(String appName, String area, String police, Integer day);

    /**
     * libra 存储峰值
     * @param appName
     * @param area
     * @param police
     * @param day
     * @return
     */
    PaasZhzy maxLibraStorage(String appName, String area, String police, Integer day);

    /**
     * es  存储峰值
     * @param appName
     * @param area
     * @param police
     * @param day
     * @return
     */
    PaasZhzy maxEsStorage(String appName, String area, String police, Integer day);

    /**
     * es 内存峰值
     * @param appName
     * @param area
     * @param police
     * @param day
     * @return
     */
    Double maxEsMemary(String appName, String area, String police, Integer day);

    /**
     * es cpu峰值
     * @param appName
     * @param area
     * @param police
     * @param day
     * @return
     */
    PaasZhzy maxEsCpu(String appName, String area, String police, Integer day);

    /**
     * es 内存总数
     * @param appName
     * @param area
     * @param police
     * @param day
     * @return
     */
    PaasZhzy totalEsMemary(String appName, String area, String police, Integer day);

    /**
     * redis 内存总数
     * @param appName
     * @param area
     * @param police
     * @param day
     * @return
     */
    PaasZhzy totalRedisMemary(String appName, String area, String police, Integer day);

    /**
     * redis 内存峰值
     * @param appName
     * @param area
     * @param police
     * @param day
     * @return
     */
    PaasZhzy maxRedisMemary(String appName, String area, String police, Integer day);

    PaasZhzy cpuTotal(String appName, String area, String police, Integer day);

}
