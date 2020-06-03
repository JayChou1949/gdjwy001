package com.upd.hwcloud.service.application;

import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.application.PaasDatabase;

/**
 * <p>
 * PaaS 数据库申请信息 服务类
 * </p>
 *
 * @author huru
 * @since 2018-12-27
 */
public interface IPaasDatabaseService extends IService<PaasDatabase>,IApplicationHandler<PaasDatabase> {

}
