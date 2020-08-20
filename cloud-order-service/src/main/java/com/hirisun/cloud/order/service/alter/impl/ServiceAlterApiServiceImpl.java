package com.hirisun.cloud.order.service.alter.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.alter.ServiceAlterApi;
import com.hirisun.cloud.order.mapper.alter.ServiceAlterApiMapper;
import com.hirisun.cloud.order.service.alter.IServiceAlterApiService;

@Service
public class ServiceAlterApiServiceImpl extends ServiceImpl<ServiceAlterApiMapper, 
	ServiceAlterApi> implements IServiceAlterApiService {

}
