package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.entity.application.IaasTxyfzjhImpl;
import com.upd.hwcloud.dao.application.IaasTxyfzjhImplMapper;
import com.upd.hwcloud.service.application.IIaasTxyfzjhImplService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 飞识二期授权码实施信息 服务实现类
 * </p>
 *
 * @author zwb
 * @since 2019-04-16
 */
@Service
public class IaasTxyfzjhImplServiceImpl extends ServiceImpl<IaasTxyfzjhImplMapper, IaasTxyfzjhImpl> implements IIaasTxyfzjhImplService {

    @Override
    public List<IaasTxyfzjhImpl> getImplServerListByAppInfoId(String id) {
        List<IaasTxyfzjhImpl> list = this.list(new QueryWrapper<IaasTxyfzjhImpl>().lambda()
                .eq(IaasTxyfzjhImpl::getAppInfoId, id)
                .orderByAsc(IaasTxyfzjhImpl::getCreateTime));
        return list;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(String appInfoId, List<IaasTxyfzjhImpl> serverList) {
        this.remove(new QueryWrapper<IaasTxyfzjhImpl>().lambda()
                .eq(IaasTxyfzjhImpl::getAppInfoId, appInfoId));

        if (serverList != null && !serverList.isEmpty()) {
            for (IaasTxyfzjhImpl txyfwImpl : serverList) {
                txyfwImpl.setAppInfoId(appInfoId);
                this.save(txyfwImpl);
            }
        }
    }
}
