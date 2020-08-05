package com.hirisun.cloud.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.user.bean.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-23
 */
public interface UserMapper extends BaseMapper<User> {

    IPage<User> getPage(IPage<User> page, @Param("param") Map map);

}
