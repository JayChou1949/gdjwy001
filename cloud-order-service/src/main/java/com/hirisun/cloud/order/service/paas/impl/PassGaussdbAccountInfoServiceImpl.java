package com.hirisun.cloud.order.service.paas.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.paas.PassGaussdbAccountInfo;
import com.hirisun.cloud.order.mapper.paas.PassGaussdbAccountInfoMapper;
import com.hirisun.cloud.order.service.paas.IPassGaussdbAccountInfoService;

/**
 *  GAUSSDB 数据库账户信息 服务实现类
 */
@Service
public class PassGaussdbAccountInfoServiceImpl extends ServiceImpl<PassGaussdbAccountInfoMapper, 
	PassGaussdbAccountInfo> implements IPassGaussdbAccountInfoService {


}
