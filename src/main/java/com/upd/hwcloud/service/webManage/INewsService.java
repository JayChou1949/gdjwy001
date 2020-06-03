package com.upd.hwcloud.service.webManage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.webManage.Apply;
import com.upd.hwcloud.bean.entity.webManage.News;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 新闻资讯 服务类
 * </p>
 *
 * @author huru
 * @since 2018-10-26
 */
public interface INewsService extends IService<News> {

    /**
     * 上/下线
     * @param result 操作结果 1:上线,其它:下线
     * @param remark 操作描述
     */
    void publish(User user, String id, Integer result, String remark);

    Page<News> getPage(Page<News> page, User user, Integer status, String title,Integer type,String belong);

    Page<News> getNewsByPage(Page<News> page,Integer type,String belong);

    void updateViewCountById(String id);

}
