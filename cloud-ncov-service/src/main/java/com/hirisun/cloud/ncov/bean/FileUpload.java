package com.hirisun.cloud.ncov.bean;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("T_FILE_UPLOAD")
public class FileUpload implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4700285025622384725L;

	@TableId(value = "ID", type = IdType.UUID)
	private String id;

	@TableField("FILE_NAME")
    private String fileName;//文件名

	@TableField("FILE_PATH")
    private String filePath;//文件访问路径,对应fastdfs的fileId

	@TableField("FILE_TYPE")
    private String fileType;//文件类型 excel pdf
	
	@TableField("SERVICE_TYPE")
    private String serviceType;//所属服务 ncov 
	
	@TableField("DATA_TYPE")
	private String dataType;//数据类型 疫情 iaas paas daas

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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
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

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

}
