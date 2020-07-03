package com.hirisun.cloud.third.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.model.ncov.dto.CovOrderDetail;
import com.hirisun.cloud.model.ncov.dto.CovOverview;
import com.hirisun.cloud.model.ncov.dto.CovOverviewLevel2;
import com.hirisun.cloud.model.ncov.dto.CovStatistic;
import com.hirisun.cloud.model.third.dto.*;
import com.hirisun.cloud.third.bean.ThreePartyInterface;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 第三方接口表 Mapper 接口
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-06-28
 */
public interface ThreePartyInterfaceMapper extends BaseMapper<ThreePartyInterface> {

    List<ThreePartyInterface> getByParams(ThreePartyInterface threePartyInterface);
    List<DaasFwTopDTO> fwfwtop();

    /**
     * 系统调用
     *
     * @param normal true:表示只查找正常数据，不包含非规范实例
     * @return
     */
    List<XtdyExport> xtdytop(@Param("normal") boolean normal, @Param("starTime") String starTime, @Param("endTime") String endTime);

    List<InstanceExport> daasInstance(@Param("starTime") String starTime, @Param("endTime") String endTime, @Param("area") String area, @Param("category") String category);

    List<DaasFwTopDTO> zxgztop();

    List<DelayExport> paasDelay();

    List<AbnoramlExport> paasAbnormal();

    List<Top10DTO> paasFwfwExport();

    /**
     * 系统调用
     *
     * @param normal true:表示只查找正常数据，不包含非规范实例
     * @return
     */
    List<XtdyExport> paasXtdyExport(@Param("normal") boolean normal, @Param("starTime") String starTime, @Param("endTime") String endTime);

    List<InstanceExport> paasInstance(@Param("starTime") String starTime, @Param("endTime") String endTime, @Param("area") String area, @Param("category") String category);

    List<TopBdyDTO> paasFwDyExport();

    List<TopDyDTO> paasXtSubExport();

    List<TopJzDTO> paasZcjzExport();

    Integer getAreaPoliceAppCount(@Param("type") String type, @Param("name") String name);

    Integer getAreaPoliceSubServiceCount(@Param("type") String type, @Param("name") String name);

    Integer getAreaPoliceReqCount(@Param("type") String type, @Param("name") String name);

    Integer getAreaPoliceYesterdayReqCount(@Param("type") String type, @Param("name") String name);

    List<GeneralDTO> getAreaPoliceRecent10ReqService(@Param("type") String type, @Param("name") String name);

    List<DelayDTO> getAreaPoliceRecent7ReqDelay(@Param("type") String type, @Param("name") String name);

    List<ReqDTO> getAreaPoliceRecent7Req(@Param("type") String type, @Param("name") String name);

    IPage<GeneralDTO> getServiceReqPage(IPage<GeneralDTO> page, @Param("type") String type, @Param("name") String name);

    IPage<GeneralDTO> getAppReqPage(IPage<GeneralDTO> page, @Param("type") String type, @Param("name") String name);

    IPage<AppReqServiceDTO> getAppReqService(IPage<AppReqServiceDTO> page, @Param("type") String type,
                                             @Param("name") String name, @Param("p") Map<String, Object> param);

    List<BigdataExport> serviceMessage();

    CovOverview policeCountAndOrder();

    CovOverview areaCountAndOrder();

    Integer policeDy();

    /**
     * 省厅昨日调用次数新增
     *
     * @return
     */
    Integer policeYesterdayDy(@Param("date") String date);

    Integer areaDy();

    /**
     * 地市昨日调用次数新增
     *
     * @return
     */
    Integer areaYesterdayDy(@Param("date") String date);

    List<CovOverviewLevel2> orderByPolice();

    List<CovOverviewLevel2> dyByPolice();

    /**
     * 昨日新增调用数
     *
     * @return
     */
    List<CovOverviewLevel2> ydByPolice(@Param("date") String date);
    List<CovOverviewLevel2> orderByArea();

    /**
     * 地市昨日新增调用
     *
     * @return
     */
    List<CovOverviewLevel2> ydByArea(@Param("date") String date);
    List<CovOverviewLevel2> dyByArea();


    List<CovStatistic> policeStatistic(@Param("policeName") String policeName, @Param("before") Integer before, @Param("after") Integer after);

    Integer policeTotal(String policeName);

    List<CovStatistic> areaStatistic(@Param("areaName") String areaName, @Param("before") Integer before, @Param("after") Integer after);

    Integer areaTotal(String areaName);

    List<CovOrderDetail> policeOrderDetail(@Param("policeName") String policeName, @Param("before") Integer before, @Param("after") Integer after);

    Integer policeOrderTotal(String policeName);

    List<CovOrderDetail> areaOrderDetail(@Param("areaName") String areaName, @Param("before") Integer before, @Param("after") Integer after);

    Integer lineDyCount(@Param("instanceId") String instanceId, @Param("serviceId") String serviceId);

    Integer areaOrderTotal(String areaName);
}
