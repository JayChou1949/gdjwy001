package com.hirisun.cloud.order.mapper.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.model.application.CloudDeskIdAndUnitVo;
import com.hirisun.cloud.model.application.CloudDeskNumByAreaOrPoliceVo;
import com.hirisun.cloud.model.application.CloudIdAndUseTypeVo;
import com.hirisun.cloud.model.daas.vo.DaasApplicationExtVo;
import com.hirisun.cloud.model.daas.vo.GeneralVo;
import com.hirisun.cloud.model.es.EcsStatistics;
import com.hirisun.cloud.model.es.EcsStatisticsTotal;
import com.hirisun.cloud.model.export.vo.EpidemicApplicationExportVo;
import com.hirisun.cloud.model.export.vo.IaasZYSBapplicationExportVo;
import com.hirisun.cloud.model.export.vo.PassDaasIaasApplicationExportVo;
import com.hirisun.cloud.model.export.vo.SaasApplicationExportVo;
import com.hirisun.cloud.model.export.vo.WorkflowApplyExportVo;
import com.hirisun.cloud.model.ncov.vo.iaas.NcovIaasVo;
import com.hirisun.cloud.model.resource.ResourceCount;
import com.hirisun.cloud.model.saas.vo.SaasLvThreeVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workbench.vo.ResourceOverviewVO;
import com.hirisun.cloud.order.bean.application.ApplicationInfo;
import com.hirisun.cloud.order.bean.publish.ServicePublishApplicationRegistExport;

/**
 * 申请信息表 Mapper 接口
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
    int getTodoCount(@Param("user") UserVO user, @Param("resourceType") Long resourceType);
    /**
     * 获取待办数 (新流程)
     */
    int getNewCount(@Param("user") UserVO user, @Param("resourceType") Long resourceType);

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

    List<String> getAppNameList(@Param("user") UserVO user);

    List<ResourceCount> getUseRes(@Param("resourceType") Long resourceType, @Param("user") UserVO user, @Param("appName") String appName);

    Integer getDaasUseRes(@Param("user") UserVO user, @Param("appName") String appName);

    int getReviewCount(@Param("resourceType") Long resourceType, @Param("user") UserVO user,@Param("p") Map<String,Object> param);

    int getImplCount(@Param("resourceType") Long resourceType, @Param("user") UserVO user,@Param("p") Map<String,Object> param);

    int getImplCountBank(@Param("resourceType") Long resourceType, @Param("user") UserVO user,@Param("p") Map<String,Object> param);


    int getRejectCount(@Param("resourceType") Long resourceType, @Param("user") UserVO user,@Param("p") Map<String,Object> param);

    int getUseCount(@Param("resourceType") Long resourceType, @Param("user") UserVO user,@Param("p") Map<String,Object> param);

    IPage<ApplicationInfo> getIaasPaasWorkbenchPage(IPage<ApplicationInfo> page, @Param("p") Map<String, Object> param);

    IPage<DaasApplicationExtVo> getDaasWorkbenchPage(IPage<DaasApplicationExtVo> page, @Param("p") Map<String, Object> param);

    List<ApplicationInfo> iaasOrderStatistics(Map params);

    List<PassDaasIaasApplicationExportVo> pdiApplicationExport(@Param("areas")String areas, @Param("policeCategory") String policeCategory,@Param("p") Map<String, String> params);

    /**
     * saas 申请信息导出
     * @return
     */
    List<SaasApplicationExportVo> saasApplicationExport(@Param("areas")String areas, @Param("policeCategory") String policeCategory,@Param("p") Map<String, String> params);

    /**
     * 疫情应用  申请信息导出
     * @return
     */
    List<EpidemicApplicationExportVo> epidemicApplicationExport(@Param("areas")String areas, @Param("policeCategory") String policeCategory, @Param("p") Map<String, String> params);


    /**
     * 服务应用发布注册 申请信息导出
     * @return
     */
    List<ServicePublishApplicationRegistExport> servicePublishRegistExport(@Param("areas")String areas, @Param("policeCategory") String policeCategory,@Param("p") Map<String, String> params);

    /**
     * iaas资源上报 申请信息导出
     * @return
     */
    List<IaasZYSBapplicationExportVo> iaasZysbAppExport(@Param("areas")String areas, @Param("policeCategory") String policeCategory,@Param("p") Map<String, String> params);

    /******************************************重写****************************************/

    /**
     * 获取 IaaS PaaS DaaS申请 导出数据
     * @return
     */
    List<WorkflowApplyExportVo> getIPDApplicationInfoOrderList(Map<String, Object> params);

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
    List<GeneralVo> getTenantUsePaasResource(@Param("p") Map<String,Object> param);

    /**
     * 获取使用中iaas服务类别
     */
    List<ApplicationInfo> getTenantUseIaasResourceInfo(@Param("p") Map<String ,Object> param);


    List<GeneralVo> getTenantUseIaasType(@Param("p") Map<String,Object> param);

    /**
     * SaaS应用使用DaaS或PaaS服务列表（通过门户订单查询）
     * @param name
     * @param type
     * @return
     */
    List<GeneralVo> serviceOfSaas(@Param("name") String name,@Param("type") Long type);


    List<SaasLvThreeVo> daasServiceOfSaasStatistics(@Param("name") String name);

    List<SaasLvThreeVo> paasServiceOfSaasStatistics(@Param("name") String name);

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


    List<CloudIdAndUseTypeVo> allCloudId(String startTime);

    Integer policeNum(String startTime);

    Integer areaNum(String startTime);

    List<CloudDeskNumByAreaOrPoliceVo> cloudDeskByArea(String startTime);

    List<CloudDeskIdAndUnitVo> idAndUnit(String startTime);

    List<CloudDeskNumByAreaOrPoliceVo> cloudDeskByPolice(String startTime);

    Integer isOrNotCloudDesktop(@Param(value = "creator") String creator);

    /**
     * 删除申请单  通过申请单号
     * @param applyNo
     */
    void deleteApplyByNo(@Param(value = "applyNo") String applyNo);
}
