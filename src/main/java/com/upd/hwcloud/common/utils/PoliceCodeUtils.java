package com.upd.hwcloud.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 根据警种简称获取警种代码工具类
 * @author junglefisher
 * @date 2020/1/16 14:11
 */
public class PoliceCodeUtils {
    private static Map<String,String> map = new HashMap<>();

    static {
        map.put("机场公安","201205");
        map.put("纪检","2001");
        map.put("督察信访","200102");
        map.put("审计","200103");
        map.put("厅办","2002");
        map.put("国保","2004");
        map.put("治安","200601");
        map.put("刑侦","2007");
        map.put("出入境","2008");
        map.put("网安","2009");
        map.put("技侦","2010");
        map.put("监管","2011");
        map.put("交管","2012");
        map.put("法制","2013");
        map.put("警保","2015");
        map.put("禁毒","2016");
        map.put("科信","2017");
        map.put("反恐","2019");
        map.put("情报指挥","201A");
        map.put("警官学院","2022");
        map.put("培训中心","2024");
        map.put("机关处","2026");
        map.put("边防","2027");
        map.put("缉私","2030");
        map.put("政治部","2034");
        map.put("经侦","2005");
        map.put("打私","2037");
        map.put("飞行队","2015");
        map.put("制证中心","2026");
        map.put("巡察","2001");
        map.put("基建","2015");
        map.put("特勤","200601");
        map.put("水域治安","200601");
    }

    public static String getCode(String police){
        return map.get(police);
    }
}
