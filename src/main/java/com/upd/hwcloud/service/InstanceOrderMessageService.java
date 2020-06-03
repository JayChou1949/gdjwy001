package com.upd.hwcloud.service;

import com.upd.hwcloud.bean.entity.InstanceOrderMessage;
import com.upd.hwcloud.bean.entity.ServiceInstance;

import java.util.List;

/**
 * @author junglefisher
 * @date 2019/11/12 18:08
 */
public interface InstanceOrderMessageService{

    List<InstanceOrderMessage> getMessageByGuid(String guid);

    List<ServiceInstance> getServiceInstanceByProid(String projectId);

    List<InstanceOrderMessage> addKeyAndSecret(List<InstanceOrderMessage> instanceOrderMessageList) throws Exception;
}
