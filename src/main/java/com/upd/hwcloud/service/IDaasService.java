package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.Daas;
import com.upd.hwcloud.bean.entity.User;

/**
 * <p>
 * DaaS 表 服务类
 * </p>
 *
 * @author wuc
 * @since 2018-10-25
 */
public interface IDaasService extends IService<Daas>,SortService {

    IPage<Daas> getPage(IPage<Daas> page, User user, Integer status, String name,String subType);
}
