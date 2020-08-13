package com.hirisun.cloud.saas.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.model.service.alter.vo.ServiceAlterVo;
import com.hirisun.cloud.model.service.publish.vo.ServicePublishVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workbench.vo.QueryVO;
import com.hirisun.cloud.saas.bean.SaasConfig;


public interface SaasConfigService {

	SaasConfig getSaasConfigById(String saasConfigId);
	
    void publish(UserVO user, String id, Integer result, String remark);

    Map<String, Object> getFrontData(Integer serviceFlag);

    List<SaasConfig>  getServiceFrontData();

    List<Map<String, Object>> getApplicationFrontData(String projectName);

    Set<String> getLabel(String typeId);

    IPage<SaasConfig> getPage(IPage<SaasConfig> page, UserVO user, 
    		Integer status, String name, String subType,Integer serviceFlag,Integer pilotApp);

    IPage<SaasConfig> getNewPage(IPage<SaasConfig> page, QueryVO queryVO);

    SaasConfig getDetail(UserVO user,String id);

    /**
     * 更多页面
     */
    IPage<SaasConfig> getMorePage(IPage<SaasConfig> page, String typeId, String keyword, String areaName, String policeCategory);


    IPage<SaasConfig> getServiceMorePage(IPage<SaasConfig> page, String keyword);


    SaasConfig getDetailWithSubTypeName(String serviceId);

    List<Map<String, Object>> getLabelWithCount(String typeId);

    /**
     * 一键申请页面
     */
    IPage<SaasConfig> getOneClickPage(IPage<SaasConfig> page, String typeId, String label, String keyword);

    List<SaasConfig> saasList(String keyword);

    void updateViewCountById(@Param("id") String id);

    List<SaasConfig> getAppName(String creator);

    boolean servicePublish2SaaS(ServicePublishVo servicePublish,String serviceGuid);
    
    boolean serviceAlter2SaaS(ServiceAlterVo serviceAlter,String serviceGuid);

    Integer userCountOfSaasApplication(String id);

    Integer areaCountOfSaasApplication(String id);

    Integer policeCountOfSaasApplication(String id);

    void hotfix();
    
    public String create(UserVO user,SaasConfig saasConfig);
    
    public void serviceSort(String id,String ope);
    
    public void delete(UserVO user,String id);
    
    public void edit(SaasConfig saas);
    
    public SaasConfig setflow(UserVO user, String id,String flowId);
    
    public List<SaasConfig> findSaasConfigByName(String name);

}
