package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.contains.ReviewStatus;
import com.upd.hwcloud.bean.entity.Files;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.UserDoc;
import com.upd.hwcloud.dao.UserDocMapper;
import com.upd.hwcloud.service.IFilesService;
import com.upd.hwcloud.service.IUserDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 用户操作文档管理 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2019-09-25
 */
@Service
public class UserDocServiceImpl extends ServiceImpl<UserDocMapper, UserDoc> implements IUserDocService {

    @Autowired
    private IFilesService filesService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveUserDoc(User user, UserDoc userDoc) {
        userDoc.setCreator(user.getIdcard());
        userDoc.setStatus(ReviewStatus.PRO_ONLINE.getCode());
        userDoc.setId(null);
        userDoc.insert();
        filesService.refFiles(Collections.singletonList(userDoc.getFile()), userDoc.getId());
    }

    @Override
    public IPage<UserDoc> getPage(IPage<UserDoc> page, User user, Integer status, String name, String domain) {
        return baseMapper.getPage(page, user, status, name, domain);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserDoc(UserDoc userDoc) {
        userDoc.setStatus(ReviewStatus.PRO_ONLINE.getCode());
        userDoc.updateById();
        filesService.refFiles(Collections.singletonList(userDoc.getFile()), userDoc.getId());
    }

    @Override
    public UserDoc getDetail(User user, String id) {
        UserDoc doc = this.getById(id);
        List<Files> filesList = filesService.getFilesByRefId(id);
        if (filesList != null && !filesList.isEmpty()) {
            doc.setFile(filesList.get(0));
        }
        return doc;
    }

    @Override
    public List<UserDoc> getDocsByDomain(String domain) {
        return baseMapper.getDocsByDomain(domain);
    }


}
