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
import com.hirisun.cloud.saas.bean.Saas;


public interface SaasService {

	Saas getSaasConfigById(String saasConfigId);
	
    void publish(UserVO user, String id, Integer result, String remark);

    Map<String, Object> getFrontData(Integer serviceFlag);

    List<Saas>  getServiceFrontData();

    List<Map<String, Object>> getApplicationFrontData(String projectName);

    Set<String> getLabel(String typeId);

    IPage<Saas> getPage(IPage<Saas> page, UserVO user, 
    		Integer status, String name, String subType,Integer serviceFlag,Integer pilotApp);

    IPage<Saas> getNewPage(IPage<Saas> page, QueryVO queryVO);

    Saas getDetail(UserVO user,String id);

    /**
     * 更多页面
     */
    IPage<Saas> getMorePage(IPage<Saas> page, String typeId, String keyword, String areaName, String policeCategory);


    IPage<Saas> getServiceMorePage(IPage<Saas> page, String keyword);


    Saas getDetailWithSubTypeName(String serviceId);

    List<Map<String, Object>> getLabelWithCount(String typeId);

    /**
     * 一键申请页面
     */
    IPage<Saas> getOneClickPage(IPage<Saas> page, String typeId, String label, String keyword);

    List<Saas> saasList(String keyword);

    void updateViewCountById(@Param("id") String id);

    List<Saas> getAppName(String creator);

    boolean servicePublish2SaaS(ServicePublishVo servicePublish,String serviceGuid);
    
    boolean serviceAlter2SaaS(ServiceAlterVo serviceAlter,String serviceGuid);

    Integer userCountOfSaasApplication(String id);

    Integer areaCountOfSaasApplication(String id);

    Integer policeCountOfSaasApplication(String id);

    void hotfix();
    
    public String create(UserVO user,Saas saasConfig);
    
    public void serviceSort(String id,String ope);
    
    public void delete(UserVO user,String id);
    
    public void edit(Saas saas);
    
    public Saas setflow(UserVO user, String id,String flowId);
    
    public List<Saas> findSaasConfigByName(String name);

}
