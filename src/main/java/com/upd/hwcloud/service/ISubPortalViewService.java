package com.upd.hwcloud.service;

import com.upd.hwcloud.bean.entity.SubPortalView;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.response.R;

/**
 * <p>
 * 二级门户浏览量 服务类
 * </p>
 *
 * @author yyc
 * @since 2019-12-20
 */
public interface ISubPortalViewService extends IService<SubPortalView> {

    R incrementCount(String type, String name);

    R getViewCount(String type,String name);

}
