package com.upd.hwcloud.dao;

import com.upd.hwcloud.bean.entity.IaasOverview;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangwy
 * @since 2019-09-09
 */
public interface IaasOverviewMapper extends BaseMapper<IaasOverview> {

    IaasOverview sumAll(@Param("area") String area);

}
