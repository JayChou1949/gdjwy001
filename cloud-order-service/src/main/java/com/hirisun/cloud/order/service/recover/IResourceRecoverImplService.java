package com.hirisun.cloud.order.service.recover;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.order.bean.recover.ResourceRecoverImpl;

import java.util.Map;

/**
 * 资源回收实施表 服务类
 */
public interface IResourceRecoverImplService extends IService<ResourceRecoverImpl> {

    void saveImpl(UserVO user, Map<String, Object> param, String modelid) throws Exception;

}
