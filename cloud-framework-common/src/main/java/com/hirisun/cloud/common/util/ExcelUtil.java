package com.hirisun.cloud.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class ExcelUtil {

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
        }finally {
        	try {
        		if(workbook != null) {
        			workbook.close();
        		}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
