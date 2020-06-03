package com.upd.hwcloud.service.application;

import com.upd.hwcloud.bean.dto.apig.ServiceReturnBean;
import com.upd.hwcloud.bean.entity.ServicePublish;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;

import java.io.IOException;
import java.util.Map;

public interface IPaasApigService {
    <I>   Map<String, String> orderService(ApplicationInfo info,IImplHandler<I> implHandler) throws Exception;

   <I> Map<String, String> mapOrderService(ApplicationInfo info,IImplHandler<I> implHandler) throws Exception;

    ServiceReturnBean apigOrderService(ServicePublish servicePublish) throws Exception;

    void addTYYHMessage(ApplicationInfo info);
}
