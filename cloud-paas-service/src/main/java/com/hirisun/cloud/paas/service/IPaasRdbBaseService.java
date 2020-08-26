package com.hirisun.cloud.paas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.paas.bean.PaasRdbBase;

/**
 * 关系型数据库组申请信息 服务类
 */
public interface IPaasRdbBaseService extends IService<PaasRdbBase>,
	IApplicationHandler<PaasRdbBase>,IImplHandler<PaasRdbBase> {

    void emptyImplServerList(String infoId);
}
