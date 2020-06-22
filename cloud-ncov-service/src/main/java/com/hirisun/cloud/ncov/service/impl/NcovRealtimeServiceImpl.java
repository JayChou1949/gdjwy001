package com.hirisun.cloud.ncov.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hirisun.cloud.common.vo.CommonCode;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.model.ncov.vo.HomePageNcovRealtimeVo;
import com.hirisun.cloud.ncov.bean.NcovRealtime;
import com.hirisun.cloud.ncov.mapper.NcovRealtimeMapper;
import com.hirisun.cloud.ncov.service.NcovRealtimeService;

@Service
public class NcovRealtimeServiceImpl implements NcovRealtimeService {

	@Autowired
	private NcovRealtimeMapper ncovRealtimeMapper;
	
	@Override
	public HomePageNcovRealtimeVo getHomePageNcovRealtimeVo() {
		
		//获取广东数据
//		ncovRealtimeMapper.selectList(new QueryWrapper<NcovRealtime>().lambda().eq(NcovRealtime::getProvinceCode,44).eq(column, val))
		
		return null;
	}

	@Override
	public boolean importNcovRealtimeData() {
		
		return false;
	}

	@Transactional
	public ResponseResult saveNcovRealtime(NcovRealtime ncovRealtime) {
		
		int insert = ncovRealtimeMapper.insert(ncovRealtime);
		
		ResponseResult result = new ResponseResult(CommonCode.SUCCESS.code(),CommonCode.SUCCESS.msg());
		
		if(insert ==0) {
			result.setCode(CommonCode.INSERT_FAIL.code());
			result.setMsg(CommonCode.INSERT_FAIL.msg());
		}
		
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

}
