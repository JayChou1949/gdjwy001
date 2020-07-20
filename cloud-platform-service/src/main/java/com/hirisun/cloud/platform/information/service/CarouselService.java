package com.hirisun.cloud.platform.information.service;

import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.platform.information.bean.Carousel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 轮播图 服务类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-15
 */
public interface CarouselService extends IService<Carousel> {

    QueryResponseResult<Carousel> movePosition(String type, String id, Integer provincial, String  belong);

}
