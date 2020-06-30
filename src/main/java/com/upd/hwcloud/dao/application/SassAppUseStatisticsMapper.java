package com.upd.hwcloud.dao.application;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upd.hwcloud.bean.entity.application.SassAppUseStatistics;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 应用访问统计表 Mapper 接口
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-06-29
 */
public interface SassAppUseStatisticsMapper extends BaseMapper<SassAppUseStatistics> {
    List<Map> queryUseCount(@Param("param") Map param);
    List<Map> queryAreaUseCount(@Param("param") Map param);
    List<Map> queryPoliceUseCount(@Param("param") Map param);
    List<Map> queryAppUseCount(@Param("param") Map param);
    List<Map> queryAreaUserCount(@Param("param") Map param);
    List<Map> queryPoliceUserCount(@Param("param") Map param);
    Integer countByAppAuth(@Param("param") Map param);
    List<Map> queryAreaUserAvgCount(@Param("param") Map param);
    List<Map> queryPoliceUserAvgCount(@Param("param") Map param);
    Long allCount(@Param("param") Map param);

}
