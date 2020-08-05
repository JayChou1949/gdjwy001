package com.hirisun.cloud.platform.document.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.platform.document.bean.DevDocClass;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文档分类表 服务类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-20
 */
public interface DevDocClassService extends IService<DevDocClass> {
    Page getPage(Page page, UserVO user, Map map);

}
