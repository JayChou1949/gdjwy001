package com.hirisun.cloud.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @ Author：xqp
 * @ Description：IAAS、PAAS、SAAS服务类型编码
 * @ Date：Created in 11:30 2020/8/14
 */
public class ServiceCodeUtil {

    private static Map<String,String> map = new HashMap<>();

    static {
        //IAAS服务
        map.put("ECS（弹性云服务器）","0101");
        map.put("BMS（裸金属服务器）","0102");
        map.put("IMS（镜像服务）","0103");
        map.put("AS（弹性伸缩）","0104");
        map.put("EVS（云硬盘）","0105");
        map.put("OBS（对象存储服务）","0106");
        map.put("ELB（弹性负载均衡）","0107");
        map.put("EIP（弹性IP）","0108");
        map.put("FusionAccess（桌面云）","0109");
        map.put("容器服务","0110");
        map.put("SFS（弹性文件服务）","0111");

        //PAAS服务
        map.put("基础服务","0201");
        map.put("应用支撑服务","0202");
        map.put("视频专项服务","0203");
        map.put("安全服务","0204");
        map.put("移动警务服务","0205");

        //SAAS服务
        map.put("政府机构应用","0401");
        map.put("专项应用","0402");
        map.put("疫情专区","0403");
        map.put("通用应用","0404");
        map.put("警种应用","0405");
        map.put("地市应用","0406");

    }

    public static String getCode(String category){
        return map.get(category);
    }
}
