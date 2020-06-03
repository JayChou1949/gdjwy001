package com.upd.hwcloud.common.utils;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AreaPoliceCategoryUtils {

    private static final List<String> areaName = Arrays.asList(
            "广州", "深圳", "珠海", "汕头", "佛山",
            "韶关", "湛江", "肇庆", "江门", "茂名",
            "惠州", "梅州", "汕尾", "河源", "阳江",
            "清远", "东莞", "中山", "潮州", "揭阳",
            "云浮");

    private static final List<String> policeCategory = Arrays.asList(
            "厅办", "出入境", "法制", "反恐", "国保",
            "基建", "纪检", "技侦", "监管", "交管",
            "禁毒", "经侦", "特勤", "警保", "督察",
            "飞行队", "科信", "培训中心", "审计", "网安",
            "刑侦", "巡察", "政治部", "治安", "机关处",
            "机场公安", "制证中心", "警官学院", "边防", "打私",
            "情报","特警"
    );

    private static final ImmutableMap<String, String> extMapping =
            ImmutableMap.<String, String>builder().put("交通管理", "交管").build();
    private static final Set<String> extPoliceCategory = extMapping.keySet();


    public static String getPoliceCategory(String appName) {
        if (StringUtils.isEmpty(appName)) {
            return null;
        }
        for (String pc : policeCategory) {//返回简称
            if (appName.contains(pc)) {
                return pc;
            }
        }
        /*for (String pc : extPoliceCategory) {//返回全称
            if (appName.contains(pc)) {
                return extMapping.get(pc);
            }
        }*/
        for(String qc :policeCategoryMapping.values() ){//遍历全称
            if (appName.contains(qc)) {
                for(String jc : policeCategory){//遍历简称
                   if( qc.equals(policeCategoryMapping.get(jc)))
                    return jc;
                }
            }
        }
        return null;
    }

    public static String getSimpleAreaName(String appName) {
        if (StringUtils.isEmpty(appName)) {
            return null;
        }
        String result =  "省厅";
        for (String area : areaName) {
            if (appName.contains(area)) {
                result = area;
                break;
            }
        }
        /*if (result != null) {
            return result;
        }*/
       /* if (appName.contains("广东")) {
            return "省厅";
        }*/
        return result;
    }

    public static String getAreaName(String appName) {
        if (appName != null){
            String simpleAreaName = getSimpleAreaName(appName);
            if (simpleAreaName != null && !"省厅".equals(simpleAreaName)) {
                simpleAreaName = simpleAreaName + "市";
                return simpleAreaName;
            }
            if (appName.contains("广东")) {
                return "省厅";
            }
        }
        return null;
    }

    public static boolean isContainAreaName(String name){
        if(areaName.contains(name)){
            return true;
        }
        return false;
    }

    public static String  getFullPoliceName(String name){
       String  fullName =  policeCategoryMapping.get(name);
       if(org.apache.commons.lang3.StringUtils.isNotEmpty(fullName)){
           return  fullName;
       }
       return name;
    }


    private static final ImmutableMap<String, String> policeCategoryMapping = ImmutableMap.<String, String>builder()
            .put("纪检", "纪委监察").put("督察", "督察信访").put("审计", "审计局").put("厅办", "办公厅（室）")
            .put("国保", "国内安全保卫").put("经侦", "经济犯罪侦查").put("治安", "治安").put("刑侦", "刑侦管理")
            .put("出入境", "出入境管理").put("网安", "网络安全保卫").put("技侦", "技术侦察").put("监管", "监所")
            .put("交管", "交通管理").put("法制", "法制").put("警保", "警务保障").put("禁毒", "禁毒管理")
            .put("科信", "科技信息化").put("反恐", "反恐怖").put("情报", "情报指挥").put("警官学院", "公安院校")
            .put("培训中心", "培训基地").put("边防", "边防管理").put("打私", "海关总署缉私").put("政治部", "政治部")
            .put("特警","特警")
            .build();

    public static ImmutableMap<String, String> getPoliceCategoryMapping() {
        return policeCategoryMapping;
    }

}
