package com.upd.hwcloud.service.application;

import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.application.AppReviewInfo;
import com.upd.hwcloud.bean.vo.applicationInfoOrder.ReviewInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 服务申请审核信息表 服务类
 * </p>
 *
 * @author wuc
 * @since 2018-12-04
 */
public interface IAppReviewInfoService extends IService<AppReviewInfo> {

    /**
     * 根据服务申请信息 id 获取最近一条通过的审核记录
     * @param id 服务申请信息 id
     */
    AppReviewInfo getLastPassReviewInfoByAppInfoId(String id);

    /**
     * 根据服务申请信息 id 获取最近一条未通过的审核记录
     * @param id 服务申请信息 id
     */
    AppReviewInfo getLastDenyReviewInfoByAppInfoId(String id);

    /**
     * 根据服务申请信息 id 获取所有审核记录
     * @param id 服务申请信息 id
     */
    List<AppReviewInfo> getAllReviewInfoByAppInfoId(String id);

    /**
     * 根据model name和申请信息ID获取审核信息
     * @param name
     * @param appInfoId
     * @return
     */
    ReviewInfoVo getReviewInfoVoByAppInfoId(String name,String appInfoId);



}