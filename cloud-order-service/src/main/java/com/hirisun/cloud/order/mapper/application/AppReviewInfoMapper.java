package com.hirisun.cloud.order.mapper.application;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hirisun.cloud.model.application.ReviewInfoVo;
import com.hirisun.cloud.order.bean.application.AppReviewInfo;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 服务申请审核信息表 Mapper 接口
 */
public interface AppReviewInfoMapper extends BaseMapper<AppReviewInfo> {

    /**
     * 根据服务申请信息 id 获取最近一条通过的审核记录
     */
    AppReviewInfo getLastPassReviewInfoByAppInfoId(@Param("appInfoId") String appInfoId);

    /**
     * 根据服务申请信息 id 获取最近一条未通过的审核记录
     */
    AppReviewInfo getLastDenyReviewInfoByAppInfoId(@Param("appInfoId") String id);

    /**
     * 根据服务申请信息 id 获取所有审核记录
     */
    List<AppReviewInfo> getAllReviewInfoByAppInfoId(@Param("appInfoId") String appInfoId);

    /**
     * 根据model name和申请信息ID获取审核信息
     * @param name
     * @param appInfoId
     * @return
     */
    ReviewInfoVo getReviewInfoVoByAppInfoId(@Param("name") String name,@Param("appInfoId") String appInfoId);

    List<ReviewInfoVo> getDynamicReviewInfoVoByAppInfoId(@Param("appInfoId") String appInfoId);




}
