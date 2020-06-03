package com.upd.hwcloud.common.utils;

/**
 * @author wuc
 * @date 2019/12/18
 */

import com.google.common.collect.Maps;
import com.upd.hwcloud.service.workbench.impl.CommonHandler;

import java.util.Map;

/**
 *
 */
public class NewsCarouselUtil {


    /**
     * 处理查询省厅地市警种参数
     * @param type
     * @param belong
     * @return
     */
    public static Map<String,Object> handlerOfParam(Integer type, String belong){
        Map<String,Object> param = Maps.newHashMap();
        param.put("province",type);
        if(type == 1){
            param.put("police","");
            param.put("area", "");
            param.put("project","");
        }
        else if(type ==2){
            param.put("police","");
            param.put("area", CommonHandler.dealNameforQuery(belong));
            param.put("project","");
        }else if(type ==3){
            param.put("police",CommonHandler.dealNameforQuery(belong));
            param.put("area", "");
            param.put("project","");
        }else if(type == 4){
            param.put("police","");
            param.put("area", "");
            param.put("project",CommonHandler.dealNameforQuery(belong));
        }
        return param;
    }
}
