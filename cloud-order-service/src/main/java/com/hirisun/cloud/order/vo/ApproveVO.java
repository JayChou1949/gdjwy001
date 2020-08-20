package com.hirisun.cloud.order.vo;

import com.hirisun.cloud.order.bean.apply.ApplyReviewRecord;
import lombok.Data;

/**
 * @author  wuxiaoxing 2020-08-18
 */
@Data
public class ApproveVO {

    //当前环节ID
    private String currentActivityId;
    //选择的加办人,多个之间用','隔开
    private String userIds;
    //审批复核等信息接收
    private ApplyReviewRecord applyReviewRecord;

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


}
