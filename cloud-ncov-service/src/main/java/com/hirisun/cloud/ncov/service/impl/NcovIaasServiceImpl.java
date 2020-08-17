package com.hirisun.cloud.ncov.service.impl;

import com.alibaba.fastjson.JSON;
import com.hirisun.cloud.model.ncov.contains.NcovFileupload;
import com.hirisun.cloud.model.ncov.contains.NcovKey;
import com.hirisun.cloud.model.ncov.vo.iaas.NcovHomePageIaasVo;
import com.hirisun.cloud.ncov.bean.NcovHomePageData;
import com.hirisun.cloud.ncov.service.NcovHomePageDataService;
import com.hirisun.cloud.ncov.service.NcovIaasService;
import com.hirisun.cloud.redis.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NcovIaasServiceImpl implements NcovIaasService {

	@Autowired
    private RedisService redisService;
	@Autowired
    private NcovHomePageDataService ncovHomePageDataService;
	
	/**
	 * 获取首页IAAS 的虚拟机和桌面云数据,先读取缓存,没有则下载文件刷新缓存
	 */
	public Map<String,NcovHomePageIaasVo> getIaasNcovData() throws Exception {
		
		Map<String,NcovHomePageIaasVo> map = new HashMap<String,NcovHomePageIaasVo>();
		NcovHomePageIaasVo vmIaasVo = null;
		NcovHomePageIaasVo desktopNumIaasVo = null;
		
		String overviewJson = redisService.get(NcovKey.HOME_PAGE_IAAS_NCOV_OVERVIEW);
        if(StringUtils.isNotBlank(overviewJson) && !"null".equals(overviewJson)) {
        	vmIaasVo = JSON.parseObject(overviewJson,NcovHomePageIaasVo.class);
        }else {
        	
        	NcovHomePageData data = ncovHomePageDataService.getNcovHomePageDataByType(NcovFileupload.IAAS_VM);
        	String json = data.getData();
        	if(StringUtils.isNotBlank(json)) {
        		vmIaasVo = JSON.parseObject(json,NcovHomePageIaasVo.class);
        		redisService.setForPerpetual(NcovKey.HOME_PAGE_IAAS_NCOV_OVERVIEW, json);
        	}
        }
        	
        String desktopJson = redisService.get(NcovKey.HOME_PAGE_IAAS_NCOV_DESKTOP);
        
        if(StringUtils.isNotBlank(desktopJson) && !"null".equals(desktopJson)) {
        	desktopNumIaasVo = JSON.parseObject(desktopJson,NcovHomePageIaasVo.class);
        }else {
        	NcovHomePageData data = ncovHomePageDataService.getNcovHomePageDataByType(NcovFileupload.IAAS_DESKTOP);
        	String json = data.getData();
        	if(StringUtils.isNotBlank(json)) {
        		desktopNumIaasVo = JSON.parseObject(json,NcovHomePageIaasVo.class);
        		redisService.setForPerpetual(NcovKey.HOME_PAGE_IAAS_NCOV_DESKTOP,json);
        	}
        }
        
        map.put("vm", vmIaasVo);
        map.put("desktop", desktopNumIaasVo);
        
        return map;
        
	}
	
	

}
