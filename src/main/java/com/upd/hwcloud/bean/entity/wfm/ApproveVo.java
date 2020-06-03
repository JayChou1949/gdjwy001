package com.upd.hwcloud.bean.entity.wfm;

import com.upd.hwcloud.bean.entity.application.AppReviewInfo;

/**
 * Created by zwb on 2019/4/23.
 */
public class ApproveVo {

    //当前环节ID
    private String currentActivityId;
    //选择的加办人,多个之间用','隔开
    private String userIds;
    //审批复核等信息接收
    private AppReviewInfo approve;

    public String getCurrentActivityId() {
        return currentActivityId;
    }

    public void setCurrentActivityId(String currentActivityId) {
        this.currentActivityId = currentActivityId;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public AppReviewInfo getApprove() {
        return approve;
    }

    public void setApprove(AppReviewInfo approve) {
        this.approve = approve;
    }
}
