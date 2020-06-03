package com.upd.hwcloud.service.webManage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.webManage.Carousel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.webManage.News;
import com.upd.hwcloud.bean.response.R;

import java.util.List;

/**
 * <p>
 * 轮播图 服务类
 * </p>
 *
 * @author huru
 * @since 2018-10-26
 */
public interface ICarouselService extends IService<Carousel> {

    /**
     * 上/下线
     * @param result 操作结果 1:上线,其它:下线
     * @param remark 操作描述
     */
    void publish(User user, String id, Integer result, String remark);

    /**
     * 手动排序
     * @param type
     * @param id
     */
    R move(String type, String id, Integer provincial, String belong);

    Page<Carousel> getPage(Page<Carousel> page, User user, Integer status, String title,Integer type,String belong);

    List<Carousel> getIndexCarousel(Integer type, String belong);

    Page<Carousel> getCrByPage(Page<Carousel> page);

}
