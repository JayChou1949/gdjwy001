package com.upd.hwcloud.dao.application;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.dto.*;
import com.upd.hwcloud.bean.dto.cov.CloudDeskIdAndUnit;
import com.upd.hwcloud.bean.dto.cov.CloudDeskNumByAreaOrPolice;
import com.upd.hwcloud.bean.dto.cov.CloudIdAndUseType;
import com.upd.hwcloud.bean.entity.IaasZYSBapplicationExport;
import com.upd.hwcloud.bean.entity.PassDaasIaasApplicationExport;
import com.upd.hwcloud.bean.entity.SaasApplicationExport;
import com.upd.hwcloud.bean.entity.ServicePublishApplicationRegistExport;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.vo.applicationInfoOrder.IPDVo;
import com.upd.hwcloud.bean.vo.ncov.NcovIaasVo;
import com.upd.hwcloud.bean.vo.workbench.ResourceOverviewVO;
import com.upd.hwcloud.bean.vo.workbench.SaasLvThree;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 申请信息表 Mapper 接口
 * </p>
 *
 * @author wuc
 * @since 2018-11-30
 */
public interface ApplicationInfoMapper extends BaseMapper<ApplicationInfo> {

    IPage<ApplicationInfo> getPage(IPage<ApplicationInfo> page, @Param("p") Map<String, Object> param);

    /**
     * 获取今日申请单总数
     */
    int getCountToday();

    /**
     * 获取待办数 (待审核,待实施)
     */
    int getTodoCount(@Param("user") User user, @Param("resourceType") Long resourceType);
    /**
     * 获取待办数 (新流程)
     */
    int getNewCount(@Param("user") User user, @Param("resourceType") Long resourceType);

    int getServiceTodo(@Param("idCard") String idCard);

    String getProcessingPersonByStepId(@Param("stepId") String stepId);

    String getProcessingPersonByAppInfoId(@Param("appInfoId") String appInfoId);
    String getRePerson(@Param("appInfoId") String appInfoId);
    List<ApplicationInfo> findByAppName(@Param("status") String status);

    ApplicationInfo getAppInfo(String id);

    IPage<ApplicationInfo> getFlowPage(IPage<ApplicationInfo> page, @Param("p") Map<String, Object> param);

    IPage<ApplicationInfo> getFlowPageOfDaas(IPage<ApplicationInfo> page, @Param("p") Map<String, Object> param);

    IPage<ApplicationInfo> getFlowPageOfSaasService(IPage<ApplicationInfo> page, @Param("p") Map<String, Object> param);

    IPage<ApplicationInfo> getFlowPageBank(IPage<ApplicationInfo> page, @Param("p") Map<String, Object> param);

    IPage<ApplicationInfo> getFlowPageOfWorkbench(IPage<ApplicationInfo> page, @Param("p") Map<String, Object> param);

    ApplicationInfo getByActId(String activityId);

    EcsStatisticsTotal ecsStatistics4Recent(@Param("type") String type, @Param("name") String name);

    IPage<EcsStatistics> ecsList4Recent(IPage<EcsStatistics> page, @Param("type") String type, @Param("name") String name);

    EcsStatisticsTotal ecsStatistics4Applied(@Param("type") String type, @Param("name") String name);

    IPage<EcsStatistics> ecsList4Applied(IPage<EcsStatistics> page, @Param("type") String type, @Param("name") String name);

    EcsStatisticsTotal ecsStatistics4Review(@Param("type") String type, @Param("name") String name);

    IPage<EcsStatistics> ecsList4Review(IPage<EcsStatistics> page, @Param("type") String type, @Param("name") String name);

    List<String> getAppNameList(@Param("user") User user);

    List<ResourceCount> getUseRes(@Param("resourceType") Long resourceType, @Param("user") User user, @Param("appName") String appName);

    Integer getDaasUseRes(@Param("user") User user, @Param("appName") String appName);

    int getReviewCount(@Param("resourceType") Long resourceType, @Param("user") User user,@Param("p") Map<String,Object> param);

    int getImplCount(@Param("resourceType") Long resourceType, @Param("user") User user,@Param("p") Map<String,Object> param);

    int getImplCountBank(@Param("resourceType") Long resourceType, @Param("user") User user,@Param("p") Map<String,Object> param);


    int getRejectCount(@Param("resourceType") Long resourceType, @Param("user") User user,@Param("p") Map<String,Object> param);

    int getUseCount(@Param("resourceType") Long resourceType, @Param("user") User user,@Param("p") Map<String,Object> param);

    IPage<ApplicationInfo> getIaasPaasWorkbenchPage(IPage<ApplicationInfo> page, @Param("p") Map<String, Object> param);

    IPage<DaasApplicationExt> getDaasWorkbenchPage(IPage<DaasApplicationExt> page, @Param("p") Map<String, Object> param);

    List<ApplicationInfo> iaasOrderStatistics(Map params);

    List<PassDaasIaasApplicationExport> pdiApplicationExport(@Param("areas")String areas, @Param("policeCategory") String policeCategory,@Param("p") Map<String, String> params);

    /**
     * saas 申请信息导出
     * @return
     */
    List<SaasApplicationExport> saasApplicationExport(@Param("areas")String areas, @Param("policeCategory") String policeCategory,@Param("p") Map<String, String> params);
    /**
     * 服务应用发布注册 申请信息导出
     * @return
     */
    List<ServicePublishApplicationRegistExport> servicePublishRegistExport(@Param("areas")String areas, @Param("policeCategory") String policeCategory,@Param("p") Map<String, String> params);

    /**
     * iaas资源上报 申请信息导出
     * @return
     */
    List<IaasZYSBapplicationExport> iaasZysbAppExport(@Param("areas")String areas, @Param("policeCategory") String policeCategory,@Param("p") Map<String, String> params);

    /******************************************重写****************************************/

    /**
     * 获取 IaaS PaaS DaaS申请 导出数据
     * @return
     */
    List<IPDVo> getIPDApplicationInfoOrderList(Map<String, Object> params);

    /**
     * 通过ID查询当前节点名称
     * @param id
     * @return
     */
    String getCurrentNodeById(@Param("id") String id);


    /**
     *  获取使用中paas服务
     * @param param
     * @return
     */
    List<GeneralDTO> getTenantUsePaasResource(@Param("p") Map<String,Object> param);

    /**
     * 获取使用中iaas服务类别
     */
    List<ApplicationInfo> getTenantUseIaasResourceInfo(@Param("p") Map<String ,Object> param);


    List<GeneralDTO> getTenantUseIaasType(@Param("p") Map<String,Object> param);

    /**
     * SaaS应用使用DaaS或PaaS服务列表（通过门户订单查询）
     * @param name
     * @param type
     * @return
     */
    List<GeneralDTO> serviceOfSaas(@Param("name") String name,@Param("type") Long type);


    List<SaasLvThree> daasServiceOfSaasStatistics(@Param("name") String name);

    List<SaasLvThree> paasServiceOfSaasStatistics(@Param("name") String name);

    /**********************工作台分页*****************************/
    IPage<ResourceOverviewVO> getDaasResourcePage(IPage<ResourceOverviewVO> page,@Param("p") Map<String,Object> param);

    IPage<ResourceOverviewVO> getPaasResourcePage(IPage<ResourceOverviewVO> page,@Param("p") Map<String,Object> param);

    /**********************工作台概览*****************************/
    HashMap<String,Long> getDaasResourceOverview(@Param("p") Map<String,Object> param);

    HashMap<String,Long> getPaasResourceOverview(@Param("p") Map<String,Object> param);

    /****获取疫情期间的使用中弹性云服务器表单**/
    List<ApplicationInfo> getNcovUsedEcsList(String startTime);

    /******获取疫情期间Iaas资源分配情况******/
    List<NcovIaasVo> getNcovIaasVoList(String startTime);


    List<CloudIdAndUseType> allCloudId(String startTime);

    Integer policeNum(String startTime);

    Integer areaNum(String startTime);

    List<CloudDeskNumByAreaOrPolice> cloudDeskByArea(String startTime);

    List<CloudDeskIdAndUnit> idAndUnit(String startTime);

    List<CloudDeskNumByAreaOrPolice> cloudDeskByPolice(String startTime);
}
