package com.hirisun.cloud.paas.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.paas.bean.PaasDistributedDbInfo;
import com.hirisun.cloud.paas.mapper.PaasDistributedDbInfoMapper;
import com.hirisun.cloud.paas.service.IPaasDistributedDbInfoService;

/**
 * Libra+分布式并行数据库申请表 服务实现类
 */
@Service
public class PaasDistributedDbInfoServiceImpl extends ServiceImpl<PaasDistributedDbInfoMapper, 
	PaasDistributedDbInfo> implements IPaasDistributedDbInfoService {

}
