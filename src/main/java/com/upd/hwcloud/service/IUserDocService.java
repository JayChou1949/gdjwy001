package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.UserDoc;

import java.util.List;

/**
 * <p>
 * 用户操作文档管理 服务类
 * </p>
 *
 * @author wuc
 * @since 2019-09-25
 */
public interface IUserDocService extends IService<UserDoc> {

    void saveUserDoc(User user, UserDoc userDoc);

    IPage<UserDoc> getPage(IPage<UserDoc> page, User user, Integer status, String name, String domain);

    void updateUserDoc(UserDoc userDoc);

    UserDoc getDetail(User user, String id);

    List<UserDoc> getDocsByDomain(String domain);

}
