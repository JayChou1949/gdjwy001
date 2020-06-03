package com.upd.hwcloud.service.impl.document;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.contains.ReviewStatus;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.document.Document;
import com.upd.hwcloud.dao.document.DocumentMapper;
import com.upd.hwcloud.service.IFilesService;
import com.upd.hwcloud.service.document.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 文档 服务实现类
 * </p>
 *
 * @author huru
 * @since 2018-11-09
 */
@Service
public class DocumentServiceImpl extends ServiceImpl<DocumentMapper, Document> implements IDocumentService {

    @Autowired
    DocumentMapper documentMapper;
    @Autowired
    IFilesService iFilesService;

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void publish(User user, String id, Integer result, String remark) {
        Document document  = this.getById(id);
        if (result.equals(1)) { // 上线
            document.setStatus(ReviewStatus.ONLINE.getCode());
            document.updateById();
        } else { // 下线
            document.setStatus(ReviewStatus.PRO_ONLINE.getCode());
            document.updateById();
        }
    }

    @Override
    public Page<Document> getPage(Page<Document> page, User user, boolean isFront, Integer status, String name, String firstClass,String secondClass) {
        return documentMapper.getPage(page,user,isFront,status,name,firstClass,secondClass);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Document create(User user, Document document) {
        document.setId(null);
        document.setCreator(user.getIdcard());
        document.setStatus(ReviewStatus.PRO_ONLINE.getCode());
        document.insert();
        //建立文件关联关系
        iFilesService.refFiles(document.getFilesList(), document.getId());
        return document;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void edit(User user, Document document) {
        document.setStatus(ReviewStatus.PRO_ONLINE.getCode());
        document.updateById();
        //建立文件关联关系
        iFilesService.refFiles(document.getFilesList(), document.getId());
    }

}
