package com.hirisun.cloud.order.service.paas;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.order.bean.paas.PaasBigdataComponent;
import com.hirisun.cloud.order.continer.IApplicationHandler;

/**
 * 大数据组件申请信息 服务类
 */
public interface IPaasBigdataComponentService extends IService<PaasBigdataComponent>,IApplicationHandler<PaasBigdataComponent> {

    /**
     * 转移旧数据
     */
    void moveData();
}
