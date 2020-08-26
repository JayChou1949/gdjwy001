package com.hirisun.cloud.paas.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.paas.bean.PassGaussdbAccountInfo;
import com.hirisun.cloud.paas.mapper.PassGaussdbAccountInfoMapper;
import com.hirisun.cloud.paas.service.IPassGaussdbAccountInfoService;

/**
 *  GAUSSDB 数据库账户信息 服务实现类
 */
@Service
public class PassGaussdbAccountInfoServiceImpl extends ServiceImpl<PassGaussdbAccountInfoMapper, 
	PassGaussdbAccountInfo> implements IPassGaussdbAccountInfoService {


}
