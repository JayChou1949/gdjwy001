package com.hirisun.cloud.platform.information.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.api.system.SystemApi;
import com.hirisun.cloud.common.util.IpUtil;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.platform.information.bean.News;
import com.hirisun.cloud.platform.information.mapper.NewsMapper;
import com.hirisun.cloud.platform.information.service.NewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    @Autowired
    private SystemApi systemApi;

    @Override
    public Page selectPage(Page page, LambdaQueryWrapper queryWrapper) {
        return newsMapper.selectPage(page,queryWrapper);
//        return null;
    }

    @Override
    public void setTop(News news,Integer type) {
        //获取当前新闻类型
        if(type.equals(News.TOP_YES)){
            //更新同类型其他新闻的置顶状态 1省厅 2地址 3警种 4国家专项
            switch (news.getProvincial()) {
                case 1:
                    this.update(new News(), new UpdateWrapper<News>().lambda()
                            .eq(News::getProvincial,news.getProvincial())
                            .set(News::getIsTop, 0));
                    break;
                case 2:
                    this.update(new News(), new UpdateWrapper<News>().lambda()
                            .eq(News::getProvincial,news.getProvincial())
                            .eq(News::getArea,news.getArea())
                            .set(News::getIsTop, 0));
                    break;
                case 3:
                    this.update(new News(), new UpdateWrapper<News>().lambda()
                            .eq(News::getProvincial,news.getProvincial())
                            .eq(News::getPoliceCategory,news.getPoliceCategory())
                            .set(News::getIsTop, 0));
                    break;
                case 4:
                    this.update(new News(), new UpdateWrapper<News>().lambda()
                            .eq(News::getProvincial,news.getProvincial())
                            .eq(News::getProject,news.getProject())
                            .set(News::getIsTop, 0));
                    break;
                default:
                    break;
            }

        }
        String id = news.getId();
        news=new News();
        news.setId(id);
        news.setUpdateTime(new Date());
        news.setIsTop(type);
        this.updateById(news);
    }

    @Override
    public void deleteNews(News news,UserVO user) {
        this.updateById(news);
        // 远程调用日志模块，记录操作人日志 sys_log
        systemApi.saveLog(user.getIdcard(),"删除新闻，新闻id："+news.getId(),"删除新闻", IpUtil.getIp());
    }


}
