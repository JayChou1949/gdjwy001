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
     * 申请单号前缀
     */
    String KEY_ORDER_NUM_PREFIX = "OrderNum:";
    
    /**
     * daas列表配置
     */
    String KEY_DAAS_COLUMN_CONFIG = "daas:column:config";

    /**
     * 服务发布申请单号前缀
     */
    String KEY_PUBLISH_PREFIX = "PublishOrdNum:";

    /**
     * SAAS服务注册回收申请单号前缀
     */
    String KEY_SAAS_PREFIX = "SaasOrdNum:";

}
