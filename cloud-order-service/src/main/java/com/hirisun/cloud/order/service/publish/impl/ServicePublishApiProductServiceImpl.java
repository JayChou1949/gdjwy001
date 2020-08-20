package com.hirisun.cloud.order.service.publish.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.publish.ServicePublishApiProduct;
import com.hirisun.cloud.order.mapper.publish.ServicePublishApiProductMapper;
import com.hirisun.cloud.order.service.publish.IServicePublishApiProductService;

/**
 * 服务发布-api产品 服务实现类
 */
@Service
public class ServicePublishApiProductServiceImpl extends ServiceImpl<ServicePublishApiProductMapper, 
	ServicePublishApiProduct> implements IServicePublishApiProductService {

}
