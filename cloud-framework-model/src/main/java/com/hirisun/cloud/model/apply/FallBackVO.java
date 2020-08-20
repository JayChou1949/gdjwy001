package com.hirisun.cloud.model.apply;

import lombok.Data;

/**
 * @author  wuxiaoxing 2020-08-18
 *
 */
@Data
public class FallBackVO {
	//回退时所处的环节ID
	private String currentActivityId;
	//选择的回退环节模型ID串,多个环节之间用','隔开
	private String fallBackModelIds;
	//审批复核等信息接收
	private ApplyReviewRecordVO applyReviewRecord;
	//反馈信息接收
	private ApplyFeedbackVO applyFeedback;

	@Override
	public String toString() {
		return "FallBackVO [currentActivityId=" + currentActivityId
				+ ", fallBackModelIds=" + fallBackModelIds + "]";
	}
	
}
