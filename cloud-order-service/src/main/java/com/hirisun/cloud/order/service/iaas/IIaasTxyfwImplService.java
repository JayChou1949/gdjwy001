package com.hirisun.cloud.order.service.iaas;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.order.bean.iaas.IaasTxyfwImpl;
import com.hirisun.cloud.order.continer.IImplHandler;

/**
 * 弹性云服务实施信息表 服务类
 */
public interface IIaasTxyfwImplService extends IService<IaasTxyfwImpl>, IImplHandler<IaasTxyfwImpl> {

    Map<String,Object> ncovOverview(String startTime);


}
