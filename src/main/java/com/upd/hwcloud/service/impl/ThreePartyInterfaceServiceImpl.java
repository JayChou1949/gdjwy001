package com.upd.hwcloud.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.dto.*;
import com.upd.hwcloud.bean.dto.cov.*;
import com.upd.hwcloud.bean.entity.ThreePartyInterface;
import com.upd.hwcloud.common.utils.OkHttpUtils;
import com.upd.hwcloud.common.utils.ncov.UnitExcelExportUtil;
import com.upd.hwcloud.dao.ThreePartyInterfaceMapper;
import com.upd.hwcloud.service.IThreePartyInterfaceService;
import okhttp3.Response;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 第三方接口表 服务实现类
 * </p>
 *
 * @author huru
 * @since 2018-12-03
 */
@Service
public class ThreePartyInterfaceServiceImpl extends ServiceImpl<ThreePartyInterfaceMapper, ThreePartyInterface> implements IThreePartyInterfaceService {

    private static Logger logger = LoggerFactory.getLogger(ThreePartyInterfaceServiceImpl.class);

    @Autowired
    private ThreePartyInterfaceMapper threePartyInterfaceMapper;

    private static String rootPath;

    @Value("${file.path}")
    public void setRootPath(String rootPath) {
        ThreePartyInterfaceServiceImpl.rootPath = rootPath;
    }


    @Override
    public void saveOrUpdateData(ThreePartyInterface threePartyInterface) {
        int resultCount = this.count(new QueryWrapper<ThreePartyInterface>().lambda().eq(ThreePartyInterface::getId, threePartyInterface.getId()));
        if (resultCount == 0) {
            this.save(threePartyInterface);
        } else {
            this.updateById(threePartyInterface);
        }
    }

    /**
     * 获取或更新数据
     *
     * @param url
     * @return
     */
    @Override
    public void getOrUpdateData(String url, String id, String label) {
        String data = null;
        Response response = null;
        try {
            response = OkHttpUtils.get(url, null);
            data = response.body().string();
            if (JSONObject.parseObject(data).getInteger("code") != 0) {
                return;
            }
            ThreePartyInterface threePartyInterface = threePartyInterfaceMapper.selectById(id);
            if (threePartyInterface == null) {
                threePartyInterface = new ThreePartyInterface();
                threePartyInterface.setId(id);
                threePartyInterface.setLabel(label);
                threePartyInterface.setData(data);
                threePartyInterfaceMapper.insert(threePartyInterface);
            } else {
                threePartyInterface.setData(data);
                threePartyInterfaceMapper.updateById(threePartyInterface);
            }
        } catch (IOException e) {
            logger.debug(label + "接口获取数据失败");
        } finally {
            if (response!=null){
                response.close();
            }
        }
    }

    @Override
    public void postOrUpdateData(String url, String id, String label, Map<String, String> map) {
        Response response = null;
        try {
            if ("模型超市按模型上线时间排行".equals(label)) {
                map = new HashMap<>();
                map.put("type", "1");
            } else if ("模型超市按模型热度排行".equals(label)) {
                map = new HashMap<>();
                map.put("type", "2");
            }
            response = OkHttpUtils.post(url, map);
            String data = response.body().string();
            ThreePartyInterface threePartyInterface = threePartyInterfaceMapper.selectById(id);
            if (threePartyInterface == null) {
                threePartyInterface = new ThreePartyInterface();
                threePartyInterface.setId(id);
                threePartyInterface.setLabel(label);
                threePartyInterface.setData(data);
                threePartyInterfaceMapper.insert(threePartyInterface);
            } else {
                threePartyInterface.setData(data);
                threePartyInterfaceMapper.updateById(threePartyInterface);
            }
        } catch (IOException e) {
            logger.debug(label + "接口获取数据失败");
        } finally {
            if (response!=null){
                response.close();
            }
        }
    }

    @Override
    public void postJsonUpdateData(String url, String id, String label, String json) {
        Response response = null;
        try {
            response = OkHttpUtils.postJson(url, json);
            String data = response.body().string();
            if (JSONObject.parseObject(data).getInteger("code") != 0) {
                return;
            }
            ThreePartyInterface threePartyInterface = threePartyInterfaceMapper.selectById(id);
            if (threePartyInterface == null) {
                threePartyInterface = new ThreePartyInterface();
                threePartyInterface.setId(id);
                threePartyInterface.setLabel(label);
                threePartyInterface.setData(data);
                threePartyInterfaceMapper.insert(threePartyInterface);
            } else {
                threePartyInterface.setData(data);
                threePartyInterfaceMapper.updateById(threePartyInterface);
            }
        } catch (IOException e) {
            logger.debug(label + "接口获取数据失败");
        } finally {
            if (response!=null){
                response.close();
            }
        }
    }

    /**
     * 服务访问top10
     */
    @Override
    public List<DaasFwTopDTO> fwfwTop10() {
        List<DaasFwTopDTO> top10DTOList = threePartyInterfaceMapper.fwfwtop();
        return top10DTOList;
    }

    @Override
    public List<XtdyExport> xtdyTop(boolean normal, String starTime, String endTime) {
        List<XtdyExport> top10DTOList = threePartyInterfaceMapper.xtdytop(normal, starTime, endTime);
        return top10DTOList;
    }

    @Override
    public List<InstanceExport> daasInstance(String starTime, String endTime, String area, String category) {
        return threePartyInterfaceMapper.daasInstance(starTime, endTime, area, category);
    }

    /**
     * 最新挂载top10
     *
     * @return
     */
    @Override
    public List<DaasFwTopDTO> zxgzTop10() {
        List<DaasFwTopDTO> top10DTOList = threePartyInterfaceMapper.zxgztop();
        return top10DTOList;
    }

    @Override
    public List<DelayExport> paasDelay() {
        return threePartyInterfaceMapper.paasDelay();
    }

    @Override
    public List<AbnoramlExport> paasAbnormal() {
        return threePartyInterfaceMapper.paasAbnormal();
    }

    @Override
    public List<Top10DTO> paasFwfwExport() {
        return threePartyInterfaceMapper.paasFwfwExport();
    }

    @Override
    public List<XtdyExport> paasXtdyExport(boolean normal, String starTime, String endTime) {
        return threePartyInterfaceMapper.paasXtdyExport(normal, starTime, endTime);
    }

    @Override
    public List<InstanceExport> paasInstance(String starTime, String endTime, String area, String category) {
        return threePartyInterfaceMapper.paasInstance(starTime, endTime, area, category);
    }

    @Override
    public List<TopBdyDTO> paasFwDyExport() {
        return threePartyInterfaceMapper.paasFwDyExport();
    }

    @Override
    public List<TopDyDTO> paasXtSubExport() {
        return threePartyInterfaceMapper.paasXtSubExport();
    }

    @Override
    public List<TopJzDTO> paasZcjzExport() {
        return threePartyInterfaceMapper.paasZcjzExport();
    }

    @Override
    public Map<String, Object> areaPoliceGk(String type, String name) {
        Integer appCount = threePartyInterfaceMapper.getAreaPoliceAppCount(type, name);
        Integer subscriptionServiceCount = threePartyInterfaceMapper.getAreaPoliceSubServiceCount(type, name);
        Integer reqCount = threePartyInterfaceMapper.getAreaPoliceReqCount(type, name);
        Integer yesterdayReqCount = threePartyInterfaceMapper.getAreaPoliceYesterdayReqCount(type, name);

        Map<String, Object> result = Maps.newHashMap();
        result.put("appCount", appCount);
        result.put("subscriptionServiceCount", subscriptionServiceCount);
        result.put("reqCount", reqCount);
        result.put("yesterdayReqCount", yesterdayReqCount);
        return result;
    }

    @Override
    public List<GeneralDTO> areaPoliceRecent10ReqService(String type, String name) {
        return threePartyInterfaceMapper.getAreaPoliceRecent10ReqService(type, name);
    }

    @Override
    public List<DelayDTO> areaPoliceRecent7ReqDelay(String type, String name) {
        return threePartyInterfaceMapper.getAreaPoliceRecent7ReqDelay(type, name);
    }

    @Override
    public List<ReqDTO> areaPoliceRecent7Req(String type, String name) {
        return threePartyInterfaceMapper.getAreaPoliceRecent7Req(type, name);
    }

    @Override
    public IPage<GeneralDTO> serviceReqPage(IPage<GeneralDTO> page, String type, String name) {
        return threePartyInterfaceMapper.getServiceReqPage(page, type, name);
    }

    @Override
    public IPage<GeneralDTO> appReqPage(IPage<GeneralDTO> page, String type, String name) {
        return threePartyInterfaceMapper.getAppReqPage(page, type, name);
    }

    @Override
    public IPage<AppReqServiceDTO> appReqService(IPage<AppReqServiceDTO> page, String type, String name, Map<String, Object> param) {
        return threePartyInterfaceMapper.getAppReqService(page, type, name, param);
    }

    @Override
    public List<BigdataExport> serviceMessage() {
        return threePartyInterfaceMapper.serviceMessage();
    }

    @Override
    public void updataFile(MultipartFile file) throws IOException {
        upFile(file, "疫情桌面云.xls");
    }

    @Override
    public void downdataFile(HttpServletResponse response) throws IOException {
        downFile(response, "疫情桌面云.xls");
    }

    @Override
    public void uploadFile(MultipartFile file) throws IOException {
        upFile(file, "省直疫情数据服务调用统计.xls");
    }

    @Override
    public void downloadFile(HttpServletResponse response) throws IOException {
        downFile(response, "省直疫情数据服务调用统计.xls");
    }

    private void downFile(HttpServletResponse response, String name) throws IOException {
        File file = new File(rootPath+"/" + name);
        if (file.exists()) {
            // 设置强制下载不打开
            response.setContentType("application/force-download");
            String originaName;
            try {
                originaName = URLEncoder.encode(name, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                originaName = "省直疫情数据服务调用统计.xls";
            }
            // 设置文件名
            response.addHeader("Content-Disposition", "attachment;fileName=" + originaName);
            FileUtils.copyFile(file, response.getOutputStream());
        }
    }

    private void upFile(MultipartFile file, String name) throws IOException {
        File targetFile = new File(rootPath+"/" + name);
        targetFile.exists();
        FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
        InputStream inputStream = file.getInputStream();
        IOUtils.copy(inputStream, fileOutputStream);
        IOUtils.closeQuietly(inputStream);
        IOUtils.closeQuietly(fileOutputStream);
    }

    @Override
    public CovOverview policeData() {
        String ydate = getYesterdayDate();
        CovOverview covOverview = threePartyInterfaceMapper.policeCountAndOrder();
        Integer count = threePartyInterfaceMapper.policeDy();
        Integer ycount = threePartyInterfaceMapper.policeYesterdayDy(ydate);
        if (count == null) {
            count = 0;
        }
        if (ycount == null) {
            ycount = 0;
        }
        covOverview.setDyCount(count);
        covOverview.setYdCount(ycount);
        return covOverview;
    }

    @Override
    public CovOverview areaData() {
        CovOverview covOverview = threePartyInterfaceMapper.areaCountAndOrder();
        Integer count = threePartyInterfaceMapper.areaDy();
        Integer ycount = threePartyInterfaceMapper.areaYesterdayDy(getYesterdayDate());
        if (count == null) {
            count = 0;
        }
        if (ycount == null) {
            ycount = 0;
        }
        covOverview.setDyCount(count);
        covOverview.setYdCount(ycount);
        return covOverview;
    }

    @Override
    public List<CovOverviewLevel2> policeDataLevel2() {
        //  各警种订阅数
        List<CovOverviewLevel2> orderByPolice = threePartyInterfaceMapper.orderByPolice();
        //  各警种调用总数
        List<CovOverviewLevel2> dyByPolice = threePartyInterfaceMapper.dyByPolice();
        // 各警钟昨日新增调用数
        List<CovOverviewLevel2> ydByPolice = threePartyInterfaceMapper.ydByPolice(getYesterdayDate());

        orderByPolice.forEach(data -> {
            Optional<CovOverviewLevel2> optional = dyByPolice.stream().filter(item -> item.getName().equals(data.getName())).findFirst();
            Optional<CovOverviewLevel2> optional1 = ydByPolice.stream().filter(item -> item.getName().equals(data.getName())).findFirst();
            if (optional.isPresent()) {
                CovOverviewLevel2 covOverviewLevel2 = optional.get();
                data.setDyCount(covOverviewLevel2.getDyCount());
            } else {
                data.setDyCount(0);
            }
            if (optional1.isPresent()) {
                CovOverviewLevel2 covOverviewLevel2 = optional1.get();
                data.setYdCount(covOverviewLevel2.getYdCount());
            } else {
                data.setYdCount(0);
            }
        });
        return orderByPolice;
    }

    @Override
    public List<CovOverviewLevel2> areaDataLevel2() {
        //  各地市订阅数
        List<CovOverviewLevel2> orderByArea = threePartyInterfaceMapper.orderByArea();
        //  各地市调用总数
        List<CovOverviewLevel2> dyByArea = threePartyInterfaceMapper.dyByArea();
        // 各地市昨日调用新增
        List<CovOverviewLevel2> ydByArea = threePartyInterfaceMapper.ydByArea(getYesterdayDate());
        orderByArea.forEach(data -> {
            Optional<CovOverviewLevel2> optional = dyByArea.stream().filter(item -> item.getName().equals(data.getName())).findFirst();
            Optional<CovOverviewLevel2> optional1 = ydByArea.stream().filter(item -> item.getName().equals(data.getName())).findFirst();
            if (optional.isPresent()) {
                CovOverviewLevel2 covOverviewLevel2 = optional.get();
                data.setDyCount(covOverviewLevel2.getDyCount());
            } else {
                data.setDyCount(0);
            }
            if (optional1.isPresent()) {
                CovOverviewLevel2 covOverviewLevel2 = optional1.get();
                data.setYdCount(covOverviewLevel2.getYdCount());
            } else {
                data.setYdCount(0);
            }
        });
        return orderByArea;
    }

    @Override
    public IPage<CovStatistic> policeStatistic(String policeName, Integer pageNo, Integer pageSize) {
        Integer before = pageSize * (pageNo - 1);
        Integer afert = pageSize * pageNo;
        IPage<CovStatistic> page = new Page<>(pageNo, pageSize);
        List<CovStatistic> covStatistics = threePartyInterfaceMapper.policeStatistic(policeName, before, afert);
        page.setTotal(threePartyInterfaceMapper.policeTotal(policeName));
        page.setRecords(covStatistics);
        return page;
    }

    @Override
    public IPage<CovStatistic> areaStatistic(String areaName, Integer pageNo, Integer pageSize) {
        Integer before = pageSize * (pageNo - 1);
        Integer afert = pageSize * pageNo;
        IPage<CovStatistic> page = new Page<>(pageNo, pageSize);
        List<CovStatistic> covStatistics = threePartyInterfaceMapper.areaStatistic(areaName, before, afert);
        page.setTotal(threePartyInterfaceMapper.areaTotal(areaName));
        page.setRecords(covStatistics);
        return page;
    }

    @Override
    public IPage<CovOrderDetail> policeOrderDetail(String policeName, Integer pageNo, Integer pageSize) {
        Integer before = pageSize * (pageNo - 1);
        Integer afert = pageSize * pageNo;
        IPage<CovOrderDetail> page = new Page<>(pageNo, pageSize);
        List<CovOrderDetail> covOrderDetails = threePartyInterfaceMapper.policeOrderDetail(policeName, before, afert);
        covOrderDetails.forEach(data -> {
            Integer dyCount = threePartyInterfaceMapper.lineDyCount(data.getInstanceId(), data.getServiceId());
            if (dyCount == null) {
                dyCount = 0;
            }
            data.setReq(dyCount);
        });
        page.setTotal(threePartyInterfaceMapper.policeOrderTotal(policeName));
        page.setRecords(covOrderDetails);
        return page;
    }

    @Override
    public IPage<CovOrderDetail> areaOrderDetail(String areaName, Integer pageNo, Integer pageSize) {
        Integer before = pageSize * (pageNo - 1);
        Integer afert = pageSize * pageNo;
        IPage<CovOrderDetail> page = new Page<>(pageNo, pageSize);
        List<CovOrderDetail> covOrderDetails = threePartyInterfaceMapper.areaOrderDetail(areaName, before, afert);
        covOrderDetails.forEach(data -> {
            Integer dyCount = threePartyInterfaceMapper.lineDyCount(data.getInstanceId(), data.getServiceId());
            if (dyCount == null) {
                dyCount = 0;
            }
            data.setReq(dyCount);
        });
        page.setTotal(threePartyInterfaceMapper.areaOrderTotal(areaName));
        page.setRecords(covOrderDetails);
        return page;
    }

    @Override
    public CovOverview unitOverview() throws Exception {
        List<List<Object>> list = UnitExcelExportUtil.list("省直疫情数据服务调用统计.xls");
        CovOverview covOverview = covOverview(list);
        return covOverview;
    }

    @Override
    public List<EpidemicDesktop> epidemicExcl() throws Exception {
        List<List<Object>> list = UnitExcelExportUtil.list("疫情桌面云.xls");
        List<EpidemicDesktop> epidemicDesktops = new ArrayList<>();
        for (List<Object> itemlist : list) {
            EpidemicDesktop epidemicDesktop = new EpidemicDesktop();
            epidemicDesktop.setUnit((String) itemlist.get(0));
            epidemicDesktop.setYunCount((String) itemlist.get(1));
            epidemicDesktop.setCpuCount((String) itemlist.get(2));
            epidemicDesktop.setRamCount((String) itemlist.get(3));
            epidemicDesktop.setDistCount((String) itemlist.get(4));
            epidemicDesktop.setPolice((String) itemlist.get(5));
            epidemicDesktop.setArea((String) itemlist.get(6));
            epidemicDesktops.add(epidemicDesktop);
        }
        return epidemicDesktops;
    }

    @Override
    public EpidemicDeskIssue epidemicDeskIssue(List<EpidemicDesktop> epidemicDesktops) {
        EpidemicDeskIssue epidemicDeskIssue = new EpidemicDeskIssue();
        List<EpidemicDeskIssue.AreaBean> areaBeans = new ArrayList<>();
        List<EpidemicDeskIssue.PoliceBean> policeBeans = new ArrayList<>();
        for (EpidemicDesktop item : epidemicDesktops) {
            if (StringUtils.isNotEmpty(item.getArea())) {
                EpidemicDeskIssue.AreaBean areaBean = new EpidemicDeskIssue.AreaBean();
                areaBean.setAreaCount(Integer.valueOf(item.getYunCount()));
                areaBean.setAreaName(item.getArea());
                areaBeans.add(areaBean);
            }
            if (StringUtils.isNotEmpty(item.getPolice())) {
                EpidemicDeskIssue.PoliceBean policeBean = new EpidemicDeskIssue.PoliceBean();
                policeBean.setPoliceCount(Integer.valueOf(item.getYunCount()));
                policeBean.setPoliceName(item.getPolice());
                policeBeans.add(policeBean);
            }
        }
        epidemicDeskIssue.setArea(areaBeans);
        epidemicDeskIssue.setPolice(policeBeans);
        return epidemicDeskIssue;
    }

    @Override
    public DeskTopNum epidemicExclNum(List<EpidemicDesktop> epidemicDesktops) throws Exception {
        int yunCount = 0;
        int areaCount = 0;
        int policeCount = 0;
        int cpuCount = 0;
        int ramCount = 0;
        double diskCount = 0;
        for (EpidemicDesktop item : epidemicDesktops) {
            yunCount += Integer.valueOf(item.getYunCount());
            if (StringUtils.isNotEmpty(item.getArea())) {
                areaCount++;
            }
            if (StringUtils.isNotEmpty(item.getPolice())) {
                policeCount++;
            }
            cpuCount += Integer.valueOf(item.getCpuCount());
            ramCount += Integer.valueOf(item.getRamCount());
            diskCount += Double.valueOf(item.getDistCount());
        }
        DeskTopNum deskTopNum = new DeskTopNum();
        deskTopNum.setYunCount(yunCount);
        deskTopNum.setAreaCount(areaCount);
        deskTopNum.setPoliceCount(policeCount);
        deskTopNum.setCpuCount(cpuCount);
        deskTopNum.setRamCount(ramCount);
        deskTopNum.setDiskCount(diskCount);
        return deskTopNum;
    }

    @Override
    public List<CovOverviewLevel2> unitOverviewLevel2() throws Exception {
        List<List<Object>> list = UnitExcelExportUtil.list("省直疫情数据服务调用统计.xls");
        List<CovOverviewLevel2> covOverviewLevel2s = unitOverviewLevel2(list);
        //单条按照调用总次数排序
        List<CovOverviewLevel2> collect = covOverviewLevel2s.stream().sorted(Comparator.comparingInt(CovOverviewLevel2::getDyCount).reversed()).collect(Collectors.toList());
        return collect;
    }

    @Override
    public IPage<DirectUnitStatistics> unitStatistic(String unitName, Integer pageNo, Integer pageSize) throws Exception {
        List<List<Object>> list = UnitExcelExportUtil.list("省直疫情数据服务调用统计.xls");
        List<DirectUnitStatistics> directUnitStatistics = unitStatistic(unitName, list);
        IPage<DirectUnitStatistics> unitStatisticsPage = new Page<>(pageNo, pageSize);
        int before = pageSize * (pageNo - 1);
        if (before > directUnitStatistics.size()) {
            unitStatisticsPage.setRecords(new ArrayList<>());
            unitStatisticsPage.setTotal(directUnitStatistics.size());
            return unitStatisticsPage;
        }
        int after = pageSize * pageNo;
        if (after > directUnitStatistics.size()) {
            after = directUnitStatistics.size();
        }
        List<DirectUnitStatistics> statistics = directUnitStatistics.subList(before, after);
        unitStatisticsPage.setTotal(directUnitStatistics.size());
        unitStatisticsPage.setRecords(statistics);
        return unitStatisticsPage;
    }

    @Override
    public IPage<DirectUnitOrderDetail> unitOrderDetail(String unitName, Integer pageNo, Integer pageSize) throws Exception {
        List<List<Object>> list = UnitExcelExportUtil.list("省直疫情数据服务调用统计.xls");
        List<DirectUnitOrderDetail> unitOrderDetails = unitOrderDetail(unitName, list);
        IPage<DirectUnitOrderDetail> unitOrderDetailPage = new Page<>(pageNo, pageSize);
        int before = pageSize * (pageNo - 1);
        if (before > unitOrderDetails.size()) {
            unitOrderDetailPage.setRecords(new ArrayList<>());
            unitOrderDetailPage.setTotal(unitOrderDetails.size());
            return unitOrderDetailPage;
        }
        int after = pageSize * pageNo;
        if (after > unitOrderDetails.size()) {
            after = unitOrderDetails.size();
        }
        List<DirectUnitOrderDetail> details = unitOrderDetails.subList(before, after);
        unitOrderDetailPage.setTotal(unitOrderDetails.size());
        unitOrderDetailPage.setRecords(details);
        return unitOrderDetailPage;
    }

    /**
     * 省直调用详情展示逻辑
     *
     * @param unitName
     * @param list
     * @return
     */
    private List<DirectUnitStatistics> unitStatistic(String unitName, List<List<Object>> list) {
        Integer number = 1;
        List<DirectUnitStatistics> unitStatisticsList = new ArrayList<>();
        for (List<Object> data : list) {
            Object object = data.get(2);
            if (!unitName.equals(object)) {
                continue;
            } else {
                String area = (String) data.get(1);
                String messageName = (String) data.get(3);
                String serviceName = (String) data.get(4);
                Integer row = 7;
                while (data.size() > row) {
                    Integer timeRow = row++;
                    Integer countRow = row++;
                    String time = (String) data.get(timeRow);
                    Object object1 = data.get(countRow);
                    if (time == null || object == null) {
                        continue;
                    }
                    Integer count = Integer.valueOf((String) object1);
                    if (count == 0) {
                        continue;
                    }
                    DirectUnitStatistics directUnitStatistics = new DirectUnitStatistics();
                    directUnitStatistics.setNum(number);
                    directUnitStatistics.setArea(area);
                    directUnitStatistics.setUnitName(unitName);
                    directUnitStatistics.setMessageName(messageName);
                    directUnitStatistics.setServiceName(serviceName);
                    directUnitStatistics.setTime(time);
                    directUnitStatistics.setCount(count);
                    unitStatisticsList.add(directUnitStatistics);
                    number++;
                }
            }
        }
        return unitStatisticsList;
    }

    /**
     * 省直订阅详情展示逻辑
     *
     * @param unitName
     * @param list
     * @return
     */
    private List<DirectUnitOrderDetail> unitOrderDetail(String unitName, List<List<Object>> list) {
        Integer number = 1;
        List<DirectUnitOrderDetail> unitOrderDetails = new ArrayList<>();
        for (List<Object> data : list) {
            Object object = data.get(2);
            if (!unitName.equals(object)) {
                continue;
            } else {
                String area = (String) data.get(1);
                String messageName = (String) data.get(3);
                String serviceName = (String) data.get(4);
                Integer countAll = 0;
                if (data.get(5) != null) {
                    countAll = Integer.valueOf((String) data.get(5));
                }
                String remark = (String) data.get(6);
                DirectUnitOrderDetail unitOrderDetail = new DirectUnitOrderDetail();
                unitOrderDetail.setNum(number);
                unitOrderDetail.setArea(area);
                unitOrderDetail.setUnitName(unitName);
                unitOrderDetail.setMessageName(messageName);
                unitOrderDetail.setServiceName(serviceName);
                unitOrderDetail.setRemark(remark);
                unitOrderDetail.setCountAll(countAll);
                unitOrderDetails.add(unitOrderDetail);
                number++;
            }
        }
        return unitOrderDetails;
    }

    /**
     * 省直各单位订阅及调用总次数数据统计逻辑
     *
     * @param list
     * @return
     */
    private List<CovOverviewLevel2> unitOverviewLevel2(List<List<Object>> list) {
        List<CovOverviewLevel2> covOverviewLevel2s = new ArrayList<>();
        Map<String, Integer> dyMap = new HashMap<>();
        Map<String, Integer> orderMap = new HashMap<>();
        Map<String, Integer> ydMap = new HashMap<>();
        for (List<Object> data : list) {
            String ydate = getYesterdayDate();
            String unitName = (String) data.get(2);
            Object object = data.get(5);
            Object objYd = data.get(data.size() - 1);
            Integer count = 0;
            Integer ycount = 0;
            if (object != null && !"".equals(object)) {
                count = Integer.valueOf((String) object);
            }
            if (objYd != null && !"".equals(objYd)) {
                ycount = Integer.valueOf((String) objYd);
            }

            if (ydMap.get(unitName) != null) {
                if (ycount != null && ycount != 0) {
                    if (ydate.equals(data.get(data.size() - 2))) {
                        Integer ydSum = ydMap.get(unitName) + ycount;
                        ydMap.put(unitName, ydSum);
                    }
                }
            } else {
                if (ycount != null) {
                    if (ydate.equals(data.get(data.size() - 2))) {
                        ydMap.put(unitName, ycount);
                    } else {
                        ydMap.put(unitName, 0);
                    }
                }
            }

            if (dyMap.get(unitName) != null) {
                if (count != null && count != 0) {
                    Integer dySum = dyMap.get(unitName) + count;
                    dyMap.put(unitName, dySum);
                }
            } else {
                if (count != null) {
                    dyMap.put(unitName, count);
                }
            }
            if (orderMap.get(unitName) != null) {
                Integer orderSum = orderMap.get(unitName) + 1;
                orderMap.put(unitName, orderSum);
            } else {
                orderMap.put(unitName, 1);
            }
        }
        Set<String> set = dyMap.keySet();
        for (String name : set) {
            CovOverviewLevel2 covOverviewLevel2 = new CovOverviewLevel2();
            covOverviewLevel2.setName(name);
            covOverviewLevel2.setOrderCount(orderMap.get(name));
            covOverviewLevel2.setDyCount(dyMap.get(name));
            covOverviewLevel2.setYdCount(ydMap.get(name));
            covOverviewLevel2s.add(covOverviewLevel2);
        }
        return covOverviewLevel2s;
    }

    /**
     * 省直单位数，总订阅数及总调用数数据统计逻辑
     *
     * @param list
     * @return
     */
    private CovOverview covOverview(List<List<Object>> list) {
        //  单位集合，去重
        Set<String> unit = new HashSet<>();
        //  调用总数
        Integer countAll = 0;
        CovOverview covOverview = new CovOverview();
        for (List<Object> data : list) {
            String unitName = (String) data.get(2);
            Object object = data.get(5);
            Integer count = 0;
            if (object != null && !"".equals(object)) {
                count = Integer.valueOf((String) object);
            }
            unit.add(unitName);
            if (countAll != null) {
                countAll = countAll + count;
            }
        }
        //获取昨天日期
        String ydate = getYesterdayDate();
        Integer yConunt = 0;
        for (List<Object> item : list) {
            Object yDate = item.get(item.size() - 2);
            if (ydate.equals(yDate)) {
                Object o = item.get(item.size() - 1);
                yConunt = yConunt + Integer.valueOf((String) o);
            }
        }
        covOverview.setOrderCount(list.size());
        covOverview.setCount(unit.size());
        covOverview.setDyCount(countAll);
        covOverview.setYdCount(yConunt);
        return covOverview;
    }

    private String getYesterdayDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String ydate = simpleDateFormat.format(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24));
        return ydate;
    }
}
