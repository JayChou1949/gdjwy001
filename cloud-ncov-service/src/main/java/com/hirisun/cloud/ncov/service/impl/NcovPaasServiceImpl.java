package com.hirisun.cloud.ncov.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hirisun.cloud.api.redis.RedisApi;
import com.hirisun.cloud.common.util.MemoryPageUtil;
import com.hirisun.cloud.model.ncov.contains.NcovKey;
import com.hirisun.cloud.model.ncov.vo.paas.NcovClusterApp;
import com.hirisun.cloud.model.ncov.vo.paas.NcovClusterAppVo;
import com.hirisun.cloud.model.ncov.vo.paas.NcovClusterOverviewVo;
import com.hirisun.cloud.model.ncov.vo.paas.NcovClusterResourceVo;
import com.hirisun.cloud.ncov.service.NcovPaasService;
import com.hirisun.cloud.ncov.util.NcovClusterImportUtil;

@Service
public class NcovPaasServiceImpl implements NcovPaasService {

	@Autowired
    private RedisApi redisApi;
	
	@Override
	public NcovClusterOverviewVo getOverview() {
		
		NcovClusterOverviewVo overview = null;
		String  res = redisApi.getStrValue(NcovKey.HOMG_PAGE_PAAS_OVERVIEW);
        if(StringUtils.isNotBlank(res)){
        	overview = JSON.parseObject(res,NcovClusterOverviewVo.class);
            if(overview != null)return  overview;
        }
        overview = NcovClusterImportUtil.getOverview();
        redisApi.setForPerpetual(NcovKey.HOMG_PAGE_PAAS_OVERVIEW, JSON.toJSONString(overview));
		
		return overview;
	}

	@Override
	public List<NcovClusterResourceVo> getResource() {
		
		List<NcovClusterResourceVo> resourceList = null;
		String  res = redisApi.getStrValue(NcovKey.HOMG_PAGE_PAAS_RESOURCE);
        if(StringUtils.isNotBlank(res)){
        	resourceList = JSON.parseArray(res, NcovClusterResourceVo.class);
            if(CollectionUtils.isNotEmpty(resourceList))return  resourceList;
        }
		resourceList = NcovClusterImportUtil.getResourceList();
		redisApi.setForPerpetual(NcovKey.HOMG_PAGE_PAAS_RESOURCE, JSON.toJSONString(resourceList));
		return resourceList;
	}

	@Override
	public Page<NcovClusterAppVo> getAppDetailList(long pageSize,long current) {
		
		List<NcovClusterApp> appList = null;
		String  res = redisApi.getStrValue(NcovKey.HOMG_PAGE_PAAS_APPDETAIL);
        if(StringUtils.isNotBlank(res)){
        	appList = JSON.parseArray(res, NcovClusterApp.class);
            if(CollectionUtils.isNotEmpty(appList)) {
            	return MemoryPageUtil.page(convert2Vo(appList), pageSize, current);
            }
        }
		
		appList = NcovClusterImportUtil.getAppDetailList();
		redisApi.setForPerpetual(NcovKey.HOMG_PAGE_PAAS_APPDETAIL, JSON.toJSONString(appList));
		Page<NcovClusterAppVo> page = MemoryPageUtil.page(convert2Vo(appList), pageSize, current);
		return page;
	}
	
	/**
     * 转换为VO（处理最近7天时间轴坐标）
     * @param appList
     * @return
     */
    private List<NcovClusterAppVo> convert2Vo(List<NcovClusterApp> appList){
        List<NcovClusterAppVo> voList = Lists.newArrayList();
        LocalDate now = LocalDate.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM-dd");

        appList.forEach(app->{
            NcovClusterAppVo vo = new NcovClusterAppVo();
            BeanUtils.copyProperties(app,vo);
			//转换CPU最近7天数据结构
			LinkedHashMap<String,Double> cpuRecent = Maps.newLinkedHashMap();
			cpuRecent.put(now.minus(7, ChronoUnit.DAYS).format(fmt),app.getCpuDay1());
			cpuRecent.put(now.minus(6, ChronoUnit.DAYS).format(fmt),app.getCpuDay2());
			cpuRecent.put(now.minus(5, ChronoUnit.DAYS).format(fmt),app.getCpuDay3());
			cpuRecent.put(now.minus(4, ChronoUnit.DAYS).format(fmt),app.getCpuDay4());
			cpuRecent.put(now.minus(3, ChronoUnit.DAYS).format(fmt),app.getCpuDay5());
			cpuRecent.put(now.minus(2, ChronoUnit.DAYS).format(fmt),app.getCpuDay6());
			cpuRecent.put(now.minus(1, ChronoUnit.DAYS).format(fmt),app.getCpuDay7());
			vo.setCpuRecent(cpuRecent);

			//处理内存最近7天数据结构
			LinkedHashMap<String,Double> memoryRecent = Maps.newLinkedHashMap();
			memoryRecent.put(now.minus(7, ChronoUnit.DAYS).format(fmt),app.getMemDay1());
			memoryRecent.put(now.minus(6, ChronoUnit.DAYS).format(fmt),app.getMemDay2());
			memoryRecent.put(now.minus(5, ChronoUnit.DAYS).format(fmt),app.getMemDay3());
			memoryRecent.put(now.minus(4, ChronoUnit.DAYS).format(fmt),app.getMemDay4());
			memoryRecent.put(now.minus(3, ChronoUnit.DAYS).format(fmt),app.getMemDay5());
			memoryRecent.put(now.minus(2, ChronoUnit.DAYS).format(fmt),app.getMemDay6());
			memoryRecent.put(now.minus(1, ChronoUnit.DAYS).format(fmt),app.getMemDay7());
			vo.setMemRecent(memoryRecent);
			voList.add(vo);
        });
        return voList;

    }

}
