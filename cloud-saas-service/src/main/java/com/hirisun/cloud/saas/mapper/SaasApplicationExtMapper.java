package com.hirisun.cloud.saas.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.model.application.AppDetailsVo;
import com.hirisun.cloud.saas.bean.SaasApplicationExt;

/**
 * SaaS申请原始信息扩展表 Mapper 接口
 */
public interface SaasApplicationExtMapper extends BaseMapper<SaasApplicationExt> {

    IPage<AppDetailsVo> getAppOpeningNum(IPage<AppDetailsVo> page, @Param("creator") String creator, @Param("serviceName") String serviceName);

    AppDetailsVo getAppRecyclingNum(@Param("creator") String creator, @Param("serviceName") String serviceName);
}
