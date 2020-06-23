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
import java.util.*;

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
        if (paasYarn != null) {
            paasYarn.setMemoryTotal(BigDecimalUtil.div(paasYarn.getMemoryTotal(),1024).doubleValue());
            paasYarn.setMemoryUsed(BigDecimalUtil.div(paasYarn.getMemoryUsed(),1024).doubleValue());
        }

        PaasZhzy paasElasticsearch = paasZhzyMapper.getPaasElasticsearch(area, police);
        if (paasElasticsearch != null) {
            paasElasticsearch.setElasticsearchCpuUsed(Double.parseDouble(df.format(paasElasticsearch.getElasticsearchCpuTotal()*paasElasticsearch.getElasticsearchCpuUsage()*0.01)));
            paasElasticsearch.setElasticsearchMemoryTotal(BigDecimalUtil.div(paasElasticsearch.getElasticsearchMemoryTotal(),1024).doubleValue());
        }
        if (paasElasticsearch.getElasticsearchMemoryTotal()!=0) {
            paasElasticsearch.setElasticsearchMemoryUsage(BigDecimalUtil.div(paasElasticsearch.getElasticsearchMemoryUsed(),paasElasticsearch.getElasticsearchMemoryTotal()).doubleValue());
        }
        paasElasticsearch.setElasticsearchDiskAvailable(BigDecimalUtil.div(paasElasticsearch.getElasticsearchDiskAvailable(),1024*1024*1024).doubleValue());
        if (paasElasticsearch.getElasticsearchDiskAvailable()!=0) {
            paasElasticsearch.setElasticsearchDiskUsage(BigDecimalUtil.div(paasElasticsearch.getElasticsearchDiskUsed(),paasElasticsearch.getElasticsearchDiskAvailable()).doubleValue());
        }

        PaasZhzy paasRedis = paasZhzyMapper.getPaasRedis(area, police);
        if (paasRedis != null) {
            paasRedis.setRedisMemoryUsed(Double.parseDouble(df.format(paasRedis.getRedisMemoryTotal()*paasRedis.getRedisMemoryUsage()*0.01)));
        }

        PaasZhzy paasLibra = paasZhzyMapper.getPaasLibra(area, police);
        if (paasLibra != null) {
            paasLibra.setMemoryTotal(BigDecimalUtil.div(paasLibra.getMemoryTotal(),1024).doubleValue());
            paasLibra.setMemoryUsed(BigDecimalUtil.div(paasLibra.getMemoryUsed(),1024).doubleValue());
        }

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

    @Override
    public PaasZhzy paasOverviewByYarn(String area, String police) {
        PaasZhzy paasZhzy = paasZhzyMapper.paasOverviewByYarn(area, police);
        paasZhzy.setMemoryTotal(BigDecimalUtil.div(paasZhzy.getMemoryTotal(),1024).doubleValue());
        if (paasZhzy.getStorageTotal() != null) {
            paasZhzy.setStorageTotal(BigDecimalUtil.div(paasZhzy.getStorageTotal(),1024*1024).doubleValue());
        }
        return paasZhzy;
    }

    @Override
    public List<PaasZhzy> paasOverviewByElasticsearch(String area, String police) {
        return paasZhzyMapper.paasOverviewByElasticsearch(area, police);
    }

    @Override
    public PaasZhzy paasOverviewByRedis(String area, String police) {
        return paasZhzyMapper.paasOverviewByRedis(area, police);
    }

    @Override
    public PaasZhzy paasOverviewByLibra(String area, String police) {
        PaasZhzy paasZhzy = paasZhzyMapper.paasOverviewByLibra(area, police);
        paasZhzy.setMemoryTotal(BigDecimalUtil.div(paasZhzy.getMemoryTotal(),1024).doubleValue());
        if (paasZhzy.getStorageTotal() != null) {
            paasZhzy.setStorageTotal(BigDecimalUtil.div(paasZhzy.getStorageTotal(),1024*1024).doubleValue());
        }
        return paasZhzy;
    }

    @Override
    public Map<String, Double> paasMaxByYarn(String area, String police,String day) {
        Map<String,Double> zhzyMap = new HashMap<>();
        DecimalFormat df = new DecimalFormat("#.00");
        PaasZhzy cpu = paasZhzyMapper.cpuMaxByYarn(area, police, day);
        zhzyMap.put("cpu",cpu.getCpuUsage());
        PaasZhzy memory = paasZhzyMapper.memoryMaxByYarn(area, police, day);
        zhzyMap.put("memory",memory.getMemoryUsage());
        PaasZhzy storage = paasZhzyMapper.storageMaxByYarn(area, police, day);
        if (storage.getStorageTotal() != 0) {
            storage.setStorageUsage(Double.valueOf(df.format(storage.getStorageUsed()/storage.getStorageTotal())));
        }
        zhzyMap.put("storage",storage.getStorageUsage());
        return zhzyMap;
    }

    @Override
    public Map<String, Double> paasMaxByLibra(String area, String police,String day) {
        Map<String,Double> zhzyMap = new HashMap<>();
        DecimalFormat df = new DecimalFormat("#.00");
        PaasZhzy cpu = paasZhzyMapper.cpuMaxByLibra(area, police, day);
        zhzyMap.put("cpu",cpu.getCpuUsage());
        PaasZhzy memory = paasZhzyMapper.memoryMaxByLibra(area, police, day);
        zhzyMap.put("memory",memory.getMemoryUsage());
        PaasZhzy storage = paasZhzyMapper.storageMaxByLibra(area, police, day);
        if (storage.getStorageTotal() != 0) {
            storage.setStorageUsage(Double.valueOf(df.format(storage.getStorageUsed()/storage.getStorageTotal())));
        }
        zhzyMap.put("storage",storage.getStorageUsage());
        return zhzyMap;
    }

    @Override
    public Map<String, Double> paasMaxByElasticsearch(String area, String police, String day,String clusterName) {
        Map<String,Double> zhzyMap = new HashMap<>();
        PaasZhzy cpu = paasZhzyMapper.cpuMaxByElasticsearch(area, police, day,clusterName);
        zhzyMap.put("cpu",cpu.getElasticsearchCpuUsage());
        PaasZhzy memory = paasZhzyMapper.memoryMaxByElasticsearch(area, police, day,clusterName);
        if (memory.getElasticsearchMemoryTotal() != 0) {
            memory.setElasticsearchMemoryUsage(BigDecimalUtil.div(memory.getElasticsearchMemoryUsed(),memory.getElasticsearchMemoryTotal()).doubleValue());
        }
        zhzyMap.put("memory",memory.getElasticsearchMemoryUsage());
        PaasZhzy storage = paasZhzyMapper.storageMaxByElasticsearch(area, police, day,clusterName);
        if (storage.getElasticsearchDiskAvailable() != 0) {
            storage.setElasticsearchDiskUsage(BigDecimalUtil.div(storage.getElasticsearchDiskUsed(),storage.getElasticsearchDiskAvailable()).doubleValue());
        }
        zhzyMap.put("storage",storage.getStorageTotal());
        return zhzyMap;
    }

    @Override
    public Map<String, Double> paasMaxByRedis(String area, String police, String day) {
        Map<String,Double> zhzyMap = new HashMap<>();
        PaasZhzy memory = paasZhzyMapper.memoryMaxByRedis(area, police, day);
        zhzyMap.put("memory",memory.getRedisMemoryUsage());
        return zhzyMap;
    }

    @Override
    public List<PaasZhzy> situationByYarn(String area, String police, String day) {
        DecimalFormat df = new DecimalFormat("#.00");
        List<PaasZhzy> paasZhzyList = paasZhzyMapper.situationByYarn(area, police, day);
        for (PaasZhzy paasZhzy:paasZhzyList) {
            if (paasZhzy != null) {
                paasZhzy.setMemoryUsed(BigDecimalUtil.div(paasZhzy.getMemoryUsed(),1024).doubleValue());
                if (paasZhzy.getStorageTotal() != 0) {
                    paasZhzy.setStorageUsed(BigDecimalUtil.div(paasZhzy.getStorageUsed(),1024*1024).doubleValue());
                    paasZhzy.setStorageUsage(Double.valueOf(df.format(paasZhzy.getStorageUsed()/paasZhzy.getStorageTotal())));
                }
            }
        }
        return paasZhzyList;
    }

    @Override
    public List<PaasZhzy> situationByLibra(String area, String police, String day) {
        DecimalFormat df = new DecimalFormat("#.00");
        List<PaasZhzy> paasZhzyList = paasZhzyMapper.situationByLibra(area, police, day);
        for (PaasZhzy paasZhzy:paasZhzyList) {
            if (paasZhzy != null) {
                paasZhzy.setMemoryUsed(BigDecimalUtil.div(paasZhzy.getMemoryUsed(),1024).doubleValue());
                if (paasZhzy.getStorageTotal() != 0) {
                    paasZhzy.setStorageUsed(BigDecimalUtil.div(paasZhzy.getStorageUsed(),1024*1024).doubleValue());
                    paasZhzy.setStorageUsage(Double.valueOf(df.format(paasZhzy.getStorageUsed()/paasZhzy.getStorageTotal())));
                }
            }
        }
        return paasZhzyList;
    }

    @Override
    public List<PaasZhzy> situationByElasticsearch(String area, String police, String day, String clusterName) {
        List<PaasZhzy> list = new ArrayList<>();

        list = paasZhzyMapper.situationByElasticsearch(area, police, day, clusterName);
        DecimalFormat df = new DecimalFormat("#.00");
        parseEsResource(list, df);
        return list;
    }

    private void parseEsResource(List<PaasZhzy> list, DecimalFormat df) {
        for (PaasZhzy paasZhzy:list) {
            if (paasZhzy.getElasticsearchCpuTotal()!=null) {
                paasZhzy.setElasticsearchCpuUsed(Double.parseDouble(df.format(paasZhzy.getElasticsearchCpuTotal()*paasZhzy.getElasticsearchCpuUsage()*0.01)));
            }
            if (paasZhzy.getElasticsearchMemoryTotal()!=null) {
                paasZhzy.setElasticsearchMemoryTotal(BigDecimalUtil.div(paasZhzy.getElasticsearchMemoryTotal(),1024).doubleValue());
                paasZhzy.setElasticsearchMemoryUsage(BigDecimalUtil.div(paasZhzy.getElasticsearchMemoryUsed(),paasZhzy.getElasticsearchMemoryTotal()).doubleValue());
            }
            if (paasZhzy.getStorageTotal() != 0) {
                paasZhzy.setStorageUsed(BigDecimalUtil.div(paasZhzy.getStorageUsed(),1024*1024).doubleValue());
                paasZhzy.setStorageUsage(Double.valueOf(df.format(paasZhzy.getStorageUsed()/paasZhzy.getStorageTotal())));
            }
            if (paasZhzy.getElasticsearchDiskAvailable()!=null) {
                paasZhzy.setElasticsearchDiskAvailable(BigDecimalUtil.div(paasZhzy.getElasticsearchDiskAvailable(),1024*1024*1024).doubleValue());
                paasZhzy.setElasticsearchDiskUsage(BigDecimalUtil.div(paasZhzy.getElasticsearchDiskUsed(),paasZhzy.getElasticsearchDiskAvailable()).doubleValue());
            }
        }
    }

    @Override
    public List<PaasZhzy> situationByRedis(String area, String police, String day) {
        List<PaasZhzy> list = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.00");
        list = paasZhzyMapper.situationByRedis(area, police, day);
        for (PaasZhzy paasZhzy:list) {
            if (paasZhzy.getRedisMemoryTotal()!=null) {
                paasZhzy.setRedisMemoryUsed(Double.parseDouble(df.format(paasZhzy.getRedisMemoryTotal()*paasZhzy.getRedisMemoryUsage()*0.01)));
            }
        }
        return list;
    }


    @Override
    public List<PaasZhzy>  getPaasYarnResource(String appName, String area, String police,Integer day) {
        return paasZhzyMapper.getPaasYarnResource(appName,area,police,day);
    }

    @Override
    public List<PaasZhzy>  getPaasLibraResource(String appName, String area, String police, Integer day) {
        List<PaasZhzy> paasLibraResource = paasZhzyMapper.getPaasLibraResource(appName, area, police, day);
        DecimalFormat df = new DecimalFormat("#.00");
        for (PaasZhzy paasZhzy : paasLibraResource) {
            if (paasZhzy.getStorageTotal() != 0) {
                paasZhzy.setStorageUsed(BigDecimalUtil.div(paasZhzy.getStorageUsed(),1024*1024).doubleValue());
                paasZhzy.setStorageUsage(Double.valueOf(df.format(paasZhzy.getStorageUsed()/paasZhzy.getStorageTotal())));
            }
        }
        return paasLibraResource;
    }

    @Override
    public List<PaasZhzy>  getPaasEsResource(String appName, String area, String police, Integer day) {
        List<PaasZhzy> list = paasZhzyMapper.getPaasEsResource(appName, area, police, day);
        DecimalFormat df = new DecimalFormat("#.00");
        parseEsResource(list, df);
        return list;
    }

    @Override
    public List<PaasZhzy>  getPaasRedisResource(String appName, String area, String police, Integer day) {
        List<PaasZhzy> paasRedisResource = paasZhzyMapper.getPaasRedisResource(appName, area, police, day);
        DecimalFormat df = new DecimalFormat("#.00");
        for (PaasZhzy paasZhzy : paasRedisResource) {
            if (paasZhzy.getRedisMemoryTotal()!=null) {
                paasZhzy.setRedisMemoryUsed(Double.parseDouble(df.format(paasZhzy.getRedisMemoryTotal()*paasZhzy.getRedisMemoryUsage()*0.01)));
            }
        }
        return paasRedisResource;
    }

    @Override
    public PaasZhzy maxYarnCpu(String appName, String area, String police, Integer day) {
        return paasZhzyMapper.maxYarnCpu(appName,area,police,day);
    }

    @Override
    public PaasZhzy maxYarnMemory(String appName, String area, String police, Integer day) {
        return paasZhzyMapper.maxYarnMemory(appName,area,police,day);
    }

    @Override
    public PaasZhzy totalYarnCpu(String appName, String area, String police, Integer day) {
        PaasZhzy paasZhzy = paasZhzyMapper.totalYarnCpu(appName, area, police, day);
            if (paasZhzy != null&&paasZhzy.getMemoryUsed()!=null) {
                paasZhzy.setMemoryUsed(BigDecimalUtil.div(paasZhzy.getMemoryUsed(),1024).doubleValue());
            }
        return paasZhzy;
    }

    @Override
    public PaasZhzy maxLibraCpu(String appName, String area, String police, Integer day) {
        return paasZhzyMapper.maxLibraCpu(appName,area,police,day);
    }

    @Override
    public PaasZhzy totalLibraMemary(String appName, String area, String police, Integer day) {
        PaasZhzy paasZhzy = paasZhzyMapper.totalLibraMemary(appName, area, police, day);
        if (paasZhzy != null&&paasZhzy.getMemoryUsed()!=null) {
            paasZhzy.setMemoryUsed(BigDecimalUtil.div(paasZhzy.getMemoryUsed(),1024).doubleValue());
            paasZhzy.setStorageTotal(BigDecimalUtil.div(paasZhzy.getStorageTotal(),1024).doubleValue());
        }
        return paasZhzy;
    }

    @Override
    public PaasZhzy maxLibraMemary(String appName, String area, String police, Integer day) {
        return paasZhzyMapper.maxLibraMemary(appName,area,police,day);
    }

    @Override
    public PaasZhzy maxLibraStorage(String appName, String area, String police, Integer day) {
        return paasZhzyMapper.maxLibraStorage(appName,area,police,day);
    }

    @Override
    public PaasZhzy maxEsStorage(String appName, String area, String police, Integer day) {
        return paasZhzyMapper.maxEsStorage(appName,area,police,day);
    }

    @Override
    public  Double maxEsMemary(String appName, String area, String police, Integer day) {
        List<PaasZhzy> maxMemory =paasZhzyMapper.maxEsMemary(appName,area,police,day);
        List<Double> list=new ArrayList();
        //es无内存使用率  需要通过计算获得
        for (PaasZhzy paasZhzy : maxMemory) {
            if (paasZhzy.getElasticsearchMemoryTotal()!=null) {
                paasZhzy.setElasticsearchMemoryTotal(BigDecimalUtil.div(paasZhzy.getElasticsearchMemoryTotal(),1024).doubleValue());
                if(paasZhzy.getElasticsearchMemoryUsed()!=null){
                    paasZhzy.setElasticsearchMemoryUsage(BigDecimalUtil.div(paasZhzy.getElasticsearchMemoryUsed(),paasZhzy.getElasticsearchMemoryTotal()).doubleValue());
                }
            }
            if(paasZhzy.getElasticsearchMemoryUsage()!=null){
                list.add(paasZhzy.getElasticsearchMemoryUsage());
            }
        }

        if(list.size()==0){
            return 0.0;
        }
        Double max= Collections.max(list);
        return max;
    }

    @Override
    public PaasZhzy maxEsCpu(String appName, String area, String police, Integer day) {
        return paasZhzyMapper.maxEsCpu(appName,area,police,day);
    }

    @Override
    public PaasZhzy totalEsMemary(String appName, String area, String police, Integer day) {
        PaasZhzy paasZhzy=  paasZhzyMapper.totalEsMemary(appName,area,police,day);
        if(paasZhzy!=null&&paasZhzy.getElasticsearchMemoryTotal()!=null){
            paasZhzy.setElasticsearchMemoryTotal(BigDecimalUtil.div(paasZhzy.getElasticsearchMemoryTotal(),1024).doubleValue());
            paasZhzy.setStorageTotal(BigDecimalUtil.div(paasZhzy.getStorageTotal(),1024).doubleValue());
        }

        return paasZhzy;
    }

    @Override
    public PaasZhzy totalRedisMemary(String appName, String area, String police, Integer day) {
        PaasZhzy paasZhzy = paasZhzyMapper.totalRedisMemary(appName, area, police, day);
        if(null!=paasZhzy&&null!=paasZhzy.getRedisMemoryTotal()){
            paasZhzy.setRedisMemoryTotal(BigDecimalUtil.div(paasZhzy.getRedisMemoryTotal(),1024).doubleValue());
        }

        return paasZhzy;
    }

    @Override
    public PaasZhzy maxRedisMemary(String appName, String area, String police, Integer day) {
        return paasZhzyMapper.maxRedisMemary(appName,area,police,day);
    }
}
