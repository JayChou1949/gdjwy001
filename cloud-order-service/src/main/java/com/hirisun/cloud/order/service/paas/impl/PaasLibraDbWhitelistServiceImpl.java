package com.hirisun.cloud.order.service.paas.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.paas.PaasLibraDbWhitelist;
import com.hirisun.cloud.order.mapper.paas.PaasLibraDbWhitelistMapper;
import com.hirisun.cloud.order.service.paas.IPaasLibraDbWhitelistService;

/**
 * Libra+分布式并行数据库白名单 服务实现类
 */
@Service
public class PaasLibraDbWhitelistServiceImpl extends ServiceImpl<PaasLibraDbWhitelistMapper,
	PaasLibraDbWhitelist> implements IPaasLibraDbWhitelistService {

}
