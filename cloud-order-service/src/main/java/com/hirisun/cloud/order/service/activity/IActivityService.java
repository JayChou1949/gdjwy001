package com.hirisun.cloud.order.service.activity;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hirisun.cloud.common.vo.ResponseResult;
import com.hirisun.cloud.model.service.AppReviewInfoVo;
import com.hirisun.cloud.model.user.UserVO;
import com.hirisun.cloud.model.workflow.AdvanceBeanVO;
import com.hirisun.cloud.model.workflow.FallBackVO;
import com.hirisun.cloud.order.bean.activity.Activity;

/**
 *  服务类
 */
public interface IActivityService extends IService<Activity> {

    /**
     * 流转
     */
    public ResponseResult advanceCurrentActivity(AdvanceBeanVO advanceBeanVO);

    /**
     * 流转并发送短信,发送短信所需信息在 map 中
     */
    ResponseResult advanceCurrentActivity(AdvanceBeanVO advanceBeanVO, Map<String, String> map);

    /**
     * 流转
     * @return
     */
    public ResponseResult advanceCurrentActivity(String currentActivityId,AppReviewInfoVo approve);

    ResponseResult advanceCurrentActivity(String currentActivityId, AppReviewInfoVo approve, Map<String, String> map);

    ResponseResult advanceCurrentActivityTemp(String currentActivityId, AppReviewInfoVo approve, Map<String, String> map);

    /**
     * 流转
     * @return
     */
    public ResponseResult advanceCurrentActivity(Activity currentActivity,String handeler);

    /**
     * 参与人处理
     * @return
     */

    public ResponseResult adviseActivity(String currentActivityId,AppReviewInfoVo approve);

    /**
     * 回退当前环节到上个环节

     * @return
     */

    public ResponseResult fallbackCurrentActivity(FallBackVO vo);
    /**
     * 审批不通过是流转到复核环节
     * @param vo 回退信息,复核环节id.当前环节id.回退审核信息
     * @param map 发送短信所需信息
     * @return
     */
    public ResponseResult fallbackOnApproveNotPass(FallBackVO vo, Map<String, String> map);

    /**
     * 终止流程
     * @param bizId
     * @return
     */
    public ResponseResult terminationInstanceOfWorkFlow(String bizId);

    /**
     * 流程转发
     * @param currentActivityId 当前所处环节ID
     * @param handlePersonIds 转发的人员，多个人员以“,”隔开
     * @return
     */
    public ResponseResult forward(String currentActivityId,String handlePersonIds);

    /**
     * 加办
     * @param approve 审批意见
     * @return
     */
    public ResponseResult add(AppReviewInfoVo approve, String handleIds, String currentActivityId, UserVO user);



}
