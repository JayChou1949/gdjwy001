package com.hirisun.cloud.workflow.service;

import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.workflow.AdvanceBeanVO;
import com.hirisun.cloud.workflow.bean.WorkflowActivity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.workflow.bean.WorkflowNode;

import java.util.Map;

/**
 * <p>
 * 流程流转表 服务类
 * </p>
 *
 * @author wuxiaoxing
 * @since 2020-08-05
 */
public interface WorkflowActivityService extends IService<WorkflowActivity> {

    public void advanceCurrentActivity(AdvanceBeanVO advanceBeanVO, Map<String, String> map, WorkflowNode nextModel);

}
