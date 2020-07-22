package com.hirisun.cloud.platform.document.mapper;

import com.hirisun.cloud.platform.document.bean.UserDoc;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedList;

/**
 * <p>
 * 用户操作文档管理 Mapper 接口
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-20
 */
public interface UserDocMapper extends BaseMapper<UserDoc> {
    LinkedList<UserDoc> listByDomain(@Param("domain") String domain);
}
