package com.hirisun.cloud.common.util;

import java.time.LocalDate;
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
	
	public static void main(String[] args) {
		Date convertDate = convertDate("2020-06-23", yyyyMMdd);
		System.out.println(convertDate.toString());
	}
	
}
