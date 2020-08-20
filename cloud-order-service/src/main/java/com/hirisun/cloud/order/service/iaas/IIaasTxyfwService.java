package com.hirisun.cloud.order.service.iaas;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.model.iaas.vo.EcsVO;
import com.hirisun.cloud.order.bean.iaas.IaasTxyfw;
import com.hirisun.cloud.order.bean.iaas.IaasTxyfwImpl;
import com.hirisun.cloud.order.continer.IApplicationHandler;

/**
 * 弹性云服务器申请信息 服务类
 */
public interface IIaasTxyfwService extends IService<IaasTxyfw>, IApplicationHandler<IaasTxyfw> {

    IPage<IaasTxyfwImpl> getEcsWorkbenchPage(IPage<IaasTxyfwImpl> page, Map<String, Object> param);

    EcsVO getiMocEcsDetail(String ecsId);

    List<EcsVO> getEcsRecent(String ecsId);

    List<EcsVO> getEcsByAppName(String name);



}
