package com.hirisun.cloud.ncov.service;

import com.hirisun.cloud.ncov.bean.NcovHomePageData;

public interface NcovHomePageDataService {

	/**
	 * 根据数据类型获取单条数据
	 * @param dataType
	 * @return
	 */
	public NcovHomePageData getNcovHomePageDataByType(String dataType);
	
	/**
	 * 新增数据
	 * @param data
	 * @return
	 */
	public int saveNcovHomePageData(NcovHomePageData data);
	
	/**
	 * 更新数据
	 * @param data
	 * @return
	 */
	public int updateNcovHomePageData(NcovHomePageData data);
}
