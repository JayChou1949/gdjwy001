package com.hirisun.cloud.paas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.paas.bean.PassGaussdbInfo;

/**
 *   GAUSSDB 申请信息 服务类
 */
public interface IPassGaussdbInfoService extends IService<PassGaussdbInfo>,
	IApplicationHandler<PassGaussdbInfo>  {

}
