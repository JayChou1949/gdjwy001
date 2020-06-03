package com.upd.hwcloud.bean.contains;

public interface RedisKey {

    /**
     * 门户首页访问量
     */
    String KEY_APP_VIEW_COUNT = "AppViewCount";
    /**
     * api网关token
     */
    String KEY_TOKEN_APIG = "ApigToken";
    /**
     * daas列表配置
     */
    String KEY_DAAS_COLUMN_CONFIG = "daas:column:config";

    /**
     * 申请单号前缀
     */
    String KEY_ORDER_NUM_PREFIX = "OrderNum:";
    /**
     * IAAS资源上报申请单号前缀
     */
    String KEY_IAAS_PREFIX = "IaasOrderNum:";
    /**
     * SAAS服务注册回收申请单号前缀
     */
    String KEY_SAAS_PREFIX = "SaasOrdNum:";
    /**
     * 服务发布申请单号前缀
     */
    String KEY_PUBLISH_PREFIX = "PublishOrdNum:";

    String KEY_RESOURCE_RECOVER = "resourceRecover";
}
