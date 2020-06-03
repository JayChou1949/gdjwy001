package com.upd.hwcloud.dao.wfm;

import com.upd.hwcloud.bean.entity.wfm.Workflow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.upd.hwcloud.bean.vo.KeyAndValueStr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zwb
 * @since 2019-04-10
 */
public interface WorkflowMapper extends BaseMapper<Workflow> {


   List<KeyAndValueStr> getHandlersMap();

}
