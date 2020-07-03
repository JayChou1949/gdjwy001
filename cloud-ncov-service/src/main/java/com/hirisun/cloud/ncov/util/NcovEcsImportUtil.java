package com.hirisun.cloud.ncov.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.hirisun.cloud.common.util.UnitExcelExportUtil;
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
