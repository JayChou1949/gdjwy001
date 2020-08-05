package com.hirisun.cloud.platform.document.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.platform.document.bean.DevDocClass;
import com.hirisun.cloud.platform.document.mapper.DevDocClassMapper;
import com.hirisun.cloud.platform.document.service.DevDocClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 文档分类表 服务实现类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-07-20
 */
@Service
public class DevDocClassServiceImpl extends ServiceImpl<DevDocClassMapper, DevDocClass> implements DevDocClassService {

    @Autowired
    private DevDocClassMapper devDocClassMapper;
    @Override
    public Page getPage(Page page, UserVO user, Map map) {
        return devDocClassMapper.getPage(page,user,map);
    }
}
