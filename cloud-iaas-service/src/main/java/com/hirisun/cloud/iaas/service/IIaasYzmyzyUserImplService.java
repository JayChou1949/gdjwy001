package com.hirisun.cloud.iaas.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.iaas.bean.IaasYzmyzyUser;
import com.hirisun.cloud.iaas.bean.IaasYzmyzyUserImpl;

/**
 * 统一用户人员信息 服务类
 */
public interface IIaasYzmyzyUserImplService extends IService<IaasYzmyzyUserImpl> {

    void saveUserList(List<IaasYzmyzyUser> impls, String appName);
}
