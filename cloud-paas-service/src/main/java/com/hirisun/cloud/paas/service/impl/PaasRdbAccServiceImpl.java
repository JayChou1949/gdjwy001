package com.hirisun.cloud.paas.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.paas.bean.PaasRdbAcc;
import com.hirisun.cloud.paas.mapper.PaasRdbAccMapper;
import com.hirisun.cloud.paas.service.IPaasRdbAccService;

/**
 * 关系型数据库账号 服务实现类
 */
@Service
public class PaasRdbAccServiceImpl extends ServiceImpl<PaasRdbAccMapper, 
	PaasRdbAcc> implements IPaasRdbAccService {

}
