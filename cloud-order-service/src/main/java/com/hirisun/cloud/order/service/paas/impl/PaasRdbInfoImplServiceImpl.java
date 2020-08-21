package com.hirisun.cloud.order.service.paas.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.paas.PaasRdbInfoImpl;
import com.hirisun.cloud.order.mapper.paas.PaasRdbInfoImplMapper;
import com.hirisun.cloud.order.service.paas.IPaasRdbInfoImplService;

/**
 * 关系型数据数据库信息实施 服务实现类
 */
@Service
public class PaasRdbInfoImplServiceImpl extends ServiceImpl<PaasRdbInfoImplMapper, 
	PaasRdbInfoImpl> implements IPaasRdbInfoImplService {

}
