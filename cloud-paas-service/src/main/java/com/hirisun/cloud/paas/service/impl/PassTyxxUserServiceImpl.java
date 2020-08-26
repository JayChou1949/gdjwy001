package com.hirisun.cloud.paas.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hirisun.cloud.paas.bean.PassTyxxUser;
import com.hirisun.cloud.paas.mapper.PassTyxxUserMapper;
import com.hirisun.cloud.paas.service.IPassTyxxUserService;

/**
 * 统一用户人员信息 服务实现类
 */
@Service
public class PassTyxxUserServiceImpl extends ServiceImpl<PassTyxxUserMapper, 
	PassTyxxUser> implements IPassTyxxUserService {

}
