package com.hirisun.cloud.order.service.paas.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.paas.PaasComponentDetail;
import com.hirisun.cloud.order.mapper.paas.PaasComponentDetailMapper;
import com.hirisun.cloud.order.service.paas.IPaasComponentDetailService;

/**
 * 大数据组件申请组件信息表 服务实现类
 */
@Service
public class PaasComponentDetailServiceImpl extends ServiceImpl<PaasComponentDetailMapper, 
	PaasComponentDetail> implements IPaasComponentDetailService {

}
