package com.upd.hwcloud.dao.document;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.document.DocumentClass;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 文档分类表 Mapper 接口
 * </p>
 *
 * @author huru
 * @since 2018-11-09
 */
public interface DocumentClassMapper extends BaseMapper<DocumentClass> {

    Page<DocumentClass> getPage(Page page, @Param("user") User user, @Param("type") Integer type, @Param("name") String name);
}
