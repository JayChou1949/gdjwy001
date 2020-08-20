package com.hirisun.cloud.order.service.publish.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.publish.ServicePublishApi;
import com.hirisun.cloud.order.mapper.publish.ServicePublishApiMapper;
import com.hirisun.cloud.order.service.publish.IServicePublishApiService;

/**
 * 服务发布-API 服务实现类
 */
@Service
public class ServicePublishApiServiceImpl extends ServiceImpl<ServicePublishApiMapper, 
	ServicePublishApi> implements IServicePublishApiService {

}
