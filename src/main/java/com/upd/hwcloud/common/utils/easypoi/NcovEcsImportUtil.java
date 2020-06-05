package com.upd.hwcloud.common.utils.easypoi;

import com.upd.hwcloud.bean.entity.application.PaasTyyh;
import com.upd.hwcloud.bean.vo.ncov.NcovEcsOverview;
import com.upd.hwcloud.bean.vo.ncov.NcovExcelSheetOneVo;
import com.upd.hwcloud.bean.vo.ncov.NcovIaasVo;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;

/**
 * 疫情虚拟机数据模板导入工具类
 * @author wuc
 * @date 2020/3/5
 */
public class NcovEcsImportUtil {


    private final static  String sourceFileName = "ncovEcsSource.xlsx";

    @Value("${file.path}")
    private static String rootPath;


    /**
     * 疫情虚拟机总览数据
     * @return
     */
    public static NcovEcsOverview getOverviewData(){
        try{
            ImportParams params = new ImportParams();
            params.setStartSheetIndex(0);
            List<NcovEcsOverview> list = ExcelImportUtil.importExcel(new FileInputStream(new File(rootPath+"/"+sourceFileName)),NcovEcsOverview.class,params);
            if(CollectionUtils.isNotEmpty(list)){
                return list.get(0);
            }
        }catch (Exception e){
            e.printStackTrace();
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
            System.out.println(list);
            System.out.println(list.size());
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }

        return  null;

    }
}
