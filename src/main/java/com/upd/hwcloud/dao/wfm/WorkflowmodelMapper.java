package com.upd.hwcloud.dao.wfm;

import com.upd.hwcloud.bean.entity.wfm.Workflowmodel;
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
public interface WorkflowmodelMapper extends BaseMapper<Workflowmodel> {

    List<Workflowmodel> getSubordinateModel(String currentModelId);

    Integer getMaxVersion(String id);
}
