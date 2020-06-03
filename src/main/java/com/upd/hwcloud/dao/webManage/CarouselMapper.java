package com.upd.hwcloud.dao.webManage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.webManage.Carousel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upd.hwcloud.bean.entity.webManage.News;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 轮播图 Mapper 接口
 * </p>
 *
 * @author huru
 * @since 2018-10-26
 */
public interface CarouselMapper extends BaseMapper<Carousel> {

    Page<Carousel> getPage(Page<Carousel> page, @Param("user") User user, @Param("status") Integer status, @Param("title") String title, @Param("p")Map<String,Object> param);


    int updateModifiedTimeAndSort(@Param("time") Date time,@Param("sort") Long  sort,@Param("id") String id);

    List<Carousel> getAllOnlineOfType(@Param("p") Map<String,Object> param);

    List<Carousel> getIndexCarouse(@Param("p") Map<String,Object> param);

    Page<Carousel> getCrByPage(Page<Carousel> page);
}
