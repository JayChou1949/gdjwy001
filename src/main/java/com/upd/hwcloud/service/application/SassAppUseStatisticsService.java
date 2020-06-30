package com.upd.hwcloud.service.application;

import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.application.SassAppUseStatistics;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 应用访问统计表 服务类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-06-29
 */
public interface SassAppUseStatisticsService extends IService<SassAppUseStatistics> {
    void importData(MultipartFile file, String idCard);

    List<Map> queryUseCount(Map map);

    List<Map> queryAreaUseCount(Map map);
    List<Map> queryPoliceUseCount(Map map);
    List<Map> queryAppUseCount(Map map);
    List<Map> queryAreaUserCount(Map map);
    List<Map> queryPoliceUserCount(Map map);
    Integer countByAppAuth(Map param);
    List<Map> queryAreaUserAvgCount(Map param);
    List<Map> queryPoliceUserAvgCount(Map param);
    Long allCount(Map param);

}
