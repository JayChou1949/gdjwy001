package com.upd.hwcloud.service.application;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.IaasYzmyzy;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.vo.workbench.CloudDesktopVO;
import com.upd.hwcloud.bean.vo.workbench.QueryVO;

import java.util.List;

/**
 * <p>
 * IaaS 云桌面云资源申请信息 服务类
 * </p>
 *
 * @author zwb
 * @since 2019-05-09
 */
public interface IIaasYzmyzyService extends IService<IaasYzmyzy>, IApplicationHandler<IaasYzmyzy> {

    IPage<CloudDesktopVO> getResourcePage(Long pageNum, Long pageSize, User user, QueryVO queryVO);



}
