package com.upd.hwcloud.service.impl.document;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.document.DocumentClass;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.dao.document.DocumentClassMapper;
import com.upd.hwcloud.service.document.IDocumentClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文档分类表 服务实现类
 * </p>
 *
 * @author huru
 * @since 2018-11-09
 */
@Service
public class DocumentClassServiceImpl extends ServiceImpl<DocumentClassMapper, DocumentClass> implements IDocumentClassService {

    @Autowired
    DocumentClassMapper documentClassMapper;

    @Override
    public Page getPage(Page page, User user, Integer type, String name) {
        return documentClassMapper.getPage(page,user,type,name);
    }
}
