package com.hirisun.cloud.paas.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.paas.bean.PaasRdbAccImpl;
import com.hirisun.cloud.paas.mapper.PaasRdbAccImplMapper;
import com.hirisun.cloud.paas.service.IPaasRdbAccImplService;

/**
 * 关系型数据库账号实施 服务实现类
 */
@Service
public class PaasRdbAccImplServiceImpl extends ServiceImpl<PaasRdbAccImplMapper, PaasRdbAccImpl> implements IPaasRdbAccImplService {

}
