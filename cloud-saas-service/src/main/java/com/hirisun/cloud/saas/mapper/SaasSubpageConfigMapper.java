package com.hirisun.cloud.saas.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hirisun.cloud.model.daas.vo.DaasServiceOverview;
import com.hirisun.cloud.model.daas.vo.InnerServiceOverview;
import com.hirisun.cloud.model.daas.vo.ServiceOverview;
import com.hirisun.cloud.saas.bean.SaasSubpageConfig;


public interface SaasSubpageConfigMapper extends BaseMapper<SaasSubpageConfig> {

    List<DaasServiceOverview> daas(@Param("name") String name);

    List<ServiceOverview> paas(@Param("name") String name);

    List<InnerServiceOverview> paasOther(@Param("name") String name);

}
