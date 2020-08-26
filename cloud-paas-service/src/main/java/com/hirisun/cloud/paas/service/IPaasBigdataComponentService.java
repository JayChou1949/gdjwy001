package com.hirisun.cloud.paas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.paas.bean.PaasBigdataComponent;

/**
 * 大数据组件申请信息 服务类
 */
public interface IPaasBigdataComponentService extends IService<PaasBigdataComponent>,IApplicationHandler<PaasBigdataComponent> {

    /**
     * 转移旧数据
     */
    void moveData();
}
