package com.hirisun.cloud.ncov.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hirisun.cloud.model.ncov.vo.paas.NcovClusterApp;
import com.hirisun.cloud.model.ncov.vo.paas.NcovClusterAppVo;
import com.hirisun.cloud.model.ncov.vo.paas.NcovClusterOverviewVo;
import com.hirisun.cloud.model.ncov.vo.paas.NcovClusterResourceVo;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;

/**
 * @author wuc
 * @date 2020/3/3
 */
@Component
public class NcovClusterImportUtil {

    private final static  String sourceFileName = "ncovCluster.xlsx";

    private static String rootPath;

    @Value("${file.path}")
    public void setRootPath(String rootPath) {
        NcovClusterImportUtil.rootPath = rootPath;
    }

    /**
     * 获取第一个sheet概览数据
     * @return
     */
    public static NcovClusterOverviewVo getOverview(){
        try{
            ImportParams params = new ImportParams();
            params.setStartSheetIndex(0);
            List<NcovClusterOverviewVo> list = ExcelImportUtil.importExcel(new FileInputStream(new File(rootPath+"/"+sourceFileName)),NcovClusterOverviewVo.class,params);
            if(CollectionUtils.isEmpty(list)){
                return null;
            }else {
                return list.get(0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取资源分配列表
     * @return
     */
    public static List<NcovClusterResourceVo> getResourceList(){
    try{
        ImportParams params = new ImportParams();
        params.setStartSheetIndex(1);
        List<NcovClusterResourceVo> list = ExcelImportUtil.importExcel(new FileInputStream(new File(rootPath+"/"+sourceFileName)),NcovClusterResourceVo.class,params);
        return list;
    }catch (Exception e){
        e.printStackTrace();
    }
        return null;

    }

    public static List<NcovClusterApp> getAppDetailList(){
        try{
            ImportParams params = new ImportParams();
            params.setStartSheetIndex(2);
            List<NcovClusterApp> list = ExcelImportUtil.importExcel(new FileInputStream(new File(rootPath+"/"+sourceFileName)),NcovClusterApp.class,params);
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
