package com.hirisun.cloud.ncov.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.model.ncov.vo.HomePageNcovRealtimeVo;
import com.hirisun.cloud.ncov.NcovApplication;
import com.hirisun.cloud.ncov.bean.NcovRealtime;
import com.hirisun.cloud.ncov.service.NcovRealtimeService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NcovApplication.class)
public class NcovRealTimeTest {

	@Autowired
	private NcovRealtimeService ncovRealtimeService;
	
	
	
	
	private String getData(){
		
		String json = "{date:'2020-06-25',city:["
				+ "{'cure':15,'death':15,'diagnosis':15,'provinceCode':44,'regionName':'广州','regionType':2,'suspected':15},"
				+ "{'cure':1,'death':4,'diagnosis':30,'provinceCode':44,'regionName':'深圳','regionType':2,'suspected':20},"
				+ "{'cure':2,'death':6,'diagnosis':13,'provinceCode':44,'regionName':'珠海','regionType':2,'suspected':21},"
				+ "{'cure':3,'death':5,'diagnosis':31,'provinceCode':44,'regionName':'佛山','regionType':2,'suspected':30},"
				+ "{'cure':4,'death':2,'diagnosis':16,'provinceCode':44,'regionName':'东莞','regionType':2,'suspected':41},"
				+ "{'cure':5,'death':4,'diagnosis':20,'provinceCode':44,'regionName':'中山','regionType':2,'suspected':45},"
				+ "{'cure':6,'death':6,'diagnosis':10,'provinceCode':44,'regionName':'惠州','regionType':2,'suspected':31},"
				+ "{'cure':1,'death':2,'diagnosis':23,'provinceCode':44,'regionName':'汕头','regionType':2,'suspected':22},"
				+ "{'cure':3,'death':1,'diagnosis':0,'provinceCode':44,'regionName':'湛江','regionType':2,'suspected':21},"
				+ "{'cure':5,'death':0,'diagnosis':15,'provinceCode':44,'regionName':'江门','regionType':2,'suspected':30},"
				+ "{'cure':6,'death':12,'diagnosis':41,'provinceCode':44,'regionName':'肇庆','regionType':2,'suspected':89},"
				+ "{'cure':10,'death':34,'diagnosis':32,'provinceCode':44,'regionName':'阳江','regionType':2,'suspected':100},"
				+ "{'cure':12,'death':15,'diagnosis':30,'provinceCode':44,'regionName':'梅州','regionType':2,'suspected':20},"
				+ "{'cure':16,'death':23,'diagnosis':24,'provinceCode':44,'regionName':'茂名','regionType':2,'suspected':40},"
				+ "{'cure':20,'death':18,'diagnosis':18,'provinceCode':44,'regionName':'清远','regionType':2,'suspected':34},"
				+ "{'cure':20,'death':29,'diagnosis':19,'provinceCode':44,'regionName':'揭阳','regionType':2,'suspected':30},"
				+ "{'cure':14,'death':28,'diagnosis':20,'provinceCode':44,'regionName':'韶关','regionType':2,'suspected':25},"
				+ "{'cure':18,'death':0,'diagnosis':10,'provinceCode':44,'regionName':'汕尾','regionType':2,'suspected':89},"
				+ "{'cure':90,'death':14,'diagnosis':29,'provinceCode':44,'regionName':'潮州','regionType':2,'suspected':27},"
				+ "{'cure':1,'death':18,'diagnosis':30,'provinceCode':44,'regionName':'河源','regionType':2,'suspected':35},"
				+ "],province:["
				+ "{'cure':1,'death':1,'diagnosis':1,'provinceCode':11,'regionName':'北京','regionType':1,'suspected':2},"
				+ "{'cure':2,'death':1,'diagnosis':2,'provinceCode':12,'regionName':'天津','regionType':1,'suspected':3},"
				+ "{'cure':3,'death':1,'diagnosis':3,'provinceCode':31,'regionName':'上海','regionType':1,'suspected':4},"
				+ "{'cure':4,'death':2,'diagnosis':4,'provinceCode':50,'regionName':'重庆','regionType':1,'suspected':5},"
				+ "{'cure':5,'death':3,'diagnosis':5,'provinceCode':13,'regionName':'河北','regionType':1,'suspected':6},"
				+ "{'cure':6,'death':4,'diagnosis':6,'provinceCode':41,'regionName':'河南','regionType':1,'suspected':2},"
				+ "{'cure':7,'death':5,'diagnosis':7,'provinceCode':53,'regionName':'云南','regionType':1,'suspected':1},"
				+ "{'cure':8,'death':4,'diagnosis':8,'provinceCode':21,'regionName':'辽宁','regionType':1,'suspected':2},"
				+ "{'cure':9,'death':4,'diagnosis':9,'provinceCode':23,'regionName':'黑龙江','regionType':1,'suspected':2},"
				+ "{'cure':10,'death':3,'diagnosis':10,'provinceCode':43,'regionName':'湖南','regionType':1,'suspected':3},"
				+ "{'cure':11,'death':1,'diagnosis':11,'provinceCode':34,'regionName':'安徽','regionType':1,'suspected':4},"
				+ "{'cure':12,'death':2,'diagnosis':2,'provinceCode':37,'regionName':'山东','regionType':1,'suspected':5},"
				+ "{'cure':13,'death':3,'diagnosis':21,'provinceCode':65,'regionName':'新疆','regionType':1,'suspected':1},"
				+ "{'cure':14,'death':5,'diagnosis':13,'provinceCode':32,'regionName':'江苏','regionType':1,'suspected':1},"
				+ "{'cure':15,'death':6,'diagnosis':4,'provinceCode':33,'regionName':'浙江','regionType':1,'suspected':2},"
				+ "{'cure':16,'death':5,'diagnosis':6,'provinceCode':36,'regionName':'江西','regionType':1,'suspected':5},"
				+ "{'cure':17,'death':2,'diagnosis':13,'provinceCode':42,'regionName':'湖北','regionType':1,'suspected':6},"
				+ "{'cure':18,'death':1,'diagnosis':12,'provinceCode':45,'regionName':'广西','regionType':1,'suspected':2},"
				+ "{'cure':19,'death':5,'diagnosis':12,'provinceCode':62,'regionName':'甘肃','regionType':1,'suspected':1},"
				+ "{'cure':20,'death':6,'diagnosis':17,'provinceCode':14,'regionName':'山西','regionType':1,'suspected':2},"
				+ "{'cure':21,'death':1,'diagnosis':8,'provinceCode':15,'regionName':'内蒙古','regionType':1,'suspected':2},"
				+ "{'cure':22,'death':3,'diagnosis':1,'provinceCode':61,'regionName':'陕西','regionType':1,'suspected':5},"
				+ "{'cure':23,'death':6,'diagnosis':10,'provinceCode':22,'regionName':'吉林','regionType':1,'suspected':7},"
				+ "{'cure':24,'death':2,'diagnosis':2,'provinceCode':35,'regionName':'福建','regionType':1,'suspected':2},"
				+ "{'cure':25,'death':2,'diagnosis':3,'provinceCode':52,'regionName':'贵州','regionType':1,'suspected':2},"
				+ "{'cure':26,'death':1,'diagnosis':6,'provinceCode':44,'regionName':'广东','regionType':1,'suspected':1},"
				+ "{'cure':27,'death':6,'diagnosis':7,'provinceCode':63,'regionName':'青海','regionType':1,'suspected':1},"
				+ "{'cure':28,'death':12,'diagnosis':1,'provinceCode':54,'regionName':'西藏','regionType':1,'suspected':3},"
				+ "{'cure':29,'death':2,'diagnosis':9,'provinceCode':51,'regionName':'四川','regionType':1,'suspected':8},"
				+ "{'cure':30,'death':6,'diagnosis':1,'provinceCode':64,'regionName':'宁夏','regionType':1,'suspected':1},"
				+ "{'cure':31,'death':4,'diagnosis':1,'provinceCode':46,'regionName':'海南','regionType':1,'suspected':2}"
				+ "]}";
		
		
		
		return json;
	}
	
	
	@Test
	public void save() {
		
		JSONObject parseObject = JSONObject.parseObject(getData());
		
		System.out.println(parseObject.toString());
//		ResponseResult responseResult = ncovRealtimeService.importNcovRealtimeData(getData());
//		System.out.println(JSONObject.toJSONString(responseResult));
		
	}
	
	@Test
	public void update() {
		
		String id = "10dabc0498ab2a7c837a0e52561a68d4";
		NcovRealtime ncovRealtime = ncovRealtimeService.getNcovRealtimeById(id );
		System.out.println(JSONObject.toJSONString(ncovRealtime));
		ncovRealtime.setCure(2);
		ncovRealtime.setDeath(2);
		ncovRealtime.setDiagnosis(2);
		ncovRealtime.setSuspected(2);
		ncovRealtimeService.updateNcovRealtimeById(ncovRealtime);
		ncovRealtime = ncovRealtimeService.getNcovRealtimeById(id );
		System.out.println(JSONObject.toJSONString(ncovRealtime));
	}
	
	@Test
	public void delete() {
		
		ResponseResult responseResult = ncovRealtimeService.deleteNcovRealtimeById("59e9d02c08a8dcb6d6472d45b37cc55e");
		System.out.println(JSONObject.toJSONString(responseResult));
	}
	
	@Test
	public void getHomePageNcovRealtimeVo() {
		
		HomePageNcovRealtimeVo homePageNcovRealtimeVo = ncovRealtimeService.getHomePageNcovRealtimeVo();
		System.out.println(JSONObject.toJSONString(homePageNcovRealtimeVo));
	}
	
}
