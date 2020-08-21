package com.hirisun.cloud.order.service.paas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.order.bean.paas.PassGaussdbInfo;
import com.hirisun.cloud.order.continer.IApplicationHandler;

/**
 *   GAUSSDB 申请信息 服务类
 */
public interface IPassGaussdbInfoService extends IService<PassGaussdbInfo>,
	IApplicationHandler<PassGaussdbInfo>  {

}
