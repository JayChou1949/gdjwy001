package com.hirisun.cloud.saas.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workbench.vo.QueryVO;
import com.hirisun.cloud.model.workbench.vo.ResourceOverviewVO;
import com.hirisun.cloud.saas.bean.SaasApplication;
import com.hirisun.cloud.saas.bean.SaasApplicationImport;

/**
 * <p>
 * SaaS资源申请原始信息表 服务类
 * </p>
 *
 * @author wuc
 * @since 2019-07-24
 */
public interface ISaasApplicationService extends IService<SaasApplication> {

    void create(UserVO user, SaasApplication info);

    void update(UserVO user, SaasApplication info);

    Integer getWorkbenchApplyCount(UserVO user,QueryVO queryVO);

    IPage<SaasApplication> getPage(UserVO user, IPage<SaasApplication> page, Map<String, Object> param);

    Map<String, Object> getDetail(UserVO user, String id);

    /**
     * 获取待合并列表
     * @param keyword 关键字
     */
    IPage<SaasApplication> mergePage(UserVO user, IPage<SaasApplication> page, String keyword);

    /**
     * 修改状态,和合并的单子保持一致
     * @param mergeId
     * @param status
     */
    void updateStatus(String mergeId, String status);

    /**
     * 同步合并后单子的id查询合并的申请原始单
     * @param id
     * @return
     */
    List<SaasApplication> getListByMergeId(String id);

    int getSaasTodoCount(UserVO user);

    int getApplicationTodo(String idCard);

    Integer getSaasUseRes(UserVO user, String appName);

    int getReviewCount(UserVO user, QueryVO queryVO);

    int getImplCount(UserVO user,QueryVO queryVO);

    int getRejectCount(UserVO user,QueryVO queryVO);

    int getUseCount(UserVO user,QueryVO queryVO);

    IPage<SaasApplication> getWorkbenchPage(IPage<SaasApplication> page, Map<String, Object> param);

    void submit(UserVO user, String id);

    void reject(UserVO user, String id);

    /**
     * 查询SAAS所申请过的用户
     *
     */
    IPage<SaasApplication> getApplicationUser(IPage<SaasApplication> page, String area,String policeCategory,
                                              String name,String idcard);

    /**
     * 获取用户正在使用的服务(包含正在回收的服务)
     */
    List<SaasApplication> getSaasUseService(String userId);
    /**
     * 获取用户正在使用的服务(包含正在回收的服务)
     */
    List<SaasApplication> getSaasRecoverManageUseService(String userId);
    /**
     * 更新回收申请单中人员的服务状态,由 回收中-->未回收
     * @param id 回收申请单id
     */
    void updateRecovering2NotRecover(String id);

    /**
     * 更新回收申请单中人员的服务状态,所有在使用服务都更新为已回收
     * @param id 回收申请单id
     */
    void updateUse2Recover(String id);

    /**
     * 工作台应用分页
     * @param pageNum
     * @param pageSize
     * @param user
     * @param appName
     * @return
     */
    IPage<ResourceOverviewVO> getWorkbenchNewPage(Long pageNum,Long pageSize,UserVO user,String appName);

    HashMap<String,Long> getWorkbenchOverview(UserVO user);

    List<SaasApplicationImport> importData(MultipartFile file, String idcard);

    void downloadFile(HttpServletResponse response) throws IOException;

    void uploadFile(MultipartFile file) throws IOException;
}
