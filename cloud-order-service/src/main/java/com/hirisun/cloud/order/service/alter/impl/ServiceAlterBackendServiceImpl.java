package com.hirisun.cloud.order.service.alter.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.alter.ServiceAlterBackend;
import com.hirisun.cloud.order.mapper.alter.ServiceAlterBackendMapper;
import com.hirisun.cloud.order.service.alter.IServiceAlterBackendService;

@Service
public class ServiceAlterBackendServiceImpl extends ServiceImpl<ServiceAlterBackendMapper, 
		ServiceAlterBackend> implements IServiceAlterBackendService {

}
