package com.hirisun.cloud.platform.information.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.platform.information.bean.Carousel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 轮播图 服务类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-15
 */
public interface CarouselService extends IService<Carousel> {

    /**
     * 上/下移动轮播图位置
     * @param type  up/down 表示上/下 移动
     * @param id 新闻id
     * @param provincial 类别
     * @param belong 类别所属
     */
    QueryResponseResult<Carousel> movePosition(String type, String id, Integer provincial, String  belong);

    List<Carousel> allList(Integer type,String belong);

    Page<Carousel> getPage(Integer pageNum, Integer pageSize,Integer status,Integer type,String belong,String title);

    Carousel getCarouselDetail(String id);
}
