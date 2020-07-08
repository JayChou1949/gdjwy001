package com.hirisun.cloud.common.util;

import java.util.List;

import com.alibaba.fastjson.JSON;

public class FastJsonUtil {
	
    public static String bean2Json(Object obj) {
    	try {
    		return JSON.toJSONString(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

    public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
    	
    	try {
    		return JSON.parseObject(jsonStr, objClass);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    
    
    public static <T> List<T> json2ListBean(String jsonStr, Class<T> objClass) {
    	try {
    		return JSON.parseArray(jsonStr, objClass);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    

}