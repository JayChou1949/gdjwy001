package com.upd.hwcloud.common.utils.easypoi;

import com.upd.hwcloud.bean.vo.ncov.NcovEcsOverview;
import com.upd.hwcloud.bean.vo.ncov.NcovIaasVo;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
    public static NcovEcsOverview getOverviewData(){
        FileInputStream inputStream = null;
        try{
            ImportParams params = new ImportParams();
            inputStream = new FileInputStream(new File(rootPath+"/"+sourceFileName));
            params.setStartSheetIndex(0);
            List<NcovEcsOverview> list = ExcelImportUtil.importExcel(inputStream,NcovEcsOverview.class,params);
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
