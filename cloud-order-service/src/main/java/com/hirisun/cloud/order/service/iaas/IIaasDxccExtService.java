package com.hirisun.cloud.order.service.iaas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.order.bean.iaas.IaasDxccExt;
import com.hirisun.cloud.order.continer.IApplicationHandler;

/**
 * IaaS 对象存储申请信息 服务类
 */
public interface IIaasDxccExtService extends IService<IaasDxccExt>, 
	IApplicationHandler<IaasDxccExt> {

}
