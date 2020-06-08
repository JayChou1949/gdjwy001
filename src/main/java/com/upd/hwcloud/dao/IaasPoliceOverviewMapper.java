package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upd.hwcloud.bean.entity.IaasPoliceOverview;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 警种IAAS资源总览 Mapper 接口
 * </p>
 *
 * @author xqp
 * @since 2020-06-04
 */
public interface IaasPoliceOverviewMapper extends BaseMapper<IaasPoliceOverview> {

    IaasPoliceOverview sumAllByPolice(@Param("police") String police);

}
