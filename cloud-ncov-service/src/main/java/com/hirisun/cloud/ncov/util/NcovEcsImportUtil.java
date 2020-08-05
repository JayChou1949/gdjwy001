package com.hirisun.cloud.ncov.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.hirisun.cloud.common.util.UnitExcelExportUtil;
import com.hirisun.cloud.model.ncov.vo.daas.DataAccessVo;
import com.hirisun.cloud.model.ncov.vo.daas.DataGovernanceLevel2Vo;
import com.hirisun.cloud.model.ncov.vo.daas.DataGovernanceVo;
import com.hirisun.cloud.model.ncov.vo.daas.DataModelingVo;
import com.hirisun.cloud.model.ncov.vo.daas.DataSharingVo;
import com.hirisun.cloud.model.ncov.vo.daas.NcovDataLongVo;
import com.hirisun.cloud.model.ncov.vo.daas.NcovDataOverviewVo;
import com.hirisun.cloud.model.ncov.vo.daas.NcovDataVo;
import com.hirisun.cloud.model.ncov.vo.iaas.NcovHomePageIaasVo;
import com.hirisun.cloud.model.ncov.vo.paas.NcovClusterApp;
import com.hirisun.cloud.model.ncov.vo.paas.NcovClusterOverviewVo;
import com.hirisun.cloud.model.ncov.vo.paas.NcovClusterResourceVo;
import com.hirisun.cloud.model.ncov.vo.realtime.NcovRealtimeVo;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;

@Component
public class NcovEcsImportUtil {

    public static DataSharingVo getShardingExcelData(InputStream inputStream) {
    	
    	Workbook workbook;
    	DataSharingVo dataSharing = null;
		try {
			dataSharing =new DataSharingVo();
			workbook = UnitExcelExportUtil.createWorkbook(inputStream);
			
			//总览数据
	        List<List<Object>> overview = UnitExcelExportUtil.getDataListBySheet(workbook,1,0);
	        List<NcovDataOverviewVo> ncovDataOverviewDtos = Lists.newArrayList();
	        for (List<Object> objects : overview) {
	            NcovDataOverviewVo ncovDataOverviewDto = new NcovDataOverviewVo();
	            ncovDataOverviewDto.setName((String) objects.get(0));
	            ncovDataOverviewDto.setCount((String) objects.get(1));
	            ncovDataOverviewDto.setUnit((String) objects.get(2));
	            ncovDataOverviewDtos.add(ncovDataOverviewDto);
	        }
	        dataSharing.setDataSharingOverview(ncovDataOverviewDtos);
	        //单位下载
	        List<List<Object>> unitDownload = UnitExcelExportUtil.getDataListBySheet(workbook,1,1);
	        List<NcovDataVo> ncovDataDtos1 = Lists.newArrayList();
	        for (List<Object> objects : unitDownload) {
	        	NcovDataVo ncovDataDto = new NcovDataVo();
	            ncovDataDto.setName((String) objects.get(1));
	            ncovDataDto.setCount((String) objects.get(2));
	            ncovDataDtos1.add(ncovDataDto);
	        }
	        Collections.sort(ncovDataDtos1);
	        dataSharing.setUnitDownload(ncovDataDtos1.subList(0,10));
	        //高频使用资源
	        List<List<Object>> highFrequencyUse = UnitExcelExportUtil.getDataListBySheet(workbook,1,2);
	        List<NcovDataVo> ncovDataDtos2 = Lists.newArrayList();
	        for (List<Object> objects : highFrequencyUse) {
	        	NcovDataVo ncovDataDto = new NcovDataVo();
	            ncovDataDto.setName((String) objects.get(1));
	            ncovDataDto.setCount((String) objects.get(2));
	            ncovDataDtos2.add(ncovDataDto);
	        }
	        Collections.sort(ncovDataDtos2);
	        dataSharing.setHighFrequencyUse(ncovDataDtos2.subList(0,10));
	        return dataSharing;
		} catch (InvalidFormatException | IOException e) {
			e.printStackTrace();
		}
        
        return null;
    }
    
    
    /**
     * 上传
     * 首页 daas 数据共享和数据建模数据 
     * 原来读 数据共享 读取  数据共享情况V1.xlsx
     * 原来读 数据建模 读取  数据建模情况V1.xlsx
     * @param fileId
     * @param num
     * @param sheetNum
     * @return
     */
    public static DataModelingVo getMoelingExcelData(InputStream inputStream) {

    	
    	DataModelingVo dataModeling = null;
    	Workbook workbook = null;
		try {
			workbook = UnitExcelExportUtil.createWorkbook(inputStream);
			dataModeling = new DataModelingVo();
	        //总览数据
	        List<List<Object>> overview = UnitExcelExportUtil.getDataListBySheet(workbook,1,0);
	        List<NcovDataOverviewVo> ncovDataOverviewDtos = Lists.newArrayList();
	        for (List<Object> objects : overview) {
	        	NcovDataOverviewVo ncovDataOverviewDto = new NcovDataOverviewVo();
	            ncovDataOverviewDto.setName((String) objects.get(0));
	            ncovDataOverviewDto.setCount((String) objects.get(1));
	            ncovDataOverviewDto.setUnit((String) objects.get(2));
	            ncovDataOverviewDtos.add(ncovDataOverviewDto);
	        }
	        dataModeling.setDataModelingOverview(ncovDataOverviewDtos);
	        //单位建模排名
	        List<List<Object>> unitModeling = UnitExcelExportUtil.getDataListBySheet(workbook,1,1);
	        List<NcovDataVo> ncovDataDtos1 = Lists.newArrayList();
	        for (List<Object> objects : unitModeling) {
	        	NcovDataVo ncovDataDto = new NcovDataVo();
	            ncovDataDto.setName((String) objects.get(1));
	            ncovDataDto.setCount((String) objects.get(2));
	            ncovDataDtos1.add(ncovDataDto);
	        }
	        Collections.sort(ncovDataDtos1);
	        dataModeling.setUnitModeling(ncovDataDtos1.subList(0,10));
	        //公共模型建设单位排名
	        List<List<Object>> publicModelConstructionUnit = UnitExcelExportUtil.getDataListBySheet(workbook,1,2);
	        List<NcovDataVo> ncovDataDtos2 = Lists.newArrayList();
	        for (List<Object> objects : publicModelConstructionUnit) {
	        	NcovDataVo ncovDataDto = new NcovDataVo();
	            ncovDataDto.setName((String) objects.get(1));
	            ncovDataDto.setCount((String) objects.get(2));
	            ncovDataDtos2.add(ncovDataDto);
	        }
	        Collections.sort(ncovDataDtos2);
	        dataModeling.setPublicModelConstructionUnit(ncovDataDtos2.subList(0,10));
	        //模型热度排名
	        List<List<Object>> modelPopularity = UnitExcelExportUtil.getDataListBySheet(workbook,1,3);
	        List<NcovDataVo> ncovDataDtos3 = Lists.newArrayList();
	        for (List<Object> objects : modelPopularity) {
	        	NcovDataVo ncovDataDto = new NcovDataVo();
	            ncovDataDto.setName((String) objects.get(1));
	            ncovDataDto.setCount((String) objects.get(2));
	            ncovDataDtos3.add(ncovDataDto);
	        }
	        Collections.sort(ncovDataDtos3);
	        dataModeling.setModelPopularity(ncovDataDtos3.subList(0,10));
			
		} catch (InvalidFormatException | IOException e) {
			e.printStackTrace();
		}finally {
            if(workbook != null){
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inputStream !=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return dataModeling;
  	}
    
    public static List<List<Object>> getNcovDataList(InputStream inputStream,Integer num,Integer sheetNum){
    	

        Workbook workbook = null;
        List<List<Object>> resultList = Lists.newArrayList();
        try{
        	
            workbook = UnitExcelExportUtil.createWorkbook(inputStream);
            resultList = UnitExcelExportUtil.getDataListBySheet(workbook,num,sheetNum);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(workbook != null){
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inputStream !=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultList;
    
    	
    }
    
    public static List<List<Object>> getDataAccessLevel3(InputStream inputStream,String from,
    		Integer num, Integer sheetNum) throws Exception{
    	
    	Workbook workbook = UnitExcelExportUtil.createWorkbook(inputStream);
        List<List<Object>> data = new ArrayList<List<Object>>();
        if ("公安".equals(from)){
            data = UnitExcelExportUtil.getDataListBySheet(workbook,num,sheetNum);
        }else {
            data = UnitExcelExportUtil.getDataListBySheet(workbook,num,sheetNum);
        }
        return data;
    }
    
    
    public static DataAccessVo getDataAccess(InputStream inputStream) throws Exception{
    	
    	Workbook workbook = UnitExcelExportUtil.createWorkbook(inputStream);

    	DataAccessVo dataAccess = new DataAccessVo();
        List<List<Object>> overview = UnitExcelExportUtil.getDataListBySheet(workbook,1,0);
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
        List<List<Object>> policeData = UnitExcelExportUtil.getDataListBySheet(workbook,1,2);
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
        List<List<Object>> govData = UnitExcelExportUtil.getDataListBySheet(workbook,1,1);
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
    
    public static List<NcovDataLongVo> mapSort(Map<String,Long> map){
    	
        List<NcovDataLongVo> ncovDataLongList = Lists.newArrayList();
        
        LinkedHashMap<String, Long> collect = map.entrySet().stream()
        		.sorted(Map.Entry.comparingByValue()).limit(10)
        		.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (oldValue, newValue) -> oldValue, LinkedHashMap<String, Long>::new));
        
        collect.forEach((key,value)->{
        	ncovDataLongList.add(new NcovDataLongVo(key,value));
        });
        
        return ncovDataLongList;
    }
    
    private static List<String> getLatelyDaysTime(){
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
    private static String getTime(Integer day){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //  当前时间毫秒数
        long timeMillis = System.currentTimeMillis();
        //  七天前毫秒数
        long sevenDaysMillis = timeMillis - day*24L*60*60*1000;
        Date date =new Date(sevenDaysMillis);
        return simpleDateFormat.format(date);
    }
    
    /**
     * 上传
     * 首页 daas 数据治理
     * 原来读 数据接入情况.xlsx
     * @param fileId
     * @param num
     * @param sheetNum
     * @return
     */
    public static DataGovernanceVo getDataGovernance(InputStream inputStream){
    	Workbook workbook = null;
		try {
			workbook = UnitExcelExportUtil.createWorkbook(inputStream);
			DataGovernanceVo dataGovernance=new DataGovernanceVo();
	    	
	        List<List<Object>> tableName = UnitExcelExportUtil.getDataListBySheet(workbook,1,0);
	        Set<String> unitSet = new HashSet();
	        for (List<Object> list : tableName) {
	            String unitName = (String) list.get(1);
	            unitSet.add(unitName);
	        }
	        dataGovernance.setNames(unitSet);
	        List<List<Object>> dataSummary = UnitExcelExportUtil.getDataListBySheet(workbook,1,1);
	        List<NcovDataVo> ncovDataDtos = Lists.newArrayList();
	        for (List<Object> objects : dataSummary) {
	        	NcovDataVo ncovDataDto = new NcovDataVo();
	            ncovDataDto.setName((String) objects.get(1));
	            ncovDataDto.setCount((String) objects.get(2));
	            ncovDataDtos.add(ncovDataDto);
	        }
	        dataGovernance.setDataSummary(ncovDataDtos);
	        List<DataGovernanceLevel2Vo> updateTypeDtos = Lists.newArrayList();
	        List<List<Object>> updateType = UnitExcelExportUtil.getDataListBySheet(workbook,1,2);
	        for (List<Object> objects : updateType) {
	        	DataGovernanceLevel2Vo dataGovernanceLevel2 = new DataGovernanceLevel2Vo();
	            dataGovernanceLevel2.setType((String) objects.get(1));
	            dataGovernanceLevel2.setNum((String) objects.get(2));
	            dataGovernanceLevel2.setSum((String) objects.get(3));
	            dataGovernanceLevel2.setPercentage((String) objects.get(4));
	            updateTypeDtos.add(dataGovernanceLevel2);
	        }
	        dataGovernance.setUpdateType(updateTypeDtos);
	        List<List<Object>> updateCycle = UnitExcelExportUtil.getDataListBySheet(workbook,1,3);
	        List<DataGovernanceLevel2Vo> updateCycleDtos = Lists.newArrayList();
	        for (List<Object> objects : updateCycle) {
	        	DataGovernanceLevel2Vo dataGovernanceLevel2 = new DataGovernanceLevel2Vo();
	            dataGovernanceLevel2.setType((String) objects.get(1));
	            dataGovernanceLevel2.setNum((String) objects.get(2));
	            dataGovernanceLevel2.setSum((String) objects.get(3));
	            dataGovernanceLevel2.setPercentage((String) objects.get(4));
	            updateCycleDtos.add(dataGovernanceLevel2);
	        }
	        dataGovernance.setUpdateCycle(updateCycleDtos);
	        return dataGovernance;
		} catch (InvalidFormatException | IOException e) {
			e.printStackTrace();
		}finally {
            if(workbook != null){
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inputStream !=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    	
        return null;
    	
    }
    
	private static List<DataGovernanceLevel2Vo> setDataGovernanceData(List<List<Object>> updateType) {
		List<DataGovernanceLevel2Vo> updateTypeVos = Lists.newArrayList();
		for (List<Object> objects : updateType) {
			DataGovernanceLevel2Vo dataGovernanceLevel2 = new DataGovernanceLevel2Vo();
		    dataGovernanceLevel2.setType((String) objects.get(1));
		    dataGovernanceLevel2.setNum((String) objects.get(2));
		    dataGovernanceLevel2.setSum((String) objects.get(3));
		    dataGovernanceLevel2.setPercentage((String) objects.get(4));
		    updateTypeVos.add(dataGovernanceLevel2);
		}
		return updateTypeVos;
	}
    
	/**
     * 上传
     * 首页 daas 数据治理
     * 原来读    质量分析情况清单.xls
     * @param fileId
     * @param num
     * @param sheetNum
     * @return
     */
	public static Map<String, Map<String, Long>> getNcovDataServiceCount(InputStream inputStream) {
    	
        Workbook workbook = null;
        Map<String, Map<String, Long>> map = new HashMap<String, Map<String, Long>>();
        try{
            workbook = UnitExcelExportUtil.createWorkbook(inputStream);
            Map<String, Long> areaMap = converDataService(UnitExcelExportUtil.getDataListBySheet(workbook,3,0));
            Map<String, Long> govMap = converDataService(UnitExcelExportUtil.getDataListBySheet(workbook,3,1));
            
            map.put("areaMap", areaMap);
            map.put("govMap", govMap);
            
            return map;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(workbook != null){
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inputStream !=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    	
    }
	
	public static Map<String,Long> converDataService(List<List<Object>> list) {
		
		Map<String,Long> areaMap = new HashMap<>();
        for (List<Object> objects : list) {
            String name = (String) objects.get(3);
            String call = (String) objects.get(6);
            if (call==null){
                call="0";
            }
            Long num = areaMap.get(name);
            if (num == null){
                areaMap.put(name,Long.valueOf(call));
            }else {
                areaMap.put(name,num+Long.valueOf(call));
            }
        }
		return areaMap;
	}
	
	
    /**
     * 获取表格数据
     * @return
     * @throws Exception
     */
    public static List<List<Object>> list(InputStream inputStream,String name){
        List<List<Object>> list=null;
        Workbook workbook = null;
        try{
            workbook = UnitExcelExportUtil.createWorkbook(inputStream);
            if (null != workbook) {
                if ("省直疫情数据服务调用统计.xls".equals(name)) {
                    list = UnitExcelExportUtil.getDataList(workbook,3);
                } else if ("疫情桌面云.xls".equals(name)) {
                    list = UnitExcelExportUtil.getDataList(workbook, 1);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(workbook!=null){
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return list;
    }
    
    public static List<NcovRealtimeVo> getNcovRealtimeByExcel(InputStream inputStream,int regionType){
    	
		try{
            ImportParams params = new ImportParams();
            params.setStartSheetIndex(0);
            
            List<NcovRealtimeVo> list = ExcelImportUtil.importExcel(inputStream,NcovRealtimeVo.class,params);
            if(CollectionUtils.isNotEmpty(list)) {
            	list.forEach(vo->{vo.setRegionType(regionType);});
            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    	return null;
    }
    
    /**
     * 疫情虚拟机总览数据
     * 原来读取 ncovEcsSource.xlsx 
     * @return
     */
    public static NcovHomePageIaasVo getOverviewData(InputStream inputStream){
        try{
            ImportParams params = new ImportParams();
            params.setStartSheetIndex(0);
            List<NcovHomePageIaasVo> list = ExcelImportUtil.importExcel(inputStream,NcovHomePageIaasVo.class,params);
            if(CollectionUtils.isNotEmpty(list)){
                return list.get(0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return  null;
    }
    
    /**
     * 首页 iaas 疫情桌面云数据 上传
     * 原来读取 疫情桌面云.xls
     * @return
     */
    public static NcovHomePageIaasVo epidemicExcl(InputStream inputStream) throws Exception {
        List<List<Object>> list = NcovEcsImportUtil.list(inputStream,"疫情桌面云.xls");
        List<NcovHomePageIaasVo> iaasVoList = new ArrayList<NcovHomePageIaasVo>();
        if(CollectionUtils.isNotEmpty(list)) {
        	for (List<Object> itemlist : list) {
            	NcovHomePageIaasVo ncovHomePageIaasVo = new NcovHomePageIaasVo();
            	ncovHomePageIaasVo.setTotal(Integer.valueOf(itemlist.get(1).toString()));
            	ncovHomePageIaasVo.setCpu(Integer.valueOf(itemlist.get(2).toString()));
            	ncovHomePageIaasVo.setMemory(Double.valueOf(itemlist.get(3).toString()));
            	ncovHomePageIaasVo.setStorage(Double.valueOf(itemlist.get(4).toString()));
            	ncovHomePageIaasVo.setSupportPolice(itemlist.get(5) != null?1:0);
            	ncovHomePageIaasVo.setSupportArea(itemlist.get(6) != null?1:0);
            	iaasVoList.add(ncovHomePageIaasVo);
            }
            return epidemicExclNum(iaasVoList);
        }
        return null;
        
    }
    
    public static  NcovHomePageIaasVo epidemicExclNum(List<NcovHomePageIaasVo> epidemicDesktops) throws Exception {
    	
        int yunCount = 0;
        int areaCount = 0;
        int policeCount = 0;
        int cpuCount = 0;
        double ramCount = 0;
        double diskCount = 0;
        
        for (NcovHomePageIaasVo item : epidemicDesktops) {
            yunCount += Integer.valueOf(item.getTotal());
            if (item.getSupportArea() > 0) {
                areaCount++;
            }
            if (item.getSupportPolice() > 0) {
                policeCount++;
            }
            cpuCount += Integer.valueOf(item.getCpu());
            ramCount += item.getMemory();
            diskCount += item.getStorage();
        }
        NcovHomePageIaasVo deskTopNum = new NcovHomePageIaasVo();
        deskTopNum.setTotal(yunCount);
        deskTopNum.setSupportArea(areaCount);
        deskTopNum.setSupportPolice(policeCount);
        deskTopNum.setCpu(cpuCount);
        deskTopNum.setMemory(ramCount);
        deskTopNum.setStorage(diskCount);
        
        return deskTopNum;
    }
    
    /**
     * 首页 paas 数据概况
     * 原来读取 ncovCluster.xlsx SheetIndex(0)
     * @return
     */
    public static NcovClusterOverviewVo getOverview(InputStream inputStream,Integer sheetIndex){
        try{
            ImportParams params = new ImportParams();
            params.setStartSheetIndex(sheetIndex);
            List<NcovClusterOverviewVo> list = ExcelImportUtil.importExcel(inputStream,NcovClusterOverviewVo.class,params);
            if(CollectionUtils.isEmpty(list)){
                return null;
            }else {
                return list.get(0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
        return null;
    }
    
    /**
     * 首页 paas 数据资源分配上传
     * 原来读取 ncovCluster.xlsx SheetIndex(1)
     * @return
     */
	public static List<NcovClusterResourceVo> getResourceList(InputStream inputStream,Integer sheetIndex) {
		try {
			ImportParams params = new ImportParams();
			params.setStartSheetIndex(sheetIndex);
			List<NcovClusterResourceVo> list = ExcelImportUtil.importExcel(inputStream, NcovClusterResourceVo.class,
					params);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;

	}
    
	/**
     * 首页 paas APP应用明细资源分配上传
     * 原来读取 ncovCluster.xlsx SheetIndex(2)
     * @return
     */
	public static List<NcovClusterApp> getAppDetailList(InputStream inputStream,Integer sheetIndex){
        try{
            ImportParams params = new ImportParams();
            params.setStartSheetIndex(sheetIndex);
            List<NcovClusterApp> list = ExcelImportUtil.importExcel(inputStream,NcovClusterApp.class,params);
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
        return null;
    }
	
}
