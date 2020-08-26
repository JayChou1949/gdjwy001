package com.hirisun.cloud.platform.document.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.model.platform.vo.UserDocVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.platform.document.bean.UserDoc;

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
    
    List<UserDocVo> findByDomain(String domain);

    Page getPage(Page page, UserVO user, Map map);

    void saveOrUpdateUserDoc(UserVO userVO, UserDoc userDoc);

}
