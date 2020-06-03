package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.Register;
import com.upd.hwcloud.bean.entity.Saas;
import com.upd.hwcloud.bean.entity.SaasRegister;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.vo.workbench.QueryVO;

import org.apache.ibatis.annotations.Param;

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
    void save(User user, T info);

    T getDetails(String id);

    IPage<T> getPage(User user, IPage<T> page, Map<String, Object> param);

    IPage<T> getResponsePage(IPage<T> page, User user,String appName);


    void update(User user, T info);
    void saveService(T register);

    int getTodoCount(User user);

    int getRegisterTodo(String idCard);

}
