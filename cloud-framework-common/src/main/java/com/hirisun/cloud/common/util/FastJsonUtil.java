package com.hirisun.cloud.common.util;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.hirisun.cloud.model.ncov.vo.daas.DataGovernanceLevel2Vo;

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
    
    public static Object json2ListBean2(String jsonStr, Class<List<Object>> objClass) {
    	
    	try {
    		return JSON.parseArray(jsonStr, objClass);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }

	public static List<List<Object>> json2ListBean2(String jsonStr,
			TypeReference<List<List<Object>>> typeReference) {
		
		try {
    		return JSON.parseObject(jsonStr, typeReference);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
		
	}

	public static Object json2Bean(String dataGovernanceStr,
			TypeReference<List<Map<String, List<DataGovernanceLevel2Vo>>>> typeReference) {
		// TODO Auto-generated method stub
		return null;
	}
}