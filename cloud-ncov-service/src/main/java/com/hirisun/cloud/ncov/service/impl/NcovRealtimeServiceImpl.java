package com.hirisun.cloud.ncov.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hirisun.cloud.api.redis.RedisApi;
import com.hirisun.cloud.common.exception.CustomException;
import com.hirisun.cloud.common.vo.CommonCode;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.model.ncov.contains.NcovKey;
import com.hirisun.cloud.model.ncov.vo.realtime.HomePageNcovRealtimeVo;
import com.hirisun.cloud.model.ncov.vo.realtime.NcovRealtimeVo;
import com.hirisun.cloud.ncov.bean.NcovRealtime;
import com.hirisun.cloud.ncov.mapper.NcovRealtimeMapper;
import com.hirisun.cloud.ncov.service.NcovRealtimeService;

@Service
public class NcovRealtimeServiceImpl implements NcovRealtimeService {

	@Autowired
	private NcovRealtimeMapper ncovRealtimeMapper;
	@Autowired
    private RedisApi redisApi;
	
	@Override
	public HomePageNcovRealtimeVo getHomePageNcovRealtimeVo() {
		
		String resString = redisApi.getStrValue(NcovKey.HOMG_PAGE_NCOV_REALTIME);
		if(StringUtils.isNotBlank(resString))return JSON.parseObject(resString,HomePageNcovRealtimeVo.class);
		
		HomePageNcovRealtimeVo vo = getHomePageNcovRealtime();
		redisApi.setForPerpetual(NcovKey.HOMG_PAGE_NCOV_REALTIME, JSON.toJSONString(vo));
		return vo;
	}

	private HomePageNcovRealtimeVo getHomePageNcovRealtime() {
		//统计全国数据
		List<NcovRealtimeVo> toDayCounturyList = ncovRealtimeMapper.findNcovRealtimeList(1, 1);
		NcovRealtimeVo yesterdayCountury = ncovRealtimeMapper.countNcovRealTime(1, 2);
		
		//统计所有城市
		List<NcovRealtimeVo> toDayCityList = ncovRealtimeMapper.findNcovRealtimeList(2, 1);
		NcovRealtimeVo yesterdayCity = ncovRealtimeMapper.countNcovRealTime(2, 2);
		
		HomePageNcovRealtimeVo vo = new HomePageNcovRealtimeVo();
		
		NcovRealtimeVo toDayCountury = extracted(toDayCounturyList, yesterdayCountury);
		NcovRealtimeVo toDayCity = extracted(toDayCityList, yesterdayCity);
		
		
		toDayCounturyList.forEach(ncovRealtimeVo->{
			
			ncovRealtimeVo.setDeath(0);
			ncovRealtimeVo.setCure(0);
			ncovRealtimeVo.setSuspected(0);
			
			if(ncovRealtimeVo.getProvinceCode() == 44) {
				int diagnosis = toDayCityList.stream().mapToInt(NcovRealtimeVo::getDiagnosis).sum();
				ncovRealtimeVo.setDiagnosis(diagnosis);	
			}
		});
		
		//当天全国各省确诊、疑似、治愈、死亡总计
		vo.setToDayCountryTotal(toDayCountury);
		
		//当天全国各省确诊、疑似、治愈、死亡较昨日增长
		vo.setYesterdayCountryTotal(yesterdayCountury);
		
		//广东确诊、治愈、死亡总计
		vo.setToDayCityTotal(toDayCity);
		
		//广东确诊、治愈、死亡较昨日增长
		vo.setYesterdayCityTotal(yesterdayCity);
		
		//当天全国各省确诊、疑似、治愈、死亡地图数据
		vo.setProvinceList(toDayCounturyList);
		
		//广东列表数据
		vo.setCityList(toDayCityList);
		return vo;
	}

	private NcovRealtimeVo extracted(List<NcovRealtimeVo> toDayCountury, NcovRealtimeVo yesterdayCountury) {
		
		NcovRealtimeVo toDay = new NcovRealtimeVo();
		
		toDayCountury.forEach(ncovRealtime->{
			toDay.setCure(toDay.getCure() + ncovRealtime.getCure());
			toDay.setDeath(toDay.getDeath() + ncovRealtime.getDeath());
			toDay.setDiagnosis(toDay.getDiagnosis() + ncovRealtime.getDiagnosis());
			toDay.setSuspected(toDay.getSuspected() + ncovRealtime.getSuspected());
			
		});
		
		yesterdayCountury.setCure(toDay.getCure() - yesterdayCountury.getCure());
		yesterdayCountury.setDeath(toDay.getDeath() - yesterdayCountury.getDeath());
		yesterdayCountury.setDiagnosis(toDay.getDiagnosis() - yesterdayCountury.getDiagnosis());
		yesterdayCountury.setSuspected(toDay.getSuspected() - yesterdayCountury.getSuspected());
		return toDay;
	}

	@Transactional
	public ResponseResult importNcovRealtimeData(String json)throws CustomException {
		
		ResponseResult result = new ResponseResult(CommonCode.SUCCESS.code(),CommonCode.SUCCESS.msg());
		JSONObject jo = JSONObject.parseObject(json);
		String date = jo.get("date").toString();
		List<NcovRealtime> cityList = JSONArray.parseArray(jo.get("city").toString(), NcovRealtime.class);
		List<NcovRealtime> provinceList = JSONArray.parseArray(jo.get("province").toString(), NcovRealtime.class);
		
		if(StringUtils.isBlank(date) 
				|| CollectionUtils.isEmpty(cityList) 
				|| CollectionUtils.isEmpty(provinceList)) {
			
			throw new CustomException(CommonCode.INVALID_PARAM);
			
		}
		
		// 先删除需要导入那天的数据 
		Map<String, Object> columnMap = new HashMap<String, Object>();
		columnMap.put("CREATE_DATE", date);
		ncovRealtimeMapper.deleteByMap(columnMap);

		cityList.forEach(cityNcovRealtime -> {
			cityNcovRealtime.setCreateDate(date);
			ncovRealtimeMapper.insert(cityNcovRealtime);
		});
			
		provinceList.forEach(provinceNcovRealtime->{
			provinceNcovRealtime.setCreateDate(date);
			ncovRealtimeMapper.insert(provinceNcovRealtime);
		});
		
		redisApi.delete(NcovKey.HOMG_PAGE_NCOV_REALTIME);
		
		return result;
	}


	@Transactional
	public ResponseResult updateNcovRealtimeById(NcovRealtime ncovRealtime) {
		
		int update = ncovRealtimeMapper.updateById(ncovRealtime);
		
		ResponseResult result = new ResponseResult(CommonCode.SUCCESS.code(),CommonCode.SUCCESS.msg());
		
		if(update ==0) {
			result.setCode(CommonCode.UPDATE_FAIL.code());
			result.setMsg(CommonCode.UPDATE_FAIL.msg());
		}
		
		return result;
	}

	@Transactional
	public ResponseResult deleteNcovRealtimeById(String id) {
		
		int delete = ncovRealtimeMapper.deleteById(id);
		
		ResponseResult result = new ResponseResult(CommonCode.SUCCESS.code(),CommonCode.SUCCESS.msg());
		
		if(delete ==0) {
			result.setCode(CommonCode.DELETE_FAIL.code());
			result.setMsg(CommonCode.DELETE_FAIL.msg());
		}
		
		return result;
	}

	@Override
	public NcovRealtime getNcovRealtimeById(String id) {
		NcovRealtime ncovRealtime = ncovRealtimeMapper.selectById(id);
		return ncovRealtime;
	}

}
