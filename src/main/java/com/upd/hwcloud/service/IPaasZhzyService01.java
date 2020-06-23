package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.PaasZhzy;
import org.apache.ibatis.annotations.Param;

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
public interface IPaasZhzyService01 extends IService<PaasZhzy> {



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
}
