package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upd.hwcloud.bean.entity.PaasZhzy;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * PAAS租户资源 Mapper 接口
 * </p>
 *
 * @author xqp
 * @since 2020-06-16
 */
public interface PaasZhzyMapper01 extends BaseMapper<PaasZhzy> {



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
}
