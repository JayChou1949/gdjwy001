package com.hirisun.cloud.platform.document.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.common.contains.ReviewStatus;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.platform.document.bean.UserDoc;
import com.hirisun.cloud.platform.document.mapper.UserDocMapper;
import com.hirisun.cloud.platform.document.service.UserDocService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Map;

/**
 * <p>
 * 用户操作文档管理 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-20
 */
@Service
public class UserDocServiceImpl extends ServiceImpl<UserDocMapper, UserDoc> implements UserDocService {

    @Autowired
    private UserDocMapper userDocMapper;

    @Override
    public LinkedList<UserDoc> listByDomain(String domain) {
        return userDocMapper.listByDomain(domain);
    }

    @Override
    public Page getPage(Page page, UserVO user, Map map) {
        return userDocMapper.getPage(page,user,map);
    }

    @Override
    public void saveOrUpdateUserDoc(UserVO user, UserDoc userDoc) {
        if (StringUtils.isEmpty(userDoc.getId())) {
            userDoc.setCreator(user.getIdCard());
            userDoc.setStatus(ReviewStatus.PRO_ONLINE.getCode());
            this.save(userDoc);
        }else{
            userDoc.setStatus(ReviewStatus.PRO_ONLINE.getCode());
            this.updateById(userDoc);
        }
    }
}
