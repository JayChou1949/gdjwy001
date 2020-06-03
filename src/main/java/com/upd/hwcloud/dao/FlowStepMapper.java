package com.upd.hwcloud.dao;

import com.upd.hwcloud.bean.entity.FlowStep;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upd.hwcloud.bean.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 处理环节 Mapper 接口
 * </p>
 *
 * @author wuc
 * @since 2018-11-26
 */
public interface FlowStepMapper extends BaseMapper<FlowStep> {

    List<FlowStep> getUserFlowStepListByResourceType(@Param("user") User user,
                                                     @Param("resourceType") Long resourceType,
                                                     @Param("stepType") String stepType);

    List<FlowStep> getUserFlowStepListByServiceId(@Param("user") User user,
                                                     @Param("serviceId") String serviceId,
                                                     @Param("stepType") String stepType);

}
