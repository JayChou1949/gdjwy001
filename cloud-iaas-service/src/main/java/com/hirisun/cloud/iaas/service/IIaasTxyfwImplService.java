package com.hirisun.cloud.iaas.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.iaas.bean.IaasTxyfwImpl;

/**
 * 弹性云服务实施信息表 服务类
 */
public interface IIaasTxyfwImplService extends IService<IaasTxyfwImpl>, IImplHandler<IaasTxyfwImpl> {

    Map<String,Object> ncovOverview(String startTime);


}
