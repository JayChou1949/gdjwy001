package com.hirisun.cloud.order.service.paas.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.order.bean.paas.PassTyxxUser;
import com.hirisun.cloud.order.mapper.paas.PassTyxxUserMapper;
import com.hirisun.cloud.order.service.paas.IPassTyxxUserService;

/**
 * 统一用户人员信息 服务实现类
 */
@Service
public class PassTyxxUserServiceImpl extends ServiceImpl<PassTyxxUserMapper, 
	PassTyxxUser> implements IPassTyxxUserService {

}
