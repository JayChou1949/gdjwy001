package com.upd.hwcloud.service.document;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.document.Document;

/**
 * <p>
 * 文档 服务类
 * </p>
 *
 * @author huru
 * @since 2018-11-09
 */
public interface IDocumentService extends IService<Document> {

    /**
     * 上/下线
     * @param result 操作结果 1:上线,其它:下线
     * @param remark 操作描述
     */
    void publish(User user, String id, Integer result, String remark);

    Page<Document> getPage(Page<Document> page, User user, boolean isFront, Integer status, String name, String firstClass,String secondClass);

    Document create(User user, Document document);

    void edit(User user, Document document);

}
