package com.hirisun.cloud.daas.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hirisun.cloud.daas.bean.DaasApplication;
import com.hirisun.cloud.model.daas.vo.GeneralVo;

/**
 * DaaS服务申请信息 Mapper 接口
 */
public interface DaasApplicationMapper extends BaseMapper<DaasApplication> {

    List<GeneralVo> tenantStatistics(@Param("p") Map<String,Object> param);

    List<GeneralVo> serviceOfSaas(String name);

}
