package com.upd.hwcloud.dao.webManage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.webManage.Apply;
import com.upd.hwcloud.bean.entity.webManage.News;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 新闻资讯 Mapper 接口
 * </p>
 *
 * @author huru
 * @since 2018-10-26
 */
public interface NewsMapper extends BaseMapper<News> {

    Page<News> getPage(Page<News> page, @Param("user") User user, @Param("status") Integer status, @Param("title") String title,@Param("p") Map<String,Object> param);

    Page<News> getNewsByPage(Page<News> page,@Param("p") Map<String,Object> param);

    void updateViewCountById(@Param("id") String id);

}
