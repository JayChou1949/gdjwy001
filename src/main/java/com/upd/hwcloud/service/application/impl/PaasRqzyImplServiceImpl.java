package com.upd.hwcloud.service.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upd.hwcloud.bean.entity.application.PaasFseqsqmImpl;
import com.upd.hwcloud.bean.entity.application.PaasRqzyImpl;
import com.upd.hwcloud.dao.application.PaasRqzyImplMapper;
import com.upd.hwcloud.service.application.IPaasRqzyImplService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 容器资源申请实施信息 服务实现类
 * </p>
 *
 * @author zwb
 * @since 2019-05-28
 */
@Service
public class PaasRqzyImplServiceImpl extends ServiceImpl<PaasRqzyImplMapper, PaasRqzyImpl> implements IPaasRqzyImplService {

    @Override
    public List<PaasRqzyImpl> getImplServerListByAppInfoId(String appInfoId) {
        List<PaasRqzyImpl> list = this.list(new QueryWrapper<PaasRqzyImpl>().lambda()
                .eq(PaasRqzyImpl::getAppInfoId, appInfoId)
                .orderByAsc(PaasRqzyImpl::getCreateTime));
        return list;
    }

    @Override
    public void update(String appInfoId, List<PaasRqzyImpl> serverList) {
        this.remove(new QueryWrapper<PaasRqzyImpl>().lambda()
                .eq(PaasRqzyImpl::getAppInfoId, appInfoId));

        if (serverList != null && !serverList.isEmpty()) {
            for (PaasRqzyImpl database : serverList) {
                database.setId(null);
                database.setAppInfoId(appInfoId);
                this.save(database);
            }
        }
    }
}
