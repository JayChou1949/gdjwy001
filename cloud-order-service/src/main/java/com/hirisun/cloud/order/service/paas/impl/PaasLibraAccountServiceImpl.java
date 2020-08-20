package com.hirisun.cloud.order.service.paas.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.paas.PaasLibraAccount;
import com.hirisun.cloud.order.mapper.paas.PaasLibraAccountMapper;
import com.hirisun.cloud.order.service.paas.IPaasLibraAccountService;

/**
 *  服务实现类
 */
@Service
public class PaasLibraAccountServiceImpl extends ServiceImpl<PaasLibraAccountMapper, 
	PaasLibraAccount> implements IPaasLibraAccountService {

}
