package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.application.IaasSfswjfwImpl;
import com.upd.hwcloud.dao.application.IaasSfswjfwImplMapper;
import com.upd.hwcloud.service.application.IIaasSfswjfwImplService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * sfs弹性文件服务实施信息 服务实现类
 * </p>
 *
 * @author wuc
 * @since 2019-10-25
 */
@Service
public class IaasSfswjfwImplServiceImpl extends ServiceImpl<IaasSfswjfwImplMapper, IaasSfswjfwImpl> implements IIaasSfswjfwImplService {

    @Override
    public List<IaasSfswjfwImpl> getImplServerListByAppInfoId(String id) {
        List<IaasSfswjfwImpl> list = this.list(new QueryWrapper<IaasSfswjfwImpl>().lambda()
                .eq(IaasSfswjfwImpl::getAppInfoId, id)
                .orderByAsc(IaasSfswjfwImpl::getCreateTime));
        return list;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(String appInfoId, List<IaasSfswjfwImpl> serverList) {
        this.remove(new QueryWrapper<IaasSfswjfwImpl>().lambda()
                .eq(IaasSfswjfwImpl::getAppInfoId, appInfoId));

        if (serverList != null && !serverList.isEmpty()) {
            for (IaasSfswjfwImpl database : serverList) {
                database.setId(null);
                database.setAppInfoId(appInfoId);
                this.save(database);
            }
        }
    }
}
