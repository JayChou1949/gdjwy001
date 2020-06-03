package com.upd.hwcloud.common.utils;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WordUtil {

    public static void export(HttpServletRequest request, HttpServletResponse response, String fileName, XWPFDocument document){
        response.reset();//删除多余的空白字符
        try {
            //导出支持中文名，兼容IE、火狐
            if (request.getHeader("user-agent").indexOf("MSIE") != -1) {
                fileName = java.net.URLEncoder.encode(fileName, "utf-8") + ".docx";
            } else {
                fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1") + ".docx";
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            document.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
