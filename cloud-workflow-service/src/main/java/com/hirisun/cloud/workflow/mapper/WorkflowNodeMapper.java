package com.hirisun.cloud.workflow.mapper;

import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.workflow.bean.WorkflowNode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 流程模型 Mapper 接口
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-05
 */
public interface WorkflowNodeMapper extends BaseMapper<WorkflowNode> {

    WorkflowNode getNextNodeById(@Param("id") String id);

    List<WorkflowNode> getOldData();

}
