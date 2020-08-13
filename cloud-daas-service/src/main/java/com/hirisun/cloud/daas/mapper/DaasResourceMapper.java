package com.hirisun.cloud.daas.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.daas.bean.DaasResource;
import com.hirisun.cloud.model.daas.vo.BigDataOfSaasVO;
import com.hirisun.cloud.model.daas.vo.ServiceIssueVo;
import com.hirisun.cloud.model.daas.vo.ServiceQualityVo;
import com.hirisun.cloud.model.daas.vo.ServiceRequestVo;
import com.hirisun.cloud.model.daas.vo.ServiceSubscribeVo;
import com.hirisun.cloud.model.daas.vo.YqServiceDetailVo;
import com.hirisun.cloud.model.daas.vo.YqStatisticsVo;


public interface DaasResourceMapper extends BaseMapper<DaasResource> {

    Page<DaasResource> getPage(Page<DaasResource> page, @Param("name") String name, @Param("dataFrom") String dataFrom,
                          @Param("collectionUnit") String collectionUnit, @Param("category") String category);

    List<DaasResource> getPage(@Param("name") String name, @Param("dataFrom") String dataFrom,
                          @Param("collectionUnit") String collectionUnit, @Param("category") String category);

    List<ServiceIssueVo> serviceStatisticsByIssue(Page page, @Param("ew") Wrapper<DaasResource> wrapper);

    List<ServiceSubscribeVo> serviceStatisticsBySubscribe(Page page, @Param("ew") Wrapper<DaasResource> wrapper);

    List<ServiceRequestVo> appStatisticsByRequest(Page page, @Param("ew") Wrapper<DaasResource> wrapper);

    List<ServiceRequestVo> serviceStatisticsByRequest(Page page, @Param("ew") Wrapper<DaasResource> wrapper);

    List<ServiceQualityVo> serviceStatisticsByQuality(Page page, @Param("ew") Wrapper<DaasResource> wrapper);

    List<BigDataOfSaasVO> saasLvThreeExportVo(@Param("names") List<String> names);

    List<YqServiceDetailVo> yqServiceDetail(@Param("label") String label,@Param("serviceName") String serviceName,@Param("appName") String appName,@Param("before") Integer before,@Param("after") Integer after);

    YqServiceDetailVo countAndAvg(@Param("apiId") String apiId, @Param("actualId") String actualId);

    List<YqStatisticsVo> areaOrder(String label);

    List<YqStatisticsVo> policeOrder(String label);

    List<YqStatisticsVo> appOrder(String label);

    List<YqStatisticsVo> serviceByOrder(String label);

    Integer yqdy(@Param("label") String label,@Param("serviceName") String serviceName,@Param("appName") String appName);
}
