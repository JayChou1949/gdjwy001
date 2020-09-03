package com.hirisun.cloud.order.mapper.register;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.model.user.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * SAAS服务注册表 Mapper 接口
 * </p>
 *
 * @author zwb
 * @since 2019-05-27
 */
public interface RegisterMapper<T> extends BaseMapper<T> {
    T getDetails(String id);

    IPage<T> getPage(IPage<T> page, @Param("p") Map<String, Object> param);

    IPage<T> getResponsePage(IPage<T> page, @Param("name") String name, @Param("orgName") String orgName, @Param("idCard") String idCard, @Param("appName") String appName, @Param("phone") String phone);

    int getTodoCount(@Param("user") UserVO user);

    int getRegisterTodo(@Param("idCard") String idCard);


}
