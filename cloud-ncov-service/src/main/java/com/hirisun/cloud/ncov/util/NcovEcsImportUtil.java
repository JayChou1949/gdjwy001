package com.hirisun.cloud.ncov.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hirisun.cloud.model.ncov.vo.iaas.NcovEcsOverviewVo;
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
     * 疫情虚拟机总览数据
     * @return
     */
    public static NcovEcsOverviewVo getOverviewData(){
        FileInputStream inputStream = null;
        try{
            ImportParams params = new ImportParams();
            inputStream = new FileInputStream(new File(rootPath+"/"+sourceFileName));
            params.setStartSheetIndex(0);
            List<NcovEcsOverviewVo> list = ExcelImportUtil.importExcel(inputStream,NcovEcsOverviewVo.class,params);
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
