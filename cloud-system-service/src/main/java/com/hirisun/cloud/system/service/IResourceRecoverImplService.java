package com.hirisun.cloud.system.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.system.bean.ResourceRecoverImpl;

/**
 * 资源回收实施表 服务类
 */
public interface IResourceRecoverImplService extends IService<ResourceRecoverImpl> {

    void saveImpl(UserVO user, Map<String, Object> param, String modelid) throws Exception;

}
