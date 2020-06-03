package com.upd.hwcloud.dao;

import com.upd.hwcloud.bean.entity.IaasAppScene;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * IAAS应用场景 Mapper 接口
 * </p>
 *
 * @author zwb
 * @since 2019-06-03
 */
public interface IaasAppSceneMapper extends BaseMapper<IaasAppScene> {

    List<IaasAppScene> getByIaasId(String iaasId);
}
