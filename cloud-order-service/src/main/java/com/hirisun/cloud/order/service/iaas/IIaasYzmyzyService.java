package com.hirisun.cloud.order.service.iaas;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.model.iaas.vo.CloudDesktopVO;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workbench.vo.QueryVO;
import com.hirisun.cloud.order.bean.iaas.IaasYzmyzy;
import com.hirisun.cloud.order.continer.IApplicationHandler;

/**
 * <p>
 * IaaS 云桌面云资源申请信息 服务类
 * </p>
 *
 * @author zwb
 * @since 2019-05-09
 */
public interface IIaasYzmyzyService extends IService<IaasYzmyzy>, IApplicationHandler<IaasYzmyzy> {

    IPage<CloudDesktopVO> getResourcePage(Long pageNum, Long pageSize, UserVO user, QueryVO queryVO);



}
