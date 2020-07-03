package com.hirisun.cloud.model.third.dto;

//import org.joda.time.DateTime;
//import org.joda.time.Duration;
//
//import java.util.Date;
//
//public class RedPoint {
//
//    /**
//     * 时间段
//     */
//    private Integer during;
//
//    /**
//     * 单位 1:天 2:小时
//     */
//    private Integer unit;
//
//    public Integer getDuring() {
//        return during;
//    }
//
//    public void setDuring(Integer during) {
//        this.during = during;
//    }
//
//    public Integer getUnit() {
//        return unit;
//    }
//
//    public void setUnit(Integer unit) {
//        this.unit = unit;
//    }
//
//    @Override
//    public String toString() {
//        return "RedPoint{" +
//                "during=" + during +
//                ", unit=" + unit +
//                '}';
//    }
//
//    public boolean isShow(Date date) {
//        if (date == null) {
//            return false;
//        }
//        if (during == null || during <= 0) {
//            return false;
//        }
//        if (unit == null) {
//            return false;
//        }
//
//        DateTime now = DateTime.now();
//        DateTime modify = new DateTime(date);
//
//        Duration duration = new Duration(modify, now);
//        if (unit == 1) {
//            long days = duration.getStandardDays();
//            return days < during;
//        } else if (unit == 2) {
//            long hours = duration.getStandardHours();
//            return hours < during;
//        }
//        return false;
//    }

//}
