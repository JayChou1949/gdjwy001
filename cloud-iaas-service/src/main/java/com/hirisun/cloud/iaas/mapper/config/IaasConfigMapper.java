package com.hirisun.cloud.iaas.mapper.config;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.iaas.bean.config.IaasConfig;
import com.hirisun.cloud.model.user.UserVO;

public interface IaasConfigMapper extends BaseMapper<IaasConfig> {
	
    IPage<IaasConfig> getPage(IPage<IaasConfig> page, @Param("user") UserVO user, @Param("status") Integer status
            , @Param("name") String name, @Param("subType") String subType);
    
    List<IaasConfig> getLabel(@Param("home") Integer home, @Param("canApplication") Integer canApplication);
    
    List<IaasConfig> getPageByCondition(@Param("label") String label,
                                  @Param("home") Integer home, @Param("canApplication") Integer canApplication,
                                  @Param("keyword") String keyword);
}
