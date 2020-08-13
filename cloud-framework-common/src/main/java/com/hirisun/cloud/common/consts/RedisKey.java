package com.hirisun.cloud.common.consts;

/**
 * @author wuxiaoxing
 *
 * redis常量定义
 */
public interface RedisKey {

    public static String REDIS_SYS_DICT="REDIS_SYS_DICT";

    public static String MONITOR_LOG="MONITOR_LOG";

    public static String ABNORMAL_LOG="ABNORMAL_LOG";
    
    /**
     * daas列表配置
     */
    String KEY_DAAS_COLUMN_CONFIG = "daas:column:config";

}
