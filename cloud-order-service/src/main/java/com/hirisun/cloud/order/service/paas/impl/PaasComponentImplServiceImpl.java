package com.hirisun.cloud.order.service.paas.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.common.util.UUIDUtil;
import com.hirisun.cloud.order.bean.paas.PaasComponentDetailImpl;
import com.hirisun.cloud.order.bean.paas.PaasComponentImpl;
import com.hirisun.cloud.order.mapper.paas.PaasComponentImplMapper;
import com.hirisun.cloud.order.service.paas.IPaasComponentDetailImplService;
import com.hirisun.cloud.order.service.paas.IPaasComponentImplService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yyc
 * @since 2020-05-11
 */
@Service
public class PaasComponentImplServiceImpl extends ServiceImpl<PaasComponentImplMapper, 
	PaasComponentImpl> implements IPaasComponentImplService {


    private static final Logger logger = LoggerFactory.getLogger(PaasComponentImplServiceImpl.class);

    @Autowired
    private IPaasComponentDetailImplService detailImplService;

    /**
     * 根据申请信息 id 查询实施服务器信息列表
     *
     * @param appInfoId 申请信息 id
     */
    @Override
    public List<PaasComponentImpl> getImplServerListByAppInfoId(String appInfoId) {
        List<PaasComponentImpl> implList = this.list(new QueryWrapper<PaasComponentImpl>().lambda().eq(PaasComponentImpl::getAppInfoId,appInfoId)
                                                            .orderByDesc(PaasComponentImpl::getModifiedTime));
        for(PaasComponentImpl impl:implList){
            List<PaasComponentDetailImpl> detailImplList = detailImplService.list(new QueryWrapper<PaasComponentDetailImpl>().lambda().eq(PaasComponentDetailImpl::getLinkId,impl.getId())
                    .orderByDesc(PaasComponentDetailImpl::getModifiedTime));

            impl.setDetailImplList(detailImplList);
        }
        return implList;

    }

    /**
     * 更新实施服务器信息(先删除,后添加)
     *
     * @param appInfoId
     * @param serverList
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void update(String appInfoId, List<PaasComponentImpl> serverList) {
        this.remove(new QueryWrapper<PaasComponentImpl>().lambda().eq(PaasComponentImpl::getAppInfoId,appInfoId));
        detailImplService.remove(new QueryWrapper<PaasComponentDetailImpl>().lambda().eq(PaasComponentDetailImpl::getAppInfoId,appInfoId));

        if(CollectionUtils.isNotEmpty(serverList)){
            for(PaasComponentImpl impl : serverList){
                String uuid = UUIDUtil.getUUID();
                impl.setId(uuid);
                impl.setAppInfoId(appInfoId);
                this.save(impl);
                List<PaasComponentDetailImpl> detailImplList = impl.getDetailImplList();
                if(CollectionUtils.isNotEmpty(detailImplList)){
                    for(PaasComponentDetailImpl detailImpl :detailImplList ){
                        detailImpl.setLinkId(uuid);
                        detailImpl.setAppInfoId(appInfoId);
                    }
                    logger.debug("detailImpl -> {}",detailImplList);
                    detailImplService.saveBatch(detailImplList);
                }
            }
        }
    }
}
