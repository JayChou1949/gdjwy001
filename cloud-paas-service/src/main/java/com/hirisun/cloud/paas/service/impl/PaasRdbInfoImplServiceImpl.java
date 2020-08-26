package com.hirisun.cloud.paas.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.paas.bean.PaasRdbInfoImpl;
import com.hirisun.cloud.paas.mapper.PaasRdbInfoImplMapper;
import com.hirisun.cloud.paas.service.IPaasRdbInfoImplService;

/**
 * 关系型数据数据库信息实施 服务实现类
 */
@Service
public class PaasRdbInfoImplServiceImpl extends ServiceImpl<PaasRdbInfoImplMapper, 
	PaasRdbInfoImpl> implements IPaasRdbInfoImplService {

}
