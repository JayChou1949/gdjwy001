package com.hirisun.cloud.model.workflow;

import com.hirisun.cloud.model.service.AppReviewInfoVo;

/**
 * 流程回退时的封装类,用以接口调用传递参数
 * @author 尹涛
 * create Date 2017-5-12
 *
 */
public class FallBackVO {
	//回退时所处的环节ID
	private String currentActivityId;
	//选择的回退环节模型ID串,多个环节之间用','隔开
	private String fallBackModelIds;
	//审批复核等信息接收
	private AppReviewInfoVo approve;
	//反馈信息接收
	private ApplicationFeedbackVo feedBack;

	public String getCurrentActivityId() {
		return currentActivityId;
	}
	public void setCurrentActivityId(String currentActivityId) {
		this.currentActivityId = currentActivityId;
	}
	public String getFallBackModelIds() {
		return fallBackModelIds;
	}
	public void setFallBackModelIds(String fallBackModelIds) {
		this.fallBackModelIds = fallBackModelIds;
	}
	
	public AppReviewInfoVo getApprove() {
		return approve;
	}
	public void setApprove(AppReviewInfoVo approve) {
		this.approve = approve;
	}

	public ApplicationFeedbackVo getFeedBack() {
		return feedBack;
	}

	public void setFeedBack(ApplicationFeedbackVo feedBack) {
		this.feedBack = feedBack;
	}

	@Override
	public String toString() {
		return "FallBackVO [currentActivityId=" + currentActivityId
				+ ", fallBackModelIds=" + fallBackModelIds + "]";
	}
	
}
