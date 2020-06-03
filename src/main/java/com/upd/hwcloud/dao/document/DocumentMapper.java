package com.upd.hwcloud.dao.document;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.document.Document;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 文档 Mapper 接口
 * </p>
 *
 * @author huru
 * @since 2018-11-09
 */
public interface DocumentMapper extends BaseMapper<Document> {

    Page<Document> getPage(Page<Document> page, @Param("user") User user, @Param("isFront") boolean isFront, @Param("status") Integer status,
                           @Param("name") String name, @Param("firstClass") String firstClass, @Param("secondClass") String secondClass);
}
