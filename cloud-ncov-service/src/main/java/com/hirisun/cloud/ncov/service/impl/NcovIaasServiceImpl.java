package com.hirisun.cloud.ncov.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hirisun.cloud.api.redis.RedisApi;
import com.hirisun.cloud.model.ncov.contains.NcovFileupload;
import com.hirisun.cloud.model.ncov.contains.NcovKey;
import com.hirisun.cloud.model.ncov.vo.iaas.NcovHomePageIaasVo;
import com.hirisun.cloud.ncov.service.NcovFileUploadService;
import com.hirisun.cloud.ncov.service.NcovIaasService;
import com.hirisun.cloud.ncov.util.NcovEcsImportUtil;

@Service
public class NcovIaasServiceImpl implements NcovIaasService {

	@Autowired
    private RedisApi redisApi;
	@Autowired
    private NcovFileUploadService ncovFileUploadService;
	
	/**
	 * 获取首页IAAS 的虚拟机和桌面云数据,先读取缓存,没有则下载文件刷新缓存
	 */
	public Map<String,NcovHomePageIaasVo> getIaasNcovData() throws Exception {
		
		Map<String,NcovHomePageIaasVo> map = new HashMap<String,NcovHomePageIaasVo>();
		NcovHomePageIaasVo vmIaasVo = null;
		NcovHomePageIaasVo desktopNumIaasVo = null;
		Map<String, String> urlMap = null;
		
		String overviewJson = redisApi.getStrValue(NcovKey.HOME_PAGE_IAAS_NCOV_OVERVIEW);
        if(StringUtils.isNotBlank(overviewJson)) {
        	vmIaasVo = JSON.parseObject(overviewJson,NcovHomePageIaasVo.class);
        }else {
        	
        	urlMap = ncovFileUploadService.getFileUriByServiceType(NcovFileupload.NCOV_FILE_TYPE);
        	
        	vmIaasVo =  NcovEcsImportUtil.getOverviewData(urlMap.get(NcovFileupload.IAAS_VM));
            redisApi.setForPerpetual(NcovKey.HOME_PAGE_IAAS_NCOV_OVERVIEW, JSON.toJSONString(vmIaasVo));
        }
        	
        String desktopJson = redisApi.getStrValue(NcovKey.HOME_PAGE_IAAS_NCOV_DESKTOP);
        if(StringUtils.isNotBlank(desktopJson)) {
        	desktopNumIaasVo = JSON.parseObject(desktopJson,NcovHomePageIaasVo.class);
        }else {
        	if(urlMap == null)urlMap = ncovFileUploadService.getFileUriByServiceType(NcovFileupload.NCOV_FILE_TYPE);
        	
        	desktopNumIaasVo = NcovEcsImportUtil.epidemicExcl(urlMap.get(NcovFileupload.IAAS_DESKTOP));
        	if(desktopNumIaasVo != null)
        		redisApi.setForPerpetual(NcovKey.HOME_PAGE_IAAS_NCOV_DESKTOP,JSON.toJSONString(desktopNumIaasVo));
        }
        
        map.put("vm", vmIaasVo);
        map.put("desktop", desktopNumIaasVo);
        
        return map;
        
	}
	
	

}
