package com.hirisun.cloud.ncov.mapper;

import com.hirisun.cloud.ncov.bean.NcovRealtime;

public interface NcovRealtimeMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(NcovRealtime record);

    int insertSelective(NcovRealtime record);

    NcovRealtime selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(NcovRealtime record);

    int updateByPrimaryKey(NcovRealtime record);
}