package com.hirisun.cloud.order.service.publish.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.publish.ServicePublishBackend;
import com.hirisun.cloud.order.mapper.publish.ServicePublishBackendMapper;
import com.hirisun.cloud.order.service.publish.IServicePublishBackendService;

/**
 * 服务发布-后端服务 服务实现类
 */
@Service
public class ServicePublishBackendServiceImpl extends ServiceImpl<ServicePublishBackendMapper, 
	ServicePublishBackend> implements IServicePublishBackendService {

}
