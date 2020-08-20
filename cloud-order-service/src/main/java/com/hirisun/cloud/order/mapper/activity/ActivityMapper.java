package com.hirisun.cloud.order.mapper.activity;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hirisun.cloud.model.export.vo.ExportActVo;
import com.hirisun.cloud.order.bean.activity.Activity;

/**
 *  Mapper 接口
 */
public interface ActivityMapper extends BaseMapper<Activity> {

    void terminationActivity(String instanceId);

    /**
     * 回退时获取上级环节信息
     * @param modelId
     * @param instanceId
     * @return
     */
    public List<Activity> selectFallBackActivity(@Param("modelId")String modelId, @Param("instanceId")String instanceId);

    /**
     * 获取当前实例活动的环节
     * @param instanceId
     * @return
     */
    public List<Activity> getResultActivities(@Param("instanceId")String instanceId);

    /**
     * 获取用户待办数量
     * @param userId 用户idcard
     * @return
     */
    public int getAgencyTaskNumberByIdCard(@Param("userId")String userId);

    /**
     * 获取用户科信环节已办
     * @param idCard
     * @return
     */
    int getKxDoneByIdCard(@Param("idCard")String idCard);

    ExportActVo getCurrentNodeWithTimeById(@Param("id") String id);

    List<ExportActVo> getSameDayActivity(@Param("id") String id,@Param("createTime") String createTime);
}
