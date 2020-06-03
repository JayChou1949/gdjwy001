package com.upd.hwcloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.dto.apig.ServiceReturnBean;
import com.upd.hwcloud.bean.entity.ServicePublish;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.response.R;
import com.upd.hwcloud.bean.vo.workbench.QueryVO;
import com.upd.hwcloud.bean.vo.workbench.ResourceOverviewVO;

import java.util.Map;

/**
 * <p>
 * 服务发布 服务类
 * </p>
 *
 * @author wuc
 * @since 2019-10-17
 */
public interface IServicePublishService extends IService<ServicePublish> {

    void save(User user, ServicePublish info);

    void saveMY(ServicePublish info);

    ServicePublish getDetails(String id);

    ServicePublish getDetailsMY(String id);

    void update(User user, ServicePublish info);

    void deleteById(User user, String id);

    ServicePublish getByActId(String activityId);

    ServiceReturnBean apigOrderService(ServicePublish info) throws Exception;

    void saveImpl(User user, Map<String, Object> param, String modelid) throws Exception;

    IPage<ServicePublish> getPage(User user, IPage<ServicePublish> page, Map<String, Object> param);

    IPage<ResourceOverviewVO> getResourcePage(Long pageNum,Long pageSize,String type,QueryVO queryVO,User user);

    IPage<ServicePublish> getWorkbenchApplyPage(User user, IPage<ServicePublish> page,QueryVO queryVO);

    IPage<ServicePublish> getPageFromMY(User user, IPage<ServicePublish> page, Map<String, Object> param,String processType);

    int getPublishTodoCount(User user);

    int getPublishTodo(String idCard);

    boolean convertIntoSaas(ServicePublish servicePublish,String serviceGuid);

    boolean convertIntoPaas(ServicePublish servicePublish, ServiceReturnBean returnBean);

    boolean convertIntoDaas(ServicePublish servicePublish,ServiceReturnBean returnBean);

    int getReviewCount(User user, QueryVO queryVO);

    int getImplCount(User user,QueryVO queryVO);

    int getRejectCount(User user,QueryVO queryVO);

    int getUseCount(User user,QueryVO queryVO);

    R servicePublishApply(User user,QueryVO queryVO);


}
