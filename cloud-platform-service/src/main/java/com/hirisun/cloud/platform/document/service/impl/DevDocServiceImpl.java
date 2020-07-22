package com.hirisun.cloud.platform.document.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.platform.document.bean.DevDoc;
import com.hirisun.cloud.platform.document.bean.DevDocClass;
import com.hirisun.cloud.platform.document.mapper.DevDocMapper;
import com.hirisun.cloud.platform.document.service.DevDocService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 文档 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-20
 */
@Service
public class DevDocServiceImpl extends ServiceImpl<DevDocMapper, DevDoc> implements DevDocService {

    @Autowired
    private DevDocMapper devDocMapper;

    @Override
    public Page<DevDoc> getPage(Page<DevDoc> page, Map paramMap) {
        return devDocMapper.getPage(page,paramMap);
    }

}
