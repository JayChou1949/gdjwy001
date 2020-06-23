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

    PaasZhzy paasOverviewByYarn(@Param("area") String area,@Param("police") String police);

    List<PaasZhzy> paasOverviewByElasticsearch(@Param("area") String area,@Param("police") String police);

    PaasZhzy paasOverviewByRedis(@Param("area") String area,@Param("police") String police);

    PaasZhzy paasOverviewByLibra(@Param("area") String area,@Param("police") String police);

    PaasZhzy cpuMaxByYarn(@Param("area") String area,@Param("police") String police,@Param("day") String day);

    PaasZhzy memoryMaxByYarn(@Param("area") String area,@Param("police") String police,@Param("day") String day);

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
}
