package com.hirisun.cloud.order.mapper.alter;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hirisun.cloud.model.service.alter.vo.ServiceAlterVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workbench.vo.ResourceOverviewVO;
import com.hirisun.cloud.order.bean.alter.ServiceAlter;

public interface ServiceAlterMapper extends BaseMapper<ServiceAlter> {

	ServiceAlterVo getByActId(@Param("activityId") String activityId);

    IPage<ServiceAlterVo> getPage(IPage<ServiceAlterVo> page, @Param("p") Map<String, Object> param);
    int getAlterTodoCount(@Param("user") UserVO user);

    int getAlterTodo(@Param("idCard") String idCard);

    IPage<ServiceAlterVo> getWorkbenchApplyPage(IPage<ServiceAlterVo> page, @Param("p") Map<String, Object> param);

    IPage<ServiceAlterVo> getPageFromMY(IPage<ServiceAlterVo> page, @Param("p") Map<String, Object> param);

    IPage<ResourceOverviewVO> getResourcePage(IPage<ResourceOverviewVO> page,@Param("type") String type,@Param("p") Map<String,Object> param);

    String getType(@Param("id") String id);

    int getReviewCount(@Param("user") UserVO user,@Param("p") Map<String,Object> param);

    int getImplCount(@Param("user") UserVO user,@Param("p") Map<String,Object> param);

    int getRejectCount(@Param("user") UserVO user,@Param("p") Map<String,Object> param);

    int getUseCount(@Param("user") UserVO user,@Param("p") Map<String,Object> param);
    
}
