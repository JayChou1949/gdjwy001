package com.hirisun.cloud.model.workflow;

import java.io.Serializable;
import java.util.Map;

/**
 * 流转时封装的流转信息类
 * @author wuxiaoxing 2020-08-10
 */
public class AdvanceBeanVO implements Serializable{
	private static final long serialVersionUID = 1L;
	//流程定义环节id与处理人对应关系
	private Map<String, String> modelMapToPerson;
	//当前要流转的环节
	private String currentActivityId;
	//流程定义环节id与流程参与人列表
	private Map<String, String> modelMapToAdviser;
	//其它通知人id，多个id之间“,”隔开(待办通知人取处理人与参与人)
	private String otherNotice;
	//前序环节通知人，多个id之间“,”隔开(待办通知人取处理人与参与人)
	private String frontNotice; 
	public Map<String, String> getModelMapToPerson() {
		return modelMapToPerson;
	}
	public void setModelMapToPerson(Map<String, String> modelMapToPerson) {
		this.modelMapToPerson = modelMapToPerson;
	}
	public String getCurrentActivityId() {
		return currentActivityId;
	}
	public void setCurrentActivityId(String currentActivityId) {
		this.currentActivityId = currentActivityId;
	}
	
	public Map<String, String> getModelMapToAdviser() {
		return modelMapToAdviser;
	}
	public void setModelMapToAdviser(Map<String, String> modelMapToAdviser) {
		this.modelMapToAdviser = modelMapToAdviser;
	}
	
	public String getOtherNotice() {
		return otherNotice;
	}
	public void setOtherNotice(String otherNotice) {
		this.otherNotice = otherNotice;
	}
	public String getFrontNotice() {
		return frontNotice;
	}
	public void setFrontNotice(String frontNotice) {
		this.frontNotice = frontNotice;
	}
	@Override
	public String toString() {
		return "AdvanceBeanVO [modelMapToPerson=" + modelMapToPerson
				+ ", currentActivityId=" + currentActivityId
				+ ", modelMapToAdviser=" + modelMapToAdviser + "]";
	}
}
