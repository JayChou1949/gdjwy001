package com.upd.hwcloud.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.SaasRecoverInfo;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.dao.SaasRecoverInfoMapper;
import com.upd.hwcloud.service.ISaasRecoverInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangbao
 * @since 2019-10-24
 */
@Service
public class SaasRecoverInfoServiceImpl extends ServiceImpl<SaasRecoverInfoMapper, SaasRecoverInfo> implements ISaasRecoverInfoService {

    @Override
    public List<SaasRecoverInfo> getUserUseService(String id) {
        return baseMapper.getUserUseService(id);
    }

    @Override
    public int getTodoCount(User user) {
        return baseMapper.getTodoCount(user);
    }

    @Override
    public int getApplicationRecoverTodo(String idCard) {
        return baseMapper.getApplicationTodo(idCard);
    }
}
