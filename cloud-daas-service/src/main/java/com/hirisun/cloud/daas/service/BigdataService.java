package com.hirisun.cloud.daas.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.daas.bean.Bigdata;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 大数据库服务目录 服务类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-02
 */
public interface BigdataService extends IService<Bigdata> {

    Page<Bigdata> getPage(Page<Bigdata> page, String name, String dataFrom, String collectionUnit, String category);

}
