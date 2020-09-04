package com.hirisun.cloud.system.bean;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("TB_IAAS_FUN_DETAIL_EXP")
public class FunDetailExp implements Serializable {

	private static final long serialVersionUID = 3658060234202173657L;

	@TableId(value = "ID",type = IdType.UUID)
    private String id;

	@TableField("IAAS_ID")
    private String iaasId;


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

    public FunDetailExp setId(String id) {
        this.id = id;
        return this;
    }

	public String getIaasId() {
		return iaasId;
	}

	public void setIaasId(String iaasId) {
		this.iaasId = iaasId;
	}

	public String getAppId() {
        return appId;
    }

    public FunDetailExp setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public FunDetailExp setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public FunDetailExp setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public FunDetailExp setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public FunDetailExp setRemark(String remark) {
        this.remark = remark;
        return this;
    }

}
