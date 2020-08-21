package com.hirisun.cloud.order.service.paas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.order.bean.paas.PaasRdbBase;
import com.hirisun.cloud.order.continer.IApplicationHandler;
import com.hirisun.cloud.order.continer.IImplHandler;

/**
 * 关系型数据库组申请信息 服务类
 */
public interface IPaasRdbBaseService extends IService<PaasRdbBase>,
	IApplicationHandler<PaasRdbBase>,IImplHandler<PaasRdbBase> {

    void emptyImplServerList(String infoId);
}
