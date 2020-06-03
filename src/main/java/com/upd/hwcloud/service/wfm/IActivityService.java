package com.upd.hwcloud.service.wfm;

import com.upd.hwcloud.bean.entity.User;
import com.upd.hwcloud.bean.entity.application.AppReviewInfo;
import com.upd.hwcloud.bean.entity.wfm.Activity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upd.hwcloud.bean.entity.wfm.AdvanceBeanVO;
import com.upd.hwcloud.bean.entity.wfm.FallBackVO;
import com.upd.hwcloud.bean.response.R;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zwb
 * @since 2019-04-10
 */
public interface IActivityService extends IService<Activity> {

    /**
     * 流转
     */
    public R advanceCurrentActivity(AdvanceBeanVO advanceBeanVO);

    /**
     * 流转并发送短信,发送短信所需信息在 map 中
     */
    R advanceCurrentActivity(AdvanceBeanVO advanceBeanVO, Map<String, String> map);

    /**
     * 流转
     * @return
     */
    public R advanceCurrentActivity(String currentActivityId,AppReviewInfo approve);

    R advanceCurrentActivity(String currentActivityId, AppReviewInfo approve, Map<String, String> map);

    /**
     * 流转
     * @return
     */
    public R advanceCurrentActivity(Activity currentActivity,String handeler);

    /**
     * 参与人处理
     * @return
     */

    public R adviseActivity(String currentActivityId,AppReviewInfo approve);

    /**
     * 回退当前环节到上个环节

     * @return
     */

    public R fallbackCurrentActivity(FallBackVO vo);
    /**
     * 审批不通过是流转到复核环节
     * @param vo 回退信息,复核环节id.当前环节id.回退审核信息
     * @param map 发送短信所需信息
     * @return
     */
    public R fallbackOnApproveNotPass(FallBackVO vo, Map<String, String> map);

    /**
     * 终止流程
     * @param bizId
     * @return
     */
    public R terminationInstanceOfWorkFlow(String bizId);

    /**
     * 流程转发
     * @param currentActivityId 当前所处环节ID
     * @param handlePersonIds 转发的人员，多个人员以“,”隔开
     * @return
     */
    public R forward(String currentActivityId,String handlePersonIds);

    /**
     * 加办
     * @param approve 审批意见
     * @return
     */
    public R add(AppReviewInfo approve, String handleIds, String currentActivityId, User user);



}
