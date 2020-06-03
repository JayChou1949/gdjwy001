package com.upd.hwcloud.service.application;

import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.ResourceRecoverImpl;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 资源回收实施表 服务类
 * </p>
 *
 * @author yyc
 * @since 2020-05-17
 */
public interface IResourceRecoverImplService extends IService<ResourceRecoverImpl> {

    void saveImpl(User user, Map<String, Object> param, String modelid) throws Exception;

}
