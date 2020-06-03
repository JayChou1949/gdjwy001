package com.upd.hwcloud.common.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @ Author     ：ljw
 * @ Date       ：Created in 10:27 2019/11/2
 * @ Description：日期工具
 */
public class DateUtil {

    /**
     * 格式化Date，并转成字符串
     * @param date
     * @return
     */
    public static String formatDate(Date date){
        if (date != null){
            LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return dtf.format(localDateTime);
        }
        return null;
    }

    public static String formateDate(Date date,String format){
        if(date != null){
            LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
            return dtf.format(localDateTime);
        }
        return null;
    }
}
