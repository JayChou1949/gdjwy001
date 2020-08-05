package com.hirisun.cloud.platform.information.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.platform.information.bean.News;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;

/**
 * <p>
 * 新闻资讯 服务类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-14
 */
public interface NewsService extends IService<News> {

    Page selectPage(Page page, LambdaQueryWrapper queryWrapper);

    /**
     * 置顶操作
     */
    void setTop(News news,Integer type);

    void deleteNews(News news,UserVO user);

}
