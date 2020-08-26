package com.hirisun.cloud.paas.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.paas.bean.PaasComponentDetailImpl;
import com.hirisun.cloud.paas.mapper.PaasComponentDetailImplMapper;
import com.hirisun.cloud.paas.service.IPaasComponentDetailImplService;

/**
 * 大数据组件申请组件信息表 服务实现类
 */
@Service
public class PaasComponentDetailImplServiceImpl extends ServiceImpl<PaasComponentDetailImplMapper, 
	PaasComponentDetailImpl> implements IPaasComponentDetailImplService {

}
