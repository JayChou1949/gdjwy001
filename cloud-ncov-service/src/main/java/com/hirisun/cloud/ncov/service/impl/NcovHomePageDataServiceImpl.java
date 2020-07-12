package com.hirisun.cloud.ncov.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hirisun.cloud.ncov.bean.NcovHomePageData;
import com.hirisun.cloud.ncov.mapper.NcovHomePageDataMapper;
import com.hirisun.cloud.ncov.service.NcovHomePageDataService;

@Service
public class NcovHomePageDataServiceImpl implements NcovHomePageDataService {

	@Autowired
	private NcovHomePageDataMapper ncovHomePageDataMapper;
	
	/**
	 * 根据数据类型获取单条数据
	 * @param dataType
	 * @return
	 */
	public NcovHomePageData getNcovHomePageDataByType(String dataType) {
		
		Map<String, Object> columnMap = new HashMap<String, Object>();
		columnMap.put("DATA_TYPE", dataType);
		List<NcovHomePageData> list = ncovHomePageDataMapper.selectByMap(columnMap);
		if(CollectionUtils.isNotEmpty(list)) {
			NcovHomePageData ncovHomePageData = list.get(0);
			return ncovHomePageData;
		}
		return null;
	}

	/**
	 * 新增数据
	 * @param data
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class) 
	public int saveNcovHomePageData(NcovHomePageData data) {
		return ncovHomePageDataMapper.insert(data);
	}

	/**
	 * 更新数据
	 * @param data
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class) 
	public int updateNcovHomePageData(NcovHomePageData data) {
		return ncovHomePageDataMapper.updateById(data);
	}

	
}
