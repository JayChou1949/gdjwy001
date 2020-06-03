package com.upd.hwcloud.service.application;

import com.upd.hwcloud.bean.entity.application.paas.rdb.PaasRdbBase;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 关系型数据库组申请信息 服务类
 * </p>
 *
 * @author yyc
 * @since 2020-04-19
 */
public interface IPaasRdbBaseService extends IService<PaasRdbBase>,IApplicationHandler<PaasRdbBase>,IImplHandler<PaasRdbBase> {

    void emptyImplServerList(String infoId);
}
