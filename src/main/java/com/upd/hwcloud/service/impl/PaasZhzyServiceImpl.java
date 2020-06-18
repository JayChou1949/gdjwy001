package com.upd.hwcloud.service.impl;

import com.upd.hwcloud.bean.entity.PaasZhzy;
import com.upd.hwcloud.common.utils.BigDecimalUtil;
import com.upd.hwcloud.dao.PaasZhzyMapper;
import com.upd.hwcloud.service.IPaasZhzyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class PaasZhzyServiceImpl extends ServiceImpl<PaasZhzyMapper, PaasZhzy> implements IPaasZhzyService {

    @Autowired
    private PaasZhzyMapper paasZhzyMapper;

    @Override
    public Map<String,PaasZhzy> getPaasZhzy(String area, String police) {
        Map<String,PaasZhzy> paasZhzyMap = new HashMap<>();
        DecimalFormat df = new DecimalFormat("#.00");

        PaasZhzy paasYarn = paasZhzyMapper.getPaasYarn(area, police);
        paasYarn.setMemoryTotal(BigDecimalUtil.div(paasYarn.getMemoryTotal(),1024).doubleValue());
        paasYarn.setMemoryUsed(BigDecimalUtil.div(paasYarn.getMemoryUsed(),1024).doubleValue());

        PaasZhzy paasElasticsearch = paasZhzyMapper.getPaasElasticsearch(area, police);
        paasElasticsearch.setElasticsearchCpuUsed(Double.parseDouble(df.format(paasElasticsearch.getElasticsearchCpuTotal()*paasElasticsearch.getElasticsearchCpuUsage()*0.01)));
        paasElasticsearch.setElasticsearchMemoryTotal(BigDecimalUtil.div(paasElasticsearch.getElasticsearchMemoryTotal(),1024).doubleValue());
        if (paasElasticsearch.getElasticsearchMemoryTotal()!=0) {
            paasElasticsearch.setElasticsearchMemoryUsage(BigDecimalUtil.div(paasElasticsearch.getElasticsearchMemoryUsed(),paasElasticsearch.getElasticsearchMemoryTotal()).doubleValue());
        }
        paasElasticsearch.setElasticsearchDiskAvailable(BigDecimalUtil.div(paasElasticsearch.getElasticsearchDiskAvailable(),1024*1024*1024).doubleValue());
        if (paasElasticsearch.getElasticsearchDiskAvailable()!=0) {
            paasElasticsearch.setElasticsearchDiskUsage(BigDecimalUtil.div(paasElasticsearch.getElasticsearchDiskUsed(),paasElasticsearch.getElasticsearchDiskAvailable()).doubleValue());
        }

        PaasZhzy paasRedis = paasZhzyMapper.getPaasRedis(area, police);
        paasRedis.setRedisMemoryUsed(Double.parseDouble(df.format(paasRedis.getRedisMemoryTotal()*paasRedis.getRedisMemoryUsage()*0.01)));

        PaasZhzy paasLibra = paasZhzyMapper.getPaasLibra(area, police);
        paasLibra.setMemoryTotal(BigDecimalUtil.div(paasLibra.getMemoryTotal(),1024).doubleValue());
        paasLibra.setMemoryUsed(BigDecimalUtil.div(paasLibra.getMemoryUsed(),1024).doubleValue());

        paasZhzyMap.put("YARN资源",paasYarn);
        paasZhzyMap.put("Elasticsearch",paasElasticsearch);
        paasZhzyMap.put("Redis",paasRedis);
        paasZhzyMap.put("Libra集群",paasLibra);
        return paasZhzyMap;
    }

    @Override
    public List<String> appFromAreaOrPolice(String area, String police) {
        return paasZhzyMapper.appFromAreaOrPolice(area,police);
    }

    @Override
    public PaasZhzy appClusterDetails(String appName, String clusterName) {
        PaasZhzy paasZhzy = new PaasZhzy();
        DecimalFormat df = new DecimalFormat("#.00");
        if (StringUtils.equals("YARN",clusterName)) {
            paasZhzy = paasZhzyMapper.appClusterDetailsByTypeSite(appName, "hadoop");
            if (paasZhzy != null) {
                paasZhzy.setMemoryTotal(BigDecimalUtil.div(paasZhzy.getMemoryTotal(),1024).doubleValue());
                paasZhzy.setMemoryUsed(BigDecimalUtil.div(paasZhzy.getMemoryUsed(),1024).doubleValue());
            }
        }else if (StringUtils.equals("Elasticsearch",clusterName)) {
            paasZhzy = paasZhzyMapper.appClusterDetailsByElasticsearch(appName);
            if (paasZhzy.getElasticsearchCpuTotal()!=null) {
                paasZhzy.setElasticsearchCpuUsed(Double.parseDouble(df.format(paasZhzy.getElasticsearchCpuTotal()*paasZhzy.getElasticsearchCpuUsage()*0.01)));
            }
            if (paasZhzy.getElasticsearchMemoryTotal()!=null) {
                paasZhzy.setElasticsearchMemoryTotal(BigDecimalUtil.div(paasZhzy.getElasticsearchMemoryTotal(),1024).doubleValue());
                paasZhzy.setElasticsearchMemoryUsage(BigDecimalUtil.div(paasZhzy.getElasticsearchMemoryUsed(),paasZhzy.getElasticsearchMemoryTotal()).doubleValue());
            }
            if (paasZhzy.getElasticsearchDiskAvailable()!=null) {
                paasZhzy.setElasticsearchDiskAvailable(BigDecimalUtil.div(paasZhzy.getElasticsearchDiskAvailable(),1024*1024*1024).doubleValue());
                paasZhzy.setElasticsearchDiskUsage(BigDecimalUtil.div(paasZhzy.getElasticsearchDiskUsed(),paasZhzy.getElasticsearchDiskAvailable()).doubleValue());
            }
        }else if (StringUtils.equals("Redis",clusterName)) {
            paasZhzy = paasZhzyMapper.appClusterDetailsByRedis(appName);
            if (paasZhzy.getRedisMemoryTotal()!=null) {
                paasZhzy.setRedisMemoryUsed(Double.parseDouble(df.format(paasZhzy.getRedisMemoryTotal()*paasZhzy.getRedisMemoryUsage()*0.01)));
            }
        }else if (StringUtils.equals("Libra",clusterName)) {
            paasZhzy = paasZhzyMapper.appClusterDetailsByTypeSite(appName,clusterName);
            if (paasZhzy != null) {
                paasZhzy.setMemoryTotal(BigDecimalUtil.div(paasZhzy.getMemoryTotal(),1024).doubleValue());
                paasZhzy.setMemoryUsed(BigDecimalUtil.div(paasZhzy.getMemoryUsed(),1024).doubleValue());
            }
        }
        return paasZhzy;
    }

    @Override
    public List<String> clusterTabs(String appName) {
        List<String> clusterList = new ArrayList<>();
        PaasZhzy typeSite = paasZhzyMapper.getClusterByTypeSite(appName);
        PaasZhzy elasticsearch = paasZhzyMapper.getClusterByElasticsearch(appName);
        PaasZhzy redis = paasZhzyMapper.getClusterByRedis(appName);
        if (StringUtils.equals("hadoop",typeSite.getTypeSite())) {
            clusterList.add("YARN");
        }else {
            clusterList.add(typeSite.getTypeSite());
        }
        if (elasticsearch != null) {
            clusterList.add("Elasticsearch");
        }
        if (redis != null) {
            clusterList.add("Redis");
        }
        return clusterList;
    }
}
