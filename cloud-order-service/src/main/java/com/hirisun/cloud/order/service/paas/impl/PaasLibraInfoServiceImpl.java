package com.hirisun.cloud.order.service.paas.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.paas.PaasLibraInfo;
import com.hirisun.cloud.order.mapper.paas.PaasLibraInfoMapper;
import com.hirisun.cloud.order.service.paas.IPaasLibraInfoService;

/**
 *  服务实现类
 */
@Service
public class PaasLibraInfoServiceImpl extends ServiceImpl<PaasLibraInfoMapper, 
	PaasLibraInfo> implements IPaasLibraInfoService {

}
