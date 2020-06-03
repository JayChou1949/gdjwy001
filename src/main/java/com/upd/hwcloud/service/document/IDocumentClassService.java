package com.upd.hwcloud.service.document;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.document.DocumentClass;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 文档分类表 服务类
 * </p>
 *
 * @author huru
 * @since 2018-11-09
 */
public interface IDocumentClassService extends IService<DocumentClass> {

    Page getPage(Page page, User user, Integer type, String name);
}
