package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upd.hwcloud.bean.entity.application.IaasTxyfwbgImpl;
import com.upd.hwcloud.dao.application.IaasTxyfwbgImplMapper;
import com.upd.hwcloud.service.application.IIaasTxyfwbgImplService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 弹性云服务器变更申请实施信息表 服务实现类
 * </p>
 *
 * @author huru
 * @since 2019-01-22
 */
@Service
public class IaasTxyfwbgImplServiceImpl extends ServiceImpl<IaasTxyfwbgImplMapper, IaasTxyfwbgImpl> implements IIaasTxyfwbgImplService {

    @Override
    public List<IaasTxyfwbgImpl> getImplServerListByAppInfoId(String id) {
        List<IaasTxyfwbgImpl> list = this.list(new QueryWrapper<IaasTxyfwbgImpl>().lambda()
                .eq(IaasTxyfwbgImpl::getAppInfoId, id)
                .orderByAsc(IaasTxyfwbgImpl::getCreateTime));
        return list;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(String appInfoId, List<IaasTxyfwbgImpl> serverList) {
        this.remove(new QueryWrapper<IaasTxyfwbgImpl>().lambda()
                .eq(IaasTxyfwbgImpl::getAppInfoId, appInfoId));

        if (serverList != null && !serverList.isEmpty()) {
            for (IaasTxyfwbgImpl txyfwImpl : serverList) {
                txyfwImpl.setAppInfoId(appInfoId);
                this.save(txyfwImpl);
            }
        }
    }

}
