package com.hirisun.cloud.saas.service;

import java.util.List;
import java.util.Map;

public interface IImplHandler<T> {

    /**
     * 根据申请信息 id 查询实施服务器信息列表
     * @param appInfoId 申请信息 id
     */
    List<T> getImplServerListByAppInfoId(String appInfoId);

    /**
     * 更新实施服务器信息(先删除,后添加)
     */
    void update (String appInfoId, List<T> serverList);
    default void update(String appInfoId, List<T> serverList, Map<String,String> map){
        update(appInfoId,serverList);
    };

}
