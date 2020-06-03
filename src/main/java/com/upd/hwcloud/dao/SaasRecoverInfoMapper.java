package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upd.hwcloud.bean.entity.SaasRecoverInfo;
import com.upd.hwcloud.bean.entity.User;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangbao
 * @since 2019-10-24
 */
public interface SaasRecoverInfoMapper extends BaseMapper<SaasRecoverInfo> {

    List<SaasRecoverInfo> getUserUseService(@Param("id") String id);


    int getTodoCount(@Param("user") User user);

    int getApplicationTodo(@Param("idCard") String idCard);

}
