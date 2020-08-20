package com.hirisun.cloud.order.service.application;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.common.contains.ResourceType;
import com.hirisun.cloud.model.daas.vo.DaasApplicationExtVo;
import com.hirisun.cloud.model.es.EcsStatistics;
import com.hirisun.cloud.model.es.EcsStatisticsTotal;
import com.hirisun.cloud.model.export.vo.EpidemicApplicationExportVo;
import com.hirisun.cloud.model.export.vo.IaasZYSBapplicationExportVo;
import com.hirisun.cloud.model.export.vo.PassDaasIaasApplicationExportVo;
import com.hirisun.cloud.model.export.vo.SaasApplicationExportVo;
import com.hirisun.cloud.model.export.vo.WorkflowApplyExportVo;
import com.hirisun.cloud.model.ncov.vo.iaas.NcovIaasVo;
import com.hirisun.cloud.model.open.TodoVo;
import com.hirisun.cloud.model.resource.ResourceCount;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workbench.vo.QueryVO;
import com.hirisun.cloud.order.bean.ApplicationInfo;
import com.hirisun.cloud.order.bean.application.ApplicationFeedback;
import com.hirisun.cloud.order.bean.application.UpdateApplicationInfo;
import com.hirisun.cloud.order.bean.publish.ServicePublishApplicationRegistExport;
import com.hirisun.cloud.order.bean.shopping.ShoppingCart;
import com.hirisun.cloud.order.continer.IApplicationHandler;
import com.hirisun.cloud.order.continer.IImplHandler;

/**
 * 申请信息表 服务类
 */
public interface IApplicationInfoService extends IService<ApplicationInfo> {

    <S> void addToShoppingCart(UserVO user, ShoppingCart<S> shoppingCart,IApplicationHandler<S> handler);

    <S> void save(UserVO user, ApplicationInfo<S, Object> info, IApplicationHandler<S> handler,boolean special);

    <S> void update(UserVO user, UpdateApplicationInfo<S> updateInfo, IApplicationHandler<S> handler);

    /**
     * 草稿
     */
    <S> void draft(UserVO user, ApplicationInfo<S, Object> info, IApplicationHandler<S> handler);

    <S, I> ApplicationInfo<S, I> getDetail(UserVO user, String id, IApplicationHandler<S> handler,
                                           IImplHandler<I> implHandler);

    public <S, I> ApplicationInfo<S, I> getNewFlowDetail(UserVO user, String id, IApplicationHandler<S> handler, IImplHandler<I> implHandler);
    /**
     * 实施
     */
    <I> void impl(UserVO user, Map<String, Object> param, IImplHandler<I> implHandler);
    <I> void saveImpl(UserVO user, Map<String, Object> param, IImplHandler<I> implHandler,String modelId) throws Exception;
    /**
     * 购物车提交
     * @param user
     * @param param
     */
    void shoppingCartSubmit(UserVO user, Map<String, Object> param);

    /**
     * 购物车
     */
    List<ApplicationInfo> shoppingCart(UserVO user);

    /**
     * 购物车条目数
     */
    int shoppingCartCount(UserVO user);

    IPage<ApplicationInfo> getPage(UserVO user, IPage<ApplicationInfo> page, String userName,
                                   String status, Long resourceType, String processType);
    IPage<ApplicationInfo> getFlowPage(UserVO user, IPage<ApplicationInfo> page, Map<String, Object> param);

    IPage<ApplicationInfo> getFlowPageOfDaas(UserVO user, IPage<ApplicationInfo> page, Map<String, Object> param);

    IPage<ApplicationInfo> getFlowPageOfSaasService(UserVO user, IPage<ApplicationInfo> page, Map<String, Object> param);
    /**
     * 审核
     */
    void review(UserVO user, Map<String, Object> param) throws Exception;

    /**
     * 未使用中 的申请还原到待审核状态(不包含购物车中的)
     * @param resourceType 资源类型
     */
    void revertStatusByResourceType(Long resourceType);

    /**
     * 未使用中 的申请还原到待审核状态(不包含购物车中的)
     * @param serviceId 服务 id
     */
    void revertStatusByServiceId(String serviceId);

    /**
     * 后台,逻辑删除
     */
    void deleteById(UserVO user, String id);

    public Map<String, Integer> newTodo(UserVO user);

    /**
     * 运维平台待办
     * @param idCard 身份证号
     * @return
     */
    public TodoVo getMaintenanceTodoVo(String idCard);
    /**
     * 转发
     */
    void forward(UserVO user, Map<String, Object> param);

    /**
     * 反馈
     */
    void feedback(UserVO user, String id, ApplicationFeedback feedback);

    List<ApplicationInfo> findByAppName(String status);

    String getRePerson(String appInfoId);

    ApplicationInfo getByActId(String activityId);

    EcsStatisticsTotal ecsStatistics4Recent(String type, String name);

    IPage<EcsStatistics> ecsList4Recent(IPage<EcsStatistics> page, String type, String name);

    EcsStatisticsTotal ecsStatistics4Applied(String type, String name);

    IPage<EcsStatistics> ecsList4Applied(IPage<EcsStatistics> page, String type, String name);

    EcsStatisticsTotal ecsStatistics4Review(String type, String name);

    IPage<EcsStatistics> ecsList4Review(IPage<EcsStatistics> page, String type, String name);

    /**
     * 获取申请的所有应用名(使用中的)
     */
    List<String> getAppNameList(UserVO user);

    List<ResourceCount> getIaasPaasUseRes(ResourceType iaas, UserVO user, String appName);

    Integer getDaasUseRes(UserVO user, String appName);

    int getReviewCount(ResourceType iaas, UserVO user, QueryVO queryVO);

    int getImplCount(ResourceType iaas, UserVO user,QueryVO queryVO);

    int getRejectCount(ResourceType iaas, UserVO user,QueryVO queryVO);

    int getUseCount(ResourceType iaas, UserVO user,QueryVO queryVO);

    IPage<ApplicationInfo> getIaasPaasWorkbenchPage(IPage<ApplicationInfo> page, Map<String, Object> param);

    IPage<DaasApplicationExtVo> getDaasWorkbenchPage(IPage<DaasApplicationExtVo> page, Map<String, Object> param);

    /**
     * 查询申请信息
     * @param params
     * @return
     */
    List<ApplicationInfo> iaasOrderStatistics(Map params);

    /**
     * paas iaas dass 申请信息导出
     * @return
     */
    List<PassDaasIaasApplicationExportVo> pdiApplicationExport(String areas,String policeCategory,Map<String, String> params);
    /**
     * saas 申请信息导出
     * @return
     */
    List<SaasApplicationExportVo> saasApplicationExport(String areas,String policeCategory,Map<String, String> params);

    /**
     * 疫情应用工单导出
     * @param areas
     * @param policeCategory
     * @param params
     * @return
     */
    List<EpidemicApplicationExportVo> epidemicApplicationExport(String areas,String policeCategory,Map<String, String> params);

    /**
     * 服务应用发布注册 申请信息导出
     * @return
     */
    List<ServicePublishApplicationRegistExport> servicePublishRegistExport(String areas,String policeCategory,Map<String, String> params);

    /**
     * iaas资源上报 申请信息导出
     * @return
     */
    List<IaasZYSBapplicationExportVo> iaasZysbAppExport(String areas,String policeCategory,Map<String, String> params);

    /**
     * 获取 IaaS PaaS DaaS申请 导出数据
     * @return
     */
    List<WorkflowApplyExportVo> getIPDApplicationInfoOrderList(Map<String, Object> params);

    /**
     * 获取IaaS资源上报 导出数据
     * @param param
     * @return
     */
    List<IaasZYSBapplicationExportVo> getIaaSZysbList(Map<String, Object> param) throws InvocationTargetException, IllegalAccessException;

    /**
     * 获取应用注册 导出数据
     * @param param
     * @return
     */
    List<ServicePublishApplicationRegistExport> getAppRegistList(Map<String, Object> param) throws InvocationTargetException, IllegalAccessException;

    /**
     * 疫情-IaaS-支撑
     * @param startTime
     * @return
     */
    Map<String,Long> getIaasSupportNcovInfo(String startTime);

    List<NcovIaasVo> getIaasNcovList(String startTime);

    /**开发给运维平台**/
    Map<String,Integer> getLeaderTodoView(String idCard);

    Integer isOrNotCloudDesktop(String creator);

}
