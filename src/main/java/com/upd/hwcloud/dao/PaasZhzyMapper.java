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
}
