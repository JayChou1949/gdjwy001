package com.hirisun.cloud.saas.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.saas.bean.Saas;

public interface SaasMapper extends BaseMapper<Saas> {
	
    IPage<Saas> getPage(IPage<Saas> page, @Param("user") UserVO user, @Param("status") Integer status,
                        @Param("name") String name, @Param("subType") String subType,@Param("serviceFlag") Integer serviceFlag,@Param("pilotApp")Integer pilotApp);

    IPage<Saas> getNewPage(IPage<Saas> page, @Param("p") Map<String,Object> param);

    List<Saas> getLabel(@Param("typeId") String typeId, @Param("canApplication") Integer canApplication);

    IPage<Saas> getPageByCondition(IPage<Saas> page, @Param("typeId") String typeId, @Param("label") String label,
                                   @Param("canApplication") Integer canApplication,
                                   @Param("keyword") String keyword, @Param("areaName") String areaName,
                                   @Param("policeCategory") String policeCategory);

    IPage<Saas> getServicePageByCondition(IPage<Saas> page,
                                   @Param("keyword") String keyword);

    List<Saas> getApplicationApplyPageByCondition(@Param("keyword") String keyword);

    List<Saas> getPageByCondition(@Param("typeId") String typeId, @Param("label") String label,
                                   @Param("canApplication") Integer canApplication,
                                   @Param("keyword") String keyword, @Param("areaName") String areaName,
                                   @Param("policeCategory") String policeCategory);

    Saas getDetailWithSubTypeName(@Param("serviceId") String serviceId);

    void updateViewCountById(@Param("id") String id);

    List<Saas> getOnlineList(@Param("serviceFlag") Integer serviceFlag);
    List<Saas> getAppName(String creator);

    Integer userOfSaasApplication(String id);

    Integer areaCountOfSaasApplication(String id);

    Integer policeCountOfSaasApplication(String id);

	

}
