package com.hirisun.cloud.order.service.epidemic;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.order.bean.epidemic.EpidemicApplicationExt;

import java.util.List;

/**
 * <p>
 * SaaS申请原始信息扩展表 服务类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-03
 */
public interface EpidemicApplicationExtService extends IService<EpidemicApplicationExt> {

    List<EpidemicApplicationExt> getListByMasterId(String id);

}
