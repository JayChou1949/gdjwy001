package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.dto.AbnoramlExport;
import com.upd.hwcloud.bean.dto.AppReqServiceDTO;
import com.upd.hwcloud.bean.dto.BigdataExport;
import com.upd.hwcloud.bean.dto.DaasFwTopDTO;
import com.upd.hwcloud.bean.dto.DelayDTO;
import com.upd.hwcloud.bean.dto.DelayExport;
import com.upd.hwcloud.bean.dto.GeneralDTO;
import com.upd.hwcloud.bean.dto.InstanceExport;
import com.upd.hwcloud.bean.dto.ReqDTO;
import com.upd.hwcloud.bean.dto.Top10DTO;
import com.upd.hwcloud.bean.dto.TopBdyDTO;
import com.upd.hwcloud.bean.dto.TopDyDTO;
import com.upd.hwcloud.bean.dto.TopJzDTO;
import com.upd.hwcloud.bean.dto.XtdyExport;
import com.upd.hwcloud.bean.dto.cov.CovOrderDetail;
import com.upd.hwcloud.bean.dto.cov.CovOverview;
import com.upd.hwcloud.bean.dto.cov.CovOverviewLevel2;
import com.upd.hwcloud.bean.dto.cov.CovStatistic;
import com.upd.hwcloud.bean.dto.cov.DeskTopNum;
import com.upd.hwcloud.bean.dto.cov.DirectUnitOrderDetail;
import com.upd.hwcloud.bean.dto.cov.DirectUnitStatistics;
import com.upd.hwcloud.bean.dto.cov.EpidemicDeskIssue;
import com.upd.hwcloud.bean.dto.cov.EpidemicDesktop;
import com.upd.hwcloud.bean.entity.ThreePartyInterface;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 第三方接口表 服务类
 * </p>
 *
 * @author huru
 * @since 2018-12-03
 */
public interface IThreePartyInterfaceService extends IService<ThreePartyInterface> {

    void getOrUpdateData(String url,String id,String label);

    void saveOrUpdateData(ThreePartyInterface threePartyInterface);

    void postOrUpdateData(String url, String id, String label, Map<String,String> map);

    void postJsonUpdateData(String url,String id,String label,String json);

    List<DaasFwTopDTO> fwfwTop10();

    List<XtdyExport> xtdyTop(boolean normal,String starTime,String endTime);

    List<InstanceExport> daasInstance(String starTime,String endTime,String area,String category);

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

    IPage<CovStatistic> policeStatistic(String policeName,Integer pageNo,Integer pageSize);

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
