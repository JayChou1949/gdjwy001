package com.hirisun.cloud.workflow.service;

import com.hirisun.cloud.common.vo.QueryResponseResult;
import com.hirisun.cloud.model.apply.ApplyReviewRecordVO;
import com.hirisun.cloud.model.apply.FallBackVO;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workflow.AdvanceBeanVO;
import com.hirisun.cloud.workflow.bean.WorkflowActivity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.workflow.bean.WorkflowNode;

import java.util.List;
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

//    public Map<String, String> advanceCurrentActivity(AdvanceBeanVO advanceBeanVO, Map<String, String> map, WorkflowNode nextModel);

    public Map<String, String> advanceActivity(String currentActivityId, Map<String, String> map);

    public Map<String, String> instanceToHandleIdCards(List<String> instanceIdList);

    public Map<String, String> fallbackOnApproveNotPass(FallBackVO vo, Map<String, String> map);

    public Map<String, String> adviseActivity(String currentActivityId);

    public Map<String,String> add(String handlerPersonIds, String currentActivityId,String creatorId);

}
