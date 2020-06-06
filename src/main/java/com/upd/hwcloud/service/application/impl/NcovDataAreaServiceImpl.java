package com.upd.hwcloud.service.application.impl;

import com.google.common.collect.Lists;
import com.upd.hwcloud.bean.dto.cov.*;
import com.upd.hwcloud.common.utils.easypoi.NcovEcsImportUtil;
import com.upd.hwcloud.common.utils.ncov.UnitExcelExportUtil;
import com.upd.hwcloud.dao.application.NcovDataAreaMapper;
import com.upd.hwcloud.service.application.NcovDataAreaService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author junglefisher
 * @date 2020/5/5 10:46
 */
@Service
public class NcovDataAreaServiceImpl implements NcovDataAreaService {

    @Value("${ncov.datasharing.name}")
    private String dataSharingName;
    @Value("${ncov.datamodeling.name}")
    private String dataModelingName;
    @Value("${ncov.datagovernance.name}")
    private String dataGovernanceName;
    @Value("${ncov.dataaccess.name}")
    private String dataAccessName;
    @Value("${ncov.dataservice.name}")
    private String dataServiceName;

    private static String rootPath;

    @Value("${file.path}")
    public void setRootPath(String rootPath) {
        NcovDataAreaServiceImpl.rootPath = rootPath;
    }

    @Resource
    private NcovDataAreaMapper ncovDataAreaMapper;

    @Override
    public DataSharing dataSharingOverview() throws Exception {
        DataSharing dataSharing =new DataSharing();
        /**
         * 总览数据
         */
        List<List<Object>> overview = UnitExcelExportUtil.ncovDataList(dataSharingName,1,0);
        List<NcovDataOverviewDto> ncovDataOverviewDtos = Lists.newArrayList();
        for (List<Object> objects : overview) {
            NcovDataOverviewDto ncovDataOverviewDto = new NcovDataOverviewDto();
            ncovDataOverviewDto.setName((String) objects.get(0));
            ncovDataOverviewDto.setCount((String) objects.get(1));
            ncovDataOverviewDto.setUnit((String) objects.get(2));
            ncovDataOverviewDtos.add(ncovDataOverviewDto);
        }
        dataSharing.setDataSharingOverview(ncovDataOverviewDtos);
        /**
         * 单位下载
         */
        List<List<Object>> unitDownload = UnitExcelExportUtil.ncovDataList(dataSharingName,1,1);
        List<NcovDataDto> ncovDataDtos1 = Lists.newArrayList();
        for (List<Object> objects : unitDownload) {
            NcovDataDto ncovDataDto = new NcovDataDto();
            ncovDataDto.setName((String) objects.get(1));
            ncovDataDto.setCount((String) objects.get(2));
            ncovDataDtos1.add(ncovDataDto);
        }
        Collections.sort(ncovDataDtos1);
        dataSharing.setUnitDownload(ncovDataDtos1.subList(0,10));
        /**
         * 高频使用资源
         */
        List<List<Object>> highFrequencyUse = UnitExcelExportUtil.ncovDataList(dataSharingName,1,2);
        List<NcovDataDto> ncovDataDtos2 = Lists.newArrayList();
        for (List<Object> objects : highFrequencyUse) {
            NcovDataDto ncovDataDto = new NcovDataDto();
            ncovDataDto.setName((String) objects.get(1));
            ncovDataDto.setCount((String) objects.get(2));
            ncovDataDtos2.add(ncovDataDto);
        }
        Collections.sort(ncovDataDtos2);
        dataSharing.setHighFrequencyUse(ncovDataDtos2.subList(0,10));
        return dataSharing;
    }

    @Override
    public List<UnitDownloadExport> unitDownloadExport() throws Exception {
        List<List<Object>> unitDownload = UnitExcelExportUtil.ncovDataList(dataSharingName,1,1);
        List<UnitDownloadExport> unitDownloadExports = Lists.newArrayList();
        int i = 1;
        for (List<Object> objects : unitDownload) {
            UnitDownloadExport unitDownloadExport = new UnitDownloadExport();
            unitDownloadExport.setNum(i++);
            unitDownloadExport.setName((String) objects.get(1));
            unitDownloadExport.setCount((String) objects.get(2));
            unitDownloadExports.add(unitDownloadExport);
        }
        return unitDownloadExports;
    }

    @Override
    public List<HighFrequencyUseExport> highFrequencyUseExport() throws Exception {
        List<List<Object>> highFrequencyUse = UnitExcelExportUtil.ncovDataList(dataSharingName,1,2);
        List<HighFrequencyUseExport> highFrequencyUseExports = Lists.newArrayList();
        int i = 1;
        for (List<Object> objects : highFrequencyUse) {
            HighFrequencyUseExport highFrequencyUseExport = new HighFrequencyUseExport();
            highFrequencyUseExport.setNum(i++);
            highFrequencyUseExport.setName((String) objects.get(1));
            highFrequencyUseExport.setCount((String) objects.get(2));
            highFrequencyUseExports.add(highFrequencyUseExport);
        }
        return highFrequencyUseExports;
    }

    @Override
    public DataModeling dataModelingOverview() throws Exception {
        DataModeling dataModeling =new DataModeling();
        /**
         * 总览数据
         */
        List<List<Object>> overview = UnitExcelExportUtil.ncovDataList(dataModelingName,1,0);
        List<NcovDataOverviewDto> ncovDataOverviewDtos = Lists.newArrayList();
        for (List<Object> objects : overview) {
            NcovDataOverviewDto ncovDataOverviewDto = new NcovDataOverviewDto();
            ncovDataOverviewDto.setName((String) objects.get(0));
            ncovDataOverviewDto.setCount((String) objects.get(1));
            ncovDataOverviewDto.setUnit((String) objects.get(2));
            ncovDataOverviewDtos.add(ncovDataOverviewDto);
        }
        dataModeling.setDataModelingOverview(ncovDataOverviewDtos);
        /**
         * 单位建模排名
         */
        List<List<Object>> unitModeling = UnitExcelExportUtil.ncovDataList(dataModelingName,1,1);
        List<NcovDataDto> ncovDataDtos1 = Lists.newArrayList();
        for (List<Object> objects : unitModeling) {
            NcovDataDto ncovDataDto = new NcovDataDto();
            ncovDataDto.setName((String) objects.get(1));
            ncovDataDto.setCount((String) objects.get(2));
            ncovDataDtos1.add(ncovDataDto);
        }
        Collections.sort(ncovDataDtos1);
        dataModeling.setUnitModeling(ncovDataDtos1.subList(0,10));
        /**
         * 公共模型建设单位排名
         */
        List<List<Object>> publicModelConstructionUnit = UnitExcelExportUtil.ncovDataList(dataModelingName,1,2);
        List<NcovDataDto> ncovDataDtos2 = Lists.newArrayList();
        for (List<Object> objects : publicModelConstructionUnit) {
            NcovDataDto ncovDataDto = new NcovDataDto();
            ncovDataDto.setName((String) objects.get(1));
            ncovDataDto.setCount((String) objects.get(2));
            ncovDataDtos2.add(ncovDataDto);
        }
        Collections.sort(ncovDataDtos2);
        dataModeling.setPublicModelConstructionUnit(ncovDataDtos2.subList(0,10));
        /**
         * 模型热度排名
         */
        List<List<Object>> modelPopularity = UnitExcelExportUtil.ncovDataList(dataModelingName,1,3);
        List<NcovDataDto> ncovDataDtos3 = Lists.newArrayList();
        for (List<Object> objects : modelPopularity) {
            NcovDataDto ncovDataDto = new NcovDataDto();
            ncovDataDto.setName((String) objects.get(1));
            ncovDataDto.setCount((String) objects.get(2));
            ncovDataDtos3.add(ncovDataDto);
        }
        Collections.sort(ncovDataDtos3);
        dataModeling.setModelPopularity(ncovDataDtos3.subList(0,10));
        return dataModeling;
    }

    @Override
    public List<UnitModelingExport> unitModelingExport() throws Exception {
        List<List<Object>> unitModeling = UnitExcelExportUtil.ncovDataList(dataModelingName,1,1);
        List<UnitModelingExport> unitModelingExports = Lists.newArrayList();
        int i = 1;
        for (List<Object> objects : unitModeling) {
            UnitModelingExport unitModelingExport = new UnitModelingExport();
            unitModelingExport.setNum(i++);
            unitModelingExport.setName((String) objects.get(1));
            unitModelingExport.setCount((String) objects.get(2));
            unitModelingExports.add(unitModelingExport);
        }
        return unitModelingExports;
    }

    @Override
    public List<UnitModelingExport> publicModelConstructionUnitExport() throws Exception {
        List<List<Object>> unitModeling = UnitExcelExportUtil.ncovDataList(dataModelingName,1,2);
        List<UnitModelingExport> unitModelingExports = Lists.newArrayList();
        int i = 1;
        for (List<Object> objects : unitModeling) {
            UnitModelingExport unitModelingExport = new UnitModelingExport();
            unitModelingExport.setNum(i++);
            unitModelingExport.setName((String) objects.get(1));
            unitModelingExport.setCount((String) objects.get(2));
            unitModelingExports.add(unitModelingExport);
        }
        return unitModelingExports;
    }

    @Override
    public List<ModelPopularityExport> modelPopularityExport() throws Exception {
        List<List<Object>> unitModeling = UnitExcelExportUtil.ncovDataList(dataModelingName,1,3);
        List<ModelPopularityExport> modelPopularityExports = Lists.newArrayList();
        int i = 1;
        for (List<Object> objects : unitModeling) {
            ModelPopularityExport modelPopularityExport = new ModelPopularityExport();
            modelPopularityExport.setNum(i++);
            modelPopularityExport.setName((String) objects.get(1));
            modelPopularityExport.setCount((String) objects.get(2));
            modelPopularityExports.add(modelPopularityExport);
        }
        return modelPopularityExports;
    }

    @Override
    public void uploadFile(MultipartFile file,String name) throws IOException{
        backUpFile(name);
        File targetFile = new File(rootPath+"/ncovArea/" + name);
        targetFile.exists();
        FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
        InputStream inputStream = file.getInputStream();
        IOUtils.copy(inputStream, fileOutputStream);
        IOUtils.closeQuietly(inputStream);
        IOUtils.closeQuietly(fileOutputStream);
    }

    private void backUpFile(String name) throws IOException {
        File file = new File(rootPath+"/ncovArea/" + name);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String format = simpleDateFormat.format(date);
        File backUpFile = new File(rootPath+"/ncovArea/backUp/"+format+"-" + name);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        FileInputStream inputStream = new FileInputStream(backUpFile);
        IOUtils.copy(inputStream, fileOutputStream);
        IOUtils.closeQuietly(inputStream);
        IOUtils.closeQuietly(fileOutputStream);
    }


    @Override
    public DataGovernance dataGovernance() throws Exception {
        DataGovernance dataGovernance=new DataGovernance();
        List<List<Object>> tableName = UnitExcelExportUtil.ncovDataList(dataGovernanceName,1,0);
        Set<String> unitSet = new HashSet();
        for (List<Object> list : tableName) {
            String unitName = (String) list.get(1);
            unitSet.add(unitName);
        }
        dataGovernance.setNames(unitSet);
        List<List<Object>> dataSummary = UnitExcelExportUtil.ncovDataList(dataGovernanceName,1,1);
        List<NcovDataDto> ncovDataDtos = Lists.newArrayList();
        for (List<Object> objects : dataSummary) {
            NcovDataDto ncovDataDto = new NcovDataDto();
            ncovDataDto.setName((String) objects.get(1));
            ncovDataDto.setCount((String) objects.get(2));
            ncovDataDtos.add(ncovDataDto);
        }
        dataGovernance.setDataSummary(ncovDataDtos);
        List<DataGovernanceLevel2> updateTypeDtos = Lists.newArrayList();
        List<List<Object>> updateType = UnitExcelExportUtil.ncovDataList(dataGovernanceName,1,2);
        for (List<Object> objects : updateType) {
            DataGovernanceLevel2 dataGovernanceLevel2 = new DataGovernanceLevel2();
            dataGovernanceLevel2.setType((String) objects.get(1));
            dataGovernanceLevel2.setNum((String) objects.get(2));
            dataGovernanceLevel2.setSum((String) objects.get(3));
            dataGovernanceLevel2.setPercentage((String) objects.get(4));
            updateTypeDtos.add(dataGovernanceLevel2);
        }
        dataGovernance.setUpdateType(updateTypeDtos);
        List<List<Object>> updateCycle = UnitExcelExportUtil.ncovDataList(dataGovernanceName,1,3);
        List<DataGovernanceLevel2> updateCycleDtos = Lists.newArrayList();
        for (List<Object> objects : updateCycle) {
            DataGovernanceLevel2 dataGovernanceLevel2 = new DataGovernanceLevel2();
            dataGovernanceLevel2.setType((String) objects.get(1));
            dataGovernanceLevel2.setNum((String) objects.get(2));
            dataGovernanceLevel2.setSum((String) objects.get(3));
            dataGovernanceLevel2.setPercentage((String) objects.get(4));
            updateCycleDtos.add(dataGovernanceLevel2);
        }
        dataGovernance.setUpdateCycle(updateCycleDtos);
        return dataGovernance;
    }

    @Override
    public List<NameAndFileName> getTableNameByUnit(String unitName) throws Exception {
        List<List<Object>> tableName = UnitExcelExportUtil.ncovDataList(dataGovernanceName,1,0);
        List<NameAndFileName> names = Lists.newArrayList();
        for (List<Object> list : tableName) {
            if (unitName.equals(list.get(1))){
                NameAndFileName nameAndFileName = new NameAndFileName();
                nameAndFileName.setName((String) list.get(4));
                nameAndFileName.setFileName((String) list.get(7));
                names.add(nameAndFileName);
            }
        }
        return names;
    }

    @Override
    public void downloadFile(HttpServletResponse response,String tableName) throws IOException {
        downFile(response, tableName);
    }

    @Override
    public DataAccess dataAccessOverview() throws Exception {
        DataAccess dataAccess = new DataAccess();
        List<List<Object>> overview = UnitExcelExportUtil.ncovDataList(dataAccessName,1,0);
        Long total = 0L;
        Long yesterday = 0L;
        List<Long> latelyFocus = Lists.newArrayList(0L,0L,0L,0L,0L,0L,0L);
        List<Long> lately = Lists.newArrayList(0L,0L,0L,0L,0L,0L,0L);
        Map<String,Boolean> focusMap = new HashMap<>();
        for (List<Object> list : overview) {
            total += Long.valueOf((String)list.get(5));
            yesterday += Long.valueOf((String)list.get(6));
            String ifFocus = (String)list.get(7);
            if ("是".equals(ifFocus)){
                focusMap.put((String)list.get(3),true);
            }
        }
        dataAccess.setTotalCount(total);
        dataAccess.setResourceCount(overview.size());
        dataAccess.setYesterdayCount(yesterday);
        List<List<Object>> policeData = UnitExcelExportUtil.ncovDataList(dataAccessName,1,2);
        Map<String,Map<String,Long>> typeMap = new HashMap<>();
        Set<String> fromPolice = new HashSet<>();
        for (List<Object> objects : policeData) {
            String name = (String) objects.get(1);
            fromPolice.add(name);
            String type = (String) objects.get(4);
            Long num = Long.valueOf((String)objects.get(6));
            Long yesterdayByType = Long.valueOf((String)objects.get(13));
            Map<String,Long> map = typeMap.get(type);
            if (map==null){
                Map<String,Long> countMap = new HashMap<>();
                countMap.put("总数", num);
                countMap.put("公安", num);
                countMap.put("政府", 0L);
                countMap.put("昨日增量", yesterdayByType);
                typeMap.put(type,countMap);
            }else {
                Long all = map.get("总数");
                map.put("总数", all+num);
                Long police = map.get("公安");
                map.put("公安", police+num);
                Long yesterdayOld = map.get("昨日增量");
                map.put("昨日增量",yesterdayByType+yesterdayOld);
                typeMap.put(type,map);
            }
            for (int i = 0; i < 7; i++) {
                Long day = lately.get(i);
                lately.set(i,day+Long.valueOf((String)objects.get(i+7)));
            }
            String tableName = (String)objects.get(3);
            if (focusMap.get(tableName)!=null){
                for (int i = 0; i < 7; i++) {
                    Long day = latelyFocus.get(i);
                    latelyFocus.set(i,day+Long.valueOf((String)objects.get(i+7)));
                }
            }
        }
        dataAccess.setPoliceCount(fromPolice.size());
        List<List<Object>> govData = UnitExcelExportUtil.ncovDataList(dataAccessName,1,1);
        Set<String> fromGov = new HashSet<>();
        Map<String,Long> govDataMap = new HashMap<>();
        for (List<Object> objects : govData) {
            String name = (String) objects.get(1);
            fromGov.add(name);
            String type = (String) objects.get(4);
            Long num = Long.valueOf((String)objects.get(6));
            Long yesterdayByType = Long.valueOf((String)objects.get(13));
            Map<String,Long> map = typeMap.get(type);
            if (map==null){
                Map<String,Long> countMap = new HashMap<>();
                countMap.put("总数", num);
                countMap.put("政府", num);
                countMap.put("公安", 0L);
                countMap.put("昨日增量", yesterdayByType);
                typeMap.put(type,countMap);
            }else {
                Long all = map.get("总数");
                map.put("总数", all+num);
                Long gov = map.get("政府");
                map.put("政府", gov+num);
                Long yesterdayOld = map.get("昨日增量");
                map.put("昨日增量",yesterdayByType+yesterdayOld);
                typeMap.put(type,map);
            }
            for (int i = 0; i < 7; i++) {
                Long day = lately.get(i);
                lately.set(i,day+Long.valueOf((String)objects.get(i+7)));
            }
            String tableName = (String)objects.get(3);
            if (focusMap.get(tableName)!=null){
                for (int i = 0; i < 7; i++) {
                    Long day = latelyFocus.get(i);
                    latelyFocus.set(i,day+Long.valueOf((String)objects.get(i+7)));
                }
            }
            String unitName = (String) objects.get(1);
            Long aLong = govDataMap.get(unitName);
            if (aLong!=null){
                govDataMap.put(unitName,num+aLong);
            }else {
                govDataMap.put(unitName,num);
            }
            dataAccess.setGovData(mapSort(govDataMap));
        }
        dataAccess.setLately7Days(lately);
        dataAccess.setLatelyFocus(latelyFocus);
        dataAccess.setTime(getLatelyDaysTime());
        dataAccess.setGovermentCount(fromGov.size());
        dataAccess.setTypeMap(typeMap);
        return dataAccess;
    }

    @Override
    public Map<String,Map<String,Long>> dataTypeLevel2(String type, String from) throws Exception {
        Map<String,Map<String,Long>> map = new HashMap<>();
        List<List<Object>> data;
        if ("公安".equals(from)){
            data = UnitExcelExportUtil.ncovDataList(dataAccessName,1,2);
        }else {
            data = UnitExcelExportUtil.ncovDataList(dataAccessName,1,1);
        }
        for (List<Object> objects : data) {
            String typeName = (String) objects.get(4);
            if (type.equals(typeName)){
                String unitName = (String) objects.get(1);
                Long num = Long.valueOf((String)objects.get(6));
                Long yesterday = Long.valueOf((String)objects.get(13));
                Map<String, Long> longMap = map.get(unitName);
                if (longMap!=null){
                    longMap.put("条数",longMap.get("条数")+num);
                    longMap.put("表数",longMap.get("表数")+1);
                    longMap.put("昨日增量",longMap.get("昨日增量")+yesterday);
                    map.put(unitName,longMap);
                }else {
                    Map<String, Long> newMap = new HashMap<>();
                    newMap.put("条数",num);
                    newMap.put("表数",1L);
                    newMap.put("昨日增量",yesterday);
                    map.put(unitName,newMap);
                }
            }
        }
        return map;
    }

    @Override
    public List<DataAccessLevel3> dataAccessLevel3(String unitName,String from,String type) throws Exception {
        List<DataAccessLevel3> dataAccessLevel3s=Lists.newArrayList();
        List<List<Object>> data;
        if ("公安".equals(from)){
            data = UnitExcelExportUtil.ncovDataList(dataAccessName,1,2);
        }else {
            data = UnitExcelExportUtil.ncovDataList(dataAccessName,1,1);
        }
        int i = 1;
        for (List<Object> objects : data) {
            if (unitName.equals(objects.get(1))&&type.equals(objects.get(4))){
                DataAccessLevel3 dataAccessLevel3 = new DataAccessLevel3();
                String name = (String) objects.get(1);
                dataAccessLevel3.setUnitName(name);
                String tableName = (String) objects.get(2);
                dataAccessLevel3.setTableName(tableName);
                String type1 = (String) objects.get(4);
                dataAccessLevel3.setType(type1);
                Long count = Long.valueOf((String)objects.get(6));
                dataAccessLevel3.setCountAll(count);
                Long yesterday = Long.valueOf((String)objects.get(13));
                if (yesterday<0){
                    yesterday=0L;
                }
                dataAccessLevel3.setYesyerdayCount(yesterday);
                dataAccessLevel3.setNum(i++);
                dataAccessLevel3s.add(dataAccessLevel3);
            }
        }
        return dataAccessLevel3s;
    }

    @Override
    public DataService dataService() throws Exception {
        DataService dataService = new DataService();
        Integer serviceCount = ncovDataAreaMapper.serviceCount();
        dataService.setServiceCount(serviceCount);
        Integer policeCount = ncovDataAreaMapper.policeCount();
        Integer areaCount = ncovDataAreaMapper.areaCount();
        dataService.setPoliceCount(policeCount+areaCount);
        Long ncovPlatform = ncovDataAreaMapper.ncovPlatform();
        dataService.setGovCall(ncovPlatform);
        Long ncovServiceCall = ncovDataAreaMapper.ncovServiceCall();
        dataService.setPoliceCall(ncovServiceCall-ncovPlatform);
        String time = getTime(1);
        // String time = getTime(31);
        Long yesterdayCall = ncovDataAreaMapper.yesterdayCall(time);
        dataService.setYesterdayCall(yesterdayCall);
        List<CallAndNameDto> callByPolice = ncovDataAreaMapper.callByPolice();
        for (CallAndNameDto callAndNameDto : callByPolice) {
            String name = callAndNameDto.getName();
            if ("科信".equals(name)){
                callAndNameDto.setName("大数据办");
            }
        }
        List<CallAndNameDto> callByArea = ncovDataAreaMapper.callByArea();
        for (CallAndNameDto callAndNameDto : callByArea) {
            callAndNameDto.setName(callAndNameDto.getName()+"科信");
        }
        callByPolice.addAll(callByArea);
        Collections.sort(callByPolice);
        dataService.setPolice(callByPolice.subList(0,10));
        String last7Time = getTime(6);
        //String last7Time = getTime(36);
        List<CallAndTimeDto> callAll = ncovDataAreaMapper.callAll(last7Time);
        List<CallAndTimeDto> callByApp = ncovDataAreaMapper.callByApp(last7Time);
        dataService.setGovLately(callByApp);
        for (CallAndTimeDto callAndTimeDto : callAll) {
            Map<String, Long> collect = callByApp.stream().collect(Collectors.toMap(CallAndTimeDto::getTime, CallAndTimeDto::getCount));
            Long aLong = collect.get(callAndTimeDto.getTime());
            callAndTimeDto.setCount(callAndTimeDto.getCount()-aLong);
        }
        dataService.setPoliceLately(callAll);
        List<List<Object>> dataServiceArea = UnitExcelExportUtil.ncovDataList(dataServiceName,3,0);
        List<List<Object>> dataServiceGov = UnitExcelExportUtil.ncovDataList(dataServiceName,3,1);
        Set<String> set = new HashSet<>();
        Map<String,Long> areaMap = new HashMap<>();
        for (List<Object> objects : dataServiceArea) {
            String name = (String) objects.get(3);
            String call = (String) objects.get(6);
            if (call==null){
                call="0";
            }
            set.add(name);
            Long num = areaMap.get(name);
            if (num == null){
                areaMap.put(name,Long.valueOf(call));
            }else {
                areaMap.put(name,num+Long.valueOf(call));
            }
        }
        dataService.setCity(mapSort(areaMap));
        Map<String,Long> govDirectMap = new HashMap<>();
        for (List<Object> objects : dataServiceGov) {
            String name = (String) objects.get(3);
            String call = (String) objects.get(6);
            if (call==null){
                call="0";
            }
            set.add(name);
            Long num = govDirectMap.get(name);
            if (num == null){
                govDirectMap.put(name,Long.valueOf(call));
            }else {
                govDirectMap.put(name,num+Long.valueOf(call));
            }
        }
        dataService.setGovDirect(mapSort(govDirectMap));
        dataService.setGovCount(set.size());
        return dataService;
    }

    @Override
    public List<ServiceData> dataList() {
        //  查询所有疫情服务
        List<String> names = ncovDataAreaMapper.serviceAll();
        //  查询服务调用总数
        List<NcovDataLongDto> serviceCallAll = ncovDataAreaMapper.serviceCallAll();
        //  查询服务近七天调用数
        String last7Time = getTime(6);
        //String last7Time = getTime(36);
        List<NcovDataLongDto> serviceCallLately7Days = ncovDataAreaMapper.serviceCallLately7Days(last7Time);
        //  查询服务昨日调用
        String time = getTime(1);
        //String time = getTime(31);
        List<NcovDataLongDto> serviceCallYesterday = ncovDataAreaMapper.serviceCallYesterday(time);
        //  查询服务被订阅应用数
        List<NcovDataLongDto> serviceOrder = ncovDataAreaMapper.serviceOrder();
        List<ServiceData> serviceDataList =Lists.newArrayList();
        names.forEach(data ->{
            ServiceData serviceData = new ServiceData();
            serviceData.setName(data);
            //  是否有该服务的总调用次数
            Long callAll = findDataByName(serviceCallAll, data);
            serviceData.setCallAll(callAll);
            //  是否有该服务的近七天调用数
            Long lately7Call = findDataByName(serviceCallLately7Days, data);
            serviceData.setLately7Call(lately7Call);
            //  是否有该服务的昨日调用
            Long yesterdayCall = findDataByName(serviceCallYesterday, data);
            serviceData.setYesterdayCall(yesterdayCall);
            //  是否有该服务的被订阅应用数
            Long orderCount = findDataByName(serviceOrder, data);
            serviceData.setOrderCount(orderCount);
            serviceDataList.add(serviceData);
        });
        return serviceDataList;
    }

    @Override
    public List<PreventionSituation> preventionSituation(String from) throws Exception {
        List<PreventionSituation> preventionSituations = Lists.newArrayList();
        List<List<Object>> data;
        if ("省直".equals(from)){
            data = UnitExcelExportUtil.ncovDataList(dataServiceName,3,1);
        }else {
            data = UnitExcelExportUtil.ncovDataList(dataServiceName,3,0);
        }
        for (List<Object> objects : data) {
            PreventionSituation preventionSituation = new PreventionSituation();
            String type = (String) objects.get(1);
            preventionSituation.setType(type);
            String unitName = (String) objects.get(2);
            preventionSituation.setUnitName(unitName);
            String serviceName = (String) objects.get(5);
            preventionSituation.setServiceName(serviceName);
            String callAll = (String) objects.get(6);
            if (callAll==null){
                callAll = "0";
            }
            preventionSituation.setCallAll(callAll);
            String yesterdayCall = (String) objects.get(objects.size()-1);
            if (yesterdayCall==null){
                yesterdayCall = "0";
            }
            preventionSituation.setYesterdayCall(yesterdayCall);
            preventionSituations.add(preventionSituation);
        }
        return preventionSituations;
    }


    private Long findDataByName(List<NcovDataLongDto> ncovDataLongDtos,String name){
        Optional<NcovDataLongDto> optional = ncovDataLongDtos.stream().filter(item -> item.getName().equals(name)).findFirst();
        if (optional.isPresent()){
            NcovDataLongDto ncovDataLongDto = optional.get();
            return ncovDataLongDto.getCount();
        }else {
            return 0L;
        }
    }


    @Override
    public HomePageData homePage() throws Exception {
        HomePageData homePageData = new HomePageData();
        //  数据共享
        List<List<Object>> dataSharing = UnitExcelExportUtil.ncovDataList(dataSharingName,1,0);
        List<NcovDataOverviewDto> dataSharingDtos = Lists.newArrayList();
        for (List<Object> objects : dataSharing) {
            NcovDataOverviewDto ncovDataOverviewDto = new NcovDataOverviewDto();
            ncovDataOverviewDto.setName((String) objects.get(0));
            ncovDataOverviewDto.setCount((String) objects.get(1));
            ncovDataOverviewDto.setUnit((String) objects.get(2));
            dataSharingDtos.add(ncovDataOverviewDto);
        }
        homePageData.setDataSharingOverview(dataSharingDtos);
        //  数据建模
        List<List<Object>> dataModeling = UnitExcelExportUtil.ncovDataList(dataModelingName,1,0);
        List<NcovDataOverviewDto> dataModelingDtos = Lists.newArrayList();
        for (List<Object> objects : dataModeling) {
            NcovDataOverviewDto ncovDataOverviewDto = new NcovDataOverviewDto();
            ncovDataOverviewDto.setName((String) objects.get(0));
            ncovDataOverviewDto.setCount((String) objects.get(1));
            ncovDataOverviewDto.setUnit((String) objects.get(2));
            dataModelingDtos.add(ncovDataOverviewDto);
        }
        homePageData.setDataModelingOverview(dataModelingDtos);
        //  数据服务
        Integer serviceCount = ncovDataAreaMapper.serviceCount();
        homePageData.setServiceCount(serviceCount);
        Integer policeCount = ncovDataAreaMapper.policeCount();
        Integer areaCount = ncovDataAreaMapper.areaCount();
        List<List<Object>> dataServiceArea = UnitExcelExportUtil.ncovDataList(dataServiceName,3,0);
        List<List<Object>> dataServiceGov = UnitExcelExportUtil.ncovDataList(dataServiceName,3,1);
        Set<String> set = new HashSet<>();
        for (List<Object> objects : dataServiceArea) {
            String name = (String) objects.get(3);
            set.add(name);
        }
        for (List<Object> objects : dataServiceGov) {
            String name = (String) objects.get(3);
            set.add(name);
        }
        homePageData.setUnitCount(policeCount+areaCount+set.size());
        Long ncovServiceCall = ncovDataAreaMapper.ncovServiceCall();
        homePageData.setAllCall(ncovServiceCall);
        String time = getTime(1);
        //String time = getTime(31);
        Long yesterdayCall = ncovDataAreaMapper.yesterdayCall(time);
        homePageData.setYesterdayCall(yesterdayCall);
        //  数据接入
        List<List<Object>> overview = UnitExcelExportUtil.ncovDataList(dataAccessName,1,0);
        Long total = 0L;
        Long yesterday = 0L;
        for (List<Object> list : overview) {
            total += Long.valueOf((String)list.get(5));
            yesterday += Long.valueOf((String)list.get(6));
        }
        homePageData.setTotalCount(total);
        homePageData.setResourceCount(overview.size());
        homePageData.setYesterdayCount(yesterday);
        //  数据治理
        List<DataGovernanceLevel2> updateTypeDtos = Lists.newArrayList();
        List<List<Object>> updateType = UnitExcelExportUtil.ncovDataList(dataGovernanceName,1,2);
        for (List<Object> objects : updateType) {
            DataGovernanceLevel2 dataGovernanceLevel2 = new DataGovernanceLevel2();
            dataGovernanceLevel2.setType((String) objects.get(1));
            dataGovernanceLevel2.setNum((String) objects.get(2));
            dataGovernanceLevel2.setSum((String) objects.get(3));
            dataGovernanceLevel2.setPercentage((String) objects.get(4));
            updateTypeDtos.add(dataGovernanceLevel2);
        }
        homePageData.setUpdateType(updateTypeDtos);
        List<List<Object>> updateCycle = UnitExcelExportUtil.ncovDataList(dataGovernanceName,1,3);
        List<DataGovernanceLevel2> updateCycleDtos = Lists.newArrayList();
        for (List<Object> objects : updateCycle) {
            DataGovernanceLevel2 dataGovernanceLevel2 = new DataGovernanceLevel2();
            dataGovernanceLevel2.setType((String) objects.get(1));
            dataGovernanceLevel2.setNum((String) objects.get(2));
            dataGovernanceLevel2.setSum((String) objects.get(3));
            dataGovernanceLevel2.setPercentage((String) objects.get(4));
            updateCycleDtos.add(dataGovernanceLevel2);
        }
        homePageData.setUpdateCycle(updateCycleDtos);
        return homePageData;
    }

    private List<NcovDataLongDto> mapSort(Map<String,Long> map){
        List<NcovDataLongDto> ncovDataLongDtoList = Lists.newArrayList();
        List<Map.Entry<String,Long>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list,new Comparator<Map.Entry<String,Long>>() {
            //升序排序
            @Override
            public int compare(Map.Entry<String, Long> o1,
                               Map.Entry<String, Long> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        int n = 10;
        if (list.size()<10){
            n=list.size();
        }
        for (int i = 0; i < n; i++) {
            Map.Entry<String, Long> entry = list.get(i);
            NcovDataLongDto ncovDataLongDto = new NcovDataLongDto();
            ncovDataLongDto.setName(entry.getKey());
            ncovDataLongDto.setCount(entry.getValue());
            ncovDataLongDtoList.add(ncovDataLongDto);
        }
        return ncovDataLongDtoList;
    }

    private List<String> getLatelyDaysTime(){
        List<String> list = Lists.newArrayList();
        for (int i = 6; i >= 0 ; i--) {
            list.add(getTime(i));
        }
        return list;
    }

    /**
     * 获取之前的日期
     * @param day 天数
     * @return
     */
    private String getTime(Integer day){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //  当前时间毫秒数
        long timeMillis = System.currentTimeMillis();
        //  七天前毫秒数
        long sevenDaysMillis = timeMillis - day*24L*60*60*1000;
        Date date =new Date(sevenDaysMillis);
        return simpleDateFormat.format(date);
    }

    private void downFile(HttpServletResponse response, String name) throws IOException {
        File file = new File(rootPath+"/ncovArea/质量分析报告/" + name);
        if (file.exists()) {
            // 设置强制下载不打开
            response.setContentType("application/force-download");
            String originaName=null;
            try {
                originaName = URLEncoder.encode(name, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            // 设置文件名
            response.addHeader("Content-Disposition", "attachment;fileName=" + originaName);
            FileUtils.copyFile(file, response.getOutputStream());
        }
    }
}
