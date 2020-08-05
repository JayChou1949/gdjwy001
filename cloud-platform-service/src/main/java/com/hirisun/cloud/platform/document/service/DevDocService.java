package com.hirisun.cloud.platform.document.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.platform.document.bean.DevDoc;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.platform.document.bean.DevDocClass;

import java.util.Map;

/**
 * <p>
 * 文档 服务类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-20
 */
public interface DevDocService extends IService<DevDoc> {

    Page<DevDoc> getPage(Page<DevDoc> page,UserVO userVO, Map paramMap);

    void saveOrUpdateDevDoc(UserVO userVO,DevDoc devDoc);

}
