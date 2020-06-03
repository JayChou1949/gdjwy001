package com.upd.hwcloud.service.application;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.contains.ResourceType;
import com.upd.hwcloud.bean.dto.*;
import com.upd.hwcloud.bean.entity.*;
import com.upd.hwcloud.bean.entity.application.ApplicationFeedback;
import com.upd.hwcloud.bean.entity.application.ApplicationInfo;
import com.upd.hwcloud.bean.entity.application.ShoppingCart;
import com.upd.hwcloud.bean.vo.applicationInfoOrder.IPDVo;
import com.upd.hwcloud.bean.vo.ncov.NcovIaasVo;
import com.upd.hwcloud.bean.vo.open.maintenance.TodoVo;
import com.upd.hwcloud.bean.vo.workbench.QueryVO;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 申请信息表 服务类
 * </p>
 *
 * @author wuc
 * @since 2018-11-30
 */
public interface IApplicationInfoService extends IService<ApplicationInfo> {

    <S> void addToShoppingCart(User user, ShoppingCart<S> shoppingCart,IApplicationHandler<S> handler);

    <S> void save(User user, ApplicationInfo<S, Object> info, IApplicationHandler<S> handler,boolean special);

    <S> void update(User user, UpdateApplicationInfo<S> updateInfo, IApplicationHandler<S> handler);

    /**
     * 草稿
     */
    <S> void draft(User user, ApplicationInfo<S, Object> info, IApplicationHandler<S> handler);

    <S, I> ApplicationInfo<S, I> getDetail(User user, String id, IApplicationHandler<S> handler,
                                           IImplHandler<I> implHandler);

    public <S, I> ApplicationInfo<S, I> getNewFlowDetail(User user, String id, IApplicationHandler<S> handler, IImplHandler<I> implHandler);
    /**
     * 实施
     */
    <I> void impl(User user, Map<String, Object> param, IImplHandler<I> implHandler);
    <I> void saveImpl(User user, Map<String, Object> param, IImplHandler<I> implHandler,String modelId) throws Exception;
    /**
     * 购物车提交
     * @param user
     * @param param
     */
    void shoppingCartSubmit(User user, Map<String, Object> param);

    /**
     * 购物车
     */
    List<ApplicationInfo> shoppingCart(User user);

    /**
     * 购物车条目数
     */
    int shoppingCartCount(User user);

    IPage<ApplicationInfo> getPage(User user, IPage<ApplicationInfo> page, String userName,
                                   String status, Long resourceType, String processType);
    IPage<ApplicationInfo> getFlowPage(User user, IPage<ApplicationInfo> page, Map<String, Object> param);

    IPage<ApplicationInfo> getFlowPageOfDaas(User user, IPage<ApplicationInfo> page, Map<String, Object> param);

    IPage<ApplicationInfo> getFlowPageOfSaasService(User user, IPage<ApplicationInfo> page, Map<String, Object> param);
    /**
     * 审核
     */
    void review(User user, Map<String, Object> param) throws Exception;

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
    void deleteById(User user, String id);

    public Map<String, Integer> newTodo(User user);

    /**
     * 运维平台待办
     * @param idCard 身份证号
     * @return
     */
    public TodoVo getMaintenanceTodoVo(String idCard);
    /**
     * 转发
     */
    void forward(User user, Map<String, Object> param);

    /**
     * 反馈
     */
    void feedback(User user, String id, ApplicationFeedback feedback);

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
    List<String> getAppNameList(User user);

    List<ResourceCount> getIaasPaasUseRes(ResourceType iaas, User user, String appName);

    Integer getDaasUseRes(User user, String appName);

    int getReviewCount(ResourceType iaas, User user, QueryVO queryVO);

    int getImplCount(ResourceType iaas, User user,QueryVO queryVO);

    int getRejectCount(ResourceType iaas, User user,QueryVO queryVO);

    int getUseCount(ResourceType iaas, User user,QueryVO queryVO);

    IPage<ApplicationInfo> getIaasPaasWorkbenchPage(IPage<ApplicationInfo> page, Map<String, Object> param);

    IPage<DaasApplicationExt> getDaasWorkbenchPage(IPage<DaasApplicationExt> page, Map<String, Object> param);

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
    List<PassDaasIaasApplicationExport> pdiApplicationExport(String areas,String policeCategory,Map<String, String> params);
    /**
     * saas 申请信息导出
     * @return
     */
    List<SaasApplicationExport> saasApplicationExport(String areas,String policeCategory,Map<String, String> params);
    /**
     * 服务应用发布注册 申请信息导出
     * @return
     */
    List<ServicePublishApplicationRegistExport> servicePublishRegistExport(String areas,String policeCategory,Map<String, String> params);

    /**
     * iaas资源上报 申请信息导出
     * @return
     */
    List<IaasZYSBapplicationExport> iaasZysbAppExport(String areas,String policeCategory,Map<String, String> params);

    /**
     * 获取 IaaS PaaS DaaS申请 导出数据
     * @return
     */
    List<IPDVo> getIPDApplicationInfoOrderList(Map<String, Object> params);

    /**
     * 获取IaaS资源上报 导出数据
     * @param param
     * @return
     */
    List<IaasZYSBapplicationExport> getIaaSZysbList(Map<String, Object> param) throws InvocationTargetException, IllegalAccessException;

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

}
