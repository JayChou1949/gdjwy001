package com.hirisun.cloud.model.param;

import java.io.Serializable;

import lombok.Data;

@Data
public class ActivityParam implements Serializable{

	private static final long serialVersionUID = -7478427779427032403L;

	private Integer activitystatus;
	
	private String instanceId;
	
	private Integer isstart;
	
	private String handlePersons;
	
	private String activityType;
	
	
}
