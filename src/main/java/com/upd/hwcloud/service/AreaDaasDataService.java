package com.upd.hwcloud.service;

import com.upd.hwcloud.bean.dto.AreaDaasData;

/**
 * @author junglefisher
 * @date 2020/6/14 11:23
 */
public interface AreaDaasDataService {

    /**
     * 插入地市Daas服务数据
     * @param areaDaasData
     */
    void importDaasData(AreaDaasData areaDaasData);
}
