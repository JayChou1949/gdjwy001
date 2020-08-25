package com.hirisun.cloud.saas.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.model.user.UserInfoVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workbench.vo.ResourceOverviewVO;
import com.hirisun.cloud.saas.bean.SaasApplication;

/**
 * SaaS资源申请原始信息表 Mapper 接口
 */
public interface SaasApplicationMapper extends BaseMapper<SaasApplication> {

    IPage<SaasApplication> getPage(IPage<SaasApplication> page, @Param("p") Map<String, Object> param);

    IPage<SaasApplication> getWorkbenchApplyPage(IPage<SaasApplication> page, @Param("p") Map<String, Object> param);

    Integer getWorkbenchApplyCount(@Param("p") Map<String,Object> param);

    IPage<SaasApplication> getMergePage(IPage<SaasApplication> page, @Param("tenantArea") String tenantArea,
                                        @Param("tenantPoliceCategory") String tenantPoliceCategory, @Param("keyword") String keyword);

    List<SaasApplication> getListByMergeId(@Param("mergeId") String mergeId);

    /**
     * 获取SaaS代办数
     */
    int getSaasTodoCount(@Param("user") UserVO user);

    int getApplicationTodo(@Param("idCard") String idCard);

    Integer getSaasUseRes(@Param("user") UserVO user, @Param("appName") String appName);

    int getReviewCount(@Param("user") UserVO user,@Param("p") Map<String,Object> param);

    int getImplCount(@Param("user") UserVO user,@Param("p") Map<String,Object> param);

    int getRejectCount(@Param("user") UserVO user,@Param("p") Map<String,Object> param);

    int getUseCount(@Param("user") UserVO user,@Param("p") Map<String,Object> param);

    IPage<SaasApplication> getWorkbenchPage(IPage<SaasApplication> page, @Param("p") Map<String, Object> param);


    IPage<SaasApplication> getApplicationUser(IPage<SaasApplication> page, @Param("area") String area,@Param("policeCategory") String policeCategory,
                                              @Param("name") String name, @Param("idcard") String idcard);

    List<SaasApplication> getSaasUseService(@Param("userId") String userId);

    String getBelongAppName(@Param("id") String id);

    void updateRecovering2NotRecover(@Param("id") String id);

    void updateUse2Recover(@Param("id") String id);
    List<SaasApplication> getSaasRecoverManageUseService(String userId);

    /**
     * 工作台-SaaS待审核列表
     * @param page
     * @param param
     * @return
     */
    IPage<SaasApplication> getReviewOfWorkbench(IPage<SaasApplication> page,@Param("p") Map<String,Object> param);

    /**
     *工作台-SaaS待实施列表
     * @param page
     * @param param
     * @return
     */
    IPage<SaasApplication> getImplOfWorkbench(IPage<SaasApplication> page,@Param("p") Map<String,Object> param);


    /**
     *工作台-SaaS驳回列表
     * @param page
     * @param param
     * @return
     */
    IPage<SaasApplication> getRejectOfWorkbench(IPage<SaasApplication> page,@Param("p") Map<String,Object> param);


    /**
     *工作台-SaaS使用列表
     * @param page
     * @param param
     * @return
     */
    IPage<SaasApplication> getUseOfWorkbench(IPage<SaasApplication> page,@Param("p") Map<String,Object> param);

    IPage<ResourceOverviewVO> getApplicationPage(IPage<ResourceOverviewVO> page,@Param("p") Map<String,Object> param);

    HashMap<String,Long> getApplicationOverview(@Param("p")  Map<String,Object> param);

    IPage<UserInfoVo> getUser(IPage<UserInfoVo> page, @Param("creatorName") String creatorName, @Param("creator") String creator, @Param("orgName") String orgName, @Param("areaOrPolice") String areaOrPolice);
}
