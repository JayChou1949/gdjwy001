package com.hirisun.cloud.iaas.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.iaas.bean.Iaas;
import com.hirisun.cloud.iaas.bean.Subpage;
import com.hirisun.cloud.model.user.UserVO;

public interface IaasMapper extends BaseMapper<Iaas> {
	
    IPage<Iaas> getPage(IPage<Iaas> page, @Param("user") UserVO user, @Param("status") Integer status
            , @Param("name") String name, @Param("subType") String subType);
    
    List<Subpage> getLabel(@Param("home") Integer home, @Param("canApplication") Integer canApplication);
    
    List<Iaas> getPageByCondition(@Param("label") String label,
                                  @Param("home") Integer home, @Param("canApplication") Integer canApplication,
                                  @Param("keyword") String keyword);
    
    Iaas getCloudDesktopByName(@Param("name") String name);
}
