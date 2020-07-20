package com.hirisun.cloud.platform.information.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.platform.information.bean.News;
import com.hirisun.cloud.platform.information.mapper.NewsMapper;
import com.hirisun.cloud.platform.information.service.NewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 新闻资讯 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-14
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

    @Autowired
    private NewsMapper newsMapper;

    @Override
    public Page selectPage(Page page, LambdaQueryWrapper queryWrapper) {
        return newsMapper.selectPage(page,queryWrapper);
//        return null;
    }
}
