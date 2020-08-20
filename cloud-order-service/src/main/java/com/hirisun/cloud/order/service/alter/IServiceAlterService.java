package com.hirisun.cloud.order.service.alter;

import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.model.apig.vo.ServiceReturnBeanVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workbench.vo.QueryVO;
import com.hirisun.cloud.model.workbench.vo.ResourceOverviewVO;
import com.hirisun.cloud.order.bean.alter.ServiceAlter;


public interface IServiceAlterService extends IService<ServiceAlter> {

    void save(UserVO user, ServiceAlter info);

    void saveMY(ServiceAlter info);

    ServiceAlter getDetails(String id);

    ServiceAlter getDetailsMY(String id);

    void update(UserVO user, ServiceAlter info);

    void deleteById(UserVO user, String id);

    ServiceAlter getByActId(String activityId);

    ServiceReturnBeanVo apigOrderService(ServiceAlter info) throws Exception;

    void saveImpl(UserVO user, Map<String, Object> param, String modelid) throws Exception;

    IPage<ServiceAlter> getPage(UserVO user, IPage<ServiceAlter> page, Map<String, Object> param);

    IPage<ResourceOverviewVO> getResourcePage(Long pageNum,Long pageSize,String type,QueryVO queryVO,UserVO user);

    IPage<ServiceAlter> getWorkbenchApplyPage(UserVO user, IPage<ServiceAlter> page,QueryVO queryVO);

    IPage<ServiceAlter> getPageFromMY(UserVO user, IPage<ServiceAlter> page, Map<String, Object> param,String processType);

    int getAlterTodoCount(UserVO user);

    int getAlterTodo(String idCard);

    boolean convertIntoSaas(ServiceAlter ServiceAlter,String serviceGuid);

    boolean convertIntoPaas(ServiceAlter ServiceAlter, ServiceReturnBeanVo returnBean);

    boolean convertIntoDaas(ServiceAlter ServiceAlter,ServiceReturnBeanVo returnBean);

    int getReviewCount(UserVO user, QueryVO queryVO);

    int getImplCount(UserVO user,QueryVO queryVO);

    int getRejectCount(UserVO user,QueryVO queryVO);

    int getUseCount(UserVO user,QueryVO queryVO);

    R ServiceAlterApply(UserVO user,QueryVO queryVO);


}
