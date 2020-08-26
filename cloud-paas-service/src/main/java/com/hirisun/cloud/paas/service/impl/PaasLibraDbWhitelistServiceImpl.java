package com.hirisun.cloud.paas.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.paas.bean.PaasLibraDbWhitelist;
import com.hirisun.cloud.paas.mapper.PaasLibraDbWhitelistMapper;
import com.hirisun.cloud.paas.service.IPaasLibraDbWhitelistService;

/**
 * Libra+分布式并行数据库白名单 服务实现类
 */
@Service
public class PaasLibraDbWhitelistServiceImpl extends ServiceImpl<PaasLibraDbWhitelistMapper,
	PaasLibraDbWhitelist> implements IPaasLibraDbWhitelistService {

}
