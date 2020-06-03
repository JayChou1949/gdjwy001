package com.upd.hwcloud.service.application;

import com.upd.hwcloud.bean.entity.application.PaasBigdataComponent;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 大数据组件申请信息 服务类
 * </p>
 *
 * @author junglefisher
 * @since 2020-05-08
 */
public interface IPaasBigdataComponentService extends IService<PaasBigdataComponent>,IApplicationHandler<PaasBigdataComponent> {

    /**
     * 转移旧数据
     */
    void moveData();
}
