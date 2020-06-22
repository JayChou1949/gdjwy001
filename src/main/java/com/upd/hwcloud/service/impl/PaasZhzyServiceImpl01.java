package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.PaasZhzy;
import com.upd.hwcloud.common.utils.BigDecimalUtil;
import com.upd.hwcloud.dao.PaasZhzyMapper;
import com.upd.hwcloud.dao.PaasZhzyMapper01;
import com.upd.hwcloud.service.IPaasZhzyService;
import com.upd.hwcloud.service.IPaasZhzyService01;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * PAAS租户资源 服务实现类
 * </p>
 *
 * @author xqp
 * @since 2020-06-16
 */
@Service
public class PaasZhzyServiceImpl01 extends ServiceImpl<PaasZhzyMapper01, PaasZhzy> implements IPaasZhzyService01 {


    @Autowired
    private PaasZhzyMapper01 paasZhzyMapper01;



    @Override
    public PaasZhzy getPaasYarnResource(String appName, String area, String police,Integer day) {
        return paasZhzyMapper01.getPaasYarnResource(appName,area,police,day);
    }

    @Override
    public PaasZhzy getPaasLibraResource(String appName, String area, String police, Integer day) {
        return paasZhzyMapper01.getPaasLibraResource(appName,area,police,day);
    }

    @Override
    public PaasZhzy getPaasEsResource(String appName, String area, String police, Integer day) {
        return paasZhzyMapper01.getPaasEsResource(appName,area,police,day);
    }

    @Override
    public PaasZhzy getPaasRedisResource(String appName, String area, String police, Integer day) {
        return paasZhzyMapper01.getPaasRedisResource(appName,area,police,day);
    }

    @Override
    public int maxYarnCpu(String appName, String area, String police, Integer day) {
        return paasZhzyMapper01.maxYarnCpu(appName,area,police,day);
    }

    @Override
    public int maxYarnMemory(String appName, String area, String police, Integer day) {
        return paasZhzyMapper01.maxYarnMemory(appName,area,police,day);
    }

    @Override
    public int totalYarnCpu(String appName, String area, String police, Integer day) {
        return paasZhzyMapper01.totalYarnCpu(appName,area,police,day);
    }

    @Override
    public int maxLibraCpu(String appName, String area, String police, Integer day) {
        return paasZhzyMapper01.maxLibraCpu(appName,area,police,day);
    }

    @Override
    public int totalLibraMemary(String appName, String area, String police, Integer day) {
        return paasZhzyMapper01.totalLibraMemary(appName,area,police,day);
    }

    @Override
    public int maxLibraMemary(String appName, String area, String police, Integer day) {
        return paasZhzyMapper01.maxLibraMemary(appName,area,police,day);
    }

    @Override
    public int maxLibraStorage(String appName, String area, String police, Integer day) {
        return paasZhzyMapper01.maxLibraStorage(appName,area,police,day);
    }

    @Override
    public int maxEsStorage(String appName, String area, String police, Integer day) {
        return paasZhzyMapper01.maxEsStorage(appName,area,police,day);
    }

    @Override
    public int maxEsMemary(String appName, String area, String police, Integer day) {
        return paasZhzyMapper01.maxEsMemary(appName,area,police,day);
    }

    @Override
    public int maxEsCpu(String appName, String area, String police, Integer day) {
        return paasZhzyMapper01.maxEsMemary(appName,area,police,day);
    }

    @Override
    public int totalEsMemary(String appName, String area, String police, Integer day) {
        return paasZhzyMapper01.totalEsMemary(appName,area,police,day);
    }

    @Override
    public int totalRedisMemary(String appName, String area, String police, Integer day) {
        return paasZhzyMapper01.totalRedisMemary(appName,area,police,day);
    }

    @Override
    public int maxRedisMemary(String appName, String area, String police, Integer day) {
        return paasZhzyMapper01.maxRedisMemary(appName,area,police,day);
    }
}
