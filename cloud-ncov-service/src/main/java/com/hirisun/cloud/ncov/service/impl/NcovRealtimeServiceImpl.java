package com.hirisun.cloud.ncov.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
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
	
	/**
	 * 获取首页 疫情实时数据,先读取缓存，没有则读取数据库
	 */
	public HomePageNcovRealtimeVo getHomePageNcovRealtimeVo() {
		
		String resString = redisApi.getStrValue(NcovKey.HOME_PAGE_NCOV_REALTIME);
		if(StringUtils.isNotBlank(resString))return JSON.parseObject(resString,HomePageNcovRealtimeVo.class);
		
		HomePageNcovRealtimeVo vo = setHomePageNcovRealtimeCache();
		return vo;
	}
	
	/**
	 * 获取首页疫情实时数据
	 * @return
	 */
	private HomePageNcovRealtimeVo getHomePageNcovRealtime() {
		//全国数据省份
		List<NcovRealtimeVo> provinceList = ncovRealtimeMapper.findNcovRealtimeList(1);
		//全国统计数据
		NcovRealtimeVo counturyTotal = ncovRealtimeMapper.countNcovRealTime(1);
		//所有城市列表数据
		List<NcovRealtimeVo> cityList = ncovRealtimeMapper.findNcovRealtimeList(2);
		//统计所有城市列表数据
		NcovRealtimeVo cityTotal = ncovRealtimeMapper.countNcovRealTime(2);
		
		HomePageNcovRealtimeVo vo = new HomePageNcovRealtimeVo();
		//当天全国各省确诊、疑似、治愈、死亡,较昨日增长总计
		vo.setCountryTotal(counturyTotal);
		//广东确诊、治愈、死亡,较昨日增长总计
		vo.setCityTotal(cityTotal);
		//当天全国各省确诊、疑似、治愈、死亡地图数据
		vo.setProvinceList(provinceList);
		//广东列表数据
		vo.setCityList(cityList);
		return vo;
	}

	/**
	 * 导入数据,存在则刷新,不存在则新增
	 */
	@Transactional
	public ResponseResult importNcovRealtimeData(List<NcovRealtimeVo> importList)throws CustomException {
		
		ResponseResult result = new ResponseResult(CommonCode.SUCCESS.code(),CommonCode.SUCCESS.msg());
		
		if(CollectionUtils.isNotEmpty(importList)) {
			
			importList.forEach(ncovRealtime->{
				saveOrUpdateNcovRealtime(ncovRealtime);
			});
			//刷新缓存
			setHomePageNcovRealtimeCache();
		}else {
			result.setCode(CommonCode.IMPORT_FAIL.code());
			result.setMsg(CommonCode.IMPORT_FAIL.msg());
		}
		return result;
		
	}

	/**
	 * 根据id获取数据
	 */
	public NcovRealtime getNcovRealtimeById(String id) {
		NcovRealtime ncovRealtime = ncovRealtimeMapper.selectById(id);
		return ncovRealtime;
	}

	/**
	 * 新增或更新数据
	 * @param vo
	 */
	private void saveOrUpdateNcovRealtime(NcovRealtimeVo vo) {
		String regionName = vo.getRegionName();
		Map<String, Object> columnMap = new HashMap<String, Object>();
		columnMap.put("REGION_NAME", regionName);
		List<NcovRealtime> list = ncovRealtimeMapper.selectByMap(columnMap);
		
		if(CollectionUtils.isNotEmpty(list)) {
			list.forEach(entity->{
				BeanUtils.copyProperties(vo, entity);
				ncovRealtimeMapper.updateById(entity);
			});
		}else {
			NcovRealtime entity = new NcovRealtime();
			BeanUtils.copyProperties(vo, entity);
			ncovRealtimeMapper.insert(entity);
		}
	}
	
	/**
	 * 后台系统编辑数据
	 */
	@Transactional
	public ResponseResult editNcovRealtime(NcovRealtimeVo vo) {
		
		ResponseResult result = new ResponseResult(CommonCode.SUCCESS.code(),CommonCode.SUCCESS.msg());
		saveOrUpdateNcovRealtime(vo);
		//刷新缓存
		setHomePageNcovRealtimeCache();
		return result;
	}

	/**
	 *刷新缓存 
	 */
	public HomePageNcovRealtimeVo setHomePageNcovRealtimeCache() {
		HomePageNcovRealtimeVo vo = getHomePageNcovRealtime();
		redisApi.setForPerpetual(NcovKey.HOME_PAGE_NCOV_REALTIME, JSON.toJSONString(vo));
		return vo;
	}

}
