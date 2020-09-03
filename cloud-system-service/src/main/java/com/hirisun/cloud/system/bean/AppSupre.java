package com.hirisun.cloud.system.bean;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("TB_APP_SUPRE")
public class AppSupre implements Serializable {

	private static final long serialVersionUID = 7990305644481403414L;

	@TableId(value = "ID",type = IdType.UUID)
    private String id;

	@TableField("MASTER_ID")
    private String masterId;

        /**
     * 应用场景ID
     */
         @TableField("APP_ID")
    private String appId;

        /**
     * 标题
     */
         @TableField("TITLE")
    private String title;

        /**
     * 描述
     */
         @TableField("DESCRIPTION")
    private String description;

    @TableField("STATUS")
    private String status;

    @TableField("REMARK")
    private String remark;


    public String getId() {
        return id;
    }

    public AppSupre setId(String id) {
        this.id = id;
        return this;
    }

    public String getMasterId() {
		return masterId;
	}

	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}

	public String getAppId() {
        return appId;
    }

    public AppSupre setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public AppSupre setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AppSupre setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public AppSupre setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public AppSupre setRemark(String remark) {
        this.remark = remark;
        return this;
    }

}
