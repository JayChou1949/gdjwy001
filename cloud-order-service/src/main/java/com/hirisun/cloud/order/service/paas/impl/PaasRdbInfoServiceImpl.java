package com.hirisun.cloud.order.service.paas.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.paas.PaasRdbInfo;
import com.hirisun.cloud.order.mapper.paas.PaasRdbInfoMapper;
import com.hirisun.cloud.order.service.paas.IPaasRdbInfoService;

/**
 * 关系型数据数据库信息 服务实现类
 */
@Service
public class PaasRdbInfoServiceImpl extends ServiceImpl<PaasRdbInfoMapper, 
	PaasRdbInfo> implements IPaasRdbInfoService {

}
