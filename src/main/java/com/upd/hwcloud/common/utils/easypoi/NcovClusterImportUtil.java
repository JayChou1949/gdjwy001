package com.upd.hwcloud.common.utils.easypoi;

import com.upd.hwcloud.bean.vo.ncov.NcovClusterApp;
import com.upd.hwcloud.bean.vo.ncov.NcovClusterOverview;
import com.upd.hwcloud.bean.vo.ncov.NcovClusterResource;
import com.upd.hwcloud.bean.vo.ncov.NcovExcelSheetOneVo;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.stream.Collectors;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;

/**
 * @author wuc
 * @date 2020/3/3
 */
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
    public static NcovClusterOverview getOverview(){
        try{
            ImportParams params = new ImportParams();
            params.setStartSheetIndex(0);
            List<NcovClusterOverview> list = ExcelImportUtil.importExcel(new FileInputStream(new File(rootPath+"/"+sourceFileName)),NcovClusterOverview.class,params);
            System.out.println(list);
            System.out.println(list.size());
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
    public static List<NcovClusterResource> getResourceList(){
    try{
        ImportParams params = new ImportParams();
        params.setStartSheetIndex(1);
        List<NcovClusterResource> list = ExcelImportUtil.importExcel(new FileInputStream(new File(rootPath+"/"+sourceFileName)),NcovClusterResource.class,params);
        System.out.println(list);
        System.out.println(list.size());
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
            System.out.println(list);
            System.out.println(list.size());
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
