package com.hirisun.cloud.paas.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.paas.bean.PaasRdbInfo;
import com.hirisun.cloud.paas.mapper.PaasRdbInfoMapper;
import com.hirisun.cloud.paas.service.IPaasRdbInfoService;

/**
 * 关系型数据数据库信息 服务实现类
 */
@Service
public class PaasRdbInfoServiceImpl extends ServiceImpl<PaasRdbInfoMapper, 
	PaasRdbInfo> implements IPaasRdbInfoService {

}
