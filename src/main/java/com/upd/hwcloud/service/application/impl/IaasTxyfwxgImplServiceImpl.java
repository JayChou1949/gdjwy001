package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.entity.application.IaasTxyfwxgImpl;
import com.upd.hwcloud.dao.application.IaasTxyfwxgImplMapper;
import com.upd.hwcloud.service.application.IIaasTxyfwxgImplService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangbao
 * @since 2019-10-18
 */
@Service
public class IaasTxyfwxgImplServiceImpl extends ServiceImpl<IaasTxyfwxgImplMapper, IaasTxyfwxgImpl> implements IIaasTxyfwxgImplService {

    @Override
    public List<IaasTxyfwxgImpl> getImplServerListByAppInfoId(String id) {
        List<IaasTxyfwxgImpl> list = this.list(new QueryWrapper<IaasTxyfwxgImpl>().lambda()
                .eq(IaasTxyfwxgImpl::getAppInfoId, id)
                .orderByAsc(IaasTxyfwxgImpl::getCreateTime));
        return list;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(String appInfoId, List<IaasTxyfwxgImpl> serverList) {
        this.remove(new QueryWrapper<IaasTxyfwxgImpl>().lambda()
                .eq(IaasTxyfwxgImpl::getAppInfoId, appInfoId));

        if (serverList != null && !serverList.isEmpty()) {
            for (IaasTxyfwxgImpl txyfwImpl : serverList) {
                txyfwImpl.setId(null);
                txyfwImpl.setAppInfoId(appInfoId);
                this.save(txyfwImpl);
            }
        }
    }

}
