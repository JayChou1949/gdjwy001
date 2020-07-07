package com.hirisun.cloud.ncov.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.hirisun.cloud.common.util.UnitExcelExportUtil;
import com.hirisun.cloud.model.ncov.vo.daas.DataGovernanceLevel2Vo;
import com.hirisun.cloud.model.ncov.vo.iaas.NcovHomePageIaasVo;
import com.hirisun.cloud.model.ncov.vo.iaas.NcovIaasVo;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;

/**
 * 疫情虚拟机数据模板导入工具类
 * @author wuc
 * @date 2020/3/5
 */
@Component
public class NcovEcsImportUtil {


    private final static  String sourceFileName = "ncovEcsSource.xlsx";

    private static String rootPath;

    @Value("${file.path}")
    public void setRootPath(String rootPath) {
        NcovEcsImportUtil.rootPath = rootPath;
    }

    /**
     * 获取疫情专区表格数据
     * @return
     * @throws Exception
     */
    public static List<List<Object>> ncovDataList(String name,Integer num,Integer sheetNum){
        File file = new File(rootPath+"/ncovArea/"+name);
        InputStream inputStream = null;
        Workbook workbook = null;
        List<List<Object>> resultList = Lists.newArrayList();
        try{
            inputStream=new FileInputStream(file);
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
    
    public static Map<String,List<DataGovernanceLevel2Vo>> getDataGovernanceMap(String dataGovernanceName){
    	
    	File file = new File(rootPath+"/ncovArea/"+dataGovernanceName);
        InputStream inputStream = null;
        Workbook workbook = null;
        Map<String,List<DataGovernanceLevel2Vo>> map = new HashMap<String,List<DataGovernanceLevel2Vo>>();
        try{
            inputStream=new FileInputStream(file);
            workbook = UnitExcelExportUtil.createWorkbook(inputStream);
            List<List<Object>> updateType = UnitExcelExportUtil.getDataListBySheet(workbook,1,2);
            List<DataGovernanceLevel2Vo> updateTypeVos = Lists.newArrayList();
            for (List<Object> objects : updateType) {
            	DataGovernanceLevel2Vo dataGovernanceLevel2 = new DataGovernanceLevel2Vo();
                dataGovernanceLevel2.setType((String) objects.get(1));
                dataGovernanceLevel2.setNum((String) objects.get(2));
                dataGovernanceLevel2.setSum((String) objects.get(3));
                dataGovernanceLevel2.setPercentage((String) objects.get(4));
                updateTypeVos.add(dataGovernanceLevel2);
            }
            
            map.put("updateTypeVos", updateTypeVos);
            
            List<List<Object>> updateCycle =UnitExcelExportUtil.getDataListBySheet(workbook,1,3);
            List<DataGovernanceLevel2Vo> updateCycleVos = Lists.newArrayList();
            for (List<Object> objects : updateCycle) {
            	DataGovernanceLevel2Vo dataGovernanceLevel2 = new DataGovernanceLevel2Vo();
                dataGovernanceLevel2.setType((String) objects.get(1));
                dataGovernanceLevel2.setNum((String) objects.get(2));
                dataGovernanceLevel2.setSum((String) objects.get(3));
                dataGovernanceLevel2.setPercentage((String) objects.get(4));
                updateCycleVos.add(dataGovernanceLevel2);
            }
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
    
    public static int getNcovDataServiceCount(String dataServiceName) {
    	
    	File file = new File(rootPath+"/ncovArea/"+dataServiceName);
        InputStream inputStream = null;
        Workbook workbook = null;
        Set<String> set = new HashSet<>();
        try{
            inputStream=new FileInputStream(file);
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
     * 获取表格数据
     * @return
     * @throws Exception
     */
    public static List<List<Object>> list(String name){
        List<List<Object>> list=null;
        InputStream inputStream =null;
        Workbook workbook = null;
        File file = new File(rootPath+"/"+name);
//        File file = new File("E:/hwyFiles/省直疫情数据服务调用统计.xls");//song本地测试用
        try{
            inputStream =new FileInputStream(file);
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
     * @return
     */
    public static NcovHomePageIaasVo getOverviewData(){
        FileInputStream inputStream = null;
        try{
            ImportParams params = new ImportParams();
            inputStream = new FileInputStream(new File(rootPath+"/"+sourceFileName));
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
     * 获取虚拟分配情况
     * @return
     */
    public static List<NcovIaasVo> getAllocationData(){

        try{
            ImportParams params = new ImportParams();
            params.setStartSheetIndex(1);
            List<NcovIaasVo> list = ExcelImportUtil.importExcel(new FileInputStream(new File(rootPath+"/"+sourceFileName)),NcovIaasVo.class,params);
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }

        return  null;

    }
}
