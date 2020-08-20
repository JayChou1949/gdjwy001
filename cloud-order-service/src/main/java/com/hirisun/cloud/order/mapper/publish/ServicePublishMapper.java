package com.hirisun.cloud.order.mapper.publish;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.model.service.publish.vo.ServicePublishVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workbench.vo.ResourceOverviewVO;
import com.hirisun.cloud.order.bean.publish.ServicePublish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 服务发布 Mapper 接口
 */
public interface ServicePublishMapper extends BaseMapper<ServicePublish> {

    ServicePublishVo getByActId(@Param("activityId") String activityId);

    IPage<ServicePublish> getPage(IPage<ServicePublish> page, @Param("p") Map<String, Object> param);
    int getPublishTodoCount(@Param("user") UserVO user);

    int getPublishTodo(@Param("idCard") String idCard);

    IPage<ServicePublish> getWorkbenchApplyPage(IPage<ServicePublish> page, @Param("p") Map<String, Object> param);

    IPage<ServicePublish> getPageFromMY(IPage<ServicePublish> page, @Param("p") Map<String, Object> param);

    IPage<ResourceOverviewVO> getResourcePage(IPage<ResourceOverviewVO> page,@Param("type") String type,@Param("p") Map<String,Object> param);

    String getType(@Param("id") String id);

    int getReviewCount(@Param("user") UserVO user,@Param("p") Map<String,Object> param);

    int getImplCount(@Param("user") UserVO user,@Param("p") Map<String,Object> param);

    int getRejectCount(@Param("user") UserVO user,@Param("p") Map<String,Object> param);

    int getUseCount(@Param("user") UserVO user,@Param("p") Map<String,Object> param);

}
