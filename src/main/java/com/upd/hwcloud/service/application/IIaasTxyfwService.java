package com.upd.hwcloud.service.application;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.application.IaasTxyfw;
import com.upd.hwcloud.bean.entity.application.IaasTxyfwImpl;
import com.upd.hwcloud.bean.entity.application.IaasYzmImpl;
import com.upd.hwcloud.bean.vo.workbench.EcsVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 弹性云服务器申请信息 服务类
 * </p>
 *
 * @author wuc
 * @since 2018-11-30
 */
public interface IIaasTxyfwService extends IService<IaasTxyfw>, IApplicationHandler<IaasTxyfw> {

    IPage<IaasTxyfwImpl> getEcsWorkbenchPage(IPage<IaasTxyfwImpl> page, Map<String, Object> param);

    EcsVO getiMocEcsDetail(String ecsId);

    List<EcsVO> getEcsRecent(String ecsId);

    List<EcsVO> getEcsByAppName(String name);



}
