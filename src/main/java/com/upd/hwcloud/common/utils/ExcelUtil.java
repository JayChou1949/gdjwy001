package com.upd.hwcloud.common.utils;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.entity.Asset;
import com.upd.hwcloud.bean.vo.daasService.ServiceIssueVo;
import com.upd.hwcloud.common.utils.annotation.ExcelExplain;
import com.upd.hwcloud.common.utils.easypoi.ExcelStyleUtil;
import com.upd.hwcloud.common.utils.easypoi.ExportMoreView;
import com.upd.hwcloud.common.utils.easypoi.ExportView;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;

public class ExcelUtil {

    public static List<Asset> readXls(InputStream inputStream) throws IOException {
        List<Asset> assets = new ArrayList<>();
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = workbook.getSheetAt(0);
        for(int i=1;i<=sheet.getLastRowNum();i++) {
            Asset asset = new Asset();
            HSSFRow row = sheet.getRow(i);
            asset.setContractNo(row.getCell(0).getStringCellValue());
            asset.setTime(row.getCell(1).getDateCellValue());
            asset.setProductLine(row.getCell(2).getStringCellValue());
            asset.setDeviceType(row.getCell(3).getStringCellValue());
            asset.setProductType(row.getCell(4).getStringCellValue());
            asset.setDeviceName(row.getCell(5).getStringCellValue());
            asset.setConfig(row.getCell(6).getStringCellValue());
            asset.setBoqNo(row.getCell(7).getStringCellValue());
            row.getCell(8).setCellType(CellType.STRING);
            asset.setTotal(Long.valueOf(row.getCell(8).getStringCellValue()));
            asset.setSite(row.getCell(9).getStringCellValue());
            row.getCell(10).setCellType(CellType.STRING);
            asset.setAmount(Long.valueOf(row.getCell(10).getStringCellValue()));
            asset.setSoftwareName(row.getCell(11).getStringCellValue());
            asset.setLocation(row.getCell(12).getStringCellValue());
            assets.add(asset);
        }
        return assets;
    }

    public static List<Asset> readXlsx(InputStream inputStream) throws IOException {
        List<Asset> assets = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        for(int i=1;i<=sheet.getLastRowNum();i++) {
            Asset asset = new Asset();
            XSSFRow row = sheet.getRow(i);
            asset.setContractNo(row.getCell(0).getStringCellValue());
            if(row.getCell(1) != null) {
                asset.setTime(row.getCell(1).getDateCellValue());
            }
            row.getCell(2).setCellType(CellType.STRING);
            asset.setProductLine(row.getCell(2).getStringCellValue());
            asset.setDeviceType(row.getCell(3).getStringCellValue());
            asset.setProductType(row.getCell(4).getStringCellValue());
            asset.setDeviceName(row.getCell(5).getStringCellValue());
            if(row.getCell(6) != null) {
                asset.setConfig(row.getCell(6).getStringCellValue());
            }
            if(row.getCell(7) != null) {
                asset.setBoqNo(row.getCell(7).getStringCellValue());
            }
            row.getCell(8).setCellType(CellType.STRING);
            asset.setTotal(Long.valueOf(row.getCell(8).getStringCellValue()));
            if(row.getCell(9) != null) {
                asset.setBoqNo(row.getCell(9).getStringCellValue());
            }
            if(row.getCell(10) != null) {
                row.getCell(10).setCellType(CellType.STRING);
                asset.setAmount(Long.valueOf(row.getCell(10).getStringCellValue()));
            }
            if(row.getCell(11) != null) {
                asset.setSoftwareName(row.getCell(11).getStringCellValue());
            }
            if(row.getCell(12) != null) {
                asset.setLocation(row.getCell(12).getStringCellValue());
            }
            assets.add(asset);
        }
        return assets;
    }
    public static final int maxRowNum = 65000;

    public static void setCell(HSSFCell cell, Object cellValue) {
        if (cellValue == null) {
            HSSFRichTextString hrts = new HSSFRichTextString("");
            cell.setCellValue(hrts);
        } else if (cellValue instanceof String) {
            HSSFRichTextString hrts = new HSSFRichTextString((String) cellValue);
            cell.setCellValue(hrts);
        } else if (cellValue instanceof Boolean) {
            if ((Boolean) cellValue){
                cell.setCellValue("是");
            }else{
                cell.setCellValue("否");
            }
            //  cell.setCellValue((Boolean) cellValue);
        } else if (cellValue instanceof Calendar) {
            cell.setCellValue((Calendar) cellValue);
        } else if (cellValue instanceof Date) {
            cell.setCellValue((Date) cellValue);
        } else if (cellValue instanceof Double) {
            cell.setCellValue((Double) cellValue);
        } else if (cellValue instanceof Integer) {
            cell.setCellValue((Integer) cellValue);
        } else if (cellValue instanceof Float) {
            cell.setCellValue((Float) cellValue);
        } else {
            HSSFRichTextString hrts = new HSSFRichTextString(cellValue
                    .toString());
            cell.setCellValue(hrts);
        }
    }

    public static HSSFWorkbook createExcel(List<Object> list, Map<String, List> headerAndFields, String sheetName) throws Exception {
        if (null==sheetName||sheetName.trim().length()<1){
            sheetName = "工作表";
        }
        HSSFWorkbook wb = new HSSFWorkbook();
        int size = list.size();
        int sheetCount = size / maxRowNum + 1;
        for (int i = 0; i < sheetCount; i++) {

            HSSFSheet sheet = wb.createSheet(sheetName);
            int rowNum = 0, cellNum = 0;
            HSSFRow row = sheet.createRow(rowNum);

            HSSFCellStyle rowStyle = wb.createCellStyle();
            rowStyle.setAlignment(HorizontalAlignment.CENTER);
            //表头添加
            for (String cellVelue : (List<String>) headerAndFields.get("header")) {
                HSSFCell cell = row.createCell(cellNum);
                setCell(cell, cellVelue);
                cell.setCellStyle(rowStyle);
                sheet.setColumnWidth(cellNum, cellVelue.getBytes().length * 2 * 256);
                cellNum++;
            }
            rowNum++;
            HSSFCellStyle cellStyle = wb.createCellStyle();
            ;
            cellStyle.setWrapText(true);
            for (int j = i * maxRowNum; j < (i + 1) * maxRowNum && j < size; j++) {
                Object rowValue = list.get(j);
                row = sheet.createRow(rowNum);
                cellNum = 0;
                for (Field cellVelue : (List<Field>) headerAndFields.get("fields")) {
                    cellVelue.setAccessible(true);
                    HSSFCell cell = row.createCell(cellNum);
                    setCell(cell, cellVelue.get(rowValue));
                    /*                    cell.setCellStyle(cellStyle);*/
                    cellNum++;
                }
                rowNum++;
            }
        }
        return wb;
    }

    public static HSSFWorkbook createExcel(List list, Class searchResultVoClass, String sheetName) throws Exception {
        return createExcel(list,getExcelHeaderAndFields(searchResultVoClass),sheetName);
    }

    /**
     * 导出数据存储对象
     * @param classz
     * @return 表头集合+属性集合
     */
    public static Map getExcelHeaderAndFields(Class classz) {
        Field[] allFields=classz.getDeclaredFields();
        List<Field> fields = new ArrayList<>();
        List<String> headers  = new ArrayList<>();
        for(int i = 0;i<allFields.length ; i++){
            allFields[i].setAccessible(true);

            ExcelExplain annotation=allFields[i].getAnnotation(ExcelExplain.class); //获取指定类型注解
            if(annotation!=null){
                fields.add(allFields[i]);
                headers.add(annotation.head());
            }
        }
        Map map = new HashMap();
        map.put("header",headers);
        map.put("fields",fields);
        return map;
    }
    public static void export(HttpServletRequest request, HttpServletResponse response, String fileName, Workbook workbook){
        response.reset();//删除多余的空白字符
        try {
            //导出支持中文名，兼容IE、火狐
            if (request.getHeader("user-agent").indexOf("MSIE") != -1) {
                fileName = java.net.URLEncoder.encode(fileName, "utf-8") + ".xls";
            } else {
                fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1") + ".xls";
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentType("application/vnd.ms-excel");
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportServiceStatistics(HttpServletRequest request, HttpServletResponse response, String fileName, Class<?> pojoClass, Collection<?> dataSet){
        ExportParams exportParams = new ExportParams(null,fileName);
        exportParams.setStyle(ExcelStyleUtil.class);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams,pojoClass,dataSet);
        export(request, response, fileName, workbook);
    }

    public static void exportMoreView(HttpServletRequest request, HttpServletResponse response, String fileName, ExportMoreView moreView) {
        // 迭代导出对象，将对应的配置信息写入到实际的配置中
        List<Map<String, Object>> exportParamList = Lists.newArrayList();
        for(ExportView view : moreView.getMoreViewList()){
            Map<String, Object> valueMap= Maps.newHashMap();
            valueMap.put("title", view.getExportParams());
            valueMap.put("data", view.getDataList());
            valueMap.put("entity", view.getCls());
            exportParamList.add(valueMap);
        }
        Workbook workbook = ExcelExportUtil.exportExcel(exportParamList, ExcelType.HSSF);
        ExcelUtil.export(request, response, fileName, workbook);
    }

    /**
     *
     * @param file 上传的文件
     * @return
     */
    public static Sheet getSheet(MultipartFile file){
        String fileName = file.getOriginalFilename();
        Workbook wb = null;
        InputStream in = null;
        //初始化一个excel对象
        try {
            in = file.getInputStream();
            if(fileName.endsWith(".xls")){
                    wb = new HSSFWorkbook(in);
            }else if(fileName.endsWith(".xlsx")){
                wb = new XSSFWorkbook(in);
            }else{
                throw new Exception("不是excel文件，请重新选择文件");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        //获取第一个sheet表单
        Sheet sheet = wb.getSheetAt(0);
        return sheet;
    }

    /**
     *  excel导入如果文本是数字，单元格格式会变为numberic，但只想要string类型的
     * @param row 行
     * @param i 单元格索引
     * @return
     */
    public static String getStringCellValue(Row row,int i){
        row.getCell(i).setCellType(CellType.STRING);
        return row.getCell(i).getStringCellValue();
    }

}
