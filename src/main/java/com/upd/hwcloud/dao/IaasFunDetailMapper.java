package com.upd.hwcloud.dao;

import com.upd.hwcloud.bean.entity.IaasFunDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * IAAS功能详情 Mapper 接口
 * </p>
 *
 * @author zwb
 * @since 2019-06-03
 */
public interface IaasFunDetailMapper extends BaseMapper<IaasFunDetail> {

    List<IaasFunDetail> getByIaasId(String iaasId);
}
