package com.upd.hwcloud.bean.contains;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by zwb on 2019/5/14.
 */
public enum MapType {

    PGIS_SL("4591","PGIS","PGIS矢量地图"),
    PGIS_YX("4592","PGIS","PGIS影像地图"),
    PGIS_SY("4593","PGIS","PGIS矢影地图"),

    SMAP_SL("4594","SMAP","SMAP矢量地图"),
    SMAP_YX("4595","SMAP","SMAP影像地图"),
    SMAP_SY("4596","SMAP","SMAP矢影地图"),
    SMAP_WY("4597","SMAP","SMAP午夜蓝地图"),

    CMAP_SL("4598","CMAP","CMAP矢量地图"),
    CMAP_YX("4599","CMAP","CMAP影像地图"),
    CMAP_SY("4600","CMAP","CMAP矢影地图"),

    AMAP_SL("4601","AMAP","AMAP矢量地图"),
    AMAP_YX("4602","AMAP","AMAP影像地图"),
    AMAP_SY("4603","AMAP","AMAP矢影地图");
        String code;
        String desc;
        String type;
    MapType(String code, String type,String desc) {

        this.code=code;
        this.desc = desc;
        this.type=type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public static List<String> getCodes(){
        return Arrays.stream(values()).map(t->t.getType()).collect(Collectors.toList());
    }

    public static List<Map> getMap(){
        return Arrays.stream(values()).map(t->{
            Map map = new HashMap();
           map.put("code",t.getCode());
           map.put("type",t.getType());
           map.put("desc",t.getDesc());
            return map;
        }).collect(Collectors.toList());
    }
}
