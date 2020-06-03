package com.upd.hwcloud.service.application;

import com.upd.hwcloud.bean.entity.application.ApplicationInfo;

public interface IDaasApigService {

    /**
     * 业务办理,批量订购
     * @param info
     * @throws Exception
     */
    void orderService(ApplicationInfo info) throws Exception;

    /**
     * 单独订购
     * @param daasApplicationId
     * @throws Exception
     */
    void orderService(String daasApplicationId) throws Exception;

}
