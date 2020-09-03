package com.hirisun.cloud.order.service.register;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.order.bean.register.Register;

import java.util.Map;

/**
 * <p>
 * SAAS服务注册表 服务类
 * </p>
 *
 * @author zwb
 * @since 2019-05-27
 */
public interface IRegisterService<T extends Register> extends IService<T> {
    void save(UserVO user, T info);

    T getDetails(String id);

    IPage<T> getPage(UserVO user, IPage<T> page, Map<String, Object> param);

    IPage<T> getResponsePage(IPage<T> page, UserVO user, String appName);


    void update(UserVO user, T info);
    void saveService(T register);

    int getTodoCount(UserVO user);

    int getRegisterTodo(String idCard);

}
