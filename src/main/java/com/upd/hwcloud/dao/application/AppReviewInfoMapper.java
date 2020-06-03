package com.upd.hwcloud.dao.application;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upd.hwcloud.bean.entity.application.AppReviewInfo;
import com.upd.hwcloud.bean.vo.applicationInfoOrder.ReviewInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 服务申请审核信息表 Mapper 接口
 * </p>
 *
 * @author wuc
 * @since 2018-12-04
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



}
