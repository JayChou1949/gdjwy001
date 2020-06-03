package com.upd.hwcloud.service.impl.webManage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.upd.hwcloud.bean.contains.ReviewStatus;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.webManage.News;
import com.upd.hwcloud.common.utils.NewsCarouselUtil;
import com.upd.hwcloud.dao.webManage.NewsMapper;
import com.upd.hwcloud.service.webManage.INewsService;
import com.upd.hwcloud.service.workbench.impl.CommonHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * 新闻资讯 服务实现类
 * </p>
 *
 * @author huru
 * @since 2018-10-26
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements INewsService {

    @Autowired
    NewsMapper newsMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publish(User user, String id, Integer result, String remark) {
        News news = this.getById(id);
        if (result.equals(1)) { // 上线
            news.setStatus(ReviewStatus.ONLINE.getCode());
            news.updateById();
        } else { // 下线
            news.setStatus(ReviewStatus.PRO_ONLINE.getCode());
            news.updateById();
        }
    }

    @Override
    public Page<News> getPage(Page<News> page, User user, Integer status, String title,Integer type,String belong) {
        Map<String,Object> param = NewsCarouselUtil.handlerOfParam(type,belong);
        return newsMapper.getPage(page,user,status,title,param);
    }

    @Override
    public Page<News> getNewsByPage(Page<News> page,Integer type,String belong) {

        Map<String,Object> param = NewsCarouselUtil.handlerOfParam(type,belong);
        return newsMapper.getNewsByPage(page,param);
    }




    @Override
    public void updateViewCountById(String id) {
        newsMapper.updateViewCountById(id);
    }
}
