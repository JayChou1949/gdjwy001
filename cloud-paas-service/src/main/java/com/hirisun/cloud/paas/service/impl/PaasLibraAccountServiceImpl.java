package com.hirisun.cloud.paas.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.paas.bean.PaasLibraAccount;
import com.hirisun.cloud.paas.mapper.PaasLibraAccountMapper;
import com.hirisun.cloud.paas.service.IPaasLibraAccountService;

/**
 *  服务实现类
 */
@Service
public class PaasLibraAccountServiceImpl extends ServiceImpl<PaasLibraAccountMapper, 
	PaasLibraAccount> implements IPaasLibraAccountService {

}
