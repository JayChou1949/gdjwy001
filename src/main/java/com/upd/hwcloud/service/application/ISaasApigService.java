package com.upd.hwcloud.service.application;

import com.upd.hwcloud.bean.entity.application.ApplicationInfo;


/**
 * @author junglefisher
 * @date 2020/6/18 11:11
 */
public interface ISaasApigService {

    void orderService(ApplicationInfo info) throws Exception;
}
