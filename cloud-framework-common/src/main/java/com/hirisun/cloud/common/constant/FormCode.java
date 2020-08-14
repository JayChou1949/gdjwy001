package com.hirisun.cloud.common.constant;

import com.hirisun.cloud.common.contains.ResourceType;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FormCode {

    public static Map<String, Integer> serviceResourceTypeValueMap = new ConcurrentHashMap<>();

    public static Map<Integer, String> serviceResourceTypeKeyMap = new ConcurrentHashMap<>();

    static {
        /**
         * 1.服务名对应服务类型
         * 2.服务类型对应服务名
         */

        serviceResourceTypeValueMap.put("IAAS_TXYFW", ResourceType.IAAS.getCode());

        serviceResourceTypeKeyMap.put(ResourceType.IAAS.getCode(),"IAAS_TXYFW");
    }

}
