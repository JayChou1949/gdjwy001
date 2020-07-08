package com.hirisun.cloud.ncov.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hirisun.cloud.api.redis.RedisApi;
import com.hirisun.cloud.model.ncov.contains.NcovKey;
import com.hirisun.cloud.model.ncov.vo.iaas.NcovHomePageIaasVo;
import com.hirisun.cloud.ncov.service.NcovIaasService;
import com.hirisun.cloud.ncov.util.NcovEcsImportUtil;

@Service
public class NcovIaasServiceImpl implements NcovIaasService {

	@Autowired
    private RedisApi redisApi;
	
	@Override
	public NcovHomePageIaasVo getIaasVmData() {
		
		String resString = redisApi.getStrValue(NcovKey.NCOV_IAAS_OVERVIEW);
        if(StringUtils.isNotBlank(resString))return JSON.parseObject(resString,NcovHomePageIaasVo.class);

        NcovHomePageIaasVo overview =  NcovEcsImportUtil.getOverviewData();
        redisApi.setForPerpetual(NcovKey.NCOV_IAAS_OVERVIEW, JSON.toJSONString(overview));
        
        return  overview;
	}
	
	@Override
	public NcovHomePageIaasVo epidemicDesktopNum() throws Exception {
		
		String res = redisApi.getStrValue(NcovKey.EPIC_DESKTOP);
        if(StringUtils.isNotBlank(res))return JSON.parseObject(res,NcovHomePageIaasVo.class);
        
        NcovHomePageIaasVo ncovHomePageIaasVo = epidemicExclNum(epidemicExcl());
        redisApi.setForPerpetual(NcovKey.EPIC_DESKTOP,JSON.toJSONString(ncovHomePageIaasVo));
        return ncovHomePageIaasVo;
		
	}
	
    private List<NcovHomePageIaasVo> epidemicExcl() throws Exception {
        List<List<Object>> list = NcovEcsImportUtil.list("疫情桌面云.xls");
        List<NcovHomePageIaasVo> iaasVoList = new ArrayList<NcovHomePageIaasVo>();
        for (List<Object> itemlist : list) {
        	NcovHomePageIaasVo ncovHomePageIaasVo = new NcovHomePageIaasVo();
        	ncovHomePageIaasVo.setTotal(Integer.valueOf(itemlist.get(1).toString()));
        	ncovHomePageIaasVo.setCpu(Integer.valueOf(itemlist.get(2).toString()));
        	ncovHomePageIaasVo.setMemory(Double.valueOf(itemlist.get(3).toString()));
        	ncovHomePageIaasVo.setStorage(Double.valueOf(itemlist.get(4).toString()));
        	ncovHomePageIaasVo.setSupportPolice(itemlist.get(5) != null?1:0);
        	ncovHomePageIaasVo.setSupportArea(itemlist.get(6) != null?1:0);
        	iaasVoList.add(ncovHomePageIaasVo);
        }
        return iaasVoList;
    }
	
    private NcovHomePageIaasVo epidemicExclNum(List<NcovHomePageIaasVo> epidemicDesktops) throws Exception {
    	
        int yunCount = 0;
        int areaCount = 0;
        int policeCount = 0;
        int cpuCount = 0;
        double ramCount = 0;
        double diskCount = 0;
        
        for (NcovHomePageIaasVo item : epidemicDesktops) {
            yunCount += Integer.valueOf(item.getTotal());
            if (item.getSupportArea() > 0) {
                areaCount++;
            }
            if (item.getSupportPolice() > 0) {
                policeCount++;
            }
            cpuCount += Integer.valueOf(item.getCpu());
            ramCount += item.getMemory();
            diskCount += item.getStorage();
        }
        NcovHomePageIaasVo deskTopNum = new NcovHomePageIaasVo();
        deskTopNum.setTotal(yunCount);
        deskTopNum.setSupportArea(areaCount);
        deskTopNum.setSupportPolice(policeCount);
        deskTopNum.setCpu(cpuCount);
        deskTopNum.setMemory(ramCount);
        deskTopNum.setStorage(diskCount);
        
        return deskTopNum;
    }

	

}
