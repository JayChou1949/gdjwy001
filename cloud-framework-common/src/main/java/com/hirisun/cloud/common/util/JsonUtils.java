package com.hirisun.cloud.common.util;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hirisun.cloud.model.ncov.vo.daas.DataGovernanceLevel2Vo;

public class JsonUtils {
	
     private static final ObjectMapper MAPPER = new ObjectMapper();
 
     public static String objectToJson(Object data) {
         try {
             String result = MAPPER.writeValueAsString(data);
             return result;
         } catch (JsonProcessingException e) {
             e.printStackTrace();
         }
         return null;
     }
 
     public static <T> T jsonToBean(String jsonData, Class<T> beanType) {
         try {
             T result = MAPPER.readValue(jsonData, beanType);
             return result;
         } catch (Exception e) {
             e.printStackTrace();
         }
 
         return null;
     }
     
     public static <T> T voToBean(Object obj, Class<T> beanType) {
    	 
    	 String objectToJson = objectToJson(obj);
         try {
             T result = MAPPER.readValue(objectToJson, beanType);
             return result;
         } catch (Exception e) {
             e.printStackTrace();
         }
 
         return null;
     }
     
     public static <T> T jsonToMapList(String jsonData, TypeReference<T> beanType) {
         try {
             T result = MAPPER.readValue(jsonData, beanType);
             return result;
         } catch (Exception e) {
             e.printStackTrace();
         }
 
         return null;
     }
 
     public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
         JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
 
         try {
             List<T> resultList = MAPPER.readValue(jsonData, javaType);
             return resultList;
         } catch (Exception e) {
             e.printStackTrace();
         }
 
         return null;
     }
     
     public static <T> T jsonToMap(String jsonData, TypeReference<T> beanType) {
         try {
             T result = MAPPER.readValue(jsonData, beanType);
             return result;
         } catch (Exception e) {
             e.printStackTrace();
         }
 
         return null;
     }
     
     public static <K, V> Map<K, V> jsonToMap(String jsonData, Class<K> keyType, Class<V> valueType) {
         JavaType javaType = MAPPER.getTypeFactory().constructMapType(Map.class, keyType, valueType);
 
         try {
             Map<K, V> resultMap = MAPPER.readValue(jsonData, javaType);
             return resultMap;
         } catch (Exception e) {
             e.printStackTrace();
         }
 
         return null;
     }
     
     public static void main(String[] args) {
		
//    	 String json = "{\"updateCycleVos\":[{\"num\":\"42\",\"percentage\":\"0.4667\",\"sum\":\"90\",\"type\":\"实时\"},{\"num\":\"19\",\"percentage\":\"0.2111\",\"sum\":\"90\",\"type\":\"按天\"},{\"num\":\"9\",\"percentage\":\"0.1\",\"sum\":\"90\",\"type\":\"按月\"},{\"num\":\"20\",\"percentage\":\"0.2222\",\"sum\":\"90\",\"type\":\"一次性\"}],\"updateTypeVos\":[{\"num\":\"57\",\"percentage\":\"0.6333\",\"sum\":\"90\",\"type\":\"自动同步\"},{\"num\":\"23\",\"percentage\":\"0.2556\",\"sum\":\"90\",\"type\":\"文件拷贝\"}]}";
//    	 
//    	 TypeReference<Map<String,List<DataGovernanceLevel2Vo>>> typeReference = new TypeReference<Map<String,List<DataGovernanceLevel2Vo>>>() {};
//    	 Map<String, List<DataGovernanceLevel2Vo>> readValue = jsonToMapList(json,typeReference);
//		 System.out.println(readValue);
    	 
	}
     
     
 }