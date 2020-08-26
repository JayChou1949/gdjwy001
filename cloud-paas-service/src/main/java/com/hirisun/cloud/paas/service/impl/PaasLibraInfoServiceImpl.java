package com.hirisun.cloud.paas.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.paas.bean.PaasLibraInfo;
import com.hirisun.cloud.paas.mapper.PaasLibraInfoMapper;
import com.hirisun.cloud.paas.service.IPaasLibraInfoService;

/**
 *  服务实现类
 */
@Service
public class PaasLibraInfoServiceImpl extends ServiceImpl<PaasLibraInfoMapper, 
	PaasLibraInfo> implements IPaasLibraInfoService {

}
