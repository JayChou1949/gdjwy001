package com.hirisun.cloud.ncov.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.hirisun.cloud.api.file.FileUploadApi;
import com.hirisun.cloud.common.util.UnitExcelExportUtil;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.ncov.vo.daas.DataGovernanceLevel2Vo;
import com.hirisun.cloud.model.ncov.vo.daas.NcovDataOverviewVo;
import com.hirisun.cloud.model.ncov.vo.iaas.NcovHomePageIaasVo;
import com.hirisun.cloud.model.ncov.vo.paas.NcovClusterApp;
import com.hirisun.cloud.model.ncov.vo.paas.NcovClusterOverviewVo;
import com.hirisun.cloud.model.ncov.vo.paas.NcovClusterResourceVo;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;

@Component
public class NcovEcsImportUtil {

    private static FileUploadApi fileUploadApi;

    @Autowired
    public NcovEcsImportUtil(FileUploadApi fileUploadApi) {
    	NcovEcsImportUtil.fileUploadApi = fileUploadApi;
    }
    
    /**
     * 下载
     * 首页 daas 数据共享和数据建模数据 
     * 原来读 数据共享 读取  数据共享情况V1.xlsx
     * 原来读 数据建模 读取  数据建模情况V1.xlsx
     * @param fileId
     * @param num
     * @param sheetNum
     * @return
     */
    public static List<NcovDataOverviewVo> getShardingMoelingExcelData(String fileId,
  			 Integer num, Integer sheetNum) {
  		
    	List<NcovDataOverviewVo> dataSharingVos = new ArrayList<NcovDataOverviewVo>();
    	Object object = downloadFileByFileId(fileId);
    	if(object != null) {
    		byte[] bytes = object.toString().getBytes();
    		
    		List<List<Object>> ncovDataList = getNcovDataList(new ByteArrayInputStream(bytes), num, sheetNum);
	  		for (List<Object> objects : ncovDataList) {
	  			NcovDataOverviewVo ncovDataOverviewVo = new NcovDataOverviewVo();
	  			ncovDataOverviewVo.setName((String) objects.get(0));
	  			ncovDataOverviewVo.setCount((String) objects.get(1));
	  			ncovDataOverviewVo.setUnit((String) objects.get(2));
	  			dataSharingVos.add(ncovDataOverviewVo);
	  		}
    	}
  		return dataSharingVos;
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
    public static List<NcovDataOverviewVo> getShardingMoelingExcelData(InputStream inputStream,
  			 Integer num, Integer sheetNum) {
  		
    	List<NcovDataOverviewVo> dataSharingVos = new ArrayList<NcovDataOverviewVo>();
		List<List<Object>> ncovDataList = getNcovDataList(inputStream, num, sheetNum);
  		for (List<Object> objects : ncovDataList) {
  			NcovDataOverviewVo ncovDataOverviewVo = new NcovDataOverviewVo();
  			ncovDataOverviewVo.setName((String) objects.get(0));
  			ncovDataOverviewVo.setCount((String) objects.get(1));
  			ncovDataOverviewVo.setUnit((String) objects.get(2));
  			dataSharingVos.add(ncovDataOverviewVo);
  		}
  		return dataSharingVos;
  	}
    
    /**
     * 根据文件id下载文件
     * @param fileId
     * @return
     */
    private static Object downloadFileByFileId(String fileId) {
    	
    	if(StringUtils.isNotBlank(fileId)) {
    		QueryResponseResult result = fileUploadApi.downloadFileByFileId(fileId);
        	return result.getData();
    	}
    	return null;
    	
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
    
    /**
     * 上传
     * 首页 daas 数据接入
     * 原来读 数据接入情况.xlsx
     * @param fileId
     * @param num
     * @param sheetNum
     * @return
     */
    public static Map<String,Long> getDataAccess(InputStream inputStream){
    	
    	Map<String,Long> map = new HashMap<String,Long>();
    	
        Workbook workbook = null;
        try{
            workbook = UnitExcelExportUtil.createWorkbook(inputStream);
            List<List<Object>> dataList = UnitExcelExportUtil.getDataListByCellNumber(workbook,1,0,5);
            
            Long total = dataList.stream()
             .mapToLong(list -> Long.valueOf(list.get(0).toString())).sum();
            
            Long yesterday = UnitExcelExportUtil.getDataListByCellNumber(workbook,1,0,6).stream()
                    .mapToLong(list -> Long.valueOf(list.get(0).toString())).sum();
            map.put("total", total);
            map.put("yesterday", yesterday);
            map.put("size", Long.valueOf(dataList.size()));
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
    
    /**
     * 下载
     * 首页 daas 数据接入
     * 原来读 数据接入情况.xlsx
     * @param fileId
     * @param num
     * @param sheetNum
     * @return
     */
    public static Map<String,Long> getDataAccess(String fileId){
    	
    	Object obj = downloadFileByFileId(fileId);
    	if(obj != null) return getDataAccess(new ByteArrayInputStream((byte[]) obj));
		return null;
    	
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
    public static Map<String,List<DataGovernanceLevel2Vo>> getDataGovernanceMap(InputStream inputStream){
    	
        Workbook workbook = null;
        Map<String,List<DataGovernanceLevel2Vo>> map = new HashMap<String,List<DataGovernanceLevel2Vo>>();
        try{
            workbook = UnitExcelExportUtil.createWorkbook(inputStream);
            List<List<Object>> updateType = UnitExcelExportUtil.getDataListBySheet(workbook,1,2);
            List<DataGovernanceLevel2Vo> updateTypeVos = setDataGovernanceData(updateType);
            map.put("updateTypeVos", updateTypeVos);
            List<List<Object>> updateCycle =UnitExcelExportUtil.getDataListBySheet(workbook,1,3);
            List<DataGovernanceLevel2Vo> updateCycleVos = setDataGovernanceData(updateCycle);
            map.put("updateCycleVos", updateCycleVos);
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
    
    /**
     * 下载
     * 首页 daas 数据治理
     * 原来读 数据接入情况.xlsx
     * @param fileId
     * @param num
     * @param sheetNum
     * @return
     */
    public static Map<String,List<DataGovernanceLevel2Vo>> getDataGovernanceMap(String fileId){
    	
    	Object object = downloadFileByFileId(fileId);
    	if(object != null) return getDataGovernanceMap(new ByteArrayInputStream((byte[]) object));
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
	public static int getNcovDataServiceCount(InputStream inputStream) {
    	
        Workbook workbook = null;
        Set<String> set = new HashSet<>();
        try{
            workbook = UnitExcelExportUtil.createWorkbook(inputStream);
            UnitExcelExportUtil.getDataListBySheet(workbook,3,0).forEach(objs->{set.add((String) objs.get(3));});
            UnitExcelExportUtil.getDataListBySheet(workbook,3,1).forEach(objs->{set.add((String) objs.get(3));});
            return set.size();
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
        return 0;
    	
    }
	
	
	
	/**
     * 下载
     * 首页 daas 数据服务
     * 原来读 质量分析情况清单.xls
     * @param fileId
     * @param num
     * @param sheetNum
     * @return
     */
    public static int getNcovDataServiceCount(String fileId) {
    	
    	Object object = downloadFileByFileId(fileId);
    	if(object != null) return getNcovDataServiceCount(new ByteArrayInputStream((byte[]) object));
        return 0;
    	
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
     * 疫情虚拟机总览数据
     * 原来读取 ncovEcsSource.xlsx 
     * @return
     */
    public static NcovHomePageIaasVo getOverviewData(String fileId){
    	
        try{
        	Object obj = downloadFileByFileId(fileId);
        	if(obj != null) {
        		ImportParams params = new ImportParams();
                params.setStartSheetIndex(0);
                List<NcovHomePageIaasVo> list = ExcelImportUtil.importExcel(new ByteArrayInputStream((byte[]) obj),NcovHomePageIaasVo.class,params);
                if(CollectionUtils.isNotEmpty(list))return list.get(0);
        	}
        }catch (Exception e){
            e.printStackTrace();
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
    
    /**
     * 首页 iaas 疫情桌面云数据 下载
     * 原来读取 疫情桌面云.xls
     * @return
     */
    public static NcovHomePageIaasVo epidemicExcl(String fileId) throws Exception {
    	
    	Object obj = downloadFileByFileId(fileId);
    	if(obj != null)return epidemicExcl(new ByteArrayInputStream((byte[]) obj));
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
     * 首页 paas 数据概况
     * 原来读取 ncovCluster.xlsx SheetIndex(0)
     * @return
     */
    public static NcovClusterOverviewVo getOverview(String fileId,Integer sheetIndex){
    	
    	Object obj = downloadFileByFileId(fileId);
    	if(obj != null) return getOverview(new ByteArrayInputStream((byte[]) obj),sheetIndex);
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
     * 首页 paas 数据资源分配下载
     * 原来读取 ncovCluster.xlsx SheetIndex(1)
     * @return
     */
	public static List<NcovClusterResourceVo> getResourceList(String fileId,Integer sheetIndex) {
		
		Object obj = downloadFileByFileId(fileId);
    	if(obj != null) return getResourceList(new ByteArrayInputStream((byte[]) obj),sheetIndex);
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
	
	/**
     * 首页 paas APP应用明细资源分配下载
     * 原来读取 ncovCluster.xlsx SheetIndex(2)
     * @return
     */
    public static List<NcovClusterApp> getAppDetailList(String fileId,Integer sheetIndex){
    	Object obj = downloadFileByFileId(fileId);
    	if(obj != null) return getAppDetailList(new ByteArrayInputStream((byte[]) obj),sheetIndex);
        return null;
    }
    
}
