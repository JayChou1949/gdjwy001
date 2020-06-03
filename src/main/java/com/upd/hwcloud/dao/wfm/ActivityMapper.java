package com.upd.hwcloud.dao.wfm;

import com.upd.hwcloud.bean.entity.wfm.Activity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zwb
 * @since 2019-04-10
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
}
