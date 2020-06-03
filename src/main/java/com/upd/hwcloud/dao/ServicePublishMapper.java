package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.upd.hwcloud.bean.entity.ServicePublish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upd.hwcloud.bean.entity.User;

import com.upd.hwcloud.bean.vo.workbench.ResourceOverviewVO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 服务发布 Mapper 接口
 * </p>
 *
 * @author wuc
 * @since 2019-10-17
 */
public interface ServicePublishMapper extends BaseMapper<ServicePublish> {

    ServicePublish getByActId(@Param("activityId") String activityId);

    IPage<ServicePublish> getPage(IPage<ServicePublish> page, @Param("p") Map<String, Object> param);
    int getPublishTodoCount(@Param("user") User user);

    int getPublishTodo(@Param("idCard") String idCard);

    IPage<ServicePublish> getWorkbenchApplyPage(IPage<ServicePublish> page, @Param("p") Map<String, Object> param);

    IPage<ServicePublish> getPageFromMY(IPage<ServicePublish> page, @Param("p") Map<String, Object> param);

    IPage<ResourceOverviewVO> getResourcePage(IPage<ResourceOverviewVO> page,@Param("type") String type,@Param("p") Map<String,Object> param);

    String getType(@Param("id") String id);

    int getReviewCount(@Param("user") User user,@Param("p") Map<String,Object> param);

    int getImplCount(@Param("user") User user,@Param("p") Map<String,Object> param);

    int getRejectCount(@Param("user") User user,@Param("p") Map<String,Object> param);

    int getUseCount(@Param("user") User user,@Param("p") Map<String,Object> param);

}
