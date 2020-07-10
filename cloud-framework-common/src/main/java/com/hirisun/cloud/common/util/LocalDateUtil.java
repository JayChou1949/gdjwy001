package com.hirisun.cloud.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class LocalDateUtil {

	public final static String yyyyMMdd = "yyyy-MM-dd";
	public final static String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
	
	public static Date convertDate(String dateStr,String format) {
		
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(format);     
        LocalDate localDate = LocalDate.parse(dateStr, fmt);
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
		
	}
	
	/**
	 * 获取当前年月日时分秒
	 * @return
	 */
	public static String getCurrentDateTime() {
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern(yyyyMMddHHmmss);
		return fmt.format(LocalDateTime.now());
	}
	
	/**
	 * 获取当前年月日
	 * @return
	 */
	public static String getCurrentDate() {
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern(yyyyMMdd);
		return fmt.format(LocalDateTime.now());
	}
	
	public static void main(String[] args) {
		Date convertDate = convertDate("2020-06-23", yyyyMMdd);
		System.out.println(convertDate.toString());
		System.out.println(getCurrentDateTime());
	}
	
}
