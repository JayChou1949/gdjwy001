package com.hirisun.cloud.ncov.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.ncov.NcovApplication;
import com.hirisun.cloud.ncov.bean.NcovRealtime;
import com.hirisun.cloud.ncov.service.NcovRealtimeService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NcovApplication.class)
public class NcovRealTimeTest {

	@Autowired
	private NcovRealtimeService ncovRealtimeService;
	
	@Test
	public void save() {
		
		NcovRealtime ncovRealtime = new NcovRealtime();
		ncovRealtime.setRegionName("广东");
		ncovRealtime.setCreateUser("");
		ncovRealtime.setCreateDate(new Date());
		ncovRealtime.setCure(1);
		ncovRealtime.setDeath(1);
		ncovRealtime.setDiagnosis(1);
		ncovRealtime.setProvinceCode(44);
		ncovRealtime.setRegionType(1);
		ncovRealtime.setSuspected(1);
		ResponseResult responseResult = ncovRealtimeService.saveNcovRealtime(ncovRealtime );
		System.out.println(JSONObject.toJSONString(responseResult));
		
	}
	
	@Test
	public void update() {
		
	}
	
	@Test
	public void delete() {
		
	}
}
