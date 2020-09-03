package com.hirisun.cloud.daas.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.daas.bean.Bigdata;
import com.hirisun.cloud.daas.mapper.BigdataMapper;
import com.hirisun.cloud.daas.service.BigdataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 大数据库服务目录 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-02
 */
@Service
public class BigdataServiceImpl extends ServiceImpl<BigdataMapper, Bigdata> implements BigdataService {

    @Autowired
    private BigdataMapper bigdataMapper;

    @Override
    public Page<Bigdata> getPage(Page<Bigdata> page, String name, String dataFrom, String collectionUnit, String category) {
        return bigdataMapper.getPage(page,name,dataFrom,collectionUnit,category);
    }
}
