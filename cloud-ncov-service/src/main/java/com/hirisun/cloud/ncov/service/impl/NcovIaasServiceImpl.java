package com.hirisun.cloud.ncov.service.impl;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hirisun.cloud.model.ncov.contains.NcovKey;
import com.hirisun.cloud.model.ncov.vo.iaas.NcovEcsOverviewVo;
import com.hirisun.cloud.ncov.service.NcovIaasService;
import com.hirisun.cloud.ncov.util.NcovEcsImportUtil;

@Service
public class NcovIaasServiceImpl implements NcovIaasService {

	@Autowired
    private StringRedisTemplate stringRedisTemplate; 
	
	@Override
	public NcovEcsOverviewVo getOverview() {
		
		NcovEcsOverviewVo overview = null;
		String resString = stringRedisTemplate.opsForValue().get(NcovKey.NCOV_IAAS_OVERVIEW);
        if(StringUtils.isNotBlank(resString)){
        	overview = JSON.parseObject(resString,NcovEcsOverviewVo.class);
            return overview;
        }

        overview =  NcovEcsImportUtil.getOverviewData();
        stringRedisTemplate.opsForValue().set(NcovKey.NCOV_IAAS_OVERVIEW,JSON.toJSONString(overview),5, TimeUnit.HOURS);
        return  overview;
		
	}

	@Override
	public NcovEcsOverviewVo getSupport() {
		
		NcovEcsOverviewVo overview = null;
		String resString = stringRedisTemplate.opsForValue().get(NcovKey.NCOV_IAAS_SUPPORT);
        if(StringUtils.isNotBlank(resString)){
        	overview = JSON.parseObject(resString,NcovEcsOverviewVo.class);
            return overview;
        }
		
		overview =  NcovEcsImportUtil.getOverviewData();
        stringRedisTemplate.opsForValue().set(NcovKey.NCOV_IAAS_SUPPORT,JSON.toJSONString(overview),5, TimeUnit.HOURS);
        
        return overview;
		
	}

}
