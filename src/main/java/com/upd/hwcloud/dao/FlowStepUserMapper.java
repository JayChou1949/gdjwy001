package com.upd.hwcloud.dao;

import com.upd.hwcloud.bean.entity.FlowStepUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upd.hwcloud.bean.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 处理环节审核人关联表 Mapper 接口
 * </p>
 *
 * @author wuc
 * @since 2018-11-26
 */
public interface FlowStepUserMapper extends BaseMapper<FlowStepUser> {

    List<User> findUserByFlowStepId(@Param("flowStepId") String flowStepId);

    void deleteUserByFlowId(@Param("flowId") String flowId);

}
