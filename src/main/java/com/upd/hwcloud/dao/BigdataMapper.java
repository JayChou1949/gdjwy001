package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.dto.YqServiceDetail;
import com.upd.hwcloud.bean.dto.YqStatistics;
import com.upd.hwcloud.bean.entity.Bigdata;
import com.upd.hwcloud.bean.vo.daasService.ServiceIssueVo;
import com.upd.hwcloud.bean.vo.daasService.ServiceQualityVo;
import com.upd.hwcloud.bean.vo.daasService.ServiceRequestVo;
import com.upd.hwcloud.bean.vo.daasService.ServiceSubscribeVo;
import com.upd.hwcloud.bean.vo.workbench.BigDataOfSaasVO;

import org.apache.ibatis.annotations.Param;
import org.apache.kafka.common.protocol.types.Field;

import java.util.List;

/**
 * <p>
 * 大数据库服务目录 Mapper 接口
 * </p>
 *
 * @author huru
 * @since 2018-12-26
 */
public interface BigdataMapper extends BaseMapper<Bigdata> {

    Page<Bigdata> getPage(Page<Bigdata> page, @Param("name") String name, @Param("dataFrom") String dataFrom,
                          @Param("collectionUnit") String collectionUnit, @Param("category") String category);

    List<Bigdata> getPage(@Param("name") String name, @Param("dataFrom") String dataFrom,
                          @Param("collectionUnit") String collectionUnit, @Param("category") String category);

    List<ServiceIssueVo> serviceStatisticsByIssue(Page page, @Param("ew") Wrapper<Bigdata> wrapper);

    List<ServiceSubscribeVo> serviceStatisticsBySubscribe(Page page, @Param("ew") Wrapper<Bigdata> wrapper);

    List<ServiceRequestVo> serviceStatisticsByRequest(Page page, @Param("ew") Wrapper<Bigdata> wrapper);

    List<ServiceQualityVo> serviceStatisticsByQuality(Page page, @Param("ew") Wrapper<Bigdata> wrapper);

    List<BigDataOfSaasVO> saasLvThreeExportVo(@Param("names") List<String> names);

    List<YqServiceDetail> yqServiceDetail(@Param("label") String label,@Param("serviceName") String serviceName,@Param("appName") String appName,@Param("before") Integer before,@Param("after") Integer after);

    YqServiceDetail countAndAvg(@Param("apiId") String apiId, @Param("actualId") String actualId);

    List<YqStatistics> areaOrder(String label);

    List<YqStatistics> policeOrder(String label);

    List<YqStatistics> appOrder(String label);

    List<YqStatistics> serviceByOrder(String label);

    Integer yqdy(@Param("label") String label,@Param("serviceName") String serviceName,@Param("appName") String appName);
}
