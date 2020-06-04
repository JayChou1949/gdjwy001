package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.Dict;
import com.upd.hwcloud.bean.entity.Saas;
import com.upd.hwcloud.bean.entity.ServicePublish;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.vo.workbench.QueryVO;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * SaaS 表 服务类
 * </p>
 *
 * @author wuc
 * @since 2018-10-25
 */
public interface ISaasService extends IService<Saas> ,SortService {

    void publish(User user, String id, Integer result, String remark);

    Map<String, Object> getFrontData(Integer serviceFlag);

    List<Saas>  getServiceFrontData();

    List<Map<String, Object>> getApplicationFrontData(String projectName);

    Set<String> getLabel(String typeId);

    IPage<Saas> getPage(IPage<Saas> page, User user, Integer status, String name, String subType,Integer serviceFlag,Integer pilotApp);

    IPage<Saas> getNewPage(IPage<Saas> page, QueryVO queryVO);

    Saas getDetail(User user, String id);

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

    boolean servicePublish2SaaS(ServicePublish servicePublish,String serviceGuid);

    Integer userCountOfSaasApplication(String id);

    Integer areaCountOfSaasApplication(String id);

    Integer policeCountOfSaasApplication(String id);

    void hotfix();

}
