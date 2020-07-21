package com.hirisun.cloud.platform.document.service.impl;

import com.hirisun.cloud.platform.document.bean.UserDoc;
import com.hirisun.cloud.platform.document.mapper.UserDocMapper;
import com.hirisun.cloud.platform.document.service.UserDocService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

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
}
