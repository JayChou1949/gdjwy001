package com.hirisun.cloud.order.service.servicePublish;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.apply.FallBackVO;
import com.hirisun.cloud.model.saas.vo.SaasApplicationVO;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workbench.vo.QueryVO;
import com.hirisun.cloud.order.bean.servicePublish.ServicePublish;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.order.vo.ApproveVO;
import com.hirisun.cloud.order.vo.ImplRequest;

import java.util.Map;

/**
 * <p>
 * 服务发布 服务类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-09-01
 */
public interface ServicePublishService extends IService<ServicePublish> {

    public IPage<ServicePublish> getPage(UserVO user, IPage<ServicePublish> page, Map<String, Object> param);

    void saveServicePublish(UserVO user, ServicePublish info);

    public QueryResponseResult detail(String id);

    public ServicePublish getDetails(String id);

    public void update(UserVO user, ServicePublish info);

    public void deleteById(UserVO user, String id);

    public void saveImpl(UserVO user, Map<String, Object> param, String modelId) throws Exception;

    public int getReviewCount(UserVO user, QueryVO queryVO);

    public int getImplCount(UserVO user, QueryVO queryVO);

    public int getRejectCount(UserVO user, QueryVO queryVO);

    public int getUseCount(UserVO user, QueryVO queryVO);

    public QueryResponseResult servicePublishApply(UserVO user, QueryVO queryVO);

    public QueryResponseResult create(UserVO user,ServicePublish servicePublish);

    public QueryResponseResult deleteObj(UserVO user, String id);

    public QueryResponseResult submit(UserVO user, String id, String type, String userIds);

    public QueryResponseResult forward(UserVO user, String activityId, String userIds, String applyInfoId);

    public QueryResponseResult approve(UserVO user, FallBackVO vo);

    public QueryResponseResult reject(UserVO user, FallBackVO vo);

    public QueryResponseResult impl(UserVO user,String id, String modelId, String activityId, ImplRequest implRequest) throws Exception;

    public QueryResponseResult termination(String id);



}
