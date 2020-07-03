package com.hirisun.cloud.third.service;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.model.ncov.dto.CovOrderDetail;
import com.hirisun.cloud.model.ncov.dto.CovOverview;
import com.hirisun.cloud.model.ncov.dto.CovOverviewLevel2;
import com.hirisun.cloud.model.ncov.dto.CovStatistic;
import com.hirisun.cloud.model.ncov.dto.DeskTopNum;
import com.hirisun.cloud.model.ncov.dto.EpidemicDeskIssue;
import com.hirisun.cloud.model.ncov.dto.EpidemicDesktop;
import com.hirisun.cloud.model.third.dto.AbnoramlExport;
import com.hirisun.cloud.model.third.dto.AppReqServiceDTO;
import com.hirisun.cloud.model.third.dto.BigdataExport;
import com.hirisun.cloud.model.third.dto.DaasFwTopDTO;
import com.hirisun.cloud.model.third.dto.DelayDTO;
import com.hirisun.cloud.model.third.dto.DelayExport;
import com.hirisun.cloud.model.third.dto.DirectUnitOrderDetail;
import com.hirisun.cloud.model.third.dto.DirectUnitStatistics;
import com.hirisun.cloud.model.third.dto.GeneralDTO;
import com.hirisun.cloud.model.third.dto.InstanceExport;
import com.hirisun.cloud.model.third.dto.ReqDTO;
import com.hirisun.cloud.model.third.dto.Top10DTO;
import com.hirisun.cloud.model.third.dto.TopBdyDTO;
import com.hirisun.cloud.model.third.dto.TopDyDTO;
import com.hirisun.cloud.model.third.dto.TopJzDTO;
import com.hirisun.cloud.model.third.dto.XtdyExport;
import com.hirisun.cloud.third.bean.ThreePartyInterface;

/**
 * <p>
 * 第三方接口表 服务类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-06-28
 */
public interface ThreePartyInterfaceService extends IService<ThreePartyInterface> {

    List<ThreePartyInterface> getByParams(ThreePartyInterface threePartyInterface);

    void getOrUpdateData(String url,String id,String label);

    void saveOrUpdateData(ThreePartyInterface threePartyInterface);

    void postOrUpdateData(String url, String id, String label, Map<String,String> map);

    void postJsonUpdateData(String url,String id,String label,String json);

    List<DaasFwTopDTO> fwfwTop10();

    List<XtdyExport> xtdyTop(boolean normal, String starTime, String endTime);

    List<InstanceExport> daasInstance(String starTime, String endTime, String area, String category);

    List<DaasFwTopDTO> zxgzTop10();

    List<DelayExport> paasDelay();

    List<AbnoramlExport> paasAbnormal();

    List<Top10DTO> paasFwfwExport();

    List<XtdyExport> paasXtdyExport(boolean normal,String starTime,String endTime);

    List<InstanceExport> paasInstance(String starTime,String endTime,String area,String category);

    List<TopBdyDTO> paasFwDyExport();

    List<TopDyDTO> paasXtSubExport();

    List<TopJzDTO> paasZcjzExport();

    Map<String, Object> areaPoliceGk(String type, String name);

    List<GeneralDTO> areaPoliceRecent10ReqService(String type, String name);

    List<DelayDTO> areaPoliceRecent7ReqDelay(String type, String name);

    List<ReqDTO> areaPoliceRecent7Req(String type, String name);

    IPage<GeneralDTO> serviceReqPage(IPage<GeneralDTO> page, String type, String name);

    IPage<GeneralDTO> appReqPage(IPage<GeneralDTO> page, String type, String name);

    IPage<AppReqServiceDTO> appReqService(IPage<AppReqServiceDTO> page, String type, String name, Map<String, Object> param);

    List<BigdataExport> serviceMessage();

    void updataFile(MultipartFile file) throws IOException;

    void downdataFile(HttpServletResponse response) throws IOException;

    void uploadFile(MultipartFile file) throws IOException;

    void downloadFile(HttpServletResponse response) throws IOException;

    CovOverview areaData();

    CovOverview policeData();

    List<CovOverviewLevel2> policeDataLevel2();

    List<CovOverviewLevel2> areaDataLevel2();

    IPage<CovStatistic> policeStatistic(String policeName, Integer pageNo, Integer pageSize);

    IPage<CovStatistic> areaStatistic(String areaName,Integer pageNo,Integer pageSize);

    IPage<CovOrderDetail> policeOrderDetail(String policeName, Integer pageNo, Integer pageSize);

    IPage<CovOrderDetail> areaOrderDetail(String areaName, Integer pageNo, Integer pageSize);

    CovOverview unitOverview() throws Exception;

    List<EpidemicDesktop> epidemicExcl() throws Exception;

    EpidemicDeskIssue epidemicDeskIssue(List<EpidemicDesktop> epidemicDesktops);

    DeskTopNum epidemicExclNum(List<EpidemicDesktop> epidemicDesktops) throws Exception;

    List<CovOverviewLevel2> unitOverviewLevel2() throws Exception;

    IPage<DirectUnitStatistics> unitStatistic(String unitName, Integer pageNo, Integer pageSize) throws Exception;

    IPage<DirectUnitOrderDetail> unitOrderDetail(String unitName, Integer pageNo, Integer pageSize) throws Exception;

}
