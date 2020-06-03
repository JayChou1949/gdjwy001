package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.UserDoc;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户操作文档管理 Mapper 接口
 * </p>
 *
 * @author wuc
 * @since 2019-09-25
 */
public interface UserDocMapper extends BaseMapper<UserDoc> {

    IPage<UserDoc> getPage(IPage<UserDoc> page, @Param("user") User user, @Param("status") Integer status,
                           @Param("name") String name, @Param("domain") String domain);

    List<UserDoc> getDocsByDomain(@Param("domain") String domain);

}
