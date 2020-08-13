package com.hirisun.cloud.saas.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.saas.bean.SaasConfig;

public interface SaasConfigMapper extends BaseMapper<SaasConfig> {
	
    IPage<SaasConfig> getPage(IPage<SaasConfig> page, @Param("user") UserVO user, @Param("status") Integer status,
                        @Param("name") String name, @Param("subType") String subType,@Param("serviceFlag") Integer serviceFlag,@Param("pilotApp")Integer pilotApp);

    IPage<SaasConfig> getNewPage(IPage<SaasConfig> page, @Param("p") Map<String,Object> param);

    List<SaasConfig> getLabel(@Param("typeId") String typeId, @Param("canApplication") Integer canApplication);

    IPage<SaasConfig> getPageByCondition(IPage<SaasConfig> page, @Param("typeId") String typeId, @Param("label") String label,
                                   @Param("canApplication") Integer canApplication,
                                   @Param("keyword") String keyword, @Param("areaName") String areaName,
                                   @Param("policeCategory") String policeCategory);

    IPage<SaasConfig> getServicePageByCondition(IPage<SaasConfig> page,
                                   @Param("keyword") String keyword);

    List<SaasConfig> getApplicationApplyPageByCondition(@Param("keyword") String keyword);

    List<SaasConfig> getPageByCondition(@Param("typeId") String typeId, @Param("label") String label,
                                   @Param("canApplication") Integer canApplication,
                                   @Param("keyword") String keyword, @Param("areaName") String areaName,
                                   @Param("policeCategory") String policeCategory);

    SaasConfig getDetailWithSubTypeName(@Param("serviceId") String serviceId);

    void updateViewCountById(@Param("id") String id);

    List<SaasConfig> getOnlineList(@Param("serviceFlag") Integer serviceFlag);
    List<SaasConfig> getAppName(String creator);

    Integer userOfSaasApplication(String id);

    Integer areaCountOfSaasApplication(String id);

    Integer policeCountOfSaasApplication(String id);

	

}
