package com.upd.hwcloud.service;

import com.upd.hwcloud.bean.entity.IaasAppScene;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * IAAS应用场景 服务类
 * </p>
 *
 * @author zwb
 * @since 2019-06-03
 */
public interface IIaasAppSceneService extends IService<IaasAppScene> {

    void save(List<IaasAppScene> scenes, String masterId);

    List<IaasAppScene> getByIaasId(String iaasId);
}
