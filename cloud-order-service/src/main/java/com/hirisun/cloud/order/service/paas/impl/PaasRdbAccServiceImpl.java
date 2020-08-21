package com.hirisun.cloud.order.service.paas.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.paas.PaasRdbAcc;
import com.hirisun.cloud.order.mapper.paas.PaasRdbAccMapper;
import com.hirisun.cloud.order.service.paas.IPaasRdbAccService;

/**
 * 关系型数据库账号 服务实现类
 */
@Service
public class PaasRdbAccServiceImpl extends ServiceImpl<PaasRdbAccMapper, 
	PaasRdbAcc> implements IPaasRdbAccService {

}
