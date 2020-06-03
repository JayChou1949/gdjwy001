package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.entity.SaasApplication;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.vo.workbench.ResourceOverviewVO;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * SaaS资源申请原始信息表 Mapper 接口
 * </p>
 *
 * @author wuc
 * @since 2019-07-24
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
    int getSaasTodoCount(@Param("user") User user);

    int getApplicationTodo(@Param("idCard") String idCard);

    Integer getSaasUseRes(@Param("user") User user, @Param("appName") String appName);

    int getReviewCount(@Param("user") User user,@Param("p") Map<String,Object> param);

    int getImplCount(@Param("user") User user,@Param("p") Map<String,Object> param);

    int getRejectCount(@Param("user") User user,@Param("p") Map<String,Object> param);

    int getUseCount(@Param("user") User user,@Param("p") Map<String,Object> param);

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


}
