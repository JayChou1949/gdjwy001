package com.hirisun.cloud.order.service.paas.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.paas.PaasComponentDetailImpl;
import com.hirisun.cloud.order.mapper.paas.PaasComponentDetailImplMapper;
import com.hirisun.cloud.order.service.paas.IPaasComponentDetailImplService;

/**
 * 大数据组件申请组件信息表 服务实现类
 */
@Service
public class PaasComponentDetailImplServiceImpl extends ServiceImpl<PaasComponentDetailImplMapper, 
	PaasComponentDetailImpl> implements IPaasComponentDetailImplService {

}
