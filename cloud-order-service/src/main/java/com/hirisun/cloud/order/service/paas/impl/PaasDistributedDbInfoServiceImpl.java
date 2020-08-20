package com.hirisun.cloud.order.service.paas.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.paas.PaasDistributedDbInfo;
import com.hirisun.cloud.order.mapper.paas.PaasDistributedDbInfoMapper;
import com.hirisun.cloud.order.service.paas.IPaasDistributedDbInfoService;

/**
 * Libra+分布式并行数据库申请表 服务实现类
 */
@Service
public class PaasDistributedDbInfoServiceImpl extends ServiceImpl<PaasDistributedDbInfoMapper, 
	PaasDistributedDbInfo> implements IPaasDistributedDbInfoService {

}
