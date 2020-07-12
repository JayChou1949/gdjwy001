package com.hirisun.cloud.ncov.bean;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("T_NCOV_HOME_PAGE_DATA")
public class NcovHomePageData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1593360308201316618L;

	@TableId(value = "ID", type = IdType.UUID)
	private String id;

	@TableField("DATA_TYPE")
    private String dataType; //数据类型
	
	@TableField("DATA")
    private String data; //json数据
	
	@TableField("CREATE_DATE")
    private String createDate; //创建日期

	@TableField("UPDATE_DATE")
    private String updateDate;//更新日期

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

}
