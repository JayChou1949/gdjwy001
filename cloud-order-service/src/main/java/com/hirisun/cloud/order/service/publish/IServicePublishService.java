package com.hirisun.cloud.order.service.publish;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.model.apig.vo.ServiceReturnBeanVo;
import com.hirisun.cloud.model.service.publish.vo.ServicePublishVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workbench.vo.QueryVO;
import com.hirisun.cloud.model.workbench.vo.ResourceOverviewVO;
import com.hirisun.cloud.order.bean.publish.ServicePublish;

import java.util.Map;


public interface IServicePublishService extends IService<ServicePublish> {

    void save(UserVO user, ServicePublishVo info);

    void saveMY(ServicePublishVo info);

    ServicePublishVo getDetails(String id);

    ServicePublishVo getDetailsMY(String id);

    void update(UserVO user, ServicePublishVo info);

    void deleteById(UserVO user, String id);

    ServicePublishVo getByActId(String activityId);

    ServiceReturnBeanVo apigOrderService(ServicePublishVo info) throws Exception;

    void saveImpl(UserVO user, Map<String, Object> param, String modelid) throws Exception;

    IPage<ServicePublishVo> getPage(UserVO user, IPage<ServicePublishVo> page, Map<String, Object> param);

    IPage<ResourceOverviewVO> getResourcePage(Long pageNum,Long pageSize,String type,QueryVO queryVO,UserVO user);

    IPage<ServicePublishVo> getWorkbenchApplyPage(UserVO user, IPage<ServicePublishVo> page,QueryVO queryVO);

    IPage<ServicePublishVo> getPageFromMY(UserVO user, IPage<ServicePublishVo> page, Map<String, Object> param,String processType);

    int getPublishTodoCount(UserVO user);

    int getPublishTodo(String idCard);

    boolean convertIntoSaas(ServicePublishVo ServicePublishVo,String serviceGuid);

    boolean convertIntoPaas(ServicePublishVo ServicePublishVo, ServiceReturnBeanVo returnBean);

    boolean convertIntoDaas(ServicePublishVo ServicePublishVo,ServiceReturnBeanVo returnBean);

    int getReviewCount(UserVO user, QueryVO queryVO);

    int getImplCount(UserVO user,QueryVO queryVO);

    int getRejectCount(UserVO user,QueryVO queryVO);

    int getUseCount(UserVO user,QueryVO queryVO);

    ResponseResult servicePublishApply(UserVO user,QueryVO queryVO);


}
