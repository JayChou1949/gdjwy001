package com.hirisun.cloud.platform.document.service;

import com.hirisun.cloud.platform.document.bean.UserDoc;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedList;

/**
 * <p>
 * 用户操作文档管理 服务类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-20
 */
public interface UserDocService extends IService<UserDoc> {

    LinkedList<UserDoc> listByDomain(String domain);

}
