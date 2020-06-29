package com.upd.hwcloud.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @ Author     ：ljw
 * @ Date       ：Created in 10:27 2019/11/2
 * @ Description：日期工具
 */
public class DateUtil {

    /** 默认时间格式yyyy-MM-dd HH:mm:ss **/
    public static String DEFAULTFORMAT = "yyyy-MM-dd HH:mm:ss";
    /** 日期格式yyyy-MM-dd **/
    public static String Date_Format = "yyyy-MM-dd";
    public static String Date_FormatYMDHM = "yyyy-MM-dd HH:mm";
    public static String Date_FormatYMDHMS = "yyyy-MM-dd HH:mm:ss";

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

    public static String  dateAdd(Date date,String addTime)throws Exception{
        if(date==null||addTime==null)return null;
        String[] times=addTime.split(":");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.HOUR, Integer.parseInt(times[0]));
        cd.add(Calendar.MINUTE, Integer.parseInt(times[1]));
        cd.add(Calendar.SECOND, Integer.parseInt(times[2]));
        return sdf.format(cd.getTime());
    }
    public static String dateAdd(Date date, int add,String pattern) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            c.setTime(date);
            c.add(c.DATE, add);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdf.format(c.getTime());
    }
    /**
     * 返回相加后的日期 天相加
     * @param date String yyyy-MM-dd
     * @param add int 需要相加的天数
     * @return String yyyy-MM-dd
     */
    public static String dateAdd(String date, int add) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            c.setTime(sdf.parse(date));
            c.add(c.DATE, add);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdf.format(c.getTime());
    }

    /**
     * 返回相加后的日期
     * @param date Date yyyy-MM-dd HH:mm:ss
     * @param add int 单位,分 日期相加  分
     * @return String yyyy-MM-dd HH:mm:ss
     */
    public static String dateAdd(Date date, int add) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            c.setTime(date);
            c.add(c.MINUTE, add);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdf.format(c.getTime());
    }

    public static String secondAdd(Date date, int add) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            c.setTime(date);
            c.add(c.SECOND, add);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdf.format(c.getTime());
    }

    /**
     * 指定日期的月份 相加
     * @param date String yyyy-MM
     * @param add int 添加的月份
     * @return String 返回相加后的日期 yyyy-MM
     */
    public static String monthAdd(String date, int add) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        try {
            c.setTime(sdf.parse(date));
            c.add(c.MONTH, add);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdf.format(c.getTime());
    }

    /**
     * 年份添加
     * @param date String 日期 yyyy-MM-dd
     * @param add int 年份添加值
     * @return String 年份添加后的日期
     */
    public static String yearhAdd(String date, int add) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            c.setTime(sdf.parse(date));
            c.add(c.YEAR, add);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdf.format(c.getTime());
    }
    /**
     * 返回两个日期的差
     * sdate1-sdate2
     * @param sdate1 String 日期 会以yyyy-MM-dd 转为 java.util.date
     * @param sdate2 String 日期 会以yyyy-MM-dd 转为java.util.date
     * @return long
     */
    public static long dateDiff(String sdate1, String sdate2) {
        long diff = 0;
        Date date1 = null;
        Date date2 = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date1 = format.parse(sdate1);
            date2 = format.parse(sdate2);
            diff = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            System.out.println("dateDiff>>"+e.getMessage());
            //e.printStackTrace();
        }
        return diff;
    }
    /**
     * 按指定日期单位计算两个日期间的间隔
     * @param timeInterval
     *  总共支持year,quarter,month,week,day,hour,minute,second这几种时间间隔
     *  间隔 = date1-date2
     * @param date1 Date
     * @param date2 Date
     * @return long
     */
    public static long dateDiff(String timeInterval, Date date1,
                                Date date2){
        try {
            if ("year".equals(timeInterval)) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date1);
                int time = calendar.get(Calendar.YEAR);
                calendar.setTime(date2);
                return time - calendar.get(Calendar.YEAR);
            }

            if ("quarter".equals(timeInterval)) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date1);
                int time = calendar.get(Calendar.YEAR) * 4;
                calendar.setTime(date2);
                time -= calendar.get(Calendar.YEAR) * 4;
                calendar.setTime(date1);
                time += calendar.get(Calendar.MONTH) / 4;
                calendar.setTime(date2);
                return time - calendar.get(Calendar.MONTH) / 4;
            }

            if ("month".equals(timeInterval)) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date1);
                int time = calendar.get(Calendar.YEAR) * 12;
                calendar.setTime(date2);
                time -= calendar.get(Calendar.YEAR) * 12;
                calendar.setTime(date1);
                time += calendar.get(Calendar.MONTH);
                calendar.setTime(date2);
                return time - calendar.get(Calendar.MONTH);
            }

            if ("week".equals(timeInterval)) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date1);
                int time = calendar.get(Calendar.YEAR) * 52;
                calendar.setTime(date2);
                time -= calendar.get(Calendar.YEAR) * 52;
                calendar.setTime(date1);
                time += calendar.get(Calendar.WEEK_OF_YEAR);
                calendar.setTime(date2);
                return time - calendar.get(Calendar.WEEK_OF_YEAR);
            }

            if ("day".equals(timeInterval)) {
                return ((date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000));
            }

            if ("hour".equals(timeInterval)) {
                long time = date1.getTime() / 1000 / 60 / 60;
                return time - date2.getTime() / 1000 / 60 / 60;
            }

            if ("minute".equals(timeInterval)) {
                long time = date1.getTime() / 1000 / 60;
                return time - date2.getTime() / 1000 / 60;
            }
            if ("second".equals(timeInterval)) {
                long time = date1.getTime() / 1000;
                return (time - date2.getTime() / 1000);
            }
            if ("millsecond".equals(timeInterval)) {
                long time = date1.getTime();
                return (time - date2.getTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date1.getTime() - date2.getTime();
    }
    /**
     * 两个月份之间的相隔
     * month1-month2
     * @param month1 String 月份 yyyy-MM
     * @param month2 String 月份yyyy-MM
     * @return long
     * @throws Exception
     */
    public static long monthDateDiff(String month1, String month2) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date date1 = format.parse(month1);
        Date date2 = format.parse(month2);;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        int time = calendar.get(Calendar.YEAR) * 12;
        calendar.setTime(date2);
        time -= calendar.get(Calendar.YEAR) * 12;
        calendar.setTime(date1);
        time += calendar.get(Calendar.MONTH);
        calendar.setTime(date2);
        return time - calendar.get(Calendar.MONTH);
    }
    /**
     * 把字符串的日期按照相应的格式转换为 calendar对象
     * @param dateStr String 日期时间
     * @param pattern String 日期时间格式
     * @return Calendar 转换后
     * @throws ParseException
     */
    public static Calendar parseString(String dateStr, String pattern) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = sdf.parse(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
    /**
     * 转换日期时间格式
     * @param date String 字符串 表示的时间
     * @param pattern String 当前传入字符串的时间格式
     * @return  Date 返回一个java.util.Date对象
     * @throws ParseException
     */

    public static Date parseDate(String date, String pattern) throws ParseException{
        if("".equals(fMstr(date))){
            return null;
        }
        if(pattern.equals(Date_Format)){
            if(!checkDate(date)){
                return null;
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return  sdf.parse(date);
    }

    public static String fMstr(String param) {
        if (param == null) {
            return "";
        }
        return param.trim();
    }

    /**
     * 验证只能为yyyy-dd-mm或者yyyy/dd/mm日期格式
     * @param value
     * @return boolean 如为日期返回true 否则返回false
     */
    public static boolean checkDate(String value){
        value = fMstr(value);
        if(value.equals("")){
            return false;
        }
        value = value.replaceAll("/", "-");
        String vals[] = value.split("-");
        if(vals.length != 3){
            return false;
        }
        if(vals[0].length() != 4){
            return false;
        }
        if(vals[1].length() <= 0){
            return false;
        }
        if(vals[2].length() <= 0){
            return false;
        }
        return true;
    }

}
