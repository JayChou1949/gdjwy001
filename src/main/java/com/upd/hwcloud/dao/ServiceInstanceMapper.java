package com.upd.hwcloud.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upd.hwcloud.bean.entity.ServiceInstance;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 */
public interface ServiceInstanceMapper extends BaseMapper<ServiceInstance> {

    List<ServiceInstance> getServiceInstanceByProid(@Param("projectId") String projectId);
}
