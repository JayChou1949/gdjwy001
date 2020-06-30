package com.hirisun.cloud.ncov.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hirisun.cloud.model.ncov.vo.realtime.NcovRealtimeVo;
import com.hirisun.cloud.ncov.bean.NcovRealtime;

public interface NcovRealtimeMapper extends BaseMapper<NcovRealtime>{
	
	NcovRealtimeVo countNcovRealTime(@Param("regionType")Integer regionType,@Param("rowNum")Integer rowNum );
	
	List<NcovRealtimeVo> findNcovRealtimeList(@Param("regionType")Integer regionType,@Param("rowNum")Integer rowNum );
	
	
}