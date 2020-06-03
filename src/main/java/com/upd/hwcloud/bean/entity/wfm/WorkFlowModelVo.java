package com.upd.hwcloud.bean.entity.wfm;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class WorkFlowModelVo implements Serializable{
	private static final long serialVersionUID = 1L;
	// 主ID
	private String id;
	// 模块名称
	private String modelName;
	// 下一个环节
	private List<WorkFlowModelVo> nextModels;
	// 前一个环节ID
	private String preModelId;
	// 版本号
	private Integer version;
	// 处理模式 0会签 1抢占
	private Integer handleModel;
	// 默认处理人
	private String defaultHandlePerson;
	//当前定义环节处理人
	private String handlePerson;
	//定义环节状态
	private String modelStatus;
	//状态编码
	private String modelStatusCode;
	//类名
	private String modelStatusClassNme;
	//处理时间
	private Date handleDate;
	//接收时间
	private Date reciveDate;
	//实例环节ID
	private String activityId;
	//是否线下（0:线下，1:线上）
	private Integer isOnLine;


	public Integer getIsOnLine() {
		return isOnLine;
	}
	public void setIsOnLine(Integer isOnLine) {
		this.isOnLine = isOnLine;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getPreModelId() {
		return preModelId;
	}

	public void setPreModelId(String preModelId) {
		this.preModelId = preModelId;
	}

	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	public List<WorkFlowModelVo> getNextModels() {
		return nextModels;
	}
	public void setNextModels(List<WorkFlowModelVo> nextModels) {
		this.nextModels = nextModels;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Integer getHandleModel() {
		return handleModel;
	}
	public void setHandleModel(Integer handleModel) {
		this.handleModel = handleModel;
	}
	public String getDefaultHandlePerson() {
		return defaultHandlePerson;
	}
	public void setDefaultHandlePerson(String defaultHandlePerson) {
		this.defaultHandlePerson = defaultHandlePerson;
	}
	public String getHandlePerson() {
		return handlePerson;
	}
	public void setHandlePerson(String handlePerson) {
		this.handlePerson = handlePerson;
	}
	public String getModelStatus() {
		return modelStatus;
	}
	public void setModelStatus(String modelStatus) {
		this.modelStatus = modelStatus;
	}
	
	public Date getHandleDate() {
		return handleDate;
	}
	public void setHandleDate(Date handleDate) {
		this.handleDate = handleDate;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
	public Date getReciveDate() {
		return reciveDate;
	}
	public void setReciveDate(Date reciveDate) {
		this.reciveDate = reciveDate;
	}
	public String getModelStatusCode() {
		return modelStatusCode;
	}
	public void setModelStatusCode(String modelStatusCode) {
		this.modelStatusCode = modelStatusCode;
	}
	public String getModelStatusClassNme() {
		return modelStatusClassNme;
	}
	public void setModelStatusClassNme(String modelStatusClassNme) {
		this.modelStatusClassNme = modelStatusClassNme;
	}

	@Override
	public String toString() {
		return "WorkFlowModelVo{" +
				"id='" + id + '\'' +
				", modelName='" + modelName + '\'' +
				", nextModels=" + nextModels +
				", preModelId='" + preModelId + '\'' +
				", version=" + version +
				", handleModel=" + handleModel +
				", defaultHandlePerson='" + defaultHandlePerson + '\'' +
				", handlePerson='" + handlePerson + '\'' +
				", modelStatus='" + modelStatus + '\'' +
				", modelStatusCode='" + modelStatusCode + '\'' +
				", modelStatusClassNme='" + modelStatusClassNme + '\'' +
				", handleDate=" + handleDate +
				", reciveDate=" + reciveDate +
				", activityId='" + activityId + '\'' +
				", isOnLine=" + isOnLine +
				'}';
	}
}
