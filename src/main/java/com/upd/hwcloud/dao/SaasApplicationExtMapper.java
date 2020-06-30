package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.entity.SaasApplication;
import com.upd.hwcloud.bean.entity.SaasApplicationExt;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * SaaS申请原始信息扩展表 Mapper 接口
 * </p>
 *
 * @author wuc
 * @since 2019-07-24
 */
public interface SaasApplicationExtMapper extends BaseMapper<SaasApplicationExt> {

    IPage<SaasApplicationExt> getAppOpeningNum(IPage<SaasApplicationExt> page, @Param("creator") String creator, @Param("serviceName") String serviceName);

    SaasApplicationExt getAppRecyclingNum(@Param("creator") String creator, @Param("serviceName") String serviceName);
}
