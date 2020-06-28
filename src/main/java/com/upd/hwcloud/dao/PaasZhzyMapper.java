package com.upd.hwcloud.dao;

import com.upd.hwcloud.bean.entity.PaasZhzy;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * PAAS租户资源 Mapper 接口
 * </p>
 *
 * @author xqp
 * @since 2020-06-16
 */
public interface PaasZhzyMapper extends BaseMapper<PaasZhzy> {

    PaasZhzy getPaasLibra(@Param("area") String area, @Param("police") String police);

    List<String> appFromAreaOrPolice(@Param("area") String area, @Param("police") String police);

    PaasZhzy appClusterDetailsByTypeSite(@Param("appName") String appName, @Param("typeSite") String typeSite);

    PaasZhzy appClusterDetailsByElasticsearch(@Param("appName") String appName);

    PaasZhzy appClusterDetailsByRedis(@Param("appName") String appName);

    PaasZhzy getPaasYarn(@Param("area")String area,@Param("police") String police);

    PaasZhzy getPaasElasticsearch(@Param("area")String area, @Param("police")String police);

    PaasZhzy getPaasRedis(@Param("area")String area, @Param("police")String police);

    PaasZhzy getClusterByTypeSite(@Param("appName") String appName);

    PaasZhzy getClusterByElasticsearch(@Param("appName") String appName);

    PaasZhzy getClusterByRedis(@Param("appName") String appName);

    List<PaasZhzy> getTypeSiteByAreaOrPolice(@Param("area") String area,@Param("police") String police);

    List<PaasZhzy> getElasticsearchByAreaOrPolice(@Param("area") String area,@Param("police") String police);

    List<PaasZhzy> getRedisByAreaOrPolice(@Param("area") String area,@Param("police") String police);

    PaasZhzy paasOverviewByYarn(@Param("area") String area,@Param("police") String police);

    List<PaasZhzy> paasOverviewByElasticsearch(@Param("area") String area,@Param("police") String police);

    PaasZhzy paasOverviewByRedis(@Param("area") String area,@Param("police") String police);

    PaasZhzy paasOverviewByLibra(@Param("area") String area,@Param("police") String police);

    PaasZhzy cpuMaxByYarn(@Param("area") String area,@Param("police") String police,@Param("day") String day);

    PaasZhzy memoryMaxByYarn(@Param("area") String area,@Param("police") String police,@Param("day") String day);

    PaasZhzy storageMaxByYarn(@Param("area") String area,@Param("police") String police,@Param("day") String day);

    PaasZhzy cpuMaxByLibra(@Param("area") String area,@Param("police") String police,@Param("day") String day);

    PaasZhzy memoryMaxByLibra(@Param("area") String area,@Param("police") String police,@Param("day") String day);

    PaasZhzy storageMaxByLibra(@Param("area") String area,@Param("police") String police,@Param("day") String day);

    PaasZhzy cpuMaxByElasticsearch(@Param("area") String area,@Param("police") String police,@Param("day") String day,@Param("clusterName") String clusterName);

    PaasZhzy memoryMaxByElasticsearch(@Param("area") String area,@Param("police") String police,@Param("day") String day,@Param("clusterName") String clusterName);

    PaasZhzy storageMaxByElasticsearch(@Param("area") String area,@Param("police") String police,@Param("day") String day,@Param("clusterName") String clusterName);

    PaasZhzy memoryMaxByRedis(@Param("area") String area,@Param("police") String police,@Param("day") String day);

    List<PaasZhzy> situationByYarn(@Param("area") String area,@Param("police") String police,@Param("day") String day);

    List<PaasZhzy> situationByLibra(@Param("area") String area,@Param("police") String police,@Param("day") String day);

    List<PaasZhzy> situationByElasticsearch(@Param("area") String area,@Param("police") String police,@Param("day") String day,@Param("clusterName") String clusterName);

    List<PaasZhzy> situationByRedis(@Param("area") String area,@Param("police") String police,@Param("day") String day);




    /**
     * 查询  Yarn的cpu  内存使用情况
     * @param appName  应用名称
     * @param area     地市名称
     * @param police   警钟
     * @param day   1天   7天  30天
     * @return
     */
    List<PaasZhzy> getPaasYarnResource(@Param("appName") String appName, @Param("area") String area, @Param("police") String police, @Param("day") Integer day);


    List<PaasZhzy>  getPaasLibraResource(@Param("appName") String appName,@Param("area") String area,@Param("police") String police,@Param("day") Integer day);

    List<PaasZhzy>  getPaasEsResource(@Param("appName") String appName,@Param("area") String area,@Param("police") String police,@Param("day") Integer day);

    List<PaasZhzy>  getPaasRedisResource(@Param("appName") String appName,@Param("area") String area,@Param("police") String police,@Param("day") Integer day);


    /**
     * 查询Yarn  CPU的峰值
     * @param appName
     * @param area
     * @param police
     * @param day
     * @return
     */
    PaasZhzy maxYarnCpu(@Param("appName") String appName, @Param("area") String area, @Param("police") String police, @Param("day") Integer day);

    /**
     * yarn  内存峰值
     * @param appName
     * @param area
     * @param police
     * @param day
     * @return
     */
    PaasZhzy maxYarnMemory(@Param("appName") String appName, @Param("area") String area, @Param("police") String police, @Param("day") Integer day);

    /**
     * yarn  总cpu
     * @param appName
     * @param area
     * @param police
     * @param day
     * @return
     */
    PaasZhzy totalYarnCpu(@Param("appName") String appName, @Param("area") String area, @Param("police") String police, @Param("day") Integer day);

    /**
     * libra  cpu峰值
     * @param appName
     * @param area
     * @param police
     * @param day
     * @return
     */
    PaasZhzy maxLibraCpu(@Param("appName") String appName,@Param("area") String area,@Param("police") String police,@Param("day") Integer day);


    /**
     * libra 总内存
     * @param appName
     * @param area
     * @param police
     * @param day
     * @return
     */
    PaasZhzy totalLibraMemary(@Param("appName") String appName,@Param("area") String area,@Param("police") String police,@Param("day") Integer day);

    /**
     * libra  内存峰值
     * @param appName
     * @param area
     * @param police
     * @param day
     * @return
     */
    PaasZhzy maxLibraMemary(@Param("appName") String appName,@Param("area") String area,@Param("police") String police,@Param("day") Integer day);

    /**
     * libra 存储峰值
     * @param appName
     * @param area
     * @param police
     * @param day
     * @return
     */
    PaasZhzy maxLibraStorage(@Param("appName") String appName,@Param("area") String area,@Param("police") String police,@Param("day") Integer day);

    /**
     * es  存储峰值
     * @param appName
     * @param area
     * @param police
     * @param day
     * @return
     */
    PaasZhzy maxEsStorage(@Param("appName") String appName,@Param("area") String area,@Param("police") String police,@Param("day") Integer day);

    /**
     * es 内存峰值
     * @param appName
     * @param area
     * @param police
     * @param day
     * @return
     */
    List<PaasZhzy> maxEsMemary(@Param("appName") String appName,@Param("area") String area,@Param("police") String police,@Param("day") Integer day);

    /**
     * es cpu峰值
     * @param appName
     * @param area
     * @param police
     * @param day
     * @return
     */
    PaasZhzy maxEsCpu(@Param("appName") String appName,@Param("area") String area,@Param("police") String police,@Param("day") Integer day);

    /**
     * es 内存总数
     * @param appName
     * @param area
     * @param police
     * @param day
     * @return
     */
    PaasZhzy totalEsMemary(@Param("appName") String appName,@Param("area") String area,@Param("police") String police,@Param("day") Integer day);

    /**
     * redis 内存总数
     * @param appName
     * @param area
     * @param police
     * @param day
     * @return
     */
    PaasZhzy totalRedisMemary(@Param("appName") String appName,@Param("area") String area,@Param("police") String police,@Param("day") Integer day);

    /**
     * redis 内存峰值
     * @param appName
     * @param area
     * @param police
     * @param day
     * @return
     */
    PaasZhzy maxRedisMemary(@Param("appName") String appName,@Param("area") String area,@Param("police") String police,@Param("day") Integer day);

    PaasZhzy cpuTotal(@Param("appName") String appName,@Param("area") String area,@Param("police") String police,@Param("day") Integer day);
}
