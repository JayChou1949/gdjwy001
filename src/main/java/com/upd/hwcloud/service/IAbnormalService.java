package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upd.hwcloud.bean.entity.Abnormal;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.request.AbnormalPageRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huru
 * @since 2019-04-03
 */
public interface IAbnormalService extends IService<Abnormal> {

    Page<Abnormal> getPage(AbnormalPageRequest abnormalPageRequest);
}
