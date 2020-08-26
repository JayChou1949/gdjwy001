package com.hirisun.cloud.paas.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.paas.bean.PaasComponentDetail;
import com.hirisun.cloud.paas.mapper.PaasComponentDetailMapper;
import com.hirisun.cloud.paas.service.IPaasComponentDetailService;

/**
 * 大数据组件申请组件信息表 服务实现类
 */
@Service
public class PaasComponentDetailServiceImpl extends ServiceImpl<PaasComponentDetailMapper, 
	PaasComponentDetail> implements IPaasComponentDetailService {

}
